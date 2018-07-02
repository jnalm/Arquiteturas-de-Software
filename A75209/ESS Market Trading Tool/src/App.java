
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.swing.table.TableModel;

public interface App {
	public void registerObserver(Menu m);
	public void removeObserver(Menu m);
	public void notifyObservers();
	public TableModel transformaTabela() throws SQLException, IOException;
	public void setTm(TableModel tm);
	public String followStock() throws SQLException, IOException;
	public void setChange(String change);
	public Map<String, Float> getAtivosAlerta();
	public String isChange();
}
