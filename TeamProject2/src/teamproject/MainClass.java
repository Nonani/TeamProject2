package teamproject;
import java.util.Scanner;

public class MainClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		UserManager um = new UserManager();
		MovieManager mm = new MovieManager();
		TicketManager tm = new TicketManager(um, mm);
		tm.showAll();
//		um.showAll();
//		mm.showAll();
		
		Scanner scan = new Scanner(System.in);
		// �α���/ȸ������
		while(true) {
			System.out.println("1. �α��� / 2. ȸ������");
			String idx = scan.nextLine();
			switch(idx) {
			case "1":{	//1: �α���
				int numI = um.checkId();
				if(numI!=-1) {
					int numP = um.checkPwd(numI);
			
					switch(numP){
					case 1:	//������ �������� �α��� ����
					{	//������ ���
						System.out.println("������ ���� �α��� ����");
						Boolean excape = true;
						while(excape) {
							System.out.println("1: ��ȭ �߰�/����\t2: �˻�\t3: �α׾ƿ�");
							String _idx = scan.nextLine();
							switch(_idx) {
							case "1":
							{
								System.out.println("1: ��ȭ �߰�\t2: ��ȭ ����\n[���� : ����ȭ������ ���ư����� x(X) �Է�]");
								String __idx = scan.nextLine();
								if(__idx.equals("1"))
									mm.addMovie();
								else if(__idx.equals("2"))
									mm.delMovie();
								else
									System.out.println("�߸��� �Է��Դϴ�.");
							}
								break;
							case "2":
							{
								System.out.println("1: ����� �˻�\t2.��ȭ �˻�\t3.�󿵰� �˻�\n" + 
										"[���� : ����ȭ������ ���ư����� x(X) �Է�]");
								
								String __idx = scan.nextLine();
								if(__idx.equals("1")) {
									System.out.println("����� �˻�");
									System.out.print("�˻��� ������� id�� �Է��ϼ��� : ");
									String u_id = scan.nextLine();
									tm.SearchUser(u_id);
								}
								
								else if(__idx.equals("2")) {
									System.out.println("��ȭ �˻�");
									mm.showAll();
									System.out.print("��ȭ �̸��� �Է��ϼ��� : ");
									String name = scan.nextLine();
									mm.SearchMovie(name);
								}
								else if(__idx.equals("3")) {
									System.out.println("�󿵰� �˻�");
									//��ü �󿵰� ��±�� ���� ����
									//mm.showTotalTheather()
		
									
									System.out.print("�˻��� �󿵰��� ��ȣ�� �Է��ϼ��� : ");
									int t_id = scan.nextInt();
									
									mm.SearchTheater(t_id);
								}else
									System.out.println("�߸��� �Է��Դϴ�.");
								
							}
								break;
								
							case "x":
							case "X":
							case "3":
								excape = false;
								break;
							default:
								System.out.println("�ùٸ��� ���� �Է°��Դϴ�.");
							}
						}
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
			case "2":	// 2: ȸ������
				um.registerUser();
				break;
			default:
				System.out.println("���α׷��� �����մϴ�.");
				return;
			}
		
		}

	}
}
