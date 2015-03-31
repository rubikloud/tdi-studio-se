/**
 * WSGetItemPKsByCriteria.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.talend.mdm.webservice;

public class WSGetItemPKsByCriteria  implements java.io.Serializable {
    private java.lang.String conceptName;

    private java.lang.String contentKeywords;

    private java.lang.Long fromDate;

    private java.lang.String keys;

    private java.lang.String keysKeywords;

    private int maxItems;

    private int skip;

    private java.lang.Long toDate;

    private org.talend.mdm.webservice.WSDataClusterPK wsDataClusterPK;

    public WSGetItemPKsByCriteria() {
    }

    public WSGetItemPKsByCriteria(
           java.lang.String conceptName,
           java.lang.String contentKeywords,
           java.lang.Long fromDate,
           java.lang.String keys,
           java.lang.String keysKeywords,
           int maxItems,
           int skip,
           java.lang.Long toDate,
           org.talend.mdm.webservice.WSDataClusterPK wsDataClusterPK) {
           this.conceptName = conceptName;
           this.contentKeywords = contentKeywords;
           this.fromDate = fromDate;
           this.keys = keys;
           this.keysKeywords = keysKeywords;
           this.maxItems = maxItems;
           this.skip = skip;
           this.toDate = toDate;
           this.wsDataClusterPK = wsDataClusterPK;
    }


    /**
     * Gets the conceptName value for this WSGetItemPKsByCriteria.
     * 
     * @return conceptName
     */
    public java.lang.String getConceptName() {
        return conceptName;
    }


    /**
     * Sets the conceptName value for this WSGetItemPKsByCriteria.
     * 
     * @param conceptName
     */
    public void setConceptName(java.lang.String conceptName) {
        this.conceptName = conceptName;
    }


    /**
     * Gets the contentKeywords value for this WSGetItemPKsByCriteria.
     * 
     * @return contentKeywords
     */
    public java.lang.String getContentKeywords() {
        return contentKeywords;
    }


    /**
     * Sets the contentKeywords value for this WSGetItemPKsByCriteria.
     * 
     * @param contentKeywords
     */
    public void setContentKeywords(java.lang.String contentKeywords) {
        this.contentKeywords = contentKeywords;
    }


    /**
     * Gets the fromDate value for this WSGetItemPKsByCriteria.
     * 
     * @return fromDate
     */
    public java.lang.Long getFromDate() {
        return fromDate;
    }


    /**
     * Sets the fromDate value for this WSGetItemPKsByCriteria.
     * 
     * @param fromDate
     */
    public void setFromDate(java.lang.Long fromDate) {
        this.fromDate = fromDate;
    }


    /**
     * Gets the keys value for this WSGetItemPKsByCriteria.
     * 
     * @return keys
     */
    public java.lang.String getKeys() {
        return keys;
    }


    /**
     * Sets the keys value for this WSGetItemPKsByCriteria.
     * 
     * @param keys
     */
    public void setKeys(java.lang.String keys) {
        this.keys = keys;
    }


    /**
     * Gets the keysKeywords value for this WSGetItemPKsByCriteria.
     * 
     * @return keysKeywords
     */
    public java.lang.String getKeysKeywords() {
        return keysKeywords;
    }


    /**
     * Sets the keysKeywords value for this WSGetItemPKsByCriteria.
     * 
     * @param keysKeywords
     */
    public void setKeysKeywords(java.lang.String keysKeywords) {
        this.keysKeywords = keysKeywords;
    }


    /**
     * Gets the maxItems value for this WSGetItemPKsByCriteria.
     * 
     * @return maxItems
     */
    public int getMaxItems() {
        return maxItems;
    }


    /**
     * Sets the maxItems value for this WSGetItemPKsByCriteria.
     * 
     * @param maxItems
     */
    public void setMaxItems(int maxItems) {
        this.maxItems = maxItems;
    }


    /**
     * Gets the skip value for this WSGetItemPKsByCriteria.
     * 
     * @return skip
     */
    public int getSkip() {
        return skip;
    }


    /**
     * Sets the skip value for this WSGetItemPKsByCriteria.
     * 
     * @param skip
     */
    public void setSkip(int skip) {
        this.skip = skip;
    }


    /**
     * Gets the toDate value for this WSGetItemPKsByCriteria.
     * 
     * @return toDate
     */
    public java.lang.Long getToDate() {
        return toDate;
    }


    /**
     * Sets the toDate value for this WSGetItemPKsByCriteria.
     * 
     * @param toDate
     */
    public void setToDate(java.lang.Long toDate) {
        this.toDate = toDate;
    }


    /**
     * Gets the wsDataClusterPK value for this WSGetItemPKsByCriteria.
     * 
     * @return wsDataClusterPK
     */
    public org.talend.mdm.webservice.WSDataClusterPK getWsDataClusterPK() {
        return wsDataClusterPK;
    }


    /**
     * Sets the wsDataClusterPK value for this WSGetItemPKsByCriteria.
     * 
     * @param wsDataClusterPK
     */
    public void setWsDataClusterPK(org.talend.mdm.webservice.WSDataClusterPK wsDataClusterPK) {
        this.wsDataClusterPK = wsDataClusterPK;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WSGetItemPKsByCriteria)) return false;
        WSGetItemPKsByCriteria other = (WSGetItemPKsByCriteria) obj;
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
            ((this.contentKeywords==null && other.getContentKeywords()==null) || 
             (this.contentKeywords!=null &&
              this.contentKeywords.equals(other.getContentKeywords()))) &&
            ((this.fromDate==null && other.getFromDate()==null) || 
             (this.fromDate!=null &&
              this.fromDate.equals(other.getFromDate()))) &&
            ((this.keys==null && other.getKeys()==null) || 
             (this.keys!=null &&
              this.keys.equals(other.getKeys()))) &&
            ((this.keysKeywords==null && other.getKeysKeywords()==null) || 
             (this.keysKeywords!=null &&
              this.keysKeywords.equals(other.getKeysKeywords()))) &&
            this.maxItems == other.getMaxItems() &&
            this.skip == other.getSkip() &&
            ((this.toDate==null && other.getToDate()==null) || 
             (this.toDate!=null &&
              this.toDate.equals(other.getToDate()))) &&
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
        if (getContentKeywords() != null) {
            _hashCode += getContentKeywords().hashCode();
        }
        if (getFromDate() != null) {
            _hashCode += getFromDate().hashCode();
        }
        if (getKeys() != null) {
            _hashCode += getKeys().hashCode();
        }
        if (getKeysKeywords() != null) {
            _hashCode += getKeysKeywords().hashCode();
        }
        _hashCode += getMaxItems();
        _hashCode += getSkip();
        if (getToDate() != null) {
            _hashCode += getToDate().hashCode();
        }
        if (getWsDataClusterPK() != null) {
            _hashCode += getWsDataClusterPK().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WSGetItemPKsByCriteria.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.talend.com/mdm", "WSGetItemPKsByCriteria"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("conceptName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "conceptName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contentKeywords");
        elemField.setXmlName(new javax.xml.namespace.QName("", "contentKeywords"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fromDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fromDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("keys");
        elemField.setXmlName(new javax.xml.namespace.QName("", "keys"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("keysKeywords");
        elemField.setXmlName(new javax.xml.namespace.QName("", "keysKeywords"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("maxItems");
        elemField.setXmlName(new javax.xml.namespace.QName("", "maxItems"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("skip");
        elemField.setXmlName(new javax.xml.namespace.QName("", "skip"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("toDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "toDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
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
