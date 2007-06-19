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
package org.talend.designer.mapper.ui.visualmap.table;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.talend.commons.ui.swt.proposal.ProposalUtils;
import org.talend.commons.ui.swt.tableviewer.TableViewerCreator;
import org.talend.commons.ui.swt.tableviewer.TableViewerCreatorColumn;
import org.talend.commons.ui.swt.tableviewer.behavior.IColumnImageProvider;
import org.talend.commons.ui.utils.ControlUtils;
import org.talend.commons.ui.ws.WindowSystem;
import org.talend.commons.utils.data.bean.IBeanPropertyAccessors;
import org.talend.core.language.ECodeLanguage;
import org.talend.designer.abstractmap.model.tableentry.IColumnEntry;
import org.talend.designer.mapper.i18n.Messages;
import org.talend.designer.mapper.language.LanguageProvider;
import org.talend.designer.mapper.managers.MapperManager;
import org.talend.designer.mapper.model.table.ILookupType;
import org.talend.designer.mapper.model.table.InputTable;
import org.talend.designer.mapper.model.table.TMAP_MATCHING_MODE;
import org.talend.designer.mapper.model.tableentry.ExpressionFilterEntry;
import org.talend.designer.mapper.model.tableentry.InputColumnTableEntry;
import org.talend.designer.mapper.ui.image.ImageInfo;
import org.talend.designer.mapper.ui.image.ImageProviderMapper;
import org.talend.designer.mapper.ui.proposal.expression.ExpressionProposalProvider;
import org.talend.designer.mapper.ui.visualmap.zone.Zone;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * $Id$
 * 
 */
public class InputDataMapTableView extends DataMapTableView {

    private ToolItem dropDownItem;

    private Menu menu = null;

    private ILookupType previousMultipleModeSelected = getInputTable().getMatchingMode() != null
            && getInputTable().getMatchingMode() != TMAP_MATCHING_MODE.ALL_ROWS ? getInputTable().getMatchingMode()
            : TMAP_MATCHING_MODE.UNIQUE_MATCH;

    private boolean previousStateAtLeastOneHashKey;

    public InputDataMapTableView(Composite parent, int style, InputTable inputTable, MapperManager mapperManager) {
        super(parent, style, inputTable, mapperManager);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.mapper.ui.visualmap.table.DataMapTableView#createContent()
     */
    @Override
    protected void createContent() {
        createTableForColumns();

        createExpressionFilter();

    }

    @Override
    public void initColumnsOfTableColumns(TableViewerCreator tableViewerCreatorForColumns) {
        boolean isMainConnection = ((InputTable) getDataMapTable()).isMainConnection();
        TableViewerCreatorColumn column = null;
        if (!isMainConnection) {

            column = new TableViewerCreatorColumn(tableViewerCreatorForColumns);
            column.setTitle(Messages.getString("InputDataMapTableView.columnTitle.Expr")); //$NON-NLS-1$
            column.setId(DataMapTableView.ID_EXPRESSION_COLUMN);
            column.setBeanPropertyAccessors(new IBeanPropertyAccessors<InputColumnTableEntry, String>() {

                public String get(InputColumnTableEntry bean) {
                    return bean.getExpression();
                }

                public void set(InputColumnTableEntry bean, String value) {
                    bean.setExpression(value);
                    mapperManager.getProblemsManager().checkProblemsForTableEntry(bean, true);
                }

            });
            column.setModifiable(!mapperManager.componentIsReadOnly());
            column.setDefaultInternalValue(""); //$NON-NLS-1$
            createExpressionCellEditor(tableViewerCreatorForColumns, column, new Zone[] { Zone.INPUTS }, false);
            column.setWeight(COLUMN_EXPRESSION_SIZE_WEIGHT);
            column.setImageProvider(new IColumnImageProvider<InputColumnTableEntry>() {

                public Image getImage(InputColumnTableEntry bean) {
                    if (bean.getExpression() != null && !bean.getExpression().trim().equals("")) {
                        if (LanguageProvider.getCurrentLanguage().getCodeLanguage() == ECodeLanguage.JAVA) {
                            if (mapperManager.isAdvancedMap()) {
                                return ImageProviderMapper.getImage(ImageInfo.LOOKUP_KEY_ICON);
                            } else {
                                return null;
                            }
                        } else {
                            return null;
                        }
                    } else {
                        return null;
                    }
                }

            });
        }

        column = new TableViewerCreatorColumn(tableViewerCreatorForColumns);
        column.setTitle(DataMapTableView.COLUMN_NAME);
        column.setId(DataMapTableView.ID_NAME_COLUMN);
        column.setBeanPropertyAccessors(new IBeanPropertyAccessors<InputColumnTableEntry, String>() {

            public String get(InputColumnTableEntry bean) {
                return bean.getMetadataColumn().getLabel();
            }

            public void set(InputColumnTableEntry bean, String value) {
                bean.getMetadataColumn().setLabel(value);
            }

        });
        column.setWeight(COLUMN_NAME_SIZE_WEIGHT);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.mapper.ui.visualmap.table.DataMapTableView#initTableConstraints()
     */
    @Override
    protected void initTableFilters() {
        // nothing
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.mapper.ui.visualmap.table.DataMapTableView#addEntriesActionsComponents()
     */
    @Override
    protected boolean addToolItems() {
        Point realToolbarSize = getRealToolbarSize();
        if (!getInputTable().isMainConnection()) {

            if (mapperManager.isAdvancedMap()) {

                // DROP DOWN
                dropDownItem = new ToolItem(toolBarActions, SWT.DROP_DOWN | SWT.BORDER);
                dropDownItem.setEnabled(!mapperManager.componentIsReadOnly());
                refreshLabelForLookupTypeDropDown();
                dropDownItem.addSelectionListener(new DropDownSelectionListener());
                realToolbarSize.x += 70;

                // // UNIQUE_MATCH button
                // uniqueCheck = new ToolItem(toolBarActions, SWT.CHECK);
                // uniqueCheck.setEnabled(!mapperManager.componentIsReadOnly());
                // realToolbarSize.x += 70;
                // uniqueCheck.setToolTipText(Messages
                // .getString("InputDataMapTableView.widgetTooltip.uniqueLookupTooltip")); //$NON-NLS-1$
                // boolean isUniqueLookup = getInputTable().isUniqueMatch();
                // // Image image = ImageProviderMapper.getImage(isUniqueLookup ? ImageInfo.CHECKED_ICON :
                // // ImageInfo.UNCHECKED_ICON);
                // Image image = ImageProviderMapper.getImage(isUniqueLookup ? ImageInfo.CHECKED_ICON
                // : ImageInfo.UNCHECKED_ICON);
                // if (WindowSystem.isGTK()) {
                // uniqueCheck.setImage(image);
                // uniqueCheck.setHotImage(image);
                // } else {
                // uniqueCheck.setImage(ImageProviderMapper.getImage(ImageInfo.UNCHECKED_ICON));
                // uniqueCheck.setHotImage(image);
                // }
                // uniqueCheck.setSelection(isUniqueLookup);
                // uniqueCheck.setText(Messages.getString("InputDataMapTableView.widgetTooltip.uniqueLabel"));
                // //$NON-NLS-1$
                //
                // uniqueCheck.addSelectionListener(new SelectionListener() {
                //
                // public void widgetDefaultSelected(SelectionEvent e) {
                // }
                //
                // public void widgetSelected(SelectionEvent e) {
                // Image image = null;
                // if (uniqueCheck.getSelection()) {
                // getInputTable().setUniqueMatch(true);
                // dropDownItem.setEnabled(false);
                // dropDownItem.setText(TMAP_MULTIPLE_MATCHING_MODE.LAST_MATCH.getLabel());
                // image = ImageProviderMapper.getImage(ImageInfo.CHECKED_ICON);
                // } else {
                // getInputTable().setUniqueMatch(false);
                // dropDownItem.setText(getInputTable().getMultipleMatchingMode().getLabel());
                // dropDownItem.setEnabled(true);
                // image = ImageProviderMapper.getImage(ImageInfo.UNCHECKED_ICON);
                // }
                // if (WindowSystem.isGTK()) {
                // uniqueCheck.setImage(image);
                // uniqueCheck.setHotImage(image);
                // } else {
                // uniqueCheck.setHotImage(image);
                // }
                // }
                //
                // });
            }

            // Inner join button
            final ToolItem rejectConstraintCheck = new ToolItem(toolBarActions, SWT.CHECK);
            rejectConstraintCheck.setEnabled(!mapperManager.componentIsReadOnly());
            realToolbarSize.x += 70;
            rejectConstraintCheck.setToolTipText(Messages
                    .getString("InputDataMapTableView.widgetTooltip.rejectMainRow")); //$NON-NLS-1$
            boolean isInnerJoin = getInputTable().isInnerJoin();
            // Image image = ImageProviderMapper.getImage(isInnerJoin ? ImageInfo.CHECKED_ICON :
            // ImageInfo.UNCHECKED_ICON);
            Image image = ImageProviderMapper.getImage(isInnerJoin ? ImageInfo.CHECKED_ICON : ImageInfo.UNCHECKED_ICON);
            if (WindowSystem.isGTK()) {
                rejectConstraintCheck.setImage(image);
                rejectConstraintCheck.setHotImage(image);
            } else {
                rejectConstraintCheck.setImage(ImageProviderMapper.getImage(ImageInfo.UNCHECKED_ICON));
                rejectConstraintCheck.setHotImage(image);
            }
            rejectConstraintCheck.setSelection(isInnerJoin);
            rejectConstraintCheck.setText(Messages.getString("InputDataMapTableView.widgetTooltip.innerJoin")); //$NON-NLS-1$

            rejectConstraintCheck.addSelectionListener(new SelectionListener() {

                public void widgetDefaultSelected(SelectionEvent e) {
                }

                public void widgetSelected(SelectionEvent e) {
                    Image image = null;
                    if (rejectConstraintCheck.getSelection()) {
                        getInputTable().setInnerJoin(true);
                        image = ImageProviderMapper.getImage(ImageInfo.CHECKED_ICON);
                    } else {
                        getInputTable().setInnerJoin(false);
                        image = ImageProviderMapper.getImage(ImageInfo.UNCHECKED_ICON);
                    }
                    if (WindowSystem.isGTK()) {
                        rejectConstraintCheck.setImage(image);
                        rejectConstraintCheck.setHotImage(image);
                    } else {
                        rejectConstraintCheck.setHotImage(image);
                    }
                }

            });

        }
        if (mapperManager.isAdvancedMap()) {
            createActivateFilterCheck();
            return true;
        } else {
            if (getInputTable().isMainConnection()) {
                return false;
            } else {
                return true;
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.mapper.ui.visualmap.table.DataMapTableView#getZone()
     */
    @Override
    public Zone getZone() {
        return Zone.INPUTS;
    }

    public InputTable getInputTable() {
        return (InputTable) abstractDataMapTable;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.mapper.ui.visualmap.table.DataMapTableView#toolbarNeededToBeRightStyle()
     */
    @Override
    public boolean toolbarNeedToHaveRightStyle() {
        return !getInputTable().isMainConnection();
    }

    /**
     * Listens to widgetSelected() events on SWT.DROP_DOWN type ToolItems and opens/closes a menu when appropriate.
     */
    class DropDownSelectionListener implements SelectionListener {

        private boolean visible;

        public void widgetSelected(SelectionEvent event) {
            System.out.println("widgetSelected");
            // Create the menu if it has not already been created
            if (menu == null) {
                // Lazy create the menu.
                menu = new Menu(getShell());

                ILookupType[] availableJoins = TMAP_MATCHING_MODE.values();

                for (int i = 0; i < availableJoins.length; ++i) {
                    final String text = availableJoins[i].getLabel();
                    if (text.length() != 0) {
                        MenuItem menuItem = new MenuItem(menu, SWT.NONE);
                        menuItem.setData(availableJoins[i]);
                        menuItem.setText(text);
                        enableMenuItemMatchingMode(menuItem);
                        if (availableJoins[i] == getInputTable().getMatchingMode()) {
                            menuItem.setImage(ImageProviderMapper.getImage(ImageInfo.CHECKED_ICON));
                        }

                        /*
                         * Add a menu selection listener so that the menu is hidden when the user selects an item from
                         * the drop down menu.
                         */
                        menuItem.addSelectionListener(new SelectionAdapter() {

                            public void widgetSelected(SelectionEvent e) {
                                MenuItem menuItem = (MenuItem) e.widget;
                                selectMenuItem(menuItem);
                                ILookupType lookupType = (ILookupType) menuItem.getData();
                                getInputTable().setMatchingMode(lookupType);
                                setMenuVisible(false);
                                refreshLabelForLookupTypeDropDown();
                            }

                        });
                    } else {
                        new MenuItem(menu, SWT.SEPARATOR);
                    }
                }
            }

            /**
             * A selection event will be fired when a drop down tool item is selected in the main area and in the drop
             * down arrow. Examine the event detail to determine where the widget was selected.
             */

            /*
             * The drop down arrow was selected.
             */
            if (visible) {
                // Hide the menu to give the Arrow the appearance of being a toggle button.
                setMenuVisible(false);
            } else {
                // Position the menu below and vertically aligned with the the drop down tool button.
                final ToolItem toolItem = (ToolItem) event.widget;
                final ToolBar toolBar = toolItem.getParent();

                Rectangle toolItemBounds = toolItem.getBounds();
                Point point = toolBar.toDisplay(new Point(toolItemBounds.x, toolItemBounds.y));
                menu.setLocation(point.x, point.y + toolItemBounds.height);
                MenuItem[] menuItems = menu.getItems();
                for (int i = 0; i < menuItems.length; i++) {
                    MenuItem menuItem = menuItems[i];
                    enableMenuItemMatchingMode(menuItem);
                }
                setMenuVisible(true);
            }
        }

        private void setMenuVisible(boolean visible) {
            menu.setVisible(visible);
            this.visible = visible;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
         */
        public void widgetDefaultSelected(SelectionEvent e) {
            System.out.println("widgetDefaultSelected");
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.dbmap.ui.visualmap.table.DataMapTableView#hasDropDownToolBarItem()
     */
    @Override
    public boolean hasDropDownToolBarItem() {
        return !getInputTable().isMainConnection() && mapperManager.isAdvancedMap();
    }

    public void refreshLabelForLookupTypeDropDown() {
        ILookupType matchingMode = getInputTable().getMatchingMode();
        if (matchingMode != TMAP_MATCHING_MODE.ALL_ROWS) {
            previousMultipleModeSelected = matchingMode;
        }
        String text = matchingMode.getLabel();
        dropDownItem.setText(text);
        dropDownItem.setToolTipText(text);

        Point sizeToolBar = toolBarActions.computeSize(SWT.DEFAULT, SWT.DEFAULT);
        GridData gridData = (GridData) toolBarActions.getLayoutData();
        if (gridData != null) {
            gridData.widthHint = sizeToolBar.x;
            // gridData.widthHint -= 60;
            toolBarActions.getParent().layout();
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.dbmap.ui.visualmap.table.DataMapTableView#checkChangementsAfterNewExpressionApplied()
     */
    @Override
    public void checkChangementsAfterEntryModifiedOrAdded(boolean forceEvaluation) {
        super.checkChangementsAfterEntryModifiedOrAdded(forceEvaluation);
        if (!getInputTable().isMainConnection() && mapperManager.isAdvancedMap()) {
            boolean stateAtLeastOneHashKey = mapperManager.isTableHasAtLeastOneHashKey(getInputTable());
            if (forceEvaluation || previousStateAtLeastOneHashKey != stateAtLeastOneHashKey) {
                if (stateAtLeastOneHashKey) {
                    dropDownItem.setText(previousMultipleModeSelected.getLabel());
                    dropDownItem.setToolTipText(previousMultipleModeSelected.getLabel());
                    getInputTable().setMatchingMode(previousMultipleModeSelected);
                    selectMenuItem(previousMultipleModeSelected);
                } else {
                    dropDownItem.setText(TMAP_MATCHING_MODE.ALL_ROWS.getLabel());
                    dropDownItem.setToolTipText(TMAP_MATCHING_MODE.ALL_ROWS.getLabel());
                    getInputTable().setMatchingMode(TMAP_MATCHING_MODE.ALL_ROWS);
                    selectMenuItem(TMAP_MATCHING_MODE.ALL_ROWS);
                }
                previousStateAtLeastOneHashKey = stateAtLeastOneHashKey;
            }
            refreshLabelForLookupTypeDropDown();

        }

    }

    /**
     * DOC amaumont Comment method "enableMenuItemMatchingMode".
     * 
     * @param availableJoins
     * @param i
     * @param menuItem
     */
    private void enableMenuItemMatchingMode(MenuItem menuItem) {
        ILookupType lookupType = (ILookupType) menuItem.getData();
        if (lookupType == TMAP_MATCHING_MODE.ALL_ROWS
                && getInputTable().getMatchingMode() != TMAP_MATCHING_MODE.ALL_ROWS
                || lookupType != TMAP_MATCHING_MODE.ALL_ROWS
                && getInputTable().getMatchingMode() == TMAP_MATCHING_MODE.ALL_ROWS) {
            menuItem.setEnabled(false);
        } else {
            menuItem.setEnabled(true);
        }
    }

    /**
     * DOC amaumont Comment method "selectMenuItem".
     * 
     * @param menuItem
     */
    private void selectMenuItem(MenuItem menuItem) {
        if (menu != null) {
            MenuItem[] menuItems = menu.getItems();
            for (int j = 0; j < menuItems.length; j++) {
                menuItems[j].setImage(null);
            }
            menuItem.setImage(ImageProviderMapper.getImage(ImageInfo.CHECKED_ICON));
        }
    }

    /**
     * DOC amaumont Comment method "selectMenuItem".
     * 
     * @param menuItem
     */
    private void selectMenuItem(ILookupType lookupType) {
        if (menu != null) {
            MenuItem[] menuItems = menu.getItems();
            for (int j = 0; j < menuItems.length; j++) {
                if (menuItems[j].getData() == lookupType) {
                    selectMenuItem(menuItems[j]);
                    break;
                }
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.mapper.ui.visualmap.table.DataMapTableView#getValidZonesForExpressionFilterField()
     */
    @Override
    protected Zone[] getValidZonesForExpressionFilterField() {
        return new Zone[] { Zone.INPUTS };
    }

    /**
     * DOC amaumont Comment method "init".
     */
    public void loaded() {
        if (mapperManager.isAdvancedMap()) {
            configureExpressionFilter();
            if (!getInputTable().isMainConnection()) {
                checkChangementsAfterEntryModifiedOrAdded(true);
            }
        }

    }

}
