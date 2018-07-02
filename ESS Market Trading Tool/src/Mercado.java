import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface Mercado {
	public void addNewAtivo(String nome) throws SQLException, IOException;
	public void addOldAtivo(String nome) throws SQLException, IOException;
	public void addAtivo(String nome);
	public void removeAtivo(int id);
	public ResultSet getPortfolio();
	public ResultSet getAtivosCompra();
	public ResultSet getAtivosVenda();
	public void addAlerta(String codigo, String modalidade, float valor);
	
}
