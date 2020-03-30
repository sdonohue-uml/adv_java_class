package edu.sdonohue.advancedjava.service.stocks;


import javax.validation.constraints.NotNull;

/**
 * Factory class for creating instances of StockService
 *
 * @author Sean Donohue
 */
public class StockServiceFactory {
    private static DataSource dataSource = DataSource.DATABASE;

    public static DataSource getDataSource() {
        return dataSource;
    }

    public static void setDataSource(DataSource dataSource) {
        if (dataSource != null) {
            StockServiceFactory.dataSource = dataSource;
        }
    }

    /**
     * Returns a StockService that can be used to retrieve StockQuotes.
     *
     * @return A instance of StockService
     */
    @NotNull
    public static StockService getStockService() {
        try {
            return (StockService) dataSource.getStockServiceClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return new DatabaseStockService();
    }

    public enum DataSource {
        DATABASE("Local Database", DatabaseStockService.class),
        UNIBIT("Online Stock Service", RestStockService.class),
        TEST_DATA("Generated Test Data", BasicStockService.class);

        private String displayText;
        private Class stockServiceClass;

        DataSource(String displayText, Class stockServiceClass) {
            this.displayText = displayText;
            this.stockServiceClass = stockServiceClass;
        }

        public Class getStockServiceClass() {
            return stockServiceClass;
        }

        @Override
        public String toString(){
            return displayText;
        }
    }
}
