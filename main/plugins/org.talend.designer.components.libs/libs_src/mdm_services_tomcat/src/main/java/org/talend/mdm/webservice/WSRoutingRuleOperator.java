/**
 * WSRoutingRuleOperator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.talend.mdm.webservice;

public class WSRoutingRuleOperator implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected WSRoutingRuleOperator(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _CONTAINS = "CONTAINS";
    public static final java.lang.String _MATCHES = "MATCHES";
    public static final java.lang.String _STARTSWITH = "STARTSWITH";
    public static final java.lang.String _EQUALS = "EQUALS";
    public static final java.lang.String _NOT_EQUALS = "NOT_EQUALS";
    public static final java.lang.String _GREATER_THAN = "GREATER_THAN";
    public static final java.lang.String _GREATER_THAN_OR_EQUAL = "GREATER_THAN_OR_EQUAL";
    public static final java.lang.String _LOWER_THAN = "LOWER_THAN";
    public static final java.lang.String _LOWER_THAN_OR_EQUAL = "LOWER_THAN_OR_EQUAL";
    public static final java.lang.String _IS_NULL = "IS_NULL";
    public static final java.lang.String _IS_NOT_NULL = "IS_NOT_NULL";
    public static final WSRoutingRuleOperator CONTAINS = new WSRoutingRuleOperator(_CONTAINS);
    public static final WSRoutingRuleOperator MATCHES = new WSRoutingRuleOperator(_MATCHES);
    public static final WSRoutingRuleOperator STARTSWITH = new WSRoutingRuleOperator(_STARTSWITH);
    public static final WSRoutingRuleOperator EQUALS = new WSRoutingRuleOperator(_EQUALS);
    public static final WSRoutingRuleOperator NOT_EQUALS = new WSRoutingRuleOperator(_NOT_EQUALS);
    public static final WSRoutingRuleOperator GREATER_THAN = new WSRoutingRuleOperator(_GREATER_THAN);
    public static final WSRoutingRuleOperator GREATER_THAN_OR_EQUAL = new WSRoutingRuleOperator(_GREATER_THAN_OR_EQUAL);
    public static final WSRoutingRuleOperator LOWER_THAN = new WSRoutingRuleOperator(_LOWER_THAN);
    public static final WSRoutingRuleOperator LOWER_THAN_OR_EQUAL = new WSRoutingRuleOperator(_LOWER_THAN_OR_EQUAL);
    public static final WSRoutingRuleOperator IS_NULL = new WSRoutingRuleOperator(_IS_NULL);
    public static final WSRoutingRuleOperator IS_NOT_NULL = new WSRoutingRuleOperator(_IS_NOT_NULL);
    public java.lang.String getValue() { return _value_;}
    public static WSRoutingRuleOperator fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        WSRoutingRuleOperator enumeration = (WSRoutingRuleOperator)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static WSRoutingRuleOperator fromString(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumSerializer(
            _javaType, _xmlType);
    }
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumDeserializer(
            _javaType, _xmlType);
    }
    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WSRoutingRuleOperator.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.talend.com/mdm", "WSRoutingRuleOperator"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
