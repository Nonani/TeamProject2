package teamproject;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;



public class MovieManager extends DBManager {

	ArrayList<Movie> m_list = new ArrayList<Movie>();
	
	public MovieManager() {
		// TODO Auto-generated constructor stub
		connectDB();
		readDB();
	}
	
	@Override
	protected void readDB() {	
		// TODO Auto-generated method stub
		String sql = "select * from movie";
		if(conn!=null)
		{
			try {
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				while(rs.next())
				{
					m_list.add(new Movie(rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getInt(6)));
				} 
			} catch (SQLException e) {
				// TODO: handle exception
				System.out.println("영화 테이블 읽어오기 실패!");
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void showAll() {
		// TODO Auto-generated method stub
		System.out.println("영화 리스트 : ");
		for(int i=0;i<m_list.size();i++) {
			System.out.println(m_list.get(i));
		}
	}
	
	public void addMovie() {
		Scanner scan = new Scanner(System.in);
		System.out.println("영화 추가");
		String name, director, time;
		int age_limit, theater;
		
		System.out.print("영화 이름을 입력하세요 : ");
		name = scan.nextLine();
		System.out.print("감독을 입력하세요 : ");
		director = scan.nextLine();
		while(true) {
			System.out.print("상영시간을 입력하세요 : ");
			time = scan.nextLine();
			if(checkTime(time))
				break;
			System.out.println("올바르지 않은 입력값입니다.");
				
		}
		
		System.out.print("연령 제한을 입력하세요 : ");
		age_limit = scan.nextInt();
		System.out.print("상영관 번호를 입력하세요 : ");
		theater = scan.nextInt();
		
		String sql="insert into movie(name, director, time, age_limit, theater) values(?,?,?,?,?)";
		if(conn!=null) {
			try {
				pstmt=conn.prepareStatement(sql);
				pstmt.setString(1, name);
				pstmt.setString(2, director);
				pstmt.setString(3, time);
				pstmt.setInt(4, age_limit);
				pstmt.setInt(5, theater);
				pstmt.execute();
				m_list.add(new Movie(name, director, time, age_limit, theater));
				System.out.println("해당 영화가 추가되었습니다.");
			} catch(SQLException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
				System.out.println("영화 추가 실패!");
			}
		}
	}
	public void delMovie() {
		System.out.println("영화 삭제");
		
	}
	
	private Boolean checkTime(String _time) {
		
		Date today = new Date ();
		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYYMMddHHmm");
		dateFormat.setLenient(false);	//까다롭게 겁사
		Date reqDate;
		try {
			reqDate = dateFormat.parse(_time);
			long reqDateTime = reqDate.getTime();
			
			today = dateFormat.parse(dateFormat.format(today));
			long todayTime = today.getTime();
			
			//분으로 표현
//			long minute = (curDateTime - reqDateTime) / 60000;
			
			if(reqDateTime-todayTime>0)
				return true;
			else
				return false;
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
//			System.out.println("parse오류");
			return false;
		}
	}
	
}
