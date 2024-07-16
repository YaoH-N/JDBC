package com.atguigu.base;

import com.mysql.cj.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JDBCQuick {
    public static void main(String[] args) throws Exception {
        //1. 注册驱动(类加载时注册) jdk6以后可以省略不写）
//        Class.forName("com.mysql.cj.jdbc.Driver");
        // 自己手动注册
//        DriverManager.registerDriver(new Driver());

        //2. 获取连接对象 url地址和端口号如果都是默认的可以直接省略不写，"jdbc:mysql:///atguigu"
        String url = "jdbc:mysql://localhost:3306/atguigu";
        String username = "root";
        String password = "123456";
        Connection connection = DriverManager.getConnection(url, username, password);

        //3. 获取执行SQL语句的对象
        Statement statement = connection.createStatement();

        //4. 编写SQL语句，并执行
        String sql = "SELECT * FROM t_emp";
        ResultSet resultSet = statement.executeQuery(sql);

        //5. 处理结果，遍历resultSet结果集
        while (resultSet.next()){
            int empId = resultSet.getInt("emp_id");
            String empName = resultSet.getString("emp_name");
            double empSalary = resultSet.getDouble("emp_salary");
            int empAge = resultSet.getInt("emp_age");
            System.out.println(empId+"\t"+empName+"\t"+empSalary+"\t"+empAge);
        }

        //6. 释放资源
        resultSet.close();
        statement.close();
        connection.close();
    }
}
