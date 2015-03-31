/**
 * WSXPathsSearch.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.talend.mdm.webservice;

public class WSXPathsSearch  implements java.io.Serializable {
    private java.lang.String direction;

    private int maxItems;

    private java.lang.String orderBy;

    private java.lang.String pivotPath;

    private java.lang.Boolean returnCount;

    private int skip;

    private int spellTreshold;

    private java.lang.String[] viewablePaths;

    private org.talend.mdm.webservice.WSWhereItem whereItem;

    private org.talend.mdm.webservice.WSDataClusterPK wsDataClusterPK;

    public WSXPathsSearch() {
    }

    public WSXPathsSearch(
           java.lang.String direction,
           int maxItems,
           java.lang.String orderBy,
           java.lang.String pivotPath,
           java.lang.Boolean returnCount,
           int skip,
           int spellTreshold,
           java.lang.String[] viewablePaths,
           org.talend.mdm.webservice.WSWhereItem whereItem,
           org.talend.mdm.webservice.WSDataClusterPK wsDataClusterPK) {
           this.direction = direction;
           this.maxItems = maxItems;
           this.orderBy = orderBy;
           this.pivotPath = pivotPath;
           this.returnCount = returnCount;
           this.skip = skip;
           this.spellTreshold = spellTreshold;
           this.viewablePaths = viewablePaths;
           this.whereItem = whereItem;
           this.wsDataClusterPK = wsDataClusterPK;
    }


    /**
     * Gets the direction value for this WSXPathsSearch.
     * 
     * @return direction
     */
    public java.lang.String getDirection() {
        return direction;
    }


    /**
     * Sets the direction value for this WSXPathsSearch.
     * 
     * @param direction
     */
    public void setDirection(java.lang.String direction) {
        this.direction = direction;
    }


    /**
     * Gets the maxItems value for this WSXPathsSearch.
     * 
     * @return maxItems
     */
    public int getMaxItems() {
        return maxItems;
    }


    /**
     * Sets the maxItems value for this WSXPathsSearch.
     * 
     * @param maxItems
     */
    public void setMaxItems(int maxItems) {
        this.maxItems = maxItems;
    }


    /**
     * Gets the orderBy value for this WSXPathsSearch.
     * 
     * @return orderBy
     */
    public java.lang.String getOrderBy() {
        return orderBy;
    }


    /**
     * Sets the orderBy value for this WSXPathsSearch.
     * 
     * @param orderBy
     */
    public void setOrderBy(java.lang.String orderBy) {
        this.orderBy = orderBy;
    }


    /**
     * Gets the pivotPath value for this WSXPathsSearch.
     * 
     * @return pivotPath
     */
    public java.lang.String getPivotPath() {
        return pivotPath;
    }


    /**
     * Sets the pivotPath value for this WSXPathsSearch.
     * 
     * @param pivotPath
     */
    public void setPivotPath(java.lang.String pivotPath) {
        this.pivotPath = pivotPath;
    }


    /**
     * Gets the returnCount value for this WSXPathsSearch.
     * 
     * @return returnCount
     */
    public java.lang.Boolean getReturnCount() {
        return returnCount;
    }


    /**
     * Sets the returnCount value for this WSXPathsSearch.
     * 
     * @param returnCount
     */
    public void setReturnCount(java.lang.Boolean returnCount) {
        this.returnCount = returnCount;
    }


    /**
     * Gets the skip value for this WSXPathsSearch.
     * 
     * @return skip
     */
    public int getSkip() {
        return skip;
    }


    /**
     * Sets the skip value for this WSXPathsSearch.
     * 
     * @param skip
     */
    public void setSkip(int skip) {
        this.skip = skip;
    }


    /**
     * Gets the spellTreshold value for this WSXPathsSearch.
     * 
     * @return spellTreshold
     */
    public int getSpellTreshold() {
        return spellTreshold;
    }


    /**
     * Sets the spellTreshold value for this WSXPathsSearch.
     * 
     * @param spellTreshold
     */
    public void setSpellTreshold(int spellTreshold) {
        this.spellTreshold = spellTreshold;
    }


    /**
     * Gets the viewablePaths value for this WSXPathsSearch.
     * 
     * @return viewablePaths
     */
    public java.lang.String[] getViewablePaths() {
        return viewablePaths;
    }


    /**
     * Sets the viewablePaths value for this WSXPathsSearch.
     * 
     * @param viewablePaths
     */
    public void setViewablePaths(java.lang.String[] viewablePaths) {
        this.viewablePaths = viewablePaths;
    }


    /**
     * Gets the whereItem value for this WSXPathsSearch.
     * 
     * @return whereItem
     */
    public org.talend.mdm.webservice.WSWhereItem getWhereItem() {
        return whereItem;
    }


    /**
     * Sets the whereItem value for this WSXPathsSearch.
     * 
     * @param whereItem
     */
    public void setWhereItem(org.talend.mdm.webservice.WSWhereItem whereItem) {
        this.whereItem = whereItem;
    }


    /**
     * Gets the wsDataClusterPK value for this WSXPathsSearch.
     * 
     * @return wsDataClusterPK
     */
    public org.talend.mdm.webservice.WSDataClusterPK getWsDataClusterPK() {
        return wsDataClusterPK;
    }


    /**
     * Sets the wsDataClusterPK value for this WSXPathsSearch.
     * 
     * @param wsDataClusterPK
     */
    public void setWsDataClusterPK(org.talend.mdm.webservice.WSDataClusterPK wsDataClusterPK) {
        this.wsDataClusterPK = wsDataClusterPK;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WSXPathsSearch)) return false;
        WSXPathsSearch other = (WSXPathsSearch) obj;
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
            ((this.pivotPath==null && other.getPivotPath()==null) || 
             (this.pivotPath!=null &&
              this.pivotPath.equals(other.getPivotPath()))) &&
            ((this.returnCount==null && other.getReturnCount()==null) || 
             (this.returnCount!=null &&
              this.returnCount.equals(other.getReturnCount()))) &&
            this.skip == other.getSkip() &&
            this.spellTreshold == other.getSpellTreshold() &&
            ((this.viewablePaths==null && other.getViewablePaths()==null) || 
             (this.viewablePaths!=null &&
              java.util.Arrays.equals(this.viewablePaths, other.getViewablePaths()))) &&
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
        _hashCode += getMaxItems();
        if (getOrderBy() != null) {
            _hashCode += getOrderBy().hashCode();
        }
        if (getPivotPath() != null) {
            _hashCode += getPivotPath().hashCode();
        }
        if (getReturnCount() != null) {
            _hashCode += getReturnCount().hashCode();
        }
        _hashCode += getSkip();
        _hashCode += getSpellTreshold();
        if (getViewablePaths() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getViewablePaths());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getViewablePaths(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
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
        new org.apache.axis.description.TypeDesc(WSXPathsSearch.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.talend.com/mdm", "WSXPathsSearch"));
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
        elemField.setFieldName("pivotPath");
        elemField.setXmlName(new javax.xml.namespace.QName("", "pivotPath"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("returnCount");
        elemField.setXmlName(new javax.xml.namespace.QName("", "returnCount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
        elemField.setFieldName("viewablePaths");
        elemField.setXmlName(new javax.xml.namespace.QName("", "viewablePaths"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("", "strings"));
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
