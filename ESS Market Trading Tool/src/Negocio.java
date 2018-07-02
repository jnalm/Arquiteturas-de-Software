import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Negocio implements Negociacao, BD {
	
	private Connection con;
	private ResultSet rs;
	private int userLogin;
	
	public Negocio() throws SQLException, ClassNotFoundException {
	
		Class.forName("com.mysql.jdbc.Driver");
	
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?useSSL=true", "root", "1234");
	}
	
	public void setLogin (int userLogin) {
		this.userLogin = userLogin;
	}
	
	public ResultSet getBD (String query) throws SQLException {
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		return rs;
	}
	
	public void alterBD (String query) throws SQLException {
		Statement st = con.createStatement();
		st.executeUpdate(query);
	}
	
	public float getPlafond () {
		float plafond = 0;
		try {
			rs = getBD("SELECT Plafond FROM utilizador WHERE idUtilizador = " + userLogin);
			rs.next();
			plafond = ((Number) rs.getObject(1)).floatValue();
		} catch(Exception e) {
			System.out.println("Error: " + e);
		}
		return plafond;	
	}
	
	public void abrirNewCompra (String nome, float montante, float ll, float lp) throws SQLException, IOException {
		ResultSet rs1;
		rs1 = getBD("SELECT COUNT(idAtivo) FROM ativo");
		rs1.next();
		int nAtivos = ((Number) rs1.getObject(1)).intValue() + 1;
		String fullname = YahooFinance.get(nome).getName();
		String bid = YahooFinance.get(nome).getQuote().getBid().toString();
		String ask = YahooFinance.get(nome).getQuote().getAsk().toString();
		alterBD("INSERT INTO ativo VALUES (" + nAtivos + ",'" + fullname + "','" + nome +  "','" + ask + "','" + bid + "')");
		ResultSet rs2;
		rs2 = getBD("SELECT idAtivo FROM ativo WHERE Tipo = '" + nome + "'");
		rs2.next();
		int idAtivo = ((Number) rs2.getObject(1)).intValue();
		alterBD("INSERT INTO utilizador_has_ativo(Utilizador_idUtilizador, Ativo_idAtivo, MontanteResultante, Estado, Montante, LimiteLucro, LimitePerda) VALUES (" + userLogin + "," + idAtivo + "," + null + ", 'COMPRA', " + montante + "," + ll + "," + lp + ")");
		rs = getBD("SELECT Plafond FROM utilizador WHERE idUtilizador = " + userLogin);
		rs.next();
		float plafondAnterior = ((Number) rs.getObject(1)).floatValue();
		float plafondResultante = plafondAnterior - montante; 
		alterBD("UPDATE utilizador SET Plafond = " + plafondResultante + " WHERE idUtilizador = " + userLogin);
	}
	
	public void abrirOldCompra (String nome, float montante, float ll, float lp) throws SQLException, IOException {
		ResultSet rs3;
		rs3 = getBD("SELECT idAtivo FROM ativo WHERE Tipo = '" + nome + "'");
		rs3.next();
		int idAtivo = ((Number) rs3.getObject(1)).intValue();
		String bid = YahooFinance.get(nome).getQuote().getBid().toString();
		String ask = YahooFinance.get(nome).getQuote().getAsk().toString();
		alterBD("UPDATE ativo SET ValorCompra = '" + ask + "', ValorVenda = '" + bid + "' WHERE idAtivo = " + idAtivo);
		ResultSet rs4;
		alterBD("INSERT INTO utilizador_has_ativo(Utilizador_idUtilizador, Ativo_idAtivo, MontanteResultante, Estado, Montante, LimiteLucro, LimitePerda) VALUES (" + userLogin + "," + idAtivo + "," + null + ", 'COMPRA', " + montante + "," + ll + "," + lp + ")");
		rs4 = getBD("SELECT Plafond FROM utilizador WHERE idUtilizador = " + userLogin);
		rs4.next();
		float plafondAnterior = ((Number) rs4.getObject(1)).floatValue();
		float plafondResultante = plafondAnterior - montante; 
		alterBD("UPDATE utilizador SET Plafond = " + plafondResultante + " WHERE idUtilizador = " + userLogin);
	}
	
	public void abrirCompra(String nome, float montante, float ll, float lp) {
		try {
			ResultSet rs;
			rs = getBD("SELECT idAtivo FROM ativo WHERE Tipo = '" + nome + "'");
			if(rs.next() == false) {
				abrirNewCompra(nome, montante, ll, lp);
			} else {
				abrirOldCompra(nome, montante, ll, lp);
			}
		} catch(Exception e) {
			System.out.println("Error: " + e);
		}
		
	}
	
	public void abrirNewVenda (String nome, float montante, float ll, float lp) throws SQLException, IOException {
		ResultSet rs1;
		rs1 = getBD("SELECT COUNT(idAtivo) FROM ativo");
		rs1.next();
		int nAtivos = ((Number) rs1.getObject(1)).intValue() + 1;
		String fullname = YahooFinance.get(nome).getName();
		String bid = YahooFinance.get(nome).getQuote().getBid().toString();
		String ask = YahooFinance.get(nome).getQuote().getAsk().toString();
		alterBD("INSERT INTO ativo VALUES (" + nAtivos + ",'" + fullname + "','" + nome +  "','" + ask + "','" + bid + "')");
		ResultSet rs2;
		rs2 = getBD("SELECT idAtivo FROM ativo WHERE Tipo = '" + nome + "'");
		rs2.next();
		int idAtivo = ((Number) rs2.getObject(1)).intValue();
		alterBD("INSERT INTO utilizador_has_ativo(Utilizador_idUtilizador, Ativo_idAtivo, MontanteResultante, Estado, Montante, LimiteLucro, LimitePerda) VALUES (" + userLogin + "," + idAtivo + "," + null + ", 'VENDA', " + montante + "," + ll + "," + lp + ")");
		rs = getBD("SELECT Plafond FROM utilizador WHERE idUtilizador = " + userLogin);
		rs.next();
		float plafondAnterior = ((Number) rs.getObject(1)).floatValue();
		float plafondResultante = plafondAnterior - montante; 
		alterBD("UPDATE utilizador SET Plafond = " + plafondResultante + " WHERE idUtilizador = " + userLogin);
	}
	
	public void abrirOldVenda (String nome, float montante, float ll, float lp) throws SQLException, IOException {
		ResultSet resultset;
		resultset = getBD("SELECT idAtivo FROM ativo WHERE Tipo = '" + nome + "'");
		resultset.next();
		int idAtivo = ((Number) resultset.getObject(1)).intValue();
		String bid = YahooFinance.get(nome).getQuote().getBid().toString();
		String ask = YahooFinance.get(nome).getQuote().getAsk().toString();
		alterBD("UPDATE ativo SET ValorVenda = '" + bid + "', ValorCompra = '" + ask + "' WHERE idAtivo = " + idAtivo);
		ResultSet rs3;
		alterBD("INSERT INTO utilizador_has_ativo(Utilizador_idUtilizador, Ativo_idAtivo, MontanteResultante, Estado, Montante, LimiteLucro, LimitePerda) VALUES (" + userLogin + "," + idAtivo + "," + null + ", 'VENDA', " + montante + "," + ll + "," + lp + ")");
		rs3 = getBD("SELECT Plafond FROM utilizador WHERE idUtilizador = " + userLogin);
		rs3.next();
		float plafondAnterior = ((Number) rs3.getObject(1)).floatValue();
		float plafondResultante = plafondAnterior - montante; 
		alterBD("UPDATE utilizador SET Plafond = " + plafondResultante + " WHERE idUtilizador = " + userLogin);
	}
	
	public void abrirVenda(String nome, float montante, float ll, float lp) {
		try {
			ResultSet rs;
			rs = getBD("SELECT idAtivo FROM ativo WHERE Tipo = '" + nome + "'");
			if(rs.next() == false) {
				abrirNewVenda(nome, montante, ll, lp);
			} else {
				abrirOldVenda(nome, montante, ll, lp);
			}
		} catch(Exception e) {
			System.out.println("Error: " + e);
		}
		
	}
	
	public void fechaCompra(int idAtivo) {
		try {
			ResultSet rs;
			rs = getBD("SELECT ValorCompra FROM ativo WHERE idAtivo = " + idAtivo);
			rs.next();
			float valorCompra = rs.getFloat(1);
 			ResultSet rs2;
 			rs2 = getBD("SELECT Montante FROM utilizador_has_ativo WHERE Ativo_idAtivo = " + idAtivo + " AND Utilizador_idUtilizador = " + userLogin);
 			rs2.next();
			float montanteInvestido = ((Number) rs2.getObject(1)).floatValue();
			ResultSet rs3;
			rs3 = getBD("SELECT Tipo FROM ativo WHERE idAtivo = " + idAtivo);
			rs3.next();
			String tipo = ((String) rs3.getObject(1)).toString();
			String ask = YahooFinance.get(tipo).getQuote().getAsk().toString();
			float priceAtual = Float.parseFloat(ask);
			float quantiaInicial = montanteInvestido * valorCompra;
			float quantiaAtual = montanteInvestido * priceAtual;
			float addPlafond = quantiaAtual - quantiaInicial;
			float lucros = addPlafond + montanteInvestido;
			ResultSet rs4;
			rs4 = getBD("SELECT Plafond FROM utilizador WHERE idUtilizador = " + userLogin);
			rs4.next();
			float plafondAtual = ((Number) rs4.getObject(1)).floatValue();
			float setPlafond = plafondAtual + lucros;
			alterBD("UPDATE utilizador SET Plafond = " + setPlafond + " WHERE idUtilizador = " + userLogin);
			alterBD("DELETE FROM utilizador_has_ativo WHERE Utilizador_idUtilizador = " + userLogin + " AND Ativo_idAtivo = " + idAtivo);
		} catch(Exception e) {
			System.out.println("Error: " + e);
		}
	}
	
	public void fechaVenda(int idAtivo) {
		try {
			ResultSet rs;
			rs = getBD("SELECT ValorVenda FROM ativo WHERE idAtivo = " + idAtivo);
			rs.next();
			float valorVenda = rs.getFloat(1);
 			ResultSet rs2;
 			rs2 = getBD("SELECT Montante FROM utilizador_has_ativo WHERE Ativo_idAtivo = " + idAtivo + " AND Utilizador_idUtilizador = " + userLogin);
 			rs2.next();
			float montanteInvestido = ((Number) rs2.getObject(1)).floatValue();
			ResultSet rs3;
			rs3 = getBD("SELECT Tipo FROM ativo WHERE idAtivo = " + idAtivo);
			rs3.next();
			String tipo = ((String) rs3.getObject(1)).toString();
			String bid = YahooFinance.get(tipo).getQuote().getBid().toString();
			float priceAtual = Float.parseFloat(bid);
			float quantiaInicial = montanteInvestido * valorVenda;
			float quantiaAtual = montanteInvestido * priceAtual;
			float addPlafond = quantiaAtual - quantiaInicial;
			ResultSet rs4;
			rs4 = getBD("SELECT Plafond FROM utilizador WHERE idUtilizador = " + userLogin);
			rs4.next();
			float plafondAtual = ((Number) rs4.getObject(1)).floatValue();
			float lucros = addPlafond + montanteInvestido;
			float setPlafond = plafondAtual + lucros;
			alterBD("UPDATE utilizador SET Plafond = " + setPlafond + " WHERE idUtilizador = " + userLogin);
			alterBD("DELETE FROM utilizador_has_ativo WHERE Utilizador_idUtilizador = " + userLogin + " AND Ativo_idAtivo = " + idAtivo);
		} catch(Exception e) {
			System.out.println("Error: " + e);
		}
	}
	
	public void addFundos(float fundos) {
		try {
			ResultSet rst;
			rst = getBD("SELECT Plafond FROM utilizador WHERE idUtilizador = " + userLogin);
			rst.next();
			float plafondAtual = ((Number) rst.getObject(1)).floatValue();
			float novoPlafond = plafondAtual + fundos;
			alterBD("UPDATE utilizador SET Plafond = " + novoPlafond + " WHERE idUtilizador = " + userLogin);
		} catch(Exception e) {
			System.out.println("Error: " + e);
		}	
	}
	
	public float getPlafondInvestido() {
		float plafond = 0;
		try {
			ResultSet rs = getBD("SELECT IFNULL(SUM(Montante), 0) FROM utilizador_has_ativo WHERE Utilizador_idUtilizador = " + userLogin);
			rs.next();
			plafond = ((Number) rs.getObject(1)).floatValue();			
		} catch(Exception e) {
			System.out.println("Error: " + e);
		}
		return plafond;	
	}
	
	public float getPlafondInvestidoModalidade(String modalidade) {
		float plafond = 0;
		try {
			ResultSet rs = getBD("SELECT IFNULL(SUM(Montante), 0) FROM utilizador_has_ativo WHERE Utilizador_idUtilizador = " + userLogin + " AND Estado = '" + modalidade + "'");
			rs.next();
			plafond = ((Number) rs.getObject(1)).floatValue();			
		} catch(Exception e) {
			System.out.println("Error: " + e);
		}
		return plafond;	
	}
	
	public void updateCompra() throws SQLException, IOException {
		ResultSet r;
		r = getBD("SELECT a.Tipo, uha.Montante, a.ValorCompra, uha.LimiteLucro, uha.LimitePerda, uha.Ativo_idAtivo FROM utilizador_has_ativo AS uha\r\n" + 
				"INNER JOIN ativo AS a ON uha.Ativo_idAtivo = a.idAtivo\r\n" + 
				"WHERE uha.Utilizador_idUtilizador = " + userLogin);
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
				alterBD("UPDATE utilizador_has_ativo SET MontanteResultante = " + Math.abs(resultante) + " WHERE Utilizador_idUtilizador = " + userLogin + " AND Ativo_idAtivo = " + id);
			} else {
				fechaCompra(id);
				System.out.println("FECHOU");
			}
		}
	}
	
	public void updateVenda() throws SQLException, IOException {
		ResultSet r;
		r = getBD("SELECT a.Tipo, uha.Montante, a.ValorVenda, uha.LimiteLucro, uha.LimitePerda, uha.Ativo_idAtivo FROM utilizador_has_ativo AS uha\r\n" + 
				"INNER JOIN ativo AS a ON uha.Ativo_idAtivo = a.idAtivo\r\n" + 
				"WHERE uha.Utilizador_idUtilizador = " + userLogin);
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
				alterBD("UPDATE utilizador_has_ativo SET MontanteResultante = " + Math.abs(resultante) + " WHERE Utilizador_idUtilizador = " + userLogin + " AND Ativo_idAtivo = " + id);
			} else {
				fechaCompra(id);
			}
		}
	}
	
}