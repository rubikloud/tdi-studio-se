/**
 * WSDigest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.talend.mdm.webservice;

public class WSDigest  implements java.io.Serializable {
    private java.lang.String digestValue;

    private long timeStamp;

    private org.talend.mdm.webservice.WSDigestKey wsDigestKey;

    public WSDigest() {
    }

    public WSDigest(
           java.lang.String digestValue,
           long timeStamp,
           org.talend.mdm.webservice.WSDigestKey wsDigestKey) {
           this.digestValue = digestValue;
           this.timeStamp = timeStamp;
           this.wsDigestKey = wsDigestKey;
    }


    /**
     * Gets the digestValue value for this WSDigest.
     * 
     * @return digestValue
     */
    public java.lang.String getDigestValue() {
        return digestValue;
    }


    /**
     * Sets the digestValue value for this WSDigest.
     * 
     * @param digestValue
     */
    public void setDigestValue(java.lang.String digestValue) {
        this.digestValue = digestValue;
    }


    /**
     * Gets the timeStamp value for this WSDigest.
     * 
     * @return timeStamp
     */
    public long getTimeStamp() {
        return timeStamp;
    }


    /**
     * Sets the timeStamp value for this WSDigest.
     * 
     * @param timeStamp
     */
    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }


    /**
     * Gets the wsDigestKey value for this WSDigest.
     * 
     * @return wsDigestKey
     */
    public org.talend.mdm.webservice.WSDigestKey getWsDigestKey() {
        return wsDigestKey;
    }


    /**
     * Sets the wsDigestKey value for this WSDigest.
     * 
     * @param wsDigestKey
     */
    public void setWsDigestKey(org.talend.mdm.webservice.WSDigestKey wsDigestKey) {
        this.wsDigestKey = wsDigestKey;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WSDigest)) return false;
        WSDigest other = (WSDigest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.digestValue==null && other.getDigestValue()==null) || 
             (this.digestValue!=null &&
              this.digestValue.equals(other.getDigestValue()))) &&
            this.timeStamp == other.getTimeStamp() &&
            ((this.wsDigestKey==null && other.getWsDigestKey()==null) || 
             (this.wsDigestKey!=null &&
              this.wsDigestKey.equals(other.getWsDigestKey())));
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
        if (getDigestValue() != null) {
            _hashCode += getDigestValue().hashCode();
        }
        _hashCode += new Long(getTimeStamp()).hashCode();
        if (getWsDigestKey() != null) {
            _hashCode += getWsDigestKey().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WSDigest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.talend.com/mdm", "WSDigest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("digestValue");
        elemField.setXmlName(new javax.xml.namespace.QName("", "digestValue"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("timeStamp");
        elemField.setXmlName(new javax.xml.namespace.QName("", "timeStamp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wsDigestKey");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wsDigestKey"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.talend.com/mdm", "WSDigestKey"));
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
