
package com.raven.event;

import com.raven.model.Model_HistoryChat;

public interface EventBody {
    public void sendChatToHistoryChat(Model_HistoryChat data);
}
