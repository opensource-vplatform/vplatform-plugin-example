package com.yindangu.plugin.demo;

public class BusinessUtil {
	private static char[] chinas= {'零','壹','贰','叁','肆','伍','陆','柒','捌','玖','拾'};
	/**转汉字*/
	public static String toChinese(int n) {
		
		char[] numbs = String.valueOf(n).toCharArray();
		char[] rs = new char[numbs.length];
		
		for(int i =0 ;i < numbs.length;i++) {
			int idx = numbs[i] - '0';
			rs[i] = chinas[idx];
		}
		return String.valueOf(rs);
	}
}
