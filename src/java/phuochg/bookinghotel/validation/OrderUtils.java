/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phuochg.bookinghotel.validation;

import java.util.List;
import phuochg.bookinghotel.room.RoomDTO;

/**
 *
 * @author Fanglong-it
 */
public class OrderUtils {

    public boolean checkExistIncart(List<RoomDTO> listCart, List<RoomDTO> listRoom) {
        int RoomNo = 0;
        for (int j = 0; j < listCart.size(); j++) {
            RoomNo = listCart.get(j).getRoomNo();
            for (int i = 0; i < listRoom.size(); i++) {
                if (listRoom.get(i).getRoomNo() == RoomNo) {
                    return true;
                }

            }
        }
        return false;
    }

    public void removeListRoom(List<RoomDTO> listCart, List<RoomDTO> listRoom) {
        int RoomNo = 0;
        for (int j = 0; j < listCart.size(); j++) {
            RoomNo = listCart.get(j).getRoomNo();
            for (int i = 0; i < listRoom.size(); i++) {
                if (listRoom.get(i).getRoomNo() == RoomNo) {
                    listRoom.remove(i);
                }

            }
        }
    }

    public float calculatePriceTotalRoomPrice(int night, float Price) {
        float result = 0;
        result = night * Price;
        return result;
    }

    public float calculateTotal(List<RoomDTO> listRoom) {
        float total = 0;
        for (int i = 0; i < listRoom.size(); i++) {
            total += listRoom.get(i).getTotal();
        }
        return total;
    }

    public boolean deleteRoomCart(List<RoomDTO> listRoom, int roomNo) {
        for (int i = 0; i < listRoom.size(); i++) {
            if (listRoom.get(i).getRoomNo() == roomNo) {
                listRoom.remove(i);
                return true;
            }
        }
        return false;
    }

    public void updateRoomNight(List<RoomDTO> listRoom, int roomNo, int night) {
        for (int i = 0; i < listRoom.size(); i++) {
            if (listRoom.get(i).getRoomNo() == roomNo) {
                listRoom.get(i).setNight(night);
            }
        }
    }

    public void updateRoomTotal(List<RoomDTO> listRoom, int roomNo, float total) {
        for (int i = 0; i < listRoom.size(); i++) {
            if (listRoom.get(i).getRoomNo() == roomNo) {
                listRoom.get(i).setTotal(total);
            }
        }
    }

    public String getCheckInRoomDate(List<RoomDTO> listRoom, int roomNo) {
        String checkInDate = "";
        for (int i = 0; i < listRoom.size(); i++) {
            if (listRoom.get(i).getRoomNo() == roomNo) {
                checkInDate = listRoom.get(i).getCheckInDate();
            }
        }
        return checkInDate;
    }

    public float getRoomPrice(List<RoomDTO> listRoom, int roomNo) {
        float price = 0;
        for (int i = 0; i < listRoom.size(); i++) {
            if (listRoom.get(i).getRoomNo() == roomNo) {
                price = listRoom.get(i).getPrice();
            }
        }
        return price;
    }

    public void updateRoomCheckoutDate(List<RoomDTO> listRoom, int roomNo, String checkOutDate) {
        for (int i = 0; i < listRoom.size(); i++) {
            if (listRoom.get(i).getRoomNo() == roomNo) {
                listRoom.get(i).setCheckOutDate(checkOutDate);
            }
        }
    }

    public String getAlphaNumericString(int n) {
        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int) (AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }
        return sb.toString();
    }

    public float calculatorDiscount(float total, int value) {
        return (total * value) / 100;
    }

    public boolean checkExistInDB(List<RoomDTO> listRoom, int roomNo) {
        for (int i = 0; i < listRoom.size(); i++) {
            if (listRoom.get(i).getRoomNo() == roomNo) {
                return true;
            }
        }
        return false;
    }

}
