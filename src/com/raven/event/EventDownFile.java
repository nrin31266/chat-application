
package com.raven.event;

import com.raven.model.Model_File_Sender;
import com.raven.model.Model_Receive_File;

public interface EventDownFile {
    public void downFile(int fileID, String fileExcetion, String fileName);
    
    public void localFileReceiver(int fileID, String fileExcetione);
    
    public void localFileSender(int fileID, String fileExcetion, String fileName);
}
