package teamproject;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class DBManager {
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	protected void connectDB() {
		try { 
			Class.forName("org.mariadb.jdbc.Driver"); 
			conn = DriverManager.getConnection( "jdbc:mariadb://127.0.0.1:5306/teamproject_db", "root", "1234"); 
			if( conn != null ) 
			{ 
				System.out.println("DB ���� ���� "); 
			} 
		} catch (ClassNotFoundException e) {
			System.out.println("[JDBC Connector Driver Error : " + e.getMessage() + "]");
			System.out.println("JDBC ����̹� �ε� ���� ");
		}catch (SQLException e) {
			// TODO: handle exception
			System.out.println("DB ���� ����");
			e.printStackTrace();
		}
	}
	protected abstract void readDB();	//�����ͺ��̽� ���� �� ������ �ڽ� ��忡 �ִ� ����Ʈ�� ��� ����
	public abstract void showAll();	//����Ʈ�� �ִ� ��� �� ���
	
	

}

