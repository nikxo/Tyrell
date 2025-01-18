package app.tyrell.backend;

import java.sql.SQLException;

public class Stats extends Search {

    public Stats() {
        super();
        countLine();
    }

    public double[] getAverageSalary() {
        String baseSql = "SELECT Salary_Range FROM job_descriptions ";
        int low = 0;
        int high = 0;
        try {
            openConnection("getAverageSalary");
            stmt = conn.createStatement();
            rs = stmt.executeQuery(baseSql);
            while (rs.next()) {
                String exp = rs.getString("Salary_Range");
                String clean = exp.replaceAll("\\$", "").replaceAll("K", "");

                String[] expTab = clean.split("-");

                low += Integer.parseInt(expTab[0]);
                high += Integer.parseInt(expTab[1]);
            }
            return new double[]{Math.round(((double) low / nbLine) * 100.0) / 100.0, Math.round(((double) high / nbLine) * 100.0) / 100.0};
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection("getAverageSalary");
        }
    }

    public double[] getAverageExp() {
        String baseSql = "SELECT Experience FROM job_descriptions ";
        int low = 0;
        int high = 0;
        try {
            openConnection("getAverageExp");
            stmt = conn.createStatement();
            rs = stmt.executeQuery(baseSql);
            while (rs.next()) {
                String exp = rs.getString("Experience");
                String clean = exp.replaceAll("Years", "").replaceAll(" ", "");

                String[] expTab = clean.split("to");

                low += Integer.parseInt(expTab[0]);
                high += Integer.parseInt(expTab[1]);
            }
            return new double[]{Math.round(((double) low / nbLine) * 100.0) / 100.0, Math.round(((double) high / nbLine) * 100.0) / 100.0};
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection("getAverageExp");
        }
    }


}
