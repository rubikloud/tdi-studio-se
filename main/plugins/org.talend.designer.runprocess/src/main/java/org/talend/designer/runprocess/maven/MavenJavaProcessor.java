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
package org.talend.designer.runprocess.maven;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.commons.utils.generation.JavaUtils;
import org.talend.commons.utils.resource.FileExtensions;
import org.talend.core.model.process.IProcess;
import org.talend.core.model.process.JobInfo;
import org.talend.core.model.process.ProcessUtils;
import org.talend.core.model.properties.ProcessItem;
import org.talend.core.model.properties.Property;
import org.talend.core.model.utils.JavaResourcesHelper;
import org.talend.core.runtime.process.ITalendProcessJavaProject;
import org.talend.designer.maven.model.MavenConstants;
import org.talend.designer.maven.pom.MavenPomManager;
import org.talend.designer.maven.pom.PomUtil;
import org.talend.designer.maven.tools.CreateMavenJobPom;
import org.talend.designer.maven.utils.JobUtils;
import org.talend.designer.runprocess.ProcessorException;
import org.talend.designer.runprocess.ProcessorUtilities;
import org.talend.designer.runprocess.java.JavaProcessor;
import org.talend.designer.runprocess.java.JavaProcessorUtilities;
import org.talend.repository.ProjectManager;

/**
 * created by ggu on 2 Feb 2015 Detailled comment
 *
 */
public class MavenJavaProcessor extends JavaProcessor {

    protected String windowsClasspath, unixClasspath;

    private boolean isTestJob;

    public MavenJavaProcessor(IProcess process, Property property, boolean filenameFromLabel) {
        super(process, property, filenameFromLabel);
        isTestJob = ProcessUtils.isTestContainer(process);
    }

    @Override
    public void generateCode(boolean statistics, boolean trace, boolean javaProperties) throws ProcessorException {
        super.generateCode(statistics, trace, javaProperties);
        // only job, now for Shadow Process/Data Preview.
        if (isStandardJob()) {
            generatePom();
            // removeGeneratedJobs(null);
        }
    }

    @Override
    public Set<JobInfo> getBuildChildrenJobs() {
        if (buildChildrenJobs == null || buildChildrenJobs.isEmpty()) {
            buildChildrenJobs = new HashSet<JobInfo>();

            if (property != null) {
                Set<JobInfo> infos = ProcessorUtilities.getChildrenJobInfo((ProcessItem) property.getItem());
                for (JobInfo jobInfo : infos) {
                    buildChildrenJobs.add(jobInfo);
                }
            }
        }
        return this.buildChildrenJobs;
    }

    @Override
    protected String getBaseLibPath() {
        if (isOldBuildJob()) {
            return super.getBaseLibPath();
        }
        return JavaUtils.JAVA_LIB_DIRECTORY;
    }

    public void initJobClasspath() {
        String oldInterpreter = ProcessorUtilities.getInterpreter();
        String oldCodeLocation = ProcessorUtilities.getCodeLocation();
        String oldLibraryPath = ProcessorUtilities.getLibraryPath();
        boolean oldExportConfig = ProcessorUtilities.isExportConfig();
        Date oldExportTimestamp = ProcessorUtilities.getExportTimestamp();
        try {
            // FIXME, must make sure the exportConfig is true, and the classpath is same as export.
            String routinesJarPath = getBaseLibPath() + JavaUtils.PATH_SEPARATOR + JavaUtils.ROUTINE_JAR_NAME
                    + FileExtensions.JAR_FILE_SUFFIX + ProcessorUtilities.TEMP_JAVA_CLASSPATH_SEPARATOR;
            ProcessorUtilities.setExportConfig(JavaUtils.JAVA_APP_NAME, routinesJarPath, getBaseLibPath());

            String contextName = JavaResourcesHelper.getJobContextName(this.context);
            this.windowsClasspath = getClasspath(Platform.OS_WIN32, contextName);
            this.unixClasspath = getClasspath(Platform.OS_LINUX, contextName);

            // ProcessorUtilities.resetExportConfig(); because will set back, so no used
        } finally {
            ProcessorUtilities.setExportConfig(oldInterpreter, oldCodeLocation, oldLibraryPath, oldExportConfig,
                    oldExportTimestamp);
        }
    }

    /**
     * 
     * copied from JobScriptsManager.getCommandByTalendJob
     */
    protected String getClasspath(String tp, String contextName) {
        try {
            // maybe should just reuse current processor's getCommandLine method.
            String[] cmds = ProcessorUtilities.getCommandLine(isOldBuildJob(), tp, true, process, null, contextName, false, -1,
                    -1);
            int cpIndex = ArrayUtils.indexOf(cmds, JavaUtils.JAVA_CP);
            if (cpIndex > -1) { // found
                // return the cp values in the next index.
                return cmds[cpIndex + 1];
            }
        } catch (ProcessorException e) {
            ExceptionHandler.process(e);
        }
        return null;
    }

    @Override
    protected String getExportJarsStr() {
        if (isOldBuildJob()) {
            return super.getExportJarsStr();
        }
        // use the maven way for jar
        final String libPrefixPath = getLibPrefixPath(true);
        final String classPathSeparator = extractClassPathSeparator();

        // Test-0.1
        String jarName = JavaResourcesHelper.getJobJarName(process.getName(), process.getVersion());
        String exportJar = libPrefixPath + jarName + FileExtensions.JAR_FILE_SUFFIX;

        Set<JobInfo> infos = getBuildChildrenJobs();
        for (JobInfo jobInfo : infos) {
            String childJarName = JavaResourcesHelper.getJobJarName(jobInfo.getJobName(), jobInfo.getJobVersion());
            exportJar += classPathSeparator + libPrefixPath + childJarName + FileExtensions.JAR_FILE_SUFFIX;
        }
        return exportJar;
    }

    /**
     * .Java/pom_TestJob.xml
     */
    protected IFile getPomFile() {
        String pomFileName = PomUtil.getPomFileName(this.getProperty().getLabel());
        return this.getTalendJavaProject().getProject().getFile(pomFileName);
    }

    /**
     * .Java/src/main/assemblies/assembly_TestJob.xml
     */
    protected IFile getAssemblyFile() {
        String assemblyFileName = PomUtil.getAssemblyFileName(this.getProperty().getLabel());
        return this.getTalendJavaProject().getAssembliesFolder().getFile(assemblyFileName);
    }

    protected void generatePom() {

        initJobClasspath();

        try {
            CreateMavenJobPom createTemplatePom = new CreateMavenJobPom(this, getPomFile());
            // TODO when export, need same as JobJavaScriptsManager.getJobInfoFile
            createTemplatePom.setAddStat(false);
            createTemplatePom.setApplyContextToChild(false);

            createTemplatePom.setUnixClasspath(this.unixClasspath);
            createTemplatePom.setWindowsClasspath(this.windowsClasspath);

            createTemplatePom.setAssemblyFile(getAssemblyFile());

            createTemplatePom.setOverwrite(true);

            createTemplatePom.create(null);

        } catch (Exception e) {
            ExceptionHandler.process(e);
        }

    }

    protected void updateProjectPom(IProgressMonitor progressMonitor) throws ProcessorException {
        JavaProcessorUtilities.checkJavaProjectLib(getNeededLibraries());

        MavenPomManager pomManager = new MavenPomManager(this);
        pomManager.updateProjectDependencies(progressMonitor, this.getPomFile());
    }

    private void removeGeneratedJobs(IProgressMonitor progressMonitor) throws ProcessorException {
        ITalendProcessJavaProject talendProcessProject = getTalendJavaProject();
        IFolder srcFolder = talendProcessProject.getSrcFolder();
        IFolder testSrcFolder = talendProcessProject.getTestSrcFolder();
        IFolder sourceFolder = null;
        if (isTestJob) {
            sourceFolder = testSrcFolder;
        } else {
            sourceFolder = srcFolder;
        }
        try {
            String projectPackage = ProjectManager.getInstance().getCurrentProject().getTechnicalLabel().toLowerCase();
            List<String> currentJobNames = JobUtils.getRunningJobFolders(this);
            IFolder sourcesFilesFolder = sourceFolder.getFolder(projectPackage);
            boolean changed = false;
            IResource[] childrenRecs = sourcesFilesFolder.members();
            if (childrenRecs.length > 0) {
                for (IResource child : childrenRecs) {
                    if (!currentJobNames.contains(child.getName())) {
                        child.delete(true, progressMonitor);
                        changed = true;
                    }
                }
            }
            if (changed) {
                sourceFolder.refreshLocal(IResource.DEPTH_INFINITE, progressMonitor);
            }
        } catch (CoreException e) {
            throw new ProcessorException(e);
        }
    }

    @Override
    public void build() {
        final ITalendProcessJavaProject talendJavaProject = getTalendJavaProject();
        if (isStandardJob()) {
            try {
                updateProjectPom(null);
            } catch (ProcessorException e) {
                ExceptionHandler.process(e);
            }
        }

        talendJavaProject.buildModules(getGoals(), null);
        // try {
        //
        // IFolder jobSrcFolder = talendJavaProject.getProject().getFolder(this.getSrcCodePath().removeLastSegments(1));
        // if (jobSrcFolder.exists()) {
        // jobSrcFolder.refreshLocal(IResource.DEPTH_INFINITE, null);
        // }
        // if (isTestJob) {
        // talendJavaProject.getTestOutputFolder().refreshLocal(IResource.DEPTH_INFINITE, null);
        // } else {
        // talendJavaProject.getOutputFolder().refreshLocal(IResource.DEPTH_INFINITE, null);
        // }
        // } catch (CoreException e) {
        // ExceptionHandler.process(e);
        // }
    }

    protected String getGoals() {
        if (isTestJob) {
            return MavenConstants.GOAL_TEST_COMPILE;
        }
        return MavenConstants.GOAL_COMPILE;
    }

    private String[] getJobModules() {
        // find the children jobs for maven build
        Set<JobInfo> infos = getBuildChildrenJobs();
        JobInfo[] childrenJobs = infos.toArray(new JobInfo[0]);
        List<String> jobswithChildren = new ArrayList<String>();

        // add routines always.
        // if (!buildRoutinesOnce) { //RoutinesMavenInstallLoginTask
        // jobswithChildren.add(getRoutineModule());
        // }
        // src/main/java
        IPath srcRelativePath = this.getTalendJavaProject().getSrcFolder().getProjectRelativePath();
        String srcRootPath = srcRelativePath.toString();

        for (JobInfo child : childrenJobs) {
            ProcessItem processItem = child.getProcessItem();
            String childJobFolder = null;
            if (processItem != null) {
                childJobFolder = JavaResourcesHelper.getJobClassPackageFolder(processItem);
            } else {
                String projectFolderName = child.getProjectFolderName();
                childJobFolder = JavaResourcesHelper.getJobClassPackageFolder(projectFolderName, child.getJobName(),
                        child.getJobVersion());
            }
            jobswithChildren.add(srcRootPath + '/' + childJobFolder);
        }

        // the main job is last one.
        jobswithChildren.add(this.getSrcCodePath().removeLastSegments(1).toString());

        return jobswithChildren.toArray(new String[0]);
    }

    private String getRoutineModule() {
        // routine module
        IFolder routinesSrcFolder = this.getTalendJavaProject().getSrcFolder().getFolder(JavaUtils.JAVA_ROUTINES_DIRECTORY);
        String routineModule = routinesSrcFolder.getProjectRelativePath().toString();
        return routineModule;
    }

}
