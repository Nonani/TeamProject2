package teamproject;

import java.sql.SQLException;
import java.util.ArrayList;

public class TheaterManager extends DBManager {

	ArrayList<Theater> t_list = new ArrayList<Theater>();
	public TheaterManager() {
		// TODO Auto-generated constructor stub
		connectDB();
		readDB();
	}
	@Override
	protected void readDB() {
		// TODO Auto-generated method stub
//		String sql = "select * from theater";
//		if(conn!=null)
//		{
//			try {
//				pstmt = conn.prepareStatement(sql);
//				rs = pstmt.executeQuery();
//				while(rs.next())
//				{
//					t_list.add(new Theater(rs.getInt(1), rs.getString(2)));
//				} 
//			} catch (SQLException e) {
//				// TODO: handle exception
//				System.out.println("유저테이블 읽어오기 실패!");
//				e.printStackTrace();
//			}
//		}
	}

	@Override
	public void showAll() {
		// TODO Auto-generated method stub
		for(int i=0;i<t_list.size();i++) {
			System.out.println(t_list.get(i));
		}
	}

}
