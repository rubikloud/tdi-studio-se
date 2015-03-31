/**
 * WSGetItems.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.talend.mdm.webservice;

public class WSGetItems  implements java.io.Serializable {
    private java.lang.String conceptName;

    private int maxItems;

    private int skip;

    private int spellTreshold;

    private java.lang.Boolean totalCountOnFirstResult;

    private org.talend.mdm.webservice.WSWhereItem whereItem;

    private org.talend.mdm.webservice.WSDataClusterPK wsDataClusterPK;

    public WSGetItems() {
    }

    public WSGetItems(
           java.lang.String conceptName,
           int maxItems,
           int skip,
           int spellTreshold,
           java.lang.Boolean totalCountOnFirstResult,
           org.talend.mdm.webservice.WSWhereItem whereItem,
           org.talend.mdm.webservice.WSDataClusterPK wsDataClusterPK) {
           this.conceptName = conceptName;
           this.maxItems = maxItems;
           this.skip = skip;
           this.spellTreshold = spellTreshold;
           this.totalCountOnFirstResult = totalCountOnFirstResult;
           this.whereItem = whereItem;
           this.wsDataClusterPK = wsDataClusterPK;
    }


    /**
     * Gets the conceptName value for this WSGetItems.
     * 
     * @return conceptName
     */
    public java.lang.String getConceptName() {
        return conceptName;
    }


    /**
     * Sets the conceptName value for this WSGetItems.
     * 
     * @param conceptName
     */
    public void setConceptName(java.lang.String conceptName) {
        this.conceptName = conceptName;
    }


    /**
     * Gets the maxItems value for this WSGetItems.
     * 
     * @return maxItems
     */
    public int getMaxItems() {
        return maxItems;
    }


    /**
     * Sets the maxItems value for this WSGetItems.
     * 
     * @param maxItems
     */
    public void setMaxItems(int maxItems) {
        this.maxItems = maxItems;
    }


    /**
     * Gets the skip value for this WSGetItems.
     * 
     * @return skip
     */
    public int getSkip() {
        return skip;
    }


    /**
     * Sets the skip value for this WSGetItems.
     * 
     * @param skip
     */
    public void setSkip(int skip) {
        this.skip = skip;
    }


    /**
     * Gets the spellTreshold value for this WSGetItems.
     * 
     * @return spellTreshold
     */
    public int getSpellTreshold() {
        return spellTreshold;
    }


    /**
     * Sets the spellTreshold value for this WSGetItems.
     * 
     * @param spellTreshold
     */
    public void setSpellTreshold(int spellTreshold) {
        this.spellTreshold = spellTreshold;
    }


    /**
     * Gets the totalCountOnFirstResult value for this WSGetItems.
     * 
     * @return totalCountOnFirstResult
     */
    public java.lang.Boolean getTotalCountOnFirstResult() {
        return totalCountOnFirstResult;
    }


    /**
     * Sets the totalCountOnFirstResult value for this WSGetItems.
     * 
     * @param totalCountOnFirstResult
     */
    public void setTotalCountOnFirstResult(java.lang.Boolean totalCountOnFirstResult) {
        this.totalCountOnFirstResult = totalCountOnFirstResult;
    }


    /**
     * Gets the whereItem value for this WSGetItems.
     * 
     * @return whereItem
     */
    public org.talend.mdm.webservice.WSWhereItem getWhereItem() {
        return whereItem;
    }


    /**
     * Sets the whereItem value for this WSGetItems.
     * 
     * @param whereItem
     */
    public void setWhereItem(org.talend.mdm.webservice.WSWhereItem whereItem) {
        this.whereItem = whereItem;
    }


    /**
     * Gets the wsDataClusterPK value for this WSGetItems.
     * 
     * @return wsDataClusterPK
     */
    public org.talend.mdm.webservice.WSDataClusterPK getWsDataClusterPK() {
        return wsDataClusterPK;
    }


    /**
     * Sets the wsDataClusterPK value for this WSGetItems.
     * 
     * @param wsDataClusterPK
     */
    public void setWsDataClusterPK(org.talend.mdm.webservice.WSDataClusterPK wsDataClusterPK) {
        this.wsDataClusterPK = wsDataClusterPK;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WSGetItems)) return false;
        WSGetItems other = (WSGetItems) obj;
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
            this.maxItems == other.getMaxItems() &&
            this.skip == other.getSkip() &&
            this.spellTreshold == other.getSpellTreshold() &&
            ((this.totalCountOnFirstResult==null && other.getTotalCountOnFirstResult()==null) || 
             (this.totalCountOnFirstResult!=null &&
              this.totalCountOnFirstResult.equals(other.getTotalCountOnFirstResult()))) &&
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
        if (getConceptName() != null) {
            _hashCode += getConceptName().hashCode();
        }
        _hashCode += getMaxItems();
        _hashCode += getSkip();
        _hashCode += getSpellTreshold();
        if (getTotalCountOnFirstResult() != null) {
            _hashCode += getTotalCountOnFirstResult().hashCode();
        }
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
        new org.apache.axis.description.TypeDesc(WSGetItems.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.talend.com/mdm", "WSGetItems"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("conceptName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "conceptName"));
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
        elemField.setFieldName("spellTreshold");
        elemField.setXmlName(new javax.xml.namespace.QName("", "spellTreshold"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalCountOnFirstResult");
        elemField.setXmlName(new javax.xml.namespace.QName("", "totalCountOnFirstResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
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
