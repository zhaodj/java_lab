package com.zhaodj.foo.script;

import java.util.ArrayList;
import java.util.List;

import org.jruby.embed.EmbedEvalUnit;
import org.jruby.embed.LocalContextScope;
import org.jruby.embed.LocalVariableBehavior;
import org.jruby.embed.PathType;
import org.jruby.embed.ScriptingContainer;
import org.jruby.javasupport.JavaEmbedUtils;

public class JRubyFoo {
	
	private ScriptingContainer container;
	private EmbedEvalUnit unit;
	private Object receiver;
	
	private JRubyFoo(String scriptFile) {
        long start = System.currentTimeMillis();
		container = new ScriptingContainer();
		//container = new ScriptingContainer(LocalContextScope.SINGLETON, LocalVariableBehavior.TRANSIENT, false);
        System.out.println("init env cost : " + (System.currentTimeMillis()-start));
		this.reload(scriptFile);
    }
	
	public void work(int max) {
        long start = System.currentTimeMillis();
		//this.unit.run();
        container.callMethod(receiver, "add", max);
        System.out.println("run script cost : " + (System.currentTimeMillis()-start));
	}
	
	public void reload(String scriptFile) {
        long start = System.currentTimeMillis();
        //container.put("max", 1000000);
        unit = container.parse(PathType.CLASSPATH, scriptFile);
        receiver = JavaEmbedUtils.rubyToJava(unit.run());
        System.out.println("load script cost : " + (System.currentTimeMillis()-start));
	}
	
	public void workReload(String scriptFile, int num, int max, boolean load) {
        long start = System.currentTimeMillis();
        for(int i = 0;i<num;i++) {
        	if(load) {
        		unit = container.parse(PathType.CLASSPATH, scriptFile);
        		receiver = JavaEmbedUtils.rubyToJava(unit.run());
        	}
        	container.callMethod(receiver, "add", max);
        }
        System.out.println("workReload script cost : " + (System.currentTimeMillis()-start));
	}
	
	public List<EventResult> call(User user, Room room) {
        long start = System.currentTimeMillis();
		ArrayList<EventResult> result = container.callMethod(receiver, "setParents", new Object[] {user,room}, ArrayList.class);
		System.out.println(result);
        System.out.println("call script cost : " + (System.currentTimeMillis()-start));
		return result;
	}

    public static void main(String[] args) {
    	JRubyFoo foo = new JRubyFoo("test.rb");
    	int max = 100000;
    	int num = 1000;
    	foo.work(max);
    	//foo.reload("test1.rb");
    	//foo.work(max);
    	//doByJava(max);
    	//foo.workReload("test.rb", num, max, false);
    	workByJava(num, max);
    	User user = new User();
    	user.setName("ab");
    	user.setAge(20);
    	Room room = new Room();
    	room.setRoomId(10000);
    	room.setName("room");
    	foo.call(user, room);
    }
    
    private static void doByJava(int max) {
        long sum = 0;
    	for(int i = 0;i<max;i++) {
    		sum = sum + i;
    	}
    	//System.out.println(sum);
    }
    
    private static void workByJava(int num, int max) {
        long start = System.currentTimeMillis();
    	for(int i = 0;i<num;i++) {
    		doByJava(max);
    	}
        System.out.println("run by java cost : " + (System.currentTimeMillis()-start));
    }


}
