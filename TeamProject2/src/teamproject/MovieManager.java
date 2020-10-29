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
				System.out.println("��ȭ ���̺� �о���� ����!");
				e.printStackTrace();
			}
		}
	}//test
	
	@Override
	public void showAll() {
		// TODO Auto-generated method stub
		System.out.println("<��ȭ ����Ʈ>");
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
				System.out.print("�� ��¥�� �Է��ϼ��� : ");
				date = scan.nextInt();
				scan.nextLine();
				System.out.print("�󿵽ð��� �Է��ϼ��� : ");
				time = scan.nextLine();
				if(time.length()!=8) {
					System.out.println("�ùٸ��� ���� �Է°��Դϴ�.");
					continue;
				}
				System.out.print("�󿵰� ��ȣ�� �Է��ϼ��� : ");
				theater = scan.nextInt();
				scan.nextLine();
				
				if(checkTime(date, time, theater))
					break;
				System.out.println("�ùٸ��� ���� �Է°��Դϴ�.");
			} catch (Exception e) {
				// TODO: handle exception\
				
				System.out.println("�ùٸ��� ���� �Է°��Դϴ�.");
				scan.nextLine();
			}
				
		}
		
		
		
		while(true) {
			System.out.print("��ȭ �̸��� �Է��ϼ��� : ");
			name = scan.nextLine();
			if(name.length()<=30)
				if(!name.replaceAll(" ", "").equals(""))
				break;
			System.out.println("�ùٸ��� ���� �Է°��Դϴ�.");
		}
		
		while(true) {
			System.out.print("������ �Է��ϼ��� : ");
			director = scan.nextLine();
			if(director.length()<=30)
				if(!director.replaceAll(" ", "").equals(""))
				break;
			System.out.println("�ùٸ��� ���� �Է°��Դϴ�.");
		}
		
	
		while(true) {
			
			try {
				System.out.print("���� ������ �Է��ϼ��� : ");
				age_limit = scan.nextInt();
				switch (age_limit) {
				case 0:
				case 12:
				case 15:
				case 19:
					break;
				default:
					scan.nextLine();
					System.out.println("�ùٸ��� ���� �Է°��Դϴ�.");
					continue;
				}
				
				
			} catch (Exception e) {
				// TODO: handle exception\
				scan.nextLine();
				System.out.println("�ùٸ��� ���� �Է°��Դϴ�.");
				continue;
			}
			scan.nextLine();
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
				System.out.println("�ش� ��ȭ�� �߰��Ǿ����ϴ�.");
			} catch(SQLException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
				System.out.println("��ȭ �߰� ����!");
			}
		}
	}
	public void delMovie() {
		Scanner scan = new Scanner(System.in);
		System.out.println("��ȭ ����");
		showAll();
		System.out.print("������ ��ȭ�� id�� �Է��ϼ��� : ");
		String id = scan.nextLine();
		
		
		try {
			if(getMovie(Integer.parseInt(id))!=null) {
				if(getMovie(Integer.parseInt(id)).getTime().equals("00000000"))
				{
					System.out.println("�ùٸ��� ���� �Է°��Դϴ�.");
					return;
				}
				getMovie(Integer.parseInt(id)).setTime("00000000");
				String sql = "update movie set time = ? where id = ?";
				pstmt = conn.prepareStatement(sql);
	        	pstmt.setString(1, "00000000");
				pstmt.setInt(2, Integer.parseInt(id));
	            pstmt.execute();
		        
			}else {
				System.out.println("�ùٸ��� ���� �Է°��Դϴ�.");
				return;
			}
	          
        } catch (Exception e) {
            // TODO Auto-generated catch block
        	System.out.println("�ùٸ��� ���� �Է°��Դϴ�.");
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
		dateFormat.setLenient(false);	//��ٷӰ� �˻�
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
			
			//������ ǥ��
//			long minute = (curDateTime - reqDateTime) / 60000;
			if(today_date>_date)
			{
				System.out.println("�̹� ���� ��¥�Դϴ�.");
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
//			System.out.println("parse����");
			return false;
		}
		
		for(Movie m : m_list) {
			
			if(m.getTime().equals("00000000"))
				continue;
			
			
			if(m.getTheater_num()==theater&&m.getDate()==_date) {	//_time = ����Ʈ ���� ��ȭ �ð�, time = ����� ��ȭ �ð�
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
		System.out.println("��ü �󿵰� : ");
		HashSet<Integer> set = new HashSet<Integer>();
		for(int i=0;i<m_list.size();i++) {
			if(m_list.get(i).getTime().equals("00000000"))
				continue;
			set.add(m_list.get(i).getTheater_num());
		}
		List<Integer> theaterList = new ArrayList<Integer>(set);
		Collections.sort(theaterList);
		for(Integer i: theaterList) {
			System.out.println(i+" ��");
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
			System.out.println("�������� �ʴ� �󿵰��Դϴ�.");
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
					System.out.println("�������� �ʴ� ��ȭ�Դϴ�.");
			} catch (SQLException e) {
				// TODO: handle exception
				System.out.println("��ȭ ���̺� �о���� ����!");
				e.printStackTrace();
			}
		}
	}
	//��ȭ ���Ŵܰ�
	public int showDate() {              //���� ��¥�� �����ְ� ���� ��¥ ���� 
		Calendar cal=Calendar.getInstance();
		int year=cal.get(Calendar.YEAR);
		int month=cal.get(Calendar.MONTH)+1;
		int day=cal.get(Calendar.DAY_OF_MONTH);
		System.out.println("���� ��¥ : "+year+month+day);
		int result=year*10000+month*100+day;
		return result;
	}
	//��ȭ ���Ŵܰ�
	public String getDate(int nowdate) {     //���糯¥�� ���ڷ� �ް� ����ڿ��� ��ȭ�� �� ��¥�� �Է¹޴´�
		Scanner scan=new Scanner(System.in);
		int year,month,day;
		while(true) {
			System.out.println("��¥�� �Է����ּ���. [ex)2020�� 10�� 10�� = 20201010]"
					+"[���� : ����ȭ������ ���ư����� x(X) �Է�]");
			String date=scan.nextLine();
			if(date.contentEquals("x")||date.contentEquals("X")) {
				return date;
			}else if(date.length()!=8) {
				System.out.println("�ùٸ��� ���� �Է°��Դϴ�.");
				continue;
			}
			if(isInteger(date)) {
				year=Integer.parseInt(date)/10000;
				month=(Integer.parseInt(date)%10000)/100;
				day=(Integer.parseInt(date)%10000)%100;
			}else {
				System.out.println("�ùٸ��� ���� �Է°��Դϴ�.");
				continue;
			}
			
			if(Integer.parseInt(date)<nowdate) {
				System.out.println("�ùٸ��� ���� �Է°��Դϴ�.");
				continue;
			}else if(year!=2020||month>12||month<1||day<1||day>31) {
				System.out.println("�ùٸ��� ���� �Է°��Դϴ�.");
				continue;
			}else if(checkDate(Integer.parseInt(date))==-1) {
				System.out.println("�ش� ��¥�� ��ȭ������ �����ϴ�.");
				System.out.println("�ٽ� ��¥�� �Է����ּ���");
				continue;
			}
			return date;
		}
	}
	//��ȭ ���Ŵܰ�
	public int checkDate(int date) {          //�ش� ��¥�� �´� ��ȭ������ �ִ��� Ȯ��
		int size=m_list.size();
		int ok=-1;
		for(int i=0;i<size;i++) {
			if(date==m_list.get(i).getDate()) {
				ok=1;
			}
		}
		return ok;
	}
	//��ȭ ���Ŵܰ�
	public void showSelectedMovie(int date) {          //���õ� ��¥�� ��ȭ ����Ʈ�� �����ش�
		System.out.println("<��ȭ ����Ʈ>");
		for(int i=0;i<m_list.size();i++) {
			if(!m_list.get(i).getTime().equals("00000000")&&m_list.get(i).getDate()==date)
				System.out.println(m_list.get(i));
		}
	}
	//��ȭ ���Ŵܰ�
	public String selectMovie(int date) {             //���ڷ� ���� ��¥�� ������ ��ȭ�� ����ڿ��� ������ ���� ��ȭ ���� id�� �޾Ƽ� �����Ѵ�
		Scanner scan=new Scanner(System.in);
		showSelectedMovie(date);
		String idx="null";
		int ok=-1;
		while(true) {
			System.out.println("��� ��ȭ�� �����Ͻðڽ��ϱ�?");
			System.out.println("[���� : ����ȭ������ ���ư����� x(X) �Է�]");
			idx=scan.nextLine();
			if(idx.contentEquals("x")||idx.contentEquals("X")) {
				return idx;
			}else if(isInteger(idx)){
				//���ڷθ� �̷����
			}else {
				System.out.println("�ùٸ��� ���� �Է°��Դϴ�.");
				continue;
			}
			//�ش糯¥�� �ִ� ��ȭ�� id���� �˻�
			for(int i=0 ;i<m_list.size();i++) {
				if(m_list.get(i).getDate()==date && m_list.get(i).getId()==Integer.parseInt(idx)) {
					ok=1;
					break;
				}
			}
			if(ok==-1) {
				System.out.println("�ùٸ��� ���� �Է°��Դϴ�.");
				continue;
			}
			
			break;
		}
		return idx;
	}
	public int checkingBookNum(int m_id) {
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
	//��ȭ ���Ŵܰ�
	public String selectNumOfPersons(int m_id) {           //��ȭ ���� �ο��� �����Ѵ�
		Scanner scan=new Scanner(System.in);
		String idx;
		int Bnum=checkingBookNum(m_id);
		int emptyseat=64-Bnum;
		System.out.println("���� �¼� �� : "+emptyseat);
		while(true) {
			System.out.println("�� ����� �����Ͻðڽ��ϱ�?");
			System.out.println("[���� : ����ȭ������ ���ư����� x(X) �Է�]");
			idx=scan.nextLine();
			if(idx.contentEquals("x")||idx.contentEquals("X")) {
				return idx;
			}else if(isInteger(idx)){
				//���ڷθ� �̷����
			}else {
				System.out.println("�ùٸ��� ���� �Է°��Դϴ�.");
				continue;
			}
			if(Integer.parseInt(idx)<1||Integer.parseInt(idx)>64) {
				System.out.println("�ùٸ��� ���� �Է°��Դϴ�.");
				continue;
			}else if(Integer.parseInt(idx)>emptyseat){
				System.out.println("���� �¼��� �ʰ� �Ͽ����ϴ�.");
				continue;
			}else
				break;
		}
		return idx;
	}
	//��ȭ ���Ŵܰ�
	public void showSeat(int id,UserManager um,MovieManager mm) {    //�¼� ����Ʈ�� �����ش�
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
//Ȯ�ο�			System.out.println(tm.t_list.get(i).getMovie().getId()+","+tm.t_list.get(i).getS_id()+","+id);
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
					System.out.print("�� ");
				else if(arr2[i][j]==1)
					System.out.print("�� ");
			}
			System.out.print("\n");
		}
	}
	//��ȭ ���Ŵܰ�
	public boolean checkingOverlap(String idx,int m_id) {            //�Է¹��� �¼��� ���� ������ ���ŵǾ� �ִ� �¼��̶� �ߺ��� �Ǵ��� üũ�ϴ� �Լ�
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
					if(rs.getInt(3)==s_id[0]&&rs.getInt(1)==m_id) {
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
	//��ȭ ���Ŵܰ�
	public String[] selectSeat(int num,int id,UserManager um,MovieManager mm) {  //�¼��� ���ù޾� ���ڿ� �迭�� �����Ѵ�
		Scanner scan=new Scanner(System.in);
		showSeat(id,um,mm);
		String idx[]=new String[num];
		for(int i=0;i<num;i++) {
			while(true) {
				System.out.println("��� �¼��� �����Ͻðڽ��ϱ�?");
				System.out.println("[���� : ����ȭ������ ���ư����� x(X) �Է�]");
				idx[i]=scan.nextLine();
				idx[i]=idx[i].replaceAll("\\p{Z}","");
				idx[i]=idx[i].toUpperCase();
				if(i>=1) {
					if(idx[i].contentEquals(idx[i-1])) {
						System.out.println("�̹� ���ŵ� �¼��Դϴ�. �ٽ� �Է����ּ��� ");
						continue;
					}
				}
				char[] ch=idx[i].toCharArray();
				if(idx[i].contentEquals("x")||idx[i].contentEquals("X")) {
					return idx;
				}
				if(idx[i].length()!=2) {
					System.out.println("�ùٸ��� ���� �Է°��Դϴ�.");
					continue;
				}else if(ch[0]<65||ch[0]>72||ch[1]<49||ch[1]>56) {
					System.out.println("�ùٸ��� ���� �Է°��Դϴ�.");
					continue;
				}else if(checkingOverlap(idx[i],id)) {
					System.out.println("�̹� ���ŵ� �¼��Դϴ�. �ٽ� �Է����ּ���");
					continue;
				}
				break;
			}
		}
		
		return idx;
	}
	//��ȭ ���Ŵܰ� 
	public String determine() { //���� Ȯ���ܰ�
		Scanner scan=new Scanner(System.in);
		System.out.println("���� ������ Ȯ���Ͻðڽ��ϱ�?(y/n)");
		System.out.println("[���� : ����ȭ������ ���ư����� x(X) �Է�]");
		String idx=scan.nextLine();
		return idx;
	}
	//��ȭ ���Ŵܰ�
	public int[] translation(String[] str,int num) {  //�¼��� �Է¹����� �̿� �ش��ϴ� int�� s_id�� �ٲ��ִ� �Լ�
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
	public String translationToStr(int s_id) {            //s_id�� �Է¹����� �̸� str���� �ٲ��ִ� �Լ�
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
	// ��ȭ ���� �ܰ� main
	public int book(String u_id,TicketManager tm,UserManager um,MovieManager mm) {
		System.out.println("����ڸ��>��ȭ����>��¥����");
		int nowdate = showDate();
		String date = getDate(nowdate); //��ȭ��¥
		if(date.contentEquals("x")||date.contentEquals("X")) {
			return 0;
		}
		System.out.println("����ڸ��>��ȭ����>��¥����>��ȭ����");
		System.out.println(date);
		String idx=selectMovie(Integer.parseInt(date));  //��ȭ id
		if(idx.contentEquals("x")||idx.contentEquals("X")) {
			return 0;
		}
		int idx_2=Integer.parseInt(idx);           //��ȭ idx
		String Mname=null;
		for(int i=0;i<m_list.size();i++) {
			if(m_list.get(i).getId()==idx_2) {
				Mname=m_list.get(i).getName();
				idx_2=i;
				break;
			}
		}
//Ȯ�ο�	System.out.println("��ȭ id : "+idx+"index : "+idx_2);
		System.out.println("����ڸ��>��ȭ����>��¥����>��ȭ����>�μ�����");
		System.out.println(": "+ date +"\\  "+ Mname +"��ȭ"+"\\  "+ m_list.get(idx_2).getTime() +"\\  "+ m_list.get(idx_2).getTheater_num()+"��");
		String idx_3=selectNumOfPersons(Integer.parseInt(idx)); //���� �μ�
		if(idx_3.contentEquals("x")||idx_3.contentEquals("X")) {
			return 0;
		}
		int num=Integer.parseInt(idx_3);  //���� �μ�
		
		System.out.println("����ڸ��>��ȭ����>��¥����>��ȭ����>�μ�����>��ȭ�¼�����");
		System.out.println(": "+ date +"\\  "+ Mname +"��ȭ"+"\\  "+ m_list.get(idx_2).getTime() +"\\  "+ m_list.get(idx_2).getTheater_num()+"��"+"\\  "+idx_3+"��");
		String idx_4[]=new String[num];
		idx_4=selectSeat(num,Integer.parseInt(idx),um,mm); //�¼� ���� �迭
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
		
		
		System.out.println("����ڸ��>��ȭ����>��¥����>��ȭ����>�μ�����>��ȭ�¼�����>����Ȯ��");
		System.out.println("���� ����");
		System.out.println(": "+ date +"\\  "+ Mname +"��ȭ"+"\\  "+ m_list.get(idx_2).getTime() +"\\  "+ m_list.get(idx_2).getTheater_num()+"��"+"\\  "+idx_3+"��");
		while(true) {
			String y_or_n = determine(); 
			if(y_or_n.contentEquals("y")||y_or_n.contentEquals("Y")) {
				System.out.println("���Ÿ� Ȯ���մϴ�.");
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
				System.out.println("���Ÿ� ����մϴ�. ����ȭ������ ���ư��ϴ�.");
				return 0;
			}else if(y_or_n.contentEquals("x")||y_or_n.contentEquals("X")) {
				return 0;
			}else {
				System.out.println("�ùٸ��� ���� �Է°��Դϴ�.");
				continue;
			}
			break;
		}
		
		return 1;	
		
	}
	//���� Ȯ�� �� ��Ҵܰ�
	public int[] showBooklist(String u_id) {  //�α��ε� ���̵�� ������ ��ȭ ������ �����ش�, ������ Ƽ���� idx�� �迭�� ����
		int size=0;
		String sql="select * from ticket";
		String s_info;
		if(conn!=null) {
			try {
				pstmt=conn.prepareStatement(sql);
				rs=pstmt.executeQuery();
				while(rs.next()) {
					if(rs.getString(2).contentEquals(u_id)) {
						s_info=translationToStr(rs.getInt(3));
						//System.out.println("Ƽ�� �ε��� : "+rs.getInt(4)+"\t"+getMovie(rs.getInt(1)).getDate()+"\t"+getMovie(rs.getInt(1)).getName()+"\t"+getMovie(rs.getInt(1)).getTime()+"\t"+getMovie(rs.getInt(1)).getTheater_num()+"\t"+"�¼���ȣ"+rs.getInt(3));
						System.out.printf("Ƽ�� �ε��� : %-3d\t%-10d\t%-10s\t%-10s\t%-10d\t�¼���ȣ : %-3s\n",rs.getInt(4),getMovie(rs.getInt(1)).getDate(),getMovie(rs.getInt(1)).getName(),getMovie(rs.getInt(1)).getTime(),getMovie(rs.getInt(1)).getTheater_num(),s_info);
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
	//���� Ȯ�� �� ��� �ܰ� main
	public int checkBookandCancel(String u_id) {
		Scanner scan=new Scanner(System.in);
		System.out.println("����ڸ��>���� Ȯ�� �� ���");
		System.out.println("��������");
		int arr[]=showBooklist(u_id);
		while(true) {
			System.out.println("����Ϸ��� ���ų����� �Է����ּ���");
			System.out.println("[���� : ����ȭ������ ���ư����� x(X) �Է�]");
			String idx=scan.nextLine();
			int ok=-1;
			if(idx.contentEquals("x")||idx.contentEquals("X")) {
				return 0;
			}else if(isInteger(idx)) {
				ok=1;
			}else {
				System.out.println("�ùٸ��� ���� �Է°��Դϴ�");
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
				System.out.println("�ùٸ��� ���� �Է°��Դϴ�.");
				continue;
			}
			break;
		}	
		return 0;	
	}
	//��ȭ �˻� �ܰ�
	public void SearchMovieByAge(int age) {          //���� �������� �˻�
		if(age>0&&age<7) {
			age=0;
		}else if(age>=7&&age<=11) {
			age=7;
		}else if(age>=12&&age<=14) {
			age=12;
		}else if(age>=15&&age<=18) {
			age=15;
		}else if(age>=19) {
			age=19;
		}
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
					System.out.println("ã���ô� ��ȭ ������ �����ϴ�.");
				}
			} catch (SQLException e) {
				// TODO: handle exception
				System.out.println("��ȭ ���̺� �о���� ����!");
				e.printStackTrace();
			}
		}
	}
	//��ȭ �˻� �ܰ�
	public int SearchMovieByName(String name) {     //��ȭ �������� �˻�
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
					System.out.println("���Ե� �ܾ��� ��ȭ�� �����ϴ�. �ٽ� �Է����ּ���.");
			} catch (SQLException e) {
				// TODO: handle exception
				System.out.println("��ȭ ���̺� �о���� ����!");
				e.printStackTrace();
			}
		}
		return cnt;
	}
	//��ȭ �˻� �ܰ� main
	public int Search() {
		Scanner scan=new Scanner(System.in);
		boolean escape=true;
		String idx;
		while(escape) {
			System.out.println("����ڸ��>��ȭ�˻�");
			while(true) {
				System.out.println("1.��ȭ���� �˻� 2.�������� �˻�");
				System.out.println("[���� : ����ȭ������ ���ư����� x(X) �Է�]");
				idx=scan.nextLine();
				if(idx.contentEquals("x")||idx.contentEquals("X")) {
					return 0;
				}else if(idx.contentEquals("")) {
					System.out.println("�ùٸ��� ���� �Է°��Դϴ�.");
					continue;
				}else if(isInteger(idx)) {
					//���ڷθ� �̷���� ���ڿ��̴�
				}else{
					System.out.println("�ùٸ��� ���� �Է°��Դϴ�.");
					continue;
				}
				if(Integer.parseInt(idx)<1||Integer.parseInt(idx)>2) {
					System.out.println("�ùٸ��� ���� �Է°��Դϴ�.");
					continue;
				}
				break;
			}
			switch(idx) {
			case "1" : {
				System.out.println("����ڸ��>��ȭ�˻�>��ȭ���� �˻�");
				while(true) {
					System.out.println("�˻��ϰ��� �ϴ� ��ȭ�� �Է����ּ���");
					System.out.println("[���� : ����ȭ������ ���ư����� x(X) �Է�]");
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
					System.out.println("[���� : ����ȭ������ ���ư����� x(X) �Է�]");
					String idx_3=scan.nextLine();
					if(idx_3.contentEquals("x")||idx_3.contentEquals("X")) {
						return 0;
					}else {
						System.out.println("�ùٸ��� ���� �Է°��Դϴ�");
						continue;
					}
				}
			}
	
			case "2" : {
				System.out.println("����ڸ��>��ȭ�˻�>�������� �˻�");
				String idx_2;
				while(true) {
					System.out.println("�� �� ���� ��ȭ�� ã���ó���? [��ü�̿밡|7��|12��|15��|19��]");
					System.out.println("[���� : ����ȭ������ ���ư����� x(X) �Է�]");
					idx_2=scan.nextLine();
					if(idx_2.contentEquals("x")||idx_2.contentEquals("X")) {
						return 0;
					}else if(isInteger(idx_2)) {
						//���ڿ��θ� �̷����
					}else {
						System.out.println("�ùٸ��� ���� �Է°��Դϴ�.");
						continue;
					}
					if(Integer.parseInt(idx_2)<0||Integer.parseInt(idx_2)>=100) {
						System.out.println("�ùٸ��� ���� �Է°��Դϴ�.");
						continue;
					}
					break;
					
				}
				SearchMovieByAge(Integer.parseInt(idx_2));
				while(true) {
					System.out.println("[���� : ����ȭ������ ���ư����� x(X) �Է�]");
					String idx_3=scan.nextLine();
					if(idx_3.contentEquals("x")||idx_3.contentEquals("X")) {
						return 0;
					}else {
						System.out.println("�ùٸ��� ���� �Է°��Դϴ�");
						continue;
					}
				}
			}
			case "x":
			case "X": {
				return 0;
			}
			default :{
				System.out.println("�ùٸ��� ���� �Է°��Դϴ�.");
				continue;
			}
			
			}
		
		}
		return 0;
	}
	
	
	public static boolean isInteger(String s) {	//���ڷθ� �̷���� ���ڿ����� �Ǻ��ϴ� �Լ�
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    return true;
	}
	
	public static boolean isPasswordType(String word) {	//��й�ȣ ���ǿ� �����ϴ� ���ڿ����� �Ǻ��ϴ� �Լ�
        return Pattern.matches("^[0-9a-zA-Z]*$", word);
    }
	public static boolean isNameType(String word) {	//�̸� ���ǿ� �����ϴ� ���ڿ����� �Ǻ��ϴ� �Լ�
        return Pattern.matches("^[a-zA-Z��-�R]*$", word);
    }
}

