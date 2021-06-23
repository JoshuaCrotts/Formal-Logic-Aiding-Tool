package com.flat.view.action;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 *
 * @author Christopher Brantley <c_brantl@uncg.edu>
 */
public class ResizePane {
    
    /**
     * The direction that the pane should be moved.
     */
    public enum Orientation {
        HORIZONTAL("Horizontal", "resizePaneHorizontal"),
        VERTICAL("Vertical", "resizePaneVertical");
        
        private String value;
        private String id;

        /**
         * 
         * @param _value Display value for output.
         * @param _id The id of this pane that is used as a CSS selector.
         */
        private Orientation (String _value, String _id) {
            this.value = _value;
            this.id = _id;
        }

        // Getters for object's attributes.
        public String getValue() {
            return this.value;
        }

        public String getId() {
            return this.id;
        }

        public void setValue(String value) {
            this.value = value;
        }

        // Setters for object's attributes.
        public void setId(String id) {
            this.id = id;
        }

    }

    /**
     * The side of the object that this resize pane will be attached to.
    */
    public enum Side {
        TOP("Top"),
        RIGHT("Right"),
        BOTTOM("Bottom"),
        LEFT("Left");

        private String value;

        /**
         * 
         * @param _value Display value for output.
         */
        private Side (String _value) {
            this.value = _value;
        }

        // Getters for object's attributes.
        public String getValue() {
            return this.value;
        }

        // Setters for object's attributes.
        public void setValue(String value) {
            this.value = value;
        }

    }

    private Pane parentPane = new Pane();
    private Pane activeObject;
    private Orientation orientation;
    private Side side;
    private double clickedWidth = 0;
    private double clickedHeight = 0;
    private double clickedX = 0;
    private double clickedY = 0;

    /**
     * 
     * @param _activeObject The pane that will be resized.
     * @param _orientation Which direction the pane will be resized.
     * @param _side Which side of the object the resize pane will be attached.
     */
    public ResizePane (Pane _activeObject, Orientation _orientation, Side _side) {
        this.orientation = _orientation;
        this.side = _side;
        this.parentPane.setId(_orientation.getId());
        this.activeObject = _activeObject;
        this.setPositionAction();
        this.setResizeAction();
    }

    /**
     * Sets the coordinate values, relative to the scene, when the resize pane 
     * is pressed.
     */
    private void setPositionAction () {
        this.parentPane.setOnMousePressed((MouseEvent event) -> {
            this.clickedWidth = this.activeObject.getWidth();
            this.clickedHeight = this.activeObject.getHeight();
            this.clickedX = event.getSceneX();
            this.clickedY = event.getSceneY();
        });
    }

    /**
     * Checks constraints on resizing and then resizes object, when the resize
     * pane is clicked and dragged.
     */
    private void setResizeAction () {
        this.parentPane.setOnMouseDragged((MouseEvent event) -> {
            if (this.checkBoundaries(event) == (BoundaryCheck.SATISFIED))
                this.resizeObject(event);
        });
    }

    /**
     * Updates the size of the active object based on which side the resize pane
     * was placed. The active object is expanded to the cursor location.
     * @param _event Event thrown that contains data on the cursor and mouse.
     */
    private void resizeObject (MouseEvent _event) {
        switch (this.side) {
            case LEFT:
                this.activeObject.setPrefWidth(this.clickedWidth + (this.clickedX - _event.getSceneX()));
                break;
            case RIGHT:
                this.activeObject.setPrefWidth(this.clickedWidth - (this.clickedX - _event.getSceneX()));
                break;
            case TOP:
                this.activeObject.setPrefHeight(this.clickedHeight + (this.clickedY - _event.getSceneY()));
                break;
            case BOTTOM:
                this.activeObject.setPrefHeight(this.clickedHeight - (this.clickedY - _event.getSceneY()));
                break;
        }
    }

    /**
     * Output for constraint checks on the boundary.
     */
    private enum BoundaryCheck {
        SATISFIED,
        UNSATISIFED,
    }

    /**
     * Checks that if the active object is resized that the constraints on the
     * boundary are maintained.
     * @param _event Event thrown that contains data on the cursor and mouse.
     * @return Returns whether the boundary constraint is satisfied.
     */
    private BoundaryCheck checkBoundaries (MouseEvent _event) {
        switch (this.side) {
            case LEFT:
                if ((this.clickedWidth + (this.clickedX - _event.getSceneX()) > this.activeObject.getParent().getBoundsInParent().getMaxX() / 2) 
                        || (this.clickedWidth + (this.clickedX - _event.getSceneX()) < this.parentPane.getWidth())) 
                    return BoundaryCheck.UNSATISIFED;
                break;
            case RIGHT:
                if ((this.clickedWidth - (this.clickedX - _event.getSceneX()) > this.activeObject.getParent().getBoundsInParent().getMaxX() / 2)
                    || (this.clickedWidth - (this.clickedX - _event.getSceneX()) < this.parentPane.getWidth()))
                    return BoundaryCheck.UNSATISIFED;
                break;
            case TOP:
                if ((this.clickedHeight + (this.clickedY - _event.getSceneY()) > this.activeObject.getParent().getBoundsInParent().getMaxY() / 2)
                    || (this.clickedHeight + (this.clickedY - _event.getSceneY()) < this.parentPane.getHeight()))
                    return BoundaryCheck.UNSATISIFED;
                break;
            case BOTTOM:
                if ((this.clickedHeight - (this.clickedY - _event.getSceneY()) > this.activeObject.getParent().getBoundsInParent().getMaxY() / 2)
                        || (this.clickedHeight - (this.clickedY - _event.getSceneY()) < this.parentPane.getHeight()))
                    return BoundaryCheck.UNSATISIFED;
                break;
        }
        return BoundaryCheck.SATISFIED;
    }

    // Getters for object's attributes.
    public Pane getParentPane() {
        return parentPane;
    }

    public Pane getActiveObject() {
        return activeObject;
    }

    // Setters for object's attributes.
    public void setParentPane(Pane parentPane) {
        this.parentPane = parentPane;
    }

    public void setActiveObject(Pane activeObject) {
        this.activeObject = activeObject;
    }

}