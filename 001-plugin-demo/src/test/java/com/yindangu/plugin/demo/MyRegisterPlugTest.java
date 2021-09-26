package com.yindangu.plugin.demo;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yindangu.v3.plugin.vds.reg.api.IRegisterPlugin;
import com.yindangu.v3.plugin.vds.reg.api.model.IComponentProfileVo;
import com.yindangu.v3.plugin.vds.reg.api.model.IPluginProfileVo;

public class MyRegisterPlugTest {
	private static final Logger log = LoggerFactory.getLogger(MyRegisterPlugTest.class);
	@Test
	public void testComponentProfile() {
		IRegisterPlugin p = new MyRegisterPlug();
		IComponentProfileVo vo = p.getComponentProfile();
		String gn = vo.getGroupId() + "-" + vo.getCode();
		//String json = RegVds.getUtils().json.toJson(vo);
		//log.info(json);
		assertEquals("构件信息不正确", "com.yindangu.plugin-mydemo",gn);
		//assertEquals("构件信息不正确","{\"groupId\":\"com.yindangu.plugin\",\"code\":\"mydemo\"}",json);
	}
	
	private static final String PluginJson="[{\"name\":\"数字转汉字-name\",\"entry\":\"com.yindangu.plugin.demo.function.NumberUpperFunc\",\"code\":\"numberConvertFunc\",\"desc\":\"数字转汉字\",\"output\":{\"type\":\"Char\",\"desc\":\"汉字大写\"},\"inputs\":[{\"default\":null,\"type\":\"Integer\",\"desc\":\"数字\",\"required\":true}]},{\"name\":\"把指定数字值转汉字\",\"entry\":\"com.yindangu.plugin.demo.rule.NumberUpperRolue\",\"code\":\"numberConvertRule\",\"desc\":\"把指定数字值转汉字desc\",\"transactionType\":false,\"output\":[{\"name\":\"年龄\",\"fields\":null,\"type\":\"Integer\",\"code\":\"age\",\"desc\":null},{\"name\":\"性名\",\"fields\":null,\"type\":\"Char\",\"code\":\"name\",\"desc\":null},{\"name\":\"退休\",\"fields\":null,\"type\":\"Boolean\",\"code\":\"retire\",\"desc\":null},{\"name\":\"汉字大写\",\"fields\":null,\"type\":\"Char\",\"code\":\"chinese\",\"desc\":null}],\"inputs\":[{\"name\":\"性名\",\"default\":null,\"fields\":null,\"type\":\"Char\",\"code\":\"name\",\"desc\":null,\"editor\":{\"type\":\"Expression\",\"options\":null},\"required\":false},{\"name\":\"年龄\",\"default\":null,\"fields\":null,\"type\":\"Integer\",\"code\":\"age\",\"desc\":null,\"editor\":{\"type\":\"Expression\",\"options\":null},\"required\":false},{\"name\":\"性别\",\"default\":null,\"fields\":null,\"type\":\"Char\",\"code\":\"sex\",\"desc\":null,\"editor\":{\"type\":\"Select\",\"options\":[{\"value\":\"male\",\"label\":\"男\"},{\"value\":\"female\",\"label\":\"女\"}]},\"required\":false}]},{\"name\":\"command\",\"entry\":\"com.yindangu.plugin.demo.command.MyHttpCommand\",\"code\":\"mycmd\",\"desc\":null,\"nextCommands\":null},{\"name\":\"把指定列 数字转汉字-entity\",\"entry\":\"com.yindangu.plugin.demo.rule.NumberUpperRolueEntity\",\"code\":\"numberConvertEntity\",\"desc\":\"把指定列 数字转汉字-实体\",\"transactionType\":false,\"output\":[{\"name\":\"用户列表实体\",\"fields\":[{\"name\":\"年龄\",\"type\":\"Integer\",\"code\":\"age\",\"desc\":null},{\"name\":\"性名\",\"type\":\"Char\",\"code\":\"name\",\"desc\":null},{\"name\":\"汉字大写\",\"type\":\"Char\",\"code\":\"chinese\",\"desc\":null}],\"type\":\"Entity\",\"code\":\"userList2\",\"desc\":null},{\"name\":\"转换个数\",\"fields\":null,\"type\":\"Integer\",\"code\":\"count\",\"desc\":null}],\"inputs\":[{\"name\":\"用户列表\",\"default\":null,\"fields\":[{\"name\":\"年龄\",\"type\":\"Integer\",\"code\":\"age\",\"desc\":null},{\"name\":\"性名\",\"type\":\"Char\",\"code\":\"name\",\"desc\":null}],\"type\":\"Entity\",\"code\":\"userList\",\"desc\":null,\"editor\":{\"type\":\"EntityField\",\"options\":null},\"required\":false},{\"name\":\"转换列名\",\"default\":null,\"fields\":null,\"type\":\"Char\",\"code\":\"age\",\"desc\":null,\"editor\":{\"type\":\"Expression\",\"options\":null},\"required\":false}]}]";
	@Test
	public void testPluginProfile() {
		IRegisterPlugin p = new MyRegisterPlug();
		List<IPluginProfileVo> pvs = p.getPluginProfile();
		//String json = VDS.getUtils().json.toJson(pvs);
		//log.info(json);
		//assertEquals("插件信息不正确", PluginJson,json);
	}
}
