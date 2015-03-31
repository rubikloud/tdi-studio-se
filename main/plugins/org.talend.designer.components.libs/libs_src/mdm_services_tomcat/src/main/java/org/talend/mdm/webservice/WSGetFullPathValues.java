/**
 * WSGetFullPathValues.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.talend.mdm.webservice;

public class WSGetFullPathValues  implements java.io.Serializable {
    private java.lang.String direction;

    private java.lang.String fullPath;

    private java.lang.String orderBy;

    private int spellThreshold;

    private org.talend.mdm.webservice.WSWhereItem whereItem;

    private org.talend.mdm.webservice.WSDataClusterPK wsDataClusterPK;

    public WSGetFullPathValues() {
    }

    public WSGetFullPathValues(
           java.lang.String direction,
           java.lang.String fullPath,
           java.lang.String orderBy,
           int spellThreshold,
           org.talend.mdm.webservice.WSWhereItem whereItem,
           org.talend.mdm.webservice.WSDataClusterPK wsDataClusterPK) {
           this.direction = direction;
           this.fullPath = fullPath;
           this.orderBy = orderBy;
           this.spellThreshold = spellThreshold;
           this.whereItem = whereItem;
           this.wsDataClusterPK = wsDataClusterPK;
    }


    /**
     * Gets the direction value for this WSGetFullPathValues.
     * 
     * @return direction
     */
    public java.lang.String getDirection() {
        return direction;
    }


    /**
     * Sets the direction value for this WSGetFullPathValues.
     * 
     * @param direction
     */
    public void setDirection(java.lang.String direction) {
        this.direction = direction;
    }


    /**
     * Gets the fullPath value for this WSGetFullPathValues.
     * 
     * @return fullPath
     */
    public java.lang.String getFullPath() {
        return fullPath;
    }


    /**
     * Sets the fullPath value for this WSGetFullPathValues.
     * 
     * @param fullPath
     */
    public void setFullPath(java.lang.String fullPath) {
        this.fullPath = fullPath;
    }


    /**
     * Gets the orderBy value for this WSGetFullPathValues.
     * 
     * @return orderBy
     */
    public java.lang.String getOrderBy() {
        return orderBy;
    }


    /**
     * Sets the orderBy value for this WSGetFullPathValues.
     * 
     * @param orderBy
     */
    public void setOrderBy(java.lang.String orderBy) {
        this.orderBy = orderBy;
    }


    /**
     * Gets the spellThreshold value for this WSGetFullPathValues.
     * 
     * @return spellThreshold
     */
    public int getSpellThreshold() {
        return spellThreshold;
    }


    /**
     * Sets the spellThreshold value for this WSGetFullPathValues.
     * 
     * @param spellThreshold
     */
    public void setSpellThreshold(int spellThreshold) {
        this.spellThreshold = spellThreshold;
    }


    /**
     * Gets the whereItem value for this WSGetFullPathValues.
     * 
     * @return whereItem
     */
    public org.talend.mdm.webservice.WSWhereItem getWhereItem() {
        return whereItem;
    }


    /**
     * Sets the whereItem value for this WSGetFullPathValues.
     * 
     * @param whereItem
     */
    public void setWhereItem(org.talend.mdm.webservice.WSWhereItem whereItem) {
        this.whereItem = whereItem;
    }


    /**
     * Gets the wsDataClusterPK value for this WSGetFullPathValues.
     * 
     * @return wsDataClusterPK
     */
    public org.talend.mdm.webservice.WSDataClusterPK getWsDataClusterPK() {
        return wsDataClusterPK;
    }


    /**
     * Sets the wsDataClusterPK value for this WSGetFullPathValues.
     * 
     * @param wsDataClusterPK
     */
    public void setWsDataClusterPK(org.talend.mdm.webservice.WSDataClusterPK wsDataClusterPK) {
        this.wsDataClusterPK = wsDataClusterPK;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WSGetFullPathValues)) return false;
        WSGetFullPathValues other = (WSGetFullPathValues) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.direction==null && other.getDirection()==null) || 
             (this.direction!=null &&
              this.direction.equals(other.getDirection()))) &&
            ((this.fullPath==null && other.getFullPath()==null) || 
             (this.fullPath!=null &&
              this.fullPath.equals(other.getFullPath()))) &&
            ((this.orderBy==null && other.getOrderBy()==null) || 
             (this.orderBy!=null &&
              this.orderBy.equals(other.getOrderBy()))) &&
            this.spellThreshold == other.getSpellThreshold() &&
            ((this.whereItem==null && other.getWhereItem()==null) || 
             (this.whereItem!=null &&
              this.whereItem.equals(other.getWhereItem()))) &&
            ((this.wsDataClusterPK==null && other.getWsDataClusterPK()==null) || 
             (this.wsDataClusterPK!=null &&
              this.wsDataClusterPK.equals(other.getWsDataClusterPK())));
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
        if (getDirection() != null) {
            _hashCode += getDirection().hashCode();
        }
        if (getFullPath() != null) {
            _hashCode += getFullPath().hashCode();
        }
        if (getOrderBy() != null) {
            _hashCode += getOrderBy().hashCode();
        }
        _hashCode += getSpellThreshold();
        if (getWhereItem() != null) {
            _hashCode += getWhereItem().hashCode();
        }
        if (getWsDataClusterPK() != null) {
            _hashCode += getWsDataClusterPK().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WSGetFullPathValues.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.talend.com/mdm", "WSGetFullPathValues"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("direction");
        elemField.setXmlName(new javax.xml.namespace.QName("", "direction"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fullPath");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fullPath"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("orderBy");
        elemField.setXmlName(new javax.xml.namespace.QName("", "orderBy"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("spellThreshold");
        elemField.setXmlName(new javax.xml.namespace.QName("", "spellThreshold"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("whereItem");
        elemField.setXmlName(new javax.xml.namespace.QName("", "whereItem"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.talend.com/mdm", "WSWhereItem"));
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
