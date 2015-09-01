package files.validator;

import java.util.List;
import java.util.Map;

public class FileValidator {

    public static boolean invalidFiles(List<Map<String, Object>> files) {
        return files == null || files.isEmpty();
    }
}
