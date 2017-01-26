package org.mk.training.jmxjsr77;

// Import Servlet classes
import javax.servlet.*;
import javax.servlet.http.*;

// Import JNDI classes
import javax.naming.*;

// Import JDOM classes
import org.jdom.*;
import org.jdom.output.*;

// Import the Java classes
import java.util.*;
import javax.management.*;

import javax.management.j2ee.statistics.*;

/**
 * Abstract Base Class for building Statistic Servlets.
 *
 * Provides the following base functionality:
 *      - Queries MBean names, sorts, and caches
 *      - Debug mode to display all MBeans (with or without attributes)
 *      - Ability to refresh object names
 *      - XML output to the caller
 */
public abstract class AbstractStatsServlet extends HttpServlet
{
    protected InitialContext ic;
    protected ServletContext ctx = null;

    // Computation parameters
    protected long now = 0l;
    protected long lastSampleTime = 0l;
    protected Element lastRequest = null;


    /**
     * Obtains an MBeanServer to communicate with and uses it to build an initial
     * map of object names.
     * 
     * The map of object names is stored in the ServletContext with the name "object-names"
     * The MBeanServer is stored in the ServletContext with the name "mbean-server"
     */
    public void init()
    {
        try
        {
            // Load our contexts
            this.ctx = getServletContext();
            this.ic = new InitialContext();

            // See if we already have the ObjectName Map defined in the application object
            Map objectNames = ( Map )ctx.getAttribute( "object-names" );
            if( objectNames == null )
            {
                // Get the MBeanServer from the Servlet instance
                MBeanServer server = getMBeanServer();
                
                // Save our MBeanServer and preload and save our object names
                objectNames = this.preloadObjectNames( server, null);
                ctx.setAttribute( "object-names", objectNames );
                ctx.setAttribute( "mbean-server", server );
            }
        }
        catch( Exception ex )
        {
            ex.printStackTrace();
        }
    }

    /**
     * Converts a String to a boolean
     */
    private boolean getBoolean( String str )
    {
        if( str != null && str.equalsIgnoreCase( "true" ) )
        {
            return true;
        }
        return false;
    }

    /**
     * Converts a boolean to a String
     */
    private String getBooleanString( boolean b )
    {
        if( b )
        {
            return "true";
        }
        return "false";
    }

    /**
     * Returns an XML document to the caller containing MBean information. The following are request options:
     * 
     *      refresh             Refresh the object-names map to pick up any newly added MBeans
     *      debug               Dump the object-names map of MBeans inside the returned XML document
     *      showAttributes      When dumping the object-names map of MBeans, include as many attribute
     *                          values as we can extract
     *      showAttributeInfo   When showing attributes, display extended information about the attribute
     */
    public void service( HttpServletRequest req, HttpServletResponse res ) throws ServletException 
    {
        try
        {
            // Load our MBeanServer from the ServletContext
            MBeanServer server = ( MBeanServer )this.ctx.getAttribute( "mbean-server" );

            // Get our request objects
            boolean refresh = getBoolean( req.getParameter( "refresh" ) );
            boolean debug = true;//getBoolean( req.getParameter( "debug" ) );
            boolean showAttributes =true;// getBoolean( req.getParameter( "showAttributes" ) );
            boolean showAttributeInfo = true;//getBoolean( req.getParameter( "showAttributeInfo" ) );
            String objectName=req.getParameter("objectName");
            Map objectNames = null;
            if( refresh )
            {
                objectNames = this.preloadObjectNames( server ,objectName);
                System.out.println( "Refresh object map..." );
            }
            else
            {
                objectNames = ( Map )this.ctx.getAttribute( "object-names" );
            }
            this.now = System.currentTimeMillis();

            // Ask the Servlet instance for the root of the document
            Element root = this.getPerformanceRoot( server, objectNames );

            // Dump the MBean info
            if( debug )
            {
                Element mbeans = new Element( "mbeans" );
                for( Iterator i=objectNames.keySet().iterator(); i.hasNext(); )
                {
                    String key = ( String )i.next();
                    Element domain = new Element( "domain" );
                    domain.setAttribute( "name", key );
                    Map typeNames = ( Map )objectNames.get( key );
                    for( Iterator j=typeNames.keySet().iterator(); j.hasNext(); )
                    {
                        String typeName = ( String )j.next();
                        Element typeElement = new Element( "type" );
                        typeElement.setAttribute( "name", typeName );
                        List beans = ( List )typeNames.get( typeName );
                        for( Iterator k=beans.iterator(); k.hasNext(); )
                        {
                            ObjectName on = ( ObjectName )k.next();
                            Element bean = new Element( "mbean" );
                            bean.setAttribute( "name", on.getCanonicalName() );

                            // List the attributes
                            if( showAttributes )
                            {
                                try
                                {
                                    MBeanInfo info = server.getMBeanInfo( on );
                                    Element attributesElement = new Element( "attributes" );
                                    MBeanAttributeInfo[] attributeArray = info.getAttributes();
                                    for( int x=0; x<attributeArray.length; x++ )
                                    {
                                        String attributeName = attributeArray[ x ].getName();
                                        Element attributeElement = new Element( "attribute" );
                                        attributeElement.setAttribute( "name", attributeName );
                                        if( showAttributeInfo )
                                        {
                                            String attributeClass = attributeArray[ x ].getType();
                                            attributeElement.setAttribute( "class", attributeClass );
                                            attributeElement.setAttribute( "description", attributeArray[ x ].getDescription() );
                                            attributeElement.setAttribute( "is-getter", getBooleanString( attributeArray[ x ].isIs() ) );
                                            attributeElement.setAttribute( "readable", getBooleanString( attributeArray[ x ].isReadable() ) );
                                            attributeElement.setAttribute( "writable", getBooleanString( attributeArray[ x ].isWritable() ) );

                                            // Handle special cases
                                            if( attributeClass.equalsIgnoreCase( "javax.management.j2ee.statistics.Stats" ) )
                                            {
                                                Element statsElement = getStatsElement( ( Stats )( server.getAttribute( on, attributeName ) ) );
                                                attributeElement.addContent( statsElement );
                                            }
                                        }
                                        try
                                        {
                                            Object objectValue = server.getAttribute( on, attributeName );
                                            if( objectValue != null )
                                            {
                                                //attributeElement.addContent( objectValue.toString() );
                                            }
                                        }
                                        catch( Exception exx )
                                        {
                                            attributeElement.addContent( "Error obtaining value" );
                                        }
                                        attributesElement.addContent( attributeElement );
                                    }
                                    bean.addContent( attributesElement );
                                }
                                catch( Exception noAttributesException )
                                {
                                }
                            }

                            typeElement.addContent( bean );
                        }
                        domain.addContent( typeElement );
                    }
                    mbeans.addContent( domain );
                }
                root.addContent( mbeans );
            }

            // Save our last sample time
            this.lastSampleTime = this.now; 

            // Save the last request
            this.lastRequest = root;

            // Output the XML document to the caller
            XMLOutputter out = new XMLOutputter(  );
            out.output( root, res.getOutputStream() );
        }
        catch( Exception e )
        {
            e.printStackTrace();
            throw new ServletException( e );
        }
    }

    /**
     * This method extracts a JSR-77 stats metrics from the specified ObjectName
     * with the specified attribute name
     * 
     * @param stats         The JSR 77 Stats object
     * 
     * @return              A JDOM XML node containing the statistics
     */
    protected Element getStatsElement( Stats stats )
    {
        Element statsElement = new Element( "stats" );
        try
        {
            Statistic[] statistics = stats.getStatistics();
            for( int i=0; i<statistics.length; i++ )
            {
                Element statElement = getStatElement( statistics[ i ] );
                statsElement.addContent( statElement );
            }
        }
        catch( Exception e )
        {
            Element exceptionElement = new Element( "exception" );
            exceptionElement.addContent( e.toString() );
            statsElement.addContent( exceptionElement );
        }
        return statsElement;

    }

    protected Element getStatElement( Statistic statistic )
    {
        Element statElement = new Element( "stat" );
        try
        {
            statElement.setAttribute( "name", statistic.getName() );
            statElement.setAttribute( "description", statistic.getDescription() );
            statElement.setAttribute( "unit", statistic.getUnit() );
            statElement.setAttribute( "start-time", Long.toString( statistic.getStartTime() ) );
            statElement.setAttribute( "last-sample-time", Long.toString( statistic.getLastSampleTime() ) );

            // Get the specific statistic type information
            if( statistic instanceof BoundedRangeStatistic )
            {
                statElement.setAttribute( "type", "bounded-range-statistic" );

                BoundedRangeStatistic brs = ( BoundedRangeStatistic )statistic;
                statElement.setAttribute( "current", Long.toString( brs.getCurrent() ) );
                statElement.setAttribute( "low-water-mark", Long.toString( brs.getLowWaterMark() ) );
                statElement.setAttribute( "high-water-mark", Long.toString( brs.getHighWaterMark() ) );
                statElement.setAttribute( "lower-bound", Long.toString( brs.getLowerBound() ) );
                statElement.setAttribute( "upper-bound", Long.toString( brs.getUpperBound() ) );
            }
            else if( statistic instanceof BoundaryStatistic )
            {
                statElement.setAttribute( "type", "boundary-statistic" );

                BoundaryStatistic bs = ( BoundaryStatistic )statistic;
                statElement.setAttribute( "lower-bound", Long.toString( bs.getLowerBound() ) );
                statElement.setAttribute( "upper-bound", Long.toString( bs.getUpperBound() ) );
            }
            else if( statistic instanceof RangeStatistic )
            {
                statElement.setAttribute( "type", "range-statistic" );

                RangeStatistic rs = ( RangeStatistic )statistic;
                statElement.setAttribute( "current", Long.toString( rs.getCurrent() ) );
                statElement.setAttribute( "low-water-mark", Long.toString( rs.getLowWaterMark() ) );
                statElement.setAttribute( "high-water-mark", Long.toString( rs.getHighWaterMark() ) );
            }
            else if( statistic instanceof CountStatistic )
            {
                statElement.setAttribute( "type", "count-statistic" );

                CountStatistic cs = ( CountStatistic )statistic;
                statElement.setAttribute( "count", Long.toString( cs.getCount() ) );
            }
            
            else if( statistic instanceof TimeStatistic )
            {
                statElement.setAttribute( "type", "time-statistic" );

                TimeStatistic ts = ( TimeStatistic )statistic;
                statElement.setAttribute( "count", Long.toString( ts.getCount() ) );
                statElement.setAttribute( "max-time", Long.toString( ts.getMaxTime() ) );
                statElement.setAttribute( "min-time", Long.toString( ts.getMinTime() ) );
                statElement.setAttribute( "total-time", Long.toString( ts.getTotalTime() ) );
            }
        }
        catch( Exception e )
        {
            Element exceptionElement = new Element( "exception" );
            exceptionElement.addContent( e.toString() );
            statElement.addContent( exceptionElement );
        }
        return statElement;
    }

    /**
     * Classes extending this Servlet are responsible for locating and returning
     * an MBeanServer instance. This instance is used to preload object names and for
     * managing state access.
     */
    public abstract MBeanServer getMBeanServer();

    /**
     * This is the main focus point of the Application Server specific Servlet classes;
     * though the getPerformanceRoot() method you will build an XML document that you
     * want to return to the caller
     */
    public abstract Element getPerformanceRoot( MBeanServer server, Map objectNames );


    /**
     * Returns a specific ObjectName with the mbean name for the specified mbean type
     * in the specified domain
     */
    protected ObjectName getObjectName( Map objectNames, String domain, String type, String name )
    {
        // Get the List of domain names
        List ons = getObjectNames(objectNames,domain,type);

        // Find the requested bean
        for( Iterator i=ons.iterator(); i.hasNext(); )
        {
            ObjectName on = ( ObjectName )i.next();
            String objectName = on.getKeyProperty( "name" );
            if( objectName != null && objectName.equalsIgnoreCase( name ) )
            {
                // Found it
                return on;
            }
        }

        // Didn't find it
        return null;
    }

    /**
     * Returns a List of ObjectNames in the specified domain for the specified
     * type of mbeans
     */
    protected List getObjectNames( Map objectNames, String domain, String type )
    {
        // Get the domain map
        Map domainMap = getDomainMap(objectNames,domain);
        if( domainMap == null )
        {
            return null;
        }

        // Get the List of ObjectNames
        List l = ( List )domainMap.get( type );
        return l;
    }

    /**
     * Returns the domain map for the specified doamin name from the map of 
     * object names; map of object names must be passed instead of stored as a
     * member variable to support multithreading
     */
    protected Map getDomainMap( Map objectNames, String domain )
    {
        // Get the domain Map
        Map domainMap = ( Map )objectNames.get( domain );
        return domainMap;
    }


    /**
     * Returns all of the domain names found in the MBeanServer
     */
    protected Set getDomainNames( Map objectNames )
    {
        return objectNames.keySet();
    }

    /**
     * Preloads the ObjectName instances and sorts them into a Map indexed by
     * domain; e.g. jboss.web is a domain and Jetty=0,SocketListener=0 is the 
     * ObjectName.
     * 
     * For WebSphere, further categorizes by "type":
     *  Map of domain names to a vector of maps of type names to object names
     */
    protected Map preloadObjectNames( MBeanServer server,String on )
    {
        Map objectNames = new TreeMap();
        try
        {
        	ObjectName ddname=null;
        	if (on==null) {
        		ddname=new ObjectName("*:*");
			}
        	else{
        		ddname=new ObjectName(on);
        	}
            Set ons = server.queryNames( ddname, null );
            for( Iterator i=ons.iterator(); i.hasNext(); )
            {
                ObjectName name = ( ObjectName )i.next();
                String domain = name.getDomain();
                Map typeNames = null;
                if( objectNames.containsKey( domain ) )
                {
                    // Load this domain's List from our map and 
                    // add this ObjectName to it
                    typeNames = ( Map )objectNames.get( domain );
                }
                else
                {
                    // This is a domain that we don't have yet, add it
                    // to our map
                    typeNames = new TreeMap();
                    objectNames.put( domain, typeNames );
                }

                // Search the typeNames map to match the type of this object
                String typeName = name.getKeyProperty( "type" );
                if( typeName == null ) typeName = name.getKeyProperty( "Type" );
                //if( typeName == null ) typeName = name.getKeyProperty( "j2eeType" );
                if( typeName == null ) typeName = "none";

                if( typeNames.containsKey( typeName ) )
                {
                    List l = ( List )typeNames.get( typeName );
                    l.add( name );
                }
                else
                {
                    List l = new ArrayList();
                    l.add( name );
                    typeNames.put( typeName, l );
                }
            }
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }
        return objectNames;
    }
}
