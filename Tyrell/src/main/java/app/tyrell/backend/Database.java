package app.tyrell.backend;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;

public class Database {

    protected final String url;
    protected final String user;
    protected final String password;
    protected Connection conn;
    protected Statement stmt;
    protected PreparedStatement pstmt;
    protected ResultSet rs;
    public final int nbItem;
    public int nbLine;

    public Database() {
        this.url = "jdbc:mysql://localhost:3306/job_app";
        this.user = "App";  // Replace with your MySQL username
        this.password = "jobteaser";
        this.conn = null;
        this.stmt = null;
        this.rs = null;
        this.nbItem = 20;
        this.nbLine = 0;
    }

    protected void openConnection(String data){
        try {
            if(conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(url, user, password);
                System.out.println("Connected --> [" + data + "]  [" + url + "]");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void closeResource(AutoCloseable resource) {
        if (resource!= null) {
            try {
                resource.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void closeConnection(String data) {
        closeResource(rs);
        closeResource(pstmt);
        closeResource(stmt);
        closeResource(conn);
        System.out.println("Connection closed --> [" + data + "]  [" + url + "]");
    }

    public void countLine() {
        try {
            openConnection("countLine");
            String sql = "select COUNT(*) from job_descriptions";

            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            if(rs.next()){
                nbLine = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Failed!-- (countLine)");
            e.printStackTrace();
        }finally {
            closeConnection("coutLine");
        }
    }

    protected CachedRowSet cacheRow(ResultSet rs) throws SQLException {
        CachedRowSet cachedRowSet = RowSetProvider.newFactory().createCachedRowSet();
        cachedRowSet.populate(rs);
        return cachedRowSet;
    }
    public int pageCount() {
        return this.nbLine / nbItem;
    }


}


