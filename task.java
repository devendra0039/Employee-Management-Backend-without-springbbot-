import sun.lwawt.macosx.CSystemTray;

import java.sql.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;


class Employeee{

    public void get(Connection conn) throws SQLException{
        String sql = "SELECT EMPLOYEE.EmployeeId,EMPLOYEE.EmployeeName, EMPVSDEP.DepartmetId, EMPLOYEE.EmployeeJoiningDate, EMPLOYEE.EmployeeAge, EMPLOYEE.EmployeeLocation, EMPLOYEE.EmployeeActive FROM EMPLOYEE INNER JOIN EMPVSDEP ON EMPVSDEP.EmployeeId=EMPLOYEE.EmployeeId";
        Scanner input = new Scanner(System.in);
        System.out.println(" 1. Get By Name or Id \n2. Get By Department Id");
        String choice = input.next();
        if (choice.equals("1")){
            System.out.println("Enter Employee Name or Employee Id ");
            String inp = input.next();
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()){
                String id = result.getString(1);
                String name = result.getString(2);
                String depid = result.getString(3);
                String date = result.getString(4);
                String age = result.getString(5);
                String location = result.getString(6);
                String active = result.getString(7);

                if (inp.equals(id) ||  name.toLowerCase().startsWith(inp.toLowerCase())){

                    String output = "User: %s- %s - %s - %s - %s - %s - %s";
                    System.out.println(String.format(output, id, name,depid, date, age, location, active ));
                }
                else {
                    continue;
                }
            }
        }
        else if (choice.equals("2")) {
            System.out.println("Enter Department Id ");
            String inp = input.next();
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()){
                String id = result.getString(1);
                String name = result.getString(2);
                String depid = result.getString(3);
                String date = result.getString(4);
                String age = result.getString(5);
                String location = result.getString(6);
                String active = result.getString(7);

                if (inp.equals(depid)){

                    String output = "User: %s- %s - %s - %s - %s - %s - %s";
                    System.out.println(String.format(output, id, name,depid, date, age, location, active ));
                }
                else {
                    continue;
                }
            }
        }

    }

    public void create(Connection conn) throws SQLException {
        String sql = "INSERT INTO EMPLOYEE(EmployeeName, EmployeeJoiningDate, EmployeeAge, EmployeeLocation, EmployeeActive) VALUES(?, ?, ?, ?,?)";
        Scanner input = new Scanner(System.in);
        System.out.println("Enter Employee Name");
        String empName = input.nextLine();
        System.out.println("Enter Joining Date");
        String EmployeeJoiningDate = input.nextLine();
        System.out.println("Enter Employee Age");
        String EmployeeAge = input.nextLine();
        System.out.println("Enter Location");
        String location = input.nextLine();
        System.out.println("Enter whether Active or Not");
        String active = input.nextLine();
        while(true){
            System.out.println("Enter Department Id");
            String depid = input.nextLine();

            ArrayList<String> arr= new ArrayList<String>();
            String getdepid="0";
            String getdepsql="SELECT DepartmentId FROM DEPARTMENT";
            Statement getdepstatement = conn.createStatement();
            ResultSet getdepresult= getdepstatement.executeQuery(getdepsql);
            while (getdepresult.next()){
                getdepid=getdepresult.getString(1);
                arr.add(getdepid);
            }
            if (arr.contains(depid)){
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, empName);
                statement.setString(2, EmployeeJoiningDate);
                statement.setString(3, EmployeeAge);
                statement.setString(4, location);
                statement.setString(5, active);
                int rowsInserted = statement.executeUpdate();

                if (rowsInserted > 0) {
                    System.out.println("A new Employee was inserted successfully!");
                }

                String empid="0";
                String getsql = "SELECT EmployeeId FROM EMPLOYEE";
                Statement getstatement = conn.createStatement();
                ResultSet getresult =  getstatement.executeQuery(getsql);
                while(getresult.next()){
                    empid = getresult.getString(1);
                }

                String depsql="INSERT INTO EMPVSDEP(DepartmetId, EmployeeId) VALUES(?,?)";
                PreparedStatement depstatement = conn.prepareStatement(depsql);
                depstatement.setString(1,depid);
                depstatement.setString(2,empid);
                int rowsInserted2 = depstatement.executeUpdate();

                if (rowsInserted2 > 0) {
                    System.out.println("A new DepartmentId and EmployeeId is inserted successfully!");
                }
                break;
            }
            else {
                System.out.println("Entered Department Id doesn't exist");
            }
        }
    }

    public void edit(Connection conn) throws SQLException{
        String sql = "UPDATE EMPLOYEE SET EmployeeName=?, EmployeeJoiningDate=?, EmployeeAge=?, EmployeeLocation=?, EmployeeActive=? WHERE EmployeeId=?";
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the Employee Id");
        String id = input.next();
        System.out.println("Enter Employee Name");
        String empName = input.next();
        System.out.println("Enter Joining Date");
        String EmployeeJoiningDate = input.next();
        System.out.println("Enter Employee Age");
        String EmployeeAge = input.next();
        System.out.println("Enter Location");
        String location = input.next();
        System.out.println("Enter whether Active or Not");
        String active = input.next();
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1,empName);
        statement.setString(2,EmployeeJoiningDate);
        statement.setString(3,EmployeeAge);
        statement.setString(4,location);
        statement.setString(5,active);
        statement.setString(6,id);
        int rowsUpdated = statement.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("The existing user is  updated successfully!");
        }


    }

    public void delete(Connection conn)throws SQLException{
        String sql = "UPDATE EMPLOYEE SET EmployeeActive=0 WHERE EmployeeId=?";
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the Employee Id");
        String id = input.next();
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1,id);
        int rowsUpdated = statement.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("The  Employee is now Unactive");
        }


    }

    public void list(Connection conn) throws SQLException{
        String sql = "SELECT EMPLOYEE.EmployeeId,EMPLOYEE.EmployeeName, EMPVSDEP.DepartmetId, EMPLOYEE.EmployeeJoiningDate, EMPLOYEE.EmployeeAge, EMPLOYEE.EmployeeLocation, EMPLOYEE.EmployeeActive FROM EMPLOYEE INNER JOIN EMPVSDEP ON EMPVSDEP.EmployeeId=EMPLOYEE.EmployeeId ";
        Statement statement = conn.createStatement();
        ResultSet result = statement.executeQuery(sql);
        int count=0;
        while (result.next()){
            String id = result.getString(1);
            String name = result.getString(2);
            String date = result.getString(3);
            String age = result.getString(4);
            String location = result.getString(5);
            String active = result.getString(6);

            String output = "User #%d: %s - %s - %s - %s - %s - %s";
            System.out.println(String.format(output, ++count, id, name, date, age, location, active ));
        }

    }


}



public class task {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String dbURL = "jdbc:mysql://localhost:3306/TASK";
        String username = "root";
        String password = "@Devendra123";
        try {
            Connection conn = DriverManager.getConnection(dbURL, username, password);

            if (conn != null) {
                System.out.println("DataBase Connected");

                System.out.println("\nOperations");
                while (true) {
                    System.out.println(" 1. Get \n 2. Create \n 3. Edit \n 4. Delete \n 5. List \n 6.Exit");
                    System.out.println("\nSelect The Operation");
                    String opration = input.nextLine();

                    Employeee op= new Employeee();

                    if (opration.toLowerCase(Locale.ROOT).equals("get") || opration.equals("1")) {
                        System.out.println("Get operation");
                        op.get(conn);

                    }

                    else if (opration.toLowerCase(Locale.ROOT).equals("create") || opration.equals("2")) {
                        System.out.println("Create operation");
                        op.create(conn);

                    }

                    else if (opration.toLowerCase(Locale.ROOT).equals("edit") || opration.equals("3")) {
                        System.out.println("Edit operation");
                        op.edit(conn);

                    }

                    else if (opration.toLowerCase(Locale.ROOT).equals("delete") || opration.equals("4")) {
                        System.out.println("Delete operation");
                        op.delete(conn);

                    }

                    else if (opration.toLowerCase(Locale.ROOT).equals("list") || opration.equals("5")) {
                        System.out.println("List of Employee");
                        op.list(conn);

                    }

                    else if (opration.toLowerCase(Locale.ROOT).equals("exit") || opration.equals("6")) {
                        System.out.println("You are Exited");
                        conn.close();
                        break;
                    }

                    else {
                        System.out.println("Invalid opration");
                    }
                }


            }
        }


        catch (Exception e) {
            e.printStackTrace();
        }
    }

}

