package com.yindangu.plugin.demo.service.consume;
 
import com.yindangu.plugin.demoserver.api.IMathService;
import com.yindangu.v3.business.VDS;
/**
 * 跨构件、或者远程调用示例
 */
public class MathConsume {
	private static final String ServiceCode ="com.yindangu.plugin.myservice.";
	public int abs(int x) {
		IMathService match = VDS.getIntance().getService(IMathService.class, ServiceCode + "match"); 
		int rs = match.abs(x);
		return rs;
	}
	public int max(int x,int y) {
		IMathService match = VDS.getIntance().getService(IMathService.class,ServiceCode + "match"); 
		int rs = match.max(x,y);
		return rs;
	}
}
