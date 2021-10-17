/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phuochg.bookinghotel.order.controller;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import phuochg.bookinghotel.room.RoomDTO;
import phuochg.bookinghotel.validation.OrderUtils;

/**
 *
 * @author Fanglong-it
 */
@WebServlet(name = "OrderRoomServlet", urlPatterns = {"/OrderRoomServlet"})
public class OrderRoomServlet extends HttpServlet {

    private static final String ORDER_PAGE = "orderDetailPage.jsp";
    private static final String DETAIL_ROOM = "detailRoom.jsp";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = DETAIL_ROOM;
        try {
            int hotelId = Integer.parseInt(request.getParameter("hotelId"));
            String hotelName = request.getParameter("hotelName");
            int roomNo = Integer.parseInt(request.getParameter("roomNo"));
            String roomName = request.getParameter("roomName");
            String typeId = request.getParameter("typeId");
            Float roomPrice = Float.parseFloat(request.getParameter("roomPrice"));

            Date checkIn = Date.valueOf(request.getParameter("checkIn"));
            Date checkOut = Date.valueOf(request.getParameter("checkOut"));
            OrderUtils orderUtils = new OrderUtils();
            String msg = "";
            Long milis = System.currentTimeMillis();
            Date today = new Date(milis);
            
            
            LocalDate dateBefore = LocalDate.parse(request.getParameter("checkIn"));
            LocalDate dateAfter = LocalDate.parse(request.getParameter("checkOut"));
            int noOfDaysBetween = (int) ChronoUnit.DAYS.between(dateBefore, dateAfter);
            
            if (checkIn.before(today)) {
                msg = "Can't Order Date Before Today!";
                url = "MainController?btnAction=viewDetailRoom&hotelId=" + hotelId + "&roomNo=" + roomNo;
            } else if (checkOut.before(checkIn)) {
                msg = "You can't Check out before CheckIn Date!";
                url = "MainController?btnAction=viewDetailRoom&hotelId=" + hotelId + "&roomNo=" + roomNo;
            } else if (noOfDaysBetween > 30) {
                msg = "You can't Check out greater than 30 day!";
                url = "MainController?btnAction=viewDetailRoom&hotelId=" + hotelId + "&roomNo=" + roomNo;
            } else {
                float total = orderUtils.calculatePriceTotalRoomPrice(noOfDaysBetween, roomPrice);
                RoomDTO room = new RoomDTO(hotelId, hotelName, roomNo, roomName, "", 1, typeId, roomPrice, noOfDaysBetween, "" + checkIn, "" + checkOut, total);
                HttpSession session = request.getSession();
                List<RoomDTO> listRoom = (List<RoomDTO>) session.getAttribute("LIST_CART");
                if (listRoom == null) {
                    listRoom = new ArrayList<>();
                }
                listRoom.add(room);
                session.setAttribute("LIST_CART", listRoom);
                url = "MainController?btnAction=";
                msg= "Order Success! View in Cart Page";
            }
            request.setAttribute("ORDER_MSG", msg);

        } catch (Exception e) {
            log("Error at OrderRoomServlet: " + e.toString());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
