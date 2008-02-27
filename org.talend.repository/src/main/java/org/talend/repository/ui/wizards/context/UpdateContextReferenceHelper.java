// ============================================================================
//
// Copyright (C) 2006-2007 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.repository.ui.wizards.context;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.PlatformUI;
import org.talend.commons.exception.PersistenceException;
import org.talend.core.context.UpdateRunJobComponentContextHelper;
import org.talend.core.model.context.JobContextManager;
import org.talend.core.model.context.UpdateContextVariablesHelper;
import org.talend.core.model.process.IContext;
import org.talend.core.model.process.IContextManager;
import org.talend.core.model.process.IContextParameter;
import org.talend.core.model.process.IProcess;
import org.talend.core.model.properties.ContextItem;
import org.talend.core.model.properties.ProcessItem;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.core.model.repository.IRepositoryObject;
import org.talend.designer.core.model.utils.emf.talendfile.ContextParameterType;
import org.talend.designer.core.model.utils.emf.talendfile.ContextType;
import org.talend.repository.RepositoryPlugin;
import org.talend.repository.model.IProxyRepositoryFactory;
import org.talend.repository.model.ProxyRepositoryFactory;

/**
 * DOC ggu class global comment. Detailled comment
 */
public final class UpdateContextReferenceHelper {

    public static synchronized void updateJobContextReference(JobContextManager curRepositoryManager, ContextItem curContextItem) {
        final Set<String> repositoryVarsSet = getCurRepositoryContextVarsName(curRepositoryManager);
        final Map<String, String> renamedMap = curRepositoryManager.getNameMap();

        String curContextName = curContextItem.getProperty().getLabel();
        String curContextId = curContextItem.getProperty().getId();
        // if rename the context variable naem, update the item context.
        if (renamedMap != null && !renamedMap.isEmpty()) {
            updateProcessItemforVariablesReference(curRepositoryManager, curContextId, repositoryVarsSet, renamedMap);
        }
        // update the opened context.
        updateOpenedJobforVariablesReference(curRepositoryManager, curContextName, repositoryVarsSet, renamedMap);
    }

    /*
     * update the opened job context.
     */
    private static void updateOpenedJobforVariablesReference(JobContextManager curRepositoryManager, final String contextName,
            final Set<String> repositoryVarsSet, final Map<String, String> renamedMap) {
        if (contextName == null) {
            return;
        }

        IEditorReference[] reference = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getEditorReferences();
        List<IProcess> processes = RepositoryPlugin.getDefault().getDesignerCoreService().getOpenedProcess(reference);
        for (IProcess process : processes) {
            IContextManager openedContextManager = process.getContextManager();

            boolean modified = false; // checked the done again flag.

            // update paramters reference for context. for 2608
            Map<String, String> realRenamedVarMap = new HashMap<String, String>();
            boolean recordFlag = false;

            for (IContext context : openedContextManager.getListContext()) {
                for (IContextParameter contextParameter : context.getContextParameterList()) {
                    if (!contextParameter.isBuiltIn()) {
                        String source = contextParameter.getSource();
                        if (source.equals(contextName)) {
                            // check variable reference of current repository context
                            String newName = getRenamedVarName(contextParameter.getName(), renamedMap);
                            if (newName != null) {
                                // for 2608
                                if (!recordFlag && !realRenamedVarMap.containsKey(newName)) {
                                    realRenamedVarMap.put(newName, contextParameter.getName());
                                }

                                // have renamed this variable.
                                contextParameter.setName(newName);
                                updateVariableAttributions(curRepositoryManager, contextParameter, context.getName());

                            } else if (!isExistedVarInCurRepositoryContext(contextParameter.getName(), repositoryVarsSet)) {
                                // check the nonexistent variable and set to built-in.
                                contextParameter.setSource(IContextParameter.BUILT_IN);
                            } else {
                                // update other variable value.
                                updateVariableAttributions(curRepositoryManager, contextParameter, context.getName());

                            }
                            modified = true;
                        }
                    }
                }
                recordFlag = true;
                if (!modified) {
                    // not existed current source variables.
                    break;
                }
            }
            if (modified) {
                // update tRunJob component reference
                UpdateRunJobComponentContextHelper.updateOpenedJobRunJobComponentReference(processes, realRenamedVarMap, process
                        .getLabel(), null);
                // update parameter for current job and nodes in it. for 2608
                UpdateContextVariablesHelper.updateProcessForRenamed(process, realRenamedVarMap);
            }
        }

    }

    private static Set<String> getCurRepositoryContextVarsName(JobContextManager curRepositoryManager) {
        if (curRepositoryManager == null) {
            return Collections.emptySet();
        }
        Set<String> varNameSet = new HashSet<String>();
        for (IContextParameter param : curRepositoryManager.getDefaultContext().getContextParameterList()) {
            varNameSet.add(param.getName());
        }
        return varNameSet;
    }

    private static String getRenamedVarName(final String varName, Map<String, String> renamedMap) {
        if (varName == null || renamedMap == null || renamedMap.isEmpty()) {
            return null;
        }

        Set<String> keySet = renamedMap.keySet();
        for (String newName : keySet) {
            String oldName = renamedMap.get(newName);
            if (varName.equals(oldName)) {
                return newName;
            }
        }
        return null;
    }

    private static boolean isExistedVarInCurRepositoryContext(final String varName, final Set<String> repositoryVarsSet) {
        if (repositoryVarsSet == null || repositoryVarsSet.isEmpty()) {
            return false;
        }
        if (repositoryVarsSet.contains(varName)) {
            return true;
        }
        return false;
    }

    /*
     * if not existed current variable in the context. get the default to set.
     */
    private static boolean updateVariableAttributions(JobContextManager curRepositoryManager, Object modifiedParameter,
            final String jobContextname) {
        if (curRepositoryManager == null || modifiedParameter == null) {
            return false;
        }
        IContext context = null;
        if (jobContextname != null) {
            context = curRepositoryManager.getContext(jobContextname);
        }
        if (context == null) {
            context = curRepositoryManager.getDefaultContext();
        }
        if (context != null) {
            if (modifiedParameter instanceof IContextParameter) {
                // update the opened job
                IContextParameter jobContextParameter = (IContextParameter) modifiedParameter;
                IContextParameter parameter = context.getContextParameter(jobContextParameter.getName());

                jobContextParameter.setComment(parameter.getComment());
                jobContextParameter.setPrompt(parameter.getPrompt());
                jobContextParameter.setPromptNeeded(parameter.isPromptNeeded());
                jobContextParameter.setScriptCode(parameter.getScriptCode());
                jobContextParameter.setType(parameter.getType());
                jobContextParameter.setValue(parameter.getValue());
                if (parameter.getValueList() != null) {
                    jobContextParameter.setValueList(parameter.getValueList());
                }

                return true;
            } else if (modifiedParameter instanceof ContextParameterType) {
                // update the item
                ContextParameterType itemParameterType = (ContextParameterType) modifiedParameter;
                IContextParameter parameter = context.getContextParameter(itemParameterType.getName());

                itemParameterType.setComment(parameter.getComment());
                itemParameterType.setPrompt(parameter.getPrompt());
                itemParameterType.setPromptNeeded(parameter.isPromptNeeded());
                itemParameterType.setType(parameter.getType());
                itemParameterType.setValue(parameter.getValue());

                return true;

            }
        }
        return false;
    }

    /*
     * update the item.
     */
    private static void updateProcessItemforVariablesReference(JobContextManager curRepositoryManager, final String curContextId,
            final Set<String> repositoryVarsSet, final Map<String, String> renamedMap) {
        IProxyRepositoryFactory factory = factory = ProxyRepositoryFactory.getInstance();

        try {
            List<IRepositoryObject> repositoryObjList = factory.getAll(ERepositoryObjectType.PROCESS, true);
            for (IRepositoryObject repositoryObj : repositoryObjList) {
                List<IRepositoryObject> allVersion = factory.getAllVersion(repositoryObj.getId());
                for (IRepositoryObject object : allVersion) {
                    ProcessItem item = (ProcessItem) object.getProperty().getItem();

                    if (item != null) {
                        boolean modified = false; // checked the done again flag.

                        // update paramters reference for context. for 2608
                        Map<String, String> realRenamedVarMap = new HashMap<String, String>();
                        boolean recordFlag = false;

                        for (ContextType contextType : (List<ContextType>) item.getProcess().getContext()) {
                            for (ContextParameterType parameterType : (List<ContextParameterType>) contextType
                                    .getContextParameter()) {
                                String repositoryId = parameterType.getRepositoryContextId();
                                if (repositoryId != null && repositoryId.equals(curContextId)) {
                                    // found the current reference variable
                                    String newName = getRenamedVarName(parameterType.getName(), renamedMap);
                                    if (newName != null) {
                                        // for 2608
                                        if (!recordFlag && !realRenamedVarMap.containsKey(newName)) {
                                            realRenamedVarMap.put(newName, parameterType.getName());
                                        }

                                        // have renamed this variable.
                                        parameterType.setName(newName);
                                        updateVariableAttributions(curRepositoryManager, parameterType, contextType.getName());
                                    } else if (!isExistedVarInCurRepositoryContext(parameterType.getName(), repositoryVarsSet)) {
                                        // check the nonexistent variable and set to built-in.
                                        parameterType.setRepositoryContextId(null);
                                    } else {
                                        // update other variable value.
                                        updateVariableAttributions(curRepositoryManager, parameterType, contextType.getName());
                                    }
                                    modified = true;
                                }
                            }
                            recordFlag = true;
                            if (!modified) {
                                break;
                            }
                        }
                        if (modified) {
                            // update tRunJob component reference
                            UpdateRunJobComponentContextHelper.updateItemRunJobComponentReference(factory, realRenamedVarMap,
                                    item.getProperty().getLabel(), null);

                            // update parameter reference for current item and nodes. for 2608
                            UpdateContextVariablesHelper.updateProcessForRenamed(item.getProcess(), realRenamedVarMap);

                            factory.save(item);
                        }
                    }
                }
            }
        } catch (PersistenceException e) {
            e.printStackTrace();
        }

    }
}
