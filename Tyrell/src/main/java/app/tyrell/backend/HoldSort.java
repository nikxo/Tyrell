package app.tyrell.backend;

import javafx.scene.image.ImageView;

import java.sql.ResultSet;
import java.util.function.Supplier;

public class HoldSort extends AbstractHold {

    public HoldSort(ImageView arrow, Supplier<ResultSet> acs, Supplier<ResultSet> desc, Supplier<ResultSet> all) {
        super(arrow, acs, desc, all);
    }

    @Override
    public ResultSet acs() {
        this.supplierTypeSort = acs;
        return acs.get();
    }

    @Override
    public ResultSet desc() {
        this.supplierTypeSort = desc;
        return desc.get();
    }

    @Override
    public ResultSet all() {
        this.supplierTypeSort = all;
        return all.get();
    }

    @Override
    public ResultSet getLastTypeSort() {
        return supplierTypeSort.get();
    }
}
