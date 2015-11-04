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

public class GenerateID implements NodeServicePolicies.OnCreateNodePolicy, NodeServicePolicies.OnMoveNodePolicy
{
	private NodeService nodeService;
	@SuppressWarnings("unused")
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
		if(logger.isDebugEnabled()) logger.debug("Initializing GenerateID");

		this.policyComponent.bindClassBehaviour(NodeServicePolicies.OnCreateNodePolicy.QNAME,
				ContentModel.TYPE_CONTENT,
				new JavaBehaviour(this, "onCreateNode", Behaviour.NotificationFrequency.TRANSACTION_COMMIT));

		this.policyComponent.bindClassBehaviour(NodeServicePolicies.OnMoveNodePolicy.QNAME, ContentModel.TYPE_CONTENT,
				new JavaBehaviour(this, "onMoveNode", Behaviour.NotificationFrequency.TRANSACTION_COMMIT));

	}

	/**
	 * Called when a new node has been created.
	 *
	 * @param childAssocRef the created child association reference
	 */
	@Override
	public void onCreateNode(ChildAssociationRef childAssocRef) {
		if(nodeService.exists(childAssocRef.getParentRef())) {
			String parent =  (String) nodeService.getProperty(childAssocRef.getParentRef(), ContentModel.PROP_NAME);
			if(parent.equals("Published")){
				if(nodeService.exists(childAssocRef.getChildRef())){
					NodeRef child = childAssocRef.getChildRef();
					if(!nodeService.hasAspect(child, SomeCoModel.ASPECT_SC_IDENTIFIABLE_QNAME)){
						logger.debug("Child doesn't have Identifiable. Adding Identifiable and Setting Properties.");
						logger.debug("Creating properties map.");
						Map<QName, Serializable> props = new HashMap<QName, Serializable>();
						logger.debug("Putting sc:documentId into properties map");
						props.put(SomeCoModel.DOCUMENT_ID_QNAME, generateID());

						logger.debug("Calling NodeService.addAspect()");
						nodeService.addAspect(child, SomeCoModel.ASPECT_SC_IDENTIFIABLE_QNAME, props);
						logger.debug("Added sc:identifiable aspect and set properties.");
					}
					else if(nodeService.getProperty(child, SomeCoModel.PROP_FINAL_QNAME) == null){
						logger.debug("Child doesn't have a revision marker. Adding one now.");
						logger.debug("Creating properties map.");
						Map<QName, Serializable> props = new HashMap<QName, Serializable>();
						props.put(SomeCoModel.PROP_FINAL_QNAME, "Final");
						
						logger.debug("Calling NodeService.setProperty()");
						nodeService.setProperties(child, props);
						logger.debug("Added revision marker.");
					}
				}
			}

		} else logger.debug("Parent ref not found");


	}

	private String generateID()
	{
		String id = UUID.randomUUID().toString().substring(0, 5).toUpperCase();

		return "BELVAC-P"+id;
	}

	@Override
	public void onMoveNode(ChildAssociationRef oldChildAssocRef,
			ChildAssociationRef newChildAssocRef)
	{
		if(nodeService.exists(newChildAssocRef.getParentRef())) {
			if(nodeService.exists(newChildAssocRef.getChildRef()))
			{
				NodeRef child = newChildAssocRef.getChildRef();
				if(!nodeService.hasAspect(child, SomeCoModel.ASPECT_SC_IDENTIFIABLE_QNAME))
				{
					logger.debug("Child doesn't have Identifiable. Adding Identifiable and Setting Properties.");
					logger.debug("Creating properties map.");
					Map<QName, Serializable> props = new HashMap<QName, Serializable>();
					logger.debug("Putting sc:documentId into properties map");
					props.put(SomeCoModel.DOCUMENT_ID_QNAME, generateID());

					logger.debug("Calling NodeService.addAspect()");
					nodeService.addAspect(child, SomeCoModel.ASPECT_SC_IDENTIFIABLE_QNAME, props);
					logger.debug("Added sc:identifiable aspect and set properties.");
				}
				else
				{
					
				}
			}
		}
		else
		{
			
		}
	}
}
