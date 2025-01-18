package app.tyrell.backend;

import javafx.scene.image.ImageView;

import java.sql.ResultSet;
import java.util.function.Supplier;

public abstract class AbstractHold {
    protected int count;
    protected final ImageView arrow;
    protected final Supplier<ResultSet> acs;
    protected final Supplier<ResultSet> desc;
    protected final Supplier<ResultSet> all;

    protected Supplier<ResultSet> supplierTypeSort;

    public AbstractHold(ImageView arrow, Supplier<ResultSet> acs, Supplier<ResultSet> desc, Supplier<ResultSet> all) {
        this.count = 0;
        this.arrow = arrow;
        this.supplierTypeSort = all;
        this.acs = acs;
        this.desc = desc;
        this.all = all;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void increment() {
        this.count++;
        System.out.println(this.count);
    }

    public void reset() {
        this.count = 0;
    }

    public void setVisibility(Boolean visible) {
        this.arrow.setVisible(visible);
    }

    public void setImageArrow(String param) {
        if (param.equals("UP")) {
            arrow.setImage(new javafx.scene.image.Image("file:src/main/resources/Icon/chevron-up-regular-24.png"));
        } else if (param.equals("DOWN")) {
            arrow.setImage(new javafx.scene.image.Image("file:src/main/resources/Icon/chevron-down-regular-24.png"));
        }else {
            arrow.setImage(new javafx.scene.image.Image("file:src/main/resources/Icon/Transparent.png"));
        }
    }

    public abstract ResultSet acs();

    public abstract ResultSet desc();

    public abstract ResultSet all();

    public abstract ResultSet getLastTypeSort();
}
