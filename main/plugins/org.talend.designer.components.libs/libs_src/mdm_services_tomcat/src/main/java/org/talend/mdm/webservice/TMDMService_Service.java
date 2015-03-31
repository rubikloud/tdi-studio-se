/**
 * TMDMService_Service.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.talend.mdm.webservice;

public interface TMDMService_Service extends javax.xml.rpc.Service {
    public java.lang.String getTMDMPortAddress();

    public org.talend.mdm.webservice.TMDMService_PortType getTMDMPort() throws javax.xml.rpc.ServiceException;

    public org.talend.mdm.webservice.TMDMService_PortType getTMDMPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
