/**
 * WSPutItemByOperatorType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.talend.mdm.webservice;

public class WSPutItemByOperatorType  implements java.io.Serializable {
    private org.talend.mdm.webservice.WSOperatorType operatortype;

    private org.talend.mdm.webservice.WSDataClusterPK wsDataClusterPK;

    private org.talend.mdm.webservice.WSDataModelPK wsDataModelPK;

    private java.lang.String xmlString;

    public WSPutItemByOperatorType() {
    }

    public WSPutItemByOperatorType(
           org.talend.mdm.webservice.WSOperatorType operatortype,
           org.talend.mdm.webservice.WSDataClusterPK wsDataClusterPK,
           org.talend.mdm.webservice.WSDataModelPK wsDataModelPK,
           java.lang.String xmlString) {
           this.operatortype = operatortype;
           this.wsDataClusterPK = wsDataClusterPK;
           this.wsDataModelPK = wsDataModelPK;
           this.xmlString = xmlString;
    }


    /**
     * Gets the operatortype value for this WSPutItemByOperatorType.
     * 
     * @return operatortype
     */
    public org.talend.mdm.webservice.WSOperatorType getOperatortype() {
        return operatortype;
    }


    /**
     * Sets the operatortype value for this WSPutItemByOperatorType.
     * 
     * @param operatortype
     */
    public void setOperatortype(org.talend.mdm.webservice.WSOperatorType operatortype) {
        this.operatortype = operatortype;
    }


    /**
     * Gets the wsDataClusterPK value for this WSPutItemByOperatorType.
     * 
     * @return wsDataClusterPK
     */
    public org.talend.mdm.webservice.WSDataClusterPK getWsDataClusterPK() {
        return wsDataClusterPK;
    }


    /**
     * Sets the wsDataClusterPK value for this WSPutItemByOperatorType.
     * 
     * @param wsDataClusterPK
     */
    public void setWsDataClusterPK(org.talend.mdm.webservice.WSDataClusterPK wsDataClusterPK) {
        this.wsDataClusterPK = wsDataClusterPK;
    }


    /**
     * Gets the wsDataModelPK value for this WSPutItemByOperatorType.
     * 
     * @return wsDataModelPK
     */
    public org.talend.mdm.webservice.WSDataModelPK getWsDataModelPK() {
        return wsDataModelPK;
    }


    /**
     * Sets the wsDataModelPK value for this WSPutItemByOperatorType.
     * 
     * @param wsDataModelPK
     */
    public void setWsDataModelPK(org.talend.mdm.webservice.WSDataModelPK wsDataModelPK) {
        this.wsDataModelPK = wsDataModelPK;
    }


    /**
     * Gets the xmlString value for this WSPutItemByOperatorType.
     * 
     * @return xmlString
     */
    public java.lang.String getXmlString() {
        return xmlString;
    }


    /**
     * Sets the xmlString value for this WSPutItemByOperatorType.
     * 
     * @param xmlString
     */
    public void setXmlString(java.lang.String xmlString) {
        this.xmlString = xmlString;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WSPutItemByOperatorType)) return false;
        WSPutItemByOperatorType other = (WSPutItemByOperatorType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.operatortype==null && other.getOperatortype()==null) || 
             (this.operatortype!=null &&
              this.operatortype.equals(other.getOperatortype()))) &&
            ((this.wsDataClusterPK==null && other.getWsDataClusterPK()==null) || 
             (this.wsDataClusterPK!=null &&
              this.wsDataClusterPK.equals(other.getWsDataClusterPK()))) &&
            ((this.wsDataModelPK==null && other.getWsDataModelPK()==null) || 
             (this.wsDataModelPK!=null &&
              this.wsDataModelPK.equals(other.getWsDataModelPK()))) &&
            ((this.xmlString==null && other.getXmlString()==null) || 
             (this.xmlString!=null &&
              this.xmlString.equals(other.getXmlString())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getOperatortype() != null) {
            _hashCode += getOperatortype().hashCode();
        }
        if (getWsDataClusterPK() != null) {
            _hashCode += getWsDataClusterPK().hashCode();
        }
        if (getWsDataModelPK() != null) {
            _hashCode += getWsDataModelPK().hashCode();
        }
        if (getXmlString() != null) {
            _hashCode += getXmlString().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WSPutItemByOperatorType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.talend.com/mdm", "WSPutItemByOperatorType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("operatortype");
        elemField.setXmlName(new javax.xml.namespace.QName("", "operatortype"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.talend.com/mdm", "WSOperatorType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wsDataClusterPK");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wsDataClusterPK"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.talend.com/mdm", "WSDataClusterPK"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wsDataModelPK");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wsDataModelPK"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.talend.com/mdm", "WSDataModelPK"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("xmlString");
        elemField.setXmlName(new javax.xml.namespace.QName("", "xmlString"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
