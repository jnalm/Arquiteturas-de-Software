import java.awt.List;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Portfolio {
	private int id;
	private List portfolio;
	private Connection con;
	private Statement st;
	private ResultSet rs;
	
	public Portfolio() {
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
	public List getPortfolio() {
		return portfolio;
	}
	public void setPortfolio(List portfolio) {
		this.portfolio = portfolio;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((portfolio == null) ? 0 : portfolio.hashCode());
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
		Portfolio other = (Portfolio) obj;
		if (id != other.id)
			return false;
		if (portfolio == null) {
			if (other.portfolio != null)
				return false;
		} else if (!portfolio.equals(other.portfolio))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Portfolio [id=" + id + ", portfolio=" + portfolio + "]";
	}
}
