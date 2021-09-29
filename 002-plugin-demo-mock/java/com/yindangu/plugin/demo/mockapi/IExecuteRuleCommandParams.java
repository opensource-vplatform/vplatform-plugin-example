package com.yindangu.plugin.demo.mockapi;

import java.util.Map;

public interface IExecuteRuleCommandParams {
	public class DefaultParams implements IExecuteRuleCommandParams{
		private Map<String, Object> ruleConfig ;
		private Map<String, Object> inputParams ;
		private Map<String, Object> paramsMeta ;
		private String groupId,compoentCode, pluginCode;
				
		@Override
		public Map<String, Object> getRuleConfig() {
			return ruleConfig;
		}
		public void setRuleConfig(Map<String, Object> ruleConfig) {
			this.ruleConfig = ruleConfig;
		}
		@Override
		public Map<String, Object> getInputParams() {
			return inputParams;
		}
		public void setInputParams(Map<String, Object> inputParams) {
			this.inputParams = inputParams;
		}
		@Override
		public String getGroupId() {
			return groupId;
		}
		public void setGroupId(String groupId) {
			this.groupId = groupId;
		}
		@Override
		public String getCompoentCode() {
			return compoentCode;
		}
		public void setCompoentCode(String compoentCode) {
			this.compoentCode = compoentCode;
		}
		@Override
		public String getPluginCode() {
			return pluginCode;
		}
		public void setPluginCode(String pluginCode) {
			this.pluginCode = pluginCode;
		}
		@Override
		public Map<String, Object> getRuleInputParams() { 
			return paramsMeta;
		}
		public void setRuleInputParams(Map<String, Object> paramsMeta) {
			this.paramsMeta = paramsMeta;
		}
	}

	/**(Map<String, Object>) context.get(RuleChainConstants.PARAM_RULE_CONFIG)*/
	public Map<String, Object> getRuleConfig() ; 
	/**(Map<String, Object>) context.get(RuleChainConstants.PARAM_INPUT_PARAMS);*/
	public Map<String, Object> getInputParams() ; 
	public String getGroupId() ;
	public String getPluginCode() ;
	public String getCompoentCode();
	/**每个入参的定义信息*/
	public Map<String,Object> getRuleInputParams();
}
