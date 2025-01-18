package app.tyrell.backend;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModifyManager {

    protected final DataManager dataManager;
    private AnchorPane modifyRootPanel;

    protected boolean edit;
    protected Runnable reset;
    protected AnchorPane editRoot;

    protected ModifyManager() {
        this.dataManager = new DataManager();
    }

    private void anchorInit(AnchorPane pane) {
        modifyRootPanel.getChildren().clear();
        modifyRootPanel.getChildren().add(pane);
    }

    @FXML
    protected void loadModify() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("NewModify.fxml"));
            AnchorPane modify = loader.load();
            System.out.println("loadModify");

            anchorInit(modify);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void notFillStyle(TextField textField) {
        textField.setStyle("-fx-border-color: red;");
    }

    private void notFillStyle(TextArea textArea) {
        textArea.setStyle("-fx-border-color: red;");
    }

    private void FillStyle(TextField textField) {
        textField.setStyle("-fx-border-color: #e0e2e5;");
    }

    private void FillStyle(TextArea textArea) {
        textArea.setStyle("-fx-border-color: #e0e2e5;");
    }

    private String forceSalaryFormat(String input) {
        Pattern pattern = Pattern.compile(".*?(\\d+)\\s*-\\s*(\\d+).*");
        Pattern patternexact = Pattern.compile("\\$(\\d+)K-\\$(\\d+)K");
        Matcher matcher = pattern.matcher(input);
        Matcher matcherexact = patternexact.matcher(input);

        if(matcherexact.matches()) {
            return input;
        }

        if (matcher.matches()) {
            String lower = matcher.group(1);
            String upper = matcher.group(2);
            return String.format("$%sK-$%sK", lower, upper);
        } else {
            return "Invalid format. Expected: $XXK-$XXXK";
        }
    }

    private String forceExperienceFormat(String input) {
        Pattern pattern = Pattern.compile("(\\d+)\\s*to\\s*(\\d+)");
        Pattern patternExact = Pattern.compile("(\\d+)\\s*to\\s*(\\d+)\\s*Years");
        Matcher matcher = pattern.matcher(input);
        Matcher matcherexact = patternExact.matcher(input);

        if(matcherexact.matches()) {
            return input;
        }
        if (matcher.matches()) {
            String minYears = matcher.group(1);
            String maxYears = matcher.group(2);

            return String.format("%s to %s Years", minYears, maxYears);
        } else {
            return "Invalid format. Expected: X-Y";
        }
    }

    private boolean typeVerif(TextField latitude, TextField longitude, TextField companySize){
        boolean returnValue = true;
        try {
            double lat = Double.parseDouble(latitude.getText());
            if (lat < -90 || lat > 90) {
                notFillStyle(latitude);
                System.err.println("The field " + latitude.getId() + " is out of valid range (-90 to 90).");
                returnValue = false;
            }
        } catch (NumberFormatException e) {
            notFillStyle(latitude);
            System.err.println("The field " + latitude.getId() + " have a wrong format.");
            returnValue = false;
        }
        try {
            double lon = Double.parseDouble(longitude.getText());
            if (lon < -180 || lon > 180) {
                notFillStyle(longitude);
                System.err.println("The field " + longitude.getId() + " is out of valid range (-90 to 90).");
                returnValue = false;
            }
        } catch (NumberFormatException e) {
            notFillStyle(longitude);
            System.err.println("The field " + longitude.getId() + " have a wrong format.");
            returnValue = false;
        }
        try {
            Integer.parseInt(companySize.getText());
        } catch (NumberFormatException e) {
            notFillStyle(companySize);
            System.err.println("The field " + companySize.getId() + " have a wrong format.");
            returnValue = false;
        }
        return returnValue;
    }


    public boolean verify(TextField[] textFields, TextArea[] textAreas, TextField latitude, TextField longitude, TextField companySize,TextField salary,TextField experience) {
        boolean returnValue = true;
        for (TextField field : textFields) {
            if (field.getText().trim().isEmpty()) {
                System.err.println("The field " + field.getId() + " is empty.");
                notFillStyle(field);
                returnValue = false;
            } else if (field == salary) {
                String result = forceSalaryFormat(salary.getText());
                if(result.equals("Invalid format. Expected: $XXK-$XXXK")){
                    notFillStyle(field);
                    System.err.println(result);
                    returnValue = false;
                }else {
                    field.setText(result);
                    FillStyle(field);
                };
            } else if (field == experience) {
                String result = forceExperienceFormat(field.getText());
                if(result.equals("Invalid format. Expected: X-Y")){
                    notFillStyle(field);
                    System.err.println(result);
                    returnValue = false;
                }else {
                    field.setText(result);
                    FillStyle(field);
                }
            } else{
                FillStyle(field);
            }
        }

        for (TextArea area : textAreas) {
            if (area.getText().trim().isEmpty()) {
                System.err.println("The area " + area.getId() + " is empty. Please fill it in.");
                notFillStyle(area);
                returnValue = false;
            } else{
                FillStyle(area);
            }
        }

        boolean typeVerif = typeVerif(latitude, longitude, companySize);

        return returnValue && typeVerif;
    }

    private boolean verifyId(TextField textField) {
        try {
            Long.parseLong(textField.getText());
        } catch (NumberFormatException e) {
            notFillStyle(textField);
            return false;
        }
        if(!dataManager.verifyId(Long.parseLong(textField.getText()))){
            notFillStyle(textField);
            return false;
        }
        FillStyle(textField);
        return true;
    }

    public void save(TextField Id,
                     TextField latitude,
                     TextField longitude,
                     TextField companySize,
                     TextArea benefits,
                     TextField nameContact,
                     TextField phoneContact,
                     TextArea description,
                     TextArea skills,
                     TextArea responsibilities,
                     TextField companyName,
                     TextField preference,
                     TextField postingDate,
                     TextField tLocation,
                     TextField country,
                     TextField salary,
                     TextField experience,
                     TextField qualification,
                     TextField jobPortal,
                     TextField workingType,
                     TextField title,
                     TextField role,
                     TextField sector,
                     TextField industry,
                     TextField city,
                     TextField state,
                     TextField zip,
                     TextField website,
                     TextField ticker,
                     TextField ceo

    ) {
        TextField[] textFields = {
                nameContact, phoneContact, companyName, preference, postingDate, tLocation, country, salary,
                experience, qualification, jobPortal, workingType, title, role, Id, sector, industry,
                city, state, zip, website, ticker, ceo, latitude, longitude, companySize
        };

        TextArea[] textAreas = {
                description, skills, responsibilities, benefits
        };
        if (verify(textFields, textAreas, latitude, longitude, companySize,salary,experience)) {
            try {
                long jobId = Long.parseLong(Id.getText());
                double lat = Double.parseDouble(latitude.getText());
                double lon = Double.parseDouble(longitude.getText());
                Map<String, Object> companyProfileMap = new LinkedHashMap<>();
                String benefitsValue = "{'" + benefits.getText() + "'}";

                String nameContactValue = nameContact.getText();
                String phoneContactValue = phoneContact.getText();
                String descriptionValue = description.getText();
                String skillsValue = skills.getText();
                String responsibilitiesValue = responsibilities.getText();
                String companyNameValue = companyName.getText();
                String preferenceValue = preference.getText();
                String postingDateValue = postingDate.getText();
                String locationValue = tLocation.getText();
                String countryValue = country.getText();
                String salaryValue = salary.getText();
                String experienceValue = experience.getText();
                String qualificationValue = qualification.getText();
                String jobPortalValue = jobPortal.getText();
                String workingTypeValue = workingType.getText();
                String titleValue = title.getText();
                String roleValue = role.getText();
                int companySizeValue = Integer.parseInt(companySize.getText());

                companyProfileMap.put("Sector", sector.getText());
                companyProfileMap.put("Industry", industry.getText());
                companyProfileMap.put("City", city.getText());
                companyProfileMap.put("State", state.getText());
                companyProfileMap.put("Zip", zip.getText());
                companyProfileMap.put("Website", website.getText());
                companyProfileMap.put("Ticker", ticker.getText());
                companyProfileMap.put("CEO", ceo.getText());

                ObjectMapper mapper = new ObjectMapper();
                ObjectNode rootNode = mapper.createObjectNode();

                for (Map.Entry<String, Object> entry : companyProfileMap.entrySet()) {
                    rootNode.set(entry.getKey(), mapper.convertValue(entry.getValue(), JsonNode.class));
                }

                String companyProfileValue = mapper.writeValueAsString(rootNode);

                this.dataManager.addData(
                        jobId,
                        experienceValue,
                        qualificationValue,
                        salaryValue,
                        locationValue,
                        countryValue,
                        lat,
                        lon,
                        workingTypeValue,
                        companySizeValue,
                        postingDateValue,
                        preferenceValue,
                        nameContactValue,
                        phoneContactValue,
                        titleValue,
                        roleValue,
                        jobPortalValue,
                        descriptionValue,
                        benefitsValue,
                        skillsValue,
                        responsibilitiesValue,
                        companyNameValue,
                        companyProfileValue
                );
            } catch (NumberFormatException e) {
                System.err.println("Error converting text to number: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Error saving data: " + e.getMessage());
            }
            verifyEdit();
            verifySupplier();
        }
    }

    protected void verifyEdit() {
        if (getIsEdit()) {
            this.editRoot.getChildren().clear();
            this.editRoot.toBack();
        } else {
            loadModify();
        }
    }

    private void verifySupplier() {
        if (getIsEdit()) {
            reset.run();
        }
    }

    private boolean getIsEdit() {
        return this.edit;
    }

    protected boolean Find(TextField id,
                           TextField latitude,
                           TextField longitude,
                           TextField companySize,
                           TextArea benefits,
                           TextField nameContact,
                           TextField phoneContact,
                           TextArea description,
                           TextArea skills,
                           TextArea responsibilities,
                           TextField companyName,
                           TextField preference,
                           TextField postingDate,
                           TextField tLocation,
                           TextField country,
                           TextField salary,
                           TextField experience,
                           TextField qualification,
                           TextField jobPortal,
                           TextField workingType,
                           TextField title,
                           TextField role,
                           TextField sector,
                           TextField industry,
                           TextField city,
                           TextField state,
                           TextField zip,
                           TextField website,
                           TextField ticker,
                           TextField ceo) {
        if (verifyId(id)) {
            dataManager.getAllDataById(
                    Long.parseLong(id.getText()),
                    description,
                    skills,
                    responsibilities,
                    companyName,
                    preference,
                    postingDate,
                    tLocation,
                    country,
                    salary,
                    experience,
                    qualification,
                    jobPortal,
                    workingType,
                    title,
                    role,
                    id,
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
        } else {
            System.err.println("Wrong id/not exist : " + id.getText());
            return false;
        }
        return true;
    }

    public void setModifyRootPanel(AnchorPane modifyRootPanel) {
        this.modifyRootPanel = modifyRootPanel;
    }

}
