package com.yindangu.plugin.demo.db2entity.entity;

public class EntityRelation {
	/**
	 * [{
	 * 		"entityCode":"VAR_ENT_UserInfo",
	 * 		"entityType":"方法输出实体",
	 * 		"sourceData":"UserInfo",
	 * 		"sourceType":"表",
	 * 		"id":"fc4d464c92f3527815dfb124a8bb6b11"
	 * }]
	 */
	private String entityCode;
	private String entityType;
	private String sourceData;
	private String sourceType;
	private String id;
	
	public EntityRelation() {
	}

	public EntityRelation(String entityCode, String entityType, String sourceData, String sourceType, String id) {
		this.entityCode = entityCode;
		this.entityType = entityType;
		this.sourceData = sourceData;
		this.sourceType = sourceType;
		this.id = id;
	}

	@Override
	public String toString() {
		return "EntityRelation [entityCode=" + entityCode + ", entityType=" + entityType + ", sourceData=" + sourceData
				+ ", sourceType=" + sourceType + ", id=" + id + "]";
	}

	public String getEntityCode() {
		return entityCode;
	}

	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public String getSourceData() {
		return sourceData;
	}

	public void setSourceData(String sourceData) {
		this.sourceData = sourceData;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
	
	
	
}
