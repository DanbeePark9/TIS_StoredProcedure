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
import java.util.concurrent.Callable;

public class DBGuest_T  {
   
   Connection CN=null; //DB서버정보및 user/pwd기억, CN참조해서 명령어생성
   Statement ST=null;
   PreparedStatement PST=null;  //정적인명령어 ST=CN.createStatement(X);
   CallableStatement CST=null; //storedprocedure 연결
   ResultSet RS=null;  //RS=ST.executeQuery("select~"); 조회결과를 RS가 기억
   String msg="";
   
   public int countRecord() {
      int cnt = 0;
      try {         
         String msg="select count(*) cnt from guest";
         ST=CN.createStatement();
         RS=ST.executeQuery(msg);
          while(RS.next()) {
             cnt=RS.getInt("cnt");
          }
          return cnt;
      } catch (SQLException e) {
         e.printStackTrace();
         System.out.println("기록이 올바르게 출력되지 않았습니다.");
         return 0;
      }         
   }
   
   public void dbConnection() {
      try {
         Class.forName("oracle.jdbc.driver.OracleDriver"); //드라이브로드
          String url="jdbc:oracle:thin:@127.0.0.1:1521:XE" ;
          CN=DriverManager.getConnection(url,"system","1234");
      }catch (Exception e) {}
   }
       
    public void dbSelectAll() {
       try {
//             System.out.println("전체 데이터 수 : " +countRecord());
          ST=CN.createStatement();
           RS = ST.executeQuery("select rownum, a.* from guest a");
            while(RS.next()) {
               int row = RS.getInt("rownum");
               int a = RS.getInt("sabun");
               String b = RS.getString("name");
               String c = RS.getString("title");
               Date d = RS.getDate("wdate");
               int e =  RS.getInt("pay");
               int f =  RS.getInt("hit");
               String g = RS.getString("email");
               System.out.println(row + "  " + a+"\t"+b+"\t"+c+"\t"+d+"\t"+e+"\t"+f+"\t"+g);
            }
       }catch (Exception e) {}   //try catch
    }
    
    public void dbCorrect(String name) {
       try {
             ST=CN.createStatement();
          RS = ST.executeQuery("select * from guest where name like"
                   + " '%" + name+"%'");             
             if (RS.next()==false) {
                System.out.println("존재하지 않는 이름입니다");
             }
          } catch (SQLException e) {
             System.out.println("에러 : " + e.toString());
          }
    }
    
    public void dbSearchName() {
       try {          
          Scanner sc = new Scanner(System.in);
          System.out.println("검색하고 싶은 이름을 입력하세요");
          System.out.print("name>");
          String name =sc.nextLine();
          dbCorrect(name);
          
          System.out.println("검색하고 싶은 페이지를 입력하세요 (정수 입력)");
          int page = Integer.parseInt(sc.nextLine());
          
         ST=CN.createStatement();
         RS = ST.executeQuery("select * from("
               + "select rownum rn, a.* from ("
               + " select * from guest a where "
               + " name like '%"+name+"%' order by sabun)"
               + "a) where rn between "+ (page*10-9)+ " and "+ (page*10));
         
            
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
      } catch (SQLException e) {
         System.out.println("조회하고자 하는 이름이 존재하지 않습니다.");
         e.printStackTrace();
      }
    }
    
    public void dbPage() {
       try {          
          Scanner sc = new Scanner(System.in);
          
          System.out.println("검색하고 싶은 페이지를 입력하세요");
          System.out.println("(정수 입력)");
          int page = Integer.parseInt(sc.nextLine());
          
         ST=CN.createStatement();
         
         RS = ST.executeQuery("select * from ("
               + "select rownum rn, a.* from guest a order by sabun"
               + ") where rn between "
               +  (page*10-9)
               + " and "
               + (page*10)
               );
         
            
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
      } catch (SQLException e) {
         System.out.println("출력실패.");
         e.printStackTrace();
      }
    }

   public static void main(String[] args) {
      DBGuest_T db = new DBGuest_T();
       try{
         db.dbConnection();
         int cheak;
         Scanner sc = new Scanner(System.in);      
       do {
            System.out.println(" < M E N U >");
            System.out.print("1.전체 출력  2.원하는 페이지 출력  3.이름검색  9.종료 >>> ");
            cheak = Integer.parseInt(sc.nextLine());
            
            switch (cheak) {
            // 신규등록
            case 1:
               System.out.println("전체 데이터 수 : " + db.countRecord());
               db.dbSelectAll();               
               break;
            // 전체출력
            case 2:
               db.dbPage();
               break;
            // 이름
            case 3:
               db.dbSearchName();
               break;
            // 삭제
            case 9:
               System.out.println("시스템이 종료되었습니다.");
               break;
            }
        }while(cheak!=9);   
       }catch (Exception e) {}
   }//main end
}// class END