package teamproject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

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
				System.out.println("�������̺� �о���� ����!");
				e.printStackTrace();
			}
		}
	}
	//�α��� �ܰ�
	public int checkId() {
		System.out.println("���̵� �Է����ּ���.");
		System.out.print("input : ");
		Scanner scan=new Scanner(System.in);
		String Id=scan.nextLine();
		int ok=-1;
		
		for(int i=0;i<u_list.size();i++) {
			if(Id.equals(u_list.get(i).getId())) {
//				System.out.println("���̵� ��ġ�մϴ�.");
				ok=i;
				break;
			}
		}
		return ok;
	}
	//�α��� �ܰ�
	public int checkPwd(int num) {
		System.out.println("��й�ȣ�� �Է����ּ���.");
		System.out.print("input : ");
		Scanner scan=new Scanner(System.in);
		String Pwd=scan.nextLine();
		if(num==0&&Pwd.equals("admin"))	//admin�� ��� 0
			return 1;
		else if(Pwd.equals(u_list.get(num).getPwd())) {	//�Ϲ� ������ ���
			return 2;
		}else	//�α��� ����
			return 3;
		
	}
	//ȸ������ �ܰ�
	public boolean checkingSpecialChar(String str) {
		 if(!str.matches("[0-9|a-z|A-Z|��-��|��-��|��-��]*")){
//Ȯ�ο�			 System.out.println("Ư����������");
			 return true;
		 }else{
//Ȯ�ο�			 System.out.println("Ư�����ھ���");
			 return false;
		}
		
	}
	//ȸ������ �ܰ�
		public boolean checkingisEng(String str) {
			 if(!str.matches("[0-9|a-z|A-Z|]*")){
//Ȯ�ο�				 System.out.println("�ѱ� �Ǵ� Ư����������");
				 return true;
			 }else{
//Ȯ�ο�				 System.out.println("���ǿ� ����");
				 return false;
			}
			
		}
	//ȸ������ main
	public void registerUser() {  
		while(true) {
			int ok=1;
			Scanner scan=new Scanner(System.in);
			String Id ,Pwd, name, phone, age;
			while(true) {
				System.out.println("���̵� �Է��ϼ���");
				System.out.print("input : ");
				Id=scan.nextLine();
				
				if(checkingisEng(Id)==true) {  //Ư������ ���Կ��� Ȯ��
					System.out.println("�ùٸ��� ���� �Է°��Դϴ�.");
					continue;
				}else if(Id.length()<8||Id.length()>15) {    //���ڼ� ���� ����ó��
					System.out.println("�ùٸ��� ���� �Է°��Դϴ�.");
					continue;
				}
				break;
			}
			for(int i=0;i<u_list.size();i++) {
				if(Id.contentEquals(u_list.get(i).getId())){
					System.out.println("���̵� �ߺ�! �ٸ� ���̵� �Է��ϼ���");
					ok=-1;
					break;
				}
			}
			if(ok==-1) {
				continue;
			}
			while(true) {
				System.out.println("��й�ȣ�� �Է��ϼ���");
				System.out.print("input : ");
				Pwd=scan.nextLine();
				if(checkingisEng(Pwd)==true) {
					ok=-1;
					System.out.println("�ùٸ��� ���� �Է°��Դϴ�.");
					continue;
				}else if(Pwd.length()<8||Pwd.length()>15) {
					ok=-1;
					System.out.println("�ùٸ��� ���� �Է°��Դϴ�.");
					continue;
				}
				break;
			}
			System.out.println("��й�ȣ Ȯ���� ���� �ٽ� �Է����ּ���");
			System.out.print("input : ");
			String Pwd2=scan.nextLine();
			if(!Pwd.contentEquals(Pwd2)) {
				ok=-1;
				System.out.println("��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
				continue;
			}
			while(true) {
				System.out.println("�̸��� �Է����ּ���");
				System.out.print("input : ");
				name=scan.nextLine();
				if(name.length()<2||name.length()>30||checkingSpecialChar(name)==true) {
					ok=-1;
					System.out.println("�ùٸ��� ���� �Է°��Դϴ�.");
					continue;
				}
				break;
			}
			String regExp="^[0-9]+$";     //���ڸ� ����ϴ� ���� ǥ����
			while(true) {
				System.out.println("��ȭ��ȣ�� �Է����ּ���");
				System.out.print("input : ");
				phone=scan.nextLine();
				if(phone.length()<10||phone.length()>11) {
					ok=-1;
					System.out.println("�ùٸ��� ���� �Է°��Դϴ�.");
					continue;
				}
				if(phone.matches(regExp)) {
					ok=1;
				}else {
					ok=-1;
					System.out.println("�ùٸ��� ���� �Է°��Դϴ�.");
					continue;
				}
				for(int i=0;i<u_list.size();i++) {
					if(phone.contentEquals(u_list.get(i).getPhone())){
						System.out.println("��ȭ��ȣ �ߺ�! �ٸ� ��ȭ��ȣ�� �Է��ϼ���");
						ok=-1;
						break;
					}
				}
				if(ok==-1) {
					continue;
				}
				break;
			}
			while(true) {
				System.out.println("���̸� �Է����ּ���");
				System.out.print("input : ");
				age=scan.nextLine();
				if(age.length()<0||age.length()>3) {
					ok=-1;
					System.out.println("�ùٸ��� ���� �Է°��Դϴ�.");
					continue;
				}
				if(age.matches(regExp)) {
					ok=1;
				}else {
					ok=-1;
					System.out.println("�ùٸ��� ���� �Է°��Դϴ�.");
					continue;
				}
				break;
			}
			String sql="insert into user values(?,?,?,?,?)";
			if(conn!=null) {
				try {
					pstmt=conn.prepareStatement(sql);
					pstmt.setString(1, Id);
					pstmt.setString(2, Pwd);
					pstmt.setString(3, name);
					pstmt.setString(4, phone);
					pstmt.setInt(5, Integer.parseInt(age));
					pstmt.execute();
				} catch(SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		}
	}
	
	@Override
	public void showAll() {
		// TODO Auto-generated method stub
		for(int i=0;i<u_list.size();i++) {
			System.out.println(u_list.get(i));
		}

	}
	
	public User getUser(String id) {
		for(int i=0;i<u_list.size();i++) {
			if(u_list.get(i).getId().equals(id))
				return u_list.get(i); 
		}
		return null;
	}

}
