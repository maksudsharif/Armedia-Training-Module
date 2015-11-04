package com.someco.action.executer;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.alfresco.repo.action.ParameterDefinitionImpl;
import org.alfresco.repo.action.executer.ActionExecuterAbstractBase;
import org.alfresco.service.cmr.action.Action;
import org.alfresco.service.cmr.action.ParameterDefinition;
import org.alfresco.service.cmr.dictionary.DataTypeDefinition;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.namespace.QName;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.someco.model.SomeCoModel;

public class SetReviewable extends ActionExecuterAbstractBase {
	
	
	
    
	public final static String NAME = "set-reviewable";
	public final static String PARAM_FLAG = "reviewFlag";
	
	/** The NodeService to be used by the bean */
	protected NodeService nodeService;
	
	private static Log logger = LogFactory.getLog(SetReviewable.class);
	
	@Override
	protected void executeImpl(Action action, NodeRef actionedUponNodeRef) {
			
		Boolean activeFlag = (Boolean)action.getParameterValue(PARAM_FLAG);

		if (activeFlag == null) activeFlag = true;
		
		if (logger.isDebugEnabled()) logger.debug("Inside executeImpl");
					
		// set the sc:isActive property to true
		// set the sc:published property to now
		Map<QName, Serializable> properties = nodeService.getProperties(actionedUponNodeRef);
		properties.put(QName.createQName(SomeCoModel.NAMESPACE_SOMECO_CONTENT_MODEL, SomeCoModel.PROP_REVIEW_FLAG), activeFlag);
		  
		
		// if the aspect has already been added, set the properties
		if (nodeService.hasAspect(actionedUponNodeRef,
				 QName.createQName(
						SomeCoModel.NAMESPACE_SOMECO_CONTENT_MODEL,
						SomeCoModel.ASPECT_SC_TECH_REVIEWABLE))) {
			if (logger.isDebugEnabled()) logger.debug("Node has aspect");
			nodeService.setProperties(actionedUponNodeRef, properties);
		} else {
			// otherwise, add the aspect and set the properties
			if (logger.isDebugEnabled()) logger.debug("Node does not have aspect");
			nodeService.addAspect(actionedUponNodeRef, QName.createQName(SomeCoModel.NAMESPACE_SOMECO_CONTENT_MODEL, SomeCoModel.ASPECT_SC_TECH_REVIEWABLE), properties);
		}                  
				
		if (logger.isDebugEnabled()) logger.debug("Ran web enable/disable action");
                                 
	}

	@Override
	protected void addParameterDefinitions(List<ParameterDefinition> paramList) {
		paramList.add(
		         new ParameterDefinitionImpl(               		// Create a new parameter definition to add to the list
		            PARAM_FLAG,                          			 // The name used to identify the parameter
		            DataTypeDefinition.BOOLEAN,             		// The parameter value type
		            false,                                  		// Indicates whether the parameter is mandatory
		            getParamDisplayLabel(PARAM_FLAG)));   			// The parameters display label
		
	}

	/**
	* @param nodeService The NodeService to set.
	*/
	public void setNodeService(NodeService nodeService) {
		this.nodeService = nodeService;
	}

}
