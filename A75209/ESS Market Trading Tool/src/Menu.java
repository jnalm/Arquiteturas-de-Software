import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.swing.table.TableModel;

public interface Menu {
	public void update(TableModel tm, String change) throws SQLException, IOException;
	public void setMenuprincipal(String menuprincipal);
	public String getMenuprincipal();
	public void setConnect(AppESS connect);
}
