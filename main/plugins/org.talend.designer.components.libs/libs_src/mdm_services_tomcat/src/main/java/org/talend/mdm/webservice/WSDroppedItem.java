/**
 * WSDroppedItem.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.talend.mdm.webservice;

public class WSDroppedItem  implements java.io.Serializable {
    private java.lang.String conceptName;

    private java.lang.String[] ids;

    private java.lang.Long insertionTime;

    private java.lang.String insertionUserName;

    private java.lang.String partPath;

    private java.lang.String projection;

    private java.lang.String uniqueId;

    private org.talend.mdm.webservice.WSDataClusterPK wsDataClusterPK;

    public WSDroppedItem() {
    }

    public WSDroppedItem(
           java.lang.String conceptName,
           java.lang.String[] ids,
           java.lang.Long insertionTime,
           java.lang.String insertionUserName,
           java.lang.String partPath,
           java.lang.String projection,
           java.lang.String uniqueId,
           org.talend.mdm.webservice.WSDataClusterPK wsDataClusterPK) {
           this.conceptName = conceptName;
           this.ids = ids;
           this.insertionTime = insertionTime;
           this.insertionUserName = insertionUserName;
           this.partPath = partPath;
           this.projection = projection;
           this.uniqueId = uniqueId;
           this.wsDataClusterPK = wsDataClusterPK;
    }


    /**
     * Gets the conceptName value for this WSDroppedItem.
     * 
     * @return conceptName
     */
    public java.lang.String getConceptName() {
        return conceptName;
    }


    /**
     * Sets the conceptName value for this WSDroppedItem.
     * 
     * @param conceptName
     */
    public void setConceptName(java.lang.String conceptName) {
        this.conceptName = conceptName;
    }


    /**
     * Gets the ids value for this WSDroppedItem.
     * 
     * @return ids
     */
    public java.lang.String[] getIds() {
        return ids;
    }


    /**
     * Sets the ids value for this WSDroppedItem.
     * 
     * @param ids
     */
    public void setIds(java.lang.String[] ids) {
        this.ids = ids;
    }

    public java.lang.String getIds(int i) {
        return this.ids[i];
    }

    public void setIds(int i, java.lang.String _value) {
        this.ids[i] = _value;
    }


    /**
     * Gets the insertionTime value for this WSDroppedItem.
     * 
     * @return insertionTime
     */
    public java.lang.Long getInsertionTime() {
        return insertionTime;
    }


    /**
     * Sets the insertionTime value for this WSDroppedItem.
     * 
     * @param insertionTime
     */
    public void setInsertionTime(java.lang.Long insertionTime) {
        this.insertionTime = insertionTime;
    }


    /**
     * Gets the insertionUserName value for this WSDroppedItem.
     * 
     * @return insertionUserName
     */
    public java.lang.String getInsertionUserName() {
        return insertionUserName;
    }


    /**
     * Sets the insertionUserName value for this WSDroppedItem.
     * 
     * @param insertionUserName
     */
    public void setInsertionUserName(java.lang.String insertionUserName) {
        this.insertionUserName = insertionUserName;
    }


    /**
     * Gets the partPath value for this WSDroppedItem.
     * 
     * @return partPath
     */
    public java.lang.String getPartPath() {
        return partPath;
    }


    /**
     * Sets the partPath value for this WSDroppedItem.
     * 
     * @param partPath
     */
    public void setPartPath(java.lang.String partPath) {
        this.partPath = partPath;
    }


    /**
     * Gets the projection value for this WSDroppedItem.
     * 
     * @return projection
     */
    public java.lang.String getProjection() {
        return projection;
    }


    /**
     * Sets the projection value for this WSDroppedItem.
     * 
     * @param projection
     */
    public void setProjection(java.lang.String projection) {
        this.projection = projection;
    }


    /**
     * Gets the uniqueId value for this WSDroppedItem.
     * 
     * @return uniqueId
     */
    public java.lang.String getUniqueId() {
        return uniqueId;
    }


    /**
     * Sets the uniqueId value for this WSDroppedItem.
     * 
     * @param uniqueId
     */
    public void setUniqueId(java.lang.String uniqueId) {
        this.uniqueId = uniqueId;
    }


    /**
     * Gets the wsDataClusterPK value for this WSDroppedItem.
     * 
     * @return wsDataClusterPK
     */
    public org.talend.mdm.webservice.WSDataClusterPK getWsDataClusterPK() {
        return wsDataClusterPK;
    }


    /**
     * Sets the wsDataClusterPK value for this WSDroppedItem.
     * 
     * @param wsDataClusterPK
     */
    public void setWsDataClusterPK(org.talend.mdm.webservice.WSDataClusterPK wsDataClusterPK) {
        this.wsDataClusterPK = wsDataClusterPK;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WSDroppedItem)) return false;
        WSDroppedItem other = (WSDroppedItem) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.conceptName==null && other.getConceptName()==null) || 
             (this.conceptName!=null &&
              this.conceptName.equals(other.getConceptName()))) &&
            ((this.ids==null && other.getIds()==null) || 
             (this.ids!=null &&
              java.util.Arrays.equals(this.ids, other.getIds()))) &&
            ((this.insertionTime==null && other.getInsertionTime()==null) || 
             (this.insertionTime!=null &&
              this.insertionTime.equals(other.getInsertionTime()))) &&
            ((this.insertionUserName==null && other.getInsertionUserName()==null) || 
             (this.insertionUserName!=null &&
              this.insertionUserName.equals(other.getInsertionUserName()))) &&
            ((this.partPath==null && other.getPartPath()==null) || 
             (this.partPath!=null &&
              this.partPath.equals(other.getPartPath()))) &&
            ((this.projection==null && other.getProjection()==null) || 
             (this.projection!=null &&
              this.projection.equals(other.getProjection()))) &&
            ((this.uniqueId==null && other.getUniqueId()==null) || 
             (this.uniqueId!=null &&
              this.uniqueId.equals(other.getUniqueId()))) &&
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
        if (getConceptName() != null) {
            _hashCode += getConceptName().hashCode();
        }
        if (getIds() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getIds());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getIds(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getInsertionTime() != null) {
            _hashCode += getInsertionTime().hashCode();
        }
        if (getInsertionUserName() != null) {
            _hashCode += getInsertionUserName().hashCode();
        }
        if (getPartPath() != null) {
            _hashCode += getPartPath().hashCode();
        }
        if (getProjection() != null) {
            _hashCode += getProjection().hashCode();
        }
        if (getUniqueId() != null) {
            _hashCode += getUniqueId().hashCode();
        }
        if (getWsDataClusterPK() != null) {
            _hashCode += getWsDataClusterPK().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WSDroppedItem.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.talend.com/mdm", "WSDroppedItem"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("conceptName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "conceptName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ids");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ids"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("insertionTime");
        elemField.setXmlName(new javax.xml.namespace.QName("", "insertionTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("insertionUserName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "insertionUserName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("partPath");
        elemField.setXmlName(new javax.xml.namespace.QName("", "partPath"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("projection");
        elemField.setXmlName(new javax.xml.namespace.QName("", "projection"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("uniqueId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "uniqueId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
