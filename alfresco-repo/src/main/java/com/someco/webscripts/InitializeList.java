package com.someco.webscripts;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.alfresco.model.ContentModel;
import org.alfresco.model.DataListModel;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.ContentReader;
import org.alfresco.service.cmr.repository.ContentService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.service.cmr.search.ResultSet;
import org.alfresco.service.cmr.search.SearchService;
import org.alfresco.service.cmr.site.SiteInfo;
import org.alfresco.service.cmr.site.SiteService;
import org.alfresco.service.namespace.QName;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.log4j.Logger;
import org.springframework.extensions.webscripts.DeclarativeWebScript;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptRequest;

import com.someco.model.CstarModel;

public class InitializeList extends DeclarativeWebScript
{
	Logger logger = Logger.getLogger(InitializeList.class);

	private NodeService nodeService;

	private SiteService siteService;

	private ContentService contentService;

	private ServiceRegistry serviceRegistry;

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

	@Override
	protected Map<String, Object> executeImpl(WebScriptRequest req, Status status) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("message", "ok");
		String query = "PATH:\"/app:company_home/st:sites/cm:fda/cm:documentLibrary/cm:Configuration/cm:fda_cstar.csv\"";
		
		try {
			List<NodeRef> results = performSearch(query);
			List<CSVRecord> data = parseSearchResults(results);
			//model.put("file", data);
			parseData(data);
			
		} catch(Exception e) {
			model.put("status", "error");
			e.printStackTrace();
		}

		return model;
	}

	private void parseData(List<CSVRecord> data)
	{
		NodeRef dataList = getDataListNode();
		if(dataList == null) return;
		if(!data.isEmpty()) {
			data.forEach(i -> {
				Map<QName, Serializable> props = new HashMap<QName, Serializable>();
				try {
					props.put(CstarModel.PROP_OFFICE, i.get(0));
					props.put(CstarModel.PROP_OFFICE_CODE, i.get(1));
					props.put(CstarModel.PROP_DIVISION, i.get(2));
					props.put(CstarModel.PROP_DIVISION_CODE, i.get(3));
					props.put(CstarModel.PROP_BRANCH, i.get(4));
					props.put(CstarModel.PROP_BRANCH_CODE, i.get(5));
					ChildAssociationRef node = nodeService.createNode(dataList,
							ContentModel.ASSOC_CONTAINS,
							QName.createQName("dataListItem" + System.currentTimeMillis()),
							CstarModel.TYPE_CSTAR_DL);
					nodeService.setProperties(node.getChildRef(), props);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		}
	}
	private List<CSVRecord> parseSearchResults(List<NodeRef> results) throws IOException
	{
		ArrayList<CSVRecord> data = new ArrayList<CSVRecord>();
		if(results.isEmpty()){
			logger.debug("NO SEARCH RESULTS FOUND");
			return new ArrayList<CSVRecord>();
		}
		ContentReader cr = contentService.getReader(results.get(0), ContentModel.PROP_CONTENT);
		InputStream is = cr.getContentInputStream();
		
		CSVParser parser = new CSVParser(new InputStreamReader(is), CSVFormat.EXCEL);
		List<CSVRecord> records = parser.getRecords();
		records.remove(0);
		parser.close();
		return records;
	}
	public List<NodeRef> performSearch(String query) throws Exception{
		SearchService searchService = serviceRegistry.getSearchService();
		StoreRef storeRef = new StoreRef(StoreRef.PROTOCOL_WORKSPACE, StoreRef.STORE_REF_WORKSPACE_SPACESSTORE.getIdentifier());
		ResultSet rs = searchService.query(storeRef, SearchService.LANGUAGE_LUCENE, query);
		List<NodeRef> results = rs.getNodeRefs();
		
		return results != null ? results : new ArrayList<NodeRef>();
		
	}
	private NodeRef getDataListNode()
	{
		// Try to find the datalist they requested
		NodeRef list;

		// Get the site
		SiteInfo site = siteService.getSite("fda");
		if(site == null)
		{
		}

		// Now find the data list container with in
		NodeRef container = nodeService.getChildByName(
				site.getNodeRef(),
				ContentModel.ASSOC_CONTAINS,
				"dataLists"
		);
		if(container == null)
		{
			logger.debug("Didn't find DataLists of site: " + site);
		}

		// Now get the data list itself
		list = nodeService.getChildByName(
				container,
				ContentModel.ASSOC_CONTAINS,
				"FDA Organization List"
		);
		if(list != null){
			logger.debug("FDA Organization List found.");
		}

		if(list == null || !nodeService.exists(list))
		{
			//create a new DataList
			logger.debug("Datalist 'FDA Organization List' not found. Creating the DataList now...");
			Map<QName, Serializable> props = new HashMap<QName, Serializable>();
			props.put(ContentModel.PROP_TITLE, "FDA Organization List");
			props.put(ContentModel.PROP_NAME, "FDA Organization List");
			props.put(DataListModel.PROP_DATALIST_ITEM_TYPE, "cstar:dataList");
			list = nodeService.createNode(container, ContentModel.ASSOC_CONTAINS, QName.createQName("FDA Organization List"), DataListModel.TYPE_DATALIST,props).getChildRef();
		}
		return list;
	}



}
