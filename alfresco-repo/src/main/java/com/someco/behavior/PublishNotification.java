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

public class PublishNotification implements NodeServicePolicies.OnCreateNodePolicy
{	
	private NodeService nodeService;
	private PolicyComponent policyComponent;
	private SearchService searchService;
	private WorkflowService workflowService;

	private Logger logger = Logger.getLogger(PublishNotification.class);

	public void init() {
		if(logger.isDebugEnabled()) logger.debug("Initilizaing Publish Notification");
		this.policyComponent.bindClassBehaviour(NodeServicePolicies.OnCreateNodePolicy.QNAME, ContentModel.TYPE_CONTENT, 
				new JavaBehaviour(this, "onCreateNode", Behaviour.NotificationFrequency.TRANSACTION_COMMIT));
	}

	@Override
	public void onCreateNode(ChildAssociationRef childAssocRef)
	{
		NodeRef node = childAssocRef.getChildRef();
		NodeRef parent = childAssocRef.getParentRef();

		if(nodeService.exists(node) && nodeService.exists(parent)){
			if(nodeService.getProperty(node, SomeCoModel.PROP_COORD_REVIEWED_QNAME) == null) {
				nodeService.setProperty(node, SomeCoModel.PROP_COORD_REVIEWED_QNAME, false);

			}
			Boolean reviewed = (Boolean) nodeService.getProperty(node, SomeCoModel.PROP_COORD_REVIEWED_QNAME);
			logger.debug("Reviewed: "+reviewed);
			if(!reviewed){
				String query = "PATH:\"/app:company_home/st:sites/cm:fda/cm:documentLibrary/cm:Reports/cm:Draft\"";
				StoreRef storeRef = new StoreRef(StoreRef.PROTOCOL_WORKSPACE, StoreRef.STORE_REF_WORKSPACE_SPACESSTORE.getIdentifier());
				ResultSet rs = searchService.query(storeRef, SearchService.LANGUAGE_LUCENE, query);
				if(!rs.getNodeRefs().isEmpty()){
					NodeRef draft = rs.getNodeRefs().get(0);
					if(parent.getId().equals(draft.getId())){
						startWorkflow(childAssocRef);
						Map<QName, Serializable> nodeProperties = nodeService.getProperties(node);
						nodeProperties.put(SomeCoModel.PROP_COORD_REVIEWED_QNAME, true);
						nodeService.setProperties(node, nodeProperties);

					}
				}
			}

		}

	}

	private void startWorkflow(ChildAssociationRef childAssocRef)
	{	
		NodeRef node = childAssocRef.getChildRef();
		List<WorkflowDefinition> workflows = workflowService.getAllDefinitionsByName("activiti$flagForReview");
		WorkflowDefinition workflow = workflows.get(0); //Assume we haved deployed it.

		NodeRef workflowPackage = workflowService.createPackage(null);
		nodeService.addChild(workflowPackage, node, ContentModel.ASSOC_CONTAINS, childAssocRef.getQName());

		Map<QName, Serializable> parameters = new HashMap<QName, Serializable>();
		parameters.put(WorkflowModel.ASSOC_PACKAGE, workflowPackage);
		parameters.put(WorkflowModel.ASPECT_WORKFLOW_PACKAGE, workflowPackage);
		parameters.put(WorkflowModel.PROP_WORKFLOW_DESCRIPTION, "Notification for Review: "+nodeService.getProperty(node, ContentModel.PROP_NAME));
		parameters.put(WorkflowModel.PROP_WORKFLOW_DUE_DATE, new Date());

		WorkflowPath path = workflowService.startWorkflow(workflow.getId(), parameters);
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

}
