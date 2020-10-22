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
					m_list.add(new Movie(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getInt(6),rs.getInt(7)));
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
		System.out.println("<��ȭ ����Ʈ>");
		for(int i=0;i<m_list.size();i++) {
			if(!m_list.get(i).getTime().equals("000000000000"))
				System.out.println(m_list.get(i));
		}
	}
	
	public void addMovie() {
		Scanner scan = new Scanner(System.in);
		System.out.println("��ȭ �߰�");
		String name, director, time;
		int age_limit, theater, date;
		
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
		System.out.println("�� ��¥�� �Է��ϼ��� : ");
		date = scan.nextInt();
		
		String sql="insert into movie(name, director, time, age_limit, theater, date) values(?,?,?,?,?)";
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
		int id = scan.nextInt();
		if(getMovie(id)!=null) {
			getMovie(id).setTime("000000000000");
			String sql = "update movie set time = ? where id = ?";
	        try {
	        	pstmt = conn.prepareStatement(sql);
	        	pstmt.setString(1, "000000000000");
				pstmt.setInt(2, id);
	            pstmt.execute();
	          
	        } catch (SQLException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
		}else {
			System.out.println("�ùٸ��� ���� �Է°��Դϴ�.");
			return;
		}
	}
	
	private Boolean checkTime(String _time) {
		
		Date today = new Date ();
		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYYMMddHHmm");
		dateFormat.setLenient(false);	//��ٷӰ� �˻�
		Date reqDate;
		try {
			reqDate = dateFormat.parse(_time);
			long reqDateTime = reqDate.getTime();
			
			today = dateFormat.parse(dateFormat.format(today));
			long todayTime = today.getTime();
			
			//������ ǥ��
//			long minute = (curDateTime - reqDateTime) / 60000;
//			System.out.println(reqDateTime-todayTime);
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
	
	public Movie getMovie(int id) {
		for(int i=0;i<m_list.size();i++) {
			//&&(!m_list.get(i).getTime().equals("0000000000"))
			if(m_list.get(i).getId()==id)
				return m_list.get(i); 
		}
		return null;
	}
	
	public void SearchTheater(int id) {
		int cnt=0;
		for(int i=0;i<m_list.size();i++) {
			if(m_list.get(i).getTheater_num()==id) {
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
				while(rs.next())
				{
					System.out.println(new Movie(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getInt(6), rs.getInt(7)));
				} 
			} catch (SQLException e) {
				// TODO: handle exception
				System.out.println("��ȭ ���̺� �о���� ����!");
				e.printStackTrace();
			}
		}
	}
	
	public int showDate() {
		Calendar cal=Calendar.getInstance();
		int year=cal.get(Calendar.YEAR);
		int month=cal.get(Calendar.MONTH)+1;
		int day=cal.get(Calendar.DAY_OF_MONTH);
		System.out.println("���� ��¥ : "+year+month+day);
		return year+month+day;
	}
	
	public String getDate(int nowdate) {
		Scanner scan=new Scanner(System.in);
		System.out.println("��¥�� �Է����ּ���. [ex)2020�� 10�� 10�� = 20201010]"
				+"[���� : ����ȭ������ ���ư����� x(X) �Է�]");
		while(true) {
			String date=scan.next();
			if(date.contentEquals("x")||date.contentEquals("X")) {
				return date;
			}else if(Integer.parseInt(date)<nowdate) {
				System.out.println("�ùٸ��� ���� �Է°��Դϴ�.");
				continue;
			}else if(checkDate(Integer.parseInt(date))==-1) {
				System.out.println("�ش� ��¥�� ��ȭ������ �����ϴ�.");
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
		System.out.println("<��ȭ ����Ʈ>");
		for(int i=0;i<m_list.size();i++) {
			if(!m_list.get(i).getTime().equals("000000000000")&&m_list.get(i).getDate()==date)
				System.out.println(m_list.get(i));
		}
	}
	
	public String selectMovie(int date) {
		Scanner scan=new Scanner(System.in);
		showSelectedMovie(date); //@@@@@@@@@@@@@@@@@���� ��¥ ��ȭ�� �������� �����ؾ���@@@@@@@@@@@@@@@@@@@@@@@@@@@
		System.out.println("��� ��ȭ�� �����Ͻðڽ��ϱ�?");
		System.out.println("[���� : ����ȭ������ ���ư����� x(X) �Է�]");
		String idx=scan.next();
		return idx;
	}
	
	public String selectNumOfPersons() {
		Scanner scan=new Scanner(System.in);
		System.out.println("�� ����� �����Ͻðڽ��ϱ�?");
		System.out.println("[���� : ����ȭ������ ���ư����� x(X) �Է�]");
		String idx=scan.next();
		return idx;
	}
	
	public void showSeat(int id,UserManager um,MovieManager mm) {
		System.out.println("######�¼�#####");
		int arr[][]=new int[8][8]; 
		int arr2[][]=new int[8][8];
		TicketManager tm=new TicketManager(um,mm);

		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				arr[i][j]=(8*i)+j+1;
				arr2[i][j]=0;
			}
		}
		
		System.out.println(tm.t_list.size());
		
		if(tm.t_list.size()!=0) {
			for(int i=0;i<tm.t_list.size();i++) {
				for(int j=0;j<8;j++) {
					for(int k=0;k<8;k++) {
						if(tm.t_list.get(i).getMovie().getId()==id&&tm.t_list.get(i).getS_id()==arr[j][k]) {
							arr2[j][k]=1;
						}
					}
				}
					
			}
		}
		
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				if(arr2[i][j]==0)
					System.out.print("��");
				else if(arr2[i][j]==1)
					System.out.print("��");
			}
			System.out.print("\n");
		}
	}
	
	public String[] selectSeat(int num,int id,UserManager um,MovieManager mm) {
		Scanner scan=new Scanner(System.in);
		showSeat(id,um,mm);
		String idx[]=new String[num];
		for(int i=0;i<num;i++) {
			System.out.println("��� �¼��� �����Ͻðڽ��ϱ�?");
			System.out.println("[���� : ����ȭ������ ���ư����� x(X) �Է�]");
			idx[i]=scan.next();
			idx[i]=idx[i].toUpperCase();
		}
		return idx;
	}
	
	public String determine() {
		Scanner scan=new Scanner(System.in);
		System.out.println("���� ������ Ȯ���Ͻðڽ��ϱ�?(y/n)");
		System.out.println("[���� : ����ȭ������ ���ư����� x(X) �Է�]");
		String idx=scan.next();
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
						System.out.println("Ƽ�� �ε��� : "+rs.getInt(4)+"\t"+getMovie(rs.getInt(1)).getDate()+"\t"+getMovie(rs.getInt(1)).getName()+"\t\t"+getMovie(rs.getInt(1)).getTime()+"\t"+getMovie(rs.getInt(1)).getTheater_num()+"\t"+"�¼���ȣ"+rs.getInt(3));
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
		System.out.println("����ڸ��>���� Ȯ�� �� ���");
		System.out.println("��������");
		int arr[]=showBooklist(u_id);
		while(true) {
			System.out.println("����Ϸ��� ���ų����� �Է����ּ���");
			System.out.println("[���� : ����ȭ������ ���ư����� x(X) �Է�]");
			String idx=scan.next();
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
				System.out.println("�ùٸ��� ���� �Է°��Դϴ�.");
				continue;
			}
			break;
		}
		
		return 0;
		
	}
	
	public int book(String u_id,int t_size,UserManager um,MovieManager mm) {
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
		int idx_2=Integer.parseInt(idx)-1;
			
		System.out.println("����ڸ��>��ȭ����>��¥����>��ȭ����>�μ�����");
		System.out.println(": "+ date +"\t"+ m_list.get(idx_2).getName() +"��ȭ"+"\t"+ m_list.get(idx_2).getTime() +"\t"+ m_list.get(idx_2).getTheater_num()+"��");
		String idx_3=selectNumOfPersons(); //���� �μ�
		if(idx_3.contentEquals("x")||idx_3.contentEquals("X")) {
			return 0;
		}
		int num=Integer.parseInt(idx_3);  //���� �μ�
		
		System.out.println("����ڸ��>��ȭ����>��¥����>��ȭ����>�μ�����>��ȭ�¼�����");
		System.out.println(": "+ date +"\t"+ m_list.get(idx_2).getName() +"��ȭ"+"\t"+ m_list.get(idx_2).getTime() +"\t"+ m_list.get(idx_2).getTheater_num()+"��"+"\t"+idx_3+"��");
		String idx_4[]=new String[num];
		idx_4=selectSeat(num,Integer.parseInt(idx),um,mm); //�¼� ���� �迭
		int s_id[]=new int[num];
		s_id=translation(idx_4,num);
		
		
		
		System.out.println("����ڸ��>��ȭ����>��¥����>��ȭ����>�μ�����>��ȭ�¼�����>����Ȯ��");
		System.out.println("���� ����");
		System.out.println(": "+ date +"\t"+ m_list.get(idx_2).getName() +"��ȭ"+"\t"+ m_list.get(idx_2).getTime() +"\t"+ m_list.get(idx_2).getTheater_num()+"��"+"\t"+idx_3+"��");
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
							pstmt.setInt(4,t_size+i+1);
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
				System.out.println("��ȭ ���̺� �о���� ����!");
				e.printStackTrace();
			}
		}
	}
	
	public int Search() {
		Scanner scan=new Scanner(System.in);
		boolean escape=true;
		while(escape) {
			System.out.println("����ڸ��>��ȭ�˻�");
			System.out.println("1.��ȭ���� �˻� 2.�������� �˻�");
			System.out.println("[���� : ����ȭ������ ���ư����� x(X) �Է�]");
			String idx=scan.next();
			switch(idx) {
			case "1" : {
				System.out.println("����ڸ��>��ȭ�˻�>��ȭ���� �˻�");
				System.out.println("[���� : ����ȭ������ ���ư����� x(X) �Է�]");
				String idx_2=scan.next();
				if(idx_2.contentEquals("x")||idx_2.contentEquals("X")) {
					break;
				}
				SearchMovie(idx_2);
				System.out.println("[���� : ����ȭ������ ���ư����� x(X) �Է�]");
				String idx_3=scan.next();
				if(idx_3.contentEquals("x")||idx_3.contentEquals("X")) {
					return 1;
				}else {
					break;
				}
			}
	
			case "2" : {
				System.out.println("����ڸ��>��ȭ�˻�>�������� �˻�");
				System.out.println("�� �� �̻� ���� ���� ��ȭ�� ã���ó���?");
				System.out.println("[���� : ����ȭ������ ���ư����� x(X) �Է�]");
				String idx_2=scan.next();
				if(idx_2.contentEquals("x")||idx_2.contentEquals("X")) {
					break;
				}
				SearchMovieByAge(Integer.parseInt(idx_2));
				System.out.println("[���� : ����ȭ������ ���ư����� x(X) �Է�]");
				String idx_3=scan.next();
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
				System.out.println("�ùٸ��� ���� �Է°��Դϴ�.");
				continue;
			}
			
			}
		
		}
		return 0;
	}
	
}

