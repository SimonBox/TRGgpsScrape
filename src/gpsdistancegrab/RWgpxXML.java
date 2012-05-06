/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gpsdistancegrab;
import org.w3c.dom.*;
import java.util.Vector;

/**
 *
 * @author simon
 */
public class RWgpxXML extends RWXML {
    TimeStamper StartTime;
    TimeStamper EndTime;
    Vector<PositionSample> LoggedPositions = new Vector<PositionSample>();
    
    public RWgpxXML(String fn,TimeStamper ST, TimeStamper ET){
        super(fn);
        StartTime=ST;
        EndTime=ET;
        
        ReadInit();
        
        NodeList TrackPoints = Doc.getElementsByTagName("trkpt");
        
        
        for(int i=0; i<TrackPoints.getLength(); i++){
            Element TrackPoint = (Element)TrackPoints.item(i);
            
            String DateStr = TrackPoint.getElementsByTagName("time").item(0).getTextContent();
            TimeStamper Now = new TimeStamper();
            Now.LoadGPXdate(DateStr);
            
            String t1 = Now.ReturnGPXTime();
            String t2 = StartTime.ReturnGPXTime();
            String t3 = EndTime.ReturnGPXTime();
            
            if(Now.TheDate.after(StartTime.TheDate)&&Now.TheDate.before(EndTime.TheDate))
            {
                AddToPositionList(TrackPoint,Now);
            }
        }
        int A = 0;
        
    }
    
    private void AddToPositionList(Element TrackPnt,TimeStamper tme)
    {
        double Lat = Double.parseDouble(TrackPnt.getAttribute("lat"));
        double Lon = Double.parseDouble(TrackPnt.getAttribute("lon"));

        double Elevation = Double.parseDouble(TrackPnt.getElementsByTagName("ele").item(0).getTextContent());
        double Speed = Double.parseDouble(TrackPnt.getElementsByTagName("speed").item(0).getTextContent());
        
        LoggedPositions.add(new PositionSample(Lat,Lon,Elevation,Speed,tme));
        
    }
    
}
