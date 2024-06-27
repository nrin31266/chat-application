
package com.raven.event;

import com.raven.model.Model_Get_Chat_History;
import com.raven.model.Model_HistoryChat;

public interface EventBody {
    public void sendChatToHistoryChat(Model_HistoryChat data);
    
    public void receiverChatHisTory(Model_Get_Chat_History d);
    
    public void searchMes(String content);
}
