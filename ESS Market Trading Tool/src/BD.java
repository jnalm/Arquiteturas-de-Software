import java.sql.ResultSet;
import java.sql.SQLException;

public interface BD {
	public ResultSet getBD (String query) throws SQLException;
	public void alterBD (String query) throws SQLException;
}
