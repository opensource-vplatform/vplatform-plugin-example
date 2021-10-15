package com.yindangu.plugin.demo.mis;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

import com.yindangu.v3.business.VDS;
import com.yindangu.v3.business.metadata.apiserver.IMis;

public class MisExample {
	/**
	 * 导入数据
	 * @return
	 */
	public String importXMLData() {
		InputStream is1 =null,is2 = null;
		try {
			is1 = MisExample.class.getResourceAsStream("my.mytable1.xml");
			is2 = MisExample.class.getResourceAsStream("my.mytable2.xml");
			//对应 执行系统后台"系统高级配置/数据管理/系统初始化数据导出XML"的备份的数据格式
			String xml1 = readStream(is1);
			String xml2 = readStream(is2);
			
			IMis mis = VDS.getIntance().getMis();
			mis.importBusinessXML(Arrays.asList(xml1,xml2));
			
			return null;
		}catch(IOException e) {
			return e.getMessage();
		}
		finally {
			close(is1);
			close(is2);
		}
	}
	private String readStream(InputStream is)  throws IOException{
		InputStreamReader rd = new InputStreamReader(is, "utf-8");
		char[] bys = new char[1024*4];
		int len ;
		StringBuffer sb = new StringBuffer();
		
		while((len = rd.read(bys))!=-1) {
			sb.append(bys, 0, len);
		}
		return sb.toString();
	}
	private void close(InputStream is) {
		try {
			if(is!=null) {
				is.close();
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
}
