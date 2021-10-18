/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phuochg.bookinghotel.order.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import phuochg.bookinghotel.account.AccountDTO;
import phuochg.bookinghotel.orderdetails.OrderDetailsDAO;
import phuochg.bookinghotel.orderdetails.OrderDetailsDTO;
import phuochg.bookinghotel.orders.OrderDAO;
import phuochg.bookinghotel.orders.OrderDTO;
import phuochg.bookinghotel.room.RoomDAO;
import phuochg.bookinghotel.room.RoomDTO;
import phuochg.bookinghotel.validation.OrderUtils;

/**
 *
 * @author Fanglong-it
 */
@WebServlet(name = "CheckOutServlet", urlPatterns = {"/CheckOutServlet"})
public class CheckOutServlet extends HttpServlet {

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
            List<RoomDTO> listRoom = (List<RoomDTO>) session.getAttribute("LIST_CART");
            RoomDAO roomDAO = new RoomDAO();
            OrderUtils orderUtils = new OrderUtils();

            List<RoomDTO> listDB = roomDAO.getListRoom();

            boolean check = false;
            String msg = "";

            for (int i = 0; i < listRoom.size(); i++) {
                if (orderUtils.checkExistInDB(listDB, listRoom.get(i).getRoomNo())) {
                    check = true;
                }
            }

            if (check) {

                Float total = (Float) session.getAttribute("TOTAL");
                AccountDTO acc = (AccountDTO) session.getAttribute("ACC");
                if (listRoom.size() == 0) {
                    msg = "Nothing To CheckOut!";
                    session.setAttribute("LIST_CART", null);
                    session.setAttribute("TOTAL", null);
                } else {
                    String orderCode = orderUtils.getAlphaNumericString(10);
                    OrderDAO orderDAO = new OrderDAO();
                    OrderDetailsDAO orderDetailsDAO = new OrderDetailsDAO();
                    //get To day
                    Long milis = System.currentTimeMillis();
                    Date today = new Date(milis);
                    //Order
                    OrderDTO order = new OrderDTO(orderCode, acc.getUserId(), today.toString(), total, true);
                    orderDAO.insertOrder(order);
                    for (int i = 0; i < listRoom.size(); i++) {

                        //orderId, roomNo, hotelId, orderQuantity, night, checkIn, checkOut
                        OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO(orderCode, listRoom.get(i).getRoomNo(),
                                listRoom.get(i).getHotelId(), 1, listRoom.get(i).getNight(),
                                listRoom.get(i).getCheckInDate(), listRoom.get(i).getCheckOutDate());

                        int newQuantity = listRoom.get(i).getQuantity();
                        int oldQuantity = roomDAO.getRoomQuantity(listRoom.get(i).getRoomNo());
                        int totalQuantity = oldQuantity - newQuantity;
                        roomDAO.setRoomQuantity(listRoom.get(i).getRoomNo(), totalQuantity);
                        orderDetailsDAO.insertOrderDetails(orderDetailsDTO);
                    }
                    msg = "order success!";
                    session.setAttribute("TOTAL", null);
                    session.setAttribute("LIST_CART", null);
                }
            } else {
                msg = "Order fail, Something not right!";
                session.setAttribute("TOTAL", null);
                session.setAttribute("LIST_CART", null);
            }
            request.setAttribute("CART_MSG", msg);
        } catch (Exception e) {
            log("Error at CheckOutServlet:" + e.toString());
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
