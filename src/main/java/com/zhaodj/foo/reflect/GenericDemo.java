package com.zhaodj.foo.reflect;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by djzhao on 15-8-21.
 */
public class GenericDemo {

    public static interface Aoo<K, V>{
        V test(K key);
    }

    public static class Foo<K,V> implements Aoo<K, V>{

        @Override
        public V test(K key){
            Type type = this.getClass().getGenericSuperclass();
            if(type instanceof ParameterizedType){
                ParameterizedType parameterizedType = (ParameterizedType)type;
                System.out.println(parameterizedType.getActualTypeArguments()[0]);
            }

            return null;
        }

    }

    public static void main(String[] args){
        Foo<String, Object> foo = new Foo<>();
        foo.test("aa");
        System.out.println(foo.getClass().getGenericSuperclass());
    }

}
