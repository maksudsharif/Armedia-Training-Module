package com.someco.scripts;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.alfresco.model.ContentModel;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.ContentReader;
import org.alfresco.service.cmr.repository.ContentService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.service.cmr.search.ResultSet;
import org.alfresco.service.cmr.search.SearchService;
import org.alfresco.service.cmr.site.SiteService;
import org.alfresco.service.namespace.QName;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.extensions.webscripts.DeclarativeWebScript;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptRequest;

import com.someco.model.CstarModel;

public class InitializeList extends DeclarativeWebScript
{
	Log logger = LogFactory.getLog(InitializeList.class);

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
		model.put("status", "ok");
		String query = "PATH:\"/app:company_home/cm:fda_cstar.csv\"";
		
		try {
			List<NodeRef> results = performSearch(query);
			List<CSVRecord> data = parseSearchResults(results);
			model.put("file", data);
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
		data.forEach(i -> {
			Map<QName, Serializable> props = new HashMap<QName, Serializable>();
			try {
				props.put(CstarModel.PROP_OFFICE, i.get(0));
				props.put(CstarModel.PROP_OFFICE_CODE, i.get(1));
				props.put(CstarModel.PROP_DIVISION, i.get(2));
				props.put(CstarModel.PROP_DIVISION_CODE, i.get(3));
				props.put(CstarModel.PROP_BRANCH, i.get(4));
				props.put(CstarModel.PROP_BRANCH_CODE, i.get(5));
				ChildAssociationRef node = nodeService.createNode(	dataList, 
																	ContentModel.ASSOC_CONTAINS, 
																	QName.createQName("dataListItem"+System.currentTimeMillis()), 
																	CstarModel.TYPE_CSTAR_DL);
				nodeService.setProperties(node.getChildRef(), props);
			} catch (Exception e) {
				e.printStackTrace();
			}	
		});	
	}
	private List<CSVRecord> parseSearchResults(List<NodeRef> results) throws IOException
	{
		ArrayList<CSVRecord> data = new ArrayList<CSVRecord>();
		ContentReader cr = contentService.getReader(results.get(0), ContentModel.PROP_CONTENT);
		InputStream is = cr.getContentInputStream();
		
		CSVParser parser = new CSVParser(new InputStreamReader(is), CSVFormat.EXCEL);
		List<CSVRecord> records = parser.getRecords();
		records.remove(0);
		return records;
	}
	public List<NodeRef> performSearch(String query) throws Exception{
		SearchService searchService = serviceRegistry.getSearchService();
		StoreRef storeRef = new StoreRef(StoreRef.PROTOCOL_WORKSPACE, StoreRef.STORE_REF_WORKSPACE_SPACESSTORE.getIdentifier());
		ResultSet rs = searchService.query(storeRef, SearchService.LANGUAGE_LUCENE, query);
		List<NodeRef> customerFolders = rs.getNodeRefs(); 
		
		return customerFolders != null ? customerFolders : new ArrayList<NodeRef>();
		
	}
	private NodeRef getDataListNode()
	{
		NodeRef site = siteService.getSiteRoot();
		List<ChildAssociationRef> children = nodeService.getChildAssocs(site);
		String sitename = "name";

		NodeRef training = children.get(children.size()-1).getChildRef();
		children = nodeService.getChildAssocs(training);
		NodeRef dataLists = children.get(children.size()-1).getChildRef();
		children = nodeService.getChildAssocs(dataLists);

		NodeRef cstar_parent = children.get(0).getParentRef();
		NodeRef cstar_child = children.get(0).getChildRef();
		
		return cstar_child;
	}



}
