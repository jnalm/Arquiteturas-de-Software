
import java.sql.Connection;
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

public class AppESS implements App {
	
	private TableModel tm;
	private String change = "RECEBA NOTIFICA��ES ACERCA DOS VALORES DOS SEUS ATIVOS";
	
	public String isChange() {
		return change;
	}

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

	@Override
	public String toString() {
		return "AppESS [userLogin=" + userLogin + ", login=" + login + "]";
	}
	
	public ResultSet getBD(String query) throws SQLException {
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		return rs;
	}
	
	public void alterBD(String query) throws SQLException {
		Statement st = con.createStatement();
		st.executeUpdate(query);
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
			String ask = st.getQuote().getAsk().toString();
			String bid = st.getQuote().getBid().toString();
			String price = st.getQuote().getPrice().toString();
			String prev = st.getQuote().getChangeInPercent().toString();
			sb.append(key + "::" + name + "::" + ask + "::" + bid + "::" + price + "::" + prev + "\n");
		}
		
		return sb.toString();
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
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
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
	
	public String followStock() throws SQLException, IOException {
		StringBuilder sb = new StringBuilder();
		ResultSet rs;
		rs = getBD("SELECT * FROM alerta AS a\r\n" + 
				"INNER JOIN utilizador_has_alerta AS uha ON a.idAlerta = uha.alerta_idAlerta\r\n" + 
				"WHERE uha.utilizador_idUtilizador = " + userLogin);
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
		ResultSet r;
		r = getBD("SELECT idAlerta FROM alerta AS a INNER JOIN utilizador_has_alerta AS uha ON a.idAlerta = uha.alerta_idAlerta WHERE utilizador_idUtilizador = " + userLogin + " AND a.Tipo = '" + codigo + "'");
		r.next();
		int id = ((Number) r.getObject(1)).intValue();
		alterBD( "DELETE FROM utilizador_has_alerta WHERE alerta_idAlerta = " + id + " AND utilizador_idUtilizador = " + userLogin);
	}
	
	@Override
	public void registerObserver(Menu m) {
		listOfMenu.add(m);
	}

	@Override
	public void removeObserver(Menu m) {
		listOfMenu.remove(m);
	}

	@Override
	public void notifyObservers() {
		for(Menu m : listOfMenu) {
			try {
				m.update(tm, this.isChange());
			} catch (SQLException | IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void setChange(String change) {
		this.change = change;
	}
}
