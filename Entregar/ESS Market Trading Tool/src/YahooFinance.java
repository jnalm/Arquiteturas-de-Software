import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import yahoofinance.Stock;
import yahoofinance.Utils;
import yahoofinance.quotes.stock.StockQuote;
import yahoofinance.quotes.stock.StockQuotesData;
import yahoofinance.quotes.stock.StockQuotesRequest;

public class YahooFinance {
	
	private Map<String, Stock> stocks;
	
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
	
	static Map<String, Stock> getQuotes(String query, boolean includeHistorical) throws IOException {
        StockQuotesRequest request = new StockQuotesRequest(query);
        List<StockQuotesData> quotes = request.getResult();
        Map<String, Stock> result = new HashMap<String, Stock>();
        
        for(StockQuotesData data : quotes) {
            Stock s = data.getStock();
            result.put(s.getSymbol(), s);
        }
        
        if(includeHistorical) {
            for(Stock s : result.values()) {
                s.getHistory();
            }
        }
        
        return result;
    }
	
    public static Stock get(String symbol, boolean includeHistorical) throws IOException {
        Map<String, Stock> result = YahooFinance.getQuotes(symbol, includeHistorical);
        return result.get(symbol);
    }
	
	public static Stock get(String symbol) throws IOException {
        return YahooFinance.get(symbol, false);
    }
	
    public static Map<String, Stock> getMultiple(String[] symbols, boolean includeHistorical) throws IOException {
        return YahooFinance.getQuotes(Utils.join(symbols, ","), includeHistorical);
    }
}
