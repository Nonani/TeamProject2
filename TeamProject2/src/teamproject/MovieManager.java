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

import org.graalvm.compiler.nodes.calc.IsNullNode;



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
				System.out.println("영화 테이블 읽어오기 실패!");
				e.printStackTrace();
			}
		}
	}//test
	
	@Override
	public void showAll() {
		// TODO Auto-generated method stub
		System.out.println("<영화 리스트>");
		for(int i=0;i<m_list.size();i++) {
			if(!m_list.get(i).getTime().equals("00000000"))
				System.out.println(m_list.get(i));
		}
	}
	
	public void addMovie() {
		Scanner scan = new Scanner(System.in);
		String name, director, time, _date;
		int age_limit, theater, date=0;
		
		
		while(true){
			while(true) {
				try {
					System.out.print("상영 날짜를 입력하세요 : ");
					_date = scan.nextLine();
					_date = _date.replaceAll(" ", "").replaceAll("-", "");
					if(isInteger(_date)) {
						date = Integer.parseInt(_date);
						break;
					}
					System.out.println("올바르지 않은 입력값입니다.");
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("올바르지 않은 입력값입니다.");
				}
			}
				
			
			while(true) {
					System.out.print("상영시간을 입력하세요 : ");
					time = scan.nextLine();
					time = time.replaceAll(" ", "").replace("-", "");
					if(isInteger(time)) {
						
						break;
					}
					System.out.println("올바르지 않은 입력값입니다.");
			}
				
			while(true) {
				try {
					
					System.out.print("상영관 번호를 입력하세요 : ");
					String _t = scan.nextLine();
					theater = Integer.parseInt(_t);
					if(theater<100&&theater>0)
						break;
					System.out.println("올바르지 않은 입력값입니다.");
				} catch (Exception e) {
					// TODO: handle exception1
					System.out.println("올바르지 않은 입력값입니다.");
				}
			}
			if(checkTime(date, time, theater))
				break;
			else
				System.out.println("올바르지 않은 입력값입니다.");
		}
		
		
			
		
		
		
		
		while(true) {
			System.out.print("영화 이름을 입력하세요 : ");
			name = scan.nextLine();
			if(name.length()<=30&&isStringType(name))
				if(!name.replaceAll(" ", "").equals(""))
				break;
			System.out.println("올바르지 않은 입력값입니다.");
		}
		
		while(true) {
			System.out.print("감독을 입력하세요 : ");
			director = scan.nextLine();
			if(director.length()<=30&&isStringType(director))
				if(!director.replaceAll(" ", "").equals(""))
				break;
			System.out.println("올바르지 않은 입력값입니다.");
		}
		
	
		while(true) {
			
			try {
				System.out.print("연령 제한을 입력하세요 : ");
				String _age = scan.nextLine();
				age_limit = Integer.parseInt(_age);
				switch (age_limit) {
				case 0:
				case 12:
				case 15:
				case 19:
					break;
				default:
					System.out.println("올바르지 않은 입력값입니다.");
					continue;
				}
				
				
			} catch (Exception e) {
				// TODO: handle exception\
				System.out.println("올바르지 않은 입력값입니다.");
				continue;
			}
			break;
				
		}
		
		
		
		
		
		
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
				System.out.println("해당 영화가 추가되었습니다.");
			} catch(SQLException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
				System.out.println("영화 추가 실패!");
			}
		}
	}
	public void delMovie() {
		Scanner scan = new Scanner(System.in);
		System.out.println("영화 삭제");
		showAll();
		System.out.print("삭제할 영화의 id를 입력하세요 : ");
		String id = scan.nextLine();
		
		
		try {
//			System.out.println(isBookedMoive(Integer.parseInt(id)));
			if(getMovie(Integer.parseInt(id))!=null) {
				if(getMovie(Integer.parseInt(id)).getTime().equals("00000000")||isBookedMoive(Integer.parseInt(id)))
				{
					System.out.println("올바르지 않은 입력값입니다.");
					return;
				}
				getMovie(Integer.parseInt(id)).setTime("00000000");
				String sql = "update movie set time = ? where id = ?";
				pstmt = conn.prepareStatement(sql);
	        	pstmt.setString(1, "00000000");
				pstmt.setInt(2, Integer.parseInt(id));
	            pstmt.execute();
		        System.out.println("영화가 삭제되었습니다.");
			}else {
				System.out.println("올바르지 않은 입력값입니다.");
				return;
			}
	          
        } catch (Exception e) {
            // TODO Auto-generated catch block
        	System.out.println("올바르지 않은 입력값입니다.");
			return;
        }
	}
	
	public boolean isBookedMoive(int id) {
		int cnt = 0;
		try {
				Movie movie = getMovie(id);
				
				String sql = "Select * from ticket where m_id = ?";
				pstmt = conn.prepareStatement(sql);
	        	pstmt.setInt(1, movie.getId());
	        	rs = pstmt.executeQuery();
	        	
	        	while(rs.next())
				{
				  cnt++;
				} 
	          
        } catch (Exception e) {
            // TODO Auto-generated catch block
  
        	return false;
        }
		return cnt!=0;
		
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
		dateFormat.setLenient(false);	//까다롭게 검사
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
			
			//분으로 표현
//			long minute = (curDateTime - reqDateTime) / 60000;
			if(today_date>_date)
			{
				System.out.println("이미 지난 날짜입니다.");
				return false;
			}else if(today_date==_date){
				if(startTime - todayTime<0) {
					return false;
				}
			}
			
			if(startTime >= endTime)
				return false;
//			System.out.println(startTime - endTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
//			System.out.println("parse오류");
			return false;
		}
		
		for(Movie m : m_list) {
			
			if(m.getTime().equals("00000000"))
				continue;
			
			
			if(m.getTheater_num()==theater&&m.getDate()==_date) {	//_time = 리스트 내의 영화 시간, time = 등록할 영화 시간
				try {
					Date _start = dateFormat.parse(m.getTime().substring(0,4));
					Date _end = dateFormat.parse(m.getTime().substring(4));
					long _startTime = _start.getTime();
					long _endTime = _end.getTime();
					
					if(_startTime <= startTime && endTime <= _endTime )
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
		System.out.println("전체 상영관 : ");
		HashSet<Integer> set = new HashSet<Integer>();
		for(int i=0;i<m_list.size();i++) {
			if(m_list.get(i).getTime().equals("00000000"))
				continue;
			set.add(m_list.get(i).getTheater_num());
		}
		List<Integer> theaterList = new ArrayList<Integer>(set);
		Collections.sort(theaterList);
		for(Integer i: theaterList) {
			System.out.println(i+" 관");
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
			System.out.println("존재하지 않는 상영관입니다.");
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
					System.out.println("존재하지 않는 영화입니다.");
			} catch (SQLException e) {
				// TODO: handle exception
				System.out.println("영화 테이블 읽어오기 실패!");
				e.printStackTrace();
			}
		}
	}
	//영화 예매단계
	public int showDate() {              //현재 날짜를 보여주고 현재 날짜 리턴 
		Calendar cal=Calendar.getInstance();
		int year=cal.get(Calendar.YEAR);
		int month=cal.get(Calendar.MONTH)+1;
		int day=cal.get(Calendar.DAY_OF_MONTH);
		System.out.println("현재 날짜 : "+year+month+day);
		int result=year*10000+month*100+day;
		return result;
	}
	//영화 예매단계
	public boolean checkDate(int year,int month,int day) {
		int _date=year*10000+month*100+day;
		SimpleDateFormat testdateFormat = new SimpleDateFormat("yyyyMMdd");
		testdateFormat.setLenient(false);
		try {
			Date test_Day = testdateFormat.parse(Integer.toString(_date));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
//			e1.printStackTrace();
			return true;
		}
		return false;
	}
	//영화 예매단계
	public String getDate(int nowdate) {     //현재날짜를 인자로 받고 사용자에게 영화를 볼 날짜를 입력받는다
		Scanner scan=new Scanner(System.in);
		int year,month,day;
		while(true) {
			System.out.println("날짜를 입력해주세요. [ex)2020년 10월 10일 = 20201010]");
			System.out.println("[참고 : 이전화면으로 돌아가려면 x(X) 입력]");
			System.out.print("input : ");
			String date=scan.nextLine();
			if(date.contentEquals("x")||date.contentEquals("X")) {
				return date;
			}else if(date.length()!=8) {
				System.out.println("올바르지 않은 입력값입니다.");
				continue;
			}
			if(isInteger(date)) {
				year=Integer.parseInt(date)/10000;
				month=(Integer.parseInt(date)%10000)/100;
				day=(Integer.parseInt(date)%10000)%100;
			}else {
				System.out.println("올바르지 않은 입력값입니다.");
				continue;
			}
			
			if(Integer.parseInt(date)<nowdate) {
				System.out.println("올바르지 않은 입력값입니다.");
				continue;
			}else if(year<2020||month>12||month<1||day<1||day>31) {
				System.out.println("올바르지 않은 입력값입니다.");
				continue;
			}else if(checkDate(year,month,day)) {
				System.out.println("존재하지 않는 날짜입니다.");
				continue;
			}else if(checkDate(Integer.parseInt(date))==-1) {
				System.out.println("해당 날짜에 영화정보가 없습니다.");
				continue;
			}
			return date;
		}
	}
	//영화 예매단계
	public int checkDate(int date) {          //해당 날짜에 맞는 영화정보가 있는지 확인
		int size=m_list.size();
		int ok=-1;
		for(int i=0;i<size;i++) {
			if(date==m_list.get(i).getDate()) {
				ok=1;
			}
		}
		return ok;
	}
	//영화 예매단계
	public boolean checkTime(int start) {
		Calendar cal=Calendar.getInstance();
		int nowhour=cal.get(Calendar.HOUR_OF_DAY);
		int nowmin=cal.get(Calendar.MINUTE);
		if(start/100<=nowhour&&start%100<nowmin) {
			return false;
		}else
			return true;
	}
	//영화 예매단계
	public void showSelectedMovie(int date) {          //선택된 날짜의 영화 리스트를 보여준다
		
		
		System.out.println("<영화 리스트>");
		for(int i=0;i<m_list.size();i++) {
			if(!m_list.get(i).getTime().equals("00000000")&&m_list.get(i).getDate()==date&&checkTime(Integer.parseInt(m_list.get(i).getTime())/10000))
				System.out.println(m_list.get(i));
		}
	}
	//영화 예매단계
	public String selectMovie(int date,int age) {             //인자로 받은 날짜에 상영중인 영화중 사용자에게 선택을 받음 영화 고유 id를 받아서 리턴한다
		Scanner scan=new Scanner(System.in);
		showSelectedMovie(date);
		String idx="null";
		int ok=-1;
		while(true) {
			System.out.println("어느 영화를 선택하시겠습니까?");
			System.out.println("[참고 : 메인화면으로 돌아가려면 x(X) 입력]");
			System.out.print("input : ");
			idx=scan.nextLine();
			if(idx.contentEquals("x")||idx.contentEquals("X")) {
				return idx;
			}else if(isInteger(idx)){
				//숫자로만 이루어짐
			}else {
				System.out.println("올바르지 않은 입력값입니다.");
				continue;
			}
			//해당날짜에 있는 영화의 id인지 검사
			for(int i=0 ;i<m_list.size();i++) {
				if(m_list.get(i).getDate()==date && m_list.get(i).getId()==Integer.parseInt(idx)) {
					ok=1;
					break;
				}
			}
			if(ok==-1) {
				System.out.println("올바르지 않은 입력값입니다.");
				continue;
			}
			for(int i=0;i<m_list.size();i++) {
				if(m_list.get(i).getId()==Integer.parseInt(idx)&&m_list.get(i).getAge_limit()>age) {
					ok=-1;
					break;
				}
			}
			if(ok==-1) {
				System.out.println("연령 제한이 있는 영화입니다.");
				continue;
			}
			
			
			break;
		}
		return idx;
	}
	//영화 예매단계
	public int checkingBookNum(int m_id) {             //몇좌석이 예매되었는지 return
		int cnt=0;
		String sql="select * from ticket";
		if(conn!=null) {
			try {
				pstmt=conn.prepareStatement(sql);
				rs=pstmt.executeQuery();
				while(rs.next()) {
					if(rs.getInt(1)==m_id&&!rs.getString(2).contentEquals("NULL")) {
						cnt++;
					}
				}
			} catch (SQLException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		
		return cnt;
	}
	//영화 예매단계
	public String selectNumOfPersons(int m_id) {           //영화 예매 인원을 리턴한다
		Scanner scan=new Scanner(System.in);
		String idx;
		int Bnum=checkingBookNum(m_id);
		int emptyseat=64-Bnum;
		System.out.println("남은 좌석 수 : "+emptyseat);
		if(emptyseat==0) {
			System.out.println("남은 좌석이 없어 예매를 종료합니다.");
			return "x";
		}
		while(true) {
			System.out.println("몇 사람이 관람하시겠습니까?");
			System.out.println("[참고 : 메인화면으로 돌아가려면 x(X) 입력]");
			System.out.print("input : ");
			idx=scan.nextLine();
			if(idx.contentEquals("x")||idx.contentEquals("X")) {
				return idx;
			}else if(isInteger(idx)){
				//숫자로만 이루어짐
			}else {
				System.out.println("올바르지 않은 입력값입니다.");
				continue;
			}
			if(Integer.parseInt(idx)<1||Integer.parseInt(idx)>64) {
				System.out.println("올바르지 않은 입력값입니다.");
				continue;
			}else if(Integer.parseInt(idx)>emptyseat){
				System.out.println("해당 인원수가 잔여 좌석보다 많습니다.");
				continue;
			}else
				break;
		}
		return idx;
	}
	//영화 예매단계
	public void showSeat(int id,UserManager um,MovieManager mm) {    //좌석 리스트를 보여준다
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
//확인용			System.out.println(tm.t_list.get(i).getMovie().getId()+","+tm.t_list.get(i).getS_id()+","+id);
				for(int j=0;j<8;j++) {
					for(int k=0;k<8;k++) {
						if(tm.t_list.get(i).getMovie().getId()==id&&tm.t_list.get(i).getS_id()==arr[j][k]&&!tm.t_list.get(i).getUser().getId().contentEquals("NULL")) {
							arr2[j][k]=1;
						}
					}
				}
					
			}
		}
		System.out.println("  1 2 3 4 5 6 7 8");
		char[] row=new char[8];
		for(int i=0;i<8;i++) {
			row[i]=(char)('A'+i);
			System.out.print(row[i]+" ");
			for(int j=0;j<8;j++) {
				if(arr2[i][j]==0)
					System.out.print("□ ");
				else if(arr2[i][j]==1)
					System.out.print("■ ");
			}
			System.out.print("\n");
		}
	}
	//영화 예매단계
	public boolean checkingOverlap(String idx,int m_id) {            //입력받은 좌석의 값이 기존에 예매되어 있는 좌석이랑 중복이 되는지 체크하는 함수
		String[] str=new String[1];
		int[] s_id=new int[1]; 
		str[0]=idx;
		s_id=translation(str,1);
		String sql="select * from ticket";
		if(conn!=null) {
			try {
				pstmt=conn.prepareStatement(sql);
				rs=pstmt.executeQuery();
				while(rs.next()) {
					if(rs.getInt(3)==s_id[0]&&rs.getInt(1)==m_id&&!rs.getString(2).contentEquals("NULL")) {
						return true;
					}
				}
			} catch (SQLException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return false;
	}
	//영화 예매단계
	public String[] selectSeat(int num,int id,UserManager um,MovieManager mm) {  //좌석을 선택받아 문자열 배열에 저장한다
		Scanner scan=new Scanner(System.in);
		showSeat(id,um,mm);
		String idx[]=new String[num];
		for(int i=0;i<num;i++) {
			while(true) {
				System.out.println("어느 좌석을 선택하시겠습니까?");
				System.out.println("[참고 : 메인화면으로 돌아가려면 x(X) 입력]");
				System.out.print("input : ");
				idx[i]=scan.nextLine();
				idx[i]=idx[i].replaceAll("\\p{Z}","");
				idx[i]=idx[i].toUpperCase();
				if(i>=1) {
					if(idx[i].contentEquals(idx[i-1])) {
						System.out.println("이미 예매된 좌석입니다. 다시 입력해주세요 ");
						continue;
					}
				}
				char[] ch=idx[i].toCharArray();
				if(idx[i].contentEquals("x")||idx[i].contentEquals("X")) {
					return idx;
				}
				if(idx[i].length()!=2) {
					System.out.println("올바르지 않은 입력값입니다.");
					continue;
				}else if(ch[0]<65||ch[0]>72||ch[1]<49||ch[1]>56) {
					System.out.println("올바르지 않은 입력값입니다.");
					continue;
				}else if(checkingOverlap(idx[i],id)) {
					System.out.println("이미 예매된 좌석입니다. 다시 입력해주세요");
					continue;
				}
				break;
			}
		}
		
		return idx;
	}
	//영화 예매단계 
	public String determine() { //예매 확정단계
		Scanner scan=new Scanner(System.in);
		System.out.println("예매 내역을 확정하시겠습니까?(y/n)");
		System.out.println("[참고 : 메인화면으로 돌아가려면 x(X) 입력]");
		System.out.print("input : ");
		String idx=scan.nextLine();
		return idx;
	}
	//영화 예매단계
	public int[] translation(String[] str,int num) {  //좌석을 입력받으면 이에 해당하는 int형 s_id로 바꿔주는 함수
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
	public String translationToStr(int s_id) {            //s_id를 입력받으면 이를 str으로 바꿔주는 함수
		int row=s_id/8;
		String col=Integer.toString(s_id%8);
		String s_info="A";
		switch (row) {
		case 0:
			s_info=s_info.concat(col);
			break;
		case 1:
			if(col.contentEquals("0")) {
				s_info="A8";
				break;
			}
			s_info="B";
			s_info=s_info.concat(col);
			break;
		case 2:
			if(col.contentEquals("0")) {
				s_info="B8";
				break;
			}
			s_info="C";
			s_info=s_info.concat(col);
			break;
		case 3:
			if(col.contentEquals("0")) {
				s_info="C8";
				break;
			}
			s_info="D";
			s_info=s_info.concat(col);
			break;
		case 4:
			if(col.contentEquals("0")) {
				s_info="D8";
				break;
			}
			s_info="E";
			s_info=s_info.concat(col);
			break;
		case 5:
			if(col.contentEquals("0")) {
				s_info="E8";
				break;
			}
			s_info="F";
			s_info=s_info.concat(col);
			break;
		case 6:
			if(col.contentEquals("0")) {
				s_info="F8";
				break;
			}
			s_info="G";
			s_info=s_info.concat(col);
			break;
		case 7:
			if(col.contentEquals("0")) {
				s_info="G8";
				break;
			}
			s_info="H";
			s_info=s_info.concat(col);
			break;
		case 8:
			if(col.contentEquals("0")) {
				s_info="H8";
				break;
			}
			break;
			default :
				break;
		}
		return s_info;
	}
	// 영화 예매 단계 main
	public int book(String u_id,TicketManager tm,UserManager um,MovieManager mm,int u_age) {
		System.out.println("사용자모드>영화예매>날짜선택");
		int nowdate = showDate();
		String date = getDate(nowdate); //영화날짜
		if(date.contentEquals("x")||date.contentEquals("X")) {
			return 0;
		}
		System.out.println("사용자모드>영화예매>날짜선택>영화선택");
		System.out.println(date);
		String idx=selectMovie(Integer.parseInt(date),u_age);  //영화 id
		if(idx.contentEquals("x")||idx.contentEquals("X")) {
			return 0;
		}
		int idx_2=Integer.parseInt(idx);           //영화 idx
		String Mname=null;
		for(int i=0;i<m_list.size();i++) {
			if(m_list.get(i).getId()==idx_2) {
				Mname=m_list.get(i).getName();
				idx_2=i;
				break;
			}
		}
//확인용	System.out.println("영화 id : "+idx+"index : "+idx_2);
		System.out.println("사용자모드>영화예매>날짜선택>영화선택>인수선택");
		System.out.println(": "+ date +"  "+ Mname +"영화"+"  "+ m_list.get(idx_2).getTime() +"  "+ m_list.get(idx_2).getTheater_num()+"관");
		String idx_3=selectNumOfPersons(Integer.parseInt(idx)); //예매 인수
		if(idx_3.contentEquals("x")||idx_3.contentEquals("X")) {
			return 0;
		}
		int num=Integer.parseInt(idx_3);  //예매 인수
		
		System.out.println("사용자모드>영화예매>날짜선택>영화선택>인수선택>영화좌석선택");
		System.out.println(": "+ date +"  "+ Mname +"영화"+"  "+ m_list.get(idx_2).getTime() +"  "+ m_list.get(idx_2).getTheater_num()+"관"+"  "+idx_3+"명");
		String idx_4[]=new String[num];
		idx_4=selectSeat(num,Integer.parseInt(idx),um,mm); //좌석 정보 배열
		for(int i=0;i<num;i++) {
			if(idx_4[i].contentEquals("x")||idx_4[i].contentEquals("X")) {
				return 0;
			}
		}
		int s_id[]=new int[num];
		s_id=translation(idx_4,num);
		
		int t_size=tm.t_list.size();
		int t_idx=0;
		if(t_size!=0) {
			t_idx=tm.t_list.get(t_size-1).getIdx();
		}
		
		
		System.out.println("사용자모드>영화예매>날짜선택>영화선택>인수선택>영화좌석선택>예매확정");
		System.out.println("예매 내역");
		System.out.println(": "+ date +"  "+ Mname +"영화"+"  "+ m_list.get(idx_2).getTime() +"  "+ m_list.get(idx_2).getTheater_num()+"관"+"  "+idx_3+"명");
		while(true) {
			String y_or_n = determine(); 
			if(y_or_n.contentEquals("y")||y_or_n.contentEquals("Y")) {
				System.out.println("예매를 확정합니다.");
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
				System.out.println("예매를 취소합니다. 메인화면으로 돌아갑니다.");
				return 0;
			}else if(y_or_n.contentEquals("x")||y_or_n.contentEquals("X")) {
				return 0;
			}else {
				System.out.println("올바르지 않은 입력값입니다.");
				continue;
			}
			break;
		}
		
		return 1;	
		
	}
	//예매 확인 및 취소단계
	public int[] showBooklist(String u_id) {  //로그인된 아이디로 예매한 영화 정보를 보여준다, 예매한 티켓의 idx를 배열로 리턴
		int size=0;
		Calendar cal=Calendar.getInstance();
		int year=cal.get(Calendar.YEAR);
		int month=cal.get(Calendar.MONTH)+1;
		int day=cal.get(Calendar.DAY_OF_MONTH);
		int nowdate=year*10000+month*100+day;
		String sql="select * from ticket";
		String s_info;
		if(conn!=null) {
			try {
				pstmt=conn.prepareStatement(sql);
				rs=pstmt.executeQuery();
				while(rs.next()) {
					if(rs.getString(2).contentEquals(u_id)&&getMovie(rs.getInt(1)).getDate()>=nowdate) {
						s_info=translationToStr(rs.getInt(3));
						//System.out.println("티켓 인덱스 : "+rs.getInt(4)+"\t"+getMovie(rs.getInt(1)).getDate()+"\t"+getMovie(rs.getInt(1)).getName()+"\t"+getMovie(rs.getInt(1)).getTime()+"\t"+getMovie(rs.getInt(1)).getTheater_num()+"\t"+"좌석번호"+rs.getInt(3));
						System.out.printf("티켓 인덱스 : %-3d\t날짜 : %-10d\t영화제목 : %-10s감독 : %-10s상영시간 : %-10s연령제한 : %-10d상영관 번호 : %-10d좌석번호 : %-3s\n",rs.getInt(4),getMovie(rs.getInt(1)).getDate(),getMovie(rs.getInt(1)).getName(),getMovie(rs.getInt(1)).getDirector(),getMovie(rs.getInt(1)).getTime(),getMovie(rs.getInt(1)).getAge_limit(),getMovie(rs.getInt(1)).getTheater_num(),s_info);
						size++;
					}
				}
			} catch (SQLException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		if(size==0) {
			int idx[]=new int[1];
			idx[0]=-1;
			return idx;
		}
		int idx[]=new int[size];
			
		int i=0;
		if(conn!=null) {
			try {
				pstmt=conn.prepareStatement(sql);
				rs=pstmt.executeQuery();
				while(rs.next()) {
					if(rs.getString(2).contentEquals(u_id)&&getMovie(rs.getInt(1)).getDate()>=nowdate) {
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
	//예매 확인 및 취소 단계 main
	public int checkBookandCancel(String u_id) {
		Scanner scan=new Scanner(System.in);
		System.out.println("예매정보");
		int arr[]=showBooklist(u_id);
		if(arr[0]==-1) {
			System.out.println("예매된 정보가 없습니다.");
			return 0;
		}
		while(true) {
			System.out.println("취소하려는 예매내역을 입력해주세요");
			System.out.println("[참고 : 이전화면으로 돌아가려면 x(X) 입력]");
			System.out.print("input : ");
			String idx=scan.nextLine();
			int ok=-1;
			if(idx.contentEquals("x")||idx.contentEquals("X")) {
				return 0;
			}else if(isInteger(idx)) {
				
			}else {
				System.out.println("올바르지 않은 입력값입니다");
				continue;
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
				System.out.println("올바르지 않은 입력값입니다.");
				continue;
			}
			break;
		}
		System.out.println("영화 취소가 완료되었습니다.");
		return 0;	
	}
	//영화 검색 단계
	public void SearchMovieByAge(int age) {          //연령 제한으로 검색
		int cnt=0;
		Calendar cal=Calendar.getInstance();
		int year=cal.get(Calendar.YEAR);
		int month=cal.get(Calendar.MONTH)+1;
		int day=cal.get(Calendar.DAY_OF_MONTH);
		int nowdate=year*10000+month*100+day;
		
		String sql = "select * from movie where age_limit = ?";
		if(conn!=null)
		{
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, age);
				rs = pstmt.executeQuery();
				cnt=0;
				while(rs.next())
				{
					if(nowdate<=rs.getInt(7)) {
						cnt++;
						System.out.println(new Movie(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getInt(6), rs.getInt(7)));
					}
				}
				if(cnt==0) {
					System.out.println("찾으시는 영화 정보가 없습니다.");
				}
			} catch (SQLException e) {
				// TODO: handle exception
				System.out.println("영화 테이블 읽어오기 실패!");
				e.printStackTrace();
			}
		}
	}
	//영화 검색 단계
	public int SearchMovieByName(String name) {     //영화 제목으로 검색
		Calendar cal=Calendar.getInstance();
		int year=cal.get(Calendar.YEAR);
		int month=cal.get(Calendar.MONTH)+1;
		int day=cal.get(Calendar.DAY_OF_MONTH);
		int nowdate=year*10000+month*100+day;
		String sql = "select * from movie where name like ?";
		int cnt=0;
		if(conn!=null)
		{
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, "%"+name+"%");
				rs = pstmt.executeQuery();
				cnt=0;
				while(rs.next())
				{
					if(nowdate<=rs.getInt(7)) {
						cnt++;
						System.out.println(new Movie(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getInt(6), rs.getInt(7)));
					}
				} 
				if(cnt==0)
					System.out.println("포함된 단어의 영화가 없습니다. 다시 입력해주세요.");
			} catch (SQLException e) {
				// TODO: handle exception
				System.out.println("영화 테이블 읽어오기 실패!");
				e.printStackTrace();
			}
		}
		return cnt;
	}
	//영화 검색 단계 main
	public int Search() {
		Scanner scan=new Scanner(System.in);
		boolean escape=true;
		String idx;
		while(escape) {
			System.out.println("사용자모드>영화검색");
			while(true) {
				System.out.println("1.영화제목 검색 2.연령제한 검색");
				System.out.println("[참고 : 이전화면으로 돌아가려면 x(X) 입력]");
				System.out.print("input : ");
				idx=scan.nextLine();
				if(idx.contentEquals("x")||idx.contentEquals("X")) {
					return 0;
				}else if(idx.contentEquals("")) {
					System.out.println("올바르지 않은 입력값입니다.");
					continue;
				}else if(isInteger(idx)) {
					//숫자로만 이루어진 문자열이다
				}else{
					System.out.println("올바르지 않은 입력값입니다.");
					continue;
				}
				if(Integer.parseInt(idx)<1||Integer.parseInt(idx)>2) {
					System.out.println("올바르지 않은 입력값입니다.");
					continue;
				}
				break;
			}
			switch(idx) {
			case "1" : {
				System.out.println("사용자모드>영화검색>영화제목 검색");
				while(true) {
					System.out.println("검색하고자 하는 영화를 입력해주세요");
					System.out.println("[참고 : 메인화면으로 돌아가려면 x(X) 입력]");
					System.out.print("input : ");
					String idx_2=scan.nextLine();
					if(idx_2.contentEquals("x")||idx_2.contentEquals("X")) {
						return 0;
					}
					int cnt = SearchMovieByName(idx_2);
					if(cnt==0) {
						continue;
					}
					break;
				}
				while(true) {
					System.out.println("[참고 : 메인화면으로 돌아가려면 x(X) 입력]");
					String idx_3=scan.nextLine();
					if(idx_3.contentEquals("x")||idx_3.contentEquals("X")) {
						return 0;
					}else {
						System.out.println("올바르지 않은 입력값입니다");
						continue;
					}
				}
			}
	
			case "2" : {
				System.out.println("사용자모드>영화검색>연령제한 검색");
				String idx_2;
				while(true) {
					System.out.println("몇 세 관람 영화를 찾으시나요? [전체이용가|7세|12세|15세|19세],전체이용가는 0을 입력해주세요");
					System.out.println("[참고 : 메인화면으로 돌아가려면 x(X) 입력]");
					System.out.print("input : ");
					idx_2=scan.nextLine();
					if(idx_2.contentEquals("x")||idx_2.contentEquals("X")) {
						return 0;
					}else if(isInteger(idx_2)) {
						//문자열로만 이루어짐
					}else {
						System.out.println("올바르지 않은 입력값입니다.1");
						continue;
					}
					if(Integer.parseInt(idx_2)!=0&&Integer.parseInt(idx_2)!=7&&Integer.parseInt(idx_2)!=12&&Integer.parseInt(idx_2)!=15&&Integer.parseInt(idx_2)!=19) {
						System.out.println("올바르지 않은 입력값입니다.2");
						continue;
					}
					break;
					
				}
				SearchMovieByAge(Integer.parseInt(idx_2));
				while(true) {
					System.out.println("[참고 : 메인화면으로 돌아가려면 x(X) 입력]");
					String idx_3=scan.nextLine();
					if(idx_3.contentEquals("x")||idx_3.contentEquals("X")) {
						return 0;
					}else {
						System.out.println("올바르지 않은 입력값입니다.");
						continue;
					}
				}
			}
			case "x":
			case "X": {
				return 0;
			}
			default :{
				System.out.println("올바르지 않은 입력값입니다.");
				continue;
			}
			
			}
		
		}
		return 0;
	}
	
	
	public static boolean isInteger(String s) {	//숫자로만 이루어진 문자열인지 판별하는 함수
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    return true;
	}
	
	public static boolean isPasswordType(String word) {	//비밀번호 조건에 만족하는 문자열인지 판별하는 함수
        return Pattern.matches("^[0-9a-zA-Z]*$", word);
    }
	public static boolean isStringType(String word) {	//이름 조건에 만족하는 문자열인지 판별하는 함수
        return Pattern.matches("^[0-9a-zA-Z가-힣]*$", word);
    }
}

