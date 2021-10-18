/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phuochg.bookinghotel.room;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import phuochg.bookinghotel.hotel.HotelDTO;
import phuochg.bookinghotel.utils.DBHelper;

/**
 *
 * @author Fanglong-it
 */
public class RoomDAO implements Serializable {

    public List<RoomDTO> searchListRoom(int hotelId) throws NamingException, SQLException {
        List<RoomDTO> listRoom = new ArrayList<>();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnect();
            if (con != null) {
                String sql = "Select hotelId, roomNo, roomName, availableDate, quantity, typeId, roomPrice\n"
                        + "from tblRoom\n"
                        + "Where hotelId = ? ";
                pst = con.prepareStatement(sql);
                pst.setInt(1, hotelId);
                rs = pst.executeQuery();

                while (rs.next()) {
                    int roomNo = rs.getInt("roomNo");
                    String roomName = rs.getString("roomName");
                    String availableDate = rs.getString("availableDate");
                    int quantity = rs.getInt("quantity");
                    String typeId = rs.getString("typeId");
                    float roomPrice = rs.getFloat("roomPrice");
                    RoomDTO room = new RoomDTO(hotelId, roomNo, roomName, availableDate, quantity, typeId, roomPrice);
                    listRoom.add(room);
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
        return listRoom;

    }

    public RoomDTO getRoomInfor(int hotelId, int roomNo) throws NamingException, SQLException {
        RoomDTO room = null;
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnect();
            if (con != null) {
                String sql = "select tblHotel.hotelId, tblHotel.hotelName, roomNo, roomName, typeId, roomPrice \n"
                        + "from tblRoom\n"
                        + "inner join tblHotel on tblRoom.hotelId = tblHotel.hotelId\n"
                        + "Where tblHotel.hotelId = ?  and tblRoom.roomNo = ? ";
                pst = con.prepareStatement(sql);
                pst.setInt(1, hotelId);
                pst.setInt(2, roomNo);
                rs = pst.executeQuery();
                while (rs.next()) {
                    String hotelName = rs.getString("hotelName");
                    String roomName = rs.getString("roomName");
                    String typeId = rs.getString("typeId");
                    float roomPrice = rs.getFloat("roomPrice");

                    room = new RoomDTO(hotelId, hotelName, roomNo, roomName, "", 0, typeId, roomPrice, 0, "", "", 0);

                    //hotelId,hotelName,roomNo,roomName,typeId,roomPrice
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
        return room;

    }

    public List<RoomDTO> getListRoom() throws NamingException, SQLException {
        List<RoomDTO> listRoom = new ArrayList<>();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnect();
            if (con != null) {
                String sql = "select tblHotel.hotelId, tblHotel.hotelName, roomNo, roomName, quantity ,typeId, roomPrice \n"
                        + "from tblRoom\n"
                        + "inner join tblHotel on tblRoom.hotelId = tblHotel.hotelId		\n"
                        + "Where tblRoom.quantity > 0 ";
                pst = con.prepareStatement(sql);
                rs = pst.executeQuery();
                while (rs.next()) {
                    int hotelId = rs.getInt("hotelId");
                    String hotelName = rs.getString("hotelName");
                    int roomNo = rs.getInt("roomNo");
                    String roomName = rs.getString("roomName");
                    int quantity = rs.getInt("quantity");
                    String typeId = rs.getString("typeId");
                    float roomPrice = rs.getFloat("roomPrice");

                    RoomDTO room = new RoomDTO(hotelId, hotelName, roomNo, roomName, "", quantity, typeId, roomPrice, 0, "", "", 0);
                    listRoom.add(room);
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
        return listRoom;

    }

    public List<RoomDTO> searchByNameRoom(String searchValue) throws NamingException, SQLException {
        List<RoomDTO> listRoom = new ArrayList<>();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnect();
            if (con != null) {
                String sql = "select tblHotel.hotelId, tblHotel.hotelName, roomNo, roomName, quantity ,typeId, roomPrice\n"
                        + "from tblRoom\n"
                        + "inner join tblHotel on tblRoom.hotelId = tblHotel.hotelId\n"
                        + "where tblHotel.hotelName like ? and tblRoom.quantity > 0";
                pst = con.prepareStatement(sql);
                pst.setString(1, "%" + searchValue + "%");

                rs = pst.executeQuery();
                while (rs.next()) {

                    int hotelId = rs.getInt("hotelId");
                    String hotelName = rs.getString("hotelName");
                    int roomNo = rs.getInt("roomNo");
                    String roomName = rs.getString("roomName");
                    int quantity = rs.getInt("quantity");
                    String typeId = rs.getString("typeId");
                    float roomPrice = rs.getFloat("roomPrice");

                    RoomDTO room = new RoomDTO(hotelId, hotelName, roomNo, roomName, "", quantity, typeId, roomPrice, 0, "", "", 0);
                    listRoom.add(room);
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
        return listRoom;

    }

    public List<RoomDTO> searchListRoom(String searchValue, String typeRoom, String checkIn, String checkOut) throws NamingException, SQLException {
        List<RoomDTO> listRoom = new ArrayList<>();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnect();
            if (con != null) {
                String sql = "Select tblHotel.hotelId, tblHotel.hotelName, roomNo, roomName, quantity ,typeId, roomPrice\n"
                        + "from tblRoom	\n"
                        + "inner join tblHotel on tblHotel.hotelId = tblRoom.hotelId\n"
                        + "where tblHotel.hotelName like ? \n"
                        + "and tblRoom.typeId like ? \n"
                        + "and tblRoom.availableDate between ? and ? and tblRoom.quantity > 0";
                pst = con.prepareStatement(sql);
                pst.setString(1, "%" + searchValue + "%");
                pst.setString(2, typeRoom);
                pst.setString(3, checkIn);
                pst.setString(4, checkOut);

                rs = pst.executeQuery();
                while (rs.next()) {
                    int hotelId = rs.getInt("hotelId");
                    String hotelName = rs.getString("hotelName");
                    int roomNo = rs.getInt("roomNo");
                    String roomName = rs.getString("roomName");
                    int quantity = rs.getInt("quantity");
                    String typeId = rs.getString("typeId");
                    float roomPrice = rs.getFloat("roomPrice");

                    RoomDTO room = new RoomDTO(hotelId, hotelName, roomNo, roomName, "", quantity, typeId, roomPrice, 0, "", "", 0);
                    listRoom.add(room);
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
        return listRoom;

    }

    public List<RoomDTO> searchByTypeRoom(String searchValue) throws NamingException, SQLException {
        List<RoomDTO> listRoom = new ArrayList<>();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnect();
            if (con != null) {
                String sql = "select tblHotel.hotelId, tblHotel.hotelName, roomNo, roomName, quantity ,typeId, roomPrice\n"
                        + "from tblRoom\n"
                        + "inner join tblHotel on tblRoom.hotelId = tblHotel.hotelId\n"
                        + "where tblRoom.typeId like ? and tblRoom.quantity > 0";
                pst = con.prepareStatement(sql);
                pst.setString(1, "%" + searchValue + "%");

                rs = pst.executeQuery();
                while (rs.next()) {

                    int hotelId = rs.getInt("hotelId");
                    String hotelName = rs.getString("hotelName");
                    int roomNo = rs.getInt("roomNo");
                    String roomName = rs.getString("roomName");
                    int quantity = rs.getInt("quantity");
                    String typeId = rs.getString("typeId");
                    float roomPrice = rs.getFloat("roomPrice");

                    RoomDTO room = new RoomDTO(hotelId, hotelName, roomNo, roomName, "", quantity, typeId, roomPrice, 0, "", "", 0);
                    listRoom.add(room);
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
        return listRoom;

    }

    public int getRoomQuantity(int roomNo) throws SQLException, NamingException {
        int result = 0;
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnect();
            if (con != null) {
                String sql = "Select quantity\n"
                        + "from tblRoom\n"
                        + "Where roomNo = ?";
                pst = con.prepareStatement(sql);
                pst.setInt(1, roomNo);
                rs = pst.executeQuery();
                if (rs.next()) {
                    result = rs.getInt("quantity");
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
        return result;
    }

    public boolean setRoomQuantity(int roomNo, int quantity) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnect();
            if (con != null) {
                String sql = "update tblRoom\n"
                        + "set quantity = ? \n"
                        + "where roomNo = ? ";
                pst = con.prepareStatement(sql);
                pst.setInt(1, quantity);
                pst.setInt(2, roomNo);
                if (pst.executeUpdate() > 0) {
                    return true;
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
        return false;
    }

}
