import java.io.IOException;
import java.sql.SQLException;

public interface Negociacao {
	public float getPlafond();
	public void abrirNewCompra (String nome, float montante, float ll, float lp) throws SQLException, IOException;
	public void abrirOldCompra (String nome, float montante, float ll, float lp) throws SQLException, IOException;
	public void abrirCompra(String nome, float montante, float ll, float lp);
	public void abrirNewVenda (String nome, float montante, float ll, float lp) throws SQLException, IOException;
	public void abrirOldVenda (String nome, float montante, float ll, float lp) throws SQLException, IOException;
	public void abrirVenda(String nome, float montante, float ll, float lp);
	public void fechaCompra(int idAtivo);
	public void fechaVenda(int idAtivo);
	public void addFundos(float fundos);
	public float getPlafondInvestido();
	public float getPlafondInvestidoModalidade(String modalidade);
	public void updateCompra() throws SQLException, IOException;
	public void updateVenda() throws SQLException, IOException;
}
