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
package org.talend.repository.ui.wizards.exportjob.handler;

import java.util.Map;

import org.apache.commons.lang.BooleanUtils;
import org.talend.core.CorePlugin;
import org.talend.core.model.properties.Item;
import org.talend.core.model.properties.Project;
import org.talend.core.runtime.process.IBuildJobHandler;
import org.talend.core.runtime.process.ITalendProcessJavaProject;
import org.talend.designer.runprocess.IRunProcessService;
import org.talend.repository.ProjectManager;
import org.talend.repository.ui.wizards.exportjob.scriptsmanager.JobScriptsManager.ExportChoice;

/**
 * created by ycbai on 2015年5月13日 Detailled comment
 *
 */
public abstract class AbstractBuildJobHandler implements IBuildJobHandler {

    protected static final String PATH_SEPARATOR = "/"; //$NON-NLS-1$

    protected Map<ExportChoice, Object> exportChoice;

    protected ITalendProcessJavaProject talendProcessJavaProject;

    public AbstractBuildJobHandler(Map<ExportChoice, Object> exportChoiceMap, String contextName) {
        this.exportChoice = exportChoiceMap;
        IRunProcessService runProcessService = CorePlugin.getDefault().getRunProcessService();
        talendProcessJavaProject = runProcessService.getTalendProcessJavaProject();
    }

    protected boolean isOptionChoosed(Object key) {
        if (key != null) {
            final Object object = exportChoice.get(key);
            if (object instanceof Boolean) {
                return BooleanUtils.isTrue((Boolean) object);
            }
        }
        return false;
    }

    protected Project getProject(Item item) {
        Project project = ProjectManager.getInstance().getProject(item);
        return project;
    }

}
