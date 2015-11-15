package com.someco.webscripts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.ContentService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.site.SiteService;
import org.apache.commons.csv.CSVRecord;
import org.apache.log4j.Logger;
import org.springframework.extensions.webscripts.DeclarativeWebScript;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptRequest;

public class InitializeList extends DeclarativeWebScript
{
	Logger logger = Logger.getLogger(InitializeList.class);
	private NodeService nodeService;
	private SiteService siteService;
	private ContentService contentService;
	private ServiceRegistry serviceRegistry;

	@Override
	protected Map<String, Object> executeImpl(WebScriptRequest req, Status status) {
		Map<String, Object> model = new HashMap<String, Object>();
		
		//TODO: Implement controller to create datalist/populate datalist for FDA Organization List
		
		return model;
	}
	/**
	 * Recommended methods to complete Tasks
	 * @method void parseData
	 * @method List<CSVRecord> parseSearchResults
	 * @method List<NodeRef> performSearch
	 * @method NodeRef getDataList
	 */
	private void parseData(List<CSVRecord> data)
	{
		
	}
	private List<CSVRecord> parseSearchResults(List<NodeRef> results) throws IOException
	{
		return new ArrayList<CSVRecord>();
	}
	public List<NodeRef> performSearch(String query) throws Exception{
		return new ArrayList<NodeRef>();
	}
	private NodeRef getDataListNode()
	{
		return null;
	}

	public NodeService getNodeService()
	{
		return nodeService;
	}
	public void setNodeService(NodeService nodeService)
	{
		this.nodeService = nodeService;
	}
	public SiteService getSiteService()
	{
		return siteService;
	}
	public void setSiteService(SiteService siteService)
	{
		this.siteService = siteService;
	}

	public ContentService getContentService()
	{
		return contentService;
	}

	public void setContentService(ContentService contentService)
	{
		this.contentService = contentService;
	}
	public ServiceRegistry getServiceRegistry()
	{
		return serviceRegistry;
	}
	public void setServiceRegistry(ServiceRegistry serviceRegistry)
	{
		this.serviceRegistry = serviceRegistry;
	}
}
