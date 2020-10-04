package teamproject;

public class MainClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		UserManager um = new UserManager();
		MovieManager mm = new MovieManager();
		um.showAll();
		mm.showAll();
		
		System.out.println("1. 로그인 / 2. 회원가입");
		//로그인 회원가입 인덱스 선택받고
		//if 로그인 
//		{	//로그인의 경우 아이디 비밀번호를 입력받고
//			//아이디가 존재하지않는 경우
//			//아이디가 존재하고 비밀번호가 일치하는 경우
//			//아이디가 존재하나 비밀번호는 일치하지 않는 경우
//			//함수가 필요할 경우 유저 매니저에서 메소드를 정의해서 사용하세요!
//			//ex) um.Login(id, pwd);
//			System.out.println("아이디를 입력하세요 : ");
//			//
//			System.out.println("비밀번호를 입력하세요 : ");
//		}
//		//else 회원가입
//		{
//			//user 정보 입력받아서 user 객체 생성하면서 db에 저장 
//			//이미 존재하는 id의 경우 회원가입을 못하게 하는 처리 필수
//			//이것도 UserManager에서 메소드로 동작하게 해주세요
//			//ex)um.SignUp(id, pwd, name, ...);
//		}
		
		
		
		System.out.println();
	}

}
