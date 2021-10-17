/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phuochg.bookinghotel.order.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import phuochg.bookinghotel.account.AccountDTO;
import phuochg.bookinghotel.room.RoomDTO;
import phuochg.bookinghotel.validation.OrderUtils;

/**
 *
 * @author Fanglong-it
 */
@WebServlet(name = "UpdateCartServlet", urlPatterns = {"/UpdateCartServlet"})
public class UpdateCartServlet extends HttpServlet {

    private static final String VIEW_CART_PAGE = "viewCart.jsp";
    private static final String LOGIN_PAGE = "login.jsp";

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
        String url = VIEW_CART_PAGE;
        try {
            HttpSession session = request.getSession();
            AccountDTO acc = (AccountDTO) session.getAttribute("ACC");
            String msg = "";
            if (acc != null) {
                int roomNo = Integer.parseInt(request.getParameter("roomNo"));
                int night = Integer.parseInt(request.getParameter("night"));
                List<RoomDTO> listRoom = (List<RoomDTO>) session.getAttribute("LIST_CART");
                OrderUtils orderUtils = new OrderUtils();
                orderUtils.updateRoomNight(listRoom, roomNo, night);

                String checkInDate = orderUtils.getCheckInRoomDate(listRoom, roomNo);

                LocalDate checkOutDate = LocalDate.parse(checkInDate).plusDays(night);

                orderUtils.updateRoomCheckoutDate(listRoom, roomNo, "" + checkOutDate);

                orderUtils.updateRoomTotal(listRoom, roomNo,
                        orderUtils.calculatePriceTotalRoomPrice(night, orderUtils.getRoomPrice(listRoom, roomNo)));
                msg = "Update Success!";
                session.setAttribute("TOTAL", orderUtils.calculateTotal(listRoom));
                session.setAttribute("LIST_CART", listRoom);
            } else {
                url = LOGIN_PAGE;
                msg = "You need login to process this request!";
            }
            request.setAttribute("CART_MSG", msg);
        } catch (Exception e) {
            log("Error at UpdateCartServlet:" + e.toString());
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
