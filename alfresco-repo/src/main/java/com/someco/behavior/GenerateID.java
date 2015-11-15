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
		NodeRef node = childAssocRef.getChildRef();
		if(nodeService.exists(node)){
			if(!nodeService.hasAspect(node, SomeCoModel.ASPECT_SC_IDENTIFIABLE_QNAME)){
				Map<QName, Serializable> properties = new HashMap<QName, Serializable>();
				properties.put(SomeCoModel.DOCUMENT_ID_QNAME, generateID());
				nodeService.addAspect(node, SomeCoModel.ASPECT_SC_IDENTIFIABLE_QNAME, properties);
			}
		}
	}

	private String generateID() {
		String id = UUID.randomUUID().toString().substring(0, 9).toUpperCase();

		return id.substring(0, 9);
	}
}
