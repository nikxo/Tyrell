package app.tyrell.controller;

import app.tyrell.backend.DataManager;
import app.tyrell.backend.ModifyManager;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class UpdateController extends ModifyManager {


    public UpdateController() {
        super();
        this.edit = false;
    }

    @FXML
    private Button bFind;

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

    @FXML
    private void Find() {
        if(Find(idField,latitude,longitude,companySize,benefits,nameContact,phoneContact,description,skills,responsibilities,companyName,preference,postingDate,locationField,country,salary,experience, qualification,jobPortal,workingType,title,Role,sector,industry,city,state,zip,website,ticker,ceo)){
            bFind.setVisible(false);
        }
    }

    public void Find(Long id) {
        dataManager.getAllDataById(id,
                description,
                skills,
                responsibilities,
                companyName,
                preference,
                postingDate,
                locationField,
                country,
                salary,
                experience,
                qualification,
                jobPortal,
                workingType,
                title,
                Role,
                idField,
                nameContact,
                phoneContact,
                benefits,
                latitude,
                longitude,
                sector,
                industry,
                city,
                state,
                zip,
                website,
                ticker,
                ceo,
                companySize);
        bFind.setVisible(false);

    }

    @FXML
    private void close(){
        verifyEdit();
    }

    @FXML
    private void save() {
        save(idField,latitude,longitude,companySize,benefits,nameContact,phoneContact,description,skills,responsibilities,companyName,preference,postingDate,locationField,country,salary,experience, qualification,jobPortal,workingType,title,Role,sector,industry,city,state,zip,website,ticker,ceo);
    }

    public void setIsEdit(boolean edit){
        this.edit=edit;
    }
    public void setEditRoot(AnchorPane editRoot){
        this.editRoot=editRoot;
    }
    public void setRunnableReset(Runnable reset) {
        this.reset = reset;
    }

}
