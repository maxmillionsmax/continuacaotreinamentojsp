package connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class SingleConnectionBanco {
	
	private static String banco = "jdbc:postgresql://ec2-3-212-45-192.compute-1.amazonaws.com:5432/d8s0t8rpelnar3?sslmode=require&autoReconnect=true";
	private static String user = "swhmnngovgocdq";
	private static String senha = "9b5e2c9f267ed0d6adb050871a8630776a542f8fded2db85917f5b98a8e55218";
	private static Connection connection= null;
	
	public static Connection getConnection() {
		return connection;
	}
	
	static {
		conectar();
	}
	
	public SingleConnectionBanco() {
		conectar();
	}
	
	public static void conectar() {
		try {
			
			if (connection == null) {
				Class.forName("org.postgresql.Driver");
				connection = DriverManager.getConnection(banco, user, senha);
				connection.setAutoCommit(false);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
