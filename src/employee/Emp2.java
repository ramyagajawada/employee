package employee;
import java.sql.*;
public class Emp2 {
 // JDBC driver name and database URL
 static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
 static final String DB_URL = "jdbc:mysql://localhost/test"
 		+ "";

 //  Database credentials
 static final String USER = "root";
 static final String PASS = "root";
 
 public static void main(String[] args) {
 Connection conn = null;
 CallableStatement stmt = null;
 try{
    //STEP 2: Register JDBC driver
    Class.forName("com.mysql.jdbc.Driver");

    //STEP 3: Open a connection
    System.out.println("Connecting to database...");
    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","root");

    //STEP 4: Execute a query
    System.out.println("Creating statement...");
    String sql = "{call getEmpName (?, ?)}";
    stmt = conn.prepareCall(sql);
    
    //Bind IN parameter first, then bind OUT parameter
    int ID = 12;
    stmt.setInt(1, ID); // This would set ID as 102
    // Because second parameter is OUT so register it
    stmt.registerOutParameter(2, java.sql.Types.VARCHAR);
    
    //Use execute method to run stored procedure.
    System.out.println("Executing stored procedure..." );
    stmt.execute();

    //Retrieve employee name with getXXX method
    String empName = stmt.getString(2);
    System.out.println("Emp Name with ID:" + 
             ID + " is " + empName);
    stmt.close();
    conn.close();
 }catch(SQLException se){
    //Handle errors for JDBC
    se.printStackTrace();
 }catch(Exception e){
    //Handle errors for Class.forName
    e.printStackTrace();
 }finally{
    //finally block used to close resources
    try{
       if(stmt!=null)
          stmt.close();
    }catch(SQLException se2){
    }// nothing we can do
    try{
       if(conn!=null)
          conn.close();
    }catch(SQLException se){
       se.printStackTrace();
    }//end finally try
 }//end try
 System.out.println("Goodbye!");
}//end main
}//end JDBCExample