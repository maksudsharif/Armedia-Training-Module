package com.someco.bpm;

import java.util.List;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.context.Context;
import org.alfresco.repo.workflow.activiti.ActivitiConstants;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.action.ActionDefinition;
import org.alfresco.service.cmr.action.ActionService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FlagReview implements TaskListener {
    private static final long serialVersionUID = 1L;

   
    private static final String RECIP_PROCESS_VARIABLE = "scwf_reviewerEmail";
    
    private static Log logger = LogFactory.getLog(FlagReview.class);
    
    @Override
    public void notify(DelegateTask task) {
        logger.debug("Inside ExternalReviewNotification.notify()");
        logger.debug("Task ID:" + task.getId());
        logger.debug("Task name:" + task.getName());
        logger.debug("Task proc ID:" + task.getProcessInstanceId());
        logger.debug("Task def key:" + task.getTaskDefinitionKey());

        
        ActionService actionService = getServiceRegistry().getActionService();
        List<ActionDefinition> mailAction = actionService.getActionDefinitions();
        for(int i=0; i < mailAction.size(); i++){
        	System.out.println(mailAction.get(i));
        }
    
        logger.debug("Mail action executed");
        
        return;
    }

    /* taken from ActivitiScriptBase.java */
    protected ServiceRegistry getServiceRegistry() {
        ProcessEngineConfigurationImpl config = Context.getProcessEngineConfiguration();
        if (config != null) {
            // Fetch the registry that is injected in the activiti spring-configuration
            ServiceRegistry registry = (ServiceRegistry) config.getBeans().get(ActivitiConstants.SERVICE_REGISTRY_BEAN_KEY);
            if (registry == null) {
                throw new RuntimeException(
                            "Service-registry not present in ProcessEngineConfiguration beans, expected ServiceRegistry with key" + 
                            ActivitiConstants.SERVICE_REGISTRY_BEAN_KEY);
            }
            return registry;
        }
        throw new IllegalStateException("No ProcessEngineCOnfiguration found in active context");
    }   
}
