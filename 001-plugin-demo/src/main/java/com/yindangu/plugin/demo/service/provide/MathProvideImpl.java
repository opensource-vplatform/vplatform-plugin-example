package com.yindangu.plugin.demo.service.provide;

import com.yindangu.plugin.demoserver.api.IMathService;

/**
 * 数学函数提供商 （独立的包 : plugin-demo-serice-api/plugin-demo-serice-impl）
 * @author jiqj
 *
*/
public class MathProvideImpl implements IMathService{
	public static  final String MATH_SERVICE_Code="match";
	@Override
	public int abs(int x) { 
		return (x >= 0 ?  x : -x);
	}

	@Override
	public int max(int x, int y) {
		return (x>y ? x : y);
	}
}