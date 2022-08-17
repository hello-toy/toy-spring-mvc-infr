package org.hellotoy.mvc.infr.api.criteria.criterion;

import io.swagger.annotations.ApiModelProperty;

/**
 * Holds Query Operators as Enum
 */
public enum Operator {
    /**
     * Holds "equals" operator ( = ) to operate on all types
     */
	@ApiModelProperty(value = "等于")
    EQUALS("="),
    /**
     * Holds "not equals" operator ( != ) to operate on {@link String} or {@link Number} types
     */
    NOT_EQUALS("!="),
    /**
     * Holds "less than operator" ( < )  to operate on {@link String} or {@link Number} types
     */
    LESS_THAN("<"),
    /**
     * Holds "less than or equals operator" ( <= )  to operate on number types
     */
    LESS_OR_EQUALS_THAN("<="),
    /**
     * Holds "greater than operator" ( > ) to operate on number types
     */
    GREATER_THAN(">"),
    /**
     * Holds "greater than or equals operator" ( >= )  to operate on {@link Number} types
     */
    GREATER_OR_EQUALS_THAN(">="),
    /**
     * Holds "contains" ( ~= ) to operate on {@link String} types. In SQL its LIKE command
     */
    CONTAINS("~="),
    /**
     * Holds "q" ( _ ) to operate on {@link String} types. In SQL its LIKE command and it used to search without field names.
     */
    Q("_q"),
    /**
     * Holds "in" ( |= ) operator to operate on @{@link io.robe.common.utils.Collections} types.
     */
    IN("|="), IS_NULL("IS NULL"), IS_NOT_NULL("IS_NOT_NULL"), AND("&&"), OR("||");

    /**
     * Holds operator as {@link String}
     */
    private String value;

    /**
     *
     * @param value
     */
    Operator(String value) {
        this.value = value;
    }

    /**
     * gets Operator as {@link String}
     * @return
     */
    public String value() {
        return value;
    }


    /**
     * gets Operator as {@link Operator} enum type
     * @param value
     * @return
     */
    public static Operator value(String value){
        for(Operator exchangeOperator: Operator.values()) {
            if(exchangeOperator.value().equals(value)) {
                return exchangeOperator;
            }
        }
        throw new RuntimeException("Value not found in " + Operator.class.getName()) ;
    }
}