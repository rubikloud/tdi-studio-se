// ============================================================================
//
// Talend Community Edition
//
// Copyright (C) 2006-2007 Talend - www.talend.com
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
//
// ============================================================================
package org.talend.designer.mapper.model.tableentry;

import org.talend.designer.abstractmap.model.table.IDataMapTable;
import org.talend.designer.abstractmap.model.tableentry.IColumnEntry;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * $Id$
 * 
 */
public class VarTableEntry extends DataMapTableEntry implements IColumnEntry {

    private String type;

    private boolean nullable;

    public VarTableEntry(IDataMapTable abstractDataMapTable, String name, String expression, String type) {
        super(abstractDataMapTable, name, expression);
        this.type = type;
    }

    public VarTableEntry(IDataMapTable abstractDataMapTable, String name) {
        this(abstractDataMapTable, name, null, null);
    }

    /**
     * Getter for type.
     * 
     * @return the type
     */
    public String getType() {
        return this.type;
    }

    /**
     * Sets the type.
     * 
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Getter for nullable.
     * 
     * @return the nullable
     */
    public boolean isNullable() {
        return this.nullable;
    }

    /**
     * Sets the nullable.
     * 
     * @param nullable the nullable to set
     */
    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    /* (non-Javadoc)
     * @see org.talend.designer.mapper.model.tableentry.IDataMapTableEntry#isTableEntry()
     */
    public boolean isTableEntry() {
        return true;
    }

    /* (non-Javadoc)
     * @see org.talend.designer.mapper.model.tableentry.IDataMapTableEntry#isColumnEntry()
     */
    public boolean isColumnEntry() {
        return true;
    }

    
}
