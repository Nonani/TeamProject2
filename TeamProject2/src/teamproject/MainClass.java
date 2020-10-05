package teamproject;
import java.util.Scanner;

public class MainClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int numI=0;
		int numP=0;
		int idx;
		UserManager um = new UserManager();
		MovieManager mm = new MovieManager();
		um.showAll();
		mm.showAll();
		Scanner scan=new Scanner(System.in);
		// 로그인/회원가입
		while(true) {
			idx=0;
			System.out.println("1. 로그인 / 2. 회원가입");
			idx=scan.nextInt();
			
			if(idx==1) {
				numI = um.checkId();
				if(numI!=-1) {
					numP = um.checkPwd(numI);
					if(numP==1) {
						System.out.println("로그인 완료");
						break;
					}else {
						continue;
					}
				}else {
					continue;
				}
			}else if(idx==2) {
				um.registerUser();
			}else {
				System.out.println("잘못 입력하셨습니다. 다시 입력해주세요.");
				continue;
			}
		}
		
	}

}
