package org.mk.training.jdbc.xa;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.mk.training.jdbc.BaseWrapperManagedConnectionFactory;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.beans.PropertyEditorManager;
import java.beans.PropertyEditor;
import javax.sql.XADataSource;
import javax.sql.XAConnection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import javax.resource.ResourceException;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.ManagedConnection;
import javax.security.auth.Subject;

public class XAManagedConnectionFactory extends BaseWrapperManagedConnectionFactory {

    private static final long serialVersionUID = 1647927657609573729L;
    public static final String URL = "URL";
    public static final String ISSAMERM = "IsSameRMOverrideValue";
    private static final String SPACE = " ";
    private static final String NEWLINE = "\n";
    private static final String TAB = "\t";
    private static final String DELIMITER = "=";
    private static final byte[] SPACEBYTE = SPACE.getBytes();
    private static final byte[] NEWLINEBYTE = NEWLINE.getBytes();
    private static final byte[] TABBYTE = TAB.getBytes();
    private static final byte[] DELIMITERBYTE = DELIMITER.getBytes();
    private String xaDataSourceClass;
    private String xaDataSourceProperties;
    private Boolean isSameRMOverrideValue;
    private XADataSource xads;

    public XAManagedConnectionFactory() {
        System.out.println("XAManagedConnectionFactory()");
    }

    public String getURL() {
        return connectionProps.getProperty(URL);
    }

    public void setURL(final String connectionURL) {
        connectionProps.setProperty(URL, connectionURL);
    }

    public String getXADataSourceClass() {
        return xaDataSourceClass;
    }

    public void setXADataSourceClass(String xaDataSourceClass) {
        System.out.println("XAManagedConnectionFactory().setXADataSourceClass(String xaDataSourceClass):: "
                + "" + xaDataSourceClass);
        this.xaDataSourceClass = xaDataSourceClass;
    }

    public String getXADataSourceProperties() {
        return xaDataSourceProperties;
    }

    public void setXADataSourceProperties(String xaDataSourceProperties)
            throws ResourceException {
        this.xaDataSourceProperties = xaDataSourceProperties;
        //xaProps.clear();
        if (xaDataSourceProperties != null) {
            xaDataSourceProperties = xaDataSourceProperties.replaceAll(NEWLINE, SPACE);
            xaDataSourceProperties = xaDataSourceProperties.replaceAll(TAB, SPACE);
            Properties parsedprops = this.getProperties(xaDataSourceProperties);
            connectionProps.putAll(parsedprops);
            String tranisol = connectionProps.getProperty(TRANSACIONISOLATION);
            if (tranisol != null) {
                this.setTransactionIsolation(tranisol);
                connectionProps.remove(TRANSACIONISOLATION);
            }
            String issamerm = connectionProps.getProperty(ISSAMERM);
            if (issamerm != null) {
                Boolean isb = Boolean.valueOf(issamerm);
                this.setIsSameRMOverrideValue(isb);
                connectionProps.remove(ISSAMERM);
            }
        }
    }

    public Boolean getIsSameRMOverrideValue() {
        System.out.println("XAManagedConnectionFactory().getIsSameRMOverrideValue(Boolean isSameRMOverrideValue)"
                + isSameRMOverrideValue);
        return isSameRMOverrideValue;
    }

    public void setIsSameRMOverrideValue(Boolean isSameRMOverrideValue) {
        System.out.println("XAManagedConnectionFactory().setIsSameRMOverrideValue(Boolean isSameRMOverrideValue)"
                + isSameRMOverrideValue);
        this.isSameRMOverrideValue = Boolean.valueOf(isSameRMOverrideValue);
    }

    public ManagedConnection createManagedConnection(Subject subject,
            ConnectionRequestInfo cri) throws javax.resource.ResourceException {
        System.out.println("XAManagedConnectionFactory().createManagedConnection():"
                + isSameRMOverrideValue);
        Properties props = getConnectionProperties(subject, cri);
        try {
            final String user = props.getProperty("user");
            final String password = props.getProperty("password");
            XAConnection xaConnection = (user != null) ? getXADataSource().getXAConnection(user, password) : getXADataSource().getXAConnection();
            ManagedConnection mcon = newXAManagedConnection(props, xaConnection);
            System.out.println(mcon);
            return mcon;
        } catch (Exception e) {
            throw new ResourceException("Could not create connection", e);
        }
    }

    protected ManagedConnection newXAManagedConnection(Properties props,
            XAConnection xaConnection) throws SQLException {
        return new XAManagedConnection(this, xaConnection, props,
                transactionIsolation, preparedStatementCacheSize);
    }

    public ManagedConnection matchManagedConnections(Set mcs, Subject subject,
            ConnectionRequestInfo cri) throws ResourceException {
        Properties newProps = getConnectionProperties(subject, cri);
        for (Iterator i = mcs.iterator(); i.hasNext();) {
            Object o = i.next();
            if (o instanceof XAManagedConnection) {
                XAManagedConnection mc = (XAManagedConnection) o;
                if (mc.getProperties().equals(newProps)) {
                    // Next check to see if we are validating on
                    // matchManagedConnections
                    if ((getValidateOnMatch() && mc.checkValid())
                            || !getValidateOnMatch()) {
                        return mc;
                    }
                }
            }
        }
        return null;
    }

    public int hashCode() {
        int result = 17;
        result = result
                * 37
                + ((xaDataSourceClass == null) ? 0 : xaDataSourceClass.hashCode());
        result = result * 37 + connectionProps.hashCode();
        result = result * 37 + ((userName == null) ? 0 : userName.hashCode());
        result = result * 37 + ((password == null) ? 0 : password.hashCode());
        result = result * 37 + transactionIsolation;
        return result;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (getClass() != other.getClass()) {
            return false;
        }
        XAManagedConnectionFactory otherMcf = (XAManagedConnectionFactory) other;
        boolean result = this.xaDataSourceClass.equals(otherMcf.xaDataSourceClass)
                && this.connectionProps.equals(otherMcf.connectionProps)
                && ((this.userName == null) ? otherMcf.userName == null
                : this.userName.equals(otherMcf.userName))
                && ((this.password == null) ? otherMcf.password == null
                : this.password.equals(otherMcf.password))
                && this.transactionIsolation == otherMcf.transactionIsolation;
        System.out.println("XAManagedConnectionFactory().equals(Object other):: result:: "
                + result);
        return result;
    }

    protected synchronized XADataSource getXADataSource()
            throws ResourceException {
        if (xads == null) {
            if (xaDataSourceClass == null) {
                throw new ResourceException(
                        "No XADataSourceClass supplied!");
            }
            try {
                Class clazz = Thread.currentThread().getContextClassLoader().loadClass(xaDataSourceClass);
                xads = (XADataSource) clazz.newInstance();
                System.out.println("xaProps :: " + connectionProps);
                Class[] NOCLASSES = new Class[]{};
                for (Iterator i = connectionProps.keySet().iterator(); i.hasNext();) {
                    String name = (String) i.next();
                    String value = connectionProps.getProperty(name);
                    System.out.println("Name:-" + name + " Value:-" + value);
                    // This is a bad solution. On the other hand the only known
                    // example
                    // of a setter with no getter is for Oracle with password.
                    // Anyway, each xadatasource implementation should get its
                    // own subclass of this that explicitly sets the
                    // properties individually.
                    Class type = null;
                    try {
                        Method getter = clazz.getMethod("get" + name, NOCLASSES);
                        type = getter.getReturnType();
                    } catch (NoSuchMethodException e) {
                        type = String.class;
                    }

                    Method setter = null;
                    try {
                        setter = clazz.getMethod("set" + name, new Class[]{type});
                        System.out.println("setter.getName():" + setter.getName());
                        PropertyEditor editor = PropertyEditorManager.findEditor(type);
                        if (editor == null) {
                            throw new ResourceException(
                                    "No property editor found for type: " + type);
                        }
                        editor.setAsText(value);
                        setter.invoke(xads, new Object[]{editor.getValue()});
                    } catch (NoSuchMethodException ex) {
                        System.out.println("No setter for property:"+name);
                    }
                }
            } catch (ClassNotFoundException cnfe) {
                throw new ResourceException(
                        "Class not found for XADataSource " + xaDataSourceClass,
                        cnfe);
            } catch (InstantiationException ie) {
                throw new ResourceException(
                        "Could not create an XADataSource: ", ie);
            } catch (IllegalAccessException iae) {
                throw new ResourceException("Could not set a property: ",
                        iae);
            } catch (IllegalArgumentException iae) {
                throw new ResourceException("Could not set a property: ",
                        iae);
            } catch (InvocationTargetException ite) {
                throw new ResourceException(
                        "Could not invoke setter on XADataSource: ", ite);
            } /*catch (NoSuchMethodException nsme) {
            throw new ResourceException(
            "Could not find accessor on XADataSource: ", nsme);
            }*/
        }
        return xads;
    }

    protected Properties getXaProps() {
        return connectionProps;
    }

    private Properties getProperties(String s) {
        Properties props = new Properties();
        char[] chrs = s.trim().toCharArray();
        StringBuilder key = new StringBuilder();
        StringBuilder value = new StringBuilder();
        boolean flip = false;
        for (int i = 0; i < chrs.length; i++) {
            char c = chrs[i];
            if (!Arrays.equals(SPACEBYTE, Character.toString(c).getBytes())) {
                if (Arrays.equals(DELIMITERBYTE, Character.toString(c).getBytes())) {
                    flip = true;
                    continue;
                }
                if (!flip) {
                    key.append(c);
                } else {
                    value.append(c);
                }
            } else {
                if (value.length() == 0) {
                    continue;
                }
                if (!props.containsKey(key.toString())) {
                    props.put(key.toString(), value.toString());
                    key = new StringBuilder();
                    value = new StringBuilder();
                    flip = false;
                }
            }
        }
        if (!props.containsKey(key.toString())) {
            props.put(key.toString(), value.toString());
        }
        return props;
    }
}
