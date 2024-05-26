
package com.raven.event;

import com.raven.model.Model_Profile;
import com.raven.model.Model_User_Account;
import javax.swing.Icon;

public interface EventProfile {
    public Model_Profile getProfileMe(Model_User_Account data);
    
    public void viewProfile(Model_Profile data);
    
    public Icon createImage(String imageBase64);
}
