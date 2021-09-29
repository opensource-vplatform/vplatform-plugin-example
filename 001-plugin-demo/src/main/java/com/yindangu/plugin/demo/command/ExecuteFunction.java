package com.yindangu.plugin.demo.command;

import com.yindangu.plugin.demo.MyRegisterPlug;
import com.yindangu.plugin.demo.function.NumberUpperFunc;
import com.yindangu.v3.business.VDS;

class ExecuteFunction {
	/**模拟函数运行*/
	protected String execute(String exp) {
		if(exp.length() ==0) { //本构件的例子（入参要根据实际配置传入）
			exp =MyRegisterPlug.D_GroupId + "." + MyRegisterPlug.D_Component + "." 
					+ NumberUpperFunc.D_Code +"(54321)";
		}
		
		String exp2 = exp.replace('.', '_');
		Object o = VDS.getIntance().getFormulaEngine().eval(exp2);
		return(o == null ?"结果空":o.toString());
	}
}
