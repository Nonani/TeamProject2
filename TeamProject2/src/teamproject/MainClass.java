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
		// �α���/ȸ������
		while(true) {
			idx=0;
			System.out.println("1. �α��� / 2. ȸ������");
			idx=scan.nextInt();
			
			if(idx==1) {
				numI = um.checkId();
				if(numI!=-1) {
					numP = um.checkPwd(numI);
					if(numP==1) {
						System.out.println("�α��� �Ϸ�");
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
				System.out.println("�߸� �Է��ϼ̽��ϴ�. �ٽ� �Է����ּ���.");
				continue;
			}
		}
		
	}

}
