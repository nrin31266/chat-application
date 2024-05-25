package com.raven.event;

import com.raven.model.Model_Receive_Image;
import javax.swing.Icon;

public interface EventImageView {

    public void viewImage(Icon image,String mode, Model_Receive_Image data);

    public void saveImage(Icon image,String mode,Model_Receive_Image data);
    
    public void viewLocation(Icon image,String mode,Model_Receive_Image data);
}
