package teamproject;
import java.util.Scanner;
//test
public class MainClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		UserManager um = new UserManager();
		MovieManager mm = new MovieManager();
		TicketManager tm = new TicketManager(um, mm);
//		tm.showAll();
//		um.showAll();
//		mm.showAll();
		
		Scanner scan = new Scanner(System.in);
		// �α���/ȸ������
		while(true) {
			um.readDB();
			System.out.println("1. �α��� / 2. ȸ������");
			System.out.print("input : ");
			String idx = scan.nextLine();
			switch(idx) {
			case "1":{	//1: �α���
				int numI = um.checkId();
				if(numI!=-1) {
					int numP = um.checkPwd(numI);
			
					switch(numP){
					case 1:	//������ �������� �α��� ����
					{	//������ ���
						System.out.println("������ ���");
						Boolean excape = true;
						while(excape) {
							System.out.println("1: ��ȭ �߰�/����\t2: �˻�\t3: �α׾ƿ�");
							String _idx = scan.nextLine();
							switch(_idx) {
							case "1":
							{
								System.out.println("������ ��� > ��ȭ �߰�/����");
								System.out.println("1: ��ȭ �߰�\t2: ��ȭ ����\n[���� : ����ȭ������ ���ư����� x(X) �Է�]");
								String __idx = scan.nextLine();
								if(__idx.equals("1"))
								{
									System.out.println("������ ��� > ��ȭ �߰�/���� > �߰�");
									mm.addMovie();
								}
									
								else if(__idx.equals("2")) {
									System.out.println("������ ��� > ��ȭ �߰�/���� > ����");
									mm.delMovie();
								}else if(__idx.equals("x")||__idx.equals("X")) {
									break;
								}else
									System.out.println("�ùٸ��� ���� �Է°��Դϴ�.");
							}
								break;
							case "2":
							{
								System.out.println("������ ��� > �˻�");
								System.out.println("1: ����� �˻�\t2.��ȭ �˻�\t3.�󿵰� �˻�\n" + 
										"[���� : ����ȭ������ ���ư����� x(X) �Է�]");
								
								String __idx = scan.nextLine();
								if(__idx.equals("1")) {
									System.out.println("������ ��� > �˻� > ����� �˻�");
									
									System.out.print("�˻��� ������� id�� �Է��ϼ��� : ");
									String u_id = scan.nextLine();
									tm.SearchUser(u_id);
								}
								
								else if(__idx.equals("2")) {
									System.out.println("������ ��� > �˻� > ��ȭ �˻�");
				
									mm.showAll();
									System.out.print("��ȭ �̸��� �Է��ϼ��� : ");
									String name = scan.nextLine();
									mm.SearchMovie(name);
								}
								else if(__idx.equals("3")) {
									System.out.println("������ ��� > �˻� > �󿵰� �˻�");
									
									//��ü �󿵰� ��±�� ���� ����
									mm.showTotalTheather();
		
									
									System.out.print("�˻��� �󿵰��� ��ȣ�� �Է��ϼ��� : ");
									try {
										int t_id = scan.nextInt();
										scan.nextLine();
										mm.SearchTheater(t_id);
									} catch (Exception e) {
										// TODO: handle exception
										scan.nextLine();
										System.out.println("�ùٸ��� ���� �Է°��Դϴ�.");
										
									}
									
									
									
									
								}else if(__idx.equals("x")||__idx.equals("X")) {
									break;
								}else
									System.out.println("�ùٸ��� ���� �Է°��Դϴ�.");
								
							}
								break;
							case "3":
								System.out.println("�α׾ƿ� ����");
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
						Boolean escape = true;
						while(escape) {
							TicketManager tm_1=new TicketManager(um,mm);
							System.out.println("1: ��ȭ ����\\ 2: ��ȭ Ȯ�� �� ���\\ 3: ��ȭ�˻�\\ 4: �α׾ƿ�");
							System.out.print("input : ");
							String idx_1 = scan.nextLine();
							switch(idx_1) {
							case "1":{
								int a = mm.book(um.u_list.get(numI).getId(),tm_1,um,mm);
							}
							break;
							case "2":{ //��ȭ ��� �ܰ� ���� ��ҽ� ticket DB�� u_id�� NULL�� �ٲ��.
								int b = mm.checkBookandCancel(um.u_list.get(numI).getId());
							}
							break;
							case "3":{
								int c = mm.Search();
								if(c==1) {
									continue;
								}else if(c==0) {
									break;
								}
							}
							
							case "x":
							case "X":
							case "4":
								escape=false;
								break;
							default:
								System.out.println("�ùٸ��� ���� �Է°��Դϴ�.");
							}
						
						}
					}
					break;
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