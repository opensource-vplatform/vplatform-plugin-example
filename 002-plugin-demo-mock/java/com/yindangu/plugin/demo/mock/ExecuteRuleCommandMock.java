package com.yindangu.plugin.demo.mock;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.felix.ipojo.IPOJOServiceFactory;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.toone.itop.executechain.apiserver.Chain;
import com.toone.itop.executechain.apiserver.Context;
import com.toone.itop.executechain.apiserver.factory.ChainOperationFactory;
import com.toone.itop.executechain.spiserver.Command;
import com.toone.vcore.beans.BeanFactory;
import com.toone.vcore.beans.EmptyComponentInstance;
import com.yindangu.plugin.demo.mockapi.IExecuteRuleCommandParams;
import com.yindangu.plugin.demo.mockapi.MockFactory;
import com.yindangu.v3.platform.plugin.util.VdsUtils;
/**发起ExecuteRuleCommand<br/>
 *（规则就是用这个command执行的：execute=ExecuteRule）
 */ 
public class ExecuteRuleCommandMock  {
	private static final Logger log = LoggerFactory.getLogger(ExecuteRuleCommandMock.class);

	private static final String PARAM_RULE_CONFIG="ruleConfig",PARAM_INPUT_PARAMS="inputParams";
	private static final String Param_MetaCode = "ruleSetCode";
	private static final String Param_InputParams = "params";
	private static final String Param_ComponentCode = "componentCode";
	
	/**(Map<String, Object>) context.get(RuleChainConstants.PARAM_RULE_CONFIG)*/
	private Map<String, Object> ruleConfig ;
	/**(Map<String, Object>) context.get(RuleChainConstants.PARAM_INPUT_PARAMS);*/
	private Map<String, Object> inputParams ;
	

	public ExecuteRuleCommandMock() {
		this(null,null);
	}
	public ExecuteRuleCommandMock(HashMap<String, Object> ruleConfig,HashMap<String, Object> inputParams) {
		this.ruleConfig =(ruleConfig == null ? new HashMap<String, Object>() : ruleConfig);
		this.inputParams =(inputParams == null ? new HashMap<String, Object>() :inputParams);
	}
 
	private static boolean isEmpty(String s) {
		return (s == null || s.trim().length()==0);
	}
	private String getFunctionCode(IExecuteRuleCommandParams pars) {
		StringBuilder sb = new StringBuilder();
		if(!isEmpty(pars.getGroupId())) {
			sb.append(pars.getGroupId()).append('.');
		}
		if(!isEmpty(pars.getCompoentCode())) {
			sb.append(pars.getCompoentCode()).append('.');
		}
		if(isEmpty(pars.getPluginCode())) {
			throw new RuntimeException("插件code不能为空");
		}
		sb.append(pars.getPluginCode());
		String rs = sb.toString().replace('.','_').replace('-','_');
		return rs;
	}
	public boolean execute(IExecuteRuleCommandParams pars) {
		try {			
			String ruleName = getFunctionCode(pars);
			ruleConfig.put("ruleInstId", "ruleInstId-DEBUG");
			ruleConfig.put("ruleName", ruleName);
			
			Map<String, Object> paramsMeta = pars.getRuleInputParams();
			ruleConfig.put("ruleInputParams",paramsMeta == null ? Collections.emptyMap():pars.getRuleInputParams() );

			Chain chain = ChainOperationFactory.createChain("", "ExecuteRule");
			Context ctx = chain.generateContext();
			ctx.put(PARAM_RULE_CONFIG, ruleConfig);
			ctx.put(PARAM_INPUT_PARAMS, inputParams);
			
			//BeanFactory.getBean(Command.class);
			MockFactory fac =MockFactory.getFactory();
			Command cmd = fac.getBundleContext("com.toone.itop.command.rule.ExecuteRuleCommand", Command.class.getName());
			cmd.execute(ctx);
			return true;
		}
		catch(RuntimeException e) {
			throw e;
		} catch (Exception e) { 
			throw new RuntimeException(e);
		}
	}
	
	
	/**开发系统的配置*/
	public ExecuteRuleCommandMock setRuleConfig(Map<String,Object> pars) {
		ruleConfig = pars;
		return this;
	}
	/**运行时的入参*/
	public ExecuteRuleCommandMock setInputParams(Map<String,Object> pars) {
		inputParams = pars;
		return this;
	}
	  
}
