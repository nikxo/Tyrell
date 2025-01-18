package app.tyrell.backend;

import app.tyrell.controller.JobController;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.sql.ResultSet;

public class Sort {
    private final VBox item;
    private final int page;
    private String type;
    private final AnchorPane detailsPane;
    private final JobController jb;
    private final IsSelect isSelect;

    public Sort(IsSelect isSelect, VBox item, int page, AnchorPane detailsPane, JobController jb) {
        this.item = item;
        this.type = "";
        this.detailsPane = detailsPane;
        this.jb = jb;
        this.isSelect = isSelect;
        this.page = page;
    }

    public void sortGui(HoldSort sort) {
        item.getChildren().clear();
        if (sort.getCount() == 0) {
            sortType(sort.acs());
            sort.setImageArrow("UP");
            setType("ASC");
            sort.setVisibility(true);
            sort.increment();
        } else if (sort.getCount() == 1) {
            sortType(sort.desc());
            sort.setImageArrow("DOWN");
            setType("DESC");
            sort.setVisibility(true);
            sort.increment();
        } else {
            sortType(sort.all());
            setType("");
            sort.reset();
            sort.setVisibility(false);
        }
    }

    public void sortType(ResultSet rs) {
        jb.setData(isSelect, detailsPane, item, page, rs);
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeSort(){
        return type;
    }

}
