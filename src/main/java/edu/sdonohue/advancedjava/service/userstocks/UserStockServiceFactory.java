package edu.sdonohue.advancedjava.service.userstocks;

/**
 * A factory that returns a <CODE>UserStockService</CODE> instance.
 */
public class UserStockServiceFactory {

    /**
     * Prevent instantiations.
     */
    private UserStockServiceFactory() {}

    /**
     * Factory method to return a UserStockService.
     *
     * @return get a <CODE>UserStockService</CODE> instance
     */
    public static UserStockService getInstance() {
        return new DatabaseUserStockService();
    }

}
