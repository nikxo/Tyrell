package app.tyrell.controller;

import app.tyrell.backend.Stats;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StatsController {

    Stats stats;
    ResultSet rs;
    private double[] exp;
    private double[] sal;

    public StatsController() {
        this.stats = new Stats();
        exp = new double[2];
        sal = new double[2];
        rs=null;
    }

    @FXML
    private TextField jobTitle;

    @FXML
    private TextField workingType;

    @FXML
    private TextField country;

    @FXML
    private Text countText;

    @FXML
    private Button set;

    @FXML
    private VBox vBox;

    @FXML
    private AnchorPane anchorCount;

    @FXML
    private Text highExp;

    @FXML
    private Text lowExp;

    @FXML
    private Text highSalary;

    @FXML
    private Text lowSalary;

    @FXML
    private void search(){
        stats.getDataSearch(jobTitle.getText(),"", country.getText(),workingType.getText(),"");
        int jobCount = stats.getCount();
        hideVboxForm();
        showAnchorCount();
        setCountText(jobCount);
        clearField();
    }

    private void clearField(){
        jobTitle.clear();
        workingType.clear();
        country.clear();
    }

    @FXML
    private void reset(){
        hideAnchorCount();
        showVboxForm();
    }

    private void setCountText(int count){
        countText.setText(Integer.toString(count));
    }

    private void hideVboxForm(){
        vBox.setVisible(false);
    }

    private void showAvgExpText(){
        lowExp.setVisible(true);
        highExp.setVisible(true);
    }

    private void showAvgSalaryText(){
        highSalary.setVisible(true);
        lowSalary.setVisible(true);
    }

    private void showVboxForm(){
        vBox.setVisible(true);
    }

    private void showAnchorCount(){
        anchorCount.setVisible(true);
    }

    private void hideAnchorCount(){
        anchorCount.setVisible(false);
    }

    @FXML
    private void exp(){
        exp = stats.getAverageExp();
        System.out.println(exp[0]);
        System.out.println(exp[1]);
        lowExp.setText(exp[0]+" Years");
        highExp.setText(exp[1]+" Years");
        showAvgExpText();
    }

    @FXML
    private void sal(){
        sal = stats.getAverageSalary();
        System.out.println(sal[0]);
        System.out.println(sal[1]);
        lowSalary.setText("$"+sal[0] + "K");
        highSalary.setText("$"+sal[1]+ "K");
        showAvgSalaryText();
    }

}
