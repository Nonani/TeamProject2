package teamproject;
import java.util.Scanner;

public class MainClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		UserManager um = new UserManager();
		MovieManager mm = new MovieManager();
		um.showAll();
		mm.showAll();
		Scanner scan=new Scanner(System.in);
		// 로그인/회원가입
		while(true) {
			int idx=0;
			System.out.println("1. 로그인 / 2. 회원가입");
			idx=scan.nextInt();
			switch(idx) {
			case 1:{	//1: 로그인
				int numI = um.checkId();
				if(numI!=-1) {
					int numP = um.checkPwd(numI);
					switch(numP){
					case 1:	//관리자 계정으로 로그인 성공
					{	//관리자 모드
						System.out.println("관리자 모드로 로그인 성공");
						break;
					}
					case 2:	//일반 유저로 로그인성공
					{
						System.out.println("로그인 성공");
						break;
					}
					default:	//로그인 실패
						System.out.println("비밀번호가 일치하지 않습니다.");
						System.out.println("로그인/회원가입 선택화면으로 돌아갑니다.");
						break;
					}
				}else {
					System.out.println("존재하지 않는 아이디입니다.");
					System.out.println("로그인/회원가입 선택화면으로 돌아갑니다.");
					break;
				}
				
				break;
			}
			case 2:	// 2: 회원가입
				um.registerUser();
				break;
			default:
				System.out.println("프로그램을 종료합니다.");
				return;
			}
		
		}

	}
}
