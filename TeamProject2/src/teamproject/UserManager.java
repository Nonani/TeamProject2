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
	//로그인 단계
	public int checkId() {
		System.out.println("아이디를 입력해주세요.");
		System.out.print("input : ");
		Scanner scan=new Scanner(System.in);
		String Id=scan.nextLine();
		int ok=-1;
		
		for(int i=0;i<u_list.size();i++) {
			if(Id.equals(u_list.get(i).getId())) {
//				System.out.println("아이디가 일치합니다.");
				ok=i;
				break;
			}
		}
		return ok;
	}
	//로그인 단계
	public int checkPwd(int num) {
		System.out.println("비밀번호를 입력해주세요.");
		System.out.print("input : ");
		Scanner scan=new Scanner(System.in);
		String Pwd=scan.nextLine();
		if(num==0&&Pwd.equals("admin"))	//admin인 경우 0
			return 1;
		else if(Pwd.equals(u_list.get(num).getPwd())) {	//일반 유저인 경우
			return 2;
		}else	//로그인 실패
			return 3;
		
	}
	//회원가입 단계
	public boolean checkingSpecialChar(String str) {
		 if(!str.matches("[0-9|a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힝]*")){
//확인용			 System.out.println("특수문자있음");
			 return true;
		 }else{
//확인용			 System.out.println("특수문자없음");
			 return false;
		}
		
	}
	//회원가입 단계
		public boolean checkingisEng(String str) {
			 if(!str.matches("[0-9|a-z|A-Z|]*")){
//확인용				 System.out.println("한글 또는 특수문자있음");
				 return true;
			 }else{
//확인용				 System.out.println("조건에 맞음");
				 return false;
			}
			
		}
	//회원가입 main
	public void registerUser() {  
		while(true) {
			int ok=1;
			Scanner scan=new Scanner(System.in);
			String Id ,Pwd, name, phone, age;
			while(true) {
				System.out.println("아이디를 입력하세요");
				System.out.print("input : ");
				Id=scan.nextLine();
				
				if(checkingisEng(Id)==true) {  //특수문자 포함여부 확인
					System.out.println("올바르지 않은 입력값입니다.");
					continue;
				}else if(Id.length()<8||Id.length()>15) {    //글자수 범위 예외처리
					System.out.println("올바르지 않은 입력값입니다.");
					continue;
				}
				break;
			}
			for(int i=0;i<u_list.size();i++) {
				if(Id.contentEquals(u_list.get(i).getId())){
					System.out.println("아이디 중복! 다른 아이디를 입력하세요");
					ok=-1;
					break;
				}
			}
			if(ok==-1) {
				continue;
			}
			while(true) {
				System.out.println("비밀번호를 입력하세요");
				System.out.print("input : ");
				Pwd=scan.nextLine();
				if(checkingisEng(Pwd)==true) {
					ok=-1;
					System.out.println("올바르지 않은 입력값입니다.");
					continue;
				}else if(Pwd.length()<8||Pwd.length()>15) {
					ok=-1;
					System.out.println("올바르지 않은 입력값입니다.");
					continue;
				}
				break;
			}
			System.out.println("비밀번호 확인을 위해 다시 입력해주세요");
			System.out.print("input : ");
			String Pwd2=scan.nextLine();
			if(!Pwd.contentEquals(Pwd2)) {
				ok=-1;
				System.out.println("비밀번호가 일치하지 않습니다.");
				continue;
			}
			while(true) {
				System.out.println("이름을 입력해주세요");
				System.out.print("input : ");
				name=scan.nextLine();
				if(name.length()<2||name.length()>30||checkingSpecialChar(name)==true) {
					ok=-1;
					System.out.println("올바르지 않은 입력값입니다.");
					continue;
				}
				break;
			}
			String regExp="^[0-9]+$";     //숫자만 허용하는 정규 표현식
			while(true) {
				System.out.println("전화번호를 입력해주세요");
				System.out.print("input : ");
				phone=scan.nextLine();
				if(phone.length()<10||phone.length()>11) {
					ok=-1;
					System.out.println("올바르지 않은 입력값입니다.");
					continue;
				}
				if(phone.matches(regExp)) {
					ok=1;
				}else {
					ok=-1;
					System.out.println("올바르지 않은 입력값입니다.");
					continue;
				}
				for(int i=0;i<u_list.size();i++) {
					if(phone.contentEquals(u_list.get(i).getPhone())){
						System.out.println("전화번호 중복! 다른 전화번호를 입력하세요");
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
				System.out.println("나이를 입력해주세요");
				System.out.print("input : ");
				age=scan.nextLine();
				if(age.length()<0||age.length()>3) {
					ok=-1;
					System.out.println("올바르지 않은 입력값입니다.");
					continue;
				}
				if(age.matches(regExp)) {
					ok=1;
				}else {
					ok=-1;
					System.out.println("올바르지 않은 입력값입니다.");
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
