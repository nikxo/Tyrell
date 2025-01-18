package app.tyrell.controller;

import app.tyrell.backend.IsSelect;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JobController {

    private AnchorPane detailsPane;
    private AnchorPane editPane;

    private IsSelect isSelect;

    private int page;
    private int row;

    private Long id;

    private Runnable reset;

    @FXML
        private AnchorPane jobPane;

    @FXML
        private Text title = null;

    @FXML
        private Text workType = null;

    @FXML
        private Text Qualifications = null;

    @FXML
        private Text Country = null;

    @FXML
        private Text Location = null;

    @FXML
        private Text Experience = null;


    private void setTitle(String title) {
        this.title.setText(title);
    }

    private void setWork(String work) {
        this.workType.setText(work);
    }

    private void setQualifications(String qual) {
        this.Qualifications.setText(qual);
    }

    private void setCountry(String country) {
        this.Country.setText(country);
    }

    private void setLocation(String location) {
        this.Location.setText(location);
    }

    private void setExperience(String ex) {
        this.Experience.setText(ex);
    }

    private void setIsSelect(IsSelect isSelect) {
        this.isSelect = isSelect;
    }

    private void setPage(int page) {
        this.page = page;
    }
    private void setRow(int row) {
        this.row = row;
    }
    private void setId(Long id){
        this.id = id;
    }


    public void setData(IsSelect isSelect, AnchorPane anchorPaneDetails, VBox item, int page, ResultSet rs) {
        try {
            for (int i = 0; i < 20; i++) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(appController.class.getResource("NewJob.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();
                JobController controller = fxmlLoader.getController();


                if (rs.next()) {
                    System.out.println("Defillement NB (active Job) : "+rs.getLong("JOB_ID"));
                    controller.setTitle(rs.getString("Job_Title"));
                    controller.setWork(rs.getString("Work_Type"));
                    controller.setQualifications(rs.getString("Qualifications"));
                    controller.setCountry(rs.getString("Country"));
                    controller.setLocation(rs.getString("Location"));
                    controller.setExperience(rs.getString("Experience"));
                    controller.setId(rs.getLong("JOB_ID"));
                    controller.setDetailsPane(anchorPaneDetails);
                    controller.setIsSelect(isSelect);
                    controller.setEditRoot(editPane);
                    controller.setRunnableReset(reset);

                    if (isSelect.isActive(i, page)) {
                        System.out.println("ACTIVE --> NB : " + i + " Page : " + page);
                        controller.setStyleACtive();
                        isSelect.setActive(i, page, controller.jobPane);
                    }
                    controller.setPage(page);
                    controller.setRow(i);
                    item.getChildren().add(anchorPane);
                }else {
                    break;
                }
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void Details() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("NewDetails.fxml"));
            AnchorPane detailsPanefxml = loader.load();
            DetailsController detailsController = loader.getController();
            detailsController.init(id);
            detailsController.setRunnableReset(reset);
            detailsController.setEditRoot(editPane);
            isSelect.select(row, page,jobPane,detailsPane,detailsPanefxml);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void setStyleACtive() {
        jobPane.setStyle("-fx-background-color:white;-fx-background-radius:10px;-fx-border-color: #adc7cf;-fx-border-radius:10px");
    }


    private void setDetailsPane(AnchorPane detailsPane) {
        this.detailsPane = detailsPane;
    }

    public void setEditRoot(AnchorPane editPane) {
        this.editPane = editPane;
    }

    public void setRunnableReset(Runnable reset) {
        this.reset = reset;
    }

}
