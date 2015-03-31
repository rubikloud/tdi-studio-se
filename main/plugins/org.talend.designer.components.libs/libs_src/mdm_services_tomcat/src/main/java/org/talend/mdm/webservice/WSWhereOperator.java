/**
 * WSWhereOperator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.talend.mdm.webservice;

public class WSWhereOperator implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected WSWhereOperator(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _JOIN = "JOIN";
    public static final java.lang.String _CONTAINS_TEXT_OF = "CONTAINS_TEXT_OF";
    public static final java.lang.String _CONTAINS = "CONTAINS";
    public static final java.lang.String _STARTSWITH = "STARTSWITH";
    public static final java.lang.String _STRICTCONTAINS = "STRICTCONTAINS";
    public static final java.lang.String _EQUALS = "EQUALS";
    public static final java.lang.String _NOT_EQUALS = "NOT_EQUALS";
    public static final java.lang.String _GREATER_THAN = "GREATER_THAN";
    public static final java.lang.String _GREATER_THAN_OR_EQUAL = "GREATER_THAN_OR_EQUAL";
    public static final java.lang.String _LOWER_THAN = "LOWER_THAN";
    public static final java.lang.String _LOWER_THAN_OR_EQUAL = "LOWER_THAN_OR_EQUAL";
    public static final java.lang.String _NO_OPERATOR = "NO_OPERATOR";
    public static final java.lang.String _FULLTEXTSEARCH = "FULLTEXTSEARCH";
    public static final java.lang.String _EMPTY_NULL = "EMPTY_NULL";
    public static final WSWhereOperator JOIN = new WSWhereOperator(_JOIN);
    public static final WSWhereOperator CONTAINS_TEXT_OF = new WSWhereOperator(_CONTAINS_TEXT_OF);
    public static final WSWhereOperator CONTAINS = new WSWhereOperator(_CONTAINS);
    public static final WSWhereOperator STARTSWITH = new WSWhereOperator(_STARTSWITH);
    public static final WSWhereOperator STRICTCONTAINS = new WSWhereOperator(_STRICTCONTAINS);
    public static final WSWhereOperator EQUALS = new WSWhereOperator(_EQUALS);
    public static final WSWhereOperator NOT_EQUALS = new WSWhereOperator(_NOT_EQUALS);
    public static final WSWhereOperator GREATER_THAN = new WSWhereOperator(_GREATER_THAN);
    public static final WSWhereOperator GREATER_THAN_OR_EQUAL = new WSWhereOperator(_GREATER_THAN_OR_EQUAL);
    public static final WSWhereOperator LOWER_THAN = new WSWhereOperator(_LOWER_THAN);
    public static final WSWhereOperator LOWER_THAN_OR_EQUAL = new WSWhereOperator(_LOWER_THAN_OR_EQUAL);
    public static final WSWhereOperator NO_OPERATOR = new WSWhereOperator(_NO_OPERATOR);
    public static final WSWhereOperator FULLTEXTSEARCH = new WSWhereOperator(_FULLTEXTSEARCH);
    public static final WSWhereOperator EMPTY_NULL = new WSWhereOperator(_EMPTY_NULL);
    public java.lang.String getValue() { return _value_;}
    public static WSWhereOperator fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        WSWhereOperator enumeration = (WSWhereOperator)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static WSWhereOperator fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(WSWhereOperator.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.talend.com/mdm", "WSWhereOperator"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
