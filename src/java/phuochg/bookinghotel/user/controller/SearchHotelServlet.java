/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phuochg.bookinghotel.user.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import phuochg.bookinghotel.hotel.HotelDAO;
import phuochg.bookinghotel.hotel.HotelDTO;

/**
 *
 * @author Fanglong-it
 */
@WebServlet(name = "SearchHotelServlet", urlPatterns = {"/SearchHotelServlet"})
public class SearchHotelServlet extends HttpServlet {

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
            HotelDAO hotelDAO = new HotelDAO();
            List<HotelDTO> listHotel = null;
            String msg = "";
            if (searchValue.isEmpty()) {
                msg = "Nothing no search !";
            } else if (checkIn.isEmpty() || checkOut.isEmpty()) {
                listHotel = hotelDAO.searchByNameHotel(searchValue);
                if (listHotel.size() == 0) {
                    msg = "Nothing To search";
                } else {
                    msg = "Search Success";
                }
            } else {
                listHotel = hotelDAO.searchListHotel(searchValue, option, checkIn, checkOut);
                if (listHotel.size() == 0) {
                    msg = "Nothing To search";
                } else {
                    msg = "Search Success";
                }
            }
            if (listHotel != null) {
                
                
                
                
                request.setAttribute("LIST_ROOM", listHotel);
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
