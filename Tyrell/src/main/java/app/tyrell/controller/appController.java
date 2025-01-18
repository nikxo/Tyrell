package app.tyrell.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class appController implements Initializable{

    private String Active;
    private final HashMap<String, AnchorPane> active = new HashMap<>();

    @FXML
    private AnchorPane overViewRoot;
    @FXML
    private AnchorPane modifyRoot;
    @FXML
    private AnchorPane statsRoot;

    public appController() {
        this.Active = "";
    }


    private void switchView(String viewName) {
        this.Active = viewName;
        verify();

        if (active.containsKey(viewName)) {
            active.get(viewName).setVisible(true);
            active.get(viewName).toFront();
        }
    }

    private void verify() {
        for (String menuItem : active.keySet()) {
            if (!menuItem.equals(this.Active)) {
                active.get(menuItem).setVisible(false);
                active.get(menuItem).toBack();
            }
        }
    }

    private void loadOverview() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("NewOverview.fxml"));
            AnchorPane OverView = loader.load();
            OverviewController overviewController = loader.getController();
            System.out.println("loadOverview");
            overviewController.init();
            overViewRoot.getChildren().add(OverView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadModify() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("NewModify.fxml"));
            AnchorPane modify = loader.load();
            System.out.println("loadModify");
            modifyRoot.getChildren().clear();
            modifyRoot.getChildren().add(modify);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadStats(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("NewStats.fxml"));
            AnchorPane stats = loader.load();
            System.out.println("loadStats");
            statsRoot.getChildren().clear();
            statsRoot.getChildren().add(stats);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showModify(){
        switchView("Modify");
    }

    @FXML
    private void showOverview(){
        switchView("OverView");
    }

    @FXML
    private void showStats(){
        switchView("Stats");
    }

    private void init(){
        System.out.println("Initialize App");
        active.put("OverView", overViewRoot);
        active.put("Modify", modifyRoot);
        active.put("Stats", statsRoot);
        loadOverview();
        loadModify();
        loadStats();
        switchView("OverView");
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        init();
    }
}
