/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gpsdistancegrab;

/**
 *
 * @author simon
 */ 
    /*
%## Copyright (C) 2010 S.Box
%## 
%## This program is free software; you can redistribute it and/or modify
%## it under the terms of the GNU General Public License as published by
%## the Free Software Foundation; either version 2 of the License, or
%## (at your option) any later version.
%## 
%## This program is distributed in the hope that it will be useful,
%## but WITHOUT ANY WARRANTY; without even the implied warranty of
%## MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
%## GNU General Public License for more details.
%## 
%## You should have received a copy of the GNU General Public License
%## along with this program; if not, write to the Free Software
%## Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA

%## RWXML.java

%## Author: S.Box
%## Created: 2010-05-27
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;
import java.io.*;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author sb4p07
 */
public class RWXML {
    //*Class member
    DocumentBuilderFactory DBF;
    DocumentBuilder builder;
    Document Doc;
    String Filename;
    TimeStamper tStamp = new TimeStamper();

    ///Class Constructors
    public RWXML(String fn)
    {
        Filename = fn;
        DBF = DocumentBuilderFactory.newInstance();
        try{
            builder = DBF.newDocumentBuilder();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    //Function to initiate Doc for reading
    protected void ReadInit()
    {
        try{
            Doc = builder.parse(new File(Filename));
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,("Reading " + Filename +" failed. \nSystem msg: " + e));
        }

    }
    //Function to initiate Doc for writing
    protected void WriteInit()
    {
        try{
            Doc = builder.newDocument();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,("Launching new DOM failed. \nSystem msg: " + e));
        }
    }
    //Function to write the doc to an output xml with Filename
    protected void WriteOutput()
    {
        Source src = new DOMSource(Doc);

        File fl = new File(Filename);
        Result res = new StreamResult(fl);

        try{
            Transformer xform = TransformerFactory.newInstance().newTransformer();
            xform.transform(src, res);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,("Writing " + Filename +" failed. \nSystem msg: " + e));
        }

    }

    //function to return double under Doc given a name
    protected double DbyName(String name)
    {
        return(Double.valueOf(Doc.getElementsByTagName(name).item(0).getTextContent()));
    }

    //function to returnstring under Doc given a name
    protected String SbyName(String name)
    {
        return(Doc.getElementsByTagName(name).item(0).getTextContent());
    }

    //function to return int under Doc by given name
    protected int IbyName(String name)
    {
        return(Integer.valueOf(Doc.getElementsByTagName(name).item(0).getTextContent()));
    }

    //function to return int under Doc by given name
    protected boolean BbyName(String name)
    {
        return(Boolean.parseBoolean(Doc.getElementsByTagName(name).item(0).getTextContent()));
    }


    //function to return Vector<double> under Doc given a name
    protected Vector<Double> VDbyName(String name)
    {
        return(DstringToVector(Doc.getElementsByTagName(name).item(0).getTextContent()));
    }
    //*Function do split a data string into a vector of doubles
    protected Vector<Double> DstringToVector(String Dstring)
    {
        String[] SplitString = Dstring.split(",");
        Vector<Double> Temp = new Vector<Double>();

        for(String S : SplitString)
        {
            Temp.add(Double.valueOf(S));
        }
        return(Temp);
    }
    //and back
    protected String VectorToDstring(Vector<Double> Vec)
    {
        String Temp ="";
        for(double d : Vec)
        {
            Temp += (Double.toString(d) + ", ");
        }
        Temp = Temp.substring(0, (Temp.length() - 2)); // trim off  the last comma

        return(Temp);
    }

    protected String MatrixToDstring(double[][] Mat)
    {
        String Temp ="";
        for(double[] Row : Mat)
        {
            for(double elem : Row)
            {
                Temp += (Double.toString(elem) + ",");
            }
            Temp = Temp.substring(0, (Temp.length() - 2)); // trim off  the last comma
            Temp += ";\n";

        }
        return(Temp);

    }

     public Node CreateDataNode(String NodeName, String Data)
    {
        Node Temp = Doc.createElement(NodeName);
        Temp.appendChild(Doc.createTextNode(Data));
        return(Temp);
    }
     
     public Node CreateNode(String NodeName){
         return(Doc.createElement(NodeName));
     }


    
}
