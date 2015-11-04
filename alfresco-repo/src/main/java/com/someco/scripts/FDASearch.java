package com.someco.scripts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.site.SiteInfo;
import org.alfresco.service.cmr.site.SiteService;
import org.springframework.extensions.webscripts.DeclarativeWebScript;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptRequest;

import com.someco.submission.FDAServiceImpl;

public class FDASearch extends DeclarativeWebScript
{	
	private FDAServiceImpl fdaServiceImpl;
	private SiteService siteService;
	private NodeService nodeService;
	private String field;

	@Override
	protected Map<String, Object> executeImpl(WebScriptRequest req, Status status) {
		Map<String, Object> model = new HashMap<String, Object>();
		
		// Try to find the datalist they requested
		NodeRef list;

		// Get the site
		SiteInfo site = siteService.getSite("training");
		if(site == null)
		{
			model.put("site", "null");
		} else model.put("site", "found");

		// Now find the data list container with in
		NodeRef container = nodeService.getChildByName(
				site.getNodeRef(),
				ContentModel.ASSOC_CONTAINS,
				"dataLists"
				);
		if(container == null)
		{
			model.put("container", "null");
		} else model.put("container", "found");

		// Now get the data list itself
		list = nodeService.getChildByName(
				container,
				ContentModel.ASSOC_CONTAINS,
				"a111da37-81ca-4eb5-a480-dd86ac4ba753"
				);

		if(list == null || !nodeService.exists(list))
		{
			model.put("list", "null");
		} else model.put("list", "found");
		
		
		
		String siteId = req.getParameter("siteId");
		String office = req.getParameter("office");
		String division = req.getParameter("division");
		
		if(siteId != null) {
			if(office != null) {
				if(division != null) {
					List<String> branches = getFdaServiceImpl().getBranches(siteId, office, division);
					model.put("results", branches);
					return model;
				}
				List<String> divisions = getFdaServiceImpl().getDivisions(siteId, office);
				model.put("results", divisions);
				return model;
			}
			List<String> offices = getFdaServiceImpl().getOffices(siteId);
			model.put("results", offices);
			return model;
		}
		else
		{
			model.put("error", "Error: No SiteID given. Please provide a SiteID.");
			return model;
		}
	}

	public SiteService getSiteService()
	{
		return siteService;
	}

	public void setSiteService(SiteService siteService)
	{
		this.siteService = siteService;
	}

	public NodeService getNodeService()
	{
		return nodeService;
	}

	public void setNodeService(NodeService nodeService)
	{
		this.nodeService = nodeService;
	}

	public FDAServiceImpl getFdaServiceImpl()
	{
		return fdaServiceImpl;
	}

	public void setFdaServiceImpl(FDAServiceImpl fdaServiceImpl)
	{
		this.fdaServiceImpl = fdaServiceImpl;
	}
}
