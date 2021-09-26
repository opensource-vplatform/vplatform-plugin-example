package com.yindangu.v3.formula.rule.inte;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yindangu.v3.business.VDS;
import com.yindangu.v3.business.metadata.api.IDAS;
import com.yindangu.v3.business.metadata.api.IDataObject;
import com.yindangu.v3.business.metadata.api.IDataView;
import com.yindangu.v3.business.plugin.business.api.rule.IRule;
import com.yindangu.v3.business.plugin.business.api.rule.IRuleContext;
import com.yindangu.v3.business.plugin.business.api.rule.IRuleOutputVo;
import com.yindangu.v3.business.vds.IVDS;
import com.yindangu.v3.platform.plugin.util.VdsUtils;

public class UpdateRecord implements IRule {

	private static final Logger log = LoggerFactory.getLogger(UpdateRecord.class);
	public static final String D_CODE="updateRecordYindangu";
	public static final String D_InputEntity="inputEntity";
	public static final String D_SaveAll="isSaveAll",D_OutUpdateCount="updateCount",D_TableName="tableName";

	@Override
	public IRuleOutputVo evaluate(IRuleContext context) {
		try {
			Object dv =context.getVObject().getInput(D_InputEntity);
			IDataView dataView = (IDataView)dv;// (DataView) getDataViewWithType(context, sourceName, dataSourceType);
			String tableName =(String) context.getInput(D_TableName);
			List<IDataObject> records = dataView.select();
			int updateCount = updateLogic(tableName, records);
			return context.newOutputVo().put(D_OutUpdateCount, updateCount) ;
		}
		catch(SQLException e) {
			log.error("",e);
			return context.newOutputVo();//.setSuccess(false).setMessage(e.getMessage());
		}
	}
 
 
	/**
	 * 把前端的数据按id保存到数据库
	 * @param targetName
	 * @param targetNameMapping
	 * @param addList
	 */
	@SuppressWarnings("rawtypes")
	private int updateLogic(String targetName,   List<IDataObject> records) throws SQLException{
		if (VdsUtils.collection.isEmpty(records)) {
			return 0;
		} 
		IVDS vds = VDS.getIntance();
		List<String> idParams = new ArrayList<String>(); 
		for (IDataObject obj : records) {
			// obj.getId()如果为空，不需要处理，使用UUID产生的数据在数据库中不可能存在
			String newId = obj.getId();
			if (!VdsUtils.string.isEmpty(newId)) {
				idParams.add(newId); 
			}
		}
		Map params = Collections.singletonMap("ids", idParams);
		IDAS das = vds.getDas();
		IDataView targetDV = das.find("select * from " + targetName + " where id in (:ids)", params);
		Set<String> column = targetDV.getMetadata().getColumnNamesAll(); 
		
		Map<String, IDataObject> modifiedMap = new HashMap<String, IDataObject>();
		List<IDataObject> updateList = targetDV.select();
		for(IDataObject o : updateList) {
			modifiedMap.put(o.getId(), o);
		}
		for (IDataObject obj : records) {
			IDataObject object = modifiedMap.get(obj.getId());
			if(object == null) {
				object = targetDV.insertDataObject();
			}
			
			for(String fd : column) {
				if(!fd.startsWith("H_")) {
					Object val = obj.get(fd);
					object.set(fd, val);
				}
			}
		}
 
		targetDV.acceptChanges();
		
		//删除数据
		das.executeUpdate("delete from " + targetName + " where id not in (:ids)", params);
		return records.size();
	}
}
