/**
 * Notes:
 * https://o7planning.org/en/11115/javafx-contextmenu-tutorial
 * https://o7planning.org/en/11537/javafx-textinputdialog-tutorial
 * https://code.makery.ch/blog/javafx-dialogs-official/
 */
package StandardAnimation;

import Standard.AdjacencyList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ContextMenuGraph {

    private PaneGraph paneGraph;


    private ContextMenu contextMenu;

    private CheckMenuItem checkMenuItemEditMode;

    private MenuItem menuItemAnimationRun;

    private MenuItem menuItemAddFXShapeWrapperPseudo;

    private Map<String, AdjacencyList> mapStringAndAdjacencyList = new HashMap<>();

    public ContextMenuGraph(PaneGraph paneGraph) {
        this.paneGraph = paneGraph;

        contextMenu = new ContextMenu();

        checkMenuItemEditMode = new CheckMenuItem("Edit Mode");
        checkMenuItemEditMode.setOnAction(eventEventHandlerEditMode());
        checkMenuItemEditMode.setSelected(false);

        menuItemAnimationRun = new MenuItem("Run Animation");
        menuItemAnimationRun.setOnAction(eventEventHandlerActionEventAnimationRun());

        menuItemAddFXShapeWrapperPseudo = new MenuItem("Add New Node");

        FXAdjacencyListPseudo fxAdjacencyListPseudo = this.paneGraph.getFxAdjacencyListPseudo();

        menuItemAddFXShapeWrapperPseudo.setOnAction(eventEventHandlerMouseAddFXShapeWrapperPseudo(this.paneGraph, fxAdjacencyListPseudo));

        contextMenu.getItems().add(checkMenuItemEditMode);
        contextMenu.getItems().add(menuItemAnimationRun);
        contextMenu.getItems().add(menuItemAddFXShapeWrapperPseudo);
        contextMenu.getItems().add(new SeparatorMenuItem());

        // FIXME: THE PROGRAM IS TOO BIG SO NEED TO CANCEL ON EDITING UNTIL I HAVE TIME TO FIX IT
        checkMenuItemEditMode.setVisible(false);
        menuItemAddFXShapeWrapperPseudo.setVisible(false);

        // Add Right Click menu options
        this.paneGraph.setOnContextMenuRequested(eventEventHandlerContextMenu(contextMenu));

    }

    private EventHandler<ContextMenuEvent> eventEventHandlerContextMenu(ContextMenu contextMenu) {
        return new EventHandler<ContextMenuEvent>() {

            @Override
            public void handle(ContextMenuEvent event) {
                contextMenu.show(paneGraph.getRectangleBackground(), event.getScreenX(), event.getScreenY());
            }
        };
    }

    private EventHandler<ActionEvent> eventEventHandlerActionEventAnimationRun() {
        return new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
//                fxAdjacencyList.createParallelTransitionChangeTimeBased();
                paneGraph.getFxAdjacencyListActual().createParallelTransitionDFS();
                paneGraph.getFxAdjacencyListActual().getParallelTransition().play();
            }
        };
    }


    private EventHandler<ActionEvent> eventEventHandlerEditMode() {
        return new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                // If Edit mode is elected, then hide menuItemAnimationRun
                if (checkMenuItemEditMode.isSelected()) {
                    menuItemAnimationRun.setVisible(false);
                    menuItemAddFXShapeWrapperPseudo.setVisible(true);

                    paneGraph.enableEditMode();

                }
                // If Edit mode is Not selected, then hide menuItemAnimationRun
                else {
                    menuItemAnimationRun.setVisible(true);
                    menuItemAddFXShapeWrapperPseudo.setVisible(false);

                    paneGraph.enableNormalMode();

                }
            }
        };
    }

    // Could look cleaner
    private EventHandler<ActionEvent> eventEventHandlerMouseAddFXShapeWrapperPseudo(PaneGraph paneGraph, FXAdjacencyListPseudo fxAdjacencyListPseudo) {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TextInputDialog textInputDialog = new TextInputDialog("");
                textInputDialog.setHeaderText(null);
                textInputDialog.setGraphic(null);
                textInputDialog.setTitle("New Event");
//                        textInputDialog.setHeaderText("Test");
                textInputDialog.setContentText("Enter Event Name");
                Optional<String> eventName = textInputDialog.showAndWait();
                if (eventName.isPresent()) {

                    Point2D point2D = new Point2D(paneGraph.getWidth()/2, paneGraph.getHeight()/2);

                    FXShapeWrapperPseudo fxShapeWrapperPseudo = fxAdjacencyListPseudo.createFXShapeWrapperPseudoUser(paneGraph, eventName.get(), point2D);

                    paneGraph.generateFXShapeWrapperPredefinedSpawn(fxShapeWrapperPseudo);
                }
            }
        };
    }

    private EventHandler<ActionEvent> eventEventHandlerMouseAddAdjacencyListExample(PaneGraph paneGraph , String menuItemName) {
        Map<String, AdjacencyList> mapStringAndAdjacencyList = this.mapStringAndAdjacencyList;

        AdjacencyList adjacencyList = mapStringAndAdjacencyList.get(menuItemName);

        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                paneGraph.runCustomAdjacencyList(adjacencyList);
            }
        };
    }


    public CheckMenuItem getCheckMenuItemEditMode() {
        return checkMenuItemEditMode;
    }

    public void loadAdjacencyListExample(Map<String, AdjacencyList> mapStringAndAdjacencyList) {

        for (Map.Entry<String, AdjacencyList> entry : mapStringAndAdjacencyList.entrySet()) {
            this.mapStringAndAdjacencyList.put(entry.getKey(),entry.getValue());

            MenuItem menuItem = new MenuItem(entry.getKey());
            menuItem.setOnAction(eventEventHandlerMouseAddAdjacencyListExample(paneGraph, menuItem.getText()));
            contextMenu.getItems().add(menuItem);
        }
    }

    public ContextMenu getContextMenu() {
        return contextMenu;
    }
}
