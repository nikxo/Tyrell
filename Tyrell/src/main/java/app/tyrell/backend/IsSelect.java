package app.tyrell.backend;

import javafx.scene.layout.AnchorPane;

public class IsSelect {
    private AnchorPane jobPane;
    private int NB = -1;
    private int page = -1;


    public void setActive(int NB,int page,AnchorPane pane) {
        this.NB = NB;
        this.page = page;
        this.jobPane = pane;
    }

    public boolean isActive(int NB,int page) {
        return this.NB==NB && this.page==page;
    }

    public void reset() {
        clearPane();
        this.NB=-1;
        this.page=-1;
    }

    public void clearPane(){
        if (jobPane != null) {
            this.jobPane.setStyle("-fx-background-color:white;-fx-background-radius:10px;-fx-border-color: #ebebee;-fx-border-radius:10px");
            this.jobPane = null;
        }
    }


    public void select(int NB,int page,AnchorPane jobPane,AnchorPane detailsPane,AnchorPane detailsPanefxml) {
        if(!isActive(NB,page)) {
            detailsPane.getChildren().add(detailsPanefxml);
            reset();
            setActive(NB,page,jobPane);
            jobPane.setStyle("-fx-background-color:white;-fx-background-radius:10px;-fx-border-color: #adc7cf;-fx-border-radius:10px");
        } else if (isActive(NB,page) ) {
            detailsPane.getChildren().clear();
            reset();
        }
    }

}
