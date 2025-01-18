package app.tyrell.backend;

import javafx.scene.image.ImageView;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Supplier;

public class HoldSortCache extends HoldSort{
    private CachedRowSet cAcs;
    private CachedRowSet cDesc;
    private CachedRowSet cAll;
    private CachedRowSet lastCachedTypeSort;

    private int page;


    public HoldSortCache(ImageView arrow, Supplier<ResultSet> acs, Supplier<ResultSet> desc, Supplier<ResultSet> all) {
        super(arrow, acs, desc, all);
    }

    public ResultSet acs() {
        cAcs = getCachedResultSet(cAcs, acs, "cAcs");
        cursorToPage(cAcs);
        System.out.println("cAcs");
        return cAcs;
    }

    public ResultSet desc() {
        cDesc = getCachedResultSet(cDesc, desc, "cDesc");
        cursorToPage(cDesc);
        System.out.println("cDesc");
        return cDesc;
    }

    public ResultSet all() {
        cAll = getCachedResultSet(cAll, all, "cAll");
        cursorToPage(cAll);
        System.out.println("cAll");
        return cAll;
    }

    public ResultSet getLastTypeSort() {
        if (lastCachedTypeSort == null) {
            System.out.println("lastCachedTypeSort is null, caching cAll");
            return all();
        }
        System.out.println("lastCachedTypeSort : " + lastCachedTypeSort);
        cursorToPage(lastCachedTypeSort);
        return lastCachedTypeSort;
    }

    public void resetCacheResultSet() {
        closeCachedRowSet(cAcs);
        closeCachedRowSet(cDesc);
        closeCachedRowSet(cAll);
        cAcs = null;
        cDesc = null;
        cAll = null;
        lastCachedTypeSort = null;
    }

    protected CachedRowSet cacheRow(ResultSet rs) {
        CachedRowSet cachedRowSet = null;
        try {
            rs.beforeFirst();
            cachedRowSet = RowSetProvider.newFactory().createCachedRowSet();
            cachedRowSet.populate(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cachedRowSet;
    }

    private CachedRowSet getCachedResultSet(CachedRowSet cache, Supplier<ResultSet> supplier, String cacheName) {
        if (cache == null) {
            System.out.println(cacheName + " cached");
            cache = cacheRow(supplier.get());
        }
        this.lastCachedTypeSort = cache;
        return cache;
    }

    private void closeCachedRowSet(CachedRowSet cachedRowSet) {
        if (cachedRowSet != null) {
            try {
                cachedRowSet.close();
            } catch (SQLException e) {
                System.err.println("Error while closing CachedRowSet: " + e.getMessage());
            }
        }
    }

    private void cursorToPage(CachedRowSet rs) {
        try {
            rs.beforeFirst();
            for (int i=0;i<page*20 && rs.next() ;i++) {
                System.out.println("Skip row NB --> "+i);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setPage(int page){
        this.page=page;
    }

}
