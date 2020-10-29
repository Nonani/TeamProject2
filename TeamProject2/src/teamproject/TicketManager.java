package teamproject;

import java.sql.SQLException;
import java.util.ArrayList;

public class TicketManager extends DBManager {

	UserManager um;
	MovieManager mm;
	
	ArrayList<Ticket> t_list;
	
	
	
	public TicketManager(UserManager um, MovieManager mm) {
		super();
		this.um = um;
		this.mm = mm;
		this.t_list = new ArrayList<Ticket>();
		connectDB();
		readDB();
	}

	@Override
	protected void readDB() {
		// TODO Auto-generated method stub
		String sql = "select * from ticket";
		if(conn!=null)
		{
			try {
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				while(rs.next())
				{
					t_list.add(new Ticket(mm.getMovie(rs.getInt(1)), um.getUser(rs.getString(2)), rs.getInt(3), rs.getInt(4)));
				} 
			} catch (SQLException e) {
				// TODO: handle exception
				System.out.println("티켓 테이블 읽어오기 실패!");
				e.printStackTrace();
			}
		}
	}
	
	public void SearchUser(String id) {
		System.out.println("관리자 모드 > 검색 > 사용자 검색 > 예매취소");
		User u = um.getUser(id);
		
		if(u==null) {
			System.out.println("올바르지 않은 입력값입니다.");
			return;
		}
		int cnt = 0;
		for(int i=0;i<t_list.size();i++) {
			if(t_list.get(i).getUser().equals(u)&&!(t_list.get(i).getUser().equals("NULL"))) {
				cnt++;
			}
		}
		if(cnt==0) {
			System.out.println("영화 이력이 없습니다..");
			return;
		}else {
			
			mm.checkBookandCancel(id);
		}
	}
	
	
	
	@Override
	public void showAll() {
		// TODO Auto-generated method stub
		for(int i=0;i<t_list.size();i++) {
			System.out.println(t_list.get(i));
		}
	}
}
