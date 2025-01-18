package app.tyrell.backend;

import javax.sql.rowset.CachedRowSet;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Search extends DataManager {
    protected int count;

    protected Boolean isTitle;
    protected String[] stringTitle;
    protected int indexTitle;
    protected int sizeTitle;

    protected Boolean isId;
    protected int indexId;

    protected Boolean isCountry;
    protected String[] stringCountry;
    protected int indexCountry;
    protected int sizeCountry;

    protected Boolean isJobType;
    protected String[] stringJobType;
    protected int indexJobType;
    protected int sizeJobType;

    protected int indexGlobal;
    protected boolean isFirst;

    public Search() {
        super();
        isTitle = false;
        indexTitle = 1;
        isId = false;
        indexId = 1;
        isCountry = false;
        indexCountry = 1;
        isJobType = false;
        indexJobType = 1;
        indexGlobal = 1;
        isFirst = true;
    }


    protected void reset() {
        isTitle = false;
        indexTitle = 1;
        stringTitle = null;
        isId = false;
        indexId = 1;
        isCountry = false;
        indexCountry = 1;
        stringCountry = null;
        isJobType = false;
        indexJobType = 1;
        stringJobType = null;
        indexGlobal = 1;
        isFirst = true;
    }


    private int stringBuilder(StringBuilder baseSql,String[] stringTab,String column) {
        String where = "WHERE ";
        String and = "AND ";

        if (stringTab.length != 0 && stringTab.length != 1) {
            if (isFirst) {
                baseSql.append(where);
                baseSql.append("(");
                for (int i = 0; i < stringTab.length; i++) {
                    baseSql.append("LOWER(").append(column).append(") LIKE LOWER(?)");
                    if (i + 1 == stringTab.length) {
                        break;
                    }
                    baseSql.append(" OR ");
                }
                baseSql.append(") ");
                isFirst = false;
            } else {
                baseSql.append(and);
                baseSql.append("(");
                for (int i = 0; i < stringTab.length; i++) {
                    baseSql.append("LOWER(").append(column).append(") LIKE LOWER(?)");
                    if (i + 1 == stringTab.length) {
                        break;
                    }
                    baseSql.append(" OR ");
                }
                baseSql.append(") ");
            }
        }else {
            if (isFirst) {
                baseSql.append(where).append("LOWER(").append(column).append(") LIKE LOWER(?) ");
                isFirst = false;
            } else {
                baseSql.append("AND LOWER(").append(column).append(") LIKE LOWER(?) ");
            }
        }
        return stringTab.length;
    }


    protected String getSqlString(String sort,
                                  String title,
                                  String id,
                                  String country,
                                  String jobType
    ) {
        StringBuilder baseSql = new StringBuilder("SELECT * FROM job_descriptions ");
        if (!title.isEmpty()) {
            stringTitle = title.split(",");
            sizeTitle = stringBuilder(baseSql, stringTitle, "Job_Title");

            this.isTitle = true;
        }

        if (!country.isEmpty()) {
            System.out.println("country pass");
            stringCountry = country.split(",");
            sizeCountry= stringBuilder(baseSql,stringCountry, "Country");

            this.isCountry = true;
        }

        if (!jobType.isEmpty()) {
            stringJobType = jobType.split(",");
            sizeJobType = stringBuilder(baseSql,stringJobType, "Work_Type");

            this.isJobType = true;
        }

        if (!id.isEmpty()) {
            if (isFirst) {
                baseSql = new StringBuilder("SELECT * FROM job_descriptions ");
                baseSql.append("WHERE ").append("JOB_ID = ? ");
            } else {
                baseSql.append("AND JOB_ID = ? ");
            }
            this.isId = true;
        }

        String orderClause = "";
        if (sort.equals("ASC")) {
            orderClause = "ORDER BY Experience ASC ";
        } else if (sort.equals("DESC")) {
            orderClause = "ORDER BY Experience DESC ";
        }
        System.out.println("---getDataSearch---");
        System.out.println("getDataSearch : " + baseSql + orderClause);
        return baseSql + orderClause;
    }

    public ResultSet getDataSearch(
            String title,
            String id,
            String country,
            String jobType,
            String sort
    ) {
        try {
            openConnection("getData");
            pstmt = conn.prepareStatement(getSqlString(sort, title, id, country, jobType));


            if (isTitle) {
                this.indexTitle = indexGlobal;
                if(sizeTitle== 1){
                    System.out.println("Title : " + title);
                    System.out.println("Title index : " + this.indexTitle);
                    pstmt.setString(this.indexTitle++, "%" + title + "%");
                }else {
                    for(int i = 0; i< sizeTitle; i++) {
                        System.out.println("Title: " + stringTitle[i]);
                        System.out.println("Title index : " + this.indexTitle);
                        pstmt.setString(this.indexTitle++ , "%" + stringTitle[i] + "%");

                    }
                }
                System.out.println("Out iTitle : " + this.indexTitle);
                indexGlobal = this.indexTitle;
            }

            if (isId) {
                System.out.println("Id : " + id);
                pstmt.setLong(this.indexId, Long.parseLong(id));
            }

            if (isCountry) {
                this.indexCountry = indexGlobal;
                if(sizeCountry== 1){
                    System.out.println("Country : " + country);
                    System.out.println("Country index : " + this.indexCountry);
                    pstmt.setString(this.indexCountry++, "%" + country + "%");
                }else {
                    for(int i = 0; i< sizeCountry; i++) {
                        System.out.println("Country : " + stringCountry[i]);
                        System.out.println("Country index : " + this.indexCountry);
                        pstmt.setString(this.indexCountry++ , "%" + stringCountry[i] + "%");

                    }
                }
                System.out.println("Out iCountry : " + this.indexCountry);
                indexGlobal = this.indexCountry;

            }
            if (isJobType) {
                this.indexJobType = indexGlobal;
                if(sizeJobType == 1){
                    System.out.println("JobType : " + jobType);
                    pstmt.setString(this.indexJobType++, "%" + jobType + "%");
                }else {
                    for(int i = 0; i< sizeJobType; i++) {
                        System.out.println("JobType : " + stringJobType[i]);
                        System.out.println("JobType index : " + this.indexJobType);
                        pstmt.setString(this.indexJobType++ , "%" + stringJobType[i] + "%");
                    }
                }
                System.out.println("Out iJobType : " + this.indexJobType);
                indexGlobal = this.indexJobType;
            }

            rs = pstmt.executeQuery();
            CachedRowSet cachedRowSet = cacheRow(rs);
            count = coutLine(cachedRowSet);
            return cachedRowSet;
        } catch (SQLException e) {
            e.printStackTrace();
            closeConnection("getData");
            System.out.println("getData -- Failed");
            return null;
        } finally {
            reset();
            closeConnection("getData");
        }
    }

        public ResultSet getAllData() {
        try {
            String sql = "SELECT * FROM job_descriptions";
            openConnection("getAllData");
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery(sql);
            return rs;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public int coutLine(CachedRowSet crs){
        int count = 0;
        try {
            crs.beforeFirst();
            while (crs.next()){
                count++;
            }
            crs.beforeFirst();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }

    public int getCount(){
        return count;
    }

    public void remoteCloseConnection(String toClose,String fromClose){
        closeConnection( toClose + " -- remote close connection from "+fromClose);
    }
}
