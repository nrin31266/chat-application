package com.raven.event;

import com.raven.form.Home;
import com.raven.model.Model_Profile;
import com.raven.model.Model_User_Account;
import java.io.IOException;

public interface EventMain {

    public void showLoading(boolean show);

    public void initChat();
    
    public void selectUser(Model_User_Account user);
    
    public void updateUser(Model_User_Account user); 
    
    public void updateProfile(Model_Profile dataPr);
    
    public void setTitleName(String s);
    
    public Home getHome();
    
    public String processImage(byte[] imageByte) throws IOException;
}
