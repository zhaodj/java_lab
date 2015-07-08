package com.zhaodj.foo.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by djzhao on 15-7-8.
 */
public class SortDemo {

    private String code;
    private int value;

    public SortDemo(String code, int value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "SortDemo{" +
                "code='" + code + '\'' +
                ", value=" + value +
                '}';
    }

    public static void main(String[] args){
        List<SortDemo> list = new ArrayList<SortDemo>();
        list.add(new SortDemo("a", 0));
        list.add(new SortDemo("b", 0));
        list.add(new SortDemo("c", 0));
        list.add(new SortDemo("d", 0));
        list.add(new SortDemo("e", 0));
        Collections.sort(list, new Comparator<SortDemo>() {
            @Override
            public int compare(SortDemo o1, SortDemo o2) {
                if (o1.getValue() > o2.getValue()) {
                    return 1;
                } else if (o1.getValue() < o2.getValue()) {
                    return -1;
                }
                return 0;
            }
        });
        System.out.println(list);
    }

}
