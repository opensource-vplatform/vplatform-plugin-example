package com.yindangu.plugin.demoserver.api;

import com.yindangu.v3.business.plugin.business.api.service.IOutService;

/**
 * 函数运算服务
 * @author jiqj
 */
public interface IMathService extends IOutService{
	/**取决定值*/
	public int abs(int x);
	/**取最大值*/
	public int max(int x,int y);
}
