package net.tis.day06;
//�ϼ���
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Scanner;

public class DBTest_t  {
	Connection CN=null; //DB���������� user/pwd���, CN�����ؼ� ��ɾ����
	Statement ST=null;  //�����θ�ɾ� ST=CN.createStatement(X);
	PreparedStatement PST=null; //�����θ�ɾ� PST=CN.prepareStatememt(msg)
	CallableStatement CST=null; //storedprocedure����
	ResultSet RS=null; //RS=ST.executeQuery("select * from test"); ������ select �� �� ��ȸ����� RS�� ���
	Scanner sc = null;
	String msg="";
	String CODE="",NAME="",TITLE="",WDATE="",CNT="",field="",before="",after="";
	
	public DBTest_t(){
		int order=0;
		try{
	     Class.forName("oracle.jdbc.driver.OracleDriver"); //����̺�ε�
	     String url="jdbc:oracle:thin:@127.0.0.1:1521:XE" ;
	     CN=DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:XE","system","1234");
	     System.out.println("����Ŭ ���� �����߽��ϴ�.");
	     dbSelectAll();
		     while(true) {
		     System.out.print("������ �Ͻðھ��? \n[1]�űԵ�� [2]��ü��� [3]�Ѱ���ȸ [4]���� [5]������Ʈ [9]���� \n ���� �Է� >> ");
		     
			 sc = new Scanner(System.in);
			 order =Integer.parseInt(sc.nextLine()); 
		   
			   switch(order) {
			   case 1:
				   dbInsert2(); 
				   dbSelectAll(); break;
			   case 2:
				   dbSelectAll2(); break;
			   case 3:
				   dbSearchName(); break;
			   case 4:
				   dbSelectAll();
				   dbDelete(); 
				   dbSelectAll(); break;
			   case 5:
				   dbSelectAll();
				   dbUpdate2(); 
				   dbSelectAll(); break;
			   case 9:
				   System.out.println("���α׷��� �����մϴ�."); System.exit(1); 
			   }
		     }
		}catch(Exception ex) {System.out.println("����: "+ ex.toString() );}
	}
	
	public static void main(String[] args) {
		DBTest_t DB = new DBTest_t();
	}//main end
	
	public void dbInsert() {
		try {
			System.out.println("��� ������ �Է����ּ���.");
			
			while(true) {
		    System.out.print("code >> ");
		    int code = Integer.parseInt(sc.nextLine());
		           msg="select * from test where code=" +code;
		           RS=ST.executeQuery(msg);
		           
		    	   if(RS.next()==true) {
				   int a=RS.getInt("code");
					   if(code == a) {
						System.out.println("�̹� ������� �ڵ��Դϴ�. �ٸ� �ڵ带 ����ϼ���.");
						continue;
					   }
		    	   }
		    	System.out.print("name >> ");
		   	    String name = sc.next();
		   	   
		   	    System.out.print("title >> ");
		   	    String title = sc.next();
		    	 msg="insert into test values(?, ?, ?, sysdate, 0)";
		  		 PST=CN.prepareStatement(msg);
		  		 	   PST.setInt(1, code);
		  		 	   PST.setString(2, name);
		  		 	   PST.setString(3, title);
		  		 	   PST.executeUpdate();
		  		 	   System.out.println("���ο� ������ ��� �Ǿ����ϴ�.");
		  	    System.out.println("==================================================");
	  	    	break;
			   }
		} catch (Exception e) {System.out.println("�������� : " + e.toString());
		}
	}
	
	
	public void dbInsert2() {
		try {
		System.out.println("��� ������ �Է����ּ���.");
		while(true) {
	    System.out.print("code >> ");
	    int code = Integer.parseInt(sc.nextLine());
	           msg="select count(*) as hit from test where code=" +code;
	           RS=ST.executeQuery(msg);
	              /* 
	              RS.next(); �̷��Ը� �ᵵ ��
	              Gtotal=RS.getInt("hit");
	              if(Gtotal > 0) {
	              System.out.println(data+"�ڵ嵥���� �ߺ��Դϴ�.")
	              return;
	              } */
	           if(RS.next()==true) {
				   if(RS.getInt("hit") == 1) { // code >= 1 �� �ᵵ ��
					//System.out.println("����?");
					System.out.println(code + " �� �̹� ������� �ڵ��Դϴ�. �ٸ� �ڵ带 ����ϼ���.");
					continue;
					}
			    } 
	    	System.out.print("name >> ");  String name  = sc.next();
	   	    System.out.print("title >> "); String title = sc.next();
	    	 msg="insert into test values(?, ?, ?, sysdate, 0)";
	  		 PST=CN.prepareStatement(msg);
	  		 	   PST.setInt(1, code);
	  		 	   PST.setString(2, name);
	  		 	   PST.setString(3, title);
	  		 	   PST.executeUpdate();
	  		 	   System.out.println("���ο� ������ ��� �Ǿ����ϴ�.");
	  	    System.out.println("==================================================");
	  	    break;
		   }
	    } catch (Exception e) {System.out.println("�������� : " + e.toString());
		}
	}// dbInsert2 end
	
	public void dbInsert3() {
		try {
			System.out.println("��� ������ �Է����ּ���.");
			while(true) {
		    System.out.print("code >> ");
		    int code = Integer.parseInt(sc.nextLine());
		           msg="select count(*) as hit from test where code=" +code;
		           RS=ST.executeQuery(msg);
		              
		              RS.next(); //�̷��Ը� �ᵵ ��
		              int Gtotal=RS.getInt("hit");
		              if(Gtotal > 0) {
		              System.out.println(code+"�ڵ嵥���� �ߺ��Դϴ�.");
		              return;
		              } 
		          
		    	System.out.print("name >> ");  String name  = sc.next();
		   	    System.out.print("title >> "); String title = sc.next();
		    	 msg="insert into test values(?, ?, ?, sysdate, 0)";
		  		 PST=CN.prepareStatement(msg);
		  		 	   PST.setInt(1, code);
		  		 	   PST.setString(2, name);
		  		 	   PST.setString(3, title);
		  		 	   PST.executeUpdate();
		  		 	   System.out.println("���ο� ������ ��� �Ǿ����ϴ�.");
		  	    System.out.println("==================================================");
		  	    break;
			   }
		    } catch (Exception e) {System.out.println("�������� : " + e.toString());
			}
	}//dbInsert2 end	
	public void dbSelectAll() { //��ü���
		try {
			
			System.out.println("==================================================");
			System.out.println("���ڵ� ���� : "+dbCount());
			System.out.println("CODE \tNAME \tTITLE \tWDATE \t       CNT");
			 ST=CN.createStatement();
			 RS=ST.executeQuery("select * from test");
			   while(RS.next()) {
				   int a=RS.getInt("code");
				   String b= RS.getString("name");
				   String c=RS.getString("title");
				   Date dt=RS.getDate("wdate"); //util �� import
				   int hit=RS.getInt("cnt");
				   System.out.println(a+"\t"+b+"\t"+c+"\t"+dt+"\t"+hit);
			   }
			   System.out.println("==================================================");
		} catch (Exception e) {System.out.println("dbSelectAll �޼ҵ忡�� ������ �߻��߽��ϴ�. (���� : " + e.toString()+")");}
	}//dbSelectAll end
	
	public void dbSelectAll2() {
			try {
			System.out.println("==================================================");
			System.out.println("���ڵ� ���� : "+dbCount());
			System.out.println("ROWNUM \tCODE \tNAME \tTITLE \tWDATE \t       CNT");
			 for(int i=1; i>5; i++) {
				 System.out.println(i);
				 
					 while(RS.next()) {
						   int a=RS.getInt("code");
						   String b= RS.getString("name");
						   String c=RS.getString("title");
						   Date dt=RS.getDate("wdate"); //util �� import
						   int hit=RS.getInt("cnt");
						   System.out.println(a+"\t"+b+"\t"+c+"\t"+dt+"\t"+hit);
			   }
			  i++;
			 }
			   System.out.println("==================================================");
		} catch (Exception e) {System.out.println("dbSelectAll2 �޼ҵ忡�� ������ �߻��߽��ϴ�. (���� : " + e.toString()+")");}
	}//dbSelectAll2 end
	
	
	public void dbSearchName() { //name�ʵ� ��ȸ
		try {
		dbSelectAll();
		System.out.print("��ȸ�� name�� �Է��ϼ���. >>> ");
		String name=sc.next();
		
		//dbCorrect(name);
		
		System.out.println("==================================================");
		   System.out.println("CODE \tNAME \tTITLE \tWDATE \t       CNT");
		   ST=CN.createStatement();
		   RS=ST.executeQuery("select * from test where name like '%"+name+"%'");
			   while(RS.next()) {
			   int a=RS.getInt("code");
			   String b= RS.getString("name");
			   String c=RS.getString("title");
			   Date dt=RS.getDate("wdate"); //util �� import
			   int hit=RS.getInt("cnt");
			   System.out.println(a+"\t"+b+"\t"+c+"\t"+dt+"\t"+hit);
		   }
			   System.out.println("==================================================");
		} catch (Exception e) {System.out.println("dbSearchName �޼ҵ忡�� ������ �߻��߽��ϴ�. (���� :" + e.toString()+")");}
	}
	
	public void dbDelete() { //�ʵ����
		ResultSet RS=null;
		try {
		System.out.print("������ code �� �Է��ϼ���. >>>");
		String code=sc.next();
		ST=CN.createStatement();
		ST.executeUpdate("delete from test where code=" + code);
		System.out.println(code+"�� ���������� ���� �Ǿ����ϴ�.");
		} catch (Exception e) {System.out.println("dbDelete �޼ҵ忡�� ������ �߻��߽��ϴ�. (���� :" + e.toString()+")");}
	}
	
	public void dbCorrect(String name) {
	try {
	if (RS.next()==false) {
		System.out.println("[!] �������� �ʴ� �̸��Դϴ�");
	}
	} catch (SQLException e) {System.out.println("���� : " + e.toString());}
	}
	
	public void dbUpdate1() {
		try {
			ResultSet RS=null;
			
			System.out.println("===========================================\n�⺻���� : update test set �ʵ�='after' where �ʵ�='before';");
			System.out.print("������Ʈ�� �ʵ带 �Է��ϼ���. CODE, NAME, TITLE, WDATE, CNT >>>");
			field = sc.next();
			
			System.out.print("������Ʈ�� Before������ �Է��ϼ���. >>>");
			before = sc.next();
			
			System.out.print("������Ʈ�� After������ �Է��ϼ���. >>>");
			after = sc.next();
			
			ST=CN.createStatement();
			ST.executeUpdate("update test set " + field + "='" + after + "' where " + field +"='"+before+"'");
			System.out.println("���� �Ϸ� �Ǿ����ϴ�. (������� : " + field + " �ʵ忡��" + before + "�� " + after + "�� ����)");
			
		} catch (Exception e) { System.out.println("���̺� ���� �����Դϴ�. ��Ȯ�� �Է����ּ���. (���� :"+e.toString()+")");
		}
	}
	
	public void dbUpdate2( ) {//�Ѱ�code���� PreparedStatementó��
		try {
			ResultSet RS=null;
			System.out.println("������ ������ >>> "); 
			String before = sc.next();
			
			System.out.println("���� ���� �� >>>");   
			String after = sc.next();
			
			int after2 = Integer.parseInt(after);  
			int before2 = Integer.parseInt(before); 
	 	   
			msg="update test set code= ? where code= ?";
			PST=CN.prepareStatement(msg);
	 	    PST.setInt(1, after2);
	 	    PST.setInt(2, before2);
	 	    PST.executeUpdate();
	 	    
	 	    System.out.println("���������� ���� �Ǿ����ϴ�.");
			
		}catch (Exception e) { System.out.println("��������");}
	}
	
	
	public int dbCount() {
		int mycount=0;
		try {         
	         String msg="select * from test";
	         ST=CN.createStatement();
	         RS=ST.executeQuery(msg);
	          while(RS.next()) {
	        	  mycount++;
	          }
	          return mycount;
	      } catch (SQLException e) {
	         e.printStackTrace();
	         System.out.println("����� �ùٸ��� ��µ��� �ʾҽ��ϴ�.");
	         return 0;
	      }
	}
}// class END