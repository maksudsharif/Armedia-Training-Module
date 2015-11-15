package com.someco.fda;

import java.util.ArrayList;
import java.util.List;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.site.SiteInfo;
import org.alfresco.service.cmr.site.SiteService;
import org.apache.log4j.Logger;

import com.someco.model.CstarModel;

public class FDAServiceImpl implements FDAService
{
	private SiteService siteService;
	private NodeService nodeService;

	private Logger logger = Logger.getLogger(FDAServiceImpl.class);
	@Override
	public List<String> getOffices(String sitename)
	{
		List<String> offices = new ArrayList<String>();
		List<ChildAssociationRef> dataList =  getDataList(sitename);
		if(dataList != null) {
			dataList.forEach(entry -> {
				String office = (String) nodeService.getProperty(entry.getChildRef(), CstarModel.PROP_OFFICE);
				if (!offices.contains(office))
					offices.add(office);
			});
		}
		return offices;
	}

	@Override
	public List<String> getDivisions(String sitename, String office)
	{
		List<String> divisions = new ArrayList<String>();
		List<ChildAssociationRef> dataList = getDataList(sitename);
		if(dataList != null) {
			dataList.forEach(entry -> {
				String o = (String) nodeService.getProperty(entry.getChildRef(), CstarModel.PROP_OFFICE);
				if (o.trim().equals(office.trim())) {
					String div = (String) nodeService.getProperty(entry.getChildRef(), CstarModel.PROP_DIVISION);
					if (!divisions.contains(div)) {
						divisions.add(div);
					}
				}
			});
		}
		return divisions;
	}

	@Override
	public List<String> getBranches(String sitename, String office,
			String division)
	{
		List<String> branches = new ArrayList<String>();
		List<ChildAssociationRef> dataList = getDataList(sitename);
		if(dataList != null) {
			dataList.forEach(entry -> {
				String o = (String) nodeService.getProperty(entry.getChildRef(), CstarModel.PROP_OFFICE);

				String d = (String) nodeService.getProperty(entry.getChildRef(), CstarModel.PROP_DIVISION);

				if (o.trim().equals(office.trim()) && d.trim().equals(division.trim())) {
					String b = (String) nodeService.getProperty(entry.getChildRef(), CstarModel.PROP_BRANCH);
					branches.add(b);
				}

			});
		}
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
	
	private List<ChildAssociationRef> getDataList(String siteId)
	{
		// Try to find the datalist they requested
		NodeRef list;

		// Get the site
		SiteInfo site = siteService.getSite(siteId);
		if(site == null)
		{
			logger.debug(siteId);
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

		if(list == null || !nodeService.exists(list))
		{
			//list not found.
			return null;
		} 
		List<ChildAssociationRef> children = nodeService.getChildAssocs(list);
		return children;
	}


}
