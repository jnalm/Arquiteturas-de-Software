import java.io.IOException;
import java.sql.SQLException;

public class GetTheStock implements Runnable {
			
		private App connect;
		
		public GetTheStock(App connect) {
			this.connect = connect;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(true) {
				try {
					Thread.sleep(5000);
				} 
				catch(InterruptedException e) {}
				try {
					connect.setChange(connect.followStock());
					connect.setTm(connect.transformaTabela());
				} catch (SQLException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
}
