package org.hellotoy.mvc.infr.api.criteria.criterion;

import java.util.List;

public class Restrictions {
    // '=' equals operator

    /**
     *  generates '=' equals
     * @param name
     * variableAlias
     * @return
     */
    public static Restriction eq(String name, Object value) {
        return new Restriction(Operator.EQUALS, name, value);
    }

    /**
     *  generates '=' equals
     * @param name
     * @return
     */
    public static Restriction isNull(String name) {
        return new Restriction(Operator.IS_NULL, name);
    }

    /**
     *
     * @param name
     * variableAlias
     * @return
     */
    // '!=' not equals operator
    public static Restriction ne(String name, Object value){
        return new Restriction(Operator.NOT_EQUALS, name, value);
    }

    /**
     *
     * @param name
     * @return
     */
    public static Restriction isNotNull(String name){
        return new Restriction(Operator.IS_NOT_NULL, name);
    }

    /**
     *
     * @param name
     * variableAlias
     * @return
     */
    // '<' less than operator
    public static Restriction lt(String name, Object Object){
        return new Restriction(Operator.LESS_THAN, name, Object);
    }

    /**
     *
     * @param name
     * variableAlias
     * @return
     */
    // '<=' less or equals than operator
    public static Restriction le(String name, Object value){
        return new Restriction(Operator.LESS_OR_EQUALS_THAN, name, value);
    }

    /**
     *
     * @param name
     * variableAlias
     * @return
     */
    // '>' greater than operator
    public static Restriction gt(String name, Object value){
        return new Restriction(Operator.GREATER_THAN, name, value);
    }

    /**
     *
     * @param name
     * variableAlias
     * @return
     */
    // '>=' greater or equals than operator
    public static Restriction ge(String name, Object value){
        return new Restriction(Operator.GREATER_OR_EQUALS_THAN, name, value);
    }

    /**
     *
     * @param name
     * variableAlias
     * @return
     */
    // '~=' contains than operator
    public static Restriction ilike(String name, Object value){
        return new Restriction(Operator.CONTAINS, name, value);
    }

    /**
     *
     * @param name
     * variableAlias
     * @return
     */
    // '|=' in list operator
    public static Restriction in(String name, Object value){
        return new Restriction(Operator.IN, name, value);
    }
    
    
    public static RestrictionList and(Restriction ...restrictions){
        return new RestrictionList(Operator.AND, restrictions);
    }
    public static RestrictionList or(Restriction ...restrictions){
        return new RestrictionList(Operator.OR, restrictions);
    }
    public static RestrictionList and(List<Restriction> exchangeRestrictions){
        return new RestrictionList(Operator.AND, exchangeRestrictions);
    }
    public static RestrictionList or(List<Restriction> exchangeRestrictions){
        return new RestrictionList(Operator.OR, exchangeRestrictions);
    }

    /**
     *
     * @param name
     * @param op
     * @param value
     * variableAlias
     * @return Restriction
     */
    public static Restriction filter(String name, String op, Object value) {
        Operator exchangeOperator = Operator.value(op);
        Restriction exchangeRestriction = filter(name, exchangeOperator, value);
        return exchangeRestriction;
    }

    /**
     *
     * @param name
     * @param exchangeOperator
     * @param value
     * variableAlias
     * @return
     */
    public static Restriction filter(String name, Operator exchangeOperator, Object value) {
        switch (exchangeOperator) {
            case EQUALS:
                return value == null || "".equals(value) ?
                        new Restriction(Operator.IS_NULL, name, value) :
                        new Restriction(Operator.EQUALS, name, value);
            case NOT_EQUALS:
                return value == null || "".equals(value) ?
                        new Restriction(Operator.IS_NOT_NULL, name, value) :
                        new Restriction(Operator.EQUALS, name, value);
            default:
                return new Restriction(exchangeOperator, name, value);
        }
    }
}
