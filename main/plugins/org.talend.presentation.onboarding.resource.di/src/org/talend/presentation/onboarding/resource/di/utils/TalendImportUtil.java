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
package org.talend.presentation.onboarding.resource.di.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipFile;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.talend.commons.exception.CommonExceptionHandler;
import org.talend.commons.exception.PersistenceException;
import org.talend.commons.runtime.model.repository.ERepositoryStatus;
import org.talend.core.model.properties.Item;
import org.talend.core.model.properties.ProcessItem;
import org.talend.core.model.properties.Property;
import org.talend.core.model.utils.RepositoryManagerHelper;
import org.talend.core.repository.model.ProxyRepositoryFactory;
import org.talend.core.repository.seeker.RepositorySeekerManager;
import org.talend.core.ui.editor.JobEditorInput;
import org.talend.designer.core.ui.AbstractMultiPageTalendEditor;
import org.talend.designer.core.ui.MultiPageTalendEditor;
import org.talend.designer.core.ui.editor.ProcessEditorInput;
import org.talend.repository.items.importexport.handlers.ImportExportHandlersManager;
import org.talend.repository.items.importexport.handlers.model.ImportItem;
import org.talend.repository.items.importexport.manager.ResourcesManager;
import org.talend.repository.items.importexport.ui.managers.ResourcesManagerFactory;
import org.talend.repository.items.importexport.wizard.models.ImportNodesBuilder;
import org.talend.repository.model.IProxyRepositoryFactory;
import org.talend.repository.model.IRepositoryNode;
import org.talend.repository.ui.views.IRepositoryView;

/**
 * created by ycbai on 2015年1月29日 Detailled comment
 *
 */
public class TalendImportUtil {

    private static ImportNodesBuilder nodesBuilder = new ImportNodesBuilder();

    private static List<ImportItem> populateItems(final ImportExportHandlersManager importManager,
            final ResourcesManager resourcesManager, IProgressMonitor monitor, final boolean overwrite) {
        List<ImportItem> selectedItemRecords = new ArrayList<ImportItem>();
        nodesBuilder.clear();
        if (resourcesManager != null) { // if resource is not init successfully.
            try {
                // List<ImportItem> items = importManager.populateImportingItems(resourcesManager, overwrite,
                // new NullProgressMonitor(), true);
                List<ImportItem> items = importManager.populateImportingItems(resourcesManager, overwrite, monitor, true);
                nodesBuilder.addItems(items);
            } catch (Exception e) {
                CommonExceptionHandler.process(e);
            }
        }
        ImportItem[] allImportItemRecords = nodesBuilder.getAllImportItemRecords();
        selectedItemRecords.addAll(Arrays.asList(allImportItemRecords));
        Iterator<ImportItem> itemIterator = selectedItemRecords.iterator();
        while (itemIterator.hasNext()) {
            ImportItem item = itemIterator.next();
            if (!item.isValid()) {
                itemIterator.remove();
            }
        }
        return selectedItemRecords;
    }

    public static boolean importItems(String zipPath, IProgressMonitor monitor, final boolean overwrite, final boolean openThem)
            throws IOException {
        // File srcFile = new File(zipPath);
        ZipFile srcZipFile = new ZipFile(zipPath);
        final ImportExportHandlersManager importManager = new ImportExportHandlersManager();
        final ResourcesManager resourcesManager = ResourcesManagerFactory.getInstance().createResourcesManager(srcZipFile);
        // resourcesManager.collectPath2Object(srcFile);
        resourcesManager.collectPath2Object(srcZipFile);
        final List<ImportItem> items = populateItems(importManager, resourcesManager, monitor, overwrite);
        final List<String> itemIds = new ArrayList<String>();
        try {
            for (ImportItem itemRecord : items) {
                Item item = itemRecord.getProperty().getItem();
                if (item instanceof ProcessItem) {
                    // only select jobs
                    itemIds.add(item.getProperty().getId());
                }
                IProxyRepositoryFactory factory = ProxyRepositoryFactory.getInstance();
                if (item.getState().isLocked()) {
                    factory.unlock(item);
                }
                ERepositoryStatus status = factory.getStatus(item);
                if (status != null && status == ERepositoryStatus.LOCK_BY_USER) {
                    factory.unlock(item);
                }
            }
            // importManager.importItemRecords(new NullProgressMonitor(), resourcesManager, items, overwrite,
            // nodesBuilder.getAllImportItemRecords(), null);
            importManager.importItemRecords(monitor, resourcesManager, items, overwrite, nodesBuilder.getAllImportItemRecords(),
                    null);
        } catch (Exception e) {
            CommonExceptionHandler.process(e);
        } finally {
            // clean
            if (resourcesManager != null) {
                resourcesManager.closeResource();
            }
            nodesBuilder.clear();
        }
        Display.getDefault().syncExec(new Runnable() {

            @Override
            public void run() {
                doSelection(itemIds, openThem);
            }
        });
        return true;
    }

    private static void doSelection(List<String> itemIds, final boolean openThem) {
        List<IRepositoryNode> nodes = new ArrayList<IRepositoryNode>();
        RepositorySeekerManager repSeekerManager = RepositorySeekerManager.getInstance();
        for (String itemId : itemIds) {
            IRepositoryNode repoViewNode = repSeekerManager.searchRepoViewNode(itemId);
            if (repoViewNode != null) {
                nodes.add(repoViewNode);
            }
        }

        if (openThem) {
            openJobs(nodes);
        }

        IRepositoryView repositoryView = RepositoryManagerHelper.findRepositoryView();
        repositoryView.getViewer().setSelection(new StructuredSelection(nodes));
    }

    private static void openJobs(List<IRepositoryNode> nodes) {
        IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
        for (IRepositoryNode node : nodes) {
            Property property = node.getObject().getProperty();
            if (property != null) {
                Item item = property.getItem();
                if (!(item instanceof ProcessItem)) {
                    continue;
                }
                try {
                    ProcessItem processItem = (ProcessItem) item;
                    final JobEditorInput fileEditorInput = getEditorInput(processItem);
                    // checkUnLoadedNodeForProcess(fileEditorInput);
                    final IEditorPart editorPart = page.findEditor(fileEditorInput);
                    if (editorPart == null) {
                        fileEditorInput.setRepositoryNode(node);
                        page.openEditor(fileEditorInput, getEditorId(), true);
                    } else {
                        ((AbstractMultiPageTalendEditor) editorPart).setReadOnly(fileEditorInput.setForceReadOnly(false));
                        page.activate(editorPart);
                    }
                } catch (Throwable e) {
                    CommonExceptionHandler.process(e);
                }
            }
        }
    }

    private static JobEditorInput getEditorInput(final ProcessItem processItem) throws PersistenceException {
        return new ProcessEditorInput(processItem, true, true);
    }

    private static String getEditorId() {
        return MultiPageTalendEditor.ID;
    }
}
