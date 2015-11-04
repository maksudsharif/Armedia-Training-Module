package com.someco.model;

import org.alfresco.service.namespace.QName;

public interface CstarModel {
    
	// Types
	public static final String NAMESPACE_CSTAR_MODEL  = "http://www.someco.com/model/content/1.0";
	public static final QName TYPE_CSTAR_DL = QName.createQName(NAMESPACE_CSTAR_MODEL, "dataList");
	
	
	public static final QName PROP_OFFICE = QName.createQName(NAMESPACE_CSTAR_MODEL, "office");
	public static final QName PROP_OFFICE_CODE = QName.createQName(NAMESPACE_CSTAR_MODEL, "officeCode");
	
	public static final QName PROP_BRANCH = QName.createQName(NAMESPACE_CSTAR_MODEL, "branch");
	public static final QName PROP_BRANCH_CODE = QName.createQName(NAMESPACE_CSTAR_MODEL, "branchCode");
	
	public static final QName PROP_DIVISION = QName.createQName(NAMESPACE_CSTAR_MODEL, "division");
	public static final QName PROP_DIVISION_CODE = QName.createQName(NAMESPACE_CSTAR_MODEL, "divisionCode");
	
   
}
