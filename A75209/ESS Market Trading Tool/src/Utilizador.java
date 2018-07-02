import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Utilizador {
	private int id;
	private String nome;
	private String username;
	private String password;
	private String pais;
	private String email;
	private String dob;
	private float plafond;
	private int idPortfolio;
	private Connection con;
	private Statement st;
	private ResultSet rs;
	
	public Utilizador() {
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	public String getDob() {
		return dob;
	}
	public int getIdPortfolio() {
		return idPortfolio;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public float getPlafond() {
		return plafond;
	}
	public void setPlafond(float plafond) {
		this.plafond = plafond;
	}
	public void setIdPortfolio(int idPortfolio) {
		this.idPortfolio = idPortfolio;
	}
	@Override
	public String toString() {
		return "Utilizador [id=" + id + ", nome=" + nome + ", username=" + username + ", password=" + password
				+ ", pais=" + pais + ", dob=" + dob + ", plafond=" + plafond + ", idportfolio=" + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dob == null) ? 0 : dob.hashCode());
		result = prime * result + id;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((pais == null) ? 0 : pais.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + Float.floatToIntBits(plafond);
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		Utilizador other = (Utilizador) obj;
		if (dob == null) {
			if (other.dob != null)
				return false;
		} else if (!dob.equals(other.dob))
			return false;
		if (id != other.id)
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (pais == null) {
			if (other.pais != null)
				return false;
		} else if (!pais.equals(other.pais))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (Float.floatToIntBits(plafond) != Float.floatToIntBits(other.plafond))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	public void setEmail(String email) {
		this.email = email;		
	}
	
	
}
