/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phuochg.bookinghotel.user.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import phuochg.bookinghotel.room.RoomDAO;
import phuochg.bookinghotel.room.RoomDTO;
import phuochg.bookinghotel.validation.OrderUtils;

/**
 *
 * @author Fanglong-it
 */
@WebServlet(name = "SearchRoomServlet", urlPatterns = {"/SearchRoomServlet"})
public class SearchRoomServlet extends HttpServlet {

    private static final String HOME_PAGE_USER = "homeForUser.jsp";

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
        String url = HOME_PAGE_USER;
        try {
            String searchValue = request.getParameter("searchValue");
            String checkIn = request.getParameter("checkIn");
            String checkOut = request.getParameter("checkOut");
            String option = request.getParameter("option");
            RoomDAO roomDAO = new RoomDAO();
            List<RoomDTO> listRoomDTOs = null;
            String msg = "";
//            if (searchValue.isEmpty() && (checkIn.isEmpty() || checkOut.isEmpty()) && !option.isEmpty()) {
//                listRoomDTOs = roomDAO.searchByTypeRoom(searchValue);
//                if (listRoomDTOs.size() == 0) {
//                    msg = "Nothing To search";
//                } else {
//                    msg = "Search Success";
//                }
//            } else if (!searchValue.isEmpty() && (checkIn.isEmpty() || checkOut.isEmpty()) && option.equals("all")) {
//                listRoomDTOs = roomDAO.searchByNameRoom(searchValue);
//                if (listRoomDTOs.size() == 0) {
//                    msg = "Nothing To search";
//                } else {
//                    msg = "Search Success";
//                }
//            } else {
//                listRoomDTOs = roomDAO.searchListRoom(searchValue, option, checkIn, checkOut);
//                if (listRoomDTOs.size() == 0) {
//                    msg = "Nothing To search";
//                } else {
//                    msg = "Search Success";
//                }
//            }

            if (searchValue.isEmpty() && option.equals("all")) {
                listRoomDTOs = roomDAO.searchByNameRoom(searchValue);
            } else if (searchValue.isEmpty() && !option.equals("all") && checkIn.isEmpty()) {
                listRoomDTOs = roomDAO.searchByTypeRoom(option);
            } else if (!searchValue.isEmpty() && !option.equals("all") && checkIn.isEmpty()) {
                listRoomDTOs = roomDAO.searchByNameRoom(searchValue);
            } else {
                listRoomDTOs = roomDAO.searchListRoom(searchValue, option, checkIn, checkOut);
            }

            if (listRoomDTOs.size() == 0) {
                msg = "Nothing To search";
            } else {
                msg = "Search Success!";
                HttpSession session = request.getSession();
                List<RoomDTO> listCart = (List<RoomDTO>) session.getAttribute("LIST_CART");
                if (listCart != null) {
                    OrderUtils order = new OrderUtils();
                    if (order.checkExistIncart(listCart, listRoomDTOs)) {
                        order.removeListRoom(listCart, listRoomDTOs);
                    }
                }
                request.setAttribute("LIST_ROOM", listRoomDTOs);
            }

            request.setAttribute("SEARCH_MSG", msg);

        } catch (Exception e) {
            log("Error at SearchHotelServlet:" + e.toString());
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
