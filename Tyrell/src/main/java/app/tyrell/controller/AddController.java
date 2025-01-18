package app.tyrell.controller;


import app.tyrell.backend.DataManager;
import app.tyrell.backend.ModifyManager;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AddController extends ModifyManager {

    private final Long ID;

    public AddController() {
        DataManager dataManager = new DataManager();
        this.ID = dataManager.getUpId();
    }

    @FXML
    private TextField nameContact;

    @FXML
    private TextField phoneContact;

    @FXML
    private TextArea description;

    @FXML
    private TextArea skills;

    @FXML
    private TextArea responsibilities;

    @FXML
    private TextField companyName;

    @FXML
    private TextField companySize;

    @FXML
    private TextField preference;

    @FXML
    private TextField postingDate;

    @FXML
    private TextField locationField;

    @FXML
    private TextField country;

    @FXML
    private TextField salary;

    @FXML
    private TextField experience;

    @FXML
    private TextField qualification;

    @FXML
    private TextField jobPortal;

    @FXML
    private TextField workingType;

    @FXML
    private TextField title;

    @FXML
    private TextField Role;

    @FXML
    private TextField idField;

    @FXML
    private TextArea benefits;

    @FXML
    private TextField sector;

    @FXML
    private TextField industry;

    @FXML
    private TextField city;

    @FXML
    private TextField state;

    @FXML
    private TextField zip;

    @FXML
    private TextField website;

    @FXML
    private TextField ticker;

    @FXML
    private TextField ceo;

    @FXML
    private TextField latitude;

    @FXML
    private TextField longitude;

    private String getTodayDateString() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return today.format(formatter);
    }

    @FXML
    private void save() {
        save(idField,latitude,longitude,companySize,benefits,nameContact,phoneContact,description,skills,responsibilities,companyName,preference,postingDate,locationField,country,salary,experience, qualification,jobPortal,workingType,title,Role,sector,industry,city,state,zip,website,ticker,ceo);
    }

    public void init() {
        idField.setText(Long.toString(ID));
        postingDate.setText(getTodayDateString());
    }
}
