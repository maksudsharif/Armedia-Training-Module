package com.someco.behavior;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.node.NodeServicePolicies;
import org.alfresco.repo.policy.Behaviour;
import org.alfresco.repo.policy.JavaBehaviour;
import org.alfresco.repo.policy.PolicyComponent;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.namespace.QName;
import org.apache.log4j.Logger;

import com.someco.model.SomeCoModel;

public class GenerateID implements NodeServicePolicies.OnCreateNodePolicy {
	private NodeService nodeService;
	private ServiceRegistry serviceRegistry;
	private PolicyComponent policyComponent;

	public void setNodeService(NodeService nodeService) {
		this.nodeService = nodeService;
	}

	public void setServiceRegistry(ServiceRegistry serviceRegistry) {
		this.serviceRegistry = serviceRegistry;
	}

	public void setPolicyComponent(PolicyComponent policyComponent) {
		this.policyComponent = policyComponent;
	}

	private Logger logger = Logger.getLogger(GenerateID.class);

	public void init() {
		if (logger.isDebugEnabled()) logger.debug("Initializing GenerateID");

		this.policyComponent.bindClassBehaviour(NodeServicePolicies.OnCreateNodePolicy.QNAME,
				ContentModel.TYPE_CONTENT,
				new JavaBehaviour(this, "onCreateNode", Behaviour.NotificationFrequency.TRANSACTION_COMMIT));
	}

	/**
	 * Called when a new node has been created.
	 *
	 * @param childAssocRef the created child association reference
	 */
	@Override
	public void onCreateNode(ChildAssociationRef childAssocRef) {
		//TODO: Implement onCreateNode for Generating IDs for all content created.
	}

	private String generateID() {
		//TODO: Implement logic for creating an unique ID for content.
		return "";
	}
}
