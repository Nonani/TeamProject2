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
				System.out.println("��ȭ ���̺� �о���� ����!");
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void showAll() {
		// TODO Auto-generated method stub
		System.out.println("��ȭ ����Ʈ : ");
		for(int i=0;i<m_list.size();i++) {
			System.out.println(m_list.get(i));
		}
	}
	
	public void addMovie() {
		Scanner scan = new Scanner(System.in);
		System.out.println("��ȭ �߰�");
		String name, director, time;
		int age_limit, theater;
		
		System.out.print("��ȭ �̸��� �Է��ϼ��� : ");
		name = scan.nextLine();
		System.out.print("������ �Է��ϼ��� : ");
		director = scan.nextLine();
		while(true) {
			System.out.print("�󿵽ð��� �Է��ϼ��� : ");
			time = scan.nextLine();
			if(checkTime(time))
				break;
			System.out.println("�ùٸ��� ���� �Է°��Դϴ�.");
				
		}
		
		System.out.print("���� ������ �Է��ϼ��� : ");
		age_limit = scan.nextInt();
		System.out.print("�󿵰� ��ȣ�� �Է��ϼ��� : ");
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
				System.out.println("�ش� ��ȭ�� �߰��Ǿ����ϴ�.");
			} catch(SQLException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
				System.out.println("��ȭ �߰� ����!");
			}
		}
	}
	public void delMovie() {
		System.out.println("��ȭ ����");
		
	}
	
	private Boolean checkTime(String _time) {
		
		Date today = new Date ();
		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYYMMddHHmm");
		dateFormat.setLenient(false);	//��ٷӰ� �̻�
		Date reqDate;
		try {
			reqDate = dateFormat.parse(_time);
			long reqDateTime = reqDate.getTime();
			
			today = dateFormat.parse(dateFormat.format(today));
			long todayTime = today.getTime();
			
			//������ ǥ��
//			long minute = (curDateTime - reqDateTime) / 60000;
			
			if(reqDateTime-todayTime>0)
				return true;
			else
				return false;
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
//			System.out.println("parse����");
			return false;
		}
	}
	
}
