package com.someco.sandbox;

import org.alfresco.model.ContentModel;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.site.SiteInfo;
import org.alfresco.service.cmr.site.SiteService;
import org.springframework.extensions.webscripts.DeclarativeWebScript;


public class Sandbox extends DeclarativeWebScript
{
	private static ServiceRegistry serviceRegistry;
	private static NodeService nodeService;
	private static SiteService siteService;

	public static ServiceRegistry getServiceRegistry()
	{
		return serviceRegistry;
	}



	public static void setServiceRegistry(ServiceRegistry serviceRegistry)
	{
		Sandbox.serviceRegistry = serviceRegistry;
	}



	public static NodeService getNodeService()
	{
		return nodeService;
	}



	public static void setNodeService(NodeService nodeService)
	{
		Sandbox.nodeService = nodeService;
	}



	public static SiteService getSiteService()
	{
		return siteService;
	}



	public static void setSiteService(SiteService siteService)
	{
		Sandbox.siteService = siteService;
	}



	public static void main(String[] argv) {
		//Testing for beans/logic
	}
}