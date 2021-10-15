package com.yindangu.plugin.demo.command;
 
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yindangu.plugin.demo.mis.MisExample;
import com.yindangu.v3.business.plugin.business.api.httpcommand.FormatType;
import com.yindangu.v3.business.plugin.business.api.httpcommand.IHttpCommand;
import com.yindangu.v3.business.plugin.business.api.httpcommand.IHttpContext;
import com.yindangu.v3.business.plugin.business.api.httpcommand.IHttpResultVo;
import com.yindangu.v3.platform.plugin.util.VdsUtils;

/**
 * 调试例子入口(command一般是作为响应请求)
 * @author jiqj
 *
 */
public class PluginIndexCommand implements IHttpCommand{
	private static final Logger log = LoggerFactory.getLogger(PluginIndexCommand.class);
	@Override
	public IHttpResultVo execute(IHttpContext context) {
		HttpServletRequest req =context.getRequest();
		String execute = getParameter(req,"execute");
		String message = "业务处理成功";
		Object rs ;
		try {
			if(execute.length()==0 ) {
				Timestamp t = new Timestamp(System.currentTimeMillis());
				rs = "默认请求返回:" + t.toString();
			} 
			else if("function".equalsIgnoreCase(execute)) { //执行一个函数
				String exp = getParameter(req,"exp"); //表达式 
				rs = (new ExecuteFunction()).execute(exp);
			}
			else if("rule".equalsIgnoreCase(execute)) {  
				String groupid = getParameter(req,"groupid");
				String compoent = getParameter(req,"compoent");
				String pluginCode = getParameter(req,"code");
				rs = (new ExecuteRule()).execute(groupid,compoent,pluginCode);
			}
			else if("importXMLData".equalsIgnoreCase(execute)) {//导入数据   
				String err = (new MisExample()).importXMLData();
				rs = (err==null? "成功": err);
			}
			else {
				message ="不能识别的操作(execute):" + execute;
				rs = null;
			}
		}
		catch(Throwable e) {
			message = execute + ")出错:" + e.getMessage();
			log.error(message,e);
			rs = null;
		}
		//////////////////业务处理结果///////////////////
		Map<String,Object> success = new HashMap<String,Object>();
		success.put("message", message);
		success.put("business", rs);
		success.put("state", Boolean.valueOf(rs != null));
		
		IHttpResultVo vo = context.newResultVo();
		vo.setValue(VdsUtils.json.toJson(success));
		vo.setValueType(FormatType.Json);
		return vo;
	}
	private String getParameter(HttpServletRequest req,String key) {
		String opt = req.getParameter(key);
		return (opt == null ? "": opt.trim());
	}	
}