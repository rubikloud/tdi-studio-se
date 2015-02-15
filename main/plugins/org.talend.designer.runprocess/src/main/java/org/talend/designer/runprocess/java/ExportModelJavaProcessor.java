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
package org.talend.designer.runprocess.java;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Level;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.talend.commons.exception.PersistenceException;
import org.talend.core.GlobalServiceRegister;
import org.talend.core.model.process.IElementParameter;
import org.talend.core.model.process.INode;
import org.talend.core.model.process.IProcess;
import org.talend.core.model.properties.Item;
import org.talend.core.model.properties.ProcessItem;
import org.talend.core.model.properties.PropertiesPackage;
import org.talend.core.model.properties.Property;
import org.talend.core.model.repository.IRepositoryViewObject;
import org.talend.core.service.IMRProcessService;
import org.talend.designer.core.model.utils.emf.talendfile.ElementParameterType;
import org.talend.designer.core.model.utils.emf.talendfile.NodeType;
import org.talend.designer.core.model.utils.emf.talendfile.ProcessType;
import org.talend.designer.runprocess.IProcessMessageManager;
import org.talend.designer.runprocess.ProcessorException;
import org.talend.designer.runprocess.RunProcessPlugin;
import org.talend.repository.ui.utils.ZipToFile;

/**
 * Created by wchen on Mar 5, 2013.
 */
public class ExportModelJavaProcessor extends JavaProcessor {

    private String unzipFolder;

    /**
     * DOC wchen ExportModelJavaProcessor constructor comment.
     * 
     * @param process
     * @param property
     * @param filenameFromLabel
     */
    public ExportModelJavaProcessor(IProcess process, Property property, boolean filenameFromLabel) {
        super(process, property, filenameFromLabel);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.runprocess.Processor#run(java.lang.String[], int, int,
     * org.eclipse.core.runtime.IProgressMonitor, org.talend.designer.runprocess.IProcessMessageManager)
     */
    @Override
    public Process run(String[] optionsParam, int statisticsPort, int tracePort, IProgressMonitor monitor,
            IProcessMessageManager processMessageManager) throws ProcessorException {
        ExportProcessorHelper helper = new ExportProcessorHelper();

        // export job
        String archive = helper.exportJob(this, statisticsPort, tracePort,
                ArrayUtils.contains(optionsParam, "--watch") ? "--watch" : null, monitor);
        unzipFolder = unzipAndDeploy(process, archive);

        Process process = super.execFrom(unzipFolder + File.separatorChar + this.process.getName(), Level.INFO, statisticsPort,
                tracePort, optionsParam);

        return process;
    }

    @Override
    public Process run(int statisticsPort, int tracePort, String watchParam, String log4jLevel, IProgressMonitor monitor,
            IProcessMessageManager processMessageManager) throws ProcessorException {
        return run(new String[] { watchParam, log4jLevel }, statisticsPort, tracePort, monitor, processMessageManager);
    }

    @Override
    protected String getLibFolderInWorkingDir() {
        return unzipFolder + File.separator + "lib";
    }

    private String unzipAndDeploy(IProcess process, String archiveZipFileStr) {
        String unzipFolder = unzipProcess(process, archiveZipFileStr);
        return unzipFolder;
    }

    private String unzipProcess(IProcess process, String archiveZipFileStr) {
        // throws OozieJobDeployException {
        String jobName = process.getName();
        String tempFolder = null;
        if (archiveZipFileStr != null && !"".equals(archiveZipFileStr)) {
            File file = new File(archiveZipFileStr);
            tempFolder = file.getParentFile().getPath() + File.separator + jobName;
            try {
                ZipToFile.unZipFile(archiveZipFileStr, tempFolder);
            } catch (Exception e) {
                // throw new OozieJobDeployException("Can not unzip a file!", e);
            }
        }
        return tempFolder;
    }

    @Override
    public String[] getCommandLine() throws ProcessorException {
        return super.getCommandLine();
    }

    protected List<String> extractJobJar() {
        List<String> list = new ArrayList<String>();
        String jobFolderString = unzipFolder + File.separator + process.getName();
        File jobFolder = new File(jobFolderString);
        File[] jarFiles = jobFolder.listFiles(new FileFilter() {

            @Override
            public boolean accept(File pathname) {
                if (pathname.getName().endsWith(".jar")) {//$NON-NLS-1$
                    return true;
                }
                return false;
            }
        });
        if (jarFiles != null && jarFiles.length > 0) {
            for (File tmpFile : jarFiles) {
                list.add(tmpFile.getAbsolutePath() + extractClassPathSeparator());
            }
        }
        return list;
    }

    public boolean shouldRunAsExport() {
        List<? extends INode> generatedNodes = process.getGeneratingNodes();
        try {
            for (INode node : generatedNodes) {
                if (node.getComponent() != null && "tRunJob".equals(node.getComponent().getName())) {
                    IElementParameter elementParameter = node.getElementParameter("PROCESS:PROCESS_TYPE_PROCESS");
                    if (elementParameter != null) {
                        Object value = elementParameter.getValue();
                        if (value != null && !"".equals(value)) {
                            IRepositoryViewObject lastVersion = RunProcessPlugin.getDefault().getRepositoryService()
                                    .getProxyRepositoryFactory().getLastVersion(value.toString());
                            if (lastVersion != null) {
                                boolean hasMrSubProcess = hasMrSubProcess(lastVersion.getProperty().getItem());
                                if (hasMrSubProcess) {
                                    return true;
                                }
                            }

                        }
                    }
                }
            }

        } catch (PersistenceException e) {
            return false;
        }
        return false;
    }

    /**
     * DOC PLV Comment method "hasMrSubProcess".
     * 
     * @param mrService
     * @param lastVersion
     * @return
     * @throws PersistenceException
     */
    @SuppressWarnings("unchecked")
    private boolean hasMrSubProcess(Item item) throws PersistenceException {
        if (GlobalServiceRegister.getDefault().isServiceRegistered(IMRProcessService.class)) {
            IMRProcessService mrService = (IMRProcessService) GlobalServiceRegister.getDefault().getService(
                    IMRProcessService.class);
            if (mrService.isMapReduceItem(item)) {
                return true;
            } else if (item.eClass() == PropertiesPackage.Literals.PROCESS_ITEM) {
                ProcessType processType = ((ProcessItem) item).getProcess();
                EList<NodeType> nodes = processType.getNode();
                for (NodeType node : nodes) {
                    if ("tRunJob".equals(node.getComponentName())) {
                        EList<ElementParameterType> elementParameters = node.getElementParameter();
                        for (ElementParameterType param : elementParameters) {
                            if (param.getName() != null && "PROCESS:PROCESS_TYPE_PROCESS".equals(param.getName())) {
                                Object value = param.getValue();
                                if (value != null && !"".equals(value)) {
                                    IRepositoryViewObject lastVersion = RunProcessPlugin.getDefault().getRepositoryService()
                                            .getProxyRepositoryFactory().getLastVersion(value.toString());
                                    if (lastVersion != null) {
                                        return hasMrSubProcess(lastVersion.getProperty().getItem());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}