/**
 * TMDMService_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.talend.mdm.webservice;

public class TMDMService_ServiceLocator extends org.apache.axis.client.Service implements org.talend.mdm.webservice.TMDMService_Service {

    public TMDMService_ServiceLocator() {
    }


    public TMDMService_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public TMDMService_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for TMDMPort
    private java.lang.String TMDMPort_address = "http://localhost:8080/talendmdm/services/soap";

    public java.lang.String getTMDMPortAddress() {
        return TMDMPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String TMDMPortWSDDServiceName = "TMDMPort";

    public java.lang.String getTMDMPortWSDDServiceName() {
        return TMDMPortWSDDServiceName;
    }

    public void setTMDMPortWSDDServiceName(java.lang.String name) {
        TMDMPortWSDDServiceName = name;
    }

    public org.talend.mdm.webservice.TMDMService_PortType getTMDMPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(TMDMPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getTMDMPort(endpoint);
    }

    public org.talend.mdm.webservice.TMDMService_PortType getTMDMPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.talend.mdm.webservice.TMDMServiceSoapBindingStub _stub = new org.talend.mdm.webservice.TMDMServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getTMDMPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setTMDMPortEndpointAddress(java.lang.String address) {
        TMDMPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.talend.mdm.webservice.TMDMService_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                org.talend.mdm.webservice.TMDMServiceSoapBindingStub _stub = new org.talend.mdm.webservice.TMDMServiceSoapBindingStub(new java.net.URL(TMDMPort_address), this);
                _stub.setPortName(getTMDMPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("TMDMPort".equals(inputPortName)) {
            return getTMDMPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://www.talend.com/mdm", "TMDMService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://www.talend.com/mdm", "TMDMPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("TMDMPort".equals(portName)) {
            setTMDMPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
