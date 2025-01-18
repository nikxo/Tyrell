package app.tyrell.controller;

import app.tyrell.backend.*;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Supplier;

public class OverviewController {
    private int actualPage;
    private int pageTotal;

    private HoldSort def;
    private Sort sDef;

    private HoldSortCache search;
    private HoldSort actual;

    private String title;
    private String id;
    private String jobType;
    private String country;

    private final JobController jb;
    private final DataManager dm;
    private final Search searcher;
    private final IsSelect isSelect;
    private Runnable Reset;

    @FXML
    protected VBox item;
    @FXML
    private ImageView experienceArrow;
    @FXML
    private AnchorPane detailsPane;
    @FXML
    private Text jobName;
    @FXML
    private Text jobCount;
    @FXML
    private TextField jobField;
    @FXML
    private TextField idField;
    @FXML
    private TextField countryField;
    @FXML
    private TextField jobTypeField;
    @FXML
    private AnchorPane editPane;

    public OverviewController() {
        //Object
        this.jb = new JobController();
        this.dm = new DataManager();
        this.searcher = new Search();
        this.isSelect = new IsSelect();

        //int
        this.actualPage = 0;
        dm.countLine();
        this.pageTotal = dm.pageCount();

        //String
        this.title = "";
        this.id = "";
        this.country = "";
        this.jobType = "";

    }

    @FXML
    private void sortByExperience() {
        if (this.actual != def) {
            search.setPage(actualPage);
            isSelect.reset();
            clear(detailsPane);
            sDef.sortGui(search);
        } else {
            isSelect.reset();
            clear(detailsPane);
            sDef.sortGui(def);
        }
    }

    private ResultSet getResultSort() {
        HoldSort[] holdSortTab = {def, search};
        for (HoldSort h : holdSortTab) {
            if (actual == h && actual != search) {
                return h.getLastTypeSort();
            } else if (actual == search) {
                return search.getLastTypeSort();
            }
        }
        return def.getLastTypeSort();
    }

    @FXML
    private void previousPage() {
        if (actualPage <= 0) {
            return;
        }
        clear(item);
        actualPage--;
        search.setPage(actualPage);
        jb.setData(isSelect, detailsPane, item, actualPage, getResultSort());
    }

    @FXML
    private void nextPage() {
        if (actualPage >= pageTotal) {
            return;
        }
        clear(item);
        actualPage++;
        search.setPage(actualPage);
        jb.setData(isSelect, detailsPane, item, actualPage, getResultSort());

    }


    private String setJobName(String title, String id,String country, String jobType) {
        String[] tab = {title, id, country, jobType};
        for (String s : tab) {
            if (!s.isEmpty()) {
                return s;
            }
        }
        return "All";
    }

    private String getJobCount() {
        int jobNumber = searcher.getCount();
        this.pageTotal = jobNumber / dm.nbItem;
        return Integer.toString(jobNumber);
    }


    private void cacheSearch(){
        if(sDef.getTypeSort().equals("ASC")){
            this.search.setCount(1);
            this.search.desc();
            this.search.all();
            this.search.acs();
        } else if (sDef.getTypeSort().equals("DESC")) {
            this.search.setCount(2);
            this.search.acs();
            this.search.all();
            this.search.desc();
        } else {
            this.search.setCount(0);
            this.search.acs();
            this.search.desc();
            this.search.all();
        }

    }


    @FXML
    private void search() {
        this.search.resetCacheResultSet();
        this.actual = search;
        this.actualPage = 0;
        this.search.setPage(0);



        title = jobField.getText();
        id = idField.getText();
        country = countryField.getText();
        jobType = jobTypeField.getText();

        jobName.setText(setJobName(title,id,country,jobType));

        clear(jobField);
        clear(idField);
        clear(countryField);
        clear(jobTypeField);
        clear(item);


        if (title.isEmpty() && id.isEmpty() && country.isEmpty() && jobType.isEmpty()) {
            this.actual = def;
            this.def.setCount(0);
            this.def.setImageArrow("");
            jb.setData(isSelect, detailsPane, item, actualPage, dm.getJobData(actualPage));
            jobName.setText("All");
            jobCount.setText(Integer.toString(dm.nbLine));
        } else if (!id.isEmpty()) {
            jobName.setText("ID");
            title = "";
            country = "";
            jobType = "";
            cacheSearch();
            jb.setData(isSelect, detailsPane, item, 0, getResultSort());
            jobCount.setText(getJobCount());
        } else {
            cacheSearch();
            jb.setData(isSelect, detailsPane, item, 0, getResultSort());
            jobCount.setText(getJobCount());
        }

        clear(detailsPane);
        isSelect.reset();
    }


    @FXML
    private void export() {
        try {
            Export exp = new Export();
            FileChooser fileChooser = new FileChooser();

            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
            fileChooser.getExtensionFilters().add(extFilter);

            fileChooser.setTitle("Save as");
            fileChooser.setInitialFileName(title+"_export.csv");

            File file = fileChooser.showSaveDialog(new Stage());

            if (file != null) {
                if (title.isEmpty() && id.isEmpty() && country.isEmpty() && jobType.isEmpty()) {
                    exp.writeFile(searcher.getAllData(), file.getAbsolutePath());
                    searcher.remoteCloseConnection("getAllData", "export (overview)");
                } else {
                    exp.writeFile(getResultSort(), file.getAbsolutePath());
                }
            }

        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setDefaultJobControllerSettings() {
        jb.setEditRoot(editPane);
        jb.setRunnableReset(this.Reset);
        jb.setData(isSelect, detailsPane, item, actualPage, dm.getJobData(actualPage));
    }


    private void setDefaultParam() {
        Reset = new Runnable() {
            public void run() {
                System.out.println("Runnable Reset");
                clear(item);
                clear(detailsPane);
                isSelect.reset();
                jb.setData(isSelect, detailsPane, item, actualPage, dm.getJobData(actualPage));
                jobName.setText("All");
                jobCount.setText(Integer.toString(dm.nbLine));
            }
        };

        this.jobCount.setText(Integer.toString(dm.nbLine));
        this.jobName.setText("All");
        this.actual = def;
        editPane.toBack();
    }

    private void clear(AnchorPane pane) {
        pane.getChildren().clear();
    }
    private void clear(VBox vbox) {
        vbox.getChildren().clear();
    }
    private void clear(TextField field) {
        field.clear();
    }

    public void init() {
        this.sDef = new Sort(isSelect, item, actualPage, detailsPane, jb);

        Supplier<ResultSet> spAll = () -> dm.getJobData(actualPage);
        Supplier<ResultSet> spAllData = () -> searcher.getDataSearch(title, id, country, jobType, "");

        Supplier<ResultSet> spExperienceAsc = () -> dm.getAllDataByExperience(actualPage, "ASC");
        Supplier<ResultSet> spExperienceDesc = () -> dm.getAllDataByExperience(actualPage, "DESC");

        Supplier<ResultSet> spAllDataASC = () -> searcher.getDataSearch(title, id, country, jobType, "ASC");
        Supplier<ResultSet> spAllDataDESC = () -> searcher.getDataSearch(title, id, country, jobType, "DESC");

        this.def = new HoldSort(experienceArrow, spExperienceAsc, spExperienceDesc, spAll);
        this.search = new HoldSortCache(experienceArrow, spAllDataASC, spAllDataDESC, spAllData);

        setDefaultParam();
        setDefaultJobControllerSettings();

    }
}
