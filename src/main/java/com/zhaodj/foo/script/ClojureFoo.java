package com.zhaodj.foo.script;

import java.io.IOException;

import clojure.lang.RT;
import clojure.lang.Var;

public class ClojureFoo {
	
	public ClojureFoo(String scriptFile) throws IOException {
		reload(scriptFile);
	}
	
	public void reload(String scriptFile) throws IOException {
    	long start = System.currentTimeMillis();
        RT.loadResourceScript(scriptFile);
        System.out.println("load script cost: " + (System.currentTimeMillis() - start));
	}
	
	public void work() {
        Var add = RT.var("user", "add");
        Object result = add.invoke(100000);
		
	}
 
    public static void main(String[] args) throws Exception {
    	ClojureFoo test = new ClojureFoo("test.clj");
 
        // Get a reference to the foo function.
        Var foo = RT.var("user", "foo");
 
        // Call it!
        Object result = foo.invoke("Hi", "there");
        System.out.println(result);
        Var add = RT.var("user", "add");
        result = add.invoke(1);
        System.out.println(result);
        //System.out.println(System.currentTimeMillis() - start);
    }

}
