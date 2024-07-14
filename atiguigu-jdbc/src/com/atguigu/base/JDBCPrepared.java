package com.atguigu.base;

import java.sql.*;
import java.util.Scanner;

public class JDBCPrepared {
    public static void main(String[] args) throws SQLException {
        //1. 注册驱动（jdk6以后可以省略不写）

        //2. 获取连接对象
        Connection connection = DriverManager.getConnection("jdbc:mysql:///atguigu", "root", "123456");

        //3. 获取执行SQL语句的对象,预编译的PreparedStatement，防止SQL注入
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT emp_id,emp_name,emp_salary,emp_age FROM t_emp WHERE emp_name = ?");

        System.out.println("请输入员工姓名：");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();

        //4. 为?占位符赋值，执行SQL语句，接受返回的结果
        preparedStatement.setString(1,name);
        ResultSet resultSet = preparedStatement.executeQuery();

        //5. 处理结果，遍历resultSet
        while (resultSet.next()) {
            int empId = resultSet.getInt("emp_id");
            String empName = resultSet.getString("emp_name");
            double empSalary = resultSet.getDouble("emp_salary");
            int empAge = resultSet.getInt("emp_age");
            System.out.println(empId + "\t" + empName + "\t" + empSalary + "\t" + empAge);
        }

        //6. 释放资源
        resultSet.close();
        preparedStatement.close();
        connection.close();

    }
}
