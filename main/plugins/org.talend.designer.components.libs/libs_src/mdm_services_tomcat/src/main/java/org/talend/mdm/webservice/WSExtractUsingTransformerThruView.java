/**
 * WSExtractUsingTransformerThruView.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.talend.mdm.webservice;

public class WSExtractUsingTransformerThruView  implements java.io.Serializable {
    private java.lang.String direction;

    private int maxItems;

    private java.lang.String orderBy;

    private int skip;

    private int spellTreshold;

    private org.talend.mdm.webservice.WSWhereItem whereItem;

    private org.talend.mdm.webservice.WSDataClusterPK wsDataClusterPK;

    private org.talend.mdm.webservice.WSTransformerPK wsTransformerPK;

    private org.talend.mdm.webservice.WSViewPK wsViewPK;

    public WSExtractUsingTransformerThruView() {
    }

    public WSExtractUsingTransformerThruView(
           java.lang.String direction,
           int maxItems,
           java.lang.String orderBy,
           int skip,
           int spellTreshold,
           org.talend.mdm.webservice.WSWhereItem whereItem,
           org.talend.mdm.webservice.WSDataClusterPK wsDataClusterPK,
           org.talend.mdm.webservice.WSTransformerPK wsTransformerPK,
           org.talend.mdm.webservice.WSViewPK wsViewPK) {
           this.direction = direction;
           this.maxItems = maxItems;
           this.orderBy = orderBy;
           this.skip = skip;
           this.spellTreshold = spellTreshold;
           this.whereItem = whereItem;
           this.wsDataClusterPK = wsDataClusterPK;
           this.wsTransformerPK = wsTransformerPK;
           this.wsViewPK = wsViewPK;
    }


    /**
     * Gets the direction value for this WSExtractUsingTransformerThruView.
     * 
     * @return direction
     */
    public java.lang.String getDirection() {
        return direction;
    }


    /**
     * Sets the direction value for this WSExtractUsingTransformerThruView.
     * 
     * @param direction
     */
    public void setDirection(java.lang.String direction) {
        this.direction = direction;
    }


    /**
     * Gets the maxItems value for this WSExtractUsingTransformerThruView.
     * 
     * @return maxItems
     */
    public int getMaxItems() {
        return maxItems;
    }


    /**
     * Sets the maxItems value for this WSExtractUsingTransformerThruView.
     * 
     * @param maxItems
     */
    public void setMaxItems(int maxItems) {
        this.maxItems = maxItems;
    }


    /**
     * Gets the orderBy value for this WSExtractUsingTransformerThruView.
     * 
     * @return orderBy
     */
    public java.lang.String getOrderBy() {
        return orderBy;
    }


    /**
     * Sets the orderBy value for this WSExtractUsingTransformerThruView.
     * 
     * @param orderBy
     */
    public void setOrderBy(java.lang.String orderBy) {
        this.orderBy = orderBy;
    }


    /**
     * Gets the skip value for this WSExtractUsingTransformerThruView.
     * 
     * @return skip
     */
    public int getSkip() {
        return skip;
    }


    /**
     * Sets the skip value for this WSExtractUsingTransformerThruView.
     * 
     * @param skip
     */
    public void setSkip(int skip) {
        this.skip = skip;
    }


    /**
     * Gets the spellTreshold value for this WSExtractUsingTransformerThruView.
     * 
     * @return spellTreshold
     */
    public int getSpellTreshold() {
        return spellTreshold;
    }


    /**
     * Sets the spellTreshold value for this WSExtractUsingTransformerThruView.
     * 
     * @param spellTreshold
     */
    public void setSpellTreshold(int spellTreshold) {
        this.spellTreshold = spellTreshold;
    }


    /**
     * Gets the whereItem value for this WSExtractUsingTransformerThruView.
     * 
     * @return whereItem
     */
    public org.talend.mdm.webservice.WSWhereItem getWhereItem() {
        return whereItem;
    }


    /**
     * Sets the whereItem value for this WSExtractUsingTransformerThruView.
     * 
     * @param whereItem
     */
    public void setWhereItem(org.talend.mdm.webservice.WSWhereItem whereItem) {
        this.whereItem = whereItem;
    }


    /**
     * Gets the wsDataClusterPK value for this WSExtractUsingTransformerThruView.
     * 
     * @return wsDataClusterPK
     */
    public org.talend.mdm.webservice.WSDataClusterPK getWsDataClusterPK() {
        return wsDataClusterPK;
    }


    /**
     * Sets the wsDataClusterPK value for this WSExtractUsingTransformerThruView.
     * 
     * @param wsDataClusterPK
     */
    public void setWsDataClusterPK(org.talend.mdm.webservice.WSDataClusterPK wsDataClusterPK) {
        this.wsDataClusterPK = wsDataClusterPK;
    }


    /**
     * Gets the wsTransformerPK value for this WSExtractUsingTransformerThruView.
     * 
     * @return wsTransformerPK
     */
    public org.talend.mdm.webservice.WSTransformerPK getWsTransformerPK() {
        return wsTransformerPK;
    }


    /**
     * Sets the wsTransformerPK value for this WSExtractUsingTransformerThruView.
     * 
     * @param wsTransformerPK
     */
    public void setWsTransformerPK(org.talend.mdm.webservice.WSTransformerPK wsTransformerPK) {
        this.wsTransformerPK = wsTransformerPK;
    }


    /**
     * Gets the wsViewPK value for this WSExtractUsingTransformerThruView.
     * 
     * @return wsViewPK
     */
    public org.talend.mdm.webservice.WSViewPK getWsViewPK() {
        return wsViewPK;
    }


    /**
     * Sets the wsViewPK value for this WSExtractUsingTransformerThruView.
     * 
     * @param wsViewPK
     */
    public void setWsViewPK(org.talend.mdm.webservice.WSViewPK wsViewPK) {
        this.wsViewPK = wsViewPK;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WSExtractUsingTransformerThruView)) return false;
        WSExtractUsingTransformerThruView other = (WSExtractUsingTransformerThruView) obj;
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
            this.maxItems == other.getMaxItems() &&
            ((this.orderBy==null && other.getOrderBy()==null) || 
             (this.orderBy!=null &&
              this.orderBy.equals(other.getOrderBy()))) &&
            this.skip == other.getSkip() &&
            this.spellTreshold == other.getSpellTreshold() &&
            ((this.whereItem==null && other.getWhereItem()==null) || 
             (this.whereItem!=null &&
              this.whereItem.equals(other.getWhereItem()))) &&
            ((this.wsDataClusterPK==null && other.getWsDataClusterPK()==null) || 
             (this.wsDataClusterPK!=null &&
              this.wsDataClusterPK.equals(other.getWsDataClusterPK()))) &&
            ((this.wsTransformerPK==null && other.getWsTransformerPK()==null) || 
             (this.wsTransformerPK!=null &&
              this.wsTransformerPK.equals(other.getWsTransformerPK()))) &&
            ((this.wsViewPK==null && other.getWsViewPK()==null) || 
             (this.wsViewPK!=null &&
              this.wsViewPK.equals(other.getWsViewPK())));
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
        _hashCode += getMaxItems();
        if (getOrderBy() != null) {
            _hashCode += getOrderBy().hashCode();
        }
        _hashCode += getSkip();
        _hashCode += getSpellTreshold();
        if (getWhereItem() != null) {
            _hashCode += getWhereItem().hashCode();
        }
        if (getWsDataClusterPK() != null) {
            _hashCode += getWsDataClusterPK().hashCode();
        }
        if (getWsTransformerPK() != null) {
            _hashCode += getWsTransformerPK().hashCode();
        }
        if (getWsViewPK() != null) {
            _hashCode += getWsViewPK().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WSExtractUsingTransformerThruView.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.talend.com/mdm", "WSExtractUsingTransformerThruView"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("direction");
        elemField.setXmlName(new javax.xml.namespace.QName("", "direction"));
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
        elemField.setFieldName("orderBy");
        elemField.setXmlName(new javax.xml.namespace.QName("", "orderBy"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
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
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wsTransformerPK");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wsTransformerPK"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.talend.com/mdm", "WSTransformerPK"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wsViewPK");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wsViewPK"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.talend.com/mdm", "WSViewPK"));
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
