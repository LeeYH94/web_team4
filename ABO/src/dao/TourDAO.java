package dao;

import java.util.ArrayList;
import java.util.Scanner;

import vo.TourDTO;

public class TourDAO {
	// �޷���� �޼ҵ�
	
	
	
	
	// ���� �޼ҵ�(ȸ��, ��ȸ��)
	// ����, �� �պ�, ȣ��
	public void pay(ArrayList<Object> user_choice, TourDTO user, int idx) {
		if(user == null) {
			//�α��� �޼ҵ�
			
			
		}
		
		if(user_choice.get(1) == null) {
			
		}
		
		
		
		
	}
	
	
	// �α��� �޼ҵ�()
	
	
	
	
	
	
	// �ߺ�Ȯ�� �޼ҵ�(Tour DTO user , int idx)
	
	
	
	
	
	// ȸ������(���̵�, ��й�ȣ, �̸�, ��ȭ��ȣ, �̸���)
	public void register() {

		
		
	}
	
	
	public void view() {

		Scanner sc = new Scanner(System.in);
		String title = "ABO �����\n";
		String menu = "1. ȸ������\n2. �α���\n3. ��ȸ�� �����ϱ�\n4. ����";
		String menu_1 = "1. ����������\n2. �����ϱ�\n3. �α׾ƿ�";
		String menu_1_1 = ""; // ���������� �޴�
		String menu_1_2 = "1. �Ϻ�\n2. �̱�\n3. �߱�";
		String[] arMenu_1_2 = {"�Ϻ�", "�̱�", "�߱�"};
		String menu_1_2_1 = "1. ��\n2. �պ�";
		String[] arMenu_1_2_1 = {"��", "�պ�"};
		String menu_1_2_2 = "1. Aȣ��\n2. Bȣ��";
		String[] arMenu_1_2_2 = {"Aȣ��", "Bȣ��", "Cȣ��"};
		int choice = 0, country_choice = 0, trip_choice = 0, hotel_choice = 0, round_choice = 0;
		ArrayList<String> user_choice = new ArrayList<String>();

		while (true) {
			System.out.println(title + menu);
			choice = sc.nextInt();

			// ����
			if (choice == 4) {
				break;
			}

			switch (choice) {
			// ȸ������ ����
			case 1:

				break;
			// �α��� ����

			case 2:
				// "1. ����������\n2. �����ϱ�\n3. �α׾ƿ�"
				System.out.println(menu_1);
				choice = sc.nextInt();

				switch (choice) {
				// ���������� ����
				case 1:
					System.out.println("����");
					break;
				// �����ϱ� ����
				case 2:
//					"1. �Ϻ�\n2. �̱�\n3. �߱�"
					System.out.println(menu_1_2);
					country_choice = sc.nextInt();
					user_choice.add(arMenu_1_2[country_choice - 1]);
					System.out.println("1.�װ��� ����\n2.ȣ�� ����");
					trip_choice = sc.nextInt();
					
					
					switch (trip_choice) {
					// �װ��� ���� ����
					case 1:
//						"1. ��\n2. �պ�\n"
						System.out.println(menu_1_2_1);
						trip_choice = sc.nextInt(); // ����
						user_choice.add(arMenu_1_2_1[trip_choice - 1]);

						switch (trip_choice) {
						// �� ����
						case 1:
//							�޷� ���(�޼ҵ�)
							break;
						// �պ� ����
						case 2:
//							�޷� ���(�޼ҵ�)
							break;
						// �ڷΰ���(����)
						case 3:
							break;
						}

						break;
					// ȣ�ڿ��� ����
					case 2:
						System.out.println(menu_1_2_2);
						hotel_choice = sc.nextInt();
						user_choice.add(arMenu_1_2_2[hotel_choice - 1]);
						switch (hotel_choice) {
						// A ȣ��
						case 1:
//						�޷� ���(�޼ҵ�)
							break;
						// Bȣ��
						case 2:
//						�޷� ���(�޼ҵ�)
							break;
						// �ڷΰ���
						case 3:
							break;
						}
						break;
					// �ڷΰ��� ����(����)
					case 3:
						break;
					}
					break;
				// �α׾ƿ� ����
				case 3:
					break;
				}
				break;
			// ��ȸ�� �����ϱ� ����
			case 3:
//				"1. �Ϻ�\n2. �̱�\n3. �߱�"
				System.out.println(menu_1_2);
				country_choice = sc.nextInt();
				
				System.out.println("1.�װ��� ����\n2.ȣ�� ����");
				trip_choice = sc.nextInt();
				switch (trip_choice) {
				// �װ��� ���� ����
				case 1:
//					"1. ��\n2. �պ�\n"
					System.out.println(menu_1_2_1);
					round_choice = sc.nextInt(); // ����

					switch (round_choice) {
					// �� ����
					case 1:
//						�޷� ���(�޼ҵ�)
						break;
					// �պ� ����
					case 2:
//						�޷� ���(�޼ҵ�)
						break;
					// �ڷΰ���(����)
					case 3:
						break;
					}

					break;
				// ȣ�ڿ��� ����
				case 2:
					System.out.println(menu_1_2_2);
					hotel_choice = sc.nextInt();
					switch (hotel_choice) {
					// A ȣ��
					case 1:
//					�޷� ���(�޼ҵ�)
						break;
					// Bȣ��
					case 2:
//					�޷� ���(�޼ҵ�)
						break;
					// �ڷΰ���
					case 3:
						break;
					}
					break;
				default:

				}
			}
		}
	
	}
	
	
	
	
}