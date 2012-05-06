/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gpsdistancegrab;

import java.io.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Vector;
/**
 *
 * @author simon
 */
public class ScrapeAway {
    
    File GPXfolder;
    String[] Filenames;
    TimeStamper StartTime;
    TimeStamper EndTime;
    
    public ScrapeAway(String Path,String DateS, String StartTimeS, String EndTimeS)
    {
        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".gpx");
            }
        };
        
        GPXfolder = new File(Path);
        Filenames = GPXfolder.list(filter);
        StartTime = new TimeStamper();
        StartTime.LoadGUItime(DateS + "_" + StartTimeS);
        EndTime = new TimeStamper();
        EndTime.LoadGUItime(DateS+ "_" + EndTimeS);
        
        
    }
    
    public void ListGPXdata()
    {
        for(String fn : Filenames)
        {
            String fPath = GPXfolder.getPath() + GPXfolder.separator + fn;
            RWgpxXML ReadGPX = new RWgpxXML(fPath,StartTime,EndTime);
            CalculateTrackStatistics(ReadGPX.LoggedPositions);
        }
        
    }
    
    private void CalculateTrackStatistics(Vector<PositionSample> TheData){
        
        double DistanceTravelled = 0;
        int j=0;
        for(int i=1;i<TheData.size();i++)
        {
            uk.me.jstott.jcoord.LatLng Point1 = new uk.me.jstott.jcoord.LatLng(TheData.get(i-1).Lat, TheData.get(i-1).Lon);
            uk.me.jstott.jcoord.LatLng Point2 = new uk.me.jstott.jcoord.LatLng(TheData.get(i).Lat, TheData.get(i).Lon);
            
            
            double d = Point1.distance(Point2)*1000;
            if(!Double.isNaN(d))
            {
                DistanceTravelled += d;
            }
            else{
                j++;
            }
        }
        
        Long msGap = TheData.lastElement().time.TheDate.getTime()-TheData.firstElement().time.TheDate.getTime();
        String t1 = TheData.firstElement().time.ReturnGuiTime();
        String t2 = TheData.lastElement().time.ReturnGuiTime();
        double sTimespan = (double)msGap/1000;
        
        double msSpeed = DistanceTravelled/sTimespan;
    }
    
}
