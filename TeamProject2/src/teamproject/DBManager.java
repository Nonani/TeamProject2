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
				System.out.println("DB 접속 성공 "); 
			} 
		} catch (ClassNotFoundException e) {
			System.out.println("[JDBC Connector Driver Error : " + e.getMessage() + "]");
			System.out.println("JDBC 드라이버 로드 실패 ");
		}catch (SQLException e) {
			// TODO: handle exception
			System.out.println("DB 접속 실패");
			e.printStackTrace();
		}
	}
	protected abstract void readDB();	//데이터베이스 연결 후 각각의 자식 노드에 있는 리스트에 모두 저장
	public abstract void showAll();	//리스트에 있는 모든 값 출력
	
	

}

