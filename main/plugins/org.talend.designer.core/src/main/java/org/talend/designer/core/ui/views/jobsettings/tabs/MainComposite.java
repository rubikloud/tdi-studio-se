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
package org.talend.designer.core.ui.views.jobsettings.tabs;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.views.properties.tabbed.AbstractPropertySection;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertyConstants;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;
import org.talend.commons.exception.LoginException;
import org.talend.commons.exception.PersistenceException;
import org.talend.commons.ui.runtime.exception.MessageBoxExceptionHandler;
import org.talend.commons.utils.VersionUtils;
import org.talend.core.GlobalServiceRegister;
import org.talend.core.model.properties.Item;
import org.talend.core.model.properties.ProcessItem;
import org.talend.core.model.properties.Property;
import org.talend.core.model.properties.User;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.core.model.repository.IRepositoryViewObject;
import org.talend.core.repository.ui.actions.DeleteActionCache;
import org.talend.core.repository.utils.ConvertJobsUtil;
import org.talend.core.repository.utils.ConvertJobsUtil.JobType;
import org.talend.core.repository.utils.ConvertJobsUtil.Status;
import org.talend.core.runtime.CoreRuntimePlugin;
import org.talend.core.ui.branding.IBrandingService;
import org.talend.core.ui.editor.IJobEditorHandler;
import org.talend.core.ui.editor.JobEditorHandlerManager;
import org.talend.repository.ProjectManager;
import org.talend.repository.i18n.Messages;
import org.talend.repository.model.IProxyRepositoryFactory;

/**
 * yzhang class global comment. Detailled comment
 */
public class MainComposite extends AbstractTabComposite {

    private Property property;

    private boolean enableControl;

    private Text nameText;

    private Text versionText;

    private CCombo jobTypeCCombo;

    private CCombo jobFrameworkCCombo;

    private Text purposeText;

    private CCombo statusText;

    private Text descriptionText;

    private Button btnDown;

    private Button btnUp;

    private Button btnConfirm;

    private String jobTypeValue = null;

    private String frameworkValue = null;

    private String lastVersionFound;

    /**
     * yzhang MainComposite constructor comment.
     * 
     * @param parent
     * @param style
     */
    public MainComposite(Composite parent, int style, TabbedPropertySheetWidgetFactory factory, IRepositoryViewObject obj) {
        super(parent, style, factory, obj);

        IBrandingService brandingService = (IBrandingService) GlobalServiceRegister.getDefault().getService(
                IBrandingService.class);
        boolean allowVerchange = brandingService.getBrandingConfiguration().isAllowChengeVersion();
        boolean allowEnableControl = enableControl;
        property = repositoryObject.getProperty();
        Item item = property.getItem();
        if (item != null && item instanceof ProcessItem) {
            allowEnableControl = true;
        }

        FormLayout layout = new FormLayout();
        setLayout(layout);

        FormData thisFormData = new FormData();
        thisFormData.left = new FormAttachment(0, 0);
        thisFormData.right = new FormAttachment(100, 0);
        thisFormData.top = new FormAttachment(0, 0);
        thisFormData.bottom = new FormAttachment(100, 0);
        setLayoutData(thisFormData);

        Composite composite = widgetFactory.createFlatFormComposite(this);

        FormData compositeData = new FormData();
        compositeData.left = new FormAttachment(0, 0);
        compositeData.right = new FormAttachment(100, 0);
        compositeData.top = new FormAttachment(0, 0);
        compositeData.bottom = new FormAttachment(100, 0);
        composite.setLayoutData(thisFormData);

        nameText = widgetFactory.createText(composite, ""); //$NON-NLS-1$
        FormData data = new FormData();
        data.left = new FormAttachment(0, AbstractPropertySection.STANDARD_LABEL_WIDTH);
        data.right = new FormAttachment(50, 0);
        data.top = new FormAttachment(0, ITabbedPropertyConstants.VSPACE);
        nameText.setLayoutData(data);
        String nameContent = repositoryObject.getLabel();
        nameText.setText(nameContent != null ? nameContent : ""); //$NON-NLS-1$
        nameText.setEnabled(allowEnableControl);

        CLabel nameLabel = widgetFactory.createCLabel(composite, Messages.getString("NameSection.Name")); //$NON-NLS-1$
        data = new FormData();
        data.left = new FormAttachment(0, 0);
        data.right = new FormAttachment(nameText, -ITabbedPropertyConstants.HSPACE);
        data.top = new FormAttachment(nameText, 0, SWT.CENTER);
        nameLabel.setLayoutData(data);

        Text authorText = widgetFactory.createText(composite, ""); //$NON-NLS-1$
        authorText.setEnabled(false);
        data = new FormData();
        data.left = new FormAttachment(0, AbstractPropertySection.STANDARD_LABEL_WIDTH);
        data.right = new FormAttachment(70, 0);
        data.top = new FormAttachment(nameLabel, ITabbedPropertyConstants.VSPACE);
        authorText.setLayoutData(data);
        User user = repositoryObject.getAuthor();
        if (user != null) {
            String author = user.getLogin();
            authorText.setText(author != null ? author : ""); //$NON-NLS-1$
        } else {
            authorText.setText(nameContent);
        }
        authorText.setEnabled(enableControl);

        CLabel authorLabel = widgetFactory.createCLabel(composite, Messages.getString("VersionAuthorSection.authorLabel")); //$NON-NLS-1$
        data = new FormData();
        data.left = new FormAttachment(0, 0);
        data.right = new FormAttachment(authorText, -ITabbedPropertyConstants.HSPACE);
        data.top = new FormAttachment(authorText, 0, SWT.CENTER);
        authorLabel.setLayoutData(data);

        if (allowVerchange) {
            btnDown = widgetFactory.createButton(composite, "m", SWT.PUSH); //$NON-NLS-1$
            data = new FormData();
            data.right = new FormAttachment(100, 0);
            data.top = new FormAttachment(authorText, 0, SWT.CENTER);
            btnDown.setLayoutData(data);
            btnDown.setEnabled(allowEnableControl);

            btnUp = widgetFactory.createButton(composite, "M", SWT.PUSH); //$NON-NLS-1$
            data = new FormData();
            data.right = new FormAttachment(btnDown, 0);
            data.top = new FormAttachment(authorText, 0, SWT.CENTER);
            btnUp.setLayoutData(data);
            btnUp.setEnabled(allowEnableControl);

            versionText = widgetFactory.createText(composite, ""); //$NON-NLS-1$
            data = new FormData();
            data.left = new FormAttachment(authorText, AbstractPropertySection.STANDARD_LABEL_WIDTH);
            data.right = new FormAttachment(btnUp, -2);
            data.top = new FormAttachment(authorText, 0, SWT.CENTER);
            versionText.setLayoutData(data);
            String version = repositoryObject.getVersion();
            versionText.setText(version != null ? version : ""); //$NON-NLS-1$
            versionText.setEnabled(allowEnableControl);

            CLabel versionLabel = widgetFactory.createCLabel(composite, Messages.getString("VersionAuthorSection.versionLabel")); //$NON-NLS-1$
            data = new FormData();
            data.left = new FormAttachment(authorText, ITabbedPropertyConstants.HSPACE * 3);
            data.right = new FormAttachment(versionText, -ITabbedPropertyConstants.HSPACE);
            data.top = new FormAttachment(versionText, 0, SWT.CENTER);
            versionLabel.setLayoutData(data);
        }

        // Job Type
        jobTypeCCombo = widgetFactory.createCCombo(composite, SWT.READ_ONLY | SWT.DROP_DOWN);
        data = new FormData();
        data.left = new FormAttachment(0, AbstractPropertySection.STANDARD_LABEL_WIDTH);
        data.right = new FormAttachment(70, 0);
        data.top = new FormAttachment(authorLabel, ITabbedPropertyConstants.VSPACE);
        jobTypeCCombo.setLayoutData(data);
        jobTypeCCombo.setItems(JobType.getJobTypeToDispaly());
        jobTypeCCombo.setText(ConvertJobsUtil.getJobTypeFromFramework(item));
        jobTypeValue = jobTypeCCombo.getText();
        jobTypeCCombo.setEnabled(allowEnableControl);

        CLabel jobTypeLabel = widgetFactory.createCLabel(composite, Messages.getString("JobTypeSection.jobTypeLabel")); //$NON-NLS-1$
        data = new FormData();
        data.left = new FormAttachment(0, 0);
        data.right = new FormAttachment(jobTypeCCombo, -ITabbedPropertyConstants.HSPACE);
        data.top = new FormAttachment(jobTypeCCombo, 0, SWT.CENTER);
        jobTypeLabel.setLayoutData(data);

        // Job Framework
        jobFrameworkCCombo = widgetFactory.createCCombo(composite, SWT.READ_ONLY | SWT.DROP_DOWN);
        data = new FormData();
        data.left = new FormAttachment(jobTypeCCombo, AbstractPropertySection.STANDARD_LABEL_WIDTH);
        data.right = new FormAttachment(100, 0);
        data.top = new FormAttachment(authorLabel, ITabbedPropertyConstants.VSPACE);
        jobFrameworkCCombo.setLayoutData(data);
        jobFrameworkCCombo.setItems(ConvertJobsUtil.getFrameworkItemsByJobType(item));
        Object frameworkObj = ConvertJobsUtil.getFramework(item);
        if (frameworkObj != null) {
            String framework = ConvertJobsUtil.getFramework(item).toString();
            jobFrameworkCCombo.setText(framework != null ? framework : ""); //$NON-NLS-1$
        }
        frameworkValue = jobFrameworkCCombo.getText();
        jobFrameworkCCombo.setEnabled(allowEnableControl);

        CLabel jobFrameworkLabel = widgetFactory.createCLabel(composite,
                Messages.getString("JobFrameworkSection.jobFrameworkLabel")); //$NON-NLS-1$
        data = new FormData();
        data.left = new FormAttachment(jobTypeCCombo, ITabbedPropertyConstants.HSPACE);
        data.right = new FormAttachment(jobFrameworkCCombo, -ITabbedPropertyConstants.HSPACE);
        data.top = new FormAttachment(jobFrameworkCCombo, 0, SWT.CENTER);
        jobFrameworkLabel.setLayoutData(data);

        purposeText = widgetFactory.createText(composite, ""); //$NON-NLS-1$
        data = new FormData();
        data.left = new FormAttachment(0, AbstractPropertySection.STANDARD_LABEL_WIDTH);
        data.right = new FormAttachment(70, 0);
        data.top = new FormAttachment(jobTypeLabel, ITabbedPropertyConstants.VSPACE);
        purposeText.setLayoutData(data);
        String content = repositoryObject.getPurpose();
        purposeText.setText(content != null ? content : ""); //$NON-NLS-1$
        purposeText.setEnabled(allowEnableControl);

        CLabel purposeLabel = widgetFactory.createCLabel(composite, Messages.getString("PurposeStatusSection.purposeLabel")); //$NON-NLS-1$
        data = new FormData();
        data.left = new FormAttachment(0, 0);
        data.right = new FormAttachment(purposeText, -ITabbedPropertyConstants.HSPACE);
        data.top = new FormAttachment(purposeText, 0, SWT.CENTER);
        purposeLabel.setLayoutData(data);

        statusText = widgetFactory.createCCombo(composite, SWT.READ_ONLY | SWT.BORDER);
        data = new FormData();
        data.left = new FormAttachment(purposeText, AbstractPropertySection.STANDARD_LABEL_WIDTH);
        data.right = new FormAttachment(100, 0);
        data.top = new FormAttachment(jobTypeLabel, ITabbedPropertyConstants.VSPACE);
        statusText.setLayoutData(data);
        String status = repositoryObject.getStatusCode();
        statusText.setText(status != null ? status : ""); //$NON-NLS-1$
        statusText.setItems(Status.getStatusToDispaly());
        statusText.setEnabled(allowEnableControl);

        CLabel statusLabel = widgetFactory.createCLabel(composite, Messages.getString("PurposeStatusSection.statusLabel")); //$NON-NLS-1$
        data = new FormData();
        data.left = new FormAttachment(purposeText, ITabbedPropertyConstants.HSPACE * 3);
        data.right = new FormAttachment(statusText, -ITabbedPropertyConstants.HSPACE);
        data.top = new FormAttachment(statusText, 0, SWT.CENTER);
        statusLabel.setLayoutData(data);

        descriptionText = widgetFactory.createText(composite, "", SWT.MULTI | SWT.V_SCROLL | SWT.WRAP); //$NON-NLS-1$
        data = new FormData();
        data.left = new FormAttachment(0, AbstractPropertySection.STANDARD_LABEL_WIDTH);
        data.right = new FormAttachment(100, 0);
        data.top = new FormAttachment(statusText, ITabbedPropertyConstants.VSPACE);
        data.height = NB_LINES * descriptionText.getLineHeight();
        descriptionText.setLayoutData(data);

        String description = repositoryObject.getDescription();
        descriptionText.setText(description != null ? description : ""); //$NON-NLS-1$
        descriptionText.setEnabled(true);
        descriptionText.setEditable(allowEnableControl);

        CLabel descriptionLabel = widgetFactory.createCLabel(composite, Messages.getString("DescriptionSection.Label")); //$NON-NLS-1$
        data = new FormData();
        data.left = new FormAttachment(0, 0);
        data.right = new FormAttachment(descriptionText, -ITabbedPropertyConstants.HSPACE);
        data.top = new FormAttachment(descriptionText, 0, SWT.TOP);
        descriptionLabel.setLayoutData(data);

        Text creationDate = widgetFactory.createText(composite, ""); //$NON-NLS-1$
        data = new FormData();
        data.left = new FormAttachment(0, AbstractPropertySection.STANDARD_LABEL_WIDTH);
        data.right = new FormAttachment(33, 0);
        data.top = new FormAttachment(descriptionText, ITabbedPropertyConstants.VSPACE);
        creationDate.setLayoutData(data);
        creationDate.setEnabled(false);
        Date cDate = repositoryObject.getCreationDate();
        creationDate.setText(cDate != null ? FORMATTER.format(cDate) : ""); //$NON-NLS-1$
        creationDate.setEnabled(enableControl);

        CLabel creationLabel = widgetFactory.createCLabel(composite, Messages.getString("DateSection.creationLabel")); //$NON-NLS-1$
        data = new FormData();
        data.left = new FormAttachment(0, 0);
        data.right = new FormAttachment(creationDate, -ITabbedPropertyConstants.HSPACE);
        data.top = new FormAttachment(creationDate, 0, SWT.CENTER);
        creationLabel.setLayoutData(data);

        Text modificationDate = widgetFactory.createText(composite, ""); //$NON-NLS-1$
        data = new FormData();
        data.left = new FormAttachment(creationDate, AbstractPropertySection.STANDARD_LABEL_WIDTH + 25);
        data.right = new FormAttachment(66, 0);
        data.top = new FormAttachment(descriptionText, ITabbedPropertyConstants.VSPACE);
        modificationDate.setLayoutData(data);
        modificationDate.setEnabled(false);
        Date mDate = repositoryObject.getModificationDate();
        modificationDate.setText(mDate != null ? FORMATTER.format(mDate) : ""); //$NON-NLS-1$
        modificationDate.setEnabled(enableControl);

        CLabel modificationLabel = widgetFactory.createCLabel(composite, Messages.getString("DateSection.ModificationLabel")); //$NON-NLS-1$
        data = new FormData();
        data.left = new FormAttachment(creationDate, ITabbedPropertyConstants.HSPACE * 3);
        data.right = new FormAttachment(modificationDate, -ITabbedPropertyConstants.HSPACE);
        data.top = new FormAttachment(modificationDate, 0, SWT.CENTER);
        modificationLabel.setLayoutData(data);

        btnConfirm = widgetFactory.createButton(composite, "Confirm", SWT.PUSH); //$NON-NLS-1$
        data = new FormData();
        data.right = new FormAttachment(100, 0);
        data.top = new FormAttachment(modificationDate, 0, SWT.CENTER);
        btnConfirm.setLayoutData(data);
        btnConfirm.setEnabled(enableControl);

        // addListener

        nameText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                evaluateTextField();
            }
        });
        jobTypeCCombo.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                ConvertJobsUtil.updateJobFrameworkPart(jobTypeCCombo.getText(), jobFrameworkCCombo);
                evaluateTextField();
            }
        });

        jobFrameworkCCombo.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                evaluateTextField();
            }
        });

        purposeText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                evaluateTextField();
            }
        });

        statusText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                evaluateTextField();
            }
        });
        descriptionText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                evaluateTextField();
            }
        });

        if (allowVerchange) {
            btnUp.addSelectionListener(new SelectionAdapter() {

                @Override
                public void widgetSelected(SelectionEvent e) {
                    String version = property.getVersion();
                    if (lastVersionFound != null && VersionUtils.compareTo(lastVersionFound, version) > 0) {
                        version = lastVersionFound;
                    }
                    version = VersionUtils.upMajor(version);
                    versionText.setText(version);
                    lastVersionFound = version;
                    evaluateTextField();
                }
            });

            btnDown.addSelectionListener(new SelectionAdapter() {

                @Override
                public void widgetSelected(SelectionEvent e) {
                    String version = property.getVersion();
                    if (lastVersionFound != null && VersionUtils.compareTo(lastVersionFound, version) > 0) {
                        version = lastVersionFound;
                    }
                    version = VersionUtils.upMinor(version);
                    versionText.setText(version);
                    lastVersionFound = version;
                    evaluateTextField();
                }
            });

            btnConfirm.addSelectionListener(new SelectionAdapter() {

                @Override
                public void widgetSelected(SelectionEvent e) {
                    if (nameText == null || nameText.isDisposed() || versionText == null || versionText.isDisposed()
                            || purposeText == null || purposeText.isDisposed() || jobTypeCCombo == null
                            || jobTypeCCombo.isDisposed() || jobFrameworkCCombo == null || jobFrameworkCCombo.isDisposed()
                            || statusText == null || statusText.isDisposed() || descriptionText == null
                            || descriptionText.isDisposed()) {
                        return;
                    }
                    DeleteActionCache.getInstance().closeOpenedEditor(repositoryObject);
                    if (!nameText.getText().equals(StringUtils.trimToEmpty(repositoryObject.getLabel()))) {
                        // / property.setLabel(nameText.getText());
                    }
                    if (!versionText.getText().equals(StringUtils.trimToEmpty(repositoryObject.getVersion()))) {
                        property.setVersion(versionText.getText());
                    }
                    if (!jobTypeCCombo.getText().equals(StringUtils.trimToEmpty(jobTypeValue))) {
                    }
                    if (!jobFrameworkCCombo.getText().equals(StringUtils.trimToEmpty(frameworkValue))) {
                        if (property.getAdditionalProperties().containsKey(ConvertJobsUtil.FRAMEWORK)) {
                            property.getAdditionalProperties().removeKey(ConvertJobsUtil.FRAMEWORK);
                        }
                        property.getAdditionalProperties().put(ConvertJobsUtil.FRAMEWORK, jobFrameworkCCombo.getText());
                    }
                    if (!purposeText.getText().equals(StringUtils.trimToEmpty(repositoryObject.getPurpose()))) {
                        property.setPurpose(purposeText.getText());
                    }
                    if (!statusText.getText().equals(StringUtils.trimToEmpty(repositoryObject.getStatusCode()))) {
                        property.setStatusCode(statusText.getText());
                    }
                    if (!descriptionText.getText().equals(StringUtils.trimToEmpty(repositoryObject.getDescription()))) {
                        property.setDescription(descriptionText.getText());
                    }
                    // Convert
                    Item newItem = ConvertJobsUtil.createOperation(nameText.getText(), jobTypeCCombo.getText(),
                            jobFrameworkCCombo.getText(), repositoryObject);
                    final IProxyRepositoryFactory proxyRepositoryFactory = CoreRuntimePlugin.getInstance()
                            .getProxyRepositoryFactory();

                    IWorkspaceRunnable runnable = new IWorkspaceRunnable() {

                        @Override
                        public void run(final IProgressMonitor monitor) throws CoreException {
                            try {
                                proxyRepositoryFactory.unlock(repositoryObject);
                                proxyRepositoryFactory.deleteObjectPhysical(repositoryObject);
                                proxyRepositoryFactory.saveProject(ProjectManager.getInstance().getCurrentProject());
                            } catch (PersistenceException e1) {
                                e1.printStackTrace();
                            } catch (LoginException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    // unlockObject();
                    // alreadyEditedByUser = true; // to avoid 2 calls of unlock

                    IWorkspace workspace = ResourcesPlugin.getWorkspace();
                    try {
                        ISchedulingRule schedulingRule = workspace.getRoot();
                        // the update the project files need to be done in the workspace runnable to avoid all
                        // notification
                        // of changes before the end of the modifications.
                        workspace.run(runnable, schedulingRule, IWorkspace.AVOID_UPDATE, null);
                    } catch (CoreException e1) {
                        MessageBoxExceptionHandler.process(e1.getCause());
                    }

                    if (newItem != null) {
                        openEditorOperation(newItem);
                    }
                }
            });
        }
    }

    protected void evaluateTextField() {
        if (nameText == null || nameText.isDisposed() || versionText == null || versionText.isDisposed() || purposeText == null
                || purposeText.isDisposed()) {
            return;
        }
        if (!nameText.getText().equals(StringUtils.trimToEmpty(repositoryObject.getLabel()))) {
            btnConfirm.setEnabled(true);
        } else if (!versionText.getText().equals(StringUtils.trimToEmpty(repositoryObject.getVersion()))) {
            btnConfirm.setEnabled(true);
        } else if (!jobTypeCCombo.getText().equals(StringUtils.trimToEmpty(jobTypeValue))) {
            btnConfirm.setEnabled(true);
        } else if (!jobFrameworkCCombo.getText().equals(StringUtils.trimToEmpty(frameworkValue))) {
            btnConfirm.setEnabled(true);
        } else if (!purposeText.getText().equals(StringUtils.trimToEmpty(repositoryObject.getPurpose()))) {
            btnConfirm.setEnabled(true);
        } else if (!statusText.getText().equals(StringUtils.trimToEmpty(repositoryObject.getStatusCode()))) {
            btnConfirm.setEnabled(true);
        } else if (!descriptionText.getText().equals(StringUtils.trimToEmpty(repositoryObject.getDescription()))) {
            btnConfirm.setEnabled(true);
        }
    }

    public void openEditorOperation(Item item) {
        if (item != null) {
            try {
                ERepositoryObjectType repObjType = ERepositoryObjectType.getItemType(item);
                IJobEditorHandler editorInputFactory = JobEditorHandlerManager.getInstance().extractEditorInputFactory(
                        repObjType.getType());
                editorInputFactory.openJobEditor(editorInputFactory.createJobEditorInput(item, true));
            } catch (PartInitException e) {
                e.printStackTrace();
            } catch (PersistenceException e) {
                e.printStackTrace();
            }
        }
    }
}
