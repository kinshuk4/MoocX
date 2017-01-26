MC4J 1.2 beta 8 Read Me
Release 1/18/05
http://mc4j.sourceforge.net


 Copyright 2002-2005 Greg Hinkle

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.

 Release Notes
 1.2 beta 8
    * WebSphere 5 support fixed
    * WebSphere 6 support added
    * WebLogic 9 support added
    * JSR-77 support updates and bug fixes
    * Better query nodes (Still not persistent)
    * Sun Java App server support added

1.2 beta 7
    * HTML layout for custom dashboards
    * Support for Geronimo and Pramati
    * Support for J2SE 1.5 final
    * User interface enhancements
    * Stability enhancements
    * JSR-77 support including statistics views

Overview:
    This software allows for remote connections to JMX servers and
    provides the ability to browse existing managed beans (MBeans)
    and execute operations on them. It currently supports connecting
    to the MX4J project JRMP connector, the JBoss Application Server,
    Bea's WebLogic application server,the JDMK RMI connector from
    Sun, IBM's WebSphere 5.0 server, the JSR 160 reference implementation
    and MX4J 2.0's JSR 160 connector as well as OC4J 10.0.3 developer
    preview 2.

Note:
    This software utilizes an Installer package in order to create
    platform specific binaries for easier startup. It only creates
    the executable, linking it to your choice of JVM and also allows
    for things like linking it in Windows Program Files. It does not
    install any non-Java, unrelated software anywhere on your drive.


Features:
    * Tree view of J2EE application structures based on JSR-77
    * Tree view of MBeans, their attributes, operations and notifications
    * Connects to and remembers multiple servers
    * Descriptor information for Mbeans, attributes and operations
    * Running graphs of numeric MBean attributes
    * The ability to execute operations on MBeans and view html results
    * The ability to set attribute values of many common types
    * The ability to listen for and track notifications


Install:
    Installers are available for Windows, Mac OS X and Linux. A JVM
    version 1.4 or higher is required. JDK 1.5 is recommended.


Connecting to servers:
    For specific information on how to connect to a specific server type
    please see the user guide at http://mc4j.sourceforge.net

Building:
    For information on how to compile and run MC4J from the source code
    please see the build guide at http://mc4j.sourceforge.net. You will
    need to download the netbeans platform binary and set up the build
    before you will be able to compile and run MC4J from the sources.

Comments:
    Please submit bugs, comments and suggestions to the MC4J project on
    SourceForge (http://mc4j.sourceforge.net). Also, checkout our JMX
    Wiki of information and knowledge sharing at
    (http://mc4j.sourceforge.net/wiki).


Credits:
    This software includes libraries developed by the MX4J project
    (http://mx4j.sourceforge.net). This excellent project is providing
    an open source implementation of the JMX Specification
    (http://java.sun.com/products/JavaManagement/index.html).

    This software executes within the container of the NetBeans platform
    (http://www.netbeans.org). The NetBeans platform provides a terrific
    base of functionality that really enriches this application. It also
    includes other open source projects as

    The charting functionality of this application is provided by the
    JFreeChart project. (http://www.jfree.org/jfreechart/index.html)
    This terrific project includes all sorts of great charts and I am
    grateful for their awesome efforts.


Copyright:
    WebLogic is a trademark of Bea Systems (http://www.bea.com). MX4J is
    copyright the MX4J project (http://mx4j.sourceforge.net). This software
    and the MC4J logo are copyright Greg Hinkle.
    JMX is a trademark of Sun (http://www.sun.com). All other trademarks and
    copyrights are those of their respective owners.