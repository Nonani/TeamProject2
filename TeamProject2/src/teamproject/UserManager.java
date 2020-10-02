package teamproject;

import java.sql.SQLException;
import java.util.ArrayList;

import teamassignment.User;

public class UserManager extends DBManager {

	ArrayList<User> u_list = new ArrayList<User>();
	
	public UserManager() {
		// TODO Auto-generated constructor stub
		connectDB();
		readDB();
	}
	
	@Override
	protected void readDB() {
		// TODO Auto-generated method stub
		String sql = "select * from user";
		if(conn!=null)
		{
			try {
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				while(rs.next())
				{
					u_list.add(new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5)));
				} 
			} catch (SQLException e) {
				// TODO: handle exception
				System.out.println("유저테이블 읽어오기 실패!");
				e.printStackTrace();
			}
		}
	}

	@Override
	public void showAll() {
		// TODO Auto-generated method stub
		for(int i=0;i<u_list.size();i++) {
			System.out.println(u_list.get(i));
		}

	}

}
