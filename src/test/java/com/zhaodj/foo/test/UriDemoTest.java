package com.zhaodj.foo.test;

import junit.framework.Assert;

import org.junit.Test;

import com.zhaodj.foo.UriDemo;

public class UriDemoTest {
	
	@Test
	public void validateTest(){
		UriDemo demo = new UriDemo("http://app.56.com");
		Assert.assertTrue(demo.validate("http://app.56.com"));
		Assert.assertTrue(demo.validate("http://app.56.com/"));
		Assert.assertTrue(demo.validate("http://app.56.com/aaaa"));
		Assert.assertFalse(demo.validate("http://app.56.com@sinaapp.com"));
		Assert.assertFalse(demo.validate("http://app.56.com.sinaapp.com"));
		demo = new UriDemo("http://app.56.com/");
		Assert.assertTrue(demo.validate("http://app.56.com/"));
		Assert.assertTrue(demo.validate("http://app.56.com/aaaa"));
		Assert.assertTrue(demo.validate("http://app.56.com//aaaa"));
		Assert.assertFalse(demo.validate("http://app.56.com@sinaapp.com"));
		Assert.assertFalse(demo.validate("http://app.56.com.sinaapp.com"));
		demo = new UriDemo("http://app.56.com/aaa");
		Assert.assertTrue(demo.validate("http://app.56.com/aaa"));
		Assert.assertTrue(demo.validate("http://app.56.com/aaa/bbb"));
		Assert.assertTrue(demo.validate("http://app.56.com/aaa@sinaapp.com"));
		Assert.assertTrue(demo.validate("http://app.56.com/aaa.sinaapp.com"));
	}

}	
