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
import phuochg.bookinghotel.account.AccountDTO;
import phuochg.bookinghotel.feedback.FeedBackDAO;
import phuochg.bookinghotel.feedback.FeedBackDTO;
import phuochg.bookinghotel.room.RoomDAO;
import phuochg.bookinghotel.room.RoomDTO;
import phuochg.bookinghotel.validation.FeedBackUtils;

/**
 *
 * @author Fanglong-it
 */
@WebServlet(name = "ViewDetailRoomServlet", urlPatterns = {"/ViewDetailRoomServlet"})
public class ViewDetailRoomServlet extends HttpServlet {

    private static final String DETAIL_ROOM_PAGE = "detailRoom.jsp";
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
        String url = DETAIL_ROOM_PAGE;
        try {

            int hotelId = Integer.parseInt(request.getParameter("hotelId"));
            int roomNo = Integer.parseInt(request.getParameter("roomNo"));

            HttpSession session = request.getSession();
            AccountDTO acc = (AccountDTO) session.getAttribute("ACC");
            String msg = "";
            if (acc == null) {
                url = LOGIN_PAGE;
                msg = "You Need to Login To process this request!";
            } else {
                RoomDAO roomDAO = new RoomDAO();
                RoomDTO roomDTO = roomDAO.getRoomInfor(hotelId, roomNo);
                request.setAttribute("ROOM_DETAIL", roomDTO);
            }

            //Get Feedback Value
            FeedBackDAO feedBackDAO = new FeedBackDAO();
            List<FeedBackDTO> listFeedBackDTOs = feedBackDAO.getListFeedBack(roomNo);
            if (listFeedBackDTOs.size() == 0) {
                msg = "Nothing Feedback Yet!";

            } else {
                FeedBackUtils feedBackUtils = new FeedBackUtils();
                int startValue = feedBackUtils.caculatorStarValue(listFeedBackDTOs);
                request.setAttribute("START_VALUE", startValue);
                msg = "Here Your value!";
            }
            request.setAttribute("START_MSG", msg);
            request.setAttribute("VIEWDETAIL_MSG", msg);
        } catch (Exception e) {
            log("Error at ViewDetailRoomServlet:" + e.toString());
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
