import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;

public class TestConnection {
	
	public static void main(String[] args) {
		try {
			Class.forName("org.postgresql.Driver");
			
			String url = "jdbc:postgresql://localhost:5433/PubHub";
			String user = "postgres";
			String pass = "postgres";
			
			Connection conn = DriverManager.getConnection(url,user,pass);
			DatabaseMetaData meta = conn.getMetaData();
			System.out.println(meta.getDatabaseProductName());
			
			conn.close();	
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}	
}
