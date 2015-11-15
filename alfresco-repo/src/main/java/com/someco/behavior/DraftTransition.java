package com.someco.behavior;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.node.NodeServicePolicies;
import org.alfresco.repo.policy.Behaviour;
import org.alfresco.repo.policy.JavaBehaviour;
import org.alfresco.repo.policy.PolicyComponent;
import org.alfresco.repo.workflow.WorkflowModel;
import org.alfresco.service.cmr.model.FileExistsException;
import org.alfresco.service.cmr.model.FileFolderService;
import org.alfresco.service.cmr.model.FileNotFoundException;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.service.cmr.search.ResultSet;
import org.alfresco.service.cmr.search.SearchService;
import org.alfresco.service.cmr.workflow.WorkflowDefinition;
import org.alfresco.service.cmr.workflow.WorkflowPath;
import org.alfresco.service.cmr.workflow.WorkflowService;
import org.alfresco.service.namespace.QName;
import org.apache.log4j.Logger;

import com.someco.model.SomeCoModel;
import com.someco.model.SomeCoWorkflowModel;

public class DraftTransition implements NodeServicePolicies.OnUpdatePropertiesPolicy, NodeServicePolicies.OnMoveNodePolicy
{
	private NodeService nodeService;
	private PolicyComponent policyComponent;
	private SearchService searchService;
	private WorkflowService workflowService;
	private FileFolderService fileFolderService;
	private Logger logger = Logger.getLogger(DraftTransition.class);

	public void init() {
		logger.debug("Initializing Publish Transitions");
		this.policyComponent.bindClassBehaviour(NodeServicePolicies.OnUpdatePropertiesPolicy.QNAME, ContentModel.TYPE_CONTENT,
				new JavaBehaviour(this, "onUpdateProperties", Behaviour.NotificationFrequency.TRANSACTION_COMMIT));
		this.policyComponent.bindClassBehaviour(NodeServicePolicies.OnMoveNodePolicy.QNAME, ContentModel.TYPE_CONTENT,
				new JavaBehaviour(this, "onMoveNode", Behaviour.NotificationFrequency.TRANSACTION_COMMIT));
	}

	@Override
	public void onMoveNode(ChildAssociationRef oldChildAssocRef,
			ChildAssociationRef newChildAssocRef)
	{
		NodeRef newParent = newChildAssocRef.getParentRef();
		NodeRef newChild = newChildAssocRef.getChildRef();

		if(nodeService.getProperty(newChild, SomeCoModel.PROP_TECH_REVIEWED_QNAME) == null) {
			nodeService.setProperty(newChild, SomeCoModel.PROP_TECH_REVIEWED_QNAME, false);

		}

		Boolean reviewed = (Boolean) nodeService.getProperty(newChild, SomeCoModel.PROP_TECH_REVIEWED_QNAME);
		if(!reviewed){
			String query = "PATH:\"/app:company_home/st:sites/cm:fda/cm:documentLibrary/cm:Reports/cm:Pending\"";
			StoreRef storeRef = new StoreRef(StoreRef.PROTOCOL_WORKSPACE, StoreRef.STORE_REF_WORKSPACE_SPACESSTORE.getIdentifier());
			ResultSet rs = searchService.query(storeRef, SearchService.LANGUAGE_LUCENE, query);
			if(!rs.getNodeRefs().isEmpty()){
				NodeRef pending = rs.getNodeRefs().get(0);
				if(pending.getId().equals(newParent.getId())){
					//start a publish technical report workflow
					startWorkflow(newChildAssocRef);
					Map<QName, Serializable> nodeProperties = nodeService.getProperties(newChild);
					nodeProperties.put(SomeCoModel.PROP_TECH_REVIEWED_QNAME, true);
					nodeService.setProperties(newChild, nodeProperties);
				}

			}

		}
	}

	private void startWorkflow(ChildAssociationRef newChildAssocRef)
	{
		NodeRef node = newChildAssocRef.getChildRef();
		List<WorkflowDefinition> workflows = workflowService.getAllDefinitionsByName("activiti$publishTechnicalReport");
		WorkflowDefinition workflow = workflows.get(0); //Assume we haved deployed it.

		NodeRef workflowPackage = workflowService.createPackage(null);
		nodeService.addChild(workflowPackage, node, ContentModel.ASSOC_CONTAINS, newChildAssocRef.getQName());

		Map<QName, Serializable> parameters = new HashMap<QName, Serializable>();
		parameters.put(WorkflowModel.ASSOC_PACKAGE, workflowPackage);
		parameters.put(WorkflowModel.ASPECT_WORKFLOW_PACKAGE, workflowPackage);
		parameters.put(WorkflowModel.PROP_WORKFLOW_DESCRIPTION, "Technical Report Review: "+nodeService.getProperty(node, ContentModel.PROP_NAME));
		parameters.put(WorkflowModel.PROP_WORKFLOW_DUE_DATE, new Date());
		parameters.put(QName.createQName(SomeCoWorkflowModel.NAMESPACE_SOMECO_WORKFLOW_CONTENT_MODEL, "reviewerEmail"), "");

		WorkflowPath path = workflowService.startWorkflow(workflow.getId(), parameters);

	}

	@Override
	public void onUpdateProperties(NodeRef nodeRef,
			Map<QName, Serializable> before, Map<QName, Serializable> after)
	{
		if(!before.containsKey(SomeCoModel.PROP_REVIEW_FLAG_QNAME) && after.containsKey(SomeCoModel.PROP_REVIEW_FLAG_QNAME)){
			if(nodeService.hasAspect(nodeRef, SomeCoModel.ASPECT_SC_TECH_REVIEWABLE_QNAME)){
				Boolean review = (Boolean) nodeService.getProperty(nodeRef, SomeCoModel.PROP_REVIEW_FLAG_QNAME);
				if(review){
					//move to pending
					String query = "PATH:\"/app:company_home/st:sites/cm:fda/cm:documentLibrary/cm:Reports/cm:Pending\"";
					StoreRef storeRef = new StoreRef(StoreRef.PROTOCOL_WORKSPACE, StoreRef.STORE_REF_WORKSPACE_SPACESSTORE.getIdentifier());
					ResultSet rs = searchService.query(storeRef, SearchService.LANGUAGE_LUCENE, query);
					if(!rs.getNodeRefs().isEmpty()){
						NodeRef pending = rs.getNodeRefs().get(0);
						try
						{
							fileFolderService.move(nodeRef, pending, null);
						}
						catch (FileExistsException e)
						{
							e.printStackTrace();
						}
						catch (FileNotFoundException e)
						{
							e.printStackTrace();
						}
					}
				}
			}
		}
		if(!before.containsKey(SomeCoModel.PROP_IS_ACTIVE_QNAME) && after.containsKey(SomeCoModel.PROP_IS_ACTIVE_QNAME)){
			if(nodeService.hasAspect(nodeRef, SomeCoModel.ASPECT_SC_WEBABLE_QNAME)){
				Boolean isActive = (Boolean) nodeService.getProperty(nodeRef, SomeCoModel.PROP_IS_ACTIVE_QNAME);
				if(isActive){
					//move to published && set revision status
					String query = "PATH:\"/app:company_home/st:sites/cm:fda/cm:documentLibrary/cm:Reports/cm:Published\"";
					StoreRef storeRef = new StoreRef(StoreRef.PROTOCOL_WORKSPACE, StoreRef.STORE_REF_WORKSPACE_SPACESSTORE.getIdentifier());
					ResultSet rs = searchService.query(storeRef, SearchService.LANGUAGE_LUCENE, query);
					if(!rs.getNodeRefs().isEmpty()){
						NodeRef pending = rs.getNodeRefs().get(0);
						try
						{
							//set revision status
							Map<QName, Serializable> properties = nodeService.getProperties(nodeRef);
							properties.put(SomeCoModel.PROP_FINAL_QNAME, "Final");
							nodeService.setProperties(nodeRef, properties);
							fileFolderService.move(nodeRef, pending, null);
						}
						catch (FileExistsException e)
						{
							e.printStackTrace();
						}
						catch (FileNotFoundException e)
						{
							e.printStackTrace();
						}
					}
				}
			}
		}

	}

	public NodeService getNodeService()
	{
		return nodeService;
	}

	public void setNodeService(NodeService nodeService)
	{
		this.nodeService = nodeService;
	}

	public PolicyComponent getPolicyComponent()
	{
		return policyComponent;
	}

	public void setPolicyComponent(PolicyComponent policyComponent)
	{
		this.policyComponent = policyComponent;
	}

	public SearchService getSearchService()
	{
		return searchService;
	}

	public void setSearchService(SearchService searchService)
	{
		this.searchService = searchService;
	}

	public WorkflowService getWorkflowService()
	{
		return workflowService;
	}

	public void setWorkflowService(WorkflowService workflowService)
	{
		this.workflowService = workflowService;
	}

	public FileFolderService getFileFolderService()
	{
		return fileFolderService;
	}

	public void setFileFolderService(FileFolderService fileFolderService)
	{
		this.fileFolderService = fileFolderService;
	}

}
