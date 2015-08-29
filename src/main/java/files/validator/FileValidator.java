package files.validator;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class FileValidator {

    public static boolean invalidFiles(List<Map<String, Object>> files) throws IOException, SQLException {
        return files == null || files.isEmpty();
    }
}
