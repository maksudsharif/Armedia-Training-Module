package com.someco.fda;

import java.util.ArrayList;
import java.util.List;

import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.site.SiteService;
import org.apache.log4j.Logger;

public class FDAServiceImpl implements FDAService
{
	private SiteService siteService;
	private NodeService nodeService;
	private Logger logger = Logger.getLogger(FDAServiceImpl.class);
	
	@Override
	public List<String> getOffices(String sitename)
	{
		List<String> offices = new ArrayList<String>();
		
		//TODO: Implement logic to get Office data.
		
		return offices;
	}

	@Override
	public List<String> getDivisions(String sitename, String office)
	{
		List<String> divisions = new ArrayList<String>();
		
		//TODO: Implement logic to get Division data.
		
		return divisions;
	}

	@Override
	public List<String> getBranches(String sitename, String office,
			String division)
	{
		List<String> branches = new ArrayList<String>();
		
		//TODO: Implement logic to get Branch data.
		
		return branches;
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
}
