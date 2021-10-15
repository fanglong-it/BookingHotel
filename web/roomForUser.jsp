<%-- 
    Document   : roomForUser
    Created on : Oct 15, 2021, 4:18:12 PM
    Author     : Fanglong-it
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <meta http-equiv="Content-type" content="text/html; charset=utf-8" />
        <link rel="stylesheet" href="css/style.css" type="text/css" media="all" />
    </head>
    <body>
        <h1>HomeForUser</h1>
        <!-- Header -->
        <div id="header">
            <div class="shell">
                <!-- Logo + Top Nav -->
                <div id="top">
                    <h1><a href="#">SpringTime</a></h1>
                    <div id="top-navigation">

                        <c:if test="${sessionScope.ACC != null}">
                            <span>|</span>
                            Welcome, ${sessionScope.ACC.userId}
                            <a href="MainController?btnAction=logout">Log out</a>
                        </c:if>

                        <c:if test="${sessionScope.ACC == null}">
                            <span>|</span>
                            <a href="MainController?btnAction=loginPage">Log In</a>
                        </c:if>




                    </div>
                </div>
                <!-- End Logo + Top Nav -->
                <!-- Main Nav -->
                <div id="navigation">
                    <ul>
                        <li><a href="MainController?btnAction=" ><span>Dashboard</span></a></li>
                        <li><a href="#" class="active"><span>Room</span></a></li>
                        <li><a href="#"><span>User Management</span></a></li>
                        <li><a href="#"><span>Photo Gallery</span></a></li>
                        <li><a href="#"><span>Products</span></a></li>
                        <li><a href="#"><span>Services Control</span></a></li>
                    </ul>
                </div>
                <!-- End Main Nav -->
            </div>
        </div>
        <!-- End Header -->
        <!-- Container -->
        <div id="container">
            <div class="shell">
                <!-- Small Nav -->
                <!-- End Small Nav -->
                <!-- Message OK -->
                <!-- End Message OK -->
                <!-- Message Error -->
                <!-- End Message Error -->
                <br />
                <!-- Main -->
                <div id="main">
                    <div class="cl">&nbsp;</div>
                    <!-- Content -->
                    <div id="content" style="width: 100%">
                        <!-- Box -->
                        <div class="box">
                            <!-- Box Head -->
                            <div class="box-head">
                                <h2 class="left">Current Articles</h2>
                                <div class="right">

                                </div>


                            </div>
                            <!-- End Box Head -->
                            <!-- Table -->
                            <div class="table">
                                <c:if test="${requestScope.LIST_ROOM != null}">
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <th>No.</th>
                                            <th>RoomNo</th>
                                            <th>RoomName</th>
                                            <th>Quantity</th>
                                            <th>Type</th>
                                            <th>Price</th>
                                            <th width="110" class="ac">Content Control</th>
                                        </tr>

                                        <c:forEach var="r" items="${requestScope.LIST_ROOM}" varStatus="count">
                                            <tr>
                                                <td>${count.count}</td>
                                                <td><h3><a href="#">${r.roomNo}</a></h3></td>
                                                <td>${r.roomName}</td>
                                                <td>${r.quantity}</td>
                                                <td>${r.typeId}</td> 
                                                <td>${r.price}</td>
                                                <td><a href="MainController?btnAction=viewDetailRoom&hotelId=${r.roomNo}" class="ico edit">Order</a></td>
                                            </tr>
                                        </c:forEach>


                                    </table>
                                </c:if>

                                <!-- Pagging -->

                                <!-- End Pagging -->
                            </div>
                            <!-- Table -->
                        </div>
                        <!-- End Box -->
                        <!-- Box -->
                        <!-- End Box -->
                    </div>
                    <!-- End Content -->
                    <!-- Sidebar -->

                    <!-- End Sidebar -->
                    <div class="cl">&nbsp;</div>
                </div>
                <!-- Main -->
            </div>
        </div>
        <!-- End Container -->
        <!-- Footer -->
        <div id="footer">
            <div class="shell"> <span class="left">&copy; 2010 - CompanyName</span> <span class="right"> Design by <a href="http://chocotemplates.com">Chocotemplates.com</a> </span> </div>
        </div>
        <!-- End Footer -->
    </body>
</html>
