/**
 * WSPutBusinessConcept.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.talend.mdm.webservice;

public class WSPutBusinessConcept  implements java.io.Serializable {
    private org.talend.mdm.webservice.WSBusinessConcept businessConcept;

    private org.talend.mdm.webservice.WSDataModelPK wsDataModelPK;

    public WSPutBusinessConcept() {
    }

    public WSPutBusinessConcept(
           org.talend.mdm.webservice.WSBusinessConcept businessConcept,
           org.talend.mdm.webservice.WSDataModelPK wsDataModelPK) {
           this.businessConcept = businessConcept;
           this.wsDataModelPK = wsDataModelPK;
    }


    /**
     * Gets the businessConcept value for this WSPutBusinessConcept.
     * 
     * @return businessConcept
     */
    public org.talend.mdm.webservice.WSBusinessConcept getBusinessConcept() {
        return businessConcept;
    }


    /**
     * Sets the businessConcept value for this WSPutBusinessConcept.
     * 
     * @param businessConcept
     */
    public void setBusinessConcept(org.talend.mdm.webservice.WSBusinessConcept businessConcept) {
        this.businessConcept = businessConcept;
    }


    /**
     * Gets the wsDataModelPK value for this WSPutBusinessConcept.
     * 
     * @return wsDataModelPK
     */
    public org.talend.mdm.webservice.WSDataModelPK getWsDataModelPK() {
        return wsDataModelPK;
    }


    /**
     * Sets the wsDataModelPK value for this WSPutBusinessConcept.
     * 
     * @param wsDataModelPK
     */
    public void setWsDataModelPK(org.talend.mdm.webservice.WSDataModelPK wsDataModelPK) {
        this.wsDataModelPK = wsDataModelPK;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WSPutBusinessConcept)) return false;
        WSPutBusinessConcept other = (WSPutBusinessConcept) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.businessConcept==null && other.getBusinessConcept()==null) || 
             (this.businessConcept!=null &&
              this.businessConcept.equals(other.getBusinessConcept()))) &&
            ((this.wsDataModelPK==null && other.getWsDataModelPK()==null) || 
             (this.wsDataModelPK!=null &&
              this.wsDataModelPK.equals(other.getWsDataModelPK())));
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
        if (getBusinessConcept() != null) {
            _hashCode += getBusinessConcept().hashCode();
        }
        if (getWsDataModelPK() != null) {
            _hashCode += getWsDataModelPK().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WSPutBusinessConcept.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.talend.com/mdm", "WSPutBusinessConcept"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("businessConcept");
        elemField.setXmlName(new javax.xml.namespace.QName("", "businessConcept"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.talend.com/mdm", "WSBusinessConcept"));
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
