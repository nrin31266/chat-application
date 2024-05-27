
package com.raven.event;

import com.raven.model.Model_Image_Update;
import com.raven.model.Model_Name_Update;
import com.raven.model.Model_Profile;
import com.raven.model.Model_Profile_Update;
import com.raven.model.Model_User_Account;
import java.util.concurrent.CompletableFuture;
import javax.swing.Icon;

public interface EventProfile {
    public void setProfile(Model_User_Account data);
    
    public CompletableFuture<Model_Profile> getProfileAsync(Model_User_Account data);
    
    public void viewProfile();
    
    public Icon createImage(String imageBase64);
    
    public void updateProfile(Model_Profile_Update dataPr);
    
    public void updateAavatar(Model_Image_Update dataImage);
    
    public void updateName(Model_Name_Update name);
    
    public void updateCoverArt(Model_Image_Update dataImage);
}
