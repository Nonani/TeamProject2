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
		// �α���/ȸ������
		while(true) {
			int idx=0;
			System.out.println("1. �α��� / 2. ȸ������");
			idx=scan.nextInt();
			switch(idx) {
			case 1:{	//1: �α���
				int numI = um.checkId();
				if(numI!=-1) {
					int numP = um.checkPwd(numI);
					switch(numP){
					case 1:	//������ �������� �α��� ����
					{	//������ ���
						System.out.println("������ ���� �α��� ����");
						break;
					}
					case 2:	//�Ϲ� ������ �α��μ���
					{
						System.out.println("�α��� ����");
						break;
					}
					default:	//�α��� ����
						System.out.println("��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
						System.out.println("�α���/ȸ������ ����ȭ������ ���ư��ϴ�.");
						break;
					}
				}else {
					System.out.println("�������� �ʴ� ���̵��Դϴ�.");
					System.out.println("�α���/ȸ������ ����ȭ������ ���ư��ϴ�.");
					break;
				}
				
				break;
			}
			case 2:	// 2: ȸ������
				um.registerUser();
				break;
			default:
				System.out.println("���α׷��� �����մϴ�.");
				return;
			}
		
		}

	}
}
