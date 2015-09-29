package org.talend.presentation.onboarding.resource.di.handlers;

import java.net.URL;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.Bundle;
import org.talend.commons.exception.CommonExceptionHandler;
import org.talend.presentation.onboarding.resource.di.i18n.Messages;
import org.talend.presentation.onboarding.resource.di.utils.OnBoardingDIResourceConstants;
import org.talend.presentation.onboarding.resource.di.utils.OnBoardingDIResourceUtil;
import org.talend.presentation.onboarding.resource.di.utils.TalendImportUtil;
import org.talend.repository.ui.dialog.AProgressMonitorDialogWithCancel;

public class ImportDemoJobHandler extends AbstractHandler {

    private Shell shell;

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        boolean alreadyImportDemoJob = OnBoardingDIResourceUtil.getPreferenceStore().getBoolean(
                OnBoardingDIResourceConstants.PREFERENCE_ALREADY_IMPORT_DEMO_JOB);
        if (alreadyImportDemoJob) {
            return null;
        }

        doImport();

        return null;
    }

    private void doImport() {
        Display.getDefault().syncExec(new Runnable() {

            @Override
            public void run() {
                shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
            }
        });
        AProgressMonitorDialogWithCancel<Boolean> dialog = new AProgressMonitorDialogWithCancel<Boolean>(shell) {

            @Override
            protected Boolean runWithCancel(IProgressMonitor monitor) throws Throwable {
                try {
                    Bundle bundle = Platform.getBundle(OnBoardingDIResourceConstants.PLUGIN_ID);
                    URL resourceURL = bundle.getEntry(OnBoardingDIResourceConstants.DEMO_JOB_PATH);
                    boolean isSucceed = false;
                    try {
                        String demoZipPath = FileLocator.toFileURL(resourceURL).getPath();
                        isSucceed = TalendImportUtil.importItems(demoZipPath, monitor, false, true);
                    } catch (Throwable e) {
                        CommonExceptionHandler.process(e);
                    }
                    if (isSucceed) {
                        OnBoardingDIResourceUtil.getPreferenceStore().setValue(
                                OnBoardingDIResourceConstants.PREFERENCE_ALREADY_IMPORT_DEMO_JOB, true);
                    }
                } catch (Throwable e) {
                    CommonExceptionHandler.process(e);
                }
                return null;
            }
        };
        try {
            dialog.run(Messages.getString("importDemoJobHandler.import.excuting"), //$NON-NLS-1$
                    Messages.getString("importDemoJobHandler.import.waitingFinish"), true); //$NON-NLS-1$
        } catch (Throwable e) {
            CommonExceptionHandler.process(e);
        }
    }
}
