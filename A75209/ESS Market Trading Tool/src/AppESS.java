
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.List;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.mysql.jdbc.ResultSetMetaData;

import yahoofinance.Stock;
import yahoofinance.Utils;
import yahoofinance.quotes.stock.StockQuote;
/*
import yahoofinance.quotes.stock.StockQuotesData;
import yahoofinance.quotes.stock.StockQuotesRequest;
*/
public class AppESS implements App {
	
	private TableModel tm;
	private String change = "RECEBA NOTIFICA��ES ACERCA DOS VALORES DOS SEUS ATIVOS";
	
	public String isChange() {
		return change;
	}

	private Map<String, Float> ativosAlerta = new HashMap<String, Float>();
	private List<Menu> listOfMenu = new ArrayList<Menu>();
	private int userLogin;
	private boolean login;
	
	public TableModel getTm() {
		return tm;
	}

	public void setTm(TableModel tm) {
		this.tm = tm;
		notifyObservers();
	}

	public List<Menu> getListOfMenu() {
		return listOfMenu;
	}

	public void setListOfMenu(List<Menu> listOfMenu) {
		this.listOfMenu = listOfMenu;
	}

	private Connection con;
	private Statement st;
	private Statement st2;
	private ResultSet rs;
	
	private Map<String, Stock> stocks = new HashMap<>();
	
	public AppESS() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?useSSL=true", "root", "1234");
			st = con.createStatement();
			st2= con.createStatement();
			
			Stock cocacola = YahooFinance.get("KO");
			stocks.put("KO", cocacola);
			Stock intel = YahooFinance.get("INTC");
			stocks.put("INTC", intel);
			Stock gm = YahooFinance.get("GM");
			stocks.put("GM", gm);
			Stock apple = YahooFinance.get("AAPL");
			stocks.put("AAPL", apple);
			Stock nvidia = YahooFinance.get("NVDA");
			stocks.put("NVDA", nvidia);
			Stock mcd = YahooFinance.get("MCD");
			stocks.put("MCD", mcd);
			Stock tesla = YahooFinance.get("TSLA");
			stocks.put("TSLA", tesla);
			Stock ibm = YahooFinance.get("IBM");
			stocks.put("IBM", ibm);
			Stock ge = YahooFinance.get("GE");
			stocks.put("GE", ge);
			Stock microsoft = YahooFinance.get("MSFT");
			stocks.put("MSFT", microsoft);
			
		} catch(Exception e) {
			System.out.println("Error: " + e);
		}
	}
	
	public void getData() {
		
		try {
			
			String query = "select * from utilizador";
			rs = st.executeQuery(query);
			System.out.println("Records from Database: ");
			while(rs.next()) {
				
				String name = rs.getString("Nome");
				String username = rs.getString("Username");
				String password = rs.getString("Password");
				String email = rs.getString("Email");
				Date dob = rs.getDate("DOB");
				String pais = rs.getString("Pa�s");
				Float plafond = rs.getFloat("Plafond");
				System.out.println(name + username + password + email + pais + dob + plafond);
				
			}
			
		} catch(Exception e) {
			System.out.println("Error: " + e);
		}
	}
	
	public int getUserLogin() {
		return userLogin;
	}
	public void setUserLogin(int userLogin) {
		this.userLogin = userLogin;
	}
	public boolean isLogin() {
		return login;
	}
	public void setLogin(boolean login) {
		this.login = login;
	}
	/*
	private static Map<String, Stock> getQuotes(String query, boolean includeHistorical) throws IOException {
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
	*//*
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
	*/
	@Override
	public String toString() {
		return "AppESS [userLogin=" + userLogin + ", login=" + login + "]";
	}
	
	public void logout() {
		this.login = false;
		this.userLogin = 0;
	}
	
	public boolean criaConta(String nome, String username, String password, String pais, String dob, String email) {
		boolean good = true;
		try {
			ResultSet resultset;
			ResultSet resultset2;
			Statement st3 = con.createStatement();
			resultset = st.executeQuery("SELECT COUNT(idUtilizador) FROM utilizador");
			resultset.next();
			int nContas = ((Number) resultset.getObject(1)).intValue() + 1;
			resultset2 = st3.executeQuery("SELECT Username FROM utilizador");
			while(resultset2.next() && (good == true)) {
				if(username.equals(resultset2.getString("Username"))) {
					good = false;
					return good;
				}
			}
			st2.executeUpdate("INSERT INTO portfolio " + "VALUES (" + nContas + ")");
			st.executeUpdate("INSERT INTO utilizador " + "VALUES (" + nContas + ",'" + nome + "','" + username + "','" + password + "','" + email + "','" + dob + "','" + pais + "'," + 1000.0 + "," + nContas + ")");
			con.close();
		} catch(Exception e) {
			System.out.println("Error: " + e);
		}
		return good;
	}
	
	public void fazLogin(String user, String password) {
		
		try {
			
			ResultSet rs;
			rs = st.executeQuery("SELECT * FROM utilizador");
			
			while(rs.next() || login == false) {
				if(user.equals(rs.getString("Username"))) {
					if(password.equals(rs.getString("Password"))) {
						this.login = true;
					}
				}
			}
			rs = st.executeQuery("SELECT * FROM utilizador WHERE Username =  '" + user + "'");
			rs.next();
			this.setUserLogin(rs.getInt("idUtilizador"));
		} catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public String getAtivos() throws SQLException, IOException {
		
		StringBuilder sb = new StringBuilder(); 
		
		for(String s : stocks.keySet()) {
			String key = s;
			String name = stocks.get(s).getName();
			Stock st = YahooFinance.get(s);
			String quote = st.getQuote().toString();
			String[] tempQuote = quote.split(", ");
			String tempQuotes = tempQuote[1];
			String[] resultQuote  = tempQuotes.split(" ");
			String bid = resultQuote[1];
			String[] tempQuote2 = quote.split(", ");
			String tempQuotes2 = tempQuote2[0];
			String[] resultQuote2  = tempQuotes2.split(" ");
			String ask = resultQuote2[1];
			String[] tempQuote3 = quote.split(", ");
			String tempQuotes3 = tempQuote3[2];
			String[] resultQuote3  = tempQuotes3.split(" ");
			String price = resultQuote3[1];
			String prev = st.getQuote().getChangeInPercent().toString();
			sb.append(key + "::" + name + "::" + ask + "::" + bid + "::" + price + "::" + prev + "\n");
		}
		
		return sb.toString();
	}
	
	public void addAtivo(String nome) {
		
		try {
			
			Statement st = con.createStatement();
			String query = "SELECT Tipo FROM ativo WHERE Tipo = '" + nome + "'";
			ResultSet rst = st.executeQuery(query);
			if(rst.next() == false) {
				ResultSet rs;
				rs = st.executeQuery("SELECT COUNT(idAtivo) FROM ativo");
				rs.next();
				int nAtivos = ((Number) rs.getObject(1)).intValue() + 1;
				
				Statement st4 = con.createStatement();
				String valoresMercado = YahooFinance.get(nome).getQuote().toString();
				String[] tempQuote = valoresMercado.split(", ");
				String tempQuotes = tempQuote[1];
				String[] resultQuote  = tempQuotes.split(" ");
				String bid = resultQuote[1];
				String[] tempQuote2 = valoresMercado.split(", ");
				String tempQuotes2 = tempQuote2[0];
				String[] resultQuote2  = tempQuotes2.split(" ");
				String ask = resultQuote2[1];
				String fullname = stocks.get(nome).getName();
				String query2 = "INSERT INTO ativo VALUES (" + nAtivos + ",'" + fullname + "','" + nome +  "','" + ask + "','" + bid + "')";
				
				st4.executeUpdate(query2);
				
				ResultSet resultset;
				
				resultset = st.executeQuery("SELECT idAtivo FROM ativo WHERE Tipo = '" + nome + "'");
				resultset.next();
				int idAtivo = ((Number) resultset.getObject(1)).intValue();
			
				Statement st6 = con.createStatement();
			
				st6.executeUpdate("INSERT INTO ativo_has_portfolio(Ativo_idAtivo,Portfolio_idPortfolio) " + "VALUES (" + idAtivo + "," + userLogin + ")");
			
			} else {
			
				ResultSet resultset;
			
				resultset = st.executeQuery("SELECT idAtivo FROM ativo WHERE Tipo = '" + nome + "'");
				resultset.next();
				int idAtivo = ((Number) resultset.getObject(1)).intValue();
				
				String valoresMercado = YahooFinance.get(nome).getQuote().toString();
				String[] tempQuote = valoresMercado.split(", ");
				String tempQuotes = tempQuote[1];
				String[] resultQuote  = tempQuotes.split(" ");
				String bid = resultQuote[1];
				String[] tempQuote2 = valoresMercado.split(", ");
				String tempQuotes2 = tempQuote2[0];
				String[] resultQuote2  = tempQuotes2.split(" ");
				String ask = resultQuote2[1];
				
				Statement s = con.createStatement();
				s.executeUpdate("UPDATE ativo SET ValorVenda = '" + bid + "', ValorCompra = '" + ask + "' WHERE idAtivo = " + idAtivo);
				
				Statement st6 = con.createStatement();
			
				st6.executeUpdate("INSERT INTO ativo_has_portfolio(Ativo_idAtivo,Portfolio_idPortfolio) " + "VALUES (" + idAtivo + "," + userLogin + ")");
			
			}
		} catch(Exception e) {
			System.out.println("Error: " + e);
		}
	}
	
	public void removeAtivo(int id) {
		
		try {
			Statement st4 = con.createStatement();
			
			st4.executeUpdate("DELETE FROM ativo_has_portfolio WHERE Ativo_idAtivo = " + id + " AND Portfolio_idPortfolio = " + userLogin);
			
			con.close();
		} catch(Exception e) {
			System.out.println("Error: " + e);
		}
	}
	
	public ResultSet getPortfolio() {
		
		try {
			
			String query = "SELECT at.idAtivo, at.Nome, at.Tipo, at.ValorCompra, at.ValorVenda FROM ativo AS at INNER JOIN ativo_has_portfolio AS ahp ON at.idAtivo = ahp.Ativo_idAtivo WHERE ahp.Portfolio_idPortfolio = " + userLogin;
			rs = st.executeQuery(query);
			
		} catch(Exception e) {
			System.out.println("Error: " + e);
		}
		return rs;
	}
	
	public float getPlafond(int userLogin) {
		float plafond = 0;
		try {
			
			String query = "SELECT Plafond FROM utilizador WHERE idUtilizador = " + userLogin;
			rs = st.executeQuery(query);
			rs.next();
			plafond = ((Number) rs.getObject(1)).floatValue();
		} catch(Exception e) {
			System.out.println("Error: " + e);
		}
		return plafond;	
	}
	
	public void abrirCompra(String nome, float montante, float ll, float lp) {
		
		try {
			
			ResultSet rs;
			Statement st1 = con.createStatement();
			rs = st1.executeQuery("SELECT idAtivo FROM ativo WHERE Tipo = '" + nome + "'");
			if(rs.next() == false) {
				ResultSet rs1;
				rs1 = st.executeQuery("SELECT COUNT(idAtivo) FROM ativo");
				rs1.next();
				int nAtivos = ((Number) rs1.getObject(1)).intValue() + 1;
				
				Statement st4 = con.createStatement();
				String fullname = stocks.get(nome).getName();
				String valoresMercado = YahooFinance.get(nome).getQuote().toString();
				String[] tempQuote = valoresMercado.split(", ");
				String tempQuotes = tempQuote[0];
				String[] resultQuote  = tempQuotes.split(" ");
				String bid = resultQuote[1];
				String[] tempQuote2 = valoresMercado.split(", ");
				String tempQuotes2 = tempQuote2[1];
				String[] resultQuote2  = tempQuotes2.split(" ");
				String ask = resultQuote2[1];
				String query2 = "INSERT INTO ativo VALUES (" + nAtivos + ",'" + fullname + "','" + nome +  "','" + ask + "','" + bid + "')";
				
				st4.executeUpdate(query2);
				
				ResultSet resultset;
				
				resultset = st.executeQuery("SELECT idAtivo FROM ativo WHERE Tipo = '" + nome + "'");
				resultset.next();
				int idAtivo = ((Number) resultset.getObject(1)).intValue();
			
				Statement st6 = con.createStatement();
			
				st6.executeUpdate("INSERT INTO utilizador_has_ativo(Utilizador_idUtilizador, Ativo_idAtivo, MontanteResultante, Estado, Montante, LimiteLucro, LimitePerda) VALUES (" + getUserLogin() + "," + idAtivo + "," + null + ", 'COMPRA', " + montante + "," + ll + "," + lp + ")");
				
				rs = st6.executeQuery("SELECT Plafond FROM utilizador WHERE idUtilizador = " + getUserLogin());
				rs.next();
				float plafondAnterior = ((Number) rs.getObject(1)).floatValue();
				float plafondResultante = plafondAnterior - montante; 
				st6.executeUpdate("UPDATE utilizador SET Plafond = " + plafondResultante + " WHERE idUtilizador = " + getUserLogin());
				
			} else {
				ResultSet resultset;
				
				resultset = st.executeQuery("SELECT idAtivo FROM ativo WHERE Tipo = '" + nome + "'");
				resultset.next();
				int idAtivo = ((Number) resultset.getObject(1)).intValue();
				
				String valoresMercado = YahooFinance.get(nome).getQuote().toString();
				String[] tempQuote = valoresMercado.split(", ");
				String tempQuotes = tempQuote[0];
				String[] resultQuote  = tempQuotes.split(" ");
				String bid = resultQuote[1];
				String[] tempQuote2 = valoresMercado.split(", ");
				String tempQuotes2 = tempQuote2[1];
				String[] resultQuote2  = tempQuotes2.split(" ");
				String ask = resultQuote2[1];
				
				Statement s = con.createStatement();
				s.executeUpdate("UPDATE ativo SET ValorCompra = '" + ask + "', ValorVenda = '" + bid + "' WHERE idAtivo = " + idAtivo);
				
				Statement st6 = con.createStatement();
				ResultSet rs3;
				st6.executeUpdate("INSERT INTO utilizador_has_ativo(Utilizador_idUtilizador, Ativo_idAtivo, MontanteResultante, Estado, Montante, LimiteLucro, LimitePerda) VALUES (" + getUserLogin() + "," + idAtivo + "," + null + ", 'COMPRA', " + montante + "," + ll + "," + lp + ")");
				
				rs3 = st6.executeQuery("SELECT Plafond FROM utilizador WHERE idUtilizador = " + getUserLogin());
				rs3.next();
				float plafondAnterior = ((Number) rs3.getObject(1)).floatValue();
				float plafondResultante = plafondAnterior - montante; 
				st6.executeUpdate("UPDATE utilizador SET Plafond = " + plafondResultante + " WHERE idUtilizador = " + getUserLogin());
			}
		} catch(Exception e) {
			System.out.println("Error: " + e);
		}
		
	}
	
	public ResultSet getAtivosCompra() {
		
		try {
			
			String query = "SELECT uha.Ativo_idAtivo, at.Nome, at.ValorVenda, at.ValorCompra, uha.LimiteLucro, uha.LimitePerda, uha.Montante, uha.MontanteResultante FROM utilizador_has_ativo AS uha INNER JOIN ativo AS at ON uha.Ativo_idAtivo = at.idAtivo WHERE uha.Utilizador_idUtilizador = " + getUserLogin() + " AND uha.Estado = 'COMPRA'";
			rs = st.executeQuery(query);
			
		} catch(Exception e) {
			System.out.println("Error: " + e);
		}
		return rs;
	}
	
	public void abrirVenda(String nome, float montante, float ll, float lp) {
		
		try {
			
			ResultSet rs;
			Statement st1 = con.createStatement();
			rs = st1.executeQuery("SELECT idAtivo FROM ativo WHERE Tipo = '" + nome + "'");
			if(rs.next() == false) {
				ResultSet rs1;
				rs1 = st.executeQuery("SELECT COUNT(idAtivo) FROM ativo");
				rs1.next();
				int nAtivos = ((Number) rs1.getObject(1)).intValue() + 1;
				
				Statement st4 = con.createStatement();
				String fullname = stocks.get(nome).getName();
				String valoresMercado = YahooFinance.get(nome).getQuote().toString();
				String[] temps = valoresMercado.split(", ");
				String temp = temps[1];
				String[] result = temp.split(" ");
				String ask = result[1];
				String[] temps2 = valoresMercado.split(", ");
				String temp2 = temps2[0];
				String[] result2 = temp2.split(" ");
				String bid = result2[1];
				String query2 = "INSERT INTO ativo VALUES (" + nAtivos + ",'" + fullname + "','" + nome +  "','" + ask + "','" + bid + "')";
				
				st4.executeUpdate(query2);
				
				ResultSet resultset;
				
				resultset = st.executeQuery("SELECT idAtivo FROM ativo WHERE Tipo = '" + nome + "'");
				resultset.next();
				int idAtivo = ((Number) resultset.getObject(1)).intValue();
			
				Statement st6 = con.createStatement();
			
				st6.executeUpdate("INSERT INTO utilizador_has_ativo(Utilizador_idUtilizador, Ativo_idAtivo, MontanteResultante, Estado, Montante, LimiteLucro, LimitePerda) VALUES (" + getUserLogin() + "," + idAtivo + "," + null + ", 'VENDA', " + montante + "," + ll + "," + lp + ")");
				
				rs = st6.executeQuery("SELECT Plafond FROM utilizador WHERE idUtilizador = " + getUserLogin());
				rs.next();
				float plafondAnterior = ((Number) rs.getObject(1)).floatValue();
				float plafondResultante = plafondAnterior - montante; 
				st6.executeUpdate("UPDATE utilizador SET Plafond = " + plafondResultante + " WHERE idUtilizador = " + getUserLogin());
				
			} else {
				ResultSet resultset;
				
				resultset = st.executeQuery("SELECT idAtivo FROM ativo WHERE Tipo = '" + nome + "'");
				resultset.next();
				int idAtivo = ((Number) resultset.getObject(1)).intValue();
				
				String valoresMercado = YahooFinance.get(nome).getQuote().toString();
				String[] temps = valoresMercado.split(", ");
				String temp = temps[1];
				String[] result = temp.split(" ");
				String ask = result[1];
				String[] temps2 = valoresMercado.split(", ");
				String temp2 = temps2[0];
				String[] result2 = temp2.split(" ");
				String bid = result2[1];
				
				Statement s = con.createStatement();
				s.executeUpdate("UPDATE ativo SET ValorVenda = '" + bid + "', ValorCompra = '" + ask + "' WHERE idAtivo = " + idAtivo);
				
				Statement st6 = con.createStatement();
				ResultSet rs3;
				st6.executeUpdate("INSERT INTO utilizador_has_ativo(Utilizador_idUtilizador, Ativo_idAtivo, MontanteResultante, Estado, Montante, LimiteLucro, LimitePerda) VALUES (" + getUserLogin() + "," + idAtivo + "," + null + ", 'VENDA', " + montante + "," + ll + "," + lp + ")");
				
				rs3 = st6.executeQuery("SELECT Plafond FROM utilizador WHERE idUtilizador = " + getUserLogin());
				rs3.next();
				float plafondAnterior = ((Number) rs3.getObject(1)).floatValue();
				float plafondResultante = plafondAnterior - montante; 
				st6.executeUpdate("UPDATE utilizador SET Plafond = " + plafondResultante + " WHERE idUtilizador = " + getUserLogin());
			}
		} catch(Exception e) {
			System.out.println("Error: " + e);
		}
		
	}
	
	public ResultSet getAtivosVenda() {
		
		try {
			
			String query = "SELECT uha.Ativo_idAtivo, at.Nome, at.ValorVenda, at.ValorCompra, uha.LimiteLucro, uha.LimitePerda, uha.Montante, uha.MontanteResultante FROM utilizador_has_ativo AS uha INNER JOIN ativo AS at ON uha.Ativo_idAtivo = at.idAtivo WHERE uha.Utilizador_idUtilizador = " + getUserLogin() + " AND uha.Estado = 'VENDA'";
			rs = st.executeQuery(query);
			
		} catch(Exception e) {
			System.out.println("Error: " + e);
		}
		return rs;
	}
	
	public void fechaCompra(int idAtivo) {
		
		try {
			
			Statement st = con.createStatement();
			ResultSet rs;
			rs = st.executeQuery("SELECT ValorCompra FROM ativo WHERE idAtivo = " + idAtivo);
			rs.next();
			float valorCompra = rs.getFloat(1);

 			Statement st2 = con.createStatement();
 			ResultSet rs2;
 			rs2 = st2.executeQuery("SELECT Montante FROM utilizador_has_ativo WHERE Ativo_idAtivo = " + idAtivo + " AND Utilizador_idUtilizador = " + getUserLogin());
 			rs2.next();
			float montanteInvestido = ((Number) rs2.getObject(1)).floatValue();
			
			Statement st3 = con.createStatement();
			ResultSet rs3;
			rs3 = st3.executeQuery("SELECT Tipo FROM ativo WHERE idAtivo = " + idAtivo);
			rs3.next();
			String tipo = ((String) rs3.getObject(1)).toString();
			String quote = YahooFinance.get(tipo).getQuote().toString();
			String[] tempQuote = quote.split(", ");
			String tempQuotes = tempQuote[0];
			String[] resultQuote  = tempQuotes.split(" ");
			String tempResultQuote = resultQuote[1];
			float priceAtual = Float.parseFloat(tempResultQuote);
			
			float quantiaInicial = montanteInvestido * valorCompra;
			
			float quantiaAtual = montanteInvestido * priceAtual;
			
			float addPlafond = quantiaAtual - quantiaInicial;
			float lucros = addPlafond + montanteInvestido;
			
			Statement st4 = con.createStatement();
			ResultSet rs4;
			rs4 = st4.executeQuery("SELECT Plafond FROM utilizador WHERE idUtilizador = " + getUserLogin());
			rs4.next();
			float plafondAtual = ((Number) rs4.getObject(1)).floatValue();
			
			float setPlafond = plafondAtual + lucros;
			Statement st5 = con.createStatement();
			st5.executeUpdate("UPDATE utilizador SET Plafond = " + setPlafond + " WHERE idUtilizador = " + getUserLogin());
			
			Statement st6 = con.createStatement();
			st6.executeUpdate("DELETE FROM utilizador_has_ativo WHERE Utilizador_idUtilizador = " + getUserLogin() + " AND Ativo_idAtivo = " + idAtivo);
		} catch(Exception e) {
			System.out.println("Error: " + e);
		}
	}
	
	public void fechaVenda(int idAtivo) {
		
		try {
			
			Statement st = con.createStatement();
			ResultSet rs;
			rs = st.executeQuery("SELECT ValorVenda FROM ativo WHERE idAtivo = " + idAtivo);
			rs.next();
			float valorVenda = rs.getFloat(1);
 			
 			Statement st2 = con.createStatement();
 			ResultSet rs2;
 			rs2 = st2.executeQuery("SELECT Montante FROM utilizador_has_ativo WHERE Ativo_idAtivo = " + idAtivo + " AND Utilizador_idUtilizador = " + getUserLogin());
 			rs2.next();
			float montanteInvestido = ((Number) rs2.getObject(1)).floatValue();
			
			Statement st3 = con.createStatement();
			ResultSet rs3;
			rs3 = st3.executeQuery("SELECT Tipo FROM ativo WHERE idAtivo = " + idAtivo);
			rs3.next();
			String tipo = ((String) rs3.getObject(1)).toString();
			String quote = YahooFinance.get(tipo).getQuote().toString();
			String[] tempQuote = quote.split(", ");
			String tempQuotes = tempQuote[1];
			String[] resultQuote  = tempQuotes.split(" ");
			String tempResultQuote = resultQuote[1];
			float priceAtual = Float.parseFloat(tempResultQuote);
			float quantiaInicial = montanteInvestido * valorVenda;
			float quantiaAtual = montanteInvestido * priceAtual;
			float addPlafond = quantiaAtual - quantiaInicial;
			Statement st4 = con.createStatement();
			ResultSet rs4;
			rs4 = st4.executeQuery("SELECT Plafond FROM utilizador WHERE idUtilizador = " + getUserLogin());
			rs4.next();
			float plafondAtual = ((Number) rs4.getObject(1)).floatValue();
			float lucros = addPlafond + montanteInvestido;
			float setPlafond = plafondAtual + lucros;
			Statement st5 = con.createStatement();
			st5.executeUpdate("UPDATE utilizador SET Plafond = " + setPlafond + " WHERE idUtilizador = " + getUserLogin());
			
			Statement st6 = con.createStatement();
			st6.executeUpdate("DELETE FROM utilizador_has_ativo WHERE Utilizador_idUtilizador = " + getUserLogin() + " AND Ativo_idAtivo = " + idAtivo);
		} catch(Exception e) {
			System.out.println("Error: " + e);
		}
	}
	
	public void addFundos(float fundos) {
		
		try {
			
			Statement stat2 = con.createStatement();
			ResultSet rst;
			rst = stat2.executeQuery("SELECT Plafond FROM utilizador WHERE idUtilizador = " + getUserLogin());
			rst.next();
			float plafondAtual = ((Number) rst.getObject(1)).floatValue();
			float novoPlafond = plafondAtual + fundos;
			Statement stat = con.createStatement();
			String query = "UPDATE utilizador SET Plafond = " + novoPlafond + " WHERE idUtilizador = " + getUserLogin();
			stat.executeUpdate(query);
			
		} catch(Exception e) {
			System.out.println("Error: " + e);
		}	
	}
	
	public void addWatchlist(String nome) {
		
		try {
			
			Stock s = YahooFinance.get(nome);
			
			stocks.put(nome, s);
			
		} catch(Exception e) {
			System.out.println("Error: " + e);
		}	
	}
	
	public void removeWatchlist(String nome) {
		
		try {
					
			stocks.remove(nome);
			
		} catch(Exception e) {
			System.out.println("Error: " + e);
		}	
	}
	
	public TableModel transformaTabela() throws SQLException, IOException {
		
		String ativos = new String(getAtivos());
		
		Vector<String> header = new Vector<String>();
		header.add("C�digo do Ativo");
		header.add("Nome do Ativo");
		header.add("Valor de Venda");
		header.add("Valor de Compra");
		header.add("Pre�o");
		header.add("Delta (%)");
		
		String rows[] = ativos.split("\n");
		
		Vector<Vector<String>> dados = new Vector<Vector<String>>();
		
		for(String row : rows) {
			row = row.trim();
			Vector<String> data = new Vector<String>();
			data.addAll(Arrays.asList(row.split("::")));
			dados.add(data);
		}
		
		DefaultTableModel at = new DefaultTableModel(dados, header);
		
		return at;
	}
	
	public TableModel resultSetToTableModel(ResultSet rs) {
        try {
            ResultSetMetaData metaData = (ResultSetMetaData) rs.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
            Vector columnNames = new Vector();

            // Get the column names
            for (int column = 0; column < numberOfColumns; column++) {
                columnNames.addElement(metaData.getColumnLabel(column + 1));
            }

            // Get all rows.
            Vector rows = new Vector();

            while (rs.next()) {
                Vector newRow = new Vector();

                for (int i = 1; i <= numberOfColumns; i++) {
                    newRow.addElement(rs.getObject(i));
                }

                rows.addElement(newRow);
            }

            return new DefaultTableModel(rows, columnNames);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	
	public float getPlafondInvestido(int id) {
		
		float plafond = 0;
		try {
			
			String query = "SELECT IFNULL(SUM(Montante), 0) FROM utilizador_has_ativo WHERE Utilizador_idUtilizador = " + userLogin;
			rs = st.executeQuery(query);
			rs.next();
			plafond = ((Number) rs.getObject(1)).floatValue();			
		} catch(Exception e) {
			System.out.println("Error: " + e);
		}
		return plafond;	
	}
	
	public float getPlafondInvestidoModalidade(int id, String modalidade) {
		
		float plafond = 0;
		try {
			
			String query = "SELECT IFNULL(SUM(Montante), 0) FROM utilizador_has_ativo WHERE Utilizador_idUtilizador = " + userLogin + " AND Estado = '" + modalidade + "'";
			rs = st.executeQuery(query);
			rs.next();
			plafond = ((Number) rs.getObject(1)).floatValue();			
		} catch(Exception e) {
			System.out.println("Error: " + e);
		}
		return plafond;	
	}
	
	public void updateCompra() throws SQLException, IOException {
		
		Statement s = con.createStatement();
		ResultSet r;
		String query = "SELECT a.Tipo, uha.Montante, a.ValorCompra, uha.LimiteLucro, uha.LimitePerda, uha.Ativo_idAtivo FROM utilizador_has_ativo AS uha\r\n" + 
				"INNER JOIN ativo AS a ON uha.Ativo_idAtivo = a.idAtivo\r\n" + 
				"WHERE uha.Utilizador_idUtilizador = " + getUserLogin();
		r = s.executeQuery(query);
		
		while(r.next()) {
			int id = ((Number) r.getObject(6)).intValue();
			float montante = ((Number) r.getObject(2)).floatValue();
			String tempAntigaCompra = ((String) r.getObject(3)).toString();
			float antigaCompra = Float.parseFloat(tempAntigaCompra);
			float ll = ((Number) r.getObject(4)).floatValue();
			float lp = ((Number) r.getObject(5)).floatValue();
			String codigo = ((String) r.getObject(1)).toString();
			BigDecimal temp = YahooFinance.get(codigo).getQuote().getAsk();
			float atualCompra = temp.floatValue();
			float antigo = montante * antigaCompra;
			float atual = montante * atualCompra;
			float resultante = atual - antigo;
			
			if(Math.abs(resultante) <= ll && Math.abs(resultante) >= lp) {
				String query2 = "UPDATE utilizador_has_ativo SET MontanteResultante = " + Math.abs(resultante) + " WHERE Utilizador_idUtilizador = " + getUserLogin() + " AND Ativo_idAtivo = " + id;
				Statement s1 = con.createStatement();
				s1.executeUpdate(query2);
			} else {
				fechaCompra(id);
				System.out.println("FECHOU");
			}
		}
	}

	public void updateVenda() throws SQLException, IOException {
		
		Statement s = con.createStatement();
		ResultSet r;
		String query = "SELECT a.Tipo, uha.Montante, a.ValorVenda, uha.LimiteLucro, uha.LimitePerda, uha.Ativo_idAtivo FROM utilizador_has_ativo AS uha\r\n" + 
				"INNER JOIN ativo AS a ON uha.Ativo_idAtivo = a.idAtivo\r\n" + 
				"WHERE uha.Utilizador_idUtilizador = " + getUserLogin();
		r = s.executeQuery(query);
		
		while(r.next()) {
			int id = ((Number) r.getObject(6)).intValue();
			float montante = ((Number) r.getObject(2)).floatValue();
			String tempAntigaCompra = ((String) r.getObject(3)).toString();
			float antigaCompra = Float.parseFloat(tempAntigaCompra);
			float ll = ((Number) r.getObject(4)).floatValue();
			float lp = ((Number) r.getObject(5)).floatValue();
			String codigo = ((String) r.getObject(1)).toString();
			BigDecimal temp = YahooFinance.get(codigo).getQuote().getBid();
			float atualCompra = temp.floatValue();
			float antigo = montante * antigaCompra;
			float atual = montante * atualCompra;
			float resultante = atual - antigo;
			
			if(Math.abs(resultante) <= ll && Math.abs(resultante) >= lp) {
				String query2 = "UPDATE utilizador_has_ativo SET MontanteResultante = " + Math.abs(resultante) + " WHERE Utilizador_idUtilizador = " + getUserLogin() + " AND Ativo_idAtivo = " + id;
				Statement s1 = con.createStatement();
				s1.executeUpdate(query2);
			} else {
				fechaCompra(id);
			}
		}
	}
	
	public void addAlerta(String codigo, String modalidade, float valor) {
		
		try {
			
			Statement st = con.createStatement();
			ResultSet rs;
			rs = st.executeQuery("SELECT COUNT(idAlerta) FROM alerta");
			rs.next();
			int nAtivos = ((Number) rs.getObject(1)).intValue() + 1;
	
			Statement st4 = con.createStatement();
			
			String query2 = "INSERT INTO alerta VALUES (" + nAtivos + ",'" + codigo + "','" + modalidade +  "','" + valor + "')";
			
			st4.executeUpdate(query2);
			
			ResultSet resultset;
				
			resultset = st.executeQuery("SELECT idAlerta FROM alerta WHERE Tipo = '" + codigo + "'");
			resultset.next();
			int idAlerta = ((Number) resultset.getObject(1)).intValue();
			
			Statement st6 = con.createStatement();
			
			st6.executeUpdate("INSERT INTO utilizador_has_alerta(utilizador_idUtilizador,alerta_idAlerta) " + "VALUES (" + userLogin + "," + idAlerta + ")");
			
		} catch(Exception e) {
			System.out.println("Error: " + e);
		}
		
	}
	
	public String followStock() throws SQLException, IOException {
		
		StringBuilder sb = new StringBuilder();
		
		Statement s = con.createStatement();
		ResultSet rs;
		
		String query = "SELECT * FROM alerta AS a\r\n" + 
				"INNER JOIN utilizador_has_alerta AS uha ON a.idAlerta = uha.alerta_idAlerta\r\n" + 
				"WHERE uha.utilizador_idUtilizador = " + userLogin;
		
		rs = s.executeQuery(query);
		
		while(rs.next()) {
			String codigo = ((String) rs.getObject(2)).toString();
			String modalidade = ((String) rs.getObject(3)).toString();
			String tempValor = ((String) rs.getObject(4)).toString();
			float valor = Float.parseFloat(tempValor);
			if(modalidade.equals("Ask")) {
				BigDecimal temp = YahooFinance.get(codigo).getQuote().getAsk();
				float atual = temp.floatValue();
				if(valor <= atual) {
					sb.append("O ATIVO " + codigo + " ATINGIU OS " + atual + " $\n");
				}
			} else if(modalidade.equals("Bid")) {
				BigDecimal temp = YahooFinance.get(codigo).getQuote().getBid();
				float atual = temp.floatValue();
				if(valor <= atual) {
					sb.append("O ATIVO " + codigo + " ATINGIU OS " + atual + " $\n");
				}
			} else if(modalidade.equals("Price")) {
				BigDecimal temp = YahooFinance.get(codigo).getQuote().getPrice();
				float atual = temp.floatValue();
				if(valor <= atual) {
					sb.append("O ATIVO " + codigo + " ATINGIU OS " + atual + " $\n");
				}
			}
		}
		return sb.toString();
	}
	
	public void unfollowStock(String codigo) throws SQLException {
		
		Statement s = con.createStatement();
		ResultSet r;
		String query = "SELECT idAlerta FROM alerta AS a INNER JOIN utilizador_has_alerta AS uha ON a.idAlerta = uha.alerta_idAlerta WHERE utilizador_idUtilizador = " + userLogin + " AND a.Tipo = '" + codigo + "'";
		r = s.executeQuery(query);
		r.next();
		int id = ((Number) r.getObject(1)).intValue();
		
		Statement st = con.createStatement();
		
		String query2 = "DELETE FROM utilizador_has_alerta WHERE alerta_idAlerta = " + id + " AND utilizador_idUtilizador = " + userLogin;
		
		st.executeUpdate(query2);
		
	}
	
	@Override
	public void registerObserver(Menu m) {
		// TODO Auto-generated method stub
		listOfMenu.add(m);
	}

	@Override
	public void removeObserver(Menu m) {
		// TODO Auto-generated method stub
		listOfMenu.remove(m);
	}

	@Override
	public void notifyObservers() {
		// TODO Auto-generated method stub
		for(Menu m : listOfMenu) {
			try {
				m.update(tm, this.isChange());
			} catch (SQLException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public Map<String, Float> getAtivosAlerta() {
		return ativosAlerta;
	}

	public void setAtivosAlerta(Map<String, Float> ativosAlerta) {
		this.ativosAlerta = ativosAlerta;
	}

	@Override
	public void setChange(String change) {
		// TODO Auto-generated method stub
		this.change = change;
	}
}
