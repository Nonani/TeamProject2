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
				System.out.println("유저테이블 읽어오기 실패!");
				e.printStackTrace();
			}
		}
	}
	public int checkId() {
		System.out.println("아이디를 입력해주세요.");
		Scanner str=new Scanner(System.in);
		String Id=str.next();
		int ok=-1;
		for(int i=0;i<u_list.size();i++) {
			if(Id.equals(u_list.get(i).getId())) {
				System.out.println("아이디가 일치합니다.");
				ok=i;
				break;
			}
		}
		if(ok==-1) {
			System.out.println("존재하지 않는 아이디입니다.");
			System.out.println("로그인/회원가입 선택화면으로 돌아갑니다.");
		}
		return ok;
	}
	
	public int checkPwd(int num) {
		System.out.println("비밀번호를 입력해주세요.");
		Scanner str=new Scanner(System.in);
		String Pwd=str.next();
		int ok=-1;
		if(Pwd.equals(u_list.get(num).getPwd())) {
			System.out.println("비밀번호가 일치합니다");
			ok=1;
		}else{
			System.out.println("비밀번호가 일치하지 않습니다.");
			System.out.println("로그인/회원가입 선택화면으로 돌아갑니다.");
		}
		return ok;
	}
	
	public void registerUser() {
		while(true) {
			int ok=-1;
			Scanner scan=new Scanner(System.in);
			System.out.println("아이디를 입력하세요");
			String Id=scan.next();
			for(int i=0;i<u_list.size();i++) {
				if(Id.contentEquals(u_list.get(i).getId())){
					System.out.println("아이디 중복! 다른 아이디를 입력하세요");
					ok=1;
					break;
				}
			}
			
			if(ok==1) {
				continue;
			}
			System.out.println("비밀번호를 입력하세요");
			String Pwd=scan.next();
			System.out.println("비밀번호 확인을 위해 다시 입력해주세요");
			String Pwd2=scan.next();
			if(!Pwd.contentEquals(Pwd2)) {
				System.out.println("비밀번호가 일치하지 않습니다.");
				continue;
			}
			System.out.println("이름 전화번호 나이를 순서대로 입력해주세요");
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
