// ============================================================================
//
// Copyright (C) 2006-2015 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.repository.ui.wizards.exportjob.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.talend.core.model.properties.ProcessItem;
import org.talend.designer.core.model.utils.emf.talendfile.ContextParameterType;
import org.talend.designer.core.model.utils.emf.talendfile.ContextType;
import org.talend.designer.core.model.utils.emf.talendfile.impl.ProcessTypeImpl;

/**
 * created by ycbai on 2015年5月20日 Detailled comment
 *
 */
public class ExportJobUtil {

    /**
     * 
     * Gets the set of current job's context.
     * 
     * @return a List of context names.
     * 
     */
    public static List<String> getJobContexts(ProcessItem processItem) {
        List<String> contextNameList = new ArrayList<String>();
        for (Object o : ((ProcessTypeImpl) processItem.getProcess()).getContext()) {
            if (o instanceof ContextType) {
                ContextType context = (ContextType) o;
                if (contextNameList.contains(context.getName())) {
                    continue;
                }
                contextNameList.add(context.getName());
            }
        }
        return contextNameList;
    }

    /**
     * DOC zli Comment method "getJobContextValues".
     * 
     * @param processItem
     * @param contextName
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static List<ContextParameterType> getJobContextValues(ProcessItem processItem, String contextName) {
        if (contextName == null) {
            return null;
        }// else do next line
        List<ContextParameterType> list = new ArrayList<ContextParameterType>();
        EList contexts = ((ProcessTypeImpl) processItem.getProcess()).getContext();
        for (int i = 0; i < contexts.size(); i++) {
            Object object = contexts.get(i);
            if (object instanceof ContextType) {
                ContextType contextType = (ContextType) object;
                if (contextName.equals(contextType.getName())) {
                    EList contextParameter = contextType.getContextParameter();
                    for (int j = 0; j < contextParameter.size(); j++) {
                        Object object2 = contextParameter.get(j);
                        if (object2 instanceof ContextParameterType) {
                            ContextParameterType contextParameterType = (ContextParameterType) object2;
                            list.add(contextParameterType);
                        }
                    }
                    return list;
                }
            }
        }
        return null;
    }

}
