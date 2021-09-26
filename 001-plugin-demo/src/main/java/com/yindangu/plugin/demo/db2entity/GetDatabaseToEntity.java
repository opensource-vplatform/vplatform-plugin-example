package com.yindangu.plugin.demo.db2entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.yindangu.plugin.demo.BusinessException;
import com.yindangu.plugin.demo.db2entity.entity.EntityRelation;
import com.yindangu.plugin.demo.db2entity.entity.FieldMap;
import com.yindangu.plugin.demo.db2entity.enums.EntityType;
import com.yindangu.v3.business.VDS;
import com.yindangu.v3.business.formula.api.IFormulaEngine;
import com.yindangu.v3.business.metadata.api.IDAS;
import com.yindangu.v3.business.metadata.api.IDataObject;
import com.yindangu.v3.business.metadata.api.IDataView;
import com.yindangu.v3.business.plugin.business.api.rule.IRule;
import com.yindangu.v3.business.plugin.business.api.rule.IRuleContext;
import com.yindangu.v3.business.plugin.business.api.rule.IRuleOutputVo;

 
 
public class GetDatabaseToEntity implements IRule {
	public static final String D_PARAM_REL="ENT_EntityRelation",D_PARAM_FILEMAP="ENT_FieldMap";
	
	
	@Override
	public IRuleOutputVo evaluate(IRuleContext context) {

		String entityRelationJson = (String)context.getInput(D_PARAM_REL);
		String fieldMapJson = (String)context.getInput(D_PARAM_FILEMAP);
		
		//将解析后的Json字符串 进行赋值操作
		List<EntityRelation> rel =parseEntityRelation(entityRelationJson);
		List<FieldMap> fm = parseFieldMap(fieldMapJson);
		setDataToEntity(context, rel, fm);
		
		return null;
	}
	
	
	
	
	

	//用来接收来源数据，key：实体编码，value：来源数据的结果集
	Map<String, List<IDataObject>> sourceMap = new HashMap<String, List<IDataObject>>();
	//用来接收实体编码，实体类型【方法输出，方法输入，方法变量】
	String entityCode,entityType;
	
	/**
	 * 处理实体与表之间的数据操作
	 * @param entityRelations 解析后的List对象【实体关系】
	 * @param fieldMaps 解析后的List对象【字段映射】
	 */
	private void setDataToEntity(IRuleContext context, List<EntityRelation> entityRelations, List<FieldMap> fieldMaps) {
		//遍历出 【实体关系】 实体对象
		for(EntityRelation eRelation : entityRelations) {
			entityCode = eRelation.getEntityCode();
			entityType = eRelation.getEntityType();
			setSelectSource(eRelation.getSourceData(), eRelation.getSourceType());
			
//==================================================================
//==================		上面完成了规则窗体左边的【实体关系】		===================
//==================================================================

			//用来接收属于当前【实体关系】对象实体编码    的字段映射集
			List<FieldMap> fMapList = new ArrayList<FieldMap>();
			//遍历将属于当前 【字段映射】 的实体对象写入上行字段映射集List对象中
			for(FieldMap fMap : fieldMaps) {
				//判断是否属于    当前【实体关系】对象实体编码    的字段映射集
				if(fMap.getParentEntityCode().equals(entityCode)) {
					fMapList.add(fMap);
				}
			}
			
			assignment(context, fMapList);
		}
	}
	
	/**
	 * 查询出来源数据所对应的数据
	 * @param sourceData 来源数据 名称【表名 | 查询名】
	 * @param sourctType 来源数据 类型【表 | 查询】
	 */
	public void setSelectSource(String sourceData, String sourctType){
		//查询数据库
		IDAS das = VDS.getIntance().getDas() ;//IMetaDataFactory.getService().das();
		IDataView dataView;
		if("表".equals(sourctType)){
			dataView = das.find(" select * from " + sourceData);
		}else {//查询
			dataView = das.findByQueryName(sourceData, new HashMap());//, "", "");
		}
		//获取到来源数据的结果集
		List<IDataObject> daList = dataView.select();
		//写入来源数据的map对象中，以实体编码作为key
		sourceMap.put(entityCode, daList);
	}
	
	/**
	 * 将来源数据赋给目标实体中
	 * @param context 规则上下文对象
	 * @param fMapList 字段映射集
	 */
	private void assignment(IRuleContext context, List<FieldMap> fMapList) {
		//用来接收实体所属类型编码
		String modelTypeCode = "";
		//遍历枚举，取出对应的类型编码
		for(EntityType eType : EntityType.values()) {
			if(eType.getType().equals(entityType)) {
				modelTypeCode = eType.getTypeCode();
			}
		}
		//异常
		if(modelTypeCode.isEmpty()) {throw new BusinessException("GetDatabaseToEntity【fuzhi方法】：获取实体对应的类型编码时发生错误");}
		IFormulaEngine engine = VDS.getIntance().getFormulaEngine() ;//IMetaDataFactory.getService().das();
		//获取到指定实体的 DataView 对象( FormulaEngineFactory.getFormulaEngine())
		IDataView dataView = (IDataView)engine.eval(modelTypeCode + "." + entityCode);
		//获取到来源数据结果集
		List<IDataObject> sourceData = sourceMap.get(entityCode);
		//遍历来源数据结果集
		for(IDataObject dObject : sourceData) {
			//在实体末尾处新增一行实体记录
			IDataObject dataObject = dataView.insertDataObject();
			for(FieldMap fMap : fMapList) {
				//获取到【字段映射】对象目标字段编码
				String targetField = fMap.getTargetFiled();
				targetField = targetField.substring(0, targetField.indexOf("("));
				//用来接收需要赋值的数据
				String value;
				if("表达式".equals(fMap.getSourceType())){
					value = engine.eval(fMap.getSourceData()).toString();
				}else{//来源字段
					value = dObject.get(fMap.getSourceData()).toString();
				}
				//给实体字段赋值
				dataObject.set(targetField, value);
			}
		}
	}
	
	/**
	 * 解析【实体关系】Json格式字符串
	 * @param entityRelationJson【实体关系】Json格式字符串
	 * @return 解析后的List对象 List<EntityRelation>
	 */
	private List<EntityRelation> parseEntityRelation(String entityRelationJson){
		Gson gson = new Gson();
		List<EntityRelation> entityRelations = new ArrayList<EntityRelation>();
		List erJsonList = gson.fromJson(entityRelationJson, List.class);
		for(int i = 0; i < erJsonList.size(); i++) { 
			//将得到的Json格式字符串强转成Map对象
			Map<String,Object> map = (Map<String, Object>) erJsonList.get(i);
			String entityCode = (String) map.get("EntityCode");
			String entityType = (String) map.get("EntityType");
			String sourceData = (String) map.get("SourceData");
			String sourceType = (String) map.get("SourceType");
			String id = (String) map.get("id");
			entityRelations.add(new EntityRelation(entityCode, entityType, sourceData, sourceType, id));
		}
		return entityRelations;
	}
	
	/**
	 * 解析【字段映射】Json格式字符串
	 * @param fieldMapJson【字段映射】Json格式字符串
	 * @return 解析后的List对象 List<FieldMap>
	 */
	private List<FieldMap> parseFieldMap(String fieldMapJson){
		Gson gson = new Gson();
		List<FieldMap> fieldMaps = new ArrayList<FieldMap>();
		List fmJsonList = gson.fromJson(fieldMapJson, List.class);
		for(int i = 0; i < fmJsonList.size(); i++) { 
			//将得到的Json格式字符串强转成Map对象
			Map<String,Object> map = (Map<String, Object>) fmJsonList.get(i);
			String parentEntityCode = (String) map.get("ParentEntityCode");
			String targetFiled = (String) map.get("TargetFiled");
			String sourceType = (String) map.get("SourceType");
			String sourceData = (String) map.get("SourceData");
			String id = (String) map.get("id");
			fieldMaps.add(new FieldMap(id, parentEntityCode, targetFiled, sourceType, sourceData));
		}
		return fieldMaps;
	}
	
}

