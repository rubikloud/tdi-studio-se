// ============================================================================
//
// Copyright (C) 2006-2007 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.repository.model.migration;

import org.talend.commons.exception.ExceptionHandler;
import org.talend.commons.exception.PersistenceException;
import org.talend.core.language.ECodeLanguage;
import org.talend.core.model.context.UpdateContextVariablesHelper;
import org.talend.core.model.migration.AbstractJobMigrationTask;
import org.talend.core.model.properties.ProcessItem;
import org.talend.designer.core.model.utils.emf.talendfile.ProcessType;
import org.talend.repository.model.IProxyRepositoryFactory;
import org.talend.repository.model.ProxyRepositoryFactory;

/**
 * ggu class global comment. Detailled comment.
 * 
 * such as: replace old script code ("(String)context.getProperty("var1")") to new one ("context.var1")
 */
public class ReplaceOldContextScriptCodeMigrationTask extends AbstractJobMigrationTask {

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.migration.AbstractJobMigrationTask#executeOnProcess(org.talend.core.model.properties.ProcessItem)
     */
    @SuppressWarnings("unchecked")//$NON-NLS-1$
    @Override
    public ExecutionResult executeOnProcess(ProcessItem item) {
        if (getProject().getLanguage() == ECodeLanguage.PERL) {
            return ExecutionResult.NOTHING_TO_DO;
        }

        boolean changed = UpdateContextVariablesHelper.updateProcessForReplacedOldScriptCode(item.getProcess());

        if (changed) {
            try {
                IProxyRepositoryFactory factory = ProxyRepositoryFactory.getInstance();
                factory.save(item, true);
                return ExecutionResult.SUCCESS_WITH_ALERT;
            } catch (PersistenceException e) {
                ExceptionHandler.process(e);
                return ExecutionResult.FAILURE;
            }
        }
        return ExecutionResult.NOTHING_TO_DO;
    }

}
