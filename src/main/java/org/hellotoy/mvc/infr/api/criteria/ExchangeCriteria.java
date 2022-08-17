package org.hellotoy.mvc.infr.api.criteria;


import org.hellotoy.mvc.infr.api.criteria.criterion.Operator;
import org.hellotoy.mvc.infr.api.criteria.criterion.Restriction;

import java.util.LinkedList;
import java.util.List;


public class ExchangeCriteria {
	
	private final List<Restriction> restrictions = new LinkedList<>();
    
    public List<Restriction> getRestrictions() {
        return restrictions;
    }
    /**
     *
     */
    private List<Order> orders = new LinkedList<>();
    /**
     * Starting index for the paged fetches.
     */
    private Integer pageNum = 1;
    /**
     * Maximum number of results per page.
     */
    private Integer pageSize = 10;

  
    /**
     * Sets starting index for the paged fetches.
     * @param offset
     */
    public ExchangeCriteria setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
        return this;
    }

    /**
     * Sets maximum number of results per page.
     * @param limit
     */
    public ExchangeCriteria setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    /**
     * Gets starting index for the paged fetches.
     * @return
     */
    public Integer getPageNum() {
    	if(pageNum==null) {
    		return 1;
    	}
        return pageNum;
    }
    
    /**
     * Gets maximum number of results per page.
     * @return
     */
    public Integer getPageSize() {
        return pageSize;
    }

    public ExchangeCriteria add(Restriction criterion) {
    	restrictions.add(criterion);
        return this;
    }

    public ExchangeCriteria addOrder(Order exchangeOrder) {
        orders.add(exchangeOrder);
        return this;
    }

    public List<Order> getOrders() {
        return orders;
    }
    
    public Restriction filter(String name, Operator operator) {
    	for(Restriction restriction :restrictions) {
    		if(restriction.getName().equals(name) && restriction.getOperator() == operator) {
    			return restriction;
    		}
    	}
    	return null;
    }

    
}
