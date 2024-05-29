package com.raven.event;

import com.raven.model.Model_Receive_Image;
import javax.swing.Icon;

public interface EventImageView {

    public void viewImage(Icon image, String mode, int fileID, String fileExcetion, String fileName);

    public void saveImage(int fileId, String fileExcetion, String fileName);
    
    public void viewLocation(String mode, int fileId, String fileExcetion, String fileName);
}
