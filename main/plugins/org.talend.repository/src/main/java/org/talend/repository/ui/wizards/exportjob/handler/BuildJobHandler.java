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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.talend.core.GlobalServiceRegister;
import org.talend.core.ITDQItemService;
import org.talend.core.PluginChecker;
import org.talend.core.model.process.ProcessUtils;
import org.talend.core.model.properties.Item;
import org.talend.core.model.properties.ProcessItem;
import org.talend.core.model.properties.Project;
import org.talend.core.model.repository.IRepositoryViewObject;
import org.talend.core.model.runprocess.LastGenerationInfo;
import org.talend.core.ui.ITestContainerProviderService;
import org.talend.designer.runprocess.ProcessorUtilities;
import org.talend.model.bridge.ReponsitoryContextBridge;
import org.talend.repository.local.ExportItemUtil;
import org.talend.repository.ui.wizards.exportjob.scriptsmanager.JobScriptsManager.ExportChoice;
import org.talend.utils.io.FilesUtils;

/**
 * created by ycbai on 2015年5月13日 Detailled comment
 *
 */
public class BuildJobHandler extends AbstractBuildJobHandler {

    private final String GOAL_PACKAGE = "package"; //$NON-NLS-1$

    public BuildJobHandler(Map<ExportChoice, Object> exportChoiceMap, String contextName) {
        super(exportChoiceMap, contextName);
    }

    @Override
    public void generateJobFiles(ProcessItem processItem, String contextName, String version, IProgressMonitor monitor)
            throws Exception {
        if (!isOptionChoosed(ExportChoice.needSourceCode)) {
            return;
        }
        LastGenerationInfo.getInstance().getUseDynamicMap().clear();
        int generationOption = (isOptionChoosed(ExportChoice.includeTestSource) || isOptionChoosed(ExportChoice.executeTests)) ? ProcessorUtilities.GENERATE_ALL_CHILDS
                | ProcessorUtilities.GENERATE_TESTS
                : ProcessorUtilities.GENERATE_ALL_CHILDS;
        ProcessorUtilities.generateCode(processItem, contextName, version, false, false,
                isOptionChoosed(ExportChoice.applyToChildren), isOptionChoosed(ExportChoice.needContext), generationOption,
                monitor);
    }

    @Override
    public void generateTestReports(ProcessItem processItem, IProgressMonitor monitor) throws Exception {
        if (!isOptionChoosed(ExportChoice.includeTestSource)) {
            return;
        }
        if (GlobalServiceRegister.getDefault().isServiceRegistered(ITestContainerProviderService.class)) {
            ITestContainerProviderService testContainerService = (ITestContainerProviderService) GlobalServiceRegister
                    .getDefault().getService(ITestContainerProviderService.class);
            if (testContainerService != null) {
                List<IFile> reports = new ArrayList<IFile>();
                List<ProcessItem> testsItems = testContainerService.getAllTestContainers(processItem);
                for (ProcessItem testItem : testsItems) {
                    List<IFile> testReportFiles = testContainerService.getTestReportFiles(testItem);
                    if (testReportFiles.size() > 0) {
                        reports.add(testReportFiles.get(0));
                    }
                }
                IFolder testsFolder = talendProcessJavaProject.getTestsFolder();
                talendProcessJavaProject.cleanFolder(monitor, testsFolder);
                IFolder parentFolder = talendProcessJavaProject.createSubFolder(monitor, testsFolder, processItem.getProperty()
                        .getLabel());
                for (IFile report : reports) {
                    report.copy(parentFolder.getFile(report.getName()).getFullPath(), true, monitor);
                }
            }
        }
    }

    @Override
    public void generateItemFiles(ProcessItem processItem, boolean withDependencies, IProgressMonitor monitor) throws Exception {
        if (!isOptionChoosed(ExportChoice.needJobItem)) {
            return;
        }
        IFolder itemsFolder = talendProcessJavaProject.getItemsFolder();
        talendProcessJavaProject.cleanFolder(monitor, itemsFolder);
        List<Item> items = new ArrayList<Item>();
        items.add(processItem);
        ExportItemUtil exportItemUtil = new ExportItemUtil();
        if (withDependencies) {
            Collection<IRepositoryViewObject> allProcessDependencies = ProcessUtils.getAllProcessDependencies(items);
            for (IRepositoryViewObject repositoryObject : allProcessDependencies) {
                items.add(repositoryObject.getProperty().getItem());
            }
        }
        File destination = new File(itemsFolder.getLocation().toFile().getAbsolutePath());
        exportItemUtil.exportItems(destination, items, false, new NullProgressMonitor());
        addDQDependencies(processItem, itemsFolder);
    }

    private void addDQDependencies(ProcessItem processItem, IFolder parentFolder) throws IOException {
        if (GlobalServiceRegister.getDefault().isServiceRegistered(ITDQItemService.class)) {
            ITDQItemService tdqItemService = (ITDQItemService) GlobalServiceRegister.getDefault().getService(
                    ITDQItemService.class);
            if (tdqItemService != null && tdqItemService.hasProcessItemDependencies(Arrays.asList(new Item[] { processItem }))) {
                // add .Talend.definition file
                String defIdxFolderName = "TDQ_Libraries"; //$NON-NLS-1$
                String defIdxFileName = ".Talend.definition"; //$NON-NLS-1$
                Project pro = getProject(processItem);
                IFolder itemsProjectFolder = parentFolder.getFolder(pro.getTechnicalLabel());
                File itemsFolderDir = new File(parentFolder.getLocation().toFile().getAbsolutePath());
                IProject project = ReponsitoryContextBridge.getRootProject();
                String defIdxRelativePath = defIdxFolderName + PATH_SEPARATOR + defIdxFileName;
                IFile defIdxFile = project.getFile(defIdxRelativePath);
                if (defIdxFile.exists()) {
                    File defIdxFileSource = new File(project.getLocation().makeAbsolute().append(defIdxFolderName)
                            .append(defIdxFileName).toFile().toURI());
                    File defIdxFileTarget = new File(itemsProjectFolder.getFile(defIdxRelativePath).getLocation().toFile()
                            .getAbsolutePath());
                    FilesUtils.copyFile(defIdxFileSource, defIdxFileTarget);
                }
                // add report header image & template files
                String reportingBundlePath = PluginChecker.getBundlePath("org.talend.dataquality.reporting"); //$NON-NLS-1$
                File imageFolder = new File(reportingBundlePath + PATH_SEPARATOR + "images"); //$NON-NLS-1$
                if (imageFolder.exists()) {
                    FilesUtils.copyDirectory(imageFolder, itemsFolderDir);
                }
                File templateFolder = new File(reportingBundlePath + PATH_SEPARATOR + "reports"); //$NON-NLS-1$ 
                if (templateFolder.exists() && templateFolder.isDirectory()) {
                    FilesUtils.copyDirectory(templateFolder, itemsFolderDir);
                }
            }
        }
    }

    @Override
    public void build() throws Exception {
        talendProcessJavaProject.buildModules(GOAL_PACKAGE, null);
    }

}
