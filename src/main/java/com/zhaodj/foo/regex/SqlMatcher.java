package com.zhaodj.foo.regex;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhaodaojun on 2017/3/3.
 */
public class SqlMatcher {

    private static final String DELETE_MATCH_REGEX = "([\\s\\S]*)(((?i)DELETE)([\\s\\S]*)((?i)FROM)([\\s\\S]*)tablename)([\\s\\S]*)";
    private static final String DELETE_REPLACE_REGEX = "((?i)delete)([\\s\\S]*)((?i)from)([\\s\\S]*)tablename";

    public static void main(String[] args){
        String sql = "delete from tablename where trade_date = 20170101";
        Pattern pattern = Pattern.compile(DELETE_MATCH_REGEX);
        Matcher matcher = pattern.matcher(sql);
        while(matcher.find()){
            int groupCount = matcher.groupCount();
            for(int i = 0; i < groupCount; i++){
                System.out.println(i + " : " + matcher.group(i));
                System.out.println(i + " : " + "start=" + matcher.start(i) + " end=" + matcher.end(i));
            }
            System.out.println("start=" + matcher.start() + " end=" + matcher.end());
        }
        String[] splits = pattern.split(sql);
        System.out.println(Arrays.toString(splits));
        matcher = pattern.matcher(sql);
        if(matcher.matches()) {
            System.out.println(sql.substring(0, matcher.start(2)));
            System.out.println(sql.substring(matcher.end(2)));
        }
    }

}
