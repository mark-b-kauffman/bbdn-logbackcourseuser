<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--@elvariable id="couse" type="blackboard.data.course.Course"--%>
<%--@elvariable id="users" type="java.util.List"--%>

<html>
<body>
<pre><u>
Course: ${course.courseId} </u>
<br/>
These users were fetched by getting a list of members using: <br/>
List<CourseMembership> members = CourseMembershipDbLoader.Default.getInstance().loadByCourseId( course.getId() ); <br/>
<u>Users:</u><br/>
<c:forEach items="${users}" var="element">
    <c:out value="${element.userName}"/><br/>
</c:forEach>

<br/>
<u>The single user obtained by loadByCourseAndUserId:</u><br/>

    <c:out value="${user2.userName}"/><br/>

These users were fetched, one at a time using: <br/>
CourseMembership aMember = CourseMembershipDbLoader.Default.getInstance().loadByCourseAndUserId( course.getId(), aUser.getId() );</br>
<u>Users:</u><br/>
<c:forEach items="${userslbcuid}" var="element">
    <c:out value="${element.userName}"/><br/>
</c:forEach>


</pre>
</body>
</html>
