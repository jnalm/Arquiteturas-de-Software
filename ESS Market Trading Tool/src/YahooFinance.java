import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import yahoofinance.Stock;
import yahoofinance.Utils;
import yahoofinance.quotes.csv.StockQuotesData;
import yahoofinance.quotes.csv.StockQuotesRequest;
import yahoofinance.quotes.query1v7.StockQuotesQuery1V7Request;
/*
import yahoofinance.quotes.stock.StockQuotesData;
import yahoofinance.quotes.stock.StockQuotesRequest;
*/
public class YahooFinance {
	public static final String QUOTES_QUERY1V7_ENABLED = System.getProperty("yahoofinance.quotesquery1v7.enabled", "true");

    public static Stock get(String symbol) throws IOException {
        return YahooFinance.get(symbol, false);
    }
    
    public static Stock get(String symbol, boolean includeHistorical) throws IOException {
        Map<String, Stock> result = YahooFinance.getQuotes(symbol, includeHistorical);
        return result.get(symbol.toUpperCase());
    }
    
    public static Map<String, Stock> get(String[] symbols, boolean includeHistorical) throws IOException {
        return YahooFinance.getQuotes(Utils.join(symbols, ","), includeHistorical);
    }
    
    private static Map<String, Stock> getQuotes(String query, boolean includeHistorical) throws IOException {
        Map<String, Stock> result = new HashMap<String, Stock>();
        if(YahooFinance.QUOTES_QUERY1V7_ENABLED.equalsIgnoreCase("true")) {
            StockQuotesQuery1V7Request request = new StockQuotesQuery1V7Request(query);
            List<Stock> stocks = request.getResult();
            for(Stock stock : stocks) {
                result.put(stock.getSymbol(), stock);
            }
        } else {
            StockQuotesRequest request = new StockQuotesRequest(query);
            List<StockQuotesData> quotes = request.getResult();
            for(StockQuotesData data : quotes) {
                Stock s = data.getStock();
                result.put(s.getSymbol(), s);
            }
        }

        if(includeHistorical) {
            for(Stock s : result.values()) {
                s.getHistory();
            }
        }

        return result;
    }
}
