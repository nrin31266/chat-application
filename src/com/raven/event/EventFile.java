
package com.raven.event;

import javax.swing.Icon;


public interface EventFile {
    public String fileSizeConversion(String fileSize);
    
    public Icon getIconFile(String fileExcetion);
    
    public String getFileExtension(String fn);
}
