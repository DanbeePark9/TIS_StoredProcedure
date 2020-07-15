package SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBTest2  {

  	public static void main(String[] args) {
  		Connection CN;
		Statement ST;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url="jdbc:oracle:thin:@127.0.0.1:1521:XE" ;
			CN=DriverManager.getConnection(url,"system","1234");
			System.out.println("DB 연결 완료");
			
			ST=CN.createStatement();
			String msg= "insert into test values(1235, 'tiana', 'note', sysdate, 0)";
			int OK = ST.executeUpdate(msg);
			if(OK>0) {
				System.out.println("테이블을 출력합니다.");
				System.out.println("========================");
				 String msg1 ="select * from test order by code";
				 ResultSet RS = ST.executeQuery(msg1);
				
				 while(RS.next()) {
					System.out.println(RS.getString("CODE"));
				}
			}
		} catch (ClassNotFoundException e) {
			System.out.println("JDBC 드라이버 로드 에러");
		} catch (SQLException e) {
			System.out.println("에러: "+ e.toString());
		}
	}//main end
}// class END
