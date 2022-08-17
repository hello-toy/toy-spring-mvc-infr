package org.hellotoy.mvc.infr.api.criteria.criterion;

import java.util.Arrays;
import java.util.List;

public class RestrictionList extends Restriction {
	
    private final List<Restriction> exchangeRestrictions;
    
    public RestrictionList(Operator exchangeOperator, Restriction[] restrictions) {
        this(exchangeOperator, Arrays.asList(restrictions));
    }
    public RestrictionList(Operator exchangeOperator, List<Restriction> exchangeRestrictions) {
        super(exchangeOperator, null, null);
        this.exchangeRestrictions = exchangeRestrictions;
    }

    public List<Restriction> getRestrictions() {
        return exchangeRestrictions;
    }
}
