package net.tis.day08;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Scanner;

public class DBGuest {
	Connection CN=null;
	Statement ST=null;
	ResultSet RS=null;
	Scanner sc = null;
	PreparedStatement PST=null;
	CallableStatement CST=null;
	String msg="";
	int order;
	public static void main(String[] args) {
		//��ü���, ������, �̸��˻�
		//ù��° ������ (Page*10-1)
		//������ ������ (Page*10)
		DBGuest DBG = new DBGuest();
	}
	
	public DBGuest() {
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver"); //����̺�ε�
			String url="jdbc:oracle:thin:@127.0.0.1:1521:XE" ;
			CN=DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:XE","system","1234");
			System.out.println("����Ŭ ���� �����߽��ϴ�.");
			dbLine();
			sc = new Scanner(System.in);
			while(true) {
				System.out.print("������ �Ͻðڽ��ϱ�? \n[1]��ü��� [2]������ [3]�̸��˻� [9]���� \n ���� �Է� >>");
				//order = Integer.parseInt(sc.nextLine());
				order = Integer.parseInt(sc.next()); 
				dbLine();
				
				switch(order) {
				case 1:
					dbSearchAll(); break;
				case 2:
					dbPage(); break;
				case 3:
					dbSearchName(); break;
				case 9:
					System.out.println("���α׷��� �����մϴ�."); System.exit(1);
				}
			}
		} catch (Exception e) { System.out.println("���� ���� : " +e.toString());
		}
		
	}//�⺻������ method end
	
	public void dbSearchAll() {
		try {
			dbLine();
			System.out.println("���ڵ� ���� : "+dbCountG());
			System.out.println("ROWNUM \tSABUN \t NAME \tTITLE \tWDATE \tPAY \tHIT \tEMAIL");
			ST=CN.createStatement();
			RS=ST.executeQuery("select * from guest");
			while(RS.next()) {
				int sabun=RS.getInt("sabun");
				String name= RS.getString("name");
				String title=RS.getString("title");
				Date date=RS.getDate("wdate"); //util �� import
				int pay=RS.getInt("pay");
				int hit=RS.getInt("hit");
				String email=RS.getString("email");
				System.out.println(sabun+"\t"+name+"\t"+title+"\t"+date+"\t"+pay+"\t"+email);
		    }
			dbLine();
		} catch (Exception e) {
		}
	}//method end
	
	public void dbSearchName() {
		try {
			String name = "b";
			System.out.print("�̸��� b�� �������Դϴ�. �������� ������ ���ðھ��? >>>");
			int page = Integer.parseInt(sc.nextLine());
			dbLine();
			System.out.println("���ڵ� ���� : "+dbCountG());
			System.out.println("ROWNUM \tSABUN \t NAME \tTITLE \tWDATE \tPAY \tHIT \tEMAIL");
			
			ST=CN.createStatement();
			RS = ST.executeQuery("select * from("
		               + "select rownum rn, a.* from ("
		               + " select * from guest a where "
		               + " name like '%"+name+"%' order by sabun)"
		               + "a) where rn between "+ (page*10-9)+ " and "+ (page*10));
			
			while(RS.next()) {
				//System.out.println("�׽�Ʈ");
				int rn=RS.getInt("rn");
				int sabun=RS.getInt("sabun");
				String colname= RS.getString("name");
				String title=RS.getString("title");
				Date date=RS.getDate("wdate"); //util �� import
				int pay=RS.getInt("pay");
				int h=RS.getInt("hit");
				String email=RS.getString("email");
				System.out.println(rn+"\t"+sabun+"\t"+colname+"\t"+title+"\t"+date+"\t"+pay+"\t"+h+"\t"+email); 
			} 
			dbLine();
		} catch (Exception e) {System.out.println(e.toString());
		}
	}//method end
	
	public void dbPage() {
		try {
			System.out.println("������� �������� �Է����ּ���. ���������� 10���� ��µ˴ϴ�. >>> ");
			int pagenum = Integer.parseInt(sc.next());

			ST=CN.createStatement();

			RS = ST.executeQuery("select * from (select rownum rn, a.* from (select * from guest a where name like '%b%' order by sabun) a) where rn between " + (pagenum*10-1) + " and " + (pagenum*10));
			
			while(RS.next()==true) {
				int row = RS.getInt("rn");
				int a = RS.getInt("sabun");
				String b = RS.getString("name");
				String c = RS.getString("title");
				Date d = RS.getDate("wdate");
				int e =  RS.getInt("pay");
				int f =  RS.getInt("hit");
				String g = RS.getString("email");
				System.out.println(row + "  " + a+"\t"+b+"\t"+c+"\t"+d+"\t"+e+"\t"+f+"\t"+g);
	         }
			dbLine();
		} catch (Exception e) {System.out.println(e.toString());}
	}//method end
	
	public int dbCountG() {
		int mycount=0;
		try {         
	         String msg="select * from Guest";
	         ST=CN.createStatement();
	         RS=ST.executeQuery(msg);
	          while(RS.next()) {
	        	  mycount++;
	          }
	          return mycount;
	      } catch (SQLException e) {System.out.println("����� �ùٸ��� ��µ��� �ʾҽ��ϴ�.");
	         return 0;
	      }
	}
	
	public void dbLine() {
		System.out.println("==================================================");
	}
}//class end
