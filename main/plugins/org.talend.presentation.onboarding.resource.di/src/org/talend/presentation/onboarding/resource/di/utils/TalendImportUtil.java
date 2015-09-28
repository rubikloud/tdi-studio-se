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

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.talend.commons.exception.CommonExceptionHandler;
import org.talend.commons.runtime.model.repository.ERepositoryStatus;
import org.talend.core.model.properties.Item;
import org.talend.core.model.utils.RepositoryManagerHelper;
import org.talend.core.repository.model.ProxyRepositoryFactory;
import org.talend.core.repository.seeker.RepositorySeekerManager;
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
            final ResourcesManager resourcesManager, final Shell shell, final boolean overwrite) {
        List<ImportItem> selectedItemRecords = new ArrayList<ImportItem>();
        nodesBuilder.clear();
        if (resourcesManager != null) { // if resource is not init successfully.
            try {
                List<ImportItem> items = importManager.populateImportingItems(resourcesManager, overwrite,
                        new NullProgressMonitor(), true);
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

    public static boolean importItems(final Shell shell, String path, final boolean overwrite) {
        File srcFile = new File(path);
        final ImportExportHandlersManager importManager = new ImportExportHandlersManager();
        final ResourcesManager resourcesManager = ResourcesManagerFactory.getInstance().createResourcesManager();
        resourcesManager.collectPath2Object(srcFile);
        final List<ImportItem> items = populateItems(importManager, resourcesManager, shell, overwrite);
        List<String> itemIds = new ArrayList<String>();
        try {
            for (ImportItem itemRecord : items) {
                Item item = itemRecord.getProperty().getItem();
                itemIds.add(item.getProperty().getId());
                IProxyRepositoryFactory factory = ProxyRepositoryFactory.getInstance();
                if (item.getState().isLocked()) {
                    factory.unlock(item);
                }
                ERepositoryStatus status = factory.getStatus(item);
                if (status != null && status == ERepositoryStatus.LOCK_BY_USER) {
                    factory.unlock(item);
                }
            }
            importManager.importItemRecords(new NullProgressMonitor(), resourcesManager, items, overwrite,
                    nodesBuilder.getAllImportItemRecords(), null);
        } catch (Exception e) {
            CommonExceptionHandler.process(e);
        } finally {
            // clean
            if (resourcesManager != null) {
                resourcesManager.closeResource();
            }
            nodesBuilder.clear();
        }
        doSelection(itemIds);
        return true;
    }

    private static void doSelection(List<String> itemIds) {
        List<IRepositoryNode> nodes = new ArrayList<IRepositoryNode>();
        RepositorySeekerManager repSeekerManager = RepositorySeekerManager.getInstance();
        for (String itemId : itemIds) {
            IRepositoryNode repoViewNode = repSeekerManager.searchRepoViewNode(itemId);
            if (repoViewNode != null) {
                nodes.add(repoViewNode);
            }
        }
        IRepositoryView repositoryView = RepositoryManagerHelper.findRepositoryView();
        repositoryView.getViewer().setSelection(new StructuredSelection(nodes));
    }

}
