package teamproject;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;



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
					m_list.add(new Movie(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getInt(6),rs.getInt(7)));
				} 
			} catch (SQLException e) {
				// TODO: handle exception
				System.out.println("¿µÈ­ Å×ÀÌºí ÀĞ¾î¿À±â ½ÇÆĞ!");
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void showAll() {
		// TODO Auto-generated method stub
		System.out.println("<¿µÈ­ ¸®½ºÆ®>");
		for(int i=0;i<m_list.size();i++) {
			if(!m_list.get(i).getTime().equals("00000000"))
				System.out.println(m_list.get(i));
		}
	}
	
	public void addMovie() {
		Scanner scan = new Scanner(System.in);
		String name, director, time;
		int age_limit, theater, date;
		while(true) {
			
			try {
				System.out.print("»ó¿µ ³¯Â¥¸¦ ÀÔ·ÂÇÏ¼¼¿ä : ");
				date = scan.nextInt();
				scan.nextLine();
				System.out.print("»ó¿µ½Ã°£À» ÀÔ·ÂÇÏ¼¼¿ä : ");
				time = scan.nextLine();
				System.out.print("»ó¿µ°ü ¹øÈ£¸¦ ÀÔ·ÂÇÏ¼¼¿ä : ");
				theater = scan.nextInt();
				scan.nextLine();
				
				if(checkTime(date, time, theater))
					break;
				System.out.println("¿Ã¹Ù¸£Áö ¾ÊÀº ÀÔ·Â°ªÀÔ´Ï´Ù.");
			} catch (Exception e) {
				// TODO: handle exception\
				System.out.println("¿Ã¹Ù¸£Áö ¾ÊÀº ÀÔ·Â°ªÀÔ´Ï´Ù.");
				scan.nextLine();
			}
				
		}
		
		
		
		while(true) {
			System.out.print("¿µÈ­ ÀÌ¸§À» ÀÔ·ÂÇÏ¼¼¿ä : ");
			name = scan.nextLine();
			if(name.length()<=30)
				break;
			System.out.println("¿Ã¹Ù¸£Áö ¾ÊÀº ÀÔ·Â°ªÀÔ´Ï´Ù.");
		}
		
		while(true) {
			System.out.print("°¨µ¶À» ÀÔ·ÂÇÏ¼¼¿ä : ");
			director = scan.nextLine();
			if(director.length()<=30)
				break;
			System.out.println("¿Ã¹Ù¸£Áö ¾ÊÀº ÀÔ·Â°ªÀÔ´Ï´Ù.");
		}
		System.out.print("¿¬·É Á¦ÇÑÀ» ÀÔ·ÂÇÏ¼¼¿ä : ");
		age_limit = scan.nextInt();
		scan.nextLine();
		
		
		
		
		String sql="insert into movie(name, director, time, age_limit, theater, date) values(?,?,?,?,?,?)";
		if(conn!=null) {
			try {
				pstmt=conn.prepareStatement(sql);
				pstmt.setString(1, name);
				pstmt.setString(2, director);
				pstmt.setString(3, time);
				pstmt.setInt(4, age_limit);
				pstmt.setInt(5, theater);
				pstmt.setInt(6, date);
				pstmt.execute();
				sql = "Select last_insert_id() as id from movie";
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				int id=0;
				while(rs.next())
				{
					
					id= rs.getInt("id");
				} 
				m_list.add(new Movie(id, name, director, time, age_limit, theater, date));
				System.out.println("ÇØ´ç ¿µÈ­°¡ Ãß°¡µÇ¾ú½À´Ï´Ù.");
			} catch(SQLException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
				System.out.println("¿µÈ­ Ãß°¡ ½ÇÆĞ!");
			}
		}
	}
	public void delMovie() {
		Scanner scan = new Scanner(System.in);
		System.out.println("¿µÈ­ »èÁ¦");
		showAll();
		System.out.print("»èÁ¦ÇÒ ¿µÈ­ÀÇ id¸¦ ÀÔ·ÂÇÏ¼¼¿ä : ");
		String id = scan.nextLine();
		if(getMovie(Integer.parseInt(id))!=null) {
			if(getMovie(Integer.parseInt(id)).getTime().equals("00000000"))
			{
				System.out.println("¿Ã¹Ù¸£Áö ¾ÊÀº ÀÔ·Â°ªÀÔ´Ï´Ù.");
				return;
			}
			getMovie(Integer.parseInt(id)).setTime("00000000");
			String sql = "update movie set time = ? where id = ?";
	        try {
	        	pstmt = conn.prepareStatement(sql);
	        	pstmt.setString(1, "00000000");
				pstmt.setInt(2, Integer.parseInt(id));
	            pstmt.execute();
	          
	        } catch (SQLException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
		}else {
			System.out.println("¿Ã¹Ù¸£Áö ¾ÊÀº ÀÔ·Â°ªÀÔ´Ï´Ù.");
			return;
		}
	}
	
	private Boolean checkTime(int _date, String _time, int theater) {
		
		Date today = new Date ();
		long startTime;
		long endTime;
		long todayTime;
		
		Calendar cal = Calendar.getInstance();
		int today_year = cal.get(Calendar.YEAR);
		int today_month = cal.get(Calendar.MONTH) + 1;
		int today_day = cal.get(Calendar.DAY_OF_MONTH);
		int today_hour = cal.get(Calendar.HOUR_OF_DAY);
		int today_min = cal.get(Calendar.MINUTE);
		
		int today_date = today_year*10000+today_month*100+today_day;
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("HHmm");
		SimpleDateFormat testdateFormat = new SimpleDateFormat("yyyyMMdd");
		dateFormat.setLenient(false);	//±î´Ù·Ó°Ô °Ë»ç
		testdateFormat.setLenient(false);
		try {
			Date test_Day = testdateFormat.parse(Integer.toString(_date));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
//			e1.printStackTrace();
			return false;
		}
		Date start, end;
		try {
			
			start = dateFormat.parse(_time.substring(0, 4));
			startTime = start.getTime();
			
			end= dateFormat.parse(_time.substring(4));
			endTime = end.getTime();
			
			today = dateFormat.parse(dateFormat.format(today));
			todayTime = today.getTime();
			
			//ºĞÀ¸·Î Ç¥Çö
//			long minute = (curDateTime - reqDateTime) / 60000;
			if(today_date>_date)
			{
				System.out.println("ÀÌ¹Ì Áö³­ ³¯Â¥ÀÔ´Ï´Ù.");
				return false;
			}else if(today_date==_date){
				if(startTime - todayTime<0) {
					System.out.println("test");
					return false;
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
//			System.out.println("parse¿À·ù");
			return false;
		}
		
		for(Movie m : m_list) {
			
			if(m.getTime().equals("00000000"))
				continue;
			
			if(m.getTheater_num()==theater&&m.getDate()==_date) {
				try {
					Date _start = dateFormat.parse(m.getTime().substring(0,4));
					Date _end = dateFormat.parse(m.getTime().substring(4));
					long _startTime = _start.getTime();
					long _endTime = _end.getTime();
					if(_startTime <= startTime && endTime <= _endTime)
						return false;
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					return false;
					
				} 
			}
		}
		
		if(startTime==endTime)
			return false;
		return true;
	}
	
	public Movie getMovie(int id) {
		for(int i=0;i<m_list.size();i++) {
			//&&(!m_list.get(i).getTime().equals("00000000"))
			if(m_list.get(i).getId()==id)
				return m_list.get(i); 
		}
		return null;
	}
	public void showTotalTheather() {
		System.out.println("ÀüÃ¼ »ó¿µ°ü : ");
		HashSet<Integer> set = new HashSet<Integer>();
		for(int i=0;i<m_list.size();i++) {
			if(m_list.get(i).getTime().equals("00000000"))
				continue;
			set.add(m_list.get(i).getTheater_num());
		}
		List<Integer> theaterList = new ArrayList<Integer>(set);
		Collections.sort(theaterList);
		for(Integer i: theaterList) {
			System.out.println(i+" °ü");
		}
	}
	public void SearchTheater(int id) {
		int cnt=0;
		for(int i=0;i<m_list.size();i++) {
			if(m_list.get(i).getTheater_num()==id) {
				if(m_list.get(i).getTime().equals("00000000"))
					continue;
				System.out.println(m_list.get(i));
				cnt++;
			}
		}
		if(cnt==0)
			System.out.println("Á¸ÀçÇÏÁö ¾Ê´Â »ó¿µ°üÀÔ´Ï´Ù.");
	}
	
	public void SearchMovie(String name) {
		String sql = "select * from movie where name like ?";
		if(conn!=null)
		{
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, "%"+name+"%");
				rs = pstmt.executeQuery();
				int cnt=0;
				while(rs.next())
				{
					cnt++;
					System.out.println(new Movie(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getInt(6), rs.getInt(7)));
				} 
				if(cnt==0)
					System.out.println("Á¸ÀçÇÏÁö ¾Ê´Â ¿µÈ­ÀÔ´Ï´Ù.");
			} catch (SQLException e) {
				// TODO: handle exception
				System.out.println("¿µÈ­ Å×ÀÌºí ÀĞ¾î¿À±â ½ÇÆĞ!");
				e.printStackTrace();
			}
		}
	}
	
	public int showDate() {
		Calendar cal=Calendar.getInstance();
		int year=cal.get(Calendar.YEAR);
		int month=cal.get(Calendar.MONTH)+1;
		int day=cal.get(Calendar.DAY_OF_MONTH);
		System.out.println("ÇöÀç ³¯Â¥ : "+year+month+day);
		return year+month+day;
	}
	
	public String getDate(int nowdate) {
		Scanner scan=new Scanner(System.in);
		System.out.println("³¯Â¥¸¦ ÀÔ·ÂÇØÁÖ¼¼¿ä. [ex)2020³â 10¿ù 10ÀÏ = 20201010]"
				+"[Âü°í : ÀÌÀüÈ­¸éÀ¸·Î µ¹¾Æ°¡·Á¸é x(X) ÀÔ·Â]");
		while(true) {
			String date=scan.nextLine();
			if(date.contentEquals("x")||date.contentEquals("X")) {
				return date;
			}else if(Integer.parseInt(date)<nowdate) {
				System.out.println("¿Ã¹Ù¸£Áö ¾ÊÀº ÀÔ·Â°ªÀÔ´Ï´Ù.");
				continue;
			}else if(checkDate(Integer.parseInt(date))==-1) {
				System.out.println("ÇØ´ç ³¯Â¥¿¡ ¿µÈ­Á¤º¸°¡ ¾ø½À´Ï´Ù.");
				continue;
			}
			return date;
		}
	}
	
	public int checkDate(int date) {
		int size=m_list.size();
		int ok=-1;
		for(int i=0;i<size;i++) {
			if(date==m_list.get(i).getDate()) {
				ok=1;
			}
		}
		return ok;
	}
	
	public void showSelectedMovie(int date) {
		System.out.println("<¿µÈ­ ¸®½ºÆ®>");
		for(int i=0;i<m_list.size();i++) {
			if(!m_list.get(i).getTime().equals("00000000")&&m_list.get(i).getDate()==date)
				System.out.println(m_list.get(i));
		}
	}
	
	public String selectMovie(int date) {
		Scanner scan=new Scanner(System.in);
		showSelectedMovie(date); 
		System.out.println("¾î´À ¿µÈ­¸¦ ¼±ÅÃÇÏ½Ã°Ú½À´Ï±î?");
		System.out.println("[Âü°í : ÀÌÀüÈ­¸éÀ¸·Î µ¹¾Æ°¡·Á¸é x(X) ÀÔ·Â]");
		String idx=scan.nextLine();
		return idx;
	}
	
	public String selectNumOfPersons() {
		Scanner scan=new Scanner(System.in);
		System.out.println("¸î »ç¶÷ÀÌ °ü¶÷ÇÏ½Ã°Ú½À´Ï±î?");
		System.out.println("[Âü°í : ÀÌÀüÈ­¸éÀ¸·Î µ¹¾Æ°¡·Á¸é x(X) ÀÔ·Â]");
		String idx=scan.nextLine();
		return idx;
	}
	
	public void showSeat(int id,UserManager um,MovieManager mm) {
		System.out.println("######ÁÂ¼®#####");
		int arr[][]=new int[8][8]; 
		int arr2[][]=new int[8][8];
		TicketManager tm=new TicketManager(um,mm);

		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				arr[i][j]=(8*i)+j+1;
				arr2[i][j]=0;
			}
		}
		
		if(tm.t_list.size()!=0) {
			for(int i=0;i<tm.t_list.size();i++) {
//È®ÀÎ¿ë			System.out.println(tm.t_list.get(i).getMovie().getId()+","+tm.t_list.get(i).getS_id()+","+id);
				for(int j=0;j<8;j++) {
					for(int k=0;k<8;k++) {
						if(tm.t_list.get(i).getMovie().getId()==id&&tm.t_list.get(i).getS_id()==arr[j][k]&&!tm.t_list.get(i).getUser().getId().contentEquals("NULL")) {
							arr2[j][k]=1;
						}
					}
				}
					
			}
		}
		
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				if(arr2[i][j]==0)
					System.out.print("¡à");
				else if(arr2[i][j]==1)
					System.out.print("¡á");
			}
			System.out.print("\n");
		}
	}
	
	public String[] selectSeat(int num,int id,UserManager um,MovieManager mm) {
		Scanner scan=new Scanner(System.in);
		showSeat(id,um,mm);
		String idx[]=new String[num];
		for(int i=0;i<num;i++) {
			System.out.println("¾î´À ÁÂ¼®À» ¼±ÅÃÇÏ½Ã°Ú½À´Ï±î?");
			System.out.println("[Âü°í : ÀÌÀüÈ­¸éÀ¸·Î µ¹¾Æ°¡·Á¸é x(X) ÀÔ·Â]");
			idx[i]=scan.nextLine();
			idx[i]=idx[i].toUpperCase();
		}
		return idx;
	}
	
	public String determine() {
		Scanner scan=new Scanner(System.in);
		System.out.println("¿¹¸Å ³»¿ªÀ» È®Á¤ÇÏ½Ã°Ú½À´Ï±î?(y/n)");
		System.out.println("[Âü°í : ÀÌÀüÈ­¸éÀ¸·Î µ¹¾Æ°¡·Á¸é x(X) ÀÔ·Â]");
		String idx=scan.nextLine();
		return idx;
	}
	
	public int[] translation(String[] str,int num) {
		int idx[]=new int[num];
		for(int i=0;i<num;i++) {
			String sql="select * from seat_info";
			if(conn!=null) {
				try {
					pstmt=conn.prepareStatement(sql);
					rs=pstmt.executeQuery();
					while(rs.next()) {
						if(rs.getString(2).contentEquals(str[i].substring(0,1))&&rs.getString(3).contentEquals(str[i].substring(1))) {
							idx[i]=rs.getInt(1);
						}
					}
				} catch (SQLException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		}
		return idx;
	}
	
	public int[] showBooklist(String u_id) {
		int size=0;
		String sql="select * from ticket";
		if(conn!=null) {
			try {
				pstmt=conn.prepareStatement(sql);
				rs=pstmt.executeQuery();
				while(rs.next()) {
					if(rs.getString(2).contentEquals(u_id)) {
						System.out.println("Æ¼ÄÏ ÀÎµ¦½º : "+rs.getInt(4)+"\t"+getMovie(rs.getInt(1)).getDate()+"\t"+getMovie(rs.getInt(1)).getName()+"\t\t"+getMovie(rs.getInt(1)).getTime()+"\t"+getMovie(rs.getInt(1)).getTheater_num()+"\t"+"ÁÂ¼®¹øÈ£"+rs.getInt(3));
						size++;
					}
				}
			} catch (SQLException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		int idx[]=new int[size];
		
		int i=0;
		if(conn!=null) {
			try {
				pstmt=conn.prepareStatement(sql);
				rs=pstmt.executeQuery();
				while(rs.next()) {
					if(rs.getString(2).contentEquals(u_id)) {
						idx[i]=rs.getInt(4);
						i++;	
					}
				}
			} catch (SQLException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		
		return idx;
	}
	
	public int checkBookandCancel(String u_id) {
		Scanner scan=new Scanner(System.in);
		System.out.println("»ç¿ëÀÚ¸ğµå>¿¹¸Å È®ÀÎ ¹× Ãë¼Ò");
		System.out.println("¿¹¸ÅÁ¤º¸");
		int arr[]=showBooklist(u_id);
		while(true) {
			System.out.println("Ãë¼ÒÇÏ·Á´Â ¿¹¸Å³»¿ªÀ» ÀÔ·ÂÇØÁÖ¼¼¿ä");
			System.out.println("[Âü°í : ÀÌÀüÈ­¸éÀ¸·Î µ¹¾Æ°¡·Á¸é x(X) ÀÔ·Â]");
			String idx=scan.nextLine();
			int ok=-1;
			if(idx.contentEquals("x")||idx.contentEquals("X")) {
				return 0;
			}
			for(int i=0;i<arr.length;i++) {
				if(Integer.parseInt(idx)==arr[i]) {
					String sql="update ticket set u_id = ? where idx = ?";
					if(conn!=null) {
						try {
							pstmt=conn.prepareStatement(sql);
							pstmt.setString(1, "NULL");
							pstmt.setInt(2, Integer.parseInt(idx));
							pstmt.execute();
						} catch (SQLException e) {
							// TODO: handle exception
							e.printStackTrace();
						}
					}
					ok=1;
				}
			}
			if(ok==-1) {
				System.out.println("¿Ã¹Ù¸£Áö ¾ÊÀº ÀÔ·Â°ªÀÔ´Ï´Ù.");
				continue;
			}
			break;
		}
		
		return 0;
		
	}
	
	public int book(String u_id,TicketManager tm,UserManager um,MovieManager mm) {
		System.out.println("»ç¿ëÀÚ¸ğµå>¿µÈ­¿¹¸Å>³¯Â¥¼±ÅÃ");
		int nowdate = showDate();
		String date = getDate(nowdate); //¿µÈ­³¯Â¥
		if(date.contentEquals("x")||date.contentEquals("X")) {
			return 0;
		}
		System.out.println("»ç¿ëÀÚ¸ğµå>¿µÈ­¿¹¸Å>³¯Â¥¼±ÅÃ>¿µÈ­¼±ÅÃ");
		System.out.println(date);
		String idx=selectMovie(Integer.parseInt(date));  //¿µÈ­ id
		if(idx.contentEquals("x")||idx.contentEquals("X")) {
			return 0;
		}
		int idx_2=Integer.parseInt(idx);
		String Mname=null;
		for(int i=0;i<m_list.size();i++) {
			if(m_list.get(i).getId()==idx_2) {
				Mname=m_list.get(i).getName();
				idx_2=i;
				break;
			}
		}
		System.out.println("¿µÈ­ id : "+idx+"index : "+idx_2);
		System.out.println("»ç¿ëÀÚ¸ğµå>¿µÈ­¿¹¸Å>³¯Â¥¼±ÅÃ>¿µÈ­¼±ÅÃ>ÀÎ¼ö¼±ÅÃ");
		System.out.println(": "+ date +"\t"+ Mname +"¿µÈ­"+"\t"+ m_list.get(idx_2).getTime() +"\t"+ m_list.get(idx_2).getTheater_num()+"°ü");
		String idx_3=selectNumOfPersons(); //¿¹¸Å ÀÎ¼ö
		if(idx_3.contentEquals("x")||idx_3.contentEquals("X")) {
			return 0;
		}
		int num=Integer.parseInt(idx_3);  //¿¹¸Å ÀÎ¼ö
		
		System.out.println("»ç¿ëÀÚ¸ğµå>¿µÈ­¿¹¸Å>³¯Â¥¼±ÅÃ>¿µÈ­¼±ÅÃ>ÀÎ¼ö¼±ÅÃ>¿µÈ­ÁÂ¼®¼±ÅÃ");
		System.out.println(": "+ date +"\t"+ Mname +"¿µÈ­"+"\t"+ m_list.get(idx_2).getTime() +"\t"+ m_list.get(idx_2).getTheater_num()+"°ü"+"\t"+idx_3+"¸í");
		String idx_4[]=new String[num];
		idx_4=selectSeat(num,Integer.parseInt(idx),um,mm); //ÁÂ¼® Á¤º¸ ¹è¿­
		int s_id[]=new int[num];
		s_id=translation(idx_4,num);
		
		int t_size=tm.t_list.size();
		int t_idx=0;
		if(t_size!=0) {
			t_idx=tm.t_list.get(t_size-1).getIdx();
		}
		
		
		System.out.println("»ç¿ëÀÚ¸ğµå>¿µÈ­¿¹¸Å>³¯Â¥¼±ÅÃ>¿µÈ­¼±ÅÃ>ÀÎ¼ö¼±ÅÃ>¿µÈ­ÁÂ¼®¼±ÅÃ>¿¹¸ÅÈ®Á¤");
		System.out.println("¿¹¸Å ³»¿ª");
		System.out.println(": "+ date +"\t"+ Mname +"¿µÈ­"+"\t"+ m_list.get(idx_2).getTime() +"\t"+ m_list.get(idx_2).getTheater_num()+"°ü"+"\t"+idx_3+"¸í");
		while(true) {
			String y_or_n = determine(); 
			if(y_or_n.contentEquals("y")||y_or_n.contentEquals("Y")) {
				System.out.println("¿¹¸Å¸¦ È®Á¤ÇÕ´Ï´Ù.");
				for(int i=0;i<Integer.parseInt(idx_3);i++) {
					String sql="insert into ticket values(?,?,?,?)";
					if(conn!=null) {
						try {
							pstmt=conn.prepareStatement(sql);
							pstmt.setInt(1, m_list.get(idx_2).getId());
							pstmt.setString(2, u_id);
							pstmt.setInt(3, s_id[i]);
							pstmt.setInt(4,t_idx+i+1);
							pstmt.execute();
						} catch(SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}else if(y_or_n.contentEquals("n")||y_or_n.contentEquals("N")) {
				System.out.println("¿¹¸Å¸¦ Ãë¼ÒÇÕ´Ï´Ù. ¸ŞÀÎÈ­¸éÀ¸·Î µ¹¾Æ°©´Ï´Ù.");
				return 0;
			}else if(y_or_n.contentEquals("x")||y_or_n.contentEquals("X")) {
				return 0;
			}else {
				System.out.println("¿Ã¹Ù¸£Áö ¾ÊÀº ÀÔ·Â°ªÀÔ´Ï´Ù.");
				continue;
			}
			break;
		}
		
		return 1;	
		
	}
	public void SearchMovieByAge(int age) {
		String sql = "select * from movie where age_limit = ?";
		if(conn!=null)
		{
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, age);
				rs = pstmt.executeQuery();
				while(rs.next())
				{
					System.out.println(new Movie(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getInt(6), rs.getInt(7)));
				} 
			} catch (SQLException e) {
				// TODO: handle exception
				System.out.println("¿µÈ­ Å×ÀÌºí ÀĞ¾î¿À±â ½ÇÆĞ!");
				e.printStackTrace();
			}
		}
	}
	
	public int Search() {
		Scanner scan=new Scanner(System.in);
		boolean escape=true;
		while(escape) {
			System.out.println("»ç¿ëÀÚ¸ğµå>¿µÈ­°Ë»ö");
			System.out.println("1.¿µÈ­Á¦¸ñ °Ë»ö 2.¿¬·ÉÁ¦ÇÑ °Ë»ö");
			System.out.println("[Âü°í : ÀÌÀüÈ­¸éÀ¸·Î µ¹¾Æ°¡·Á¸é x(X) ÀÔ·Â]");
			String idx=scan.nextLine();
			switch(idx) {
			case "1" : {
				System.out.println("»ç¿ëÀÚ¸ğµå>¿µÈ­°Ë»ö>¿µÈ­Á¦¸ñ °Ë»ö");
				System.out.println("[Âü°í : ÀÌÀüÈ­¸éÀ¸·Î µ¹¾Æ°¡·Á¸é x(X) ÀÔ·Â]");
				System.out.println("°Ë»öÇÏ°íÀÚ ÇÏ´Â ¿µÈ­¸¦ ÀÔ·ÂÇØÁÖ¼¼¿ä");
				String idx_2=scan.nextLine();
				if(idx_2.contentEquals("x")||idx_2.contentEquals("X")) {
					break;
				}
				
				SearchMovie(idx_2);
				System.out.println("[Âü°í : ¸ŞÀÎÈ­¸éÀ¸·Î µ¹¾Æ°¡·Á¸é x(X) ÀÔ·Â]");
				String idx_3=scan.nextLine();
				if(idx_3.contentEquals("x")||idx_3.contentEquals("X")) {
					return 1;
				}else {
					break;
				}
			}
	
			case "2" : {
				System.out.println("»ç¿ëÀÚ¸ğµå>¿µÈ­°Ë»ö>¿¬·ÉÁ¦ÇÑ °Ë»ö");
				System.out.println("¸î ¼¼ ÀÌ»ó °ü¶÷ °¡´É ¿µÈ­¸¦ Ã£À¸½Ã³ª¿ä?");
				System.out.println("[Âü°í : ÀÌÀüÈ­¸éÀ¸·Î µ¹¾Æ°¡·Á¸é x(X) ÀÔ·Â]");
				String idx_2=scan.nextLine();
				if(idx_2.contentEquals("x")||idx_2.contentEquals("X")) {
					break;
				}
				SearchMovieByAge(Integer.parseInt(idx_2));
				System.out.println("[Âü°í : ¸ŞÀÎÈ­¸éÀ¸·Î µ¹¾Æ°¡·Á¸é x(X) ÀÔ·Â]");
				String idx_3=scan.nextLine();
				if(idx_3.contentEquals("x")||idx_3.contentEquals("X")) {
					return 1;
				}else {
					break;
				}
			}
			case "x":
			case "X": {
				return 1;
			}
			default :{
				System.out.println("¿Ã¹Ù¸£Áö ¾ÊÀº ÀÔ·Â°ªÀÔ´Ï´Ù.");
				continue;
			}
			
			}
		
		}
		return 0;
	}
	
	
	public static boolean isInteger(String s) {	//¼ıÀÚ·Î¸¸ ÀÌ·ç¾îÁø ¹®ÀÚ¿­ÀÎÁö ÆÇº°ÇÏ´Â ÇÔ¼ö
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    return true;
	}
	
	public static boolean isPasswordType(String word) {	//ºñ¹Ğ¹øÈ£ Á¶°Ç¿¡ ¸¸Á·ÇÏ´Â ¹®ÀÚ¿­ÀÎÁö ÆÇº°ÇÏ´Â ÇÔ¼ö
        return Pattern.matches("^[0-9a-zA-Z]*$", word);
    }
	public static boolean isNameType(String word) {	//ÀÌ¸§ Á¶°Ç¿¡ ¸¸Á·ÇÏ´Â ¹®ÀÚ¿­ÀÎÁö ÆÇº°ÇÏ´Â ÇÔ¼ö
        return Pattern.matches("^[a-zA-Z°¡-ÆR]*$", word);
    }
}

