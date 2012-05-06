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
    
    public Vector<JourneyStats> ListGPXdata()
    {
        Vector<JourneyStats> TheStats = new Vector<JourneyStats>();
        for(String fn : Filenames)
        {
            String fPath = GPXfolder.getPath() + GPXfolder.separator + fn;
            RWgpxXML ReadGPX = new RWgpxXML(fPath,StartTime,EndTime);
            JourneyStats JS = CalculateTrackStatistics(ReadGPX.LoggedPositions);
            TheStats.add(JS);
        }
        return(TheStats);
    }
    
    private JourneyStats CalculateTrackStatistics(Vector<PositionSample> TheData){
        
        double DistanceTravelled = 0;
        double WeightedSpeed = 0;
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
            
            Long msWeight = TheData.get(i).time.TheDate.getTime()-TheData.get(i-1).time.TheDate.getTime();
            double sWeight = (double)msWeight/1000;
            
            WeightedSpeed += TheData.get(i).speed*sWeight;
            
            
        }
        
        Long msGap = TheData.lastElement().time.TheDate.getTime()-TheData.firstElement().time.TheDate.getTime();
        
        double sTimespan = (double)msGap/1000;
        
        double Speed1 = DistanceTravelled/sTimespan;
        double Speed2 = WeightedSpeed/sTimespan;
        
        
        JourneyStats JS = new JourneyStats();
        JS.DistanceTravelled = DistanceTravelled;
        JS.TimeTaken = sTimespan;
        JS.AvSpeed1 = Speed1;
        JS.AvSpeed2 = Speed2;
        
        JS.JourneyStart = TheData.firstElement().time;
        JS.JourneyEnd = TheData.lastElement().time;
        
        return(JS);
    }
    
}
