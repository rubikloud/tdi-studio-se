/**
 * WSDeleteItemWithReport.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.talend.mdm.webservice;

public class WSDeleteItemWithReport  implements java.io.Serializable {
    private java.lang.Boolean invokeBeforeSaving;

    private java.lang.String operateType;

    private java.lang.Boolean override;

    private java.lang.Boolean pushToUpdateReport;

    private java.lang.String source;

    private java.lang.String updatePath;

    private java.lang.String user;

    private org.talend.mdm.webservice.WSItemPK wsItemPK;

    public WSDeleteItemWithReport() {
    }

    public WSDeleteItemWithReport(
           java.lang.Boolean invokeBeforeSaving,
           java.lang.String operateType,
           java.lang.Boolean override,
           java.lang.Boolean pushToUpdateReport,
           java.lang.String source,
           java.lang.String updatePath,
           java.lang.String user,
           org.talend.mdm.webservice.WSItemPK wsItemPK) {
           this.invokeBeforeSaving = invokeBeforeSaving;
           this.operateType = operateType;
           this.override = override;
           this.pushToUpdateReport = pushToUpdateReport;
           this.source = source;
           this.updatePath = updatePath;
           this.user = user;
           this.wsItemPK = wsItemPK;
    }


    /**
     * Gets the invokeBeforeSaving value for this WSDeleteItemWithReport.
     * 
     * @return invokeBeforeSaving
     */
    public java.lang.Boolean getInvokeBeforeSaving() {
        return invokeBeforeSaving;
    }


    /**
     * Sets the invokeBeforeSaving value for this WSDeleteItemWithReport.
     * 
     * @param invokeBeforeSaving
     */
    public void setInvokeBeforeSaving(java.lang.Boolean invokeBeforeSaving) {
        this.invokeBeforeSaving = invokeBeforeSaving;
    }


    /**
     * Gets the operateType value for this WSDeleteItemWithReport.
     * 
     * @return operateType
     */
    public java.lang.String getOperateType() {
        return operateType;
    }


    /**
     * Sets the operateType value for this WSDeleteItemWithReport.
     * 
     * @param operateType
     */
    public void setOperateType(java.lang.String operateType) {
        this.operateType = operateType;
    }


    /**
     * Gets the override value for this WSDeleteItemWithReport.
     * 
     * @return override
     */
    public java.lang.Boolean getOverride() {
        return override;
    }


    /**
     * Sets the override value for this WSDeleteItemWithReport.
     * 
     * @param override
     */
    public void setOverride(java.lang.Boolean override) {
        this.override = override;
    }


    /**
     * Gets the pushToUpdateReport value for this WSDeleteItemWithReport.
     * 
     * @return pushToUpdateReport
     */
    public java.lang.Boolean getPushToUpdateReport() {
        return pushToUpdateReport;
    }


    /**
     * Sets the pushToUpdateReport value for this WSDeleteItemWithReport.
     * 
     * @param pushToUpdateReport
     */
    public void setPushToUpdateReport(java.lang.Boolean pushToUpdateReport) {
        this.pushToUpdateReport = pushToUpdateReport;
    }


    /**
     * Gets the source value for this WSDeleteItemWithReport.
     * 
     * @return source
     */
    public java.lang.String getSource() {
        return source;
    }


    /**
     * Sets the source value for this WSDeleteItemWithReport.
     * 
     * @param source
     */
    public void setSource(java.lang.String source) {
        this.source = source;
    }


    /**
     * Gets the updatePath value for this WSDeleteItemWithReport.
     * 
     * @return updatePath
     */
    public java.lang.String getUpdatePath() {
        return updatePath;
    }


    /**
     * Sets the updatePath value for this WSDeleteItemWithReport.
     * 
     * @param updatePath
     */
    public void setUpdatePath(java.lang.String updatePath) {
        this.updatePath = updatePath;
    }


    /**
     * Gets the user value for this WSDeleteItemWithReport.
     * 
     * @return user
     */
    public java.lang.String getUser() {
        return user;
    }


    /**
     * Sets the user value for this WSDeleteItemWithReport.
     * 
     * @param user
     */
    public void setUser(java.lang.String user) {
        this.user = user;
    }


    /**
     * Gets the wsItemPK value for this WSDeleteItemWithReport.
     * 
     * @return wsItemPK
     */
    public org.talend.mdm.webservice.WSItemPK getWsItemPK() {
        return wsItemPK;
    }


    /**
     * Sets the wsItemPK value for this WSDeleteItemWithReport.
     * 
     * @param wsItemPK
     */
    public void setWsItemPK(org.talend.mdm.webservice.WSItemPK wsItemPK) {
        this.wsItemPK = wsItemPK;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WSDeleteItemWithReport)) return false;
        WSDeleteItemWithReport other = (WSDeleteItemWithReport) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.invokeBeforeSaving==null && other.getInvokeBeforeSaving()==null) || 
             (this.invokeBeforeSaving!=null &&
              this.invokeBeforeSaving.equals(other.getInvokeBeforeSaving()))) &&
            ((this.operateType==null && other.getOperateType()==null) || 
             (this.operateType!=null &&
              this.operateType.equals(other.getOperateType()))) &&
            ((this.override==null && other.getOverride()==null) || 
             (this.override!=null &&
              this.override.equals(other.getOverride()))) &&
            ((this.pushToUpdateReport==null && other.getPushToUpdateReport()==null) || 
             (this.pushToUpdateReport!=null &&
              this.pushToUpdateReport.equals(other.getPushToUpdateReport()))) &&
            ((this.source==null && other.getSource()==null) || 
             (this.source!=null &&
              this.source.equals(other.getSource()))) &&
            ((this.updatePath==null && other.getUpdatePath()==null) || 
             (this.updatePath!=null &&
              this.updatePath.equals(other.getUpdatePath()))) &&
            ((this.user==null && other.getUser()==null) || 
             (this.user!=null &&
              this.user.equals(other.getUser()))) &&
            ((this.wsItemPK==null && other.getWsItemPK()==null) || 
             (this.wsItemPK!=null &&
              this.wsItemPK.equals(other.getWsItemPK())));
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
        if (getInvokeBeforeSaving() != null) {
            _hashCode += getInvokeBeforeSaving().hashCode();
        }
        if (getOperateType() != null) {
            _hashCode += getOperateType().hashCode();
        }
        if (getOverride() != null) {
            _hashCode += getOverride().hashCode();
        }
        if (getPushToUpdateReport() != null) {
            _hashCode += getPushToUpdateReport().hashCode();
        }
        if (getSource() != null) {
            _hashCode += getSource().hashCode();
        }
        if (getUpdatePath() != null) {
            _hashCode += getUpdatePath().hashCode();
        }
        if (getUser() != null) {
            _hashCode += getUser().hashCode();
        }
        if (getWsItemPK() != null) {
            _hashCode += getWsItemPK().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WSDeleteItemWithReport.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.talend.com/mdm", "WSDeleteItemWithReport"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("invokeBeforeSaving");
        elemField.setXmlName(new javax.xml.namespace.QName("", "invokeBeforeSaving"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("operateType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "operateType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("override");
        elemField.setXmlName(new javax.xml.namespace.QName("", "override"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pushToUpdateReport");
        elemField.setXmlName(new javax.xml.namespace.QName("", "pushToUpdateReport"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("source");
        elemField.setXmlName(new javax.xml.namespace.QName("", "source"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("updatePath");
        elemField.setXmlName(new javax.xml.namespace.QName("", "updatePath"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("user");
        elemField.setXmlName(new javax.xml.namespace.QName("", "user"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wsItemPK");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wsItemPK"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.talend.com/mdm", "WSItemPK"));
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
