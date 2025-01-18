package app.tyrell.backend;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.json.JSONObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataManager extends Database {

    public ResultSet getJobData(int PageNb) {
        try {
            openConnection("getJobData");
            String sql = "SELECT JOB_ID,Job_Title, Work_Type, Qualifications, Country, Location, Experience " +
                    "FROM job_descriptions " +
                    "LIMIT " + nbItem + " OFFSET " + (PageNb * this.nbItem);

            stmt = conn.createStatement();

            rs = stmt.executeQuery(sql);

            return cacheRow(rs);
        } catch (SQLException e) {
            System.out.println("Échec ! -- (getJobData)");
            e.printStackTrace();
        }finally {
            closeConnection("getJobData");
        }
        return null;
    }

    public void deleteDataById(Long id) {
        try {
            openConnection("deleteDataById");
            pstmt = conn.prepareStatement("DELETE FROM job_descriptions WHERE JOB_ID=?");
            pstmt.setLong(1, id);
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("(deleteDataById -- id: " + id + "), Lignes supprimées : " + rowsAffected);
        } catch (SQLException e) {
            System.out.println("Échec de suppression pour l'ID " + id + " : " + e.getMessage());
        } finally {
            closeConnection("deleteDataById");
        }
    }

    public ResultSet getAllDataByExperience(int PageNb, String type) {
        try {
            String sql = "";
            openConnection("getDataByExperience");

            if (type.equals("DESC")) {
                sql = "select * from job_descriptions ORDER BY Experience DESC LIMIT " + nbItem + " OFFSET " + PageNb * nbItem;
                System.out.println("(getDataByExperience -- DOWN)");
            } else if (type.equals("ASC")) {
                sql = "select * from job_descriptions ORDER BY Experience LIMIT " + nbItem + " OFFSET " + PageNb * nbItem;
                System.out.println("(getDataByExperience -- UP)");
            }

            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            return cacheRow(rs);
        } catch (SQLException e) {
            closeConnection("getDataByExperience");
            System.out.println("Failed!-- (getDataByExperience)");
            e.printStackTrace();
        }finally {
            closeConnection("getDataByExperience");
        }
        return null;
    }


    public boolean verifyId(Long jobId){
        openConnection("verifyId");
        String checkSql = "SELECT COUNT(*) FROM job_descriptions WHERE JOB_ID = ?";
        try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
            checkStmt.setLong(1, jobId);
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeConnection("verifyId");
        }
        return false;
    }

    public void addData(
            long jobId,
            String experience,
            String qualifications,
            String salaryRange,
            String location,
            String country,
            double latitude,
            double longitude,
            String workType,
            int companySize,
            String jobPostingDate,
            String preference,
            String contactPerson,
            String contact,
            String jobTitle,
            String role,
            String jobPortal,
            String jobDescription,
            String benefits,
            String skills,
            String responsibilities,
            String company,
            String companyProfile
    ) {

        String updateSql = "UPDATE job_descriptions SET " +
                "Experience = ?, Qualifications = ?, Salary_Range = ?, Location = ?, Country = ?, " +
                "latitude = ?, longitude = ?, Work_Type = ?, Company_Size = ?, Job_Posting_Date = ?, Preference = ?, " +
                "Contact_Person = ?, Contact = ?, Job_Title = ?, Role_ = ?, Job_Portal = ?, Job_Description = ?, " +
                "Benefits = ?, skills = ?, Responsibilities = ?, Company = ?, Company_Profile = ? " +
                "WHERE JOB_ID = ?";

        String insertSql = "INSERT INTO job_descriptions (" +
                "JOB_ID, Experience, Qualifications, Salary_Range, Location, Country, " +
                "latitude, longitude, Work_Type, Company_Size, Job_Posting_Date, Preference, " +
                "Contact_Person, Contact, Job_Title, Role_, Job_Portal, Job_Description, " +
                "Benefits, skills, Responsibilities, Company, Company_Profile" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";


        if (verifyId(jobId)) {
            openConnection("addData");
            try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                updateStmt.setString(1, experience);
                updateStmt.setString(2, qualifications);
                updateStmt.setString(3, salaryRange);
                updateStmt.setString(4, location);
                updateStmt.setString(5, country);
                updateStmt.setDouble(6, latitude);
                updateStmt.setDouble(7, longitude);
                updateStmt.setString(8, workType);
                updateStmt.setInt(9, companySize);
                updateStmt.setDate(10, java.sql.Date.valueOf(jobPostingDate));
                updateStmt.setString(11, preference);
                updateStmt.setString(12, contactPerson);
                updateStmt.setString(13, contact);
                updateStmt.setString(14, jobTitle);
                updateStmt.setString(15, role);
                updateStmt.setString(16, jobPortal);
                updateStmt.setString(17, jobDescription);
                updateStmt.setString(18, benefits);
                updateStmt.setString(19, skills);
                updateStmt.setString(20, responsibilities);
                updateStmt.setString(21, company);
                updateStmt.setString(22, companyProfile);
                updateStmt.setLong(23, jobId);

                updateStmt.executeUpdate();
                System.out.println("Data updated successfully.");
            }catch (SQLException e) {
                System.err.println("Update failed: " + e.getMessage());
            }finally {
                closeConnection("addData");
            }
        } else {
            openConnection("addData");
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setLong(1, jobId);
                insertStmt.setString(2, experience);
                insertStmt.setString(3, qualifications);
                insertStmt.setString(4, salaryRange);
                insertStmt.setString(5, location);
                insertStmt.setString(6, country);
                insertStmt.setDouble(7, latitude);
                insertStmt.setDouble(8, longitude);
                insertStmt.setString(9, workType);
                insertStmt.setInt(10, companySize);
                insertStmt.setDate(11, java.sql.Date.valueOf(jobPostingDate));
                insertStmt.setString(12, preference);
                insertStmt.setString(13, contactPerson);
                insertStmt.setString(14, contact);
                insertStmt.setString(15, jobTitle);
                insertStmt.setString(16, role);
                insertStmt.setString(17, jobPortal);
                insertStmt.setString(18, jobDescription);
                insertStmt.setString(19, benefits);
                insertStmt.setString(20, skills);
                insertStmt.setString(21, responsibilities);
                insertStmt.setString(22, company);
                insertStmt.setString(23, companyProfile);

                insertStmt.executeUpdate();
                System.out.println("Data inserted successfully.");
            }catch (SQLException e) {
                System.err.println("Insert failed: " + e.getMessage());
            }finally {
                closeConnection("addData");
            }
        }
    }

    public long getUpId() {
        openConnection("getUpId");
        long maxJobId = 0;
        String sql = "SELECT MAX(JOB_ID) AS maxJobId FROM job_descriptions";

        try (PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                maxJobId = resultSet.getLong("maxJobId");
            }
        } catch (SQLException e) {
            System.err.println("Error fetching maximum job ID: " + e.getMessage());
        } finally {
            closeConnection("getUpId");
        }
        return maxJobId + 1;
    }

    public void getAllDataById(Long id, Text description, Text skills, Text responsibilities, Text companyName, Text preference, Text postingDate, Text tLocation, Text country, Text salary, Text experience, Text qualification, Text jobPortal, Text workingtype, Text title, Text role, Text tId, Text nameContact, Text phoneContact, Text benefits, double[] gps, Text sector, Text industry, Text city, Text state, Text zip, Text website, Text ticker, Text ceo, Text employe) {
        try {
            openConnection("getAllDataById");

            String sql = "SELECT * FROM job_descriptions WHERE JOB_ID=?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);

            rs = pstmt.executeQuery();

            rs.next();
            String jsonData = rs.getString("Company_Profile");
            JSONObject jsonObject = new JSONObject(jsonData);

            String input = rs.getString("Benefits");
            String cleanedString = input.substring(1, input.length() - 1).replace("'", "");

            description.setText(rs.getString("Job_Description"));

            skills.setText(rs.getString("skills"));

            responsibilities.setText(rs.getString("Responsibilities"));

            companyName.setText(rs.getString("Company"));

            preference.setText(rs.getString("Preference"));

            postingDate.setText(rs.getString("Job_Posting_Date"));

            tLocation.setText(rs.getString("Location"));

            country.setText(rs.getString("Country"));

            salary.setText(rs.getString("Salary_Range"));

            experience.setText(rs.getString("Experience"));

            qualification.setText(rs.getString("Qualifications"));

            jobPortal.setText(rs.getString("Job_Portal"));

            workingtype.setText(rs.getString("Work_Type"));

            title.setText(rs.getString("Job_Title"));

            role.setText(rs.getString("Role_"));

            tId.setText(rs.getString("Job_Id"));

            nameContact.setText(rs.getString("Contact_Person"));

            phoneContact.setText(rs.getString("Contact"));

            benefits.setText(cleanedString);

            gps[0] = rs.getDouble("Latitude");
            gps[1] = rs.getDouble("Longitude");

            sector.setText("Sector: "+jsonObject.getString("Sector"));
            industry.setText("Industry: "+jsonObject.getString("Industry"));
            city.setText("City: "+jsonObject.getString("City"));
            state.setText("State: "+jsonObject.getString("State"));
            zip.setText("Zip: "+jsonObject.getString("Zip"));
            website.setText("Website: "+jsonObject.getString("Website"));
            ticker.setText("Ticker: "+jsonObject.getString("Ticker"));
            ceo.setText("CEO: "+jsonObject.getString("CEO"));

            employe.setText(rs.getString("Company_Size"));
        } catch (SQLException e) {
            System.out.println("Échec ! -- (getData)");
            e.printStackTrace();
        } finally {
            closeConnection("getAllDataById");
        }
    }

    public void getAllDataById(Long id,
                               TextArea description,
                               TextArea skills,
                               TextArea Responsibilities,
                               TextField companyName,
                               TextField preference,
                               TextField postingDate,
                               TextField tLocation,
                               TextField country,
                               TextField salary,
                               TextField experience,
                               TextField Qualification,
                               TextField jobPortal,
                               TextField workingtype,
                               TextField title,
                               TextField Role,
                               TextField Id,
                               TextField nameContact,
                               TextField phoneContact,
                               TextArea Benefits,
                               TextField latitude,
                               TextField longitude,
                               TextField Sector,
                               TextField industry,
                               TextField city,
                               TextField state,
                               TextField zip,
                               TextField website,
                               TextField ticker,
                               TextField ceo,
                               TextField companySize
    ) {
        try {
            openConnection("getAllDataById");

            String sql = "SELECT * FROM job_descriptions WHERE JOB_ID=?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);

            rs = pstmt.executeQuery();

            rs.next();
            String jsonData = rs.getString("Company_Profile");
            JSONObject jsonObject = new JSONObject(jsonData);

            String input = rs.getString("Benefits");
            String cleanedString = input.substring(1, input.length() - 1).replace("'", "");

            description.setText(rs.getString("Job_Description"));
            description.setDisable(false);

            skills.setText(rs.getString("skills"));
            skills.setDisable(false);

            Responsibilities.setText(rs.getString("Responsibilities"));
            Responsibilities.setDisable(false);

            companyName.setText(rs.getString("Company"));
            companyName.setDisable(false);

            preference.setText(rs.getString("Preference"));
            preference.setDisable(false);

            postingDate.setText(rs.getString("Job_Posting_Date"));
            postingDate.setDisable(true);

            tLocation.setText(rs.getString("Location"));
            tLocation.setDisable(false);

            country.setText(rs.getString("Country"));
            country.setDisable(false);

            salary.setText(rs.getString("Salary_Range"));
            salary.setDisable(false);

            experience.setText(rs.getString("Experience"));
            experience.setDisable(false);

            Qualification.setText(rs.getString("Qualifications"));
            Qualification.setDisable(false);

            jobPortal.setText(rs.getString("Job_Portal"));
            jobPortal.setDisable(false);

            workingtype.setText(rs.getString("Work_Type"));
            workingtype.setDisable(false);

            title.setText(rs.getString("Job_Title"));
            title.setDisable(false);

            Role.setText(rs.getString("Role_"));
            Role.setDisable(false);

            Id.setText(rs.getString("Job_Id"));
            Id.setDisable(true);

            nameContact.setText(rs.getString("Contact_Person"));
            nameContact.setDisable(false);

            phoneContact.setText(rs.getString("Contact"));
            phoneContact.setDisable(false);

            Benefits.setText(cleanedString);
            Benefits.setDisable(false);

            latitude.setText(rs.getString("Latitude"));
            latitude.setDisable(false);

            longitude.setText(rs.getString("Longitude"));
            longitude.setDisable(false);

            Sector.setText(jsonObject.getString("Sector"));
            Sector.setDisable(false);
            industry.setText(jsonObject.getString("Industry"));
            industry.setDisable(false);
            city.setText(jsonObject.getString("City"));
            city.setDisable(false);
            state.setText(jsonObject.getString("State"));
            state.setDisable(false);
            zip.setText(jsonObject.getString("Zip"));
            zip.setDisable(false);
            website.setText(jsonObject.getString("Website"));
            website.setDisable(false);
            ticker.setText(jsonObject.getString("Ticker"));
            ticker.setDisable(false);
            ceo.setText(jsonObject.getString("CEO"));
            ceo.setDisable(false);

            companySize.setText(rs.getString("Company_Size"));
            companySize.setDisable(false);
        } catch (SQLException e) {
            System.out.println("Échec ! -- (getData)");
            e.printStackTrace();
        } finally {
            closeConnection("getAllDataById");
        }
    }
}
