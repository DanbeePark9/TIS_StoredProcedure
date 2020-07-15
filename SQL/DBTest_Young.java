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
		Connection CN=null;//DB서버정보및 user/pwd기억, CN참조해서 명령어생성
		Statement ST=null;//정적인명령어 ST=CN.createStatement(X);
		PreparedStatement PST=null; //동적인명령어 PST=CN.prepareStatememt(msg)
		CallableStatement CST=null; //storedprocedure연결
		ResultSet RS=null;//RS=ST.executeQuery("select~") ; 조회결과를 RS기억
		String msg="" ; 
		Scanner sc = new Scanner(System.in);
			
	 public DBTest_Young() {
		 try{
	     Class.forName("oracle.jdbc.driver.OracleDriver"); //드라이브로드
	     String url="jdbc:oracle:thin:@127.0.0.1:1521:XE" ;
	     CN=DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:XE","system","1234");
	     System.out.println("오라클연결성공success 월요일");
	     ST=CN.createStatement();
		 }catch (Exception e) {	}
	 }//생성자
	 
	public static void main(String[] args) {
		DBTest_Young test = new DBTest_Young();
				
		Scanner scin = new Scanner(System.in);
		while(true) {
			System.out.print("\n1등록 2출력 3이름조회 4삭제 5수정 9종료>>> ");
			int sel=scin.nextInt();
			if(sel==1){test.dbInsert();}
			else if(sel==2){ test.dbSelectAll();}
			else if(sel==3){ test.dbSearchName();}
			else if(sel==4){ test.dbDelete(); }
			else if(sel==9){ test.myexit(); break; }
		}
		scin.close();
	}//main end
	
	
	public void dbUpdate( ) {//한건code수정 PreparedStatement처리
		try {
			msg="update where";
		}catch (Exception e) { System.out.println("수정에러");}
	}//end--------------------
	
	public int dbCount(){//레코드갯수 Statement
		int mycount=0;
		try {
			msg="select count(*) as cnt from test";
		}catch (Exception e) { System.out.println(e);}
		return mycount;
	}//end--------------------
	
	public void dbInsert( ) {//한건등록
		try {
		 //PreparedStatement명령어로 대체
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
		 System.out.println("test테이블 PST명령어저장성공success");
		 dbSelectAll(); 
		}catch (Exception e) { System.out.println("신규등록에러");}
	}//end--------------------
	
	public void dbSelectAll( ) {//전체출력
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
		}catch (Exception e) { System.out.println("전체조회에러");}
	}//end--------------------
	
	public void dbSearchName( ) {//name필드조회
		System.out.print("\n조회이름입력>>>");
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
		}catch (Exception e) { System.out.println("이름조회에러");}
	}//end--------------------
	
	public void dbDelete( ) {//code필드삭제
		 System.out.print("\n삭제할 코드입력>>>");
		 int find = sc.nextInt();
		 try {
		  msg="delete from test where code = " + find ;
		  int OK=ST.executeUpdate(msg);
		  System.out.println(find+" 삭제성공했습니다");
		  dbSelectAll();
		}catch (Exception ex){
			System.out.println(find+" 삭제실패했습니다");
			System.out.println(ex);
		}
	}//end--------------------
	
	public void myexit() {
		System.out.println("프로그램을 종료합니다");
		System.exit(1);
	}//end--------------------
	
	
	public void dbInsert2223333444( ) {//한건등록
		try {
		 //Statement명령어대신 PreparedStatement명령어로 대체
		 System.out.print("code >> "); String code = sc.nextLine();
		 System.out.print("name >> "); String name = sc.nextLine();
		 System.out.print("title >> "); String title = sc.nextLine();
		 msg= "insert into test values("+code+",'"+name+"','"+title+"',sysdate, 0)";
		 
		 ST.executeUpdate(msg); //~.executeUpdate(i/d/u)
		 System.out.println("test테이블 저장성공success");
		 dbSelectAll(); 
		}catch (Exception e) { System.out.println("신규등록에러");}
	}//end--------------------
}/////////////////////////////////////////////class END





