
package com.example.controller;

import com.example.model.Employee;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/employees")
public class EmployeeServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // Temporary sample data.
        // Later, this will come from EmployeeDAO and Hibernate.
        List<Employee> employees = new ArrayList<>();

        employees.add(new Employee(
                "John", "Smith",
                "john@example.com", "Engineering"));

        employees.add(new Employee(
                "Sarah", "Johnson",
                "sarah@example.com", "Human Resources"));

        employees.add(new Employee(
                "Michael", "Brown",
                "michael@example.com", "Finance"));

        // Make the employee list available to the JSP.
        request.setAttribute("employees", employees);

        // Forward the request to the JSP.
        RequestDispatcher dispatcher =
                request.getRequestDispatcher(
                "/WEB-INF/views/employees.jsp");

        dispatcher.forward(request, response);
    }
}
