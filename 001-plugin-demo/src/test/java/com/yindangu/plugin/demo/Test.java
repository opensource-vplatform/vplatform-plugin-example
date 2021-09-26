package com.yindangu.plugin.demo;

public class Test {

	public static void main(String[]a) {
		System.out.println("====");
		boolean b = A.class.isAssignableFrom(B.class);
		System.out.println("===="+ b); 
	}
	
}

class A  {
	
}

class B extends A{
	
}