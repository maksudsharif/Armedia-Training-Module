package com.someco.model;

import org.alfresco.service.namespace.QName;

public interface SomeCoModel {
    
	// Types
	public static final String NAMESPACE_SOMECO_CONTENT_MODEL  = "http://www.someco.com/model/content/1.0";
    public static final String TYPE_SC_DOC = "doc";
    public static final String TYPE_SC_WHITEPAPER = "whitepaper";
    public static final String TYPE_SC_TECHREPORT = "technicalReport";
    
    public static final QName TYPE_SC_DOC_QNAME = QName.createQName(NAMESPACE_SOMECO_CONTENT_MODEL, TYPE_SC_DOC);
    public static final QName TYPE_SC_WHITEPAPER_QNAME = QName.createQName(NAMESPACE_SOMECO_CONTENT_MODEL, TYPE_SC_WHITEPAPER);
    public static final QName TYPE_SC_TECHREPORT_QNAME = QName.createQName(NAMESPACE_SOMECO_CONTENT_MODEL, TYPE_SC_TECHREPORT);
    
    
    // Aspects
    public static final String ASPECT_SC_WEBABLE = "webable";
    public static final String ASPECT_SC_PRODUCT_RELATED = "productRelated";
    public static final String ASPECT_SC_TECH_REVIEWABLE = "techReviewable";
    public static final String ASPECT_SC_IDENTIFIABLE = "identifiable";
    
    public static final QName ASPECT_SC_WEBABLE_QNAME = QName.createQName(NAMESPACE_SOMECO_CONTENT_MODEL, ASPECT_SC_WEBABLE);
    public static final QName ASPECT_SC_PRODUCT_RELATED_QNAME = QName.createQName(NAMESPACE_SOMECO_CONTENT_MODEL, ASPECT_SC_PRODUCT_RELATED);
    public static final QName ASPECT_SC_TECH_REVIEWABLE_QNAME = QName.createQName(NAMESPACE_SOMECO_CONTENT_MODEL, ASPECT_SC_TECH_REVIEWABLE);
    public static final QName ASPECT_SC_IDENTIFIABLE_QNAME = QName.createQName(NAMESPACE_SOMECO_CONTENT_MODEL, ASPECT_SC_IDENTIFIABLE);
    
    // Properties
    public static final String PROP_PRODUCT = "product";
    public static final String PROP_VERSION = "version";
    public static final String PROP_PUBLISHED = "published";
    public static final String PROP_IS_ACTIVE = "isActive";
    public static final String PROP_REVIEW_FLAG = "reviewFlag";
    public static final String PROP_DOCUMENT_ID = "documentId";
    public static final String PROP_FINAL = "final";
    public static final String PROP_COORD_REVIEWED = "coordinatorReviewed";
    public static final String PROP_TECH_REVIEWED = "techReviewed";
    
    public static final QName PROP_PRODUCT_QNAME = QName.createQName(NAMESPACE_SOMECO_CONTENT_MODEL, PROP_PRODUCT);
    public static final QName PROP_VERSION_QNAME = QName.createQName(NAMESPACE_SOMECO_CONTENT_MODEL, PROP_VERSION);
    public static final QName PROP_PUBLISHED_QNAME = QName.createQName(NAMESPACE_SOMECO_CONTENT_MODEL, PROP_PUBLISHED);
    public static final QName PROP_IS_ACTIVE_QNAME = QName.createQName(NAMESPACE_SOMECO_CONTENT_MODEL, PROP_IS_ACTIVE);
    public static final QName PROP_REVIEW_FLAG_QNAME = QName.createQName(NAMESPACE_SOMECO_CONTENT_MODEL, PROP_REVIEW_FLAG);
    public static final QName DOCUMENT_ID_QNAME = QName.createQName(NAMESPACE_SOMECO_CONTENT_MODEL, PROP_DOCUMENT_ID);
    public static final QName PROP_FINAL_QNAME = QName.createQName(NAMESPACE_SOMECO_CONTENT_MODEL, PROP_FINAL);
    public static final QName PROP_COORD_REVIEWED_QNAME = QName.createQName(NAMESPACE_SOMECO_CONTENT_MODEL, PROP_COORD_REVIEWED);
    public static final QName PROP_TECH_REVIEWED_QNAME = QName.createQName(NAMESPACE_SOMECO_CONTENT_MODEL, PROP_TECH_REVIEWED);
    
    // Associations
    public static final String ASSN_RELATED_DOCUMENTS = "relatedDocuments";
    
    public static final QName ASSN_RELATED_DOCUMENTS_QNAME = QName.createQName(NAMESPACE_SOMECO_CONTENT_MODEL, ASSN_RELATED_DOCUMENTS);
 
}
