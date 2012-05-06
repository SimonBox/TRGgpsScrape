/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gpsdistancegrab;

/**
 *
 * @author simon
 */
public class PositionSample {
    double Lat,
            Lon,
            ele,
            speed;
    TimeStamper time;
    
    public PositionSample(){}
    public PositionSample(double LatIn, double LonIn, double eleIn, double speedIn, TimeStamper timeIn)
    {
        Lat = LatIn; Lon=LonIn; ele=eleIn; speed=speedIn; time = timeIn;
    }
    public PositionSample(double LatIn, double LonIn, double eleIn, double speedIn, String timeIn)
    {
        Lat = LatIn; Lon=LonIn; ele=eleIn; speed=speedIn;
        time = new TimeStamper();
        time.LoadGPXdate(timeIn);
    }
    
}
