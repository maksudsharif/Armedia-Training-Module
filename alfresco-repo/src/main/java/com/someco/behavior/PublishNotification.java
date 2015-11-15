package com.someco.behavior;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.node.NodeServicePolicies;
import org.alfresco.repo.policy.Behaviour;
import org.alfresco.repo.policy.JavaBehaviour;
import org.alfresco.repo.policy.PolicyComponent;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.search.SearchService;
import org.alfresco.service.cmr.workflow.WorkflowService;
import org.apache.log4j.Logger;

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
		//TODO: Implement onCreateNode for flagForReview workflow.

	}

	private void startWorkflow(ChildAssociationRef childAssocRef)
	{	
		//TODO: Implement start Flag for Review Workflow process.
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
