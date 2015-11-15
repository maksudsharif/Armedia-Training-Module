package com.someco.webscripts;

import java.util.HashMap;
import java.util.Map;

import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.site.SiteService;
import org.springframework.extensions.webscripts.DeclarativeWebScript;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptRequest;

import com.someco.fda.FDAServiceImpl;

public class FDASearch extends DeclarativeWebScript
{	
	private FDAServiceImpl fdaServiceImpl;
	private SiteService siteService;
	private NodeService nodeService;

	@Override
	protected Map<String, Object> executeImpl(WebScriptRequest req, Status status) {
		Map<String, Object> model = new HashMap<String, Object>();

		//TODO: Implement controller for FDA Search

		return model;
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
