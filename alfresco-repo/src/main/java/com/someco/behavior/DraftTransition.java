package com.someco.behavior;

import java.io.Serializable;
import java.util.Map;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.node.NodeServicePolicies;
import org.alfresco.repo.policy.Behaviour;
import org.alfresco.repo.policy.JavaBehaviour;
import org.alfresco.repo.policy.PolicyComponent;
import org.alfresco.service.cmr.model.FileFolderService;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.search.SearchService;
import org.alfresco.service.cmr.workflow.WorkflowService;
import org.alfresco.service.namespace.QName;
import org.apache.log4j.Logger;

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
		//TODO: Implement onMoveNode for Draft/Review/Publish Folder Structure
	}

	private void startWorkflow(ChildAssociationRef newChildAssocRef)
	{
		//TODO: Implement start PublishTechnicalReport Workflow

	}

	@Override
	public void onUpdateProperties(NodeRef nodeRef,
			Map<QName, Serializable> before, Map<QName, Serializable> after)
	{
		//TODO: Implement onUpdateProperties for both flags of Publication (reviewFlag and isActive)
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
