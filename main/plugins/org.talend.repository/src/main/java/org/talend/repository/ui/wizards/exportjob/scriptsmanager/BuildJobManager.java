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
package org.talend.repository.ui.wizards.exportjob.scriptsmanager;

import java.util.Map;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.talend.core.model.properties.ProcessItem;
import org.talend.core.runtime.process.IBuildJobHandler;
import org.talend.repository.ui.wizards.exportjob.JavaJobScriptsExportWSWizardPage.JobExportType;
import org.talend.repository.ui.wizards.exportjob.scriptsmanager.JobScriptsManager.ExportChoice;

/**
 * created by ycbai on 2015年5月15日 Detailled comment
 *
 */
public class BuildJobManager {

    private static BuildJobManager instance = null;

    private BuildJobManager() {
    }

    public static synchronized BuildJobManager getInstance() {
        if (instance == null) {
            instance = new BuildJobManager();
        }
        return instance;
    }

    public void buildJob(ProcessItem processItem, String version, String context, Map<ExportChoice, Object> exportChoiceMap,
            JobExportType jobExportType) throws Exception {
        IBuildJobHandler buildJobHandler = BuildJobFactory.createBuildJobHandler(exportChoiceMap, context, jobExportType);
        buildJobHandler.generateItemFiles(processItem, true, new NullProgressMonitor());
        // buildJobHandler.generateTestReports(processItem, new NullProgressMonitor());
        buildJobHandler.generateJobFiles(processItem, context, version, new NullProgressMonitor());
        buildJobHandler.build();
    }

}
