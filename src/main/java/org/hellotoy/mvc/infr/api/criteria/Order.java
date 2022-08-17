package org.hellotoy.mvc.infr.api.criteria;

public class Order {
	
    private String name;
    private Type type;

    public Order(String name, Type type, boolean isAlias) {
		this.name = name;
        this.type = type;
    }
    public Order() {
    }

    public static Order asc(String name) {
        return new Order(name, Type.ASC, false);
    }

    public static Order ascByAlias(String alias) {
        return new Order(alias, Type.ASC, true);
    }

    public static Order desc(String name) {
        return new Order(name, Type.DESC, false);
    }

    public static Order descByAlias(String alias) {
        return new Order(alias, Type.DESC, true);
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public enum Type {
        ASC("+") , DESC("-");
        /**
         * Holds operator as {@link String}
         */
        private String value;

        /**
         *
         * @param value
         */
        Type(String value) {
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
         * gets Operator as {@link Type} enum type
         * @param op
         * @return
         */
        public static Type value(String op){
            for(Type type: Type.values()) {
                if(type.value().equals(op)) {
                    return type;
                }
            }
            throw new RuntimeException("Value not found in " + Type.class.getName()) ;
        }
    }
}
