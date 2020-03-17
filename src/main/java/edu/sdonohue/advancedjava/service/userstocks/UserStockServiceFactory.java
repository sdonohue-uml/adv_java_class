package edu.sdonohue.advancedjava.service.userstocks;

/**
 * A factory that returns a <CODE>ActivitiesService</CODE> instance.
 */
public class UserStockServiceFactory {

    /**
     * Prevent instantiations
     */
    private UserStockServiceFactory() {}

    /**
     *
     * @return get a <CODE>StockService</CODE> instance
     */
    public static UserStockService getInstance() {
        return new DatabaseUserStockService();
    }

}
