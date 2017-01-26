package com.remote.http;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import com.remote.beans.OpenAddress;
import com.remote.beans.OpenEmployee;
import com.remote.beans.StandardEmployee;
import com.sun.jdmk.comm.HtmlAdaptorServer;

public class JMXRIHttpAdapterServerMain {

    /**
     * @param args
     */
    public static void main(String[] args) {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        HtmlAdaptorServer adapter = new HtmlAdaptorServer();

        // Unique identification of MBeans
        StandardEmployee emp = new StandardEmployee();
        try {
            ObjectName empname =
                    new ObjectName("com.remote.simple:type=StandardEmployee");
            // Uniquely identify the MBeans and register them with the MBeanServer
            mbs.registerMBean(emp, empname);
            ObjectName name2 =
                    new ObjectName("com.remote.simple:type=OpenEmployee");

            // Create the Employee MXBean
            List<String> projects = new ArrayList<String>();
            projects.add("GPS");
            projects.add("PEERTOPEER");
            List<OpenAddress> onshorelocations = new ArrayList<OpenAddress>();
            onshorelocations.add(new OpenAddress("MGRoad", "CopenHagen", "Denmark", 560062));
            onshorelocations.add(new OpenAddress("MGRoad", "Zurick", "Switzerland", 560062));
            Map<String, String> projectnamesrolesmap = new HashMap<String, String>();
            projectnamesrolesmap.put("GPS", "PL");
            projectnamesrolesmap.put("PEERTOPEER", "PM");
            Map<String, OpenAddress> projectnameslocationsmap = new HashMap<String, OpenAddress>();
            projectnameslocationsmap.put("GPS", new OpenAddress("MGRoad", "CopenHagen", "Denmark", 560062));
            projectnameslocationsmap.put("PEERTOPEER", new OpenAddress("MGRoad", "Zurick", "Switzerland", 560062));

            OpenEmployee empmxbean = new OpenEmployee("Cheeky", "Monkey", 1000, 5.15f, false, new OpenAddress("MGRoad", "Bangalore", "KAR", 560062), projects, onshorelocations, projectnamesrolesmap, projectnameslocationsmap);

            // Register the Employee MXBean
            mbs.registerMBean(empmxbean, name2);

            // Register and start the HTML adaptor
            ObjectName adapterName = new ObjectName("SimpleAgent:name=htmladapter,port=8000");
            adapter.setPort(8000);
            mbs.registerMBean(adapter, adapterName);
            System.out.println("Started on port 8000");
            adapter.start();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
