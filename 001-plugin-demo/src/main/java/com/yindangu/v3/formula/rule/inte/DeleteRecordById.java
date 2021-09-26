package com.yindangu.v3.formula.rule.inte;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.yindangu.v3.business.VDS;
import com.yindangu.v3.business.metadata.api.IDAS;
import com.yindangu.v3.business.plugin.business.api.rule.IRule;
import com.yindangu.v3.business.plugin.business.api.rule.IRuleContext;
import com.yindangu.v3.business.plugin.business.api.rule.IRuleOutputVo;
import com.yindangu.v3.platform.plugin.util.VdsUtils;
/**
 * 按id删除
 * @author jiqj
 *
 */
public class DeleteRecordById  implements IRule {
	public static final String D_PARAM_ID="id";
	public static final String D_CODE="deleteById";
	public static final String D_OutUpdateCount="updateCount",D_TableName="tableName";
	@Override
	public IRuleOutputVo evaluate(IRuleContext context) { 
		String id = (String)context.getInput(D_PARAM_ID);
		String tableName =(String)context.getInput(D_TableName);
		int count = deleteLogic(tableName, id);
		return context.newOutputVo().put(D_OutUpdateCount,count);
	}
	/**
	 * 从targetName中删除deleteList中的数据
	 * 
	 * @param targetName
	 * @param deleteList
	 */
	private int deleteLogic(String targetName,  String id ) {
		if (VdsUtils.string.isEmpty(id)) {
			return 0;
		}
		IDAS das = VDS.getIntance().getDas();
		List<String> idParams = Collections.singletonList(id);
		Map params = Collections.singletonMap("ids", idParams);
		das.executeUpdate("delete from " + targetName + " where id in (:ids)", params);
		return 1;
	}
}
