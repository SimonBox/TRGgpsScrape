/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gpsdistancegrab;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author simon
 */
public class TimeStamper {
    Date TheDate;
    SimpleDateFormat Guiparser;
    SimpleDateFormat GPXparser;
    boolean LoadedOK;

    public TimeStamper(){
        Guiparser = new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss");
        GPXparser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        TheDate = new Date();
        LoadedOK = false;
    }

    public String ReturnGuiTime()
    {
        return(Guiparser.format(TheDate));
    }
    
    public String ReturnGPXTime()
    {
        return(GPXparser.format(TheDate));
    }
    
    
    public void LoadGPXdate(String dateS)
    {
        try{
        TheDate = GPXparser.parse(dateS);
        LoadedOK=true;
        }
        catch(Exception e){
            LoadedOK=false;
        }
    }
    
    public void LoadGUItime(String dateS)
    {
        try{
        TheDate = Guiparser.parse(dateS);
        LoadedOK = true;
        }
        catch(Exception e){
            LoadedOK=false;
        }
    }
    
}
