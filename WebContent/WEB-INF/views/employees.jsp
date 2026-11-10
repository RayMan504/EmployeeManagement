
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Employee Management</title>
</head>
<body>

    <h1>Employee Directory</h1>

    <table border="1" cellpadding="10">
        <thead>
            <tr>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Email</th>
                <th>Department</th>
            </tr>
        </thead>

        <tbody>
            <c:forEach var="employee" items="${employees}">
                <tr>
                    <td>${employee.firstName}</td>
                    <td>${employee.lastName}</td>
                    <td>${employee.email}</td>
                    <td>${employee.department}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

</body>
</html>