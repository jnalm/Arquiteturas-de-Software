import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Ativo implements Mercado, BD {

	private Connection con;
	private ResultSet rs;
	private int userLogin;
	
	public Ativo() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "1234");
			
		} catch(Exception e) {
			System.out.println("Error: " + e);
		}
	}
	
	public void setLogin (int userLogin) {
		this.userLogin = userLogin;
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
	
	public void addNewAtivo(String nome) throws SQLException, IOException {
		ResultSet rs = getBD("SELECT COUNT(idAtivo) FROM ativo");
		rs.next();
		int nAtivos = ((Number) rs.getObject(1)).intValue() + 1;
		String bid = YahooFinance.get(nome).getQuote().getBid().toString();
		String ask = YahooFinance.get(nome).getQuote().getAsk().toString();
		String fullname = YahooFinance.get(nome).getName();
		alterBD("INSERT INTO ativo VALUES (" + nAtivos + ",'" + fullname + "','" + nome +  "','" + ask + "','" + bid + "')");
		ResultSet rst2 = getBD("SELECT idAtivo FROM ativo WHERE Tipo = '" + nome + "'");
		rst2.next();
		int idAtivo = ((Number) rst2.getObject(1)).intValue();
		alterBD("INSERT INTO ativo_has_portfolio(Ativo_idAtivo,Portfolio_idPortfolio) " + "VALUES (" + idAtivo + "," + userLogin + ")");
	}
	
	public void addOldAtivo(String nome) throws SQLException, IOException {
		ResultSet rs = getBD("SELECT idAtivo FROM ativo WHERE Tipo = '" + nome + "'");
		rs.next();
		int idAtivo = ((Number) rs.getObject(1)).intValue();
		String bid = YahooFinance.get(nome).getQuote().getBid().toString();
		String ask = YahooFinance.get(nome).getQuote().getAsk().toString();
		alterBD("UPDATE ativo SET ValorVenda = '" + bid + "', ValorCompra = '" + ask + "' WHERE idAtivo = " + idAtivo);
		alterBD("INSERT INTO ativo_has_portfolio(Ativo_idAtivo,Portfolio_idPortfolio) " + "VALUES (" + idAtivo + "," + userLogin + ")");
	}
	
	public void addAtivo(String nome) {
		try {
			ResultSet rst = getBD("SELECT Tipo FROM ativo WHERE Tipo = '" + nome + "'");
			if(rst.next() == false) {
				addNewAtivo(nome);
			} else {
				addOldAtivo(nome);
			}
		} catch(Exception e) {
			System.out.println("Error: " + e);
		}
	}
	
	public void removeAtivo(int id) {
		try {
			alterBD("DELETE FROM ativo_has_portfolio WHERE Ativo_idAtivo = " + id + " AND Portfolio_idPortfolio = " + userLogin);
		} catch(Exception e) {
			System.out.println("Error: " + e);
		}
	}
	
	public ResultSet getPortfolio() {
		try {
			rs = getBD("SELECT at.idAtivo, at.Nome, at.Tipo, at.ValorCompra, at.ValorVenda FROM ativo AS at INNER JOIN ativo_has_portfolio AS ahp ON at.idAtivo = ahp.Ativo_idAtivo WHERE ahp.Portfolio_idPortfolio = " + userLogin);
		} catch(Exception e) {
			System.out.println("Error: " + e);
		}
		return rs;
	}
	
	public ResultSet getAtivosCompra() {
		try {
			rs = getBD("SELECT uha.Ativo_idAtivo, at.Nome, at.ValorVenda, at.ValorCompra, uha.LimiteLucro, uha.LimitePerda, uha.Montante, uha.MontanteResultante FROM utilizador_has_ativo AS uha INNER JOIN ativo AS at ON uha.Ativo_idAtivo = at.idAtivo WHERE uha.Utilizador_idUtilizador = " + userLogin + " AND uha.Estado = 'COMPRA'");
		} catch(Exception e) {
			System.out.println("Error: " + e);
		}
		return rs;
	}
	
	public ResultSet getAtivosVenda() {
		try {
			rs = getBD("SELECT uha.Ativo_idAtivo, at.Nome, at.ValorVenda, at.ValorCompra, uha.LimiteLucro, uha.LimitePerda, uha.Montante, uha.MontanteResultante FROM utilizador_has_ativo AS uha INNER JOIN ativo AS at ON uha.Ativo_idAtivo = at.idAtivo WHERE uha.Utilizador_idUtilizador = " + userLogin + " AND uha.Estado = 'VENDA'");
		} catch(Exception e) {
			System.out.println("Error: " + e);
		}
		return rs;
	}
	
	public void addAlerta(String codigo, String modalidade, float valor) {
		try {
			ResultSet rs;
			rs = getBD("SELECT COUNT(idAlerta) FROM alerta");
			rs.next();
			int nAtivos = ((Number) rs.getObject(1)).intValue() + 1;
			alterBD("INSERT INTO alerta VALUES (" + nAtivos + ",'" + codigo + "','" + modalidade +  "','" + valor + "')");
			ResultSet resultset;
			resultset = getBD("SELECT idAlerta FROM alerta WHERE Tipo = '" + codigo + "'");
			int idAlerta = ((Number) resultset.getObject(1)).intValue();
			alterBD("INSERT INTO utilizador_has_alerta(utilizador_idUtilizador,alerta_idAlerta) " + "VALUES (" + userLogin + "," + idAlerta + ")");
		} catch(Exception e) {
			System.out.println("Error: " + e);
		}
	}
	
}
