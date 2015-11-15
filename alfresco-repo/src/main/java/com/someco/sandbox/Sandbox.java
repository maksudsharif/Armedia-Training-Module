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
		// Try to find the datalist they requested
		NodeRef list;
		if(serviceRegistry == null) {
			System.out.println("null");
		}
		// Get the site
		SiteInfo site = siteService.getSite("training");
		if(site == null)
		{
			System.out.println("Site is null");
		}

		// Now find the data list container with in
		NodeRef container = nodeService.getChildByName(
				site.getNodeRef(),
				ContentModel.ASSOC_CONTAINS,
				"dataLists"
				);
		if(container == null)
		{
			System.out.println("Container is null");
		}

		// Now get the data list itself
		list = nodeService.getChildByName(
				container,
				ContentModel.ASSOC_CONTAINS,
				"a111da37-81ca-4eb5-a480-dd86ac4ba753"
				);

		if(list == null || !nodeService.exists(list))
		{
			System.out.println("Datalist is null or not found");
		}
	}
}