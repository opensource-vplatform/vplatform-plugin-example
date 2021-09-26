package com.yindangu.plugin.demo.db2entity.entity;

public class FieldMap {
/**
 * [{"id":"4aa548c8bac9612f0816c00c2d40c325","parentEntityCode":"VAR_ENT_UserInfo","targetFiled":"Name(学生姓名)","sourceType":"来源字段","sourceData":"Name"},
 * {"id":"d17206a29b3f645da435532fe26c8987","parentEntityCode":"VAR_ENT_UserInfo","targetFiled":"Age(年龄)","sourceType":"来源字段","sourceData":"Age"},
 * {
 * 		"id":"56a2809ddc91f741d9395a73073ac741",
 * 		"parentEntityCode":"VAR_ENT_UserInfo",
 * 		"targetFiled":"id()",
 * 		"sourceType":"来源字段",
 * 		"sourceData":"id"
 * }]
 */
	
	private String id;
	private String parentEntityCode;
	private String targetFiled;
	private String sourceType;
	private String sourceData;
	
	public FieldMap() {}
	

	public FieldMap(String id, String parentEntityCode, String targetFiled, String sourceType, String sourceData) {
		this.id = id;
		this.parentEntityCode = parentEntityCode;
		this.targetFiled = targetFiled;
		this.sourceType = sourceType;
		this.sourceData = sourceData;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getParentEntityCode() {
		return parentEntityCode;
	}


	public void setParentEntityCode(String parentEntityCode) {
		this.parentEntityCode = parentEntityCode;
	}


	public String getTargetFiled() {
		return targetFiled;
	}


	public void setTargetFiled(String targetFiled) {
		this.targetFiled = targetFiled;
	}


	public String getSourceType() {
		return sourceType;
	}


	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}


	public String getSourceData() {
		return sourceData;
	}


	public void setSourceData(String sourceData) {
		this.sourceData = sourceData;
	}


	@Override
	public String toString() {
		return "FieldMap [id=" + id + ", parentEntityCode=" + parentEntityCode + ", targetFiled=" + targetFiled
				+ ", sourceType=" + sourceType + ", sourceData=" + sourceData + "]";
	}
	
	
}
