package com.atguigu.advanced;

import com.atguigu.advanced.pojo.Employee;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCAdvanced {

    @Test
    public void testORM() throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:mysql:///atguigu", "root", "123456");

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM t_emp WHERE emp_id = ?");
        preparedStatement.setInt(1, 1);
        ResultSet resultSet = preparedStatement.executeQuery();
        Employee employee = null;

        if (resultSet.next()) {
            employee = new Employee();
            int empId = resultSet.getInt("emp_id");
            String empName = resultSet.getString("emp_name");
            double empSalary = resultSet.getDouble("emp_salary");
            int empAge = resultSet.getInt("emp_age");
            // 为对象的属性赋值
            employee.setEmpId(empId);
            employee.setEmpName(empName);
            employee.setEmpSalary(empSalary);
            employee.setEmpAge(empAge);
            System.out.println(employee);
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();

    }

    @Test
    public void testORMList() throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:mysql:///atguigu", "root", "123456");

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM t_emp");
        ResultSet resultSet = preparedStatement.executeQuery();
        Employee employee = null;
        List<Employee> employeeList = new ArrayList<>();

        while (resultSet.next()) {
            employee = new Employee();
            int empId = resultSet.getInt("emp_id");
            String empName = resultSet.getString("emp_name");
            double empSalary = resultSet.getDouble("emp_salary");
            int empAge = resultSet.getInt("emp_age");
            // 为对象的属性赋值
            employee.setEmpId(empId);
            employee.setEmpName(empName);
            employee.setEmpSalary(empSalary);
            employee.setEmpAge(empAge);
            employeeList.add(employee);
        }

        // 5. 处理结果，遍历集合
        for (Employee employee1 : employeeList) {
            System.out.println(employee1);
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();

    }

    /**
     * 该方法用来测试主键回显操作，获取新添加的用户主键，并重新赋值给Java对象
     *
     * @throws Exception
     */
    @Test
    public void testReturnId() throws Exception {
        //获取连接
        Connection connection = DriverManager.getConnection("jdbc:mysql:///atguigu", "root", "123456");
        // 预编译SQL语句, 告知preparedStatement,返回新增数据的主键列的值
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO t_emp(emp_name,emp_salary,emp_age) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
        // 创建对象，将对象的属性值填充在?占位符上(ORM思想)
        Employee employee = new Employee(null, "jack", 123.45, 30);
        preparedStatement.setString(1, employee.getEmpName());
        preparedStatement.setDouble(2, employee.getEmpSalary());
        preparedStatement.setInt(3, employee.getEmpAge());

        // 执行SQL，并获取返回的结果
        int result = preparedStatement.executeUpdate();

        // 处理结果
        ResultSet resultSet = null; // 用来接受SQL执行返回的新增主键ID
        if (result > 0) {
            System.out.println("添加成功!");

            // 获取当前新增数据的主键列，回显到Java中的employee对象的empId属性上
            // 返回的主键值，是一个单行单列的结果存储在ResultSet里
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                int empId = resultSet.getInt(1);
                employee.setEmpId(empId);
            }
            System.out.println(employee);
        } else {
            System.out.println("添加失败!");
        }

        //释放资源
        if (resultSet != null) {
            resultSet.close();
        }
        preparedStatement.close();
        connection.close();

    }
}
