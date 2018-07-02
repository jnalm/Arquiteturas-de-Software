import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import yahoofinance.Stock;
import yahoofinance.Utils;
import yahoofinance.quotes.csv.StockQuotesData;
import yahoofinance.quotes.csv.StockQuotesRequest;
import yahoofinance.quotes.query1v7.StockQuotesQuery1V7Request;
import yahoofinance.quotes.stock.StockQuote;
/*
import yahoofinance.quotes.stock.StockQuotesData;
import yahoofinance.quotes.stock.StockQuotesRequest;
*/
public class YahooFinance {
	public static final String QUOTES_QUERY1V7_ENABLED = System.getProperty("yahoofinance.quotesquery1v7.enabled", "true");
	private Map<String, Stock> stocks;
	/*
	public String getAtivos() {
		
		StringBuilder sb = new StringBuilder();
		
		String[] symbols = new String[] {"KO", "INTC", "GM", "AAPL", "NVDA", "MCD", "TSLA", "IBM", "GE", "BTCUSD=X"}; 
		
		try {
			stocks = YahooFinance.getMultiple(symbols, true);
			Stock cocacola = stocks.get("KO");
			Stock intel = stocks.get("INTC");
			Stock generalmotors = stocks.get("GM");
			Stock apple = stocks.get("AAPL");
			Stock nvidia = stocks.get("NVDA");
			Stock mcdonalds = stocks.get("MCD");
			Stock tesla = stocks.get("TSLA");
			Stock ibm = stocks.get("IBM");
			Stock ge = stocks.get("GE");
			Stock bitcoin = stocks.get("BTCUSD=X");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(Stock s : stocks.values()) {
			String name = s.getName();
			StockQuote change = s.getQuote();
			BigDecimal peg = s.getStats().getPeg();
			sb.append(name + "  " + change + "  " + peg + "\n\n");
		}
		
		return sb.toString();
	}
	*/
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
