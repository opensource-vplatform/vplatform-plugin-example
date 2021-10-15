package com.yindangu.plugin.demo.command;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
import com.yindangu.plugin.demo.MyRegisterPlug;
import com.yindangu.plugin.demo.mockapi.IExecuteRuleCommandParams;
import com.yindangu.plugin.demo.mockapi.MockFactory;
import com.yindangu.plugin.demo.rule.NumberUpperRolue;
import com.yindangu.v3.platform.plugin.util.VdsUtils;

class ExecuteRule { 
	private static final Logger log = LoggerFactory.getLogger(ExecuteRule.class);
	protected static final Charset UTF8 =  Charset.forName("utf-8");
	/**模拟规则运行*/
	protected String execute (String groupId,String compoentCode,String pluginCode) {
		//本构件的例子（入参要根据实际配置传入）
		final String gid = (groupId.length() ==0 ? MyRegisterPlug.D_GroupId : groupId);
		final String compoent = (compoentCode.length()==0 ? MyRegisterPlug.D_Component  :compoentCode);
		final String code = (pluginCode.length()==0 ?  NumberUpperRolue.D_Code :pluginCode);

		final Map<String,Object> params = new HashMap<String, Object>();
		params.put(NumberUpperRolue.INPUT_NAME, "姓名-小明");
		params.put(NumberUpperRolue.INPUT_AGE, "18");
		params.put(NumberUpperRolue.INPUT_SEX, "female");
 
		String paramsJson = getParamsMetas();
		Map<String, Object> paramsMeta= VdsUtils.json.fromJson(paramsJson);
		
		try {
			log.info("需要引入 plugin-demo-mock 包:" +  IExecuteRuleCommandParams.class.getName());
		}
		catch(NoClassDefFoundError e) {
			//log.error("IExecuteRuleCommandParams接口不存在，请引入 com.yindangu.plugin-plugin-demo-mock-xx.jar 包");
			throw new RuntimeException("IExecuteRuleCommandParams接口不存在，请引入 com.yindangu.plugin-plugin-demo-mock-xx.jar 包",e);
		}
		IExecuteRuleCommandParams pars = new IExecuteRuleCommandParams() {
			@Override
			public Map<String, Object> getRuleConfig() { 
				return Collections.emptyMap();
			}
			@Override
			public Map<String, Object> getInputParams() {
				return params;
			}
			@Override
			public String getGroupId() { 
				return gid;
			}

			@Override
			public String getPluginCode() { 
				return code;
			}
			@Override
			public String getCompoentCode() { 
				return compoent;
			}
			@Override
			public Map<String, Object> getRuleInputParams() { 
				return paramsMeta;
			}
		}; 
		Object o = MockFactory.getFactory().executeRuleCommand(pars);
		return(o == null ?"结果空":o.toString());
	} 
	/**开发系统参数*/
	private String getParamsMetas() {
		String json = "{"
				+ "    \"inParams\": ["
				+ "        {"
				+ "            \"paramCode\": \"rowIdx\","
				+ "            \"paramSourceType\": \"expression\","
				+ "            \"paramSourceValue\": \"GetLength(BR_VAR_PARENT.m_varx)+100\""
				+ "        },"
				+ "        {"
				+ "            \"paramCode\": \"column\","
				+ "            \"paramSourceType\": \"expression\","
				+ "            \"paramSourceValue\": \"BR_VAR_PARENT.m_varx\""
				+ "        },"
				+ "        {"
				+ "            \"paramCode\": \"today\","
				+ "            \"paramSourceType\": \"expression\","
				+ "            \"paramSourceValue\": \"DateTimeNow()\""
				+ "        },"
				+ "        {"
				+ "            \"paramCode\": \"myentity\","
				+ "            \"paramFieldMapping\": ["
				+ "                {"
				+ "                    \"fieldValue\": \"name\","
				+ "                    \"fieldValueType\": \"field\","
				+ "                    \"paramEntityField\": \"myname\""
				+ "                },"
				+ "                {"
				+ "                    \"fieldValue\": \"age\","
				+ "                    \"fieldValueType\": \"field\","
				+ "                    \"paramEntityField\": \"myage\""
				+ "                },"
				+ "                {"
				+ "                    \"fieldValue\": \"GetLength(BR_VAR_PARENT.m_varx)\","
				+ "                    \"fieldValueType\": \"expression\","
				+ "                    \"paramEntityField\": \"code\""
				+ "                }"
				+ "            ],"
				+ "            \"paramSourceType\": \"ruleSetVariant\","
				+ "            \"paramSourceValue\": \"m_entity\""
				+ "        }"
				+ "    ],"
				+ "    \"ruleOutputParams\": ["
				+ "        {"
				+ "            \"dest\": \"outEntity\","
				+ "            \"destFieldMapping\": ["
				+ "                {"
				+ "                    \"destField\": \"name\","
				+ "                    \"destType\": \"\","
				+ "                    \"srcValue\": \"myname\","
				+ "                    \"srcValueType\": \"field\""
				+ "                },"
				+ "                {"
				+ "                    \"destField\": \"agex\","
				+ "                    \"destType\": \"\","
				+ "                    \"srcValue\": \"myage\","
				+ "                    \"srcValueType\": \"field\""
				+ "                },"
				+ "                {"
				+ "                    \"destField\": \"age\","
				+ "                    \"destType\": \"\","
				+ "                    \"srcValue\": \"outcode\","
				+ "                    \"srcValueType\": \"field\""
				+ "                }"
				+ "            ],"
				+ "            \"destType\": \"ruleSetVariant\","
				+ "            \"srcCode\": \"dsdsads\","
				+ "            \"srcType\": \"returnValue\""
				+ "        },"
				+ "        {"
				+ "            \"dest\": \"m_varx\","
				+ "            \"destType\": \"ruleSetVariant\","
				+ "            \"srcCode\": \"number\","
				+ "            \"srcType\": \"expression\""
				+ "        },"
				+ "        {"
				+ "            \"dest\": \"oupars\","
				+ "            \"destType\": \"ruleSetOutput\","
				+ "            \"srcCode\": \"chinese\","
				+ "            \"srcType\": \"expression\""
				+ "        }"
				+ "    ]"
				+ "}";
		return json;
		
	}
	
}
