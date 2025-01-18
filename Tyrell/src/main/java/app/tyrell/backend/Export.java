package app.tyrell.backend;

import java.io.BufferedWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.io.FileWriter;
import java.io.IOException;

public class Export {

    public void writeFile(ResultSet rs, String fileName) throws IOException, SQLException {
        rs.beforeFirst();
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        try (FileWriter file = new FileWriter(fileName);

             BufferedWriter writer = new BufferedWriter(file)) {

            for (int i = 1; i <= columnCount; i++) {
                writer.write(metaData.getColumnName(i));
                if (i < columnCount) {
                    writer.write(";");
                }
            }
            writer.newLine();

            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    writer.write(rs.getString(i));
                    if (i < columnCount) {
                        writer.write(";");
                    }
                }
                writer.newLine();
            }
        }
    }

}
