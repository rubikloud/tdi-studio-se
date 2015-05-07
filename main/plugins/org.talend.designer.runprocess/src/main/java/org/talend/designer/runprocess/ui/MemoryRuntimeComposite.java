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
package org.talend.designer.runprocess.ui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.BidiMap;
import org.apache.commons.collections.buffer.UnboundedFifoBuffer;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.exolab.castor.util.Iterator;
import org.talend.commons.ui.runtime.image.ImageProvider;
import org.talend.core.CorePlugin;
import org.talend.core.model.process.EComponentCategory;
import org.talend.core.model.process.Element;
import org.talend.core.model.process.IContext;
import org.talend.core.model.process.IProcess2;
import org.talend.core.model.process.ReplaceNodesInProcessProvider;
import org.talend.core.model.properties.ConnectionItem;
import org.talend.core.model.utils.JavaResourcesHelper;
import org.talend.core.ui.CoreUIPlugin;
import org.talend.core.ui.properties.tab.IDynamicProperty;
import org.talend.designer.core.IMultiPageTalendEditor;
import org.talend.designer.runprocess.RunProcessContext;
import org.talend.designer.runprocess.i18n.Messages;
import org.talend.designer.runprocess.ui.actions.SaveJobBeforeRunAction;
import org.talend.designer.runprocess.ui.views.ProcessView;
import org.talend.designer.runprocess.ui.views.ProcessViewSelectionProvider;
import org.talend.designer.runtime.visualization.Activator;
import org.talend.designer.runtime.visualization.IActiveJvm;
import org.talend.designer.runtime.visualization.IHost;
import org.talend.designer.runtime.visualization.JvmCoreException;
import org.talend.designer.runtime.visualization.JvmModel;
import org.talend.designer.runtime.visualization.ReportMessage;
import org.talend.designer.runtime.visualization.ReportMessageManager;
import org.talend.designer.runtime.visualization.views.AbstractRuntimeGraphcsComposite;
import org.talend.designer.runtime.visualization.views.RuntimeGraphcsComposite;

/**
 * created by ldong on Apr 3, 2015 Detailled comment
 *
 */
public class MemoryRuntimeComposite extends ScrolledComposite implements IDynamicProperty {

    private Button runtimeButton;

    private Combo periodCombo;
    
    private ComboViewer contextCombo;

    private RunProcessContext processContext;

    private ProcessView viewPart;

    private IActiveJvm currentJvm;

    private Composite monitorComposite;

    private AbstractRuntimeGraphcsComposite chartComposite;

    private PropertyChangeListener propertyChangeListener;
    
    private ProcessComposite basicRunComposite;
    
    private ProcessManager processManager;
    
    private  ReportMessageManager messageManager;
    
    public MemoryRuntimeComposite(ProcessView viewPart, Composite parent, RunProcessContext processContext, int style) {
        super(parent, style);
        this.viewPart = viewPart;
        this.processContext = processContext;
        this.processManager = ProcessManager.getInstance();
        this.messageManager = ReportMessageManager.getInstance();
        init(parent);
        // CSS
        CoreUIPlugin.setCSSClass(this, this.getClass().getSimpleName());
    }

    private void init(final Composite parent) {
        initGraphicComponents(parent);
//        addListeners();
    }

    /**
     * DOC ldong Comment method "initGraphicComponents".
     * 
     * @param parent
     */
    private void initGraphicComponents(Composite parent) {
        initRuntimeGraphs(parent);
        addListeners();
        connectToJvm();
    }

    private void initRuntimeGraphs(final Composite parent) {
        ISelection processContextSelection = viewPart.getSite().getSelectionProvider() == null ? null : viewPart.getSelection();
        setExpandHorizontal(true);
        setExpandVertical(true);
        FormData layouData = new FormData();
        layouData.left = new FormAttachment(0, 0);
        layouData.right = new FormAttachment(100, 0);
        layouData.top = new FormAttachment(0, 0);
        layouData.bottom = new FormAttachment(100, 0);
        this.setBackground(parent.getBackground());
        this.setLayout(new FormLayout());
        setLayoutData(layouData);

        monitorComposite = new Composite(this, SWT.NULL);
        FormData baseData = new FormData();
        baseData.left = new FormAttachment(0, 0);
        baseData.right = new FormAttachment(100, 0);
        baseData.top = new FormAttachment(0, 0);
        baseData.bottom = new FormAttachment(100, 0);
        monitorComposite.setLayout(new FormLayout());
        monitorComposite.setLayoutData(layouData);
        setContent(monitorComposite);

        Group topGroup = createTopGroup(monitorComposite);
        runtimeButton = new Button(topGroup, SWT.PUSH);
        if(processContext!=null){
        	setRuntimeButtonByStatus(!processContext.isMonitoring());
        }else{
        	setRuntimeButtonByStatus(true);
        }
        runtimeButton.setEnabled(true);
//        runtimeButton.setImage(ImageProvider.getImage(ERunprocessImages.RUN_PROCESS_ACTION));
//        runtimeButton.setText("Run"); //$NON-NLS-1$
        FormData runButtonData = new FormData();
        Point execSize = null;
        runButtonData.left = new FormAttachment(0, 10);

        execSize = computeSize(runtimeButton.getText());
        runButtonData.right = new FormAttachment(0, execSize.x + 70);
        runButtonData.height = 30;
        runtimeButton.setLayoutData(runButtonData);

        Button gcCheckButton = new Button(topGroup,SWT.CHECK);
        FormData gcCheckButtonData = new FormData();
        gcCheckButtonData.left = new FormAttachment(runtimeButton,15,SWT.RIGHT);
        gcCheckButtonData.height = 30;
        gcCheckButton.setLayoutData(gcCheckButtonData);

        Label periodLabel = new Label(topGroup, SWT.NULL);
        periodLabel.setText(Messages.getString("ProcessView.moniorPeriod")); //$NON-NLS-1$
        periodLabel.setBackground(getBackground());
        FormData periodLabelData = new FormData();
        periodLabelData.left = new FormAttachment(gcCheckButton, 0, SWT.RIGHT);
        periodLabelData.right = new FormAttachment(gcCheckButton, 200, SWT.RIGHT);
        periodLabelData.top = new FormAttachment(0, 7);
        periodLabelData.bottom = new FormAttachment(100, 0);
        periodLabelData.height = 30;
        periodLabel.setLayoutData(periodLabelData);

        periodCombo = new Combo(topGroup, SWT.READ_ONLY);
        FormData periodData = new FormData();
        periodData.left = new FormAttachment(periodLabel, 2, SWT.RIGHT);
        periodData.right = new FormAttachment(periodLabel, 75, SWT.RIGHT);
        periodData.top = new FormAttachment(0,3);
        periodData.height = 25;
        periodCombo.setLayoutData(periodData);
        periodCombo.setItems(new String[] { "30 sec", "60 sec", "120 sec" });
        periodCombo.select(0);
        
        
        contextCombo = new ComboViewer(topGroup, SWT.BORDER | SWT.READ_ONLY);
        contextCombo.getCombo().setLayout(new FormLayout());
        FormData contextComboData = new FormData();
        contextComboData.left = new FormAttachment(100, -80);;
        contextComboData.right = new FormAttachment(100,-5);
        contextComboData.top = new FormAttachment(0,3);
        contextComboData.height = 25;
        contextCombo.getCombo().setLayoutData(contextComboData);
        contextCombo.setContentProvider(new ArrayContentProvider());
        contextCombo.setLabelProvider(new ContextNameLabelProvider());
        initContextInput();

//        chartComposite = new RuntimeGraphcsComposite(monitorComposite, processContextSelection, SWT.NULL);
        chartComposite = new RuntimeGraphcsComposite(monitorComposite, processContextSelection, SWT.NULL);
        FormLayout rgcLayout = new FormLayout();
        FormData charLayData = new FormData();
        charLayData.left = new FormAttachment(0, 10);
        charLayData.right = new FormAttachment(100, 0);
        charLayData.top = new FormAttachment(topGroup, 60, SWT.BOTTOM);
        charLayData.bottom = new FormAttachment(100, 0);
        chartComposite.setLayout(rgcLayout);
        chartComposite.setLayoutData(charLayData);
    }

	private void initContextInput() {
		List<IContext> contextList = (List<IContext>) viewPart.getContextComposite().getContextComboInput();
        if(contextList!=null){
        	contextCombo.setSelection(new StructuredSelection(contextList.get(0)));
        	contextCombo.setInput(contextList);
        	ComboViewer processContextComboViewer = viewPart.getContextComposite().getContextComboViewer();
        	IContext selectedContext = (IContext) ((IStructuredSelection) processContextComboViewer.getSelection()).getFirstElement();
        	for(int i=0;i<contextList.size();i++){
        		if(contextList.get(i).getName().equals(selectedContext.getName())){
        			contextCombo.getCombo().select(i);
        		}
        	}
        	contextCombo.addSelectionChangedListener(new ISelectionChangedListener() {
        		@Override
        		public void selectionChanged(SelectionChangedEvent event) {
        			viewPart.getContextComposite().runSelectionChange(event);
        		}
        	});
        }else{
        	contextCombo.getCombo().setEnabled(false);
        }
	}

    private void connectToJvm() {
        if (currentJvm != null && !currentJvm.isConnected() && isEnabled()) {
            try {
                currentJvm.connect(1000);
            } catch (JvmCoreException e) {
                Activator.log(NLS.bind("Jvm failed", currentJvm.getPid()), e);
            }
        }
    }

    private Group createTopGroup(Composite parent) {
        Composite topComposite = new Composite(parent, SWT.NULL);
        FormLayout topLayout = new FormLayout();
        FormData topData = new FormData();
        topData.left = new FormAttachment(0, 10);
        topData.right = new FormAttachment(100, 0);
        topData.top = new FormAttachment(0, 0);
        topComposite.setLayout(topLayout);
        topComposite.setLayoutData(topData);

        Group topGroup = new Group(topComposite, SWT.NULL);
        topGroup.setText("Monitor Control"); //$NON-NLS-1$
        FormLayout groupLayout = new FormLayout();
        groupLayout.marginHeight = 2;
        groupLayout.marginWidth = 2;
        groupLayout.spacing = 7;
        FormData groupData = new FormData();
        groupData.left = new FormAttachment(0, 0);
        groupData.right = new FormAttachment(100, 0);
        groupData.top = new FormAttachment(0, 0);
        groupData.bottom = new FormAttachment(100, 0);
        topGroup.setLayout(groupLayout);
        topGroup.setLayoutData(groupData);

        return topGroup;
    }

    private Point computeSize(String text) {
        GC gc = new GC(runtimeButton.getDisplay());
        final Point p = gc.textExtent(text);
        gc.dispose();
        return p;
    }

    private void addListeners() {
        runtimeButton.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent event) {
            	if(processContext != null && !processContext.isRunning() && runtimeButton.getText().equals(Messages.getString("ProcessComposite.exec"))){
            		runtimeButton.setEnabled(false);
            		exec();
            	}
                if (processContext != null && processContext.isRunning()) {
                	if(runtimeButton.getText().equals(Messages.getString("ProcessComposite.exec"))){
                		
                		long startTime = System.currentTimeMillis();
                		long endTime;
                		while(true){
                			if(initCurrentActiveJobJvm()){
                				break;
                			}
                			endTime = System.currentTimeMillis();
                			if(endTime - startTime > 30000){
                				MessageDialog.openWarning(getShell(), "Warning", Messages.getString("ProcessView.noJobRunning"));
                				runtimeButton.setEnabled(true);
                				return;
                			}
                		}
                		initMonitoringModel();
                		refreshMonitorComposite();
                		processContext.setMonitoring(true);
                		setRuntimeButtonByStatus(!processContext.isMonitoring());
                		//need to extract
                		String content = "Start time : "+ new SimpleDateFormat("hh:mm:ss a MM/dd/YYYY").format(new Date())+"\r\n";
                		messageManager.setStartMessage(content, getDisplay().getSystemColor(SWT.COLOR_BLUE), getDisplay().getSystemColor(SWT.COLOR_WHITE));
                		((RuntimeGraphcsComposite)chartComposite).displayReportField();
                	
                	}else if(runtimeButton.getText().equals(Messages.getString("ProcessComposite.kill"))){
                		processContext.kill();
                		processContext.setMonitoring(false);
                		setRuntimeButtonByStatus(!processContext.isMonitoring());
                		//need to extract
//                		String content = "End time : "+ new SimpleDateFormat("hh:mm:ss a MM/dd/YYYY").format(new Date())+"\r\n";
//            			messageManager.setEndMessage(content, getDisplay().getSystemColor(SWT.COLOR_BLUE), getDisplay().getSystemColor(SWT.COLOR_WHITE));
//            			((RuntimeGraphcsComposite)chartComposite).displayReportField();
                	
                	}
//                    runtimeButton.setEnabled(!processContext.isMonitoring());
                } else {
                    MessageDialog.openWarning(getShell(), "Warning", Messages.getString("ProcessView.noJobRunning")); //$NON-NLS-1$//$NON-NLS-2$
                }
                runtimeButton.setEnabled(true);
            }
        });

        propertyChangeListener = new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                runProcessContextChanged(evt);
            }
        };

    }

	private boolean initCurrentActiveJobJvm() {
    	boolean isJvmFound = false;
        JvmModel jvmModel = JvmModel.getInstance();
        String jobClassName = JavaResourcesHelper.getJobClassName(processContext.getProcess());
        List<IActiveJvm> activateJvms = jvmModel.getHost(IHost.LOCALHOST).getActiveJvms();
        for (IActiveJvm jvm : activateJvms) {
            if (jvm.getMainClass().equals(jobClassName)) {
                currentJvm = jvm;
                isJvmFound = true;
                break;
            }
        }
        return isJvmFound;
    }

    private void initMonitoringModel() {
        final List<IActiveJvm> contructSelections = new ArrayList<IActiveJvm>();
        contructSelections.add(currentJvm);
        if (currentJvm != null) {
            viewPart.getSite()
                    .setSelectionProvider(new ProcessViewSelectionProvider(new StructuredSelection(contructSelections)));
        }
    }

    private void refreshMonitorComposite() {
        if (monitorComposite != null && !monitorComposite.isDisposed()) {
            monitorComposite.dispose();
        }
        initGraphicComponents(this);
        monitorComposite.layout();
    }

    private void runProcessContextChanged(final PropertyChangeEvent evt) {
        if (isDisposed()) {
            return;
        }
        String propName = evt.getPropertyName();

        if (RunProcessContext.MEMORY_MONITOR.equals(propName)) {
            getDisplay().asyncExec(new Runnable() {

                @Override
                public void run() {
                    if (isDisposed()) {
                        return;
                    }
                    boolean monitoring = ((Boolean) evt.getNewValue()).booleanValue();
                    if (runtimeButton != null && !runtimeButton.isDisposed()) {
//                        runtimeButton.setEnabled(!(processContext != null && processContext.isRunning()));
//                        runtimeButton.setEnabled(processContext != null && processContext.isRunning());
                    	setRuntimeButtonByStatus(processContext != null && processContext.isRunning());
                    	if(processContext == null){
                    		setRuntimeButtonByStatus(false);
                    	}else if(processContext != null && processContext.isRunning()){
                    		setRuntimeButtonByStatus(false);
                    	}else{
                    		setRuntimeButtonByStatus(true);
                    		//need to extract
                    		String content = "End time : "+ new SimpleDateFormat("hh:mm:ss a MM/dd/YYYY").format(new Date())+"\r\n";
                			messageManager.setEndMessage(content, getDisplay().getSystemColor(SWT.COLOR_BLUE), getDisplay().getSystemColor(SWT.COLOR_WHITE));
                    		((RuntimeGraphcsComposite)chartComposite).displayReportField();
                    	}
                    }
                    if (!monitoring && chartComposite != null && chartComposite.isDisposed()) {
                        viewPart.setSelection(null);
                        refreshMonitorComposite();
                    }
                }
            });
        }

    }

    public void setProcessContext(RunProcessContext processContext) {

        if (this.processContext != null) {
            this.processContext.removePropertyChangeListener(propertyChangeListener);
        }
        this.processContext = processContext;
        if (processContext != null) {
            processContext.addPropertyChangeListener(propertyChangeListener);
        }

        if (runtimeButton != null && !runtimeButton.isDisposed()) {
//            runtimeButton.setEnabled(processContext != null && processContext.isRunning());
        	runtimeButton.setEnabled(processContext != null);
        	//imporve this logic later
        	if(processContext == null){
        		setRuntimeButtonByStatus(true);
        	}else if(!processContext.isRunning()){
        		setRuntimeButtonByStatus(true);
        	}else if(processContext.isMonitoring()){
        		setRuntimeButtonByStatus(false);
        	}else{
        		setRuntimeButtonByStatus(true);
        	}
        }

        if (periodCombo != null && !periodCombo.isDisposed()) {
            periodCombo.setEnabled(processContext != null && processContext.isRunning());
        }
        if (contextCombo !=null && !contextCombo.getCombo().isDisposed()){
        	if(processContext == null){
        		contextCombo.setInput(null);
        	}else{
        		initContextInput();
        	}
        		contextCombo.getCombo().setEnabled(processContext != null);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.properties.tab.IDynamicProperty#getComposite()
     */
    @Override
    public Composite getComposite() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.properties.tab.IDynamicProperty#getCurRowSize()
     */
    @Override
    public int getCurRowSize() {
        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.properties.tab.IDynamicProperty#getElement()
     */
    @Override
    public Element getElement() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.properties.tab.IDynamicProperty#getHashCurControls()
     */
    @Override
    public BidiMap getHashCurControls() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.properties.tab.IDynamicProperty#getPart()
     */
    @Override
    public IMultiPageTalendEditor getPart() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @seeorg.talend.core.properties.tab.IDynamicProperty#getRepositoryAliasName(org.talend.core.model.properties.
     * ConnectionItem)
     */
    @Override
    public String getRepositoryAliasName(ConnectionItem connectionItem) {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.properties.tab.IDynamicProperty#getSection()
     */
    @Override
    public EComponentCategory getSection() {
        return EComponentCategory.MEMORYRUN;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.properties.tab.IDynamicProperty#getTableIdAndDbSchemaMap()
     */
    @Override
    public Map<String, String> getTableIdAndDbSchemaMap() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.properties.tab.IDynamicProperty#getTableIdAndDbTypeMap()
     */
    @Override
    public Map<String, String> getTableIdAndDbTypeMap() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.properties.tab.IDynamicProperty#refresh()
     */
    @Override
    public void refresh() {
        if (!isDisposed()) {
            getParent().layout();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.properties.tab.IDynamicProperty#setCurRowSize(int)
     */
    @Override
    public void setCurRowSize(int i) {

    }
    
    /**
     * LabelProvider for a context combo. <br/>
     * 
     * $Id$
     * 
     */
    private static class ContextNameLabelProvider extends LabelProvider {

        /**
         * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
         */
        @Override
        public String getText(final Object element) {
            IContext context = (IContext) element;
            return context.getName();
        }
    }
    
    private void setRuntimeButtonByStatus(boolean isRunning){
    	if(isRunning){
    		runtimeButton.setText(Messages.getString("ProcessComposite.exec")); //$NON-NLS-1$
    		runtimeButton.setImage(ImageProvider.getImage(ERunprocessImages.RUN_PROCESS_ACTION));
    	}else{
    		runtimeButton.setText(Messages.getString("ProcessComposite.kill")); //$NON-NLS-1$
    		runtimeButton.setImage(ImageProvider.getImage(ERunprocessImages.KILL_PROCESS_ACTION));
    	}
    }
    
    private void exec() {

        if (processContext instanceof IProcess2) {
            ReplaceNodesInProcessProvider.beforeRunJobInGUI(processContext.getProcess());
        }
        CorePlugin.getDefault().getRunProcessService().saveJobBeforeRun(processContext.getProcess());
        if (processContext.isClearBeforeExec()) {
            processContext.clearMessages();
        }
        processContext.setMonitorTrace(false);
        processContext.setWatchAllowed(processManager.getExecTime());
        processContext.setMonitorPerf(processManager.getStat());
        
        if (processContext.isSaveBeforeRun()) {
            SaveJobBeforeRunAction action = new SaveJobBeforeRunAction(processContext.getProcess());
            action.run();
        }
        
        processContext.setSelectedContext(processManager.getSelectContext());
        processContext.exec(processManager.getProcessShell());

        ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
        ILaunch[] launches = manager.getLaunches();
        manager.removeLaunches(launches);

    }
}
