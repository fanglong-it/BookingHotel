/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phuochg.bookinghotel.order.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import phuochg.bookinghotel.account.AccountDTO;
import phuochg.bookinghotel.discountcode.DiscountDAO;
import phuochg.bookinghotel.discountcode.DiscountDTO;
import phuochg.bookinghotel.validation.OrderUtils;

/**
 *
 * @author Fangl
 */
@WebServlet(name = "CheckDiscountCodeServlet", urlPatterns = {"/CheckDiscountCodeServlet"})
public class CheckDiscountCodeServlet extends HttpServlet {

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
            String discountCode = request.getParameter("discountCode");
            HttpSession session = request.getSession();
            AccountDTO acc = (AccountDTO) session.getAttribute("ACC");

            String msg = "";
            if (acc != null) {
                DiscountDAO discountDAO = new DiscountDAO();

                DiscountDTO discount = discountDAO.checkDiscountCode(discountCode);

                if (discount == null) {
                    msg = "Nothing to Check!";
                } else {
                    DiscountDTO usedDiscount = (DiscountDTO) session.getAttribute("DISCOUNT_CODE");
                    if (usedDiscount == null) {
                        msg = "Your total Will Discount:" + discount.getDiscountValue();
                        OrderUtils orderUtils = new OrderUtils();
                        float total = (float) session.getAttribute("TOTAL");
                        float newTotal = orderUtils.calculatorDiscount(total, discount.getDiscountValue());
                        session.setAttribute("TOTAL", newTotal);
                        session.setAttribute("DISCOUNT_CODE", discount);
                    } else {
                        msg = "You Have used the Discount!";
                    }
                }

            } else {
                msg = "You need to Login To Process This Request!";
                url = LOGIN_PAGE;
            }
            request.setAttribute("CHECKDISCOUNT_MSG", msg);

        } catch (Exception e) {
            log("ERROR at CheckDiscountCodeServlet:" + e.toString());
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
