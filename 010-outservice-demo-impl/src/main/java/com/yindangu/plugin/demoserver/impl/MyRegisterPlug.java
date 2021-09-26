package com.yindangu.plugin.demoserver.impl;

import java.util.Arrays;
import java.util.List;

import com.yindangu.v3.plugin.vds.reg.api.IRegisterPlugin;
import com.yindangu.v3.plugin.vds.reg.api.builder.IOutServiceProfileBuilder;
import com.yindangu.v3.plugin.vds.reg.api.model.IComponentProfileVo;
import com.yindangu.v3.plugin.vds.reg.api.model.IOutServiceProfileVo;
import com.yindangu.v3.plugin.vds.reg.api.model.IPluginProfileVo;
import com.yindangu.v3.plugin.vds.reg.common.RegVds;

/**
 * 构件注册器(注册入口)
 * 
 * @author jiqj
 *
 */
public class MyRegisterPlug implements IRegisterPlugin {
	@Override
	public IComponentProfileVo getComponentProfile() {
		return RegVds.getPlugin().getComponentProfile().setGroupId("com.yindangu.plugin").setCode("myservice").build();
	}

	@Override
	public List<IPluginProfileVo> getPluginProfile() {
		IPluginProfileVo oss = getOutService();
		return Arrays.asList(oss);
	}
 
	/**
	 * 提供外部调用的服务
	 * @return
	 */
	private IPluginProfileVo getOutService() {
		IOutServiceProfileBuilder fss = RegVds.getPlugin().getOutServicePlugin();
		IOutServiceProfileVo vo = fss.setName("我的数学函数服务")
				.setCode(MathServiceImpl.MATH_SERVICE_Code)
				.setDesc("我的数学函数服务-描述")
				.setAuthor("徐刚")
				.setEntry(MathServiceImpl.class)
				.build();
		return vo;
	}
}
