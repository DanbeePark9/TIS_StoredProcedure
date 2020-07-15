package SQL;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Scanner;


public class DBTest_Young  { 
		Connection CN=null;//DB���������� user/pwd���, CN�����ؼ� ���ɾ����
		Statement ST=null;//�����θ��ɾ� ST=CN.createStatement(X);
		PreparedStatement PST=null; //�����θ��ɾ� PST=CN.prepareStatememt(msg)
		CallableStatement CST=null; //storedprocedure����
		ResultSet RS=null;//RS=ST.executeQuery("select~") ; ��ȸ����� RS���
		String msg="" ; 
		Scanner sc = new Scanner(System.in);
			
	 public DBTest_Young() {
		 try{
	     Class.forName("oracle.jdbc.driver.OracleDriver"); //����̺�ε�
	     String url="jdbc:oracle:thin:@127.0.0.1:1521:XE" ;
	     CN=DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:XE","system","1234");
	     System.out.println("����Ŭ���Ἲ��success ������");
	     ST=CN.createStatement();
		 }catch (Exception e) {	}
	 }//������
	 
	public static void main(String[] args) {
		DBTest_Young test = new DBTest_Young();
				
		Scanner scin = new Scanner(System.in);
		while(true) {
			System.out.print("\n1��� 2��� 3�̸���ȸ 4���� 5���� 9����>>> ");
			int sel=scin.nextInt();
			if(sel==1){test.dbInsert();}
			else if(sel==2){ test.dbSelectAll();}
			else if(sel==3){ test.dbSearchName();}
			else if(sel==4){ test.dbDelete(); }
			else if(sel==9){ test.myexit(); break; }
		}
		scin.close();
	}//main end
	
	
	public void dbUpdate( ) {//�Ѱ�code���� PreparedStatementó��
		try {
			msg="update where";
		}catch (Exception e) { System.out.println("��������");}
	}//end--------------------
	
	public int dbCount(){//���ڵ尹�� Statement
		int mycount=0;
		try {
			msg="select count(*) as cnt from test";
		}catch (Exception e) { System.out.println(e);}
		return mycount;
	}//end--------------------
	
	public void dbInsert( ) {//�Ѱǵ��
		try {
		 //PreparedStatement���ɾ�� ��ü
		 System.out.print("PST code >> ");
		 int code = Integer.parseInt(sc.nextLine());

		 System.out.print("PST name >> ");
		 String name = sc.nextLine();

		 System.out.print("PST title >> ");
		 String title = sc.nextLine();

		 msg="insert into test values(?, ?, ?, sysdate, 0)";
		 PST=CN.prepareStatement(msg);
		 	   PST.setInt(1, code);
		 	   PST.setString(2, name);
		 	   PST.setString(3, title);
		 int OK=PST.executeUpdate();
		 System.out.println("test���̺� PST���ɾ����强��success");
		 dbSelectAll(); 
		}catch (Exception e) { System.out.println("�űԵ�Ͽ���");}
	}//end--------------------
	
	public void dbSelectAll( ) {//��ü���
		try {
		System.out.println("=============================================");
		msg="select * from test order by code" ;
		RS = ST.executeQuery(msg);
		while(RS.next()==true) {
			int a=RS.getInt("code");
			String b = RS.getString("name");
			String c = RS.getString("title");
			Date dt = RS.getDate("wdate");
			int hit = RS.getInt("cnt");
			System.out.println(a+"\t"+b+"\t"+c+"\t"+dt+"\t"+hit);
		}
		System.out.println("=============================================");
		}catch (Exception e) { System.out.println("��ü��ȸ����");}
	}//end--------------------
	
	public void dbSearchName( ) {//name�ʵ���ȸ
		System.out.print("\n��ȸ�̸��Է�>>>");
		String data = sc.nextLine();
		try {		
			msg="select * from test where name like '%" +data+ "%' " ;
			//System.out.println(msg);
			RS = ST.executeQuery(msg);
			while(RS.next()==true) {
				int a=RS.getInt("code");
				String b = RS.getString("name");
				String c = RS.getString("title");
				Date dt = RS.getDate("wdate");
				int hit = RS.getInt("cnt");
				System.out.println(a+"\t"+b+"\t"+c+"\t"+dt+"\t"+hit);
			}	
		}catch (Exception e) { System.out.println("�̸���ȸ����");}
	}//end--------------------
	
	public void dbDelete( ) {//code�ʵ����
		 System.out.print("\n������ �ڵ��Է�>>>");
		 int find = sc.nextInt();
		 try {
		  msg="delete from test where code = " + find ;
		  int OK=ST.executeUpdate(msg);
		  System.out.println(find+" ���������߽��ϴ�");
		  dbSelectAll();
		}catch (Exception ex){
			System.out.println(find+" ���������߽��ϴ�");
			System.out.println(ex);
		}
	}//end--------------------
	
	public void myexit() {
		System.out.println("���α׷��� �����մϴ�");
		System.exit(1);
	}//end--------------------
	
	
	public void dbInsert2223333444( ) {//�Ѱǵ��
		try {
		 //Statement���ɾ��� PreparedStatement���ɾ�� ��ü
		 System.out.print("code >> "); String code = sc.nextLine();
		 System.out.print("name >> "); String name = sc.nextLine();
		 System.out.print("title >> "); String title = sc.nextLine();
		 msg= "insert into test values("+code+",'"+name+"','"+title+"',sysdate, 0)";
		 
		 ST.executeUpdate(msg); //~.executeUpdate(i/d/u)
		 System.out.println("test���̺� ���强��success");
		 dbSelectAll(); 
		}catch (Exception e) { System.out.println("�űԵ�Ͽ���");}
	}//end--------------------
}/////////////////////////////////////////////class END




