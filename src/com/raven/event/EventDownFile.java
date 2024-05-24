
package com.raven.event;

import com.raven.model.Model_File_Sender;
import com.raven.model.Model_Receive_File;

public interface EventDownFile {
    public void downFile(Model_Receive_File data);
    
    public void localFileReceiver( Model_Receive_File data);
    
    public void localFileSender(Model_File_Sender data);
}
