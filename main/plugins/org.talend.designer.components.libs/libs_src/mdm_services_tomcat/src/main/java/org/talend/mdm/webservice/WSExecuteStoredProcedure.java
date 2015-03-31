/**
 * WSExecuteStoredProcedure.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.talend.mdm.webservice;

public class WSExecuteStoredProcedure  implements java.io.Serializable {
    private java.lang.String[] parameters;

    private org.talend.mdm.webservice.WSDataClusterPK wsDataClusterPK;

    private org.talend.mdm.webservice.WSStoredProcedurePK wsStoredProcedurePK;

    public WSExecuteStoredProcedure() {
    }

    public WSExecuteStoredProcedure(
           java.lang.String[] parameters,
           org.talend.mdm.webservice.WSDataClusterPK wsDataClusterPK,
           org.talend.mdm.webservice.WSStoredProcedurePK wsStoredProcedurePK) {
           this.parameters = parameters;
           this.wsDataClusterPK = wsDataClusterPK;
           this.wsStoredProcedurePK = wsStoredProcedurePK;
    }


    /**
     * Gets the parameters value for this WSExecuteStoredProcedure.
     * 
     * @return parameters
     */
    public java.lang.String[] getParameters() {
        return parameters;
    }


    /**
     * Sets the parameters value for this WSExecuteStoredProcedure.
     * 
     * @param parameters
     */
    public void setParameters(java.lang.String[] parameters) {
        this.parameters = parameters;
    }

    public java.lang.String getParameters(int i) {
        return this.parameters[i];
    }

    public void setParameters(int i, java.lang.String _value) {
        this.parameters[i] = _value;
    }


    /**
     * Gets the wsDataClusterPK value for this WSExecuteStoredProcedure.
     * 
     * @return wsDataClusterPK
     */
    public org.talend.mdm.webservice.WSDataClusterPK getWsDataClusterPK() {
        return wsDataClusterPK;
    }


    /**
     * Sets the wsDataClusterPK value for this WSExecuteStoredProcedure.
     * 
     * @param wsDataClusterPK
     */
    public void setWsDataClusterPK(org.talend.mdm.webservice.WSDataClusterPK wsDataClusterPK) {
        this.wsDataClusterPK = wsDataClusterPK;
    }


    /**
     * Gets the wsStoredProcedurePK value for this WSExecuteStoredProcedure.
     * 
     * @return wsStoredProcedurePK
     */
    public org.talend.mdm.webservice.WSStoredProcedurePK getWsStoredProcedurePK() {
        return wsStoredProcedurePK;
    }


    /**
     * Sets the wsStoredProcedurePK value for this WSExecuteStoredProcedure.
     * 
     * @param wsStoredProcedurePK
     */
    public void setWsStoredProcedurePK(org.talend.mdm.webservice.WSStoredProcedurePK wsStoredProcedurePK) {
        this.wsStoredProcedurePK = wsStoredProcedurePK;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WSExecuteStoredProcedure)) return false;
        WSExecuteStoredProcedure other = (WSExecuteStoredProcedure) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.parameters==null && other.getParameters()==null) || 
             (this.parameters!=null &&
              java.util.Arrays.equals(this.parameters, other.getParameters()))) &&
            ((this.wsDataClusterPK==null && other.getWsDataClusterPK()==null) || 
             (this.wsDataClusterPK!=null &&
              this.wsDataClusterPK.equals(other.getWsDataClusterPK()))) &&
            ((this.wsStoredProcedurePK==null && other.getWsStoredProcedurePK()==null) || 
             (this.wsStoredProcedurePK!=null &&
              this.wsStoredProcedurePK.equals(other.getWsStoredProcedurePK())));
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
        if (getParameters() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getParameters());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getParameters(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getWsDataClusterPK() != null) {
            _hashCode += getWsDataClusterPK().hashCode();
        }
        if (getWsStoredProcedurePK() != null) {
            _hashCode += getWsStoredProcedurePK().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WSExecuteStoredProcedure.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.talend.com/mdm", "WSExecuteStoredProcedure"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("parameters");
        elemField.setXmlName(new javax.xml.namespace.QName("", "parameters"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wsDataClusterPK");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wsDataClusterPK"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.talend.com/mdm", "WSDataClusterPK"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wsStoredProcedurePK");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wsStoredProcedurePK"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.talend.com/mdm", "WSStoredProcedurePK"));
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
