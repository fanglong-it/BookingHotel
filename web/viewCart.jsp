<%-- 
    Document   : viewCart
    Created on : Oct 17, 2021, 2:12:23 AM
    Author     : Fanglong-it
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Detail Room</title>
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
                            <c:if test="${sessionScope.ACC.roleId eq '1'}">
                                <c:redirect url="login.html"></c:redirect>
                            </c:if>
                            <c:if test="${sessionScope.ACC.roleId ne '1'}">
                                Welcome, ${sessionScope.ACC.userId}
                                <a href="MainController?btnAction=logout">Log out</a>
                            </c:if>
                            <span>|</span>

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
                        <li><a href="MainController?btnAction=" ><span>Home Page</span></a></li>
                        <li><a href="MainController?btnAction=ViewCart"class="active" ><span>View Cart</span></a></li>
                        <li><a href="MainController?btnAction=ViewOrder"><span>View Order</span></a></li>
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
                    <div id="content">
                        <!-- Box -->
                        <div class="box">
                            <!-- Box Head -->
                            <div class="box-head">
                                <h2 class="left">Current Cart</h2>
                                <p class="right">${requestScope.CART_MSG}</p>
                                <p class="right">${requestScope.CHECKDISCOUNT_MSG}</p>

                            </div>
                            <!-- End Box Head -->
                            <!-- Table -->
                            <div class="table">
                                <c:if test="${sessionScope.LIST_CART != null}">
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <th>No.</th>
                                            <th>Hotel Name</th>
                                            <th>Room Name</th>

                                            <th>Type</th>
                                            <th>Price</th>
                                            <th>Check In</th>
                                            <th>Check Out</th>
                                            <th>Night</th>
                                            <th>Total Price</th>
                                            <th width="110" class="ac">Action</th>
                                        </tr>

                                        <c:forEach var="r" items="${sessionScope.LIST_CART}" varStatus="count">
                                            <form action="MainController">
                                                <tr>
                                                    <td>${count.count}</td>
                                                    <td><h3><a href="#">${r.hotelName}</a></h3></td>
                                                    <td>${r.roomName}</td>

                                                    <td>${r.typeId}</td> 
                                                    <td>${r.price} .vnđ</td>
                                                    <td>${r.checkInDate}</td>
                                                    <td>${r.checkOutDate}</td>
                                                    <td>
                                                        <input type="hidden" name="roomNo" value="${r.roomNo}"/>
                                                        <select name="night">
                                                            <option value="${r.night}" selected="">${r.night} night</option>

                                                            <c:forEach begin="1" end="30" var="num">
                                                                <option value="${num}">${num} night</option>
                                                            </c:forEach>

                                                        </select>

                                                    </td>
                                                    <td>${r.total} .vnđ</td>
                                                    <td>
                                                        <a href="MainController?btnAction=deleteCart&roomNo=${r.roomNo}" class="ico del">Del</a>
                                                        <button class="ico edit" name="btnAction" value="updateCart">Update</button>
                                                    </td>

                                                </tr>
                                            </form>
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

                    <div id="sidebar">
                        <!-- Box -->
                        <div class="box">
                            <form action="MainController">
                                <!-- Box Head -->
                                <div class="box-head">
                                    <h2>Manager Cart</h2>
                                </div>

                                <!-- Date Picker -->
                                <p>
                                    Total Price :<input type="text" name="totalPrice" value="${sessionScope.TOTAL}" style="width: 50%" class="field small-field"  readonly=""/>
                                    VNĐ
                                </p>
                                <hr>
                                <!-- End Sort -->
                                <button name="btnAction" value="checkOut" class="btn btn-primary" style="width: 100%">Check Out</button>
                                <hr>
                                <p>
                                    <c:if test="${requestScope.CHECKDISCOUNT_MSG != null}">
                                    <p style="color: redF">${requestScope.CHECKDISCOUNT_MSG}</p>
                                </c:if>
                                </p>


                                <p>
                                    Discount :<input type="text" name="discountCode" value="" style="width: 50%" class="field small-field"/>
                                    VNĐ
                                </p>
                                <button name="btnAction" value="checkDiscountCode" class="btn btn-primary" style="width: 100%">Check</button>
                            </form>
                        </div>

                        <!-- End Box -->
                    </div>

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
