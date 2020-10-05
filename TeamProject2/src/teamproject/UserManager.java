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
	public int checkId() {
		System.out.println("���̵� �Է����ּ���.");
		Scanner str=new Scanner(System.in);
		String Id=str.next();
		int ok=-1;
		for(int i=0;i<u_list.size();i++) {
			if(Id.equals(u_list.get(i).getId())) {
				System.out.println("���̵� ��ġ�մϴ�.");
				ok=i;
				break;
			}
		}
		if(ok==-1) {
			System.out.println("�������� �ʴ� ���̵��Դϴ�.");
			System.out.println("�α���/ȸ������ ����ȭ������ ���ư��ϴ�.");
		}
		return ok;
	}
	
	public int checkPwd(int num) {
		System.out.println("��й�ȣ�� �Է����ּ���.");
		Scanner str=new Scanner(System.in);
		String Pwd=str.next();
		int ok=-1;
		if(Pwd.equals(u_list.get(num).getPwd())) {
			System.out.println("��й�ȣ�� ��ġ�մϴ�");
			ok=1;
		}else{
			System.out.println("��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
			System.out.println("�α���/ȸ������ ����ȭ������ ���ư��ϴ�.");
		}
		return ok;
	}
	
	public void registerUser() {
		while(true) {
			int ok=-1;
			Scanner scan=new Scanner(System.in);
			System.out.println("���̵� �Է��ϼ���");
			String Id=scan.next();
			for(int i=0;i<u_list.size();i++) {
				if(Id.contentEquals(u_list.get(i).getId())){
					System.out.println("���̵� �ߺ�! �ٸ� ���̵� �Է��ϼ���");
					ok=1;
					break;
				}
			}
			
			if(ok==1) {
				continue;
			}
			System.out.println("��й�ȣ�� �Է��ϼ���");
			String Pwd=scan.next();
			System.out.println("��й�ȣ Ȯ���� ���� �ٽ� �Է����ּ���");
			String Pwd2=scan.next();
			if(!Pwd.contentEquals(Pwd2)) {
				System.out.println("��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
				continue;
			}
			System.out.println("�̸� ��ȭ��ȣ ���̸� ������� �Է����ּ���");
			String name=scan.next();
			String phone=scan.next();
			int age=scan.nextInt();
			String sql="insert into user values(?,?,?,?,?)";
			if(conn!=null) {
				try {
					pstmt=conn.prepareStatement(sql);
					pstmt.setString(1, Id);
					pstmt.setString(2, Pwd);
					pstmt.setString(3, name);
					pstmt.setString(4, phone);
					pstmt.setInt(5, age);
					pstmt.execute();
				} catch(SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			showAll();
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

}
