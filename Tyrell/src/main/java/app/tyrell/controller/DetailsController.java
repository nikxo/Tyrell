package app.tyrell.controller;

import app.tyrell.backend.DataManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

public class DetailsController {

    private Long id;

    private final double[] gps = new double[2];
    private Runnable reset;

    private AnchorPane editRoot;

    @FXML
    private Text nameContact;

    @FXML
    private Text phoneContact;

    @FXML
    private Text description;

    @FXML
    private Text skills;

    @FXML
    private Text Responsibilities;

    @FXML
    private Text companyName;

    @FXML
    private Text preference;

    @FXML
    private Text postingDate;

    @FXML
    private Text tLocation;

    @FXML
    private Text country;

    @FXML
    private Text salary;

    @FXML
    private Text experience;

    @FXML
    private Text Qualification;

    @FXML
    private Text jobPortal;

    @FXML
    private Text workingtype;

    @FXML
    private Text title;

    @FXML
    private Text Role;

    @FXML
    private Text tId;

    @FXML
    private Text Benefits;

    @FXML
    private Text Sector;

    @FXML
    private Text industry;

    @FXML
    private Text city;

    @FXML
    private Text state;

    @FXML
    private Text zip;

    @FXML
    private Text website;

    @FXML
    private Text ticker;

    @FXML
    private Text ceo;

    @FXML
    private Text companySize;


    public void setId(Long id) {
        this.id = id;
        System.out.println(""+id);
    }

    public Long getId() {
        return this.id;
    }

    private void anchorInit(AnchorPane pane) {
        this.editRoot.getChildren().clear();
        this.editRoot.getChildren().add(pane);
        this.editRoot.setVisible(true);
        this.editRoot.toFront();
    }

    private void controllerInit(UpdateController controller) {
        controller.setIsEdit(true);
        controller.setRunnableReset(reset);
        controller.Find(id);
        controller.setEditRoot(editRoot);
        System.out.println(editRoot);
    }

    @FXML
    private void edit(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("NewUpdate.fxml"));
            AnchorPane update = loader.load();
            UpdateController controller = loader.getController();

            controllerInit(controller);
            anchorInit(update);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void init(Long id) {
        setId(id);
        DataManager manager = new DataManager();
        manager.getAllDataById(
                getId(),
                description,
                skills,
                Responsibilities,
                companyName,
                preference,
                postingDate,
                tLocation,
                country,
                salary,
                experience,
                Qualification,
                jobPortal,
                workingtype,
                title,
                Role,
                tId,
                nameContact,
                phoneContact,
                Benefits,
                gps,
                Sector,
                industry,
                city,
                state,
                zip,
                website,
                ticker,
                ceo,
                companySize);

    }

    @FXML
    private void handleOpenGoogleMaps() {
        try {
            String url = "https://www.google.com/maps?q=" + gps[0] + "," + gps[1];

            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI(url));
            } else {
                System.out.println("Cannot open browser.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setEditRoot(AnchorPane editRoot) {
        this.editRoot = editRoot;
    }

    public void setRunnableReset(Runnable reset) {
        this.reset = reset;
    }

}
