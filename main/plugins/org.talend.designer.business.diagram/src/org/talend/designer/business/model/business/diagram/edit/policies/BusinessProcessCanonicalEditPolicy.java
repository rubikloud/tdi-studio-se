package org.talend.designer.business.model.business.diagram.edit.policies;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.commands.DeferredLayoutCommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CanonicalConnectionEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateConnectionViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.View;
import org.talend.designer.business.model.business.BaseBusinessItemRelationship;
import org.talend.designer.business.model.business.BusinessPackage;
import org.talend.designer.business.model.business.BusinessProcess;
import org.talend.designer.business.model.business.diagram.edit.parts.ActionBusinessItemEditPart;
import org.talend.designer.business.model.business.diagram.edit.parts.ActorBusinessItemEditPart;
import org.talend.designer.business.model.business.diagram.edit.parts.BidirectionalBusinessItemRelationshipEditPart;
import org.talend.designer.business.model.business.diagram.edit.parts.BusinessItemRelationshipEditPart;
import org.talend.designer.business.model.business.diagram.edit.parts.BusinessProcessEditPart;
import org.talend.designer.business.model.business.diagram.edit.parts.DataBusinessItemEditPart;
import org.talend.designer.business.model.business.diagram.edit.parts.DatabaseBusinessItemEditPart;
import org.talend.designer.business.model.business.diagram.edit.parts.DecisionBusinessItemEditPart;
import org.talend.designer.business.model.business.diagram.edit.parts.DirectionalBusinessItemRelationshipEditPart;
import org.talend.designer.business.model.business.diagram.edit.parts.DocumentBusinessItemEditPart;
import org.talend.designer.business.model.business.diagram.edit.parts.EllipseBusinessItemEditPart;
import org.talend.designer.business.model.business.diagram.edit.parts.GearBusinessItemEditPart;
import org.talend.designer.business.model.business.diagram.edit.parts.InputBusinessItemEditPart;
import org.talend.designer.business.model.business.diagram.edit.parts.ListBusinessItemEditPart;
import org.talend.designer.business.model.business.diagram.edit.parts.TerminalBusinessItemEditPart;
import org.talend.designer.business.model.business.diagram.part.BusinessVisualIDRegistry;

/**
 * @generated
 */
public class BusinessProcessCanonicalEditPolicy extends CanonicalConnectionEditPolicy {

    /**
     * @generated
     */
    protected List getSemanticChildrenList() {
        List result = new LinkedList();
        EObject modelObject = ((View) getHost().getModel()).getElement();
        View viewObject = (View) getHost().getModel();
        EObject nextValue;
        int nodeVID;
        for (Iterator values = ((BusinessProcess) modelObject).getBusinessItems().iterator(); values.hasNext();) {
            nextValue = (EObject) values.next();
            nodeVID = BusinessVisualIDRegistry.getNodeVisualID(viewObject, nextValue);
            switch (nodeVID) {
            case ActionBusinessItemEditPart.VISUAL_ID: {
                result.add(nextValue);
                break;
            }
            case TerminalBusinessItemEditPart.VISUAL_ID: {
                result.add(nextValue);
                break;
            }
            case DocumentBusinessItemEditPart.VISUAL_ID: {
                result.add(nextValue);
                break;
            }
            case DatabaseBusinessItemEditPart.VISUAL_ID: {
                result.add(nextValue);
                break;
            }
            case ListBusinessItemEditPart.VISUAL_ID: {
                result.add(nextValue);
                break;
            }
            case DataBusinessItemEditPart.VISUAL_ID: {
                result.add(nextValue);
                break;
            }
            case InputBusinessItemEditPart.VISUAL_ID: {
                result.add(nextValue);
                break;
            }
            case DecisionBusinessItemEditPart.VISUAL_ID: {
                result.add(nextValue);
                break;
            }
            case ActorBusinessItemEditPart.VISUAL_ID: {
                result.add(nextValue);
                break;
            }
            case EllipseBusinessItemEditPart.VISUAL_ID: {
                result.add(nextValue);
                break;
            }
            case GearBusinessItemEditPart.VISUAL_ID: {
                result.add(nextValue);
                break;
            }
            }
        }
        return result;
    }

    /**
     * @generated
     */
    protected boolean shouldDeleteView(View view) {
        return view.isSetElement() && view.getElement() != null && view.getElement().eIsProxy();
    }

    /**
     * @generated
     */
    protected String getDefaultFactoryHint() {
        return null;
    }

    /**
     * @generated
     */
    protected List getSemanticConnectionsList() {
        return Collections.EMPTY_LIST;
    }

    /**
     * @generated
     */
    protected EObject getSourceElement(EObject relationship) {
        return null;
    }

    /**
     * @generated
     */
    protected EObject getTargetElement(EObject relationship) {
        return null;
    }

    /**
     * @generated
     */
    protected boolean shouldIncludeConnection(Edge connector, Collection children) {
        return false;
    }

    /**
     * @generated
     */
    protected void refreshSemantic() {
        List createdViews = new LinkedList();
        createdViews.addAll(refreshSemanticChildren());
        List createdConnectionViews = new LinkedList();
        createdConnectionViews.addAll(refreshSemanticConnections());
        createdConnectionViews.addAll(refreshConnections());

        if (createdViews.size() > 1) {
            // perform a layout of the container
            DeferredLayoutCommand layoutCmd = new DeferredLayoutCommand(host().getEditingDomain(), createdViews, host());
            executeCommand(new ICommandProxy(layoutCmd));
        }

        createdViews.addAll(createdConnectionViews);
        makeViewsImmutable(createdViews);
    }

    /**
     * @generated
     */
    private Collection myLinkDescriptors = new LinkedList();

    /**
     * @generated
     */
    private Map myEObject2ViewMap = new HashMap();

    /**
     * @generated
     */
    private Collection refreshConnections() {
        try {
            collectAllLinks(getDiagram());
            Collection existingLinks = new LinkedList(getDiagram().getEdges());
            for (Iterator diagramLinks = existingLinks.iterator(); diagramLinks.hasNext();) {
                Edge nextDiagramLink = (Edge) diagramLinks.next();
                EObject diagramLinkObject = nextDiagramLink.getElement();
                EObject diagramLinkSrc = nextDiagramLink.getSource().getElement();
                EObject diagramLinkDst = nextDiagramLink.getTarget().getElement();
                int diagramLinkVisualID = BusinessVisualIDRegistry.getVisualID(nextDiagramLink);
                for (Iterator modelLinkDescriptors = myLinkDescriptors.iterator(); modelLinkDescriptors.hasNext();) {
                    LinkDescriptor nextLinkDescriptor = (LinkDescriptor) modelLinkDescriptors.next();
                    if (diagramLinkObject == nextLinkDescriptor.getLinkElement()
                            && diagramLinkSrc == nextLinkDescriptor.getSource()
                            && diagramLinkDst == nextLinkDescriptor.getDestination()
                            && diagramLinkVisualID == nextLinkDescriptor.getVisualID()) {
                        diagramLinks.remove();
                        modelLinkDescriptors.remove();
                    }
                }
            }
            deleteViews(existingLinks.iterator());
            return createConnections(myLinkDescriptors);
        } finally {
            myLinkDescriptors.clear();
            myEObject2ViewMap.clear();
        }
    }

    /**
     * @generated
     */
    private void collectAllLinks(View view) {
        EObject modelElement = view.getElement();
        int diagramElementVisualID = BusinessVisualIDRegistry.getVisualID(view);
        switch (diagramElementVisualID) {
        case ActionBusinessItemEditPart.VISUAL_ID:
        case TerminalBusinessItemEditPart.VISUAL_ID:
        case DocumentBusinessItemEditPart.VISUAL_ID:
        case DatabaseBusinessItemEditPart.VISUAL_ID:
        case ListBusinessItemEditPart.VISUAL_ID:
        case DataBusinessItemEditPart.VISUAL_ID:
        case InputBusinessItemEditPart.VISUAL_ID:
        case DecisionBusinessItemEditPart.VISUAL_ID:
        case ActorBusinessItemEditPart.VISUAL_ID:
        case EllipseBusinessItemEditPart.VISUAL_ID:
        case GearBusinessItemEditPart.VISUAL_ID:
        case BusinessProcessEditPart.VISUAL_ID: {
            myEObject2ViewMap.put(modelElement, view);
            storeLinks(modelElement, getDiagram());
        }
        default: {
        }
            for (Iterator children = view.getChildren().iterator(); children.hasNext();) {
                View childView = (View) children.next();
                collectAllLinks(childView);
            }
        }
    }

    /**
     * @generated
     */
    private Collection createConnections(Collection linkDescriptors) {
        if (linkDescriptors.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        List adapters = new LinkedList();
        for (Iterator linkDescriptorsIterator = linkDescriptors.iterator(); linkDescriptorsIterator.hasNext();) {
            final LinkDescriptor nextLinkDescriptor = (LinkDescriptor) linkDescriptorsIterator.next();
            EditPart sourceEditPart = getEditPartFor(nextLinkDescriptor.getSource());
            EditPart targetEditPart = getEditPartFor(nextLinkDescriptor.getDestination());
            if (sourceEditPart == null || targetEditPart == null) {
                continue;
            }
            CreateConnectionViewRequest.ConnectionViewDescriptor descriptor = new CreateConnectionViewRequest.ConnectionViewDescriptor(
                    nextLinkDescriptor.getSemanticAdapter(), null, ViewUtil.APPEND, false,
                    ((IGraphicalEditPart) getHost()).getDiagramPreferencesHint());
            CreateConnectionViewRequest ccr = new CreateConnectionViewRequest(descriptor);
            ccr.setType(RequestConstants.REQ_CONNECTION_START);
            ccr.setSourceEditPart(sourceEditPart);
            sourceEditPart.getCommand(ccr);
            ccr.setTargetEditPart(targetEditPart);
            ccr.setType(RequestConstants.REQ_CONNECTION_END);
            Command cmd = targetEditPart.getCommand(ccr);
            if (cmd != null && cmd.canExecute()) {
                executeCommand(cmd);
                IAdaptable viewAdapter = (IAdaptable) ccr.getNewObject();
                if (viewAdapter != null) {
                    adapters.add(viewAdapter);
                }
            }
        }
        return adapters;
    }

    /**
     * @generated
     */
    private EditPart getEditPartFor(EObject modelElement) {
        View view = (View) myEObject2ViewMap.get(modelElement);
        if (view != null) {
            return (EditPart) getHost().getViewer().getEditPartRegistry().get(view);
        }
        return null;
    }

    /**
     * @generated
     */
    private void storeLinks(EObject container, Diagram diagram) {
        EClass containerMetaclass = container.eClass();
        storeFeatureModelFacetLinks(container, containerMetaclass, diagram);
        storeTypeModelFacetLinks(container, containerMetaclass);
    }

    /**
     * @generated
     */
    private void storeTypeModelFacetLinks(EObject container, EClass containerMetaclass) {
        if (BusinessPackage.eINSTANCE.getBusinessProcess().isSuperTypeOf(containerMetaclass)) {
            for (Iterator values = ((BusinessProcess) container).getBusinessItems().iterator(); values.hasNext();) {
                EObject nextValue = ((EObject) values.next());
                int linkVID = BusinessVisualIDRegistry.getLinkWithClassVisualID(nextValue);
                if (BusinessItemRelationshipEditPart.VISUAL_ID == linkVID) {
                    Object structuralFeatureResult = ((BaseBusinessItemRelationship) nextValue).getTarget();
                    if (structuralFeatureResult instanceof EObject) {
                        EObject dst = (EObject) structuralFeatureResult;
                        structuralFeatureResult = ((BaseBusinessItemRelationship) nextValue).getSource();
                        if (structuralFeatureResult instanceof EObject) {
                            EObject src = (EObject) structuralFeatureResult;
                            myLinkDescriptors.add(new LinkDescriptor(src, dst, nextValue, linkVID));
                        }
                    }
                }
            }
        }
        if (BusinessPackage.eINSTANCE.getBusinessProcess().isSuperTypeOf(containerMetaclass)) {
            for (Iterator values = ((BusinessProcess) container).getBusinessItems().iterator(); values.hasNext();) {
                EObject nextValue = ((EObject) values.next());
                int linkVID = BusinessVisualIDRegistry.getLinkWithClassVisualID(nextValue);
                if (DirectionalBusinessItemRelationshipEditPart.VISUAL_ID == linkVID) {
                    Object structuralFeatureResult = ((BaseBusinessItemRelationship) nextValue).getTarget();
                    if (structuralFeatureResult instanceof EObject) {
                        EObject dst = (EObject) structuralFeatureResult;
                        structuralFeatureResult = ((BaseBusinessItemRelationship) nextValue).getSource();
                        if (structuralFeatureResult instanceof EObject) {
                            EObject src = (EObject) structuralFeatureResult;
                            myLinkDescriptors.add(new LinkDescriptor(src, dst, nextValue, linkVID));
                        }
                    }
                }
            }
        }
        if (BusinessPackage.eINSTANCE.getBusinessProcess().isSuperTypeOf(containerMetaclass)) {
            for (Iterator values = ((BusinessProcess) container).getBusinessItems().iterator(); values.hasNext();) {
                EObject nextValue = ((EObject) values.next());
                int linkVID = BusinessVisualIDRegistry.getLinkWithClassVisualID(nextValue);
                if (BidirectionalBusinessItemRelationshipEditPart.VISUAL_ID == linkVID) {
                    Object structuralFeatureResult = ((BaseBusinessItemRelationship) nextValue).getTarget();
                    if (structuralFeatureResult instanceof EObject) {
                        EObject dst = (EObject) structuralFeatureResult;
                        structuralFeatureResult = ((BaseBusinessItemRelationship) nextValue).getSource();
                        if (structuralFeatureResult instanceof EObject) {
                            EObject src = (EObject) structuralFeatureResult;
                            myLinkDescriptors.add(new LinkDescriptor(src, dst, nextValue, linkVID));
                        }
                    }
                }
            }
        }
    }

    /**
     * @generated
     */
    private void storeFeatureModelFacetLinks(EObject container, EClass containerMetaclass, Diagram diagram) {

    }

    /**
     * @generated
     */
    private Diagram getDiagram() {
        return ((View) getHost().getModel()).getDiagram();
    }

    /**
     * @generated
     */
    private class LinkDescriptor {

        /**
         * @generated
         */
        private EObject mySource;

        /**
         * @generated
         */
        private EObject myDestination;

        /**
         * @generated
         */
        private EObject myLinkElement;

        /**
         * @generated
         */
        private int myVisualID;

        /**
         * @generated
         */
        private IAdaptable mySemanticAdapter;

        /**
         * @generated
         */
        protected LinkDescriptor(EObject source, EObject destination, EObject linkElement, int linkVID) {
            this(source, destination, linkVID);
            myLinkElement = linkElement;
            mySemanticAdapter = new EObjectAdapter(linkElement);
        }

        /**
         * @generated
         */
        protected LinkDescriptor(EObject source, EObject destination, IElementType elementType, int linkVID) {
            this(source, destination, linkVID);
            myLinkElement = null;
            final IElementType elementTypeCopy = elementType;
            mySemanticAdapter = new IAdaptable() {

                public Object getAdapter(Class adapter) {
                    if (IElementType.class.equals(adapter)) {
                        return elementTypeCopy;
                    }
                    return null;
                }
            };
        }

        /**
         * @generated
         */
        private LinkDescriptor(EObject source, EObject destination, int linkVID) {
            mySource = source;
            myDestination = destination;
            myVisualID = linkVID;
        }

        /**
         * @generated
         */
        protected EObject getSource() {
            return mySource;
        }

        /**
         * @generated
         */
        protected EObject getDestination() {
            return myDestination;
        }

        /**
         * @generated
         */
        protected EObject getLinkElement() {
            return myLinkElement;
        }

        /**
         * @generated
         */
        protected int getVisualID() {
            return myVisualID;
        }

        /**
         * @generated
         */
        protected IAdaptable getSemanticAdapter() {
            return mySemanticAdapter;
        }
    }

}
