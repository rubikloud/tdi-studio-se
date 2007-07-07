// ============================================================================
//
// Talend Community Edition
//
// Copyright (C) 2006-2007 Talend - www.talend.com
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
//
// ============================================================================
package org.talend.repository;

import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.talend.core.CorePlugin;
import org.talend.core.model.components.IComponentsFactory;
import org.talend.core.model.repository.IRepositoryObject;
import org.talend.repository.model.ComponentsFactoryProvider;
import org.talend.repository.model.IProxyRepositoryFactory;
import org.talend.repository.model.IRepositoryService;
import org.talend.repository.model.ProxyRepositoryFactory;
import org.talend.repository.model.RepositoryNode;
import org.talend.repository.model.RepositoryNodeUtilities;
import org.talend.repository.ui.views.RepositoryView;
import org.talend.repository.ui.utils.ColumnNameValidator;
import org.talend.repository.utils.RepositoryPathProvider;

/**
 * DOC qian class global comment. Detailled comment <br/>
 * 
 * $Id: talend-code-templates.xml 1 2006-09-29 17:06:40 +0000 (星期五, 29 九月 2006) nrousseau $
 * 
 */
/**
 * DOC qian class global comment. Implementation for IRepositoryService. <br/>
 * 
 * $Id: talend-code-templates.xml 1 2006-09-29 17:06:40 +0000 (星期五, 29 九月 2006) nrousseau $
 * 
 */
public class RepositoryService implements IRepositoryService {

    public IComponentsFactory getComponentsFactory() {
        return ComponentsFactoryProvider.getInstance();
    }

    public IPath getPathFileName(String folderName, String fileName) {
        return RepositoryPathProvider.getPathFileName(folderName, fileName);
    }

    public IProxyRepositoryFactory getProxyRepositoryFactory() {
        return ProxyRepositoryFactory.getInstance();
    }

    public IPath getRepositoryPath(RepositoryNode node) {
        return RepositoryNodeUtilities.getPath(node);
    }

    ChangeProcessor changeProcessor = new ChangeProcessor();

    public void registerRepositoryChangedListener(IRepositoryChangedListener listener) {
        changeProcessor.addRepositoryChangedListener(listener);
    }

    public void removeRepositoryChangedListener(IRepositoryChangedListener listener) {
        changeProcessor.removeRepositoryChangedListener(listener);
    }

    public void repositoryChanged(RepositoryElementDelta delta) {
        changeProcessor.repositoryChanged(delta);
    }

    // This method is used for the Action in RepositoryView to synchronize the sqlBuilder.
    // see DataBaseWizard, DatabaseTableWizard, AContextualAction
    public void notifySQLBuilder(List<IRepositoryObject> list) {
        IRepositoryChangedListener listener = (IRepositoryChangedListener) RepositoryView.show();
        CorePlugin.getDefault().getRepositoryService().removeRepositoryChangedListener(listener);
        for (Iterator<IRepositoryObject> iter = list.iterator(); iter.hasNext();) {
            IRepositoryObject element = iter.next();
            repositoryChanged(new RepositoryElementDelta(element));
        }
        CorePlugin.getDefault().getRepositoryService().registerRepositoryChangedListener(listener);
    }
    
    public String validateColumnName(String columnName, int index) {
        return ColumnNameValidator.validateColumnNameFormat(columnName,index);
    }

}
