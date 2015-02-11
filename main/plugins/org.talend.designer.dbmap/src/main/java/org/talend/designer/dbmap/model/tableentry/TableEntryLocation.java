// ============================================================================
//
// Copyright (C) 2006-2014 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.designer.dbmap.model.tableentry;

import org.talend.designer.abstractmap.model.tableentry.ITableEntry;
import org.talend.designer.dbmap.language.DbLanguageConstants;
import org.talend.designer.dbmap.language.generation.DbMapSqlConstants;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * $Id: TableEntryLocation.java 1782 2007-02-03 07:57:38Z bqian $
 * 
 */
public class TableEntryLocation {

    public String tableName;

    public String columnName;

    public String prefix;

    public String sufix;

    public TableEntryLocation() {
        super();
    }

    public TableEntryLocation(TableEntryLocation tableEntryLocation) {
        this.tableName = tableEntryLocation.tableName;
        this.columnName = tableEntryLocation.columnName;
        this.prefix = tableEntryLocation.prefix;
        this.sufix = tableEntryLocation.sufix;
    }

    public TableEntryLocation(String tableName, String columnName) {
        this(DbMapSqlConstants.EMPTY, tableName, columnName, DbMapSqlConstants.EMPTY);
    }

    public TableEntryLocation(String prefix, String tableName, String columnName, String sufix) {
        this.prefix = prefix.trim();
        this.tableName = tableName.trim();
        this.columnName = columnName.trim();
        this.sufix = sufix.trim();
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(prefix);
        buffer.append(tableName);
        buffer.append(DbLanguageConstants.PREFIX_FIELD_NAME);
        buffer.append(columnName);
        buffer.append(sufix);
        return buffer.toString();
    }

    public static TableEntryLocation getNewInstance(ITableEntry dataMapTableEntry) {
        return new TableEntryLocation(dataMapTableEntry.getParent().getName(), dataMapTableEntry.getName());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.columnName == null) ? 0 : this.columnName.hashCode());
        result = prime * result + ((this.tableName == null) ? 0 : this.tableName.hashCode());
        result = prime * result + ((this.prefix == null) ? 0 : this.prefix.hashCode());
        result = prime * result + ((this.sufix == null) ? 0 : this.sufix.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TableEntryLocation other = (TableEntryLocation) obj;
        if (this.columnName == null) {
            if (other.columnName != null) {
                return false;
            }
        } else if (!this.columnName.equals(other.columnName)) {
            return false;
        }
        if (this.tableName == null) {
            if (other.tableName != null) {
                return false;
            }
        } else if (!this.tableName.equals(other.tableName)) {
            return false;
        }

        if (this.prefix == null) {
            if (other.prefix != null) {
                return false;
            }
        } else if (!this.prefix.equals(other.prefix)) {
            return false;
        }

        if (this.sufix == null) {
            if (other.sufix != null) {
                return false;
            }
        } else if (!this.sufix.equals(other.sufix)) {
            return false;
        }
        return true;
    }

}
