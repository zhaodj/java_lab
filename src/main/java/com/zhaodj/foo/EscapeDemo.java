package com.zhaodj.foo;

import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EscapeDemo {
    private static Logger log=LoggerFactory.getLogger(EscapeDemo.class);
    public static void main(String[] args){
        System.out.println(StringEscapeUtils.escapeHtml4("http%3A%2F%2Ft.163.com%2F123231231f529\"><script>alert(1)</script>87492ea3ad8"));
        log.info("info");
        log.debug("debug");
    }

}
