package com.EmployeeManagementSystem.EmployeeManagement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Main {
	public static void main(String[] args) throws IOException {

		Configuration cnf = new Configuration();
		cnf.configure("config.xml");
		SessionFactory factory = cnf.buildSessionFactory();

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String usernameString = "root";
		String passwordString = "root";
		boolean databaseFlag = true;
		boolean loginFlag = true;
		String data_user;
		String data_pass;

		while (databaseFlag) {

			System.out.println("enter username of database");
			data_user = br.readLine();
			System.out.println("enter password of database");
			data_pass = br.readLine();

			if (data_user.equals(usernameString) && data_pass.equals(passwordString)) {
				databaseFlag = false;
			}

			if (databaseFlag) {
				System.out.println("incorrect password, try again");
			}
		}

		// ++++++++++++++++++++++ logged into database ++++++++++++++++++++++++++++++

		while (loginFlag) {
			System.out.println("enter personal username");
			String userId = br.readLine();
			System.out.println("enter personal password");
			String password = br.readLine();

			Pattern admin = Pattern.compile("(admin)([0-9]*)");
			Pattern user = Pattern.compile("(user)([0-9]*)");

			Matcher adminMatch = admin.matcher(userId);
			Matcher userMatch = user.matcher(userId);

			if (adminMatch.matches()) {
				loginFlag = false;
				System.out.println("-----------------successfully logged into admin account-------------");

				boolean x = true;
				while (x) {
					System.out.println(
							"1.add new employee\n2.get employee detail\n3.update employee detail\n4.remove employee\n5.view every employee\n6.exit app");
					int choice = Integer.parseInt(br.readLine());

					switch (choice) {
					// =============================================================================
					case 1:
						Employee e1 = new Employee();

						System.out.println("enter name of new employee");
						String name = br.readLine();
						System.out.println("address : ");
						String address = br.readLine();
						System.out.println("salary : ");
						Long salary = Long.parseLong(br.readLine());
						System.out.println("enter email : ");
						String email = br.readLine();
						System.out.println("enter phone number : ");
						Long phone = Long.parseLong(br.readLine());

						Pattern validateEmail = Pattern.compile("[A-Za-z0-9._-]+@[A-Za-z0-9._-]+\\.(com|edu|net)");
						Pattern validatePhone = Pattern.compile("[7-9]{1}\\d{9}");

						String phoneForVerify = Long.toString(phone);
						Matcher emailMatch = validateEmail.matcher(email);
						Matcher phonMatch = validatePhone.matcher(phoneForVerify);

						if (emailMatch.matches()) {
							if (phonMatch.matches()) {

								e1.setName(name);
								e1.setAddress(address);
								e1.setSalary(salary);
								e1.setEmail(email);
								e1.setPhone(phone);

								Session session = factory.openSession();
								Transaction tx = session.beginTransaction();

								session.save(e1);

								tx.commit();
								session.close();

							} else {
								System.out.println("details not submitted, invalid phone number");
							}
						} else {
							System.out.println("details not submitted, invalid email");
						}

						break;
					// =======================================================================================
					case 2:

						System.out.println("enter employee id to get detail");
						int getDetail = Integer.parseInt(br.readLine());

						Session session = factory.openSession();
						Transaction tx = session.beginTransaction();
						Employee e2 = session.get(Employee.class, getDetail);
						System.out.println("========================================");
						System.out.println("id:"+e2.getEmployeeId()+"\nname:"+e2.getName()+"\naddress:"+e2.getAddress()+"\nsalary:"+e2.getSalary()+"\ne-mail:"+e2.getEmail()+"\nphone:"+e2.getPhone());
						System.out.println("=========================================");

						tx.commit();
						session.close();

						break;
					// ======================================================================================
					case 3:
						System.out.println("options to update employee detail");
						System.out.println("1.E-mail\n2.address\n3.salary\n4.name\n5.phone");
						int updateChoice = Integer.parseInt(br.readLine());

						switch (updateChoice) {

							case 1:
								System.out.println("enter employee id to update e-mail");
								int id1 = Integer.parseInt(br.readLine());
	
								System.out.println("enter new e-mail");
								String newEmail = br.readLine();
	
								Pattern newEmailValidator = Pattern
										.compile("[A-Za-z0-9._-]+@[A-Za-z0-9._-]\\.(com|net|edu)");
								Matcher matcher = newEmailValidator.matcher(newEmail);
	
								if (matcher.matches()) {
	
									Session session1 = factory.openSession();
									Transaction tx1 = session1.beginTransaction();
									Employee e3 = session1.get(Employee.class, id1);
	
									e3.setEmail(newEmail);
									session1.saveOrUpdate(e3);
	
									tx1.commit();
									session1.close();
	
								}
								break;
							case 2:
								System.out.println("enter employee id to update Address");
								int id2 = Integer.parseInt(br.readLine());
	
								System.out.println("enter new address");
								String newAddress = br.readLine();
	
								Session session1 = factory.openSession();
								Transaction tx1 = session1.beginTransaction();
	
								Employee e3 = session1.get(Employee.class, id2);
	
								e3.setAddress(newAddress);
								session1.saveOrUpdate(e3);
	
								tx1.commit();
								session1.close();
	
								break;
							case 3:
								System.out.println("enter employee id to update Salary");
								int id3 = Integer.parseInt(br.readLine());
	
								System.out.println("enter new salary");
								long newSalary = Long.parseLong(br.readLine());
	
								Session session2 = factory.openSession();
								Transaction tx2 = session2.beginTransaction();
	
								Employee e4 = session2.get(Employee.class, id3);
	
								e4.setSalary(newSalary);
								session2.saveOrUpdate(e4);
	
								tx2.commit();
								session2.close();
	
								break;
							case 4:
								System.out.println("enter employee id to update Name");
								int id4 = Integer.parseInt(br.readLine());
	
								System.out.println("enter new name");
								String newName = br.readLine();
	
								Session session3 = factory.openSession();
								Transaction tx3 = session3.beginTransaction();
	
								Employee e5 = session3.get(Employee.class, id4);
	
								e5.setName(newName);
								session3.saveOrUpdate(e5);
	
								tx3.commit();
								session3.close();
	
								break;
							case 5:
								System.out.println("enter employee id to update Phone number");
								int id5 = Integer.parseInt(br.readLine());
	
								System.out.println("enter new phone number");
								long newPhone = Long.parseLong(br.readLine());
	
								String newPhToString = Long.toString(id5);
	
								Pattern newPhoneValidator = Pattern.compile("[7-9]{1}\\d{9}");
								Matcher matcher2 = newPhoneValidator.matcher(newPhToString);
	
									if (matcher2.matches()) {
		
										Session session4 = factory.openSession();
										Transaction tx4 = session4.beginTransaction();
		
										Employee e6 = session4.get(Employee.class, id5);
		
										e6.setPhone(newPhone);
										session4.saveOrUpdate(e6);
		
										tx4.commit();
										session4.close();
		
									} else {
										System.out.println("invalid number format, phone number cant be updated");
									}
	
								break;
							default:
								System.out.println("choose valid option");
	
							}

						// ==========================================================
					case 4:
						System.out.println("enter id to remove employee");
						int id = Integer.parseInt(br.readLine());

						Session session4 = factory.openSession();
						Transaction tx4 = session4.beginTransaction();

						Employee e6 = session4.get(Employee.class, id);

						session4.delete(e6);

						tx4.commit();
						session4.close();

						break;
					// ============================================================

					case 5:	
						
						Session session3 = factory.openSession();
						
						String adminViewAllQuery = "from Employee";
						Query query = session3.createQuery(adminViewAllQuery);
						
						List<Employee> eList = query.list();
						
						for(Employee e3 : eList) {
							System.out.println("id:"+e3.getEmployeeId()+"\nname:"+e3.getName()+"\naddress:"+e3.getAddress()+"\nsalary:"+e3.getSalary()+"\ne-mail:"+e3.getEmail()+"\nphone:"+e3.getPhone()+"\n=================================================");
						}
						
						session3.close();
						
						break;
					// ============================================================
					case 6:
						System.out.println("thank you for using our service");
						System.exit(0);

						break;
					}

				}

			}

			if (userMatch.matches()) {
				System.out.println("welcome to user account");
				while (true) {
					System.out.println(
							"1.get employee detail\n2.edit your details\n3.get every employee detail\n4.get specific employee detail\n5.exit app");
					int userChoice = Integer.parseInt(br.readLine());

					switch (userChoice) {

						case 1:
							System.out.println("enter id to get details");
							int id = Integer.parseInt(br.readLine());
	
							Session session = factory.openSession();
	
							Employee e6 = session.get(Employee.class, id);
							System.out.println(e6);
	
							session.close();
	
							break;
						// ======================================
						case 2:
							String usId = userMatch.group(2);
							int changeIntoId = Integer.parseInt(usId);
	
							System.out.println("fields you are allowed to change");
							System.out.println("1.phone\n2.e-mail");
							int userModificationChoice = Integer.parseInt(br.readLine());
	
							switch (userModificationChoice) {
	
								case 1:
									System.out.println("enter new phone number");
									long newPhNo = Long.parseLong(br.readLine());
		
									String convertNewPhNo = Long.toString(newPhNo);
		
									Pattern phNoValidator = Pattern.compile("[7-9]{1}\\d{9}");
									Matcher matcher = phNoValidator.matcher(convertNewPhNo);
		
									if (matcher.matches()) {
		
										Session session4 = factory.openSession();
										Transaction tx4 = session4.beginTransaction();
		
										Employee e61 = session4.get(Employee.class, changeIntoId);
		
										e61.setPhone(newPhNo);
										session4.saveOrUpdate(e61);
		
										tx4.commit();
										session4.close();
		
									} else {
										System.out.println("number invalid, won't be updated");
									}
		
									break;
								case 2:
									System.out.println("enter new e-mail");
									String newEmail = br.readLine();
		
									Pattern newEmailValidator = Pattern
											.compile("^[A-Za-z0-9._-]+@[A-Za-z0-9._-]+\\.(com|net|edu)$");
									Matcher matcher2 = newEmailValidator.matcher(newEmail);
		
									if (matcher2.matches()) {
		
										Session session4 = factory.openSession();
										Transaction tx4 = session4.beginTransaction();
		
										Employee e61 = session4.get(Employee.class, changeIntoId);
		
										e61.setEmail(newEmail);
										session4.saveOrUpdate(e61);
		
										tx4.commit();
										session4.close();
		
									} else {
										System.out.println("invalid ");
									}		
									break;
								
								
								default:
									System.out.println("select valid option");
							}
		//====================================================================================					
						case 3:
							
							String everyEmployeeQuery = "from Employee";
							Session session3 = factory.openSession();
							Query q1 = session3.createQuery(everyEmployeeQuery);
							
							List<Employee> allEmpList = q1.list();
							
							System.out.println("======================================================");
							for(Employee e1 : allEmpList) {
								System.out.println(e1.getEmployeeId()+": "+e1.getName()+e1.getEmail());
							}
							System.out.println("======================================================");

							break;
			//========================================================================================
						case 4:
							System.out.println("enter employee id");
							int idForDetail = Integer.parseInt(br.readLine());
							
							Session session4 = factory.openSession();
							String queryForDetail = "from Employee where id =:x";
							
							Query q2 = session4.createQuery(queryForDetail);
							q2.setParameter("x", idForDetail);
							
							List<Employee>e1 = q2.list();
							System.out.println("================================================");
							for(Employee emp : e1) {
								System.out.println(emp.getEmployeeId()+": "+emp.getName()+": "+emp.getEmail());
							}
							System.out.println("==================================================");
							
							session4.close();
							break;
							
							
						case 5:
							System.out.println("thank you for using app");
							System.exit(0);
							break;
						default:
							System.out.println("select valid option");
							System.out.println("==========================================");
					}
				}
			}
		}
	}
}
