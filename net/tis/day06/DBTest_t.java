package net.tis.day06;
//완성작
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
	Connection CN=null; //DB서버정보및 user/pwd기억, CN참조해서 명령어생성
	Statement ST=null;  //정적인명령어 ST=CN.createStatement(X);
	PreparedStatement PST=null; //동적인명령어 PST=CN.prepareStatememt(msg)
	CallableStatement CST=null; //storedprocedure연결
	ResultSet RS=null; //RS=ST.executeQuery("select * from test"); 무조건 select 만 옴 조회결과를 RS가 기억
	Scanner sc = null;
	String msg="";
	String CODE="",NAME="",TITLE="",WDATE="",CNT="",field="",before="",after="";
	
	public DBTest_t(){
		int order=0;
		try{
	     Class.forName("oracle.jdbc.driver.OracleDriver"); //드라이브로드
	     String url="jdbc:oracle:thin:@127.0.0.1:1521:XE" ;
	     CN=DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:XE","system","1234");
	     System.out.println("오라클 연결 성공했습니다.");
	     dbSelectAll();
		     while(true) {
		     System.out.print("무엇을 하시겠어요? \n[1]신규등록 [2]전체출력 [3]한건조회 [4]삭제 [5]업데이트 [9]종료 \n 숫자 입력 >> ");
		     
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
				   System.out.println("프로그램을 종료합니다."); System.exit(1); 
			   }
		     }
		}catch(Exception ex) {System.out.println("에러: "+ ex.toString() );}
	}
	
	public static void main(String[] args) {
		DBTest_t DB = new DBTest_t();
	}//main end
	
	public void dbInsert() {
		try {
			System.out.println("등록 정보를 입력해주세요.");
			
			while(true) {
		    System.out.print("code >> ");
		    int code = Integer.parseInt(sc.nextLine());
		           msg="select * from test where code=" +code;
		           RS=ST.executeQuery(msg);
		           
		    	   if(RS.next()==true) {
				   int a=RS.getInt("code");
					   if(code == a) {
						System.out.println("이미 사용중인 코드입니다. 다른 코드를 사용하세요.");
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
		  		 	   System.out.println("새로운 정보가 등록 되었습니다.");
		  	    System.out.println("==================================================");
	  	    	break;
			   }
		} catch (Exception e) {System.out.println("에러이유 : " + e.toString());
		}
	}
	
	
	public void dbInsert2() {
		try {
		System.out.println("등록 정보를 입력해주세요.");
		while(true) {
	    System.out.print("code >> ");
	    int code = Integer.parseInt(sc.nextLine());
	           msg="select count(*) as hit from test where code=" +code;
	           RS=ST.executeQuery(msg);
	              /* 
	              RS.next(); 이렇게만 써도 됌
	              Gtotal=RS.getInt("hit");
	              if(Gtotal > 0) {
	              System.out.println(data+"코드데이터 중복입니다.")
	              return;
	              } */
	           if(RS.next()==true) {
				   if(RS.getInt("hit") == 1) { // code >= 1 로 써도 됌
					//System.out.println("여기?");
					System.out.println(code + " 는 이미 사용중인 코드입니다. 다른 코드를 사용하세요.");
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
	  		 	   System.out.println("새로운 정보가 등록 되었습니다.");
	  	    System.out.println("==================================================");
	  	    break;
		   }
	    } catch (Exception e) {System.out.println("에러이유 : " + e.toString());
		}
	}// dbInsert2 end
	
	public void dbInsert3() {
		try {
			System.out.println("등록 정보를 입력해주세요.");
			while(true) {
		    System.out.print("code >> ");
		    int code = Integer.parseInt(sc.nextLine());
		           msg="select count(*) as hit from test where code=" +code;
		           RS=ST.executeQuery(msg);
		              
		              RS.next(); //이렇게만 써도 됌
		              int Gtotal=RS.getInt("hit");
		              if(Gtotal > 0) {
		              System.out.println(code+"코드데이터 중복입니다.");
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
		  		 	   System.out.println("새로운 정보가 등록 되었습니다.");
		  	    System.out.println("==================================================");
		  	    break;
			   }
		    } catch (Exception e) {System.out.println("에러이유 : " + e.toString());
			}
	}//dbInsert2 end	
	public void dbSelectAll() { //전체출력
		try {
			
			System.out.println("==================================================");
			System.out.println("레코드 갯수 : "+dbCount());
			System.out.println("CODE \tNAME \tTITLE \tWDATE \t       CNT");
			 ST=CN.createStatement();
			 RS=ST.executeQuery("select * from test");
			   while(RS.next()) {
				   int a=RS.getInt("code");
				   String b= RS.getString("name");
				   String c=RS.getString("title");
				   Date dt=RS.getDate("wdate"); //util 로 import
				   int hit=RS.getInt("cnt");
				   System.out.println(a+"\t"+b+"\t"+c+"\t"+dt+"\t"+hit);
			   }
			   System.out.println("==================================================");
		} catch (Exception e) {System.out.println("dbSelectAll 메소드에서 에러가 발생했습니다. (에러 : " + e.toString()+")");}
	}//dbSelectAll end
	
	public void dbSelectAll2() {
			try {
			System.out.println("==================================================");
			System.out.println("레코드 갯수 : "+dbCount());
			System.out.println("ROWNUM \tCODE \tNAME \tTITLE \tWDATE \t       CNT");
			 for(int i=1; i>5; i++) {
				 System.out.println(i);
				 
					 while(RS.next()) {
						   int a=RS.getInt("code");
						   String b= RS.getString("name");
						   String c=RS.getString("title");
						   Date dt=RS.getDate("wdate"); //util 로 import
						   int hit=RS.getInt("cnt");
						   System.out.println(a+"\t"+b+"\t"+c+"\t"+dt+"\t"+hit);
			   }
			  i++;
			 }
			   System.out.println("==================================================");
		} catch (Exception e) {System.out.println("dbSelectAll2 메소드에서 에러가 발생했습니다. (에러 : " + e.toString()+")");}
	}//dbSelectAll2 end
	
	
	public void dbSearchName() { //name필드 조회
		try {
		dbSelectAll();
		System.out.print("조회할 name을 입력하세요. >>> ");
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
			   Date dt=RS.getDate("wdate"); //util 로 import
			   int hit=RS.getInt("cnt");
			   System.out.println(a+"\t"+b+"\t"+c+"\t"+dt+"\t"+hit);
		   }
			   System.out.println("==================================================");
		} catch (Exception e) {System.out.println("dbSearchName 메소드에서 에러가 발생했습니다. (에러 :" + e.toString()+")");}
	}
	
	public void dbDelete() { //필드삭제
		ResultSet RS=null;
		try {
		System.out.print("삭제할 code 를 입력하세요. >>>");
		String code=sc.next();
		ST=CN.createStatement();
		ST.executeUpdate("delete from test where code=" + code);
		System.out.println(code+"가 정상적으로 삭제 되었습니다.");
		} catch (Exception e) {System.out.println("dbDelete 메소드에서 에러가 발생했습니다. (에러 :" + e.toString()+")");}
	}
	
	public void dbCorrect(String name) {
	try {
	if (RS.next()==false) {
		System.out.println("[!] 존재하지 않는 이름입니다");
	}
	} catch (SQLException e) {System.out.println("에러 : " + e.toString());}
	}
	
	public void dbUpdate1() {
		try {
			ResultSet RS=null;
			
			System.out.println("===========================================\n기본형식 : update test set 필드='after' where 필드='before';");
			System.out.print("업데이트할 필드를 입력하세요. CODE, NAME, TITLE, WDATE, CNT >>>");
			field = sc.next();
			
			System.out.print("업데이트할 Before내용을 입력하세요. >>>");
			before = sc.next();
			
			System.out.print("업데이트할 After내용을 입력하세요. >>>");
			after = sc.next();
			
			ST=CN.createStatement();
			ST.executeUpdate("update test set " + field + "='" + after + "' where " + field +"='"+before+"'");
			System.out.println("변경 완료 되었습니다. (변경사항 : " + field + " 필드에서" + before + "을 " + after + "로 변경)");
			
		} catch (Exception e) { System.out.println("테이블에 없는 내용입니다. 정확히 입력해주세요. (에러 :"+e.toString()+")");
		}
	}
	
	public void dbUpdate2( ) {//한건code수정 PreparedStatement처리
		try {
			ResultSet RS=null;
			System.out.println("변경할 이전값 >>> "); 
			String before = sc.next();
			
			System.out.println("변경 후의 값 >>>");   
			String after = sc.next();
			
			int after2 = Integer.parseInt(after);  
			int before2 = Integer.parseInt(before); 
	 	   
			msg="update test set code= ? where code= ?";
			PST=CN.prepareStatement(msg);
	 	    PST.setInt(1, after2);
	 	    PST.setInt(2, before2);
	 	    PST.executeUpdate();
	 	    
	 	    System.out.println("성공적으로 변경 되었습니다.");
			
		}catch (Exception e) { System.out.println("수정에러");}
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
	         System.out.println("기록이 올바르게 출력되지 않았습니다.");
	         return 0;
	      }
	}
}// class END