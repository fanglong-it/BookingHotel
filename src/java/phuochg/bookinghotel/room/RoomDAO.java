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

    public List<RoomDTO> searchListHotel(int hotelId) throws NamingException, SQLException {
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

}
