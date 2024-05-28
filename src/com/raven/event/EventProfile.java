
package com.raven.event;

import com.raven.model.Model_Image_Update;
import com.raven.model.Model_Name_Update;
import com.raven.model.Model_Profile;
import com.raven.model.Model_Profile_Update;
import com.raven.model.Model_User_Account;
import com.raven.model.UserIDToJSON;
import java.util.concurrent.CompletableFuture;
import javax.swing.Icon;

public interface EventProfile {
    
    public CompletableFuture<Model_Profile> getProfileAsync(Model_User_Account data);
    
    public CompletableFuture<String> getAvt(UserIDToJSON userID);
    
    public CompletableFuture<String> getCoverArt(UserIDToJSON id);
    
    public void viewProfile();
    
    public Icon createImage(String imageBase64);
    
    public void updateProfile(Model_Profile_Update dataPr);
    
    public boolean updateAavatar(Model_Image_Update dataImage);
    
    public boolean updateCoverArt(Model_Image_Update dataImage);
    
    public void updateName(Model_Name_Update name);
    
    
}
