package com.yindangu.plugin.demo.command;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yindangu.plugin.demo.service.consume.MathConsume;
import com.yindangu.v3.business.plugin.business.api.httpcommand.FormatType;
import com.yindangu.v3.business.plugin.business.api.httpcommand.IHttpCommand;
import com.yindangu.v3.business.plugin.business.api.httpcommand.IHttpContext;
import com.yindangu.v3.business.plugin.business.api.httpcommand.IHttpResultVo;

/**
 * command的例子
 * @author jiqj
 *
 */
public class ExampleMyHttpCommand implements IHttpCommand{
	//private static final Logger log = (Logger)java.util.logging.Logger.getLogger(ExampleMyHttpCommand.class);

	private static final Logger log = LoggerFactory.getLogger(ExampleMyHttpCommand.class);
	public ExampleMyHttpCommand() {
		
	}
	@Override
	public IHttpResultVo execute(IHttpContext context) {
		IHttpResultVo vo = context.newResultVo();
		HttpServletRequest req =context.getRequest();
		String math = req.getParameter("math");
		
		if("abs".equalsIgnoreCase(math)) {
			int x = toInt(req.getParameter("x"),-99);
			MathConsume m = new MathConsume();
			int rs = m.abs(x);
			vo.setValueType(FormatType.Json)
			.setValue("{\"name\":\"math参数:"+ math +"\",\"result\":" + rs + "}")
		;
		}
		else if("max".equalsIgnoreCase(math)) {
			int x = toInt(req.getParameter("x"),-99);
			int y = toInt(req.getParameter("y"),-98);
			MathConsume m = new MathConsume();
			int rs = m.max(x,y);
			vo.setValueType(FormatType.Json)
				.setValue("{\"name\":\"math参数:"+ math +"\",\"result\":" + rs + "}")
			;
		}
		else {
			vo.setValueType(FormatType.Json)
			.setValue("{\"name\":\"math参数不能识别:"+ math +"\",\"entry\":\"com.yindangu.plugin.demo.function.NumberUpperFunc\",\"code\":\"numberConvertFunc\",\"desc\":\"数字转汉字\",\"output\":{\"type\":\"Char\",\"desc\":\"汉字大写\"},\"inputs\":[{\"default\":null,\"type\":\"Integer\",\"desc\":\"数字\",\"required\":true}]}")
			//.newDownload()
			;
		}
		return vo;
	}
	private int toInt(String n,int def) {
		if(n == null || n.length() ==0) {
			return def;
		}
		else {
			return Integer.parseInt(n);
		}
	}
}
