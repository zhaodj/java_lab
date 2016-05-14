package com.zhaodj.foo.jackson;

import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaodaojun on 16/5/13.
 */
public class PolymorphicType {
    @JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
    public static class Base{

    }

    public static class Combination extends Base{
        private String range = "all";
        private boolean match = true;
        private List<Base> conditions;

        public String getRange() {
            return range;
        }

        public void setRange(String range) {
            this.range = range;
        }

        public boolean isMatch() {
            return match;
        }

        public void setMatch(boolean match) {
            this.match = match;
        }

        public List<Base> getConditions() {
            return conditions;
        }

        public void setConditions(List<Base> conditions) {
            this.conditions = conditions;
        }

        public void addCondition(Base cond){
            if(this.conditions == null){
                this.conditions = new ArrayList<>();
            }
            this.conditions.add(cond);
        }
    }

    public static class Condition extends Base{
        private String field;
        private String compare = "=";
        private Object value;

        public Condition(){}

        public Condition(String field, String compare, Object value) {
            this.field = field;
            this.compare = compare;
            this.value = value;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getCompare() {
            return compare;
        }

        public void setCompare(String compare) {
            this.compare = compare;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }
    }

    public static void main(String[] args) throws IOException {
        Combination demo = new Combination();
        Condition cond = new Condition();
        cond.setField("category");
        cond.setValue(1);
        Combination subComb = new Combination();
        subComb.addCondition(new Condition("price", ">", 99.9));
        demo.addCondition(cond);
        demo.addCondition(subComb);
        ObjectMapper mapper =  new ObjectMapper();
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(demo);
        System.out.println(json);
        Combination parseDemo = mapper.readValue(json, Combination.class);
        System.out.println(parseDemo.getRange());
        for(Base obj : parseDemo.getConditions()){
            System.out.println(obj.getClass());
        }
    }

}
