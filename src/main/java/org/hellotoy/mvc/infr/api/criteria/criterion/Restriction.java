package org.hellotoy.mvc.infr.api.criteria.criterion;

import io.swagger.annotations.ApiModelProperty;

public class Restriction {
    private  Operator operator;
    @ApiModelProperty(value = "字段值")
    private Object value;
    @ApiModelProperty(value = "字段名称")
    private String name;
    
    public Restriction(){
    	
    }
    
    public Restriction(Operator exchangeOperator, String name) {
        this(exchangeOperator, name, null);
    }

    public Restriction(Operator exchangeOperator, String name, Object value) {
        this.operator = exchangeOperator;
        this.name = name;
        this.value = value;
    }

    public Operator getOperator() {
        return operator;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }


    @Override
    public String toString() {
        return "Restriction{" +
                "operator=" + operator +
                ", value=" + value +
                ", name='" + name + '\'' +
                "}";
    }

    @Override
    public int hashCode() {
        int result = getOperator() != null ? getOperator().hashCode() : 0;
        result = 31 * result + (getValue() != null ? getValue().hashCode() : 0);
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        return result;
    }
}
