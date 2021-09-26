package com.yindangu.plugin.demoserver.impl;

import com.yindangu.plugin.demoserver.api.IMathService;

/**
 * 数学函数提供商
 * @author jiqj
 *
 */
public class MathServiceImpl implements IMathService{
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
