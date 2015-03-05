package com.zhaodj.foo.script;

import java.io.File;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.core.io.ClassPathResource;

import groovy.lang.GroovyClassLoader;

import groovy.lang.GroovyObject;

public class GroovyTestMain {
	// private static File file = new File("D:\\test.groovy");
	static {
		// ff = new groovyTest();
		// Thread tttt = new Thread(ff);
		// tttt.start();
	}

	public static void main(String[] args) throws Exception {
		// groovyTest ff = new groovyTest();
		// Thread tttt = new Thread(ff);
		// tttt.start();
		ClassLoader parent = ClassLoader.getSystemClassLoader();

		GroovyClassLoader loader = new GroovyClassLoader(parent);

		long t = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			Class gclass = loader.parseClass(new File("src/main/resources/test.groovy"));
			GroovyObject groovyObject = (GroovyObject) gclass.newInstance();
			Object obj = groovyObject.invokeMethod("add",
					new Object[] { new Integer(100000) });
			// System.out.println(t1-t);
			// System.out.println(obj);

		}
		long t1 = System.currentTimeMillis();
		System.out.println(t1 - t);

		// long t4 = System.currentTimeMillis();
		// Class gclass2 = loader.parseClass(new File("D:\\test.groovy"));
		// long t5 = System.currentTimeMillis();
		// System.out.println(t5-t4);
		// GroovyObject groovyObject2 = (GroovyObject) gclass2.newInstance();
		// Object obj2 = groovyObject2.invokeMethod("add", new Object[] {new
		// Integer(2)});
		//
		//
		// System.out.println(obj2);

		// long t8 = System.currentTimeMillis();
		// Class gclass3 = loader.parseClass(new File("D:\\test.groovy"));
		// long t9 = System.currentTimeMillis();
		// System.out.println(t9-t8);
		// GroovyObject groovyObject3 = (GroovyObject) gclass3.newInstance();
		// Object obj3 = groovyObject3.invokeMethod("add", new Object[] {new
		// Integer(2), new Integer(1)});
		//
		//
		// System.out.println(obj3);

	}

}
