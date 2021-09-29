package com.yindangu.plugin.demo.mockapi;

import org.apache.felix.ipojo.IPOJOServiceFactory;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.toone.itop.executechain.spiserver.Command;
import com.toone.vcore.beans.EmptyComponentInstance; 
import com.yindangu.plugin.demo.mock.ExecuteRuleCommandMock;

public class MockFactory {
	private static final Logger log = LoggerFactory.getLogger(MockFactory.class);
	private static MockFactory ins;
	public static final MockFactory getFactory() {
		if(ins == null) {
			ins = new MockFactory(); 
		}
		return ins;
	}
	private MockFactory() {
		
	}
	/**默认 ExecuteRuleCommand 请求*/
	public Object executeRuleCommand(IExecuteRuleCommandParams pars) {
		ExecuteRuleCommandMock mock = new ExecuteRuleCommandMock();
		return mock.execute(pars);
	}

	private BundleContext bundleContext;
	private EmptyComponentInstance emptyPojo;
	/**
	 * 通过className取得OSGI的对象<br/>
	 * 参考 ExecuteRuleCommand.java使用父类注册（按ExecuteRuleCommand查找没有对象，按父类查找会返回多个对象的情况）
	 * @param className 类名（全路径），不能为空
	 * @param parentClassName 父类名，可以null（全路径）
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T getBundleContext(String className,String parentClassName) { 
		try {
			if(bundleContext == null) {
				bundleContext = FrameworkUtil.getBundle(Command.class).getBundleContext();
				emptyPojo = new EmptyComponentInstance();
			}
			if(parentClassName == null || parentClassName.length()==0) {
				parentClassName = className;
			}
			ServiceReference<?>[] references = bundleContext.getAllServiceReferences(parentClassName, null);
			
			/**
			 * ipojo instance 实例返回为proxy，需要再次调用factory.getService()方法
			 * 
			 * @param reference
			 * @return
			 */
			int size =(references == null ? 0:references.length );
			T bean = null;
			for(int i =0 ;i < size;i++) {
				Object fac = bundleContext.getService(references[i]);
				Object o = fac;
				if (fac instanceof IPOJOServiceFactory) {  
					o =  ((IPOJOServiceFactory) fac).getService(emptyPojo);
				}
				if(className.equalsIgnoreCase(o.getClass().getName())) {
					bean = (T)o;
					break;
				}
			}
			return bean; 
		}
		catch(RuntimeException e) {
			throw e;
		}
		catch (InvalidSyntaxException e) {
			throw new RuntimeException("通过className取得OSGI的对象(不会发生)",e);
		}
		catch (Throwable e) {
			throw new RuntimeException("通过className取得OSGI的对象",e);
		}
	}
}
