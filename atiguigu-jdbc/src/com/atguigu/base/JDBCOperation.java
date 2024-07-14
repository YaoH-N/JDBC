package com.atguigu.base;

import org.junit.Test;

import java.sql.*;

public class JDBCOperation {
    String url = "jdbc:mysql://localhost:3306/atguigu";
    String user = "root";
    String password = "123456";

    /**
     * 查询单行单列结果
     *
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @Test
    public void testQuerySingleRowAndCol() throws ClassNotFoundException, SQLException {
        //1.注册驱动，可以省略
        Class.forName("com.mysql.cj.jdbc.Driver");

        //2. 获取连接
        Connection connection = DriverManager.getConnection(url, user, password);

        //3. 预编译SQL语句得到PreparedStatement对象
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) as count FROM t_emp");


        //4. 执行SQL语句,获取结果
        ResultSet resultSet = preparedStatement.executeQuery();

        //5. 处理结果 如果自己明确只有一个结果，那么resultSet最少要做一次next的判断，才能拿到我们要的列的结果
        while (resultSet.next()) {
            int count = resultSet.getInt("count");
            System.out.println(count);
        }

        //6. 释放资源
        resultSet.close();
        preparedStatement.close();
        connection.close();
    }


    /**
     * 查询单行多列结果
     */
    @Test
    public void testQuerySingleRow() throws Exception {
        //1. 注册驱动
        Class.forName("com.mysql.cj.jdbc.Driver");

        //2. 获取连接
        Connection connection = DriverManager.getConnection(url, user, password);

        //3. 根据连接和预编译SQL获取PreparedStatement对象
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM t_emp WHERE emp_id = ?");

        //4. 为占位符赋值并 执行，并获取结果
        preparedStatement.setInt(1, 1);
        ResultSet resultSet = preparedStatement.executeQuery();

        //5. 处理结果
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

    /**
     * 多行多列查询
     *
     * @throws Exception
     */
    @Test
    public void testQueryMoreRow() throws Exception {
        Connection connection = DriverManager.getConnection(url, user, password);

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM t_emp WHERE emp_age > ?");

        preparedStatement.setInt(1, 25);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            int empId = resultSet.getInt("emp_id");
            String empName = resultSet.getString("emp_name");
            double empSalary = resultSet.getDouble("emp_salary");
            int empAge = resultSet.getInt("emp_age");
            System.out.println(empId + "\t" + empName + "\t" + empSalary + "\t" + empAge);
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();

    }

    @Test
    public void testInsert() throws Exception {
        Connection connection = DriverManager.getConnection(url, user, password);

        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO t_emp(emp_name,emp_salary,emp_age) VALUES (?,?,?)");

        preparedStatement.setString(1, "rose");
        preparedStatement.setDouble(2, 3456.7);
        preparedStatement.setInt(3, 27);

        // 增删改执行executeUpdate(), 返回受影响的行数
        int i = preparedStatement.executeUpdate();
        if (i > 0) {
            System.out.println("添加成功!");
        } else {
            System.out.println("添加失败!");
        }

        preparedStatement.close();
        connection.close();

    }


    @Test
    public void testUpdate() throws Exception {
        Connection connection = DriverManager.getConnection(url, user, password);

        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE t_emp SET emp_salary = ? WHERE emp_id = ?");

        preparedStatement.setDouble(1, 345.7);
        preparedStatement.setInt(2, 6);


        // 增删改执行executeUpdate(), 返回受影响的行数
        int i = preparedStatement.executeUpdate();
        if (i > 0) {
            System.out.println("薪资修改成功!");
        } else {
            System.out.println("薪资修改失败!");
        }

        preparedStatement.close();
        connection.close();

    }

    @Test
    public void testDelete() throws Exception {
        Connection connection = DriverManager.getConnection(url, user, password);

        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM t_emp WHERE emp_id = ?");

        preparedStatement.setDouble(1, 6);

        // 增删改执行executeUpdate(), 返回受影响的行数
        int i = preparedStatement.executeUpdate();
        if (i > 0) {
            System.out.println("删除成功!");
        } else {
            System.out.println("删除失败!");
        }

        preparedStatement.close();
        connection.close();

    }
}


