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
		//전체출력, 페이지, 이름검색
		//첫번째 페이지 (Page*10-1)
		//마지막 페이지 (Page*10)
		DBGuest DBG = new DBGuest();
	}
	
	public DBGuest() {
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver"); //드라이브로드
			String url="jdbc:oracle:thin:@127.0.0.1:1521:XE" ;
			CN=DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:XE","system","1234");
			System.out.println("오라클 연결 성공했습니다.");
			dbLine();
			sc = new Scanner(System.in);
			while(true) {
				System.out.print("무엇을 하시겠습니까? \n[1]전체출력 [2]페이지 [3]이름검색 [9]종료 \n 숫자 입력 >>");
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
					System.out.println("프로그램을 종료합니다."); System.exit(1);
				}
			}
		} catch (Exception e) { System.out.println("에러 사유 : " +e.toString());
		}
		
	}//기본생성자 method end
	
	public void dbSearchAll() {
		try {
			dbLine();
			System.out.println("레코드 갯수 : "+dbCountG());
			System.out.println("ROWNUM \tSABUN \t NAME \tTITLE \tWDATE \tPAY \tHIT \tEMAIL");
			ST=CN.createStatement();
			RS=ST.executeQuery("select * from guest");
			while(RS.next()) {
				int sabun=RS.getInt("sabun");
				String name= RS.getString("name");
				String title=RS.getString("title");
				Date date=RS.getDate("wdate"); //util 로 import
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
			System.out.print("이름은 b로 고정값입니다. 몇페이지 내용을 보시겠어요? >>>");
			int page = Integer.parseInt(sc.nextLine());
			dbLine();
			System.out.println("레코드 갯수 : "+dbCountG());
			System.out.println("ROWNUM \tSABUN \t NAME \tTITLE \tWDATE \tPAY \tHIT \tEMAIL");
			
			ST=CN.createStatement();
			RS = ST.executeQuery("select * from("
		               + "select rownum rn, a.* from ("
		               + " select * from guest a where "
		               + " name like '%"+name+"%' order by sabun)"
		               + "a) where rn between "+ (page*10-9)+ " and "+ (page*10));
			
			while(RS.next()) {
				//System.out.println("테스트");
				int rn=RS.getInt("rn");
				int sabun=RS.getInt("sabun");
				String colname= RS.getString("name");
				String title=RS.getString("title");
				Date date=RS.getDate("wdate"); //util 로 import
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
			System.out.println("보고싶은 페이지를 입력해주세요. 한페이지당 10건이 출력됩니다. >>> ");
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
	      } catch (SQLException e) {System.out.println("기록이 올바르게 출력되지 않았습니다.");
	         return 0;
	      }
	}
	
	public void dbLine() {
		System.out.println("==================================================");
	}
}//class end
