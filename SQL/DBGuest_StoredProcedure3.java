package SQL;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Scanner;

public class DBGuest_StoredProcedure3 {
	Connection CN=null;
	Statement ST=null;
	ResultSet RS=null;
	Scanner sc = null;
	PreparedStatement PST=null;
	CallableStatement CST=null;
	String msg="";
	int order;
	public static void main(String[] args) {
		//등록, 전체출력, 수정, 종료
		//첫번째 페이지 (Page*10-1)
		//마지막 페이지 (Page*10)
		DBGuest_StoredProcedure3 DBG = new DBGuest_StoredProcedure3();
	}
	
	public DBGuest_StoredProcedure3() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver"); //드라이브로드
			String url="jdbc:oracle:thin:@127.0.0.1:1521:XE" ;
			CN=DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:XE","system","1234");
			System.out.println("오라클 연결 성공했습니다.");
			dbLine();
			sc = new Scanner(System.in);
			while(true) {
				System.out.print("무엇을 하시겠습니까? \n[1]등록, [2]전체출력, [3]수정, [4]전체출력2, [9]종료 \n 숫자 입력 >>");
				//order = Integer.parseInt(sc.nextLine());
				order = Integer.parseInt(sc.next());  dbLine();
				switch(order) {
				/*등록*/	case 1: GuestSearchAll(); GuestInsert(); 		break;
				/*전체*/	case 2: GuestSearchAll(); 						break;
				/*수정*/	case 3: GuestSearchAll();  GuestUpdate1(); 		break;
			    /*전체*/ case 4: GuestSearchAll2(); 						break;
				case 9: System.out.println("프로그램을 종료합니다."); System.exit(1);
				}
			}
		} catch (Exception e) { System.out.println("에러 사유 : " +e.toString());
		}
	}//기본생성자 end
	
	public void GuestSearchAll() {
		try {
			System.out.println("ROWNUM \tSABUN \t NAME \tTITLE \tWDATE \tPAY \tHIT \tEMAIL");
			ST=CN.createStatement();
			RS=ST.executeQuery("select * from guest");
			while(RS.next()) {
				//int sabun = RS.getInt("sabun");
				String name= RS.getString("name");
				String title=RS.getString("title");
				Date date=RS.getDate("wdate"); //util 로 import
				int pay=RS.getInt("pay");
				int hit=RS.getInt("hit");
				String email=RS.getString("email");
				System.out.println(RS.getInt("sabun")+"\t"+name+"\t"+title+"\t"+date+"\t"+pay+"\t"+email);
		    }
			dbLine();
		} catch (Exception e) {
		}
	}//method end
	
	public void GuestSearchAll2() {
		
		System.out.println("==================================================");
		System.out.println("guest_sp_select 데이터 호출");
		dbLine();
		System.out.println("ROWNUM \tSABUN \t NAME \tTITLE \tWDATE \tPAY \tHIT \tEMAIL");
		try {
			CST=CN.prepareCall("{call guest_sp_select(?)}");
			CST.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);
			CST.executeUpdate();
			
			RS = (ResultSet)CST.getObject(1);
			while (RS.next()==true) {
				String name= RS.getString("name");
				String title=RS.getString("title");
				Date date=RS.getDate("wdate"); //util 로 import
				int pay=RS.getInt("pay");
				int hit=RS.getInt("hit");
				String email=RS.getString("email");
				System.out.println(RS.getInt("sabun")+"\t"+name+"\t"+title+"\t"+date+"\t"+pay+"\t"+email);
				
			}
		} catch (Exception e) {
		}
	}
	
	public void GuestInsert() {
		//Stored Procedure guest_sp_insert
		try {
			System.out.println("등록 정보를 입력해주세요.");
			while(true) {
	    	   System.out.print("sabun >> "); 	int sabun = Integer.parseInt(sc.next());
	    	   System.out.print("name >> "); 	String name = sc.next();
	    	   System.out.print("title >> "); 	String title = sc.next();
	    	   System.out.print("pay >> "); 	int pay = Integer.parseInt(sc.next());
	    	   System.out.print("hit >> "); 	int hit = Integer.parseInt(sc.next());
	    	   System.out.print("email >> ");	String email = sc.next();
		   	   
			   //int sabun = 9900; String name="blue";
			   //int hit=7; String email="aa@LB.com";
	    	   CST=CN.prepareCall("{call guest_sp_insert(?, ?, ?, ?, ?, ?)}");
	    	   CST.setInt(1, sabun);
	    	   CST.setString(2, name);
	    	   CST.setString(3, title);
	    	   CST.setInt(4, pay);
	    	   CST.setInt(5, hit);
	    	   CST.setString(6, email);
	    	   CST.executeUpdate();
	  		 	   System.out.println("Stored Procedure 새로운 정보가 등록 되었습니다.");
		  	    System.out.println("==================================================");
	  	    	break;
			   }
		} catch (Exception e) {System.out.println("Stored Procedure 저장 실패" + e.toString());
		}
	}
	
	public void GuestUpdate1() { //단비
		try {
			System.out.println("수정전과 수정후 사번을 입력해주세요.");
			while(true) {
	    	   System.out.print("수정전 사번 >> "); 	int sabun_BEFORE = Integer.parseInt(sc.next());
	    	   System.out.print("수정후 사번 >> "); 	int sabun_AFTER = Integer.parseInt(sc.next());
		   	   
	    	   CST=CN.prepareCall("{call GUEST_SP_UPDATE_SABUN(?, ?)}");
	    	   CST.setInt(1, sabun_BEFORE);
	    	   CST.setInt(2, sabun_AFTER);
	    	   if (sabun_BEFORE==sabun_AFTER) {
	    		   System.out.println("이미 사용중인 사번입니다. 다른 사번을 사용하세요."); continue;
	    	   }
	    	   CST.executeUpdate();
	  		 	   System.out.println("Stored Procedure 수정 되었습니다.");
		  	    System.out.println("==================================================");
	  	    	break;
			   }
		} catch (Exception e) {System.out.println("Stored Procedure 수정 실패" + e.toString());
		}
	}
	
	public void GuestUpdate2() { //태웅
		try {
			System.out.println("수정전과 수정후 사번을 입력해주세요.");
			while(true) {
	    	   System.out.print("수정할 sabun >> "); 	int sabun2 = Integer.parseInt(sc.next());
	    	   System.out.print("수정할 name >> "); 	String name2 = sc.next();
	    	   System.out.print("수정할 title >> "); 	String title2 = sc.next();
	    	   System.out.print("수정할 pay >> "); 	int pay2 = Integer.parseInt(sc.next());
	    	   System.out.print("수정할 hit >> "); 	int hit2 = Integer.parseInt(sc.next());
	    	   System.out.print("수정할 email >> "); 	String email2 = sc.next();
	    	   
	    	   CST=CN.prepareCall("{call GUEST_SP_EDIT(?,?,?,?,?,?)}");
	    	   CST.setInt(1, sabun2);
	    	   CST.setString(2, name2);
	    	   CST.setString(3, title2);
	    	   CST.setInt(4, pay2);
	    	   CST.setInt(5, hit2);
	    	   CST.setString(6, email2);
	    	   CST.executeUpdate();
	  		 	   System.out.println("Stored Procedure 수정 되었습니다.");
		  	    System.out.println("==================================================");
	  	    	break;
			   }
		} catch (Exception e) {System.out.println("Stored Procedure 수정 실패" + e.toString());
		}
	}
	
	public void dbLine() {
		System.out.print("==================================================\n");
	}
}//class end
