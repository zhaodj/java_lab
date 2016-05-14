package com.zhaodj.foo.date;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created by zhaodaojun on 16/5/13.
 */
public class Java8 {

    public static void testInstant(){
        Date now = new Date();
        System.out.println(ZoneId.systemDefault());
        System.out.println(DateTimeFormatter.ISO_INSTANT.format(now.toInstant()));
        Instant ni = now.toInstant();
        System.out.println(DateTimeFormatter.ISO_INSTANT.format(ni.atZone(ZoneId.systemDefault())));
        System.out.println(ni.toString());
    }

    public static void main(String[] args){
        testInstant();
    }

}
