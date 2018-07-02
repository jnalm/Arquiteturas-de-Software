import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;

public class Ativo {
	private int id;
	private String nome;
	private String tipo;
	private String estado;
	private float valorCompra;
	private float valorVenda;
	private float limiteLucro;
	private float limitePerda;
	private float montante;
	private Map<Ativo, Utilizador> ativosNegociados;
	private Connection con;
	private Statement st;
	private ResultSet rs;
	
	public Ativo() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "1234");
			st = con.createStatement();
			
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
				String pais = rs.getString("País");
				Float plafond = rs.getFloat("Plafond");
				System.out.println(name + username + password + email + pais + dob + plafond);
				
			}
			
		} catch(Exception e) {
			System.out.println("Error: " + e);
		}
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public float getValorCompra() {
		return valorCompra;
	}
	public void setValorCompra(float valorCompra) {
		this.valorCompra = valorCompra;
	}
	public float getValorVenda() {
		return valorVenda;
	}
	public void setValorVenda(float valorVenda) {
		this.valorVenda = valorVenda;
	}
	public float getLimiteLucro() {
		return limiteLucro;
	}
	public void setLimiteLucro(float limiteLucro) {
		this.limiteLucro = limiteLucro;
	}
	public float getLimitePerda() {
		return limitePerda;
	}
	public void setLimitePerda(float limitePerda) {
		this.limitePerda = limitePerda;
	}
	public float getMontante() {
		return montante;
	}
	public void setMontante(float montante) {
		this.montante = montante;
	}
	public Map<Ativo, Utilizador> getAtivosNegociados() {
		return ativosNegociados;
	}
	public void setAtivosNegociados(Map<Ativo, Utilizador> ativosNegociados) {
		this.ativosNegociados = ativosNegociados;
	}
	@Override
	public String toString() {
		return "Ativo [id=" + id + ", nome=" + nome + ", tipo=" + tipo + ", estado=" + estado + ", valorCompra="
				+ valorCompra + ", valorVenda=" + valorVenda + ", limiteLucro=" + limiteLucro + ", limitePerda="
				+ limitePerda + ", montante=" + montante + ", ativosNegociados=" + ativosNegociados + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ativosNegociados == null) ? 0 : ativosNegociados.hashCode());
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
		result = prime * result + id;
		result = prime * result + Float.floatToIntBits(limiteLucro);
		result = prime * result + Float.floatToIntBits(limitePerda);
		result = prime * result + Float.floatToIntBits(montante);
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		result = prime * result + Float.floatToIntBits(valorCompra);
		result = prime * result + Float.floatToIntBits(valorVenda);
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ativo other = (Ativo) obj;
		if (ativosNegociados == null) {
			if (other.ativosNegociados != null)
				return false;
		} else if (!ativosNegociados.equals(other.ativosNegociados))
			return false;
		if (estado == null) {
			if (other.estado != null)
				return false;
		} else if (!estado.equals(other.estado))
			return false;
		if (id != other.id)
			return false;
		if (Float.floatToIntBits(limiteLucro) != Float.floatToIntBits(other.limiteLucro))
			return false;
		if (Float.floatToIntBits(limitePerda) != Float.floatToIntBits(other.limitePerda))
			return false;
		if (Float.floatToIntBits(montante) != Float.floatToIntBits(other.montante))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		if (Float.floatToIntBits(valorCompra) != Float.floatToIntBits(other.valorCompra))
			return false;
		if (Float.floatToIntBits(valorVenda) != Float.floatToIntBits(other.valorVenda))
			return false;
		return true;
	}
	
	
	
}
