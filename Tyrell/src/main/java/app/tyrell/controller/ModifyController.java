package app.tyrell.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ModifyController {

    @FXML
    private AnchorPane modifyRoot;

    private void anchorInit(AnchorPane pane){
        modifyRoot.getChildren().clear();
        modifyRoot.getChildren().add(pane);
    }

    private void controllerInit(AddController controller){
        controller.init();
        controller.setModifyRootPanel(modifyRoot);
    }

    private void controllerInit(UpdateController controller){
        controller.setModifyRootPanel(modifyRoot);
    }

    private void controllerInit(DeleteController controller){
        controller.setModifyRootPanel(modifyRoot);
    }


    @FXML
    private void add(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("NewAdd.fxml"));
            AnchorPane addPanel = loader.load();
            AddController controller = loader.getController();

            controllerInit(controller);
            anchorInit(addPanel);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void modify(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("NewUpdate.fxml"));
            AnchorPane updatePane = loader.load();
            UpdateController controller = loader.getController();

            controllerInit(controller);
            anchorInit(updatePane);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void delete(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("NewDelete.fxml"));
            AnchorPane deletePane = loader.load();
            DeleteController controller = loader.getController();

            controllerInit(controller);
            anchorInit(deletePane);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
