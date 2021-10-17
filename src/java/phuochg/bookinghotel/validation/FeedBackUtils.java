/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phuochg.bookinghotel.validation;

import java.util.List;
import phuochg.bookinghotel.feedback.FeedBackDTO;

/**
 *
 * @author Fanglong-it
 */
public class FeedBackUtils {

    public int caculatorStarValue(List<FeedBackDTO> listFeedBackDTOs) {
        int result = 0;
        int index = listFeedBackDTOs.size();
        for (int i = 0; i < listFeedBackDTOs.size(); i++) {
            result += listFeedBackDTOs.get(i).getValue();
        }
        return result / index;
    }
}
