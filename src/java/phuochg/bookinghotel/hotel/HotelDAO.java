/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phuochg.bookinghotel.hotel;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import phuochg.bookinghotel.utils.DBHelper;

/**
 *
 * @author Fanglong-it
 */
public class HotelDAO implements Serializable {

    public List<HotelDTO> searchListHotel(String searchValue, String typeRoom, String checkIn, String checkOut) throws NamingException, SQLException {
        List<HotelDTO> listHotel = new ArrayList<>();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnect();
            if (con != null) {
                String sql = "Select tblHotel.hotelId, tblHotel.hotelName, tblHotel.hotelAddress, tblHotel.Status\n"
                        + "from tblHotel	\n"
                        + "inner join tblRoom on tblHotel.hotelId = tblRoom.hotelId\n"
                        + "where tblHotel.hotelName like ? \n"
                        + "and tblRoom.typeId like ? \n"
                        + "and tblRoom.availableDate between ? and ? ";
                pst = con.prepareStatement(sql);
                pst.setString(1, "%" + searchValue + "%");
                pst.setString(2, typeRoom);
                pst.setString(3, checkIn);
                pst.setString(4, checkOut);

                rs = pst.executeQuery();
                while (rs.next()) {
                    int hotelId = rs.getInt("hotelId");
                    String hotelName = rs.getString("hotelName");
                    String hotelAddress = rs.getString("hotelAddress");
                    boolean status = rs.getBoolean("status");

                    HotelDTO hotel = new HotelDTO(hotelId, hotelName, hotelAddress, status);
                    listHotel.add(hotel);
                }

            }

        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return listHotel;

    }

    public List<HotelDTO> searchByNameHotel(String searchValue) throws NamingException, SQLException {
        List<HotelDTO> listHotel = new ArrayList<>();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnect();
            if (con != null) {
                String sql = "Select tblHotel.hotelId, tblHotel.hotelName, tblHotel.hotelAddress, tblHotel.Status\n"
                        + "from tblHotel	\n"
                        + "where tblHotel.hotelName like ? ";
                pst = con.prepareStatement(sql);
                pst.setString(1, "%" + searchValue + "%");

                rs = pst.executeQuery();
                while (rs.next()) {
                    int hotelId = rs.getInt("hotelId");
                    String hotelName = rs.getString("hotelName");
                    String hotelAddress = rs.getString("hotelAddress");
                    boolean status = rs.getBoolean("status");

                    HotelDTO hotel = new HotelDTO(hotelId, hotelName, hotelAddress, status);
                    listHotel.add(hotel);
                }

            }

        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return listHotel;

    }

    public List<HotelDTO> loadListHotel() throws NamingException, SQLException {
        List<HotelDTO> listHotel = new ArrayList<>();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnect();
            if (con != null) {
                String sql = "Select hotelId, hotelName, hotelAddress, Status\n"
                        + "from tblHotel";
                pst = con.prepareStatement(sql);
                rs = pst.executeQuery();
                while (rs.next()) {
                    int hotelId = rs.getInt("hotelId");
                    String hotelName = rs.getString("hotelName");
                    String hotelAddress = rs.getString("hotelAddress");
                    boolean status = rs.getBoolean("status");

                    HotelDTO hotel = new HotelDTO(hotelId, hotelName, hotelAddress, status);
                    listHotel.add(hotel);
                }

            }

        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return listHotel;

    }
}
