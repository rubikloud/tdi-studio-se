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

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.InflaterInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.codec.binary.Base64InputStream;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.Message;
import org.eclipse.jdt.debug.core.IJavaBreakpoint;
import org.eclipse.jdt.debug.core.IJavaBreakpointListener;
import org.eclipse.jdt.debug.core.IJavaDebugTarget;
import org.eclipse.jdt.debug.core.IJavaLineBreakpoint;
import org.eclipse.jdt.debug.core.IJavaThread;
import org.eclipse.jdt.debug.core.IJavaType;
import org.eclipse.jdt.debug.core.JDIDebugModel;
import org.eclipse.jdt.internal.ui.JavaPlugin;
import org.eclipse.jdt.internal.ui.text.java.JavaFormattingStrategy;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.eclipse.jdt.ui.text.IJavaPartitions;
import org.eclipse.jdt.ui.text.JavaTextTools;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.DocumentRewriteSession;
import org.eclipse.jface.text.DocumentRewriteSessionType;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentExtension4;
import org.eclipse.jface.text.formatter.FormattingContext;
import org.eclipse.jface.text.formatter.FormattingContextProperties;
import org.eclipse.jface.text.formatter.IFormattingContext;
import org.eclipse.jface.text.formatter.MultiPassContentFormatter;
import org.eclipse.swt.widgets.Display;
import org.talend.commons.CommonsPlugin;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.commons.exception.SystemException;
import org.talend.commons.ui.runtime.exception.RuntimeExceptionHandler;
import org.talend.commons.utils.generation.JavaUtils;
import org.talend.commons.utils.io.FilesUtils;
import org.talend.commons.utils.resource.FileExtensions;
import org.talend.core.CorePlugin;
import org.talend.core.GlobalServiceRegister;
import org.talend.core.PluginChecker;
import org.talend.core.context.Context;
import org.talend.core.context.RepositoryContext;
import org.talend.core.model.general.Project;
import org.talend.core.model.process.ElementParameterParser;
import org.talend.core.model.process.IContext;
import org.talend.core.model.process.IElementParameter;
import org.talend.core.model.process.INode;
import org.talend.core.model.process.IProcess;
import org.talend.core.model.process.IProcess2;
import org.talend.core.model.process.JobInfo;
import org.talend.core.model.process.ProcessUtils;
import org.talend.core.model.properties.Item;
import org.talend.core.model.properties.ProcessItem;
import org.talend.core.model.properties.Property;
import org.talend.core.model.runprocess.IJavaProcessorStates;
import org.talend.core.model.runprocess.LastGenerationInfo;
import org.talend.core.model.utils.JavaResourcesHelper;
import org.talend.core.prefs.ITalendCorePrefConstants;
import org.talend.core.runtime.process.ITalendProcessJavaProject;
import org.talend.core.ui.services.IRulesProviderService;
import org.talend.designer.codegen.ICodeGenerator;
import org.talend.designer.codegen.ICodeGeneratorService;
import org.talend.designer.core.ISyntaxCheckableEditor;
import org.talend.designer.core.model.components.EParameterName;
import org.talend.designer.core.model.utils.emf.talendfile.ProcessType;
import org.talend.designer.core.ui.editor.CodeEditorFactory;
import org.talend.designer.core.ui.editor.nodes.Node;
import org.talend.designer.core.ui.editor.process.Process;
import org.talend.designer.runprocess.ItemCacheManager;
import org.talend.designer.runprocess.ProcessorException;
import org.talend.designer.runprocess.ProcessorUtilities;
import org.talend.designer.runprocess.RunProcessPlugin;
import org.talend.designer.runprocess.i18n.Messages;
import org.talend.designer.runprocess.prefs.RunProcessPrefsConstants;
import org.talend.repository.ProjectManager;
import org.talend.repository.utils.EsbConfigUtils;

/**
 * Creat the package folder for the java file, and put the generated file to the correct folder.
 * 
 * The creation for the java package should follow the pattern below:
 * 
 * 1)The name for the first grade folder should keep same with the T.O.S project name. 2)The folder name within the
 * project should be the job name.
 * 
 * <br/>
 * 
 * $Id: JavaProcessor.java 2007-1-22 上�?�10:53:24 yzhang $
 * 
 */
@SuppressWarnings("restriction")
public class JavaProcessor extends AbstractJavaProcessor implements IJavaBreakpointListener {

    /** The compiled job class path. */
    private IPath compiledCodePath;

    /** The compiled context file path. */
    private IPath compiledContextPath;

    /** Tells if filename is based on id or label of the process. */
    private final boolean filenameFromLabel;

    private IJavaProcessorStates states;

    private ISyntaxCheckableEditor checkableEditor;

    private String formatedCode;

    /**
     * Matchs placeholder in subprocess_header.javajet, it will be replaced by the size of method code.
     */
    private static final String SIZE_COMMENT = "?SIZE?"; //$NON-NLS-1$

    private static final String METHOD_END_COMMENT = "End of Function:"; //$NON-NLS-1$

    private static final String METHOD_START_COMMENT = "Start of Function:"; //$NON-NLS-1$

    protected Property property;

    private String exportAsOSGI;

    protected Set<JobInfo> buildChildrenJobs;

    private final ITalendProcessJavaProject talendJavaProject;

    /**
     * Set current status.
     * 
     * DOC yzhang Comment method "setStatus".
     * 
     * @param states
     */
    public void setStatus(IJavaProcessorStates states) {
        this.states = states;
    }

    /**
     * Constructs a new JavaProcessor.
     * 
     * @param process Process to be turned in Java code.
     * @param filenameFromLabel Tells if filename is based on id or label of the process.
     */
    public JavaProcessor(IProcess process, Property property, boolean filenameFromLabel) {
        super(process);
        this.process = process;
        this.property = property;
        this.talendJavaProject = JavaProcessorUtilities.getTalendJavaProject();

        if (property != null) {
            if (property.getItem() != null && property.getItem() instanceof ProcessItem) {
                final ProcessItem processItem = (ProcessItem) property.getItem();
                final ProcessType process2 = processItem.getProcess();
                if (process2 != null) {
                    // resolve the node
                    process2.getNode();
                }
            }
        }

        this.filenameFromLabel = filenameFromLabel;
        setProcessorStates(STATES_RUNTIME);
        if (checkableEditor == null && process instanceof IProcess2 && ((IProcess2) process).getEditor() != null) {
            checkableEditor = CodeEditorFactory.getInstance().getCodeEditor((IProcess2) process);
        }
    }

    @Override
    public ITalendProcessJavaProject getTalendJavaProject() {
        return this.talendJavaProject;
    }

    @Override
    public Set<JobInfo> getBuildChildrenJobs() {
        if (buildChildrenJobs == null) {
            buildChildrenJobs = new HashSet<JobInfo>();

            JobInfo lastMainJob = LastGenerationInfo.getInstance().getLastMainJob();
            Set<JobInfo> infos = null;
            if (lastMainJob == null && property != null) {
                infos = ProcessorUtilities.getChildrenJobInfo((ProcessItem) property.getItem());
            } else {
                infos = LastGenerationInfo.getInstance().getLastGeneratedjobs();
            }
            for (JobInfo jobInfo : infos) {
                buildChildrenJobs.add(jobInfo);
            }
        }
        return this.buildChildrenJobs;
    }

    /*
     * Initialization of the variable codePath and contextPath.
     * 
     * @see org.talend.designer.runprocess.IProcessor#initPaths(org.talend.core.model .process.IContext)
     */
    @Override
    public void initPaths(IContext c) throws ProcessorException {
        if (c.equals(this.context)) {
            return;
        }

        ITalendProcessJavaProject tProcessJavaProject = getTalendJavaProject();
        if (tProcessJavaProject == null) {
            throw new ProcessorException(Messages.getString("JavaProcessor.notFoundedProjectException")); //$NON-NLS-1$
        }
        this.project = tProcessJavaProject.getProject();
        createInternalPackage();

        initCodePath(c);
        this.context = c;
    }

    @Override
    public void initPath() throws ProcessorException {
        initCodePath(context);
    }

    protected boolean isStandardJob() {
        return property != null && property.getItem() != null && process instanceof IProcess2;
    }

    public void initCodePath(IContext c) throws ProcessorException {
        if (buildChildrenJobs != null) {
            buildChildrenJobs.clear();
        }

        ITalendProcessJavaProject tProcessJavaProject = getTalendJavaProject();
        if (tProcessJavaProject == null) {
            throw new ProcessorException(Messages.getString("JavaProcessor.notFoundedFolderException")); //$NON-NLS-1$
        }
        IProgressMonitor monitor = new NullProgressMonitor();

        boolean isTestJob = false;
        if (ProcessUtils.isTestContainer(process)) {
            isTestJob = true;
        }

        IFolder srcFolder = null;
        IFolder resourcesFolder = null;
        IFolder outputFolder = null;
        if (isTestJob) {
            srcFolder = tProcessJavaProject.getTestSrcFolder();
            resourcesFolder = tProcessJavaProject.getTestResourcesFolder();
            outputFolder = tProcessJavaProject.getTestOutputFolder();
        } else {
            srcFolder = tProcessJavaProject.getSrcFolder();
            resourcesFolder = tProcessJavaProject.getResourcesFolder();
            outputFolder = tProcessJavaProject.getOutputFolder();
        }

        /*
         * assum the job is "TestJob 0.1", project is "Test" .
         */
        String jobClassPackageFolder = null;
        String jobClassFilePath = null;
        // only for "standard" job
        if (isStandardJob()) {
            Item item = property.getItem();
            // test/testjob_0_1
            jobClassPackageFolder = JavaResourcesHelper.getJobClassPackageFolder(item, isTestJob);
            // test/testjob_0_1/TestJob.java
            jobClassFilePath = JavaResourcesHelper.getJobClassFilePath(item, filenameFromLabel, isTestJob);
            // test.testjob_0_1.TestJob
            this.mainClass = JavaResourcesHelper.getJobPackagedClass(item, filenameFromLabel, isTestJob);
        } else { // for shadow process, or preview process
            // test/shadowfileinputtodelimitedoutput_0_1, test/preview_data
            jobClassPackageFolder = JavaResourcesHelper.getProjectFolderName(property) + JavaUtils.PATH_SEPARATOR
                    + JavaResourcesHelper.getJobFolderName(process.getName(), process.getVersion());
            // test/shadowfileinputtodelimitedoutput_0_1/ShadowFileInputToDelimitedOutput.java,
            // test/preview_data/Preview_Data.java
            jobClassFilePath = jobClassPackageFolder + JavaUtils.PATH_SEPARATOR
                    + (filenameFromLabel ? JavaResourcesHelper.escapeFileName(process.getName()) : process.getId())
                    + JavaUtils.JAVA_EXTENSION;
            // test.shadowfileinputtodelimitedoutput_0_1.ShadowFileInputToDelimitedOutput,
            // test.preview_data.Preview_Data
            this.mainClass = new Path(jobClassFilePath).removeFileExtension().toString().replace('/', '.');
        }

        // create job packages, src/main/java/test/testjob_0_1
        // or test job packages, src/main/java/test/testjob_0_1/testjunitjob_0_1
        tProcessJavaProject.createSubFolder(monitor, srcFolder, jobClassPackageFolder);
        // src/main/java/test/testjob_0_1/TestJob.java
        // or src/main/java/test/testjob_0_1/testjunitjob_0_1/TestjunitJob.java
        this.codePath = srcFolder.getProjectRelativePath().append(jobClassFilePath);

        // target/classes/test/testjob_0_1
        // or target/test-classes/test/testjob_0_1/testjunitjob_0_1
        IFolder jobClassFolder = outputFolder.getFolder(jobClassPackageFolder);
        // TestJob
        String jobName = new Path(jobClassFilePath).removeFileExtension().lastSegment();
        // target/classes/test/testjob_0_1/TestJob
        // or target/test-classes/test/testjob_0_1/testjunitjob_0_1/TestjunitJob
        this.compiledCodePath = jobClassFolder.getProjectRelativePath().append(jobName);

        if (isTestJob) {
            this.dataFilePath = new Path(jobClassPackageFolder).append(JavaUtils.JAVA_DATAS_DIRECTORY);
        }

        /*
         * for context.
         */
        // test/testjob_0_1/contexts
        // or test/testjob_0_1/testjunitjob_0_1/contexts
        IPath jobContextFolderPath = new Path(jobClassPackageFolder).append(JavaUtils.JAVA_CONTEXTS_DIRECTORY);
        // src/main/resources/test/testjob_0_1/contexts
        // or src/test/resources/test/testjob_0_1/testjunitjob_0_1/contexts
        tProcessJavaProject.createSubFolder(monitor, resourcesFolder, jobContextFolderPath.toString());
        // for example, Default
        String contextFileName = JavaResourcesHelper.getJobContextFileName(c);
        // test/testjob_0_1/contexts/Default.properties
        // or test/testjob_0_1/testjunitjob_0_1/contexts/Default.properties
        IPath jobContextPath = jobContextFolderPath.append(contextFileName);
        // src/main/resources/test/testjob_0_1/contexts/Default.properties
        // or src/test/resources/test/testjob_0_1/testjunitjob_0_1/contexts/Default.properties
        this.contextPath = resourcesFolder.getFile(jobContextPath).getProjectRelativePath();
        // target/classes/test/testjob_0_1/contexts/Default.properties
        // or target/test-classes/test/testjob_0_1/testjunitjob_0_1/contexts/Default.properties
        this.compiledContextPath = outputFolder.getFile(jobContextPath).getProjectRelativePath();
    }

    /**
     * DOC chuang Comment method "computeMethodSizeIfNeeded".
     * 
     * @param processCode
     * @return
     */
    private String computeMethodSizeIfNeeded(String processCode) {
        // must match TalendDesignerPrefConstants.DISPLAY_METHOD_SIZE
        boolean displayMethodSize = Boolean.parseBoolean(CorePlugin.getDefault().getDesignerCoreService()
                .getPreferenceStore("displayMethodSize")); //$NON-NLS-1$
        if (displayMethodSize) {
            StringBuffer code = new StringBuffer(processCode);
            int fromIndex = 0;
            while (fromIndex != -1 && fromIndex < code.length()) {
                int methodStartPos = code.indexOf(METHOD_START_COMMENT, fromIndex);
                if (methodStartPos < 0) {
                    break;
                }
                int sizeCommentPos = code.indexOf(SIZE_COMMENT, fromIndex);

                // move ahead to the start position of source code
                methodStartPos = code.indexOf("*/", sizeCommentPos) + 2; //$NON-NLS-1$

                int methodEndPos = code.indexOf(METHOD_END_COMMENT, fromIndex);
                if (methodEndPos < 0) {
                    break;
                }
                // start position for next search
                fromIndex = methodEndPos + METHOD_END_COMMENT.length();
                // go back to the end position of source code
                methodEndPos = code.lastIndexOf("/*", methodEndPos); //$NON-NLS-1$
                int size = methodEndPos - methodStartPos;
                code.replace(sizeCommentPos, sizeCommentPos + SIZE_COMMENT.length(), String.valueOf(size));

            }
            return code.toString();
        } else {
            return processCode;
        }
    }

    /*
     * Append the generated java code form context into java file wihtin the project. If the file not existed new one
     * will be created.
     * 
     * @see org.talend.designer.runprocess.IProcessor#generateCode(org.talend.core .model.process.IContext, boolean,
     * boolean, boolean, boolean)
     */
    @Override
    public void generateCode(boolean statistics, boolean trace, boolean javaProperties, boolean exportAsOSGI)
            throws ProcessorException {
        this.exportAsOSGI = exportAsOSGI ? Boolean.TRUE.toString() : Boolean.FALSE.toString();
        generateCode(statistics, trace, javaProperties);
    }

    /*
     * Append the generated java code form context into java file wihtin the project. If the file not existed new one
     * will be created.
     * 
     * @see org.talend.designer.runprocess.IProcessor#generateCode(org.talend.core .model.process.IContext, boolean,
     * boolean, boolean)
     */
    @Override
    public void generateCode(boolean statistics, boolean trace, boolean javaProperties) throws ProcessorException {
        super.generateCode(statistics, trace, javaProperties);
        try {
            // hywang modified for 6484
            String currentJavaProject = ProjectManager.getInstance().getProject(property).getTechnicalLabel();

            ICodeGenerator codeGen;
            ICodeGeneratorService service = RunProcessPlugin.getDefault().getCodeGeneratorService();
            if (javaProperties) {
                String javaInterpreter = ""; //$NON-NLS-1$
                String javaLib = ""; //$NON-NLS-1$
                String javaContext = getContextPath().toPortableString();

                codeGen = service.createCodeGenerator(process, statistics, trace, javaInterpreter, javaLib, javaContext,
                        currentJavaProject, exportAsOSGI);
            } else {
                codeGen = service.createCodeGenerator(process, statistics, trace);
            }
            // set the selected context. if don't find, will keep default
            if (!process.getContextManager().getDefaultContext().getName().equals(context.getName())) {
                boolean found = false;
                for (IContext c : process.getContextManager().getListContext()) {
                    if (c.getName().equals(context.getName())) {
                        found = true;
                    }
                }
                if (found) {
                    codeGen.setContextName(context.getName());
                }
            }
            String processCode = ""; //$NON-NLS-1$
            try {
                processCode = codeGen.generateProcessCode();

                if (PluginChecker.isRulesPluginLoaded()) {
                    IRulesProviderService rulesService = (IRulesProviderService) GlobalServiceRegister.getDefault().getService(
                            IRulesProviderService.class);
                    if (rulesService != null) {
                        boolean useGenerateRuleFiles = false;
                        List<? extends INode> allNodes = this.process.getGeneratingNodes();
                        for (int i = 0; i < allNodes.size(); i++) {
                            if (allNodes.get(i) instanceof INode) {
                                INode node = allNodes.get(i);
                                if (rulesService.isRuleComponent(node)
                                        && !node.getElementParameter(EParameterName.PROPERTY_TYPE.getName()).getValue()
                                                .toString().equals("BUILT_IN")) { //$NON-NLS-1$
                                    useGenerateRuleFiles = true;
                                    break;
                                }
                            }
                        }
                        if (useGenerateRuleFiles && rulesService != null && currentJavaProject != null) {
                            rulesService.generateFinalRuleFiles(currentJavaProject, this.process);
                        }
                    }
                }

            } catch (SystemException e) {
                throw new ProcessorException(Messages.getString("Processor.generationFailed"), e); //$NON-NLS-1$
            } catch (IOException e) {
                ExceptionHandler.process(e);
            }
            // Generating files
            IFile codeFile = this.project.getFile(this.codePath);

            // format the code before save the file.
            final String toFormat = processCode;
            // fix for 21320
            final Job job = new Job("t") { //$NON-NLS-1$

                @Override
                protected IStatus run(IProgressMonitor monitor) {
                    monitor.beginTask("Format code", IProgressMonitor.UNKNOWN); //$NON-NLS-1$
                    formatedCode = formatCode(toFormat);
                    monitor.done();
                    return Status.OK_STATUS;
                }
            };
            long time1 = System.currentTimeMillis();

            job.setSystem(true);
            job.schedule();
            boolean f = true;
            while (f) {
                long time2 = System.currentTimeMillis();
                if (time2 - time1 > 30000) {
                    if (job.getResult() == null || !job.getResult().isOK()) {
                        f = false;
                        job.done(Status.OK_STATUS);
                        job.cancel();
                    } else {
                        processCode = formatedCode;
                        f = false;
                    }
                } else {
                    if (job.getState() != Job.RUNNING) {
                        if (job.getResult() != null && job.getResult().isOK()) {
                            processCode = formatedCode;
                            f = false;
                        }
                    }
                }

            }
            formatedCode = null;

            // see feature 4610:option to see byte length of each code method
            processCode = computeMethodSizeIfNeeded(processCode);
            InputStream codeStream = new ByteArrayInputStream(processCode.getBytes());

            if (!codeFile.exists()) {
                // see bug 0003592, detele file with different case in windows
                deleteFileIfExisted(codeFile);
                IFolder parentFolder = (IFolder) codeFile.getParent();
                if (!parentFolder.exists()) {
                    parentFolder.create(true, true, null);
                }
                codeFile.create(codeStream, true, null);
            } else {
                codeFile.setContents(codeStream, true, false, null);
            }

            processCode = null;

            // updateContextCode(codeGen);

            codeFile.getProject().deleteMarkers("org.eclipse.jdt.debug.javaLineBreakpointMarker", true, IResource.DEPTH_INFINITE); //$NON-NLS-1$

            List<INode> breakpointNodes = CorePlugin.getContext().getBreakpointNodes(process);
            if (!breakpointNodes.isEmpty()) {
                String[] nodeNames = new String[breakpointNodes.size()];
                int pos = 0;
                String nodeName;
                for (INode node : breakpointNodes) {
                    nodeName = node.getUniqueName();
                    if (node.getComponent().getMultipleComponentManagers().size() > 0) {
                        nodeName += "_" + node.getComponent().getMultipleComponentManagers().get(0).getInput().getName(); //$NON-NLS-1$
                    }
                    nodeNames[pos++] = "[" + nodeName + " main ] start"; //$NON-NLS-1$ //$NON-NLS-2$
                }
                int[] lineNumbers = getLineNumbers(codeFile, nodeNames);
                setBreakpoints(codeFile, getMainClass(), lineNumbers);
            }
        } catch (CoreException e1) {
            if (e1.getStatus() != null && e1.getStatus().getException() != null) {
                ExceptionHandler.process(e1.getStatus().getException());
            }
            throw new ProcessorException(Messages.getString("Processor.tempFailed"), e1); //$NON-NLS-1$
        }
    }

    /**
     * DOC nrousseau Comment method "formatCode".
     * 
     * @param processCode
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private String formatCode(String processCode) {
        IDocument document = new Document(processCode);

        // we cannot make calls to Ui in headless mode
        if (CommonsPlugin.isHeadless()) {
            return document.get();
        }

        JavaTextTools tools = JavaPlugin.getDefault().getJavaTextTools();
        tools.setupJavaDocumentPartitioner(document, IJavaPartitions.JAVA_PARTITIONING);

        IFormattingContext context = null;
        DocumentRewriteSession rewriteSession = null;

        IDocumentExtension4 extension = (IDocumentExtension4) document;
        DocumentRewriteSessionType type = DocumentRewriteSessionType.SEQUENTIAL;
        rewriteSession = extension.startRewriteSession(type);

        try {

            final String rememberedContents = document.get();

            try {
                final MultiPassContentFormatter formatter = new MultiPassContentFormatter(IJavaPartitions.JAVA_PARTITIONING,
                        IDocument.DEFAULT_CONTENT_TYPE);

                formatter.setMasterStrategy(new JavaFormattingStrategy());
                // formatter.setSlaveStrategy(new CommentFormattingStrategy(),
                // IJavaPartitions.JAVA_DOC);
                // formatter.setSlaveStrategy(new CommentFormattingStrategy(),
                // IJavaPartitions.JAVA_SINGLE_LINE_COMMENT);
                // formatter.setSlaveStrategy(new CommentFormattingStrategy(),
                // IJavaPartitions.JAVA_MULTI_LINE_COMMENT);

                context = new FormattingContext();
                context.setProperty(FormattingContextProperties.CONTEXT_DOCUMENT, Boolean.TRUE);
                Map map = new HashMap(JavaCore.getOptions());
                context.setProperty(FormattingContextProperties.CONTEXT_PREFERENCES, map);
                formatter.format(document, context);
            } catch (RuntimeException x) {
                // fire wall for
                // https://bugs.eclipse.org/bugs/show_bug.cgi?id=47472
                // if something went wrong we undo the changes we just did
                // TODO to be removed after 3.0 M8
                document.set(rememberedContents);
                throw x;
            }

        } finally {
            extension.stopRewriteSession(rewriteSession);
            if (context != null) {
                context.dispose();
            }
        }
        return document.get();
    }

    @Override
    public void setSyntaxCheckableEditor(ISyntaxCheckableEditor checkableEditor) {
        this.checkableEditor = checkableEditor;
    }

    @Override
    public void syntaxCheck() {
        if (checkableEditor != null) {
            checkableEditor.validateSyntax();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.runprocess.IProcessor#getCodeContext()
     */
    @Override
    public String getCodeContext() {
        return getCodeProject().getLocation().append(getContextPath()).removeLastSegments(1).toPortableString();
    }

    private String escapeFilename(final String filename) {
        return filename != null ? filename.replace(" ", "") : ""; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.runprocess.IProcessor#getCodePath()
     */
    @Override
    public IPath getCodePath() {
        return this.states.getCodePath();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.runprocess.IProcessor#getContextPath()
     */
    @Override
    public IPath getContextPath() {
        return this.states.getContextPath();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.runprocess.IProcessor#getDataSetPath()
     */
    @Override
    public IPath getDataSetPath() {
        return this.states.getDataSetPath();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.runprocess.IProcessor#getCodeProject()
     */
    @Override
    public IProject getCodeProject() {
        return this.project.getProject();
    }

    /**
     * Find line numbers of the beginning of the code of process nodes.
     * 
     * @param file Code file where we are searching node's code.
     * @param nodes List of nodes searched.
     * @return Line numbers where code of nodes appears.
     * @throws CoreException Search failed.
     */
    private static int[] getLineNumbers(IFile file, String[] nodes) throws CoreException {
        List<Integer> lineNumbers = new ArrayList<Integer>();

        // List of code's lines searched in the file
        List<String> searchedLines = new ArrayList<String>();
        for (String node : nodes) {
            searchedLines.add(node);
        }

        LineNumberReader lineReader = new LineNumberReader(new InputStreamReader(file.getContents()));
        try {
            String line = lineReader.readLine();
            while (!searchedLines.isEmpty() && line != null) {
                boolean nodeFound = false;
                for (Iterator<String> i = searchedLines.iterator(); !nodeFound && i.hasNext();) {
                    String nodeMain = i.next();
                    if (line.indexOf(nodeMain) != -1) {
                        nodeFound = true;
                        i.remove();

                        // Search the first valid code line
                        boolean lineCodeFound = false;
                        line = lineReader.readLine();
                        while (line != null && !lineCodeFound) {
                            if (isCodeLine(line)) {
                                lineCodeFound = true;
                                lineNumbers.add(new Integer(lineReader.getLineNumber() + 1));
                            }
                            line = lineReader.readLine();
                        }
                    }
                }
                line = lineReader.readLine();
            }
        } catch (IOException ioe) {
            IStatus status = new Status(IStatus.ERROR, "", IStatus.OK, "Source code read failure.", ioe); //$NON-NLS-1$ //$NON-NLS-2$
            throw new CoreException(status);
        }

        int[] res = new int[lineNumbers.size()];
        int pos = 0;
        for (Integer i : lineNumbers) {
            res[pos++] = i.intValue();
        }
        return res;
    }

    /**
     * Return line number where stands specific node in code generated.
     * 
     * @param nodeName
     */
    @Override
    public int getLineNumber(String nodeName) {
        IFile codeFile = this.project.getProject().getFile(this.codePath);
        int[] lineNumbers = new int[] { 0 };
        try {
            lineNumbers = JavaProcessor.getLineNumbers(codeFile, new String[] { nodeName });
        } catch (CoreException e) {
            lineNumbers = new int[] { 0 };
            // e.printStackTrace();
            ExceptionHandler.process(e);
        }
        if (lineNumbers.length > 0) {
            return lineNumbers[0];
        } else {
            return 0;
        }
    }

    /**
     * Tells if a line is a line of perl code, not an empty or comment line.
     * 
     * @param line The tested line of code.
     * @return true if the line is a line of code.
     */
    private static boolean isCodeLine(String line) {
        String trimed = line.trim();
        return trimed.length() > 0 && trimed.charAt(0) != '#';
    }

    /**
     * Set java breakpoints in a java file.
     * 
     * @param srcFile Java file in wich breakpoints are added.
     * @param lineNumbers Line numbers in the source file where breakpoints are installed.
     * @throws CoreException Breakpoint addition failed.
     */
    private static void setBreakpoints(IFile codeFile, String typeName, int[] lines) throws CoreException {
        final String javaLineBrekPointMarker = "org.eclipse.jdt.debug.javaLineBreakpointMarker"; //$NON-NLS-1$
        codeFile.deleteMarkers(javaLineBrekPointMarker, true, IResource.DEPTH_ZERO);

        for (int line : lines) {
            JDIDebugModel.createLineBreakpoint(codeFile, typeName, line + 1, -1, -1, 0, true, null);
        }
    }

    private void createInternalPackage() {

        ITalendProcessJavaProject tProcessJvaProject = getTalendJavaProject();
        if (tProcessJvaProject == null) {
            return;
        }
        tProcessJvaProject.createSubFolder(null, tProcessJvaProject.getSrcFolder(), JavaUtils.JAVA_INTERNAL_DIRECTORY);
    }

    /*
     * Get the interpreter of Java.
     * 
     * @see org.talend.designer.runprocess.IProcessor#getInterpreter()
     */
    @Override
    public String getInterpreter() throws ProcessorException {
        // if the interpreter has been set to a specific one (not standard),
        // then this value won't be null
        String interpreter = super.getInterpreter();
        if (interpreter != null) {
            return interpreter;
        }
        return getDefaultInterpreter();

    }

    public static String getDefaultInterpreter() throws ProcessorException {
        IPreferenceStore prefStore = CorePlugin.getDefault().getPreferenceStore();
        String javaInterpreter = prefStore.getString(ITalendCorePrefConstants.JAVA_INTERPRETER);

        if (javaInterpreter == null || javaInterpreter.length() == 0) {
            throw new ProcessorException(Messages.getString("Processor.configureJava")); //$NON-NLS-1$
        }
        Path path = new Path(javaInterpreter);
        javaInterpreter = path.toPortableString();
        return javaInterpreter;
    }

    @Override
    public String getCodeLocation() throws ProcessorException {
        // if the routine path has been set to a specific one (not standard),
        // then this value won't be null
        String codeLocation = super.getCodeLocation();
        if (codeLocation != null) {
            return codeLocation;
        }
        return this.getCodeProject().getLocation().toPortableString();
    }

    /**
     * Getter for compliedCodePath.
     * 
     * @return the compliedCodePath
     */
    public IPath getCompiledCodePath() {
        return this.compiledCodePath;
    }

    /**
     * Getter for compiledContextPath.
     * 
     * @return the compiledContextPath
     */
    public IPath getCompiledContextPath() {
        return this.compiledContextPath;
    }

    /**
     * Getter for codePath.
     * 
     * @return the codePath
     */
    public IPath getSrcCodePath() {
        return this.codePath;
    }

    /**
     * Getter for srcContextPath.
     * 
     * @return the srcContextPath
     */
    public IPath getSrcContextPath() {
        return this.contextPath;
    }

    /**
     * Getter for SrcDataSetPath.
     * 
     * @return the SrcDataSetPath
     */
    public IPath getSrcDataSetPath() {
        return this.dataFilePath;
    }

    @Override
    public String[] getCommandLine() throws ProcessorException {
        // java -cp libdirectory/*.jar;project_path classname;

        List<String> tmpParams = new ArrayList<String>();
        tmpParams.add(getCommand());

        // proxy params
        String[] proxyParameters = getProxyParameters();
        if (proxyParameters != null && proxyParameters.length > 0) {
            for (String str : proxyParameters) {
                tmpParams.add(str);
            }
        }
        // classpath
        tmpParams.add(JavaUtils.JAVA_CP);
        tmpParams.add(getLibsClasspath()); // libs

        tmpParams.add(getMainClass()); // main class

        //
        String[] additionCommandStrings = getAdditionCommandStrings();
        if (additionCommandStrings != null) {
            tmpParams.addAll(Arrays.asList(additionCommandStrings));
        }

        boolean exportingJob = ProcessorUtilities.isExportConfig();
        // vm args
        String[] cmd2 = addVMArguments(tmpParams.toArray(new String[0]), exportingJob);

        if (exportingJob) {
            // add bat/sh header lines.
            List<String> list = new ArrayList<String>();
            if (Platform.OS_WIN32.equals(getTargetPlatform())) {
                list.add("%~d0\r\n"); //$NON-NLS-1$
                list.add("cd %~dp0\r\n"); //$NON-NLS-1$
            } else {
                list.add("cd `dirname $0`\n"); //$NON-NLS-1$ 
                list.add("ROOT_PATH=`pwd`\n"); //$NON-NLS-1$ 
            }
            list.addAll(Arrays.asList(cmd2));

            return list.toArray(new String[0]);
        } else {
            return cmd2;
        }
    }

    protected String getCommand() {
        // init java interpreter
        String command;
        try {
            command = getInterpreter();
        } catch (ProcessorException e1) {
            command = "java"; //$NON-NLS-1$
        }
        return new Path(command).toPortableString();
    }

    protected String getLibPrefixPath(boolean withSep) {
        String prefixPath = "";//$NON-NLS-1$
        if (ProcessorUtilities.isExportConfig() && !Platform.OS_WIN32.equals(getTargetPlatform())) {
            prefixPath = "$ROOT_PATH";//$NON-NLS-1$
            if (withSep) {
                prefixPath += JavaUtils.PATH_SEPARATOR;
            }
        }
        return prefixPath;
    }

    protected String getLibsClasspath() throws ProcessorException {
        final boolean exportingJob = ProcessorUtilities.isExportConfig();
        final String classPathSeparator = extractClassPathSeparator();

        final String basePathClasspath = getBasePathClasspath();
        // for -cp libs str
        final String neededModulesJarStr = getNeededModulesJarStr();
        String libsStr = basePathClasspath + classPathSeparator + neededModulesJarStr.toString();
        if (exportingJob) {
            libsStr += classPathSeparator + getExportJarsStr();
        } else {
            File libDir = JavaProcessorUtilities.getJavaProjectLibFolder();
            String libFolder = new Path(libDir.getAbsolutePath()).toPortableString();
            libsStr += classPathSeparator + libFolder;
        }

        return libsStr;
    }

    protected String getBasePathClasspath() throws ProcessorException {
        final boolean exportingJob = ProcessorUtilities.isExportConfig();
        final String classPathSeparator = extractClassPathSeparator();

        String outputPath;
        if (exportingJob) {
            outputPath = getCodeLocation(); // it have contained current path "."
            if (outputPath != null) {
                outputPath = outputPath.replace(ProcessorUtilities.TEMP_JAVA_CLASSPATH_SEPARATOR, classPathSeparator);
                if (outputPath.endsWith(classPathSeparator)) { // remove the seperator
                    outputPath = outputPath.substring(0, outputPath.length() - 1);
                }

                if (!Platform.OS_WIN32.equals(getTargetPlatform())) {
                    outputPath = getLibPrefixPath(false) + classPathSeparator + outputPath;

                    String libraryPath = ProcessorUtilities.getLibraryPath();
                    if (libraryPath != null) {
                        String unixRootPath = getLibPrefixPath(true);
                        outputPath = outputPath.replace(libraryPath, unixRootPath + libraryPath);
                    }
                }
            }
        } else {
            ITalendProcessJavaProject tProcessJvaProject = this.getTalendJavaProject();
            IFolder classesFolder = tProcessJvaProject.getOutputFolder();
            outputPath = classesFolder.getLocation().toPortableString();
            outputPath += classPathSeparator + '.'; // add current path
        }

        return outputPath;
    }

    protected String getNeededModulesJarStr() {
        final boolean exportingJob = ProcessorUtilities.isExportConfig();
        final String classPathSeparator = extractClassPathSeparator();
        final String libPrefixPath = getLibPrefixPath(true);
        final File libDir = JavaProcessorUtilities.getJavaProjectLibFolder();

        Set<String> neededLibraries = getNeededLibraries();
        JavaProcessorUtilities.checkJavaProjectLib(neededLibraries);

        File[] jarFiles = libDir.listFiles(FilesUtils.getAcceptJARFilesFilter());

        StringBuffer libPath = new StringBuffer();
        if (jarFiles != null && jarFiles.length > 0) {
            for (File jarFile : jarFiles) {
                if (jarFile.isFile() && neededLibraries.contains(jarFile.getName())) {
                    libPath.append(libPrefixPath);
                    String singleLibPath = new Path(jarFile.getAbsolutePath()).toPortableString();
                    if (exportingJob) {
                        singleLibPath = singleLibPath.replace(new Path(libDir.getAbsolutePath()).toPortableString(),
                                getBaseLibPath());
                    }
                    libPath.append(singleLibPath).append(classPathSeparator);
                }
            }
        }
        final int lastSep = libPath.length() - 1;
        if (classPathSeparator.equals(String.valueOf(libPath.charAt(lastSep)))) {
            libPath.deleteCharAt(lastSep);
        }
        return libPath.toString();
    }

    protected String getBaseLibPath() {
        return "../" + JavaUtils.JAVA_LIB_DIRECTORY; //$NON-NLS-1$
    }

    protected String getExportJarsStr() {
        final String libPrefixPath = getLibPrefixPath(true);
        final String classPathSeparator = extractClassPathSeparator();

        String jarName = JavaResourcesHelper.getJobFolderName(process.getName(), process.getVersion());
        String exportJar = libPrefixPath + jarName + FileExtensions.JAR_FILE_SUFFIX;

        Set<JobInfo> infos = getBuildChildrenJobs();
        for (JobInfo jobInfo : infos) {
            String childJarName = JavaResourcesHelper.getJobFolderName(jobInfo.getJobName(), jobInfo.getJobVersion());
            exportJar += classPathSeparator + libPrefixPath + childJarName + FileExtensions.JAR_FILE_SUFFIX;
        }
        return exportJar;

    }

    @Override
    public Set<String> getNeededLibraries() {
        Set<String> neededLibraries = JavaProcessorUtilities.getNeededLibrariesForProcess(process);
        boolean isLog4jEnabled = Boolean.parseBoolean(ElementParameterParser.getValue(process, "__LOG4J_ACTIVATE__")); //$NON-NLS-1$
        if (isLog4jEnabled) {
            JavaProcessorUtilities.addLog4jToJarList(neededLibraries);
        }
        return neededLibraries;
    }

    protected String[] getAdditionCommandStrings() {
        return new String[0];
    }

    protected String getLibFolderInWorkingDir() {
        return JavaProcessorUtilities.getJavaProjectLibFolder().getAbsolutePath();
    }

    protected String extractClassPathSeparator() {
        boolean win32 = false;
        String classPathSeparator;
        win32 = Platform.OS_WIN32.equals(getTargetPlatform());
        if (win32) {
            classPathSeparator = ";"; //$NON-NLS-1$
        } else {
            classPathSeparator = ":"; //$NON-NLS-1$
        }
        return classPathSeparator;
    }

    protected String[] addVMArguments(String[] strings, boolean exportingJob) {
        String string = null;
        if (this.process != null) {
            IElementParameter param = this.process.getElementParameter(EParameterName.JOB_RUN_VM_ARGUMENTS_OPTION.getName());
            if (param != null && param.getValue() instanceof Boolean && (Boolean) param.getValue()) { // checked
                param = this.process.getElementParameter(EParameterName.JOB_RUN_VM_ARGUMENTS.getName());
                if (param != null) {
                    string = (String) param.getValue();
                }
            }
        }
        // if not check or the value is empty, should use preference
        if (string == null || "".equals(string)) { //$NON-NLS-1$
            string = RunProcessPlugin.getDefault().getPreferenceStore().getString(RunProcessPrefsConstants.VMARGUMENTS);
        }
        String replaceAll = string.trim();
        String[] vmargs = replaceAll.split(" "); //$NON-NLS-1$
        /* check parameter won't happened on exportingJob */
        if (!exportingJob) {
            String fileEncoding = System.getProperty("file.encoding"); //$NON-NLS-1$
            String encodingFromIni = "-Dfile.encoding=" + fileEncoding; //$NON-NLS-1$
            List<String> asList = convertArgsToList(vmargs);
            boolean encodingSetInjob = false;
            for (String arg : asList) {
                if (arg.startsWith("-Dfile.encoding") && fileEncoding != null) { //$NON-NLS-1$
                    /* if user has set the encoding on .ini file,should use this when exetucte job */
                    arg = encodingFromIni;
                    encodingSetInjob = true;
                }
            }
            if (!encodingSetInjob) {
                asList.add(encodingFromIni);
                vmargs = asList.toArray(new String[0]);
            }
        }
        if (vmargs != null && vmargs.length > 0) {
            String[] lines = new String[strings.length + vmargs.length];
            System.arraycopy(strings, 0, lines, 0, 1);
            System.arraycopy(vmargs, 0, lines, 1, vmargs.length);
            System.arraycopy(strings, 1, lines, vmargs.length + 1, strings.length - 1);
            return lines;
        }
        return strings; // old
    }

    private List<String> convertArgsToList(String[] args) {
        List<String> toReturn = new ArrayList<String>();
        for (String arg : args) {
            toReturn.add(arg);
        }
        return toReturn;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.runprocess.IProcessor#getProcessorType()
     */
    @Override
    public String getProcessorType() {
        return JavaUtils.PROCESSOR_TYPE;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.runprocess.IProcessor#getProcessorStates()
     */
    @Override
    public void setProcessorStates(int states) {
        if (states == STATES_RUNTIME) {
            new JavaProcessorRuntimeStates(this);
        } else if (states == STATES_EDIT) {
            new JavaProcessorEditStates(this);
        }

    }

    /*
     * Get current class name, and it imported package structure.
     * 
     * @see org.talend.designer.runprocess.IProcessor#getTypeName()
     */
    @Override
    public String getTypeName() {
        return this.getMainClass();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.runprocess.IProcessor#saveLaunchConfiguration()
     */
    @Override
    public Object saveLaunchConfiguration() throws CoreException {

        /*
         * When launch debug progress, just share all libraries between farther job and child jobs
         */
        // computeLibrariesPath(this.getProcess().getNeededLibraries(true));

        ILaunchConfiguration config = null;
        ILaunchManager launchManager = DebugPlugin.getDefault().getLaunchManager();
        String projectName = this.getCodeProject().getName();
        ILaunchConfigurationType type = launchManager
                .getLaunchConfigurationType(IJavaLaunchConfigurationConstants.ID_JAVA_APPLICATION);
        if (type != null) {
            ILaunchConfigurationWorkingCopy wc = type.newInstance(null,
                    launchManager.generateUniqueLaunchConfigurationNameFrom(this.getCodePath().lastSegment()));
            wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME, projectName);
            wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_MAIN_TYPE_NAME, this.getMainClass());
            wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_STOP_IN_MAIN, true);
            wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_PROGRAM_ARGUMENTS, CTX_ARG + context.getName());
            config = wc.doSave();
        }
        return config;
    }

    // generate the ILaunchConfiguration with the parameter string.
    @Override
    public Object saveLaunchConfigurationWithParam(String parameterStr) throws CoreException {

        /*
         * When launch debug progress, just share all libraries between farther job and child jobs
         */
        // computeLibrariesPath(this.getProcess().getNeededLibraries(true));

        ILaunchConfiguration config = null;
        ILaunchManager launchManager = DebugPlugin.getDefault().getLaunchManager();
        String projectName = this.getCodeProject().getName();
        ILaunchConfigurationType type = launchManager
                .getLaunchConfigurationType(IJavaLaunchConfigurationConstants.ID_JAVA_APPLICATION);
        if (type != null) {
            ILaunchConfigurationWorkingCopy wc = type.newInstance(null,
                    launchManager.generateUniqueLaunchConfigurationNameFrom(this.getCodePath().lastSegment()));
            wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME, projectName);
            wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_MAIN_TYPE_NAME, this.getMainClass());
            wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_STOP_IN_MAIN, true);
            wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_PROGRAM_ARGUMENTS, CTX_ARG + context.getName() + parameterStr);
            config = wc.doSave();
        }
        return config;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.runprocess.Processor#generateContextCode()
     */
    @Override
    public void generateContextCode() throws ProcessorException {
        RepositoryContext repositoryContext = (RepositoryContext) CorePlugin.getContext().getProperty(
                Context.REPOSITORY_CONTEXT_KEY);
        Project project = repositoryContext.getProject();

        ICodeGenerator codeGen;
        ICodeGeneratorService service = RunProcessPlugin.getDefault().getCodeGeneratorService();
        String javaInterpreter = ""; //$NON-NLS-1$
        String javaLib = ""; //$NON-NLS-1$
        String currentJavaProject = project.getTechnicalLabel();
        String javaContext = getContextPath().toPortableString();

        codeGen = service.createCodeGenerator(process, false, false, javaInterpreter, javaLib, javaContext, currentJavaProject);

        updateContextCode(codeGen);
    }

    /*
     * (non-Javadoc) generate ESB files on classpath for jobs with ESB components
     */
    @Override
    public void generateEsbFiles() throws ProcessorException {
        List<? extends INode> graphicalNodes = process.getGraphicalNodes(); // process.getGeneratingNodes();

        try {
            IPath jobPackagePath = this.codePath.removeLastSegments(1);
            IFolder jobPackageFolder = this.project.getFolder(jobPackagePath);
            IFolder wsdlsPackageFolder = jobPackageFolder.getFolder("wsdl"); //$NON-NLS-1$
            if (wsdlsPackageFolder.exists()) {
                wsdlsPackageFolder.delete(true, null);
            }

            for (INode node : graphicalNodes) {
                if ("tESBConsumer".equals(node.getComponent().getName()) && node.isActivate()) { //$NON-NLS-1$
                    // retrieve WSDL content (compressed-n-encoded) -> zip-content.-> wsdls.(first named main.wsdl)
                    String wsdlContent = (String) node.getPropertyValue("WSDL_CONTENT"); //$NON-NLS-1$
                    String uniqueName = node.getUniqueName();
                    if (null != uniqueName && null != wsdlContent && !wsdlContent.trim().isEmpty()) {

                        // configure decoding and uncompressing
                        InputStream wsdlStream = new BufferedInputStream(new InflaterInputStream(new Base64InputStream(
                                new ByteArrayInputStream(wsdlContent.getBytes()))));

                        if (!wsdlsPackageFolder.exists()) {
                            wsdlsPackageFolder.create(true, true, null);
                        }
                        // generate WSDL file
                        if (checkIsZipStream(wsdlStream)) {

                            ZipInputStream zipIn = new ZipInputStream(wsdlStream);
                            ZipEntry zipEntry = null;

                            while ((zipEntry = zipIn.getNextEntry()) != null) {
                                String outputName = zipEntry.getName();
                                if ("main.wsdl".equals(outputName)) { //$NON-NLS-1$
                                    outputName = uniqueName + ".wsdl"; //$NON-NLS-1$
                                }
                                IFile wsdlFile = wsdlsPackageFolder.getFile(outputName);
                                if (!wsdlFile.exists()) {
                                    // cause create file will do a close. add a warp to ignore close.
                                    InputStream unCloseIn = new FilterInputStream(zipIn) {

                                        @Override
                                        public void close() throws IOException {
                                        };
                                    };

                                    wsdlFile.create(unCloseIn, true, null);
                                }
                                zipIn.closeEntry();
                            }
                            zipIn.close();
                        } else {
                            IFile wsdlFile = wsdlsPackageFolder.getFile(uniqueName + ".wsdl"); //$NON-NLS-1$
                            wsdlFile.create(wsdlStream, true, null);
                        }
                    }
                }
            }
        } catch (CoreException e) {
            if (e.getStatus() != null && e.getStatus().getException() != null) {
                ExceptionHandler.process(e.getStatus().getException());
            }
            throw new ProcessorException(Messages.getString("Processor.tempFailed"), e); //$NON-NLS-1$
        } catch (IOException e) {
            throw new ProcessorException(Messages.getString("Processor.tempFailed"), e); //$NON-NLS-1$
        }

        boolean samEnabled = false;
        boolean slEnabled = false;
        for (INode node : graphicalNodes) {
            String nodeName = node.getComponent().getName();
            if (("tESBConsumer".equals(nodeName) || "tESBProviderRequest".equals(nodeName) //$NON-NLS-1$ //$NON-NLS-2$
                    || "tRESTClient".equals(nodeName) || "tRESTRequest".equals(nodeName) //$NON-NLS-1$ //$NON-NLS-2$
            || "cCXFRS".equals(nodeName)) //$NON-NLS-1$
                    && node.isActivate()) {

                if (!samEnabled) {
                    Object value = node.getPropertyValue("SERVICE_ACTIVITY_MONITOR"); //$NON-NLS-1$
                    if (null != value) {
                        samEnabled = (Boolean) value;
                    }
                }
                if (!slEnabled) {
                    Object value = node.getPropertyValue("SERVICE_LOCATOR"); //$NON-NLS-1$
                    if (null != value) {
                        slEnabled = (Boolean) value;
                    }
                }

                if (samEnabled && slEnabled) {
                    break;
                }
            }
        }

        if (samEnabled || slEnabled) {
            File esbConfigsSourceFolder = EsbConfigUtils.getEclipseEsbFolder();
            if (!esbConfigsSourceFolder.exists()) {
                RunProcessPlugin
                        .getDefault()
                        .getLog()
                        .log(new Status(IStatus.WARNING, RunProcessPlugin.getDefault().getBundle().getSymbolicName(),
                                "ESB configuration folder does not exists - " + esbConfigsSourceFolder.toURI())); //$NON-NLS-1$
                return;
            }
            ITalendProcessJavaProject tProcessJvaProject = this.getTalendJavaProject();
            if (tProcessJvaProject == null) {
                return;
            }
            // PTODO, need be src/main/java or src/main/resources?
            IFolder esbConfigsTargetFolder = tProcessJvaProject.getSrcFolder();

            // add SAM config file to classpath
            if (samEnabled) {
                copyEsbConfigFile(esbConfigsSourceFolder, esbConfigsTargetFolder, "agent.properties"); //$NON-NLS-1$
            }

            // add SL config file to classpath
            if (slEnabled) {
                copyEsbConfigFile(esbConfigsSourceFolder, esbConfigsTargetFolder, "locator.properties"); //$NON-NLS-1$
            }
        }
    }

    /**
     * Check current stream whether zip stream by check header signature. Zip header signature = 0x504B 0304(big endian)
     * 
     * @param wsdlStream the wsdl stream. <b>Must Support Mark&Reset . Must at beginning of stream.</b>
     * @return true, if is zip stream.
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private boolean checkIsZipStream(InputStream wsdlStream) throws IOException {
        boolean isZip = false;
        byte[] headerB = new byte[4];
        wsdlStream.mark(4);
        wsdlStream.read(headerB);
        int header = ByteBuffer.wrap(headerB).getInt();

        if (header == 0x504B0304) {
            isZip = true;
        }
        wsdlStream.reset();
        return isZip;
    }

    private static void copyEsbConfigFile(File esbConfigsSourceFolder, IFolder esbConfigsTargetFolder, String configFile) {
        File esbConfig = new File(esbConfigsSourceFolder, configFile);
        if (esbConfig.exists()) {
            try {
                IFile target = esbConfigsTargetFolder.getFile(configFile);
                InputStream is = null;
                try {
                    is = new FileInputStream(esbConfig);
                    if (!target.exists()) {
                        target.create(is, true, null);
                    } else {
                        target.setContents(is, true, false, null);
                    }
                } finally {
                    if (null != is) {
                        is.close();
                    }
                }
                // esbConfig.copy(esbConfigsTargetFolder.getChild(configFile), EFS.OVERWRITE, null);
            } catch (Exception e) {
                RunProcessPlugin
                        .getDefault()
                        .getLog()
                        .log(new Status(IStatus.WARNING, RunProcessPlugin.getDefault().getBundle().getSymbolicName(),
                                "cannot add configuration file on classpath - " + configFile, //$NON-NLS-1$
                                e));
            }
        } else {
            RunProcessPlugin
                    .getDefault()
                    .getLog()
                    .log(new Status(IStatus.WARNING, RunProcessPlugin.getDefault().getBundle().getSymbolicName(),
                            "cannot find configuration file - " + esbConfig.toURI())); //$NON-NLS-1$
        }
    }

    /*
     * (non-Javadoc) generate spring file for RouteBuilder ADDED for TESB-7887 by GangLiu
     */
    @Override
    public void generateSpringContent() throws ProcessorException {
        try {
            ICodeGeneratorService service = RunProcessPlugin.getDefault().getCodeGeneratorService();
            ICodeGenerator codeGen = service.createCodeGenerator(process, false, false);

            if (codeGen == null) {
                return;
            }
            String content = codeGen.generateSpringContent();
            if (content == null) {
                return;
            }
            IProject processorProject = this.project;
            ITalendProcessJavaProject tProcessJvaProject = getTalendJavaProject();
            if (processorProject == null && tProcessJvaProject != null) {
                processorProject = tProcessJvaProject.getProject();
            }
            if (processorProject == null || tProcessJvaProject == null) {
                return;
            }
            processorProject.refreshLocal(IResource.DEPTH_INFINITE, null);
            IFolder srcFolder = tProcessJvaProject.getSrcFolder();
            if (!srcFolder.exists()) {
                srcFolder.create(true, true, null);
            }
            IFolder metainfFolder = srcFolder.getFolder("META-INF"); //$NON-NLS-1$
            if (!metainfFolder.exists()) {
                metainfFolder.create(true, true, null);
            }
            IFolder springFolder = metainfFolder.getFolder("spring"); //$NON-NLS-1$
            if (!springFolder.exists()) {
                springFolder.create(true, true, null);
            }
            IFile springFile = springFolder.getFile(process.getName().toLowerCase() + ".xml"); //$NON-NLS-1$
            InputStream is = new ByteArrayInputStream(content.getBytes());

            if (!springFile.exists()) {
                springFile.create(is, true, null);
            } else {
                springFile.setContents(is, true, false, null);
            }
            is.close();
        } catch (SystemException e) {
            throw new ProcessorException(Messages.getString("Processor.generationFailed"), e); //$NON-NLS-1$
        } catch (CoreException e1) {
            throw new ProcessorException(Messages.getString("Processor.tempFailed"), e1); //$NON-NLS-1$
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jdt.debug.core.IJavaBreakpointListener#addingBreakpoint(org
     * .eclipse.jdt.debug.core.IJavaDebugTarget, org.eclipse.jdt.debug.core.IJavaBreakpoint)
     */
    @Override
    public void addingBreakpoint(IJavaDebugTarget target, IJavaBreakpoint breakpoint) {

    }

    /*
     * (non-Javadoc)
     * 
     * @seeorg.eclipse.jdt.debug.core.IJavaBreakpointListener#
     * breakpointHasCompilationErrors(org.eclipse.jdt.debug.core. IJavaLineBreakpoint,
     * org.eclipse.jdt.core.dom.Message[])
     */
    @Override
    public void breakpointHasCompilationErrors(IJavaLineBreakpoint breakpoint, Message[] errors) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @seeorg.eclipse.jdt.debug.core.IJavaBreakpointListener# breakpointHasRuntimeException(org.eclipse.jdt.debug.core.
     * IJavaLineBreakpoint, org.eclipse.debug.core.DebugException)
     */
    @Override
    public void breakpointHasRuntimeException(IJavaLineBreakpoint breakpoint, DebugException exception) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jdt.debug.core.IJavaBreakpointListener#breakpointHit(org. eclipse.jdt.debug.core.IJavaThread,
     * org.eclipse.jdt.debug.core.IJavaBreakpoint)
     */
    @Override
    public int breakpointHit(IJavaThread thread, IJavaBreakpoint breakpoint) {
        // TODO Auto-generated method stub
        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jdt.debug.core.IJavaBreakpointListener#breakpointInstalled
     * (org.eclipse.jdt.debug.core.IJavaDebugTarget , org.eclipse.jdt.debug.core.IJavaBreakpoint)
     */
    @Override
    public void breakpointInstalled(IJavaDebugTarget target, IJavaBreakpoint breakpoint) {
        updateGraphicalNodeBreaking(breakpoint, false);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jdt.debug.core.IJavaBreakpointListener#breakpointRemoved(
     * org.eclipse.jdt.debug.core.IJavaDebugTarget, org.eclipse.jdt.debug.core.IJavaBreakpoint)
     */
    @Override
    public void breakpointRemoved(IJavaDebugTarget target, IJavaBreakpoint breakpoint) {
        if (!target.isTerminated()) {
            updateGraphicalNodeBreaking(breakpoint, true);
        }

    }

    /**
     * yzhang Comment method "updateGraphicalNodeBreaking".
     * 
     * @param breakpoint
     */
    private void updateGraphicalNodeBreaking(IJavaBreakpoint breakpoint, boolean removed) {
        try {
            Integer breakLineNumber = (Integer) breakpoint.getMarker().getAttribute(IMarker.LINE_NUMBER);
            if (breakLineNumber == null || breakLineNumber == -1) {
                return;
            }
            IFile codeFile = this.project.getFile(this.codePath);
            if (!codeFile.exists()) {
                JDIDebugModel.removeJavaBreakpointListener(this);
                return;
            }
            LineNumberReader lineReader = new LineNumberReader(new InputStreamReader(codeFile.getContents()));
            String content = null;
            while (lineReader.getLineNumber() < breakLineNumber - 3) {
                content = lineReader.readLine();
                if (content == null) {
                    return;
                }
            }
            int startIndex = content.indexOf("[") + 1; //$NON-NLS-1$
            int endIndex = content.indexOf(" main ] start"); //$NON-NLS-1$
            if (startIndex != -1 && endIndex != -1) {
                String nodeUniqueName = content.substring(startIndex, endIndex);
                List<? extends INode> breakpointNodes = CorePlugin.getContext().getBreakpointNodes(process);
                List<? extends INode> graphicalNodes = process.getGraphicalNodes();
                if (graphicalNodes == null) {
                    return;
                }
                for (INode node : graphicalNodes) {
                    if (node.getUniqueName().equals(nodeUniqueName) && removed && breakpointNodes.contains(node)) {
                        CorePlugin.getContext().removeBreakpoint(process, node);
                        if (node instanceof Node) {
                            final INode currentNode = node;
                            Display.getDefault().syncExec(new Runnable() {

                                @Override
                                public void run() {
                                    ((Node) currentNode).removeStatus(Process.BREAKPOINT_STATUS);

                                }

                            });
                        }
                    } else if (node.getUniqueName().equals(nodeUniqueName) && !removed && !breakpointNodes.contains(node)) {
                        CorePlugin.getContext().addBreakpoint(process, node);
                        if (node instanceof Node) {
                            final INode currentNode = node;
                            Display.getDefault().syncExec(new Runnable() {

                                @Override
                                public void run() {
                                    ((Node) currentNode).addStatus(Process.BREAKPOINT_STATUS);

                                }

                            });
                        }
                    }
                }

            }

        } catch (CoreException e) {
            RuntimeExceptionHandler.process(e);
        } catch (IOException e) {
            RuntimeExceptionHandler.process(e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jdt.debug.core.IJavaBreakpointListener#installingBreakpoint
     * (org.eclipse.jdt.debug.core.IJavaDebugTarget , org.eclipse.jdt.debug.core.IJavaBreakpoint,
     * org.eclipse.jdt.debug.core.IJavaType)
     */
    @Override
    public int installingBreakpoint(IJavaDebugTarget target, IJavaBreakpoint breakpoint, IJavaType type) {
        return 0;
    }

    @Override
    public Property getProperty() {
        if (property == null) {
            property = ItemCacheManager.getProcessItem(process.getId(), process.getVersion()).getProperty();
        }
        return property;
    }

    @Override
    public void build() {
        // build whole project by default.
        getTalendJavaProject().buildModules(null, null);
    }
}
