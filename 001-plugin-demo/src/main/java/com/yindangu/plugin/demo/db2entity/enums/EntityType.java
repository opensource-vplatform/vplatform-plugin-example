package com.yindangu.plugin.demo.db2entity.enums;


public enum EntityType {
	METHOD_VAR("方法变量实体", "BR_VAR_PARENT"),
	METHODIN_VAR("方法输入实体", "BR_IN_PARENT"),
	METHODOUT_VAR("方法输出实体", "BR_OUT_PARENT");
	
	private final String entityType;
	private final String modelTypeCode;
	private EntityType(String entityType, String modelTypeCode) {
		this.entityType = entityType;
		this.modelTypeCode = modelTypeCode;
	}

	public String getType() {
		return this.entityType;
	}
	public String getTypeCode() {
		return this.modelTypeCode;
	}
	
}
