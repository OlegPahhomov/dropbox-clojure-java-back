package files.crud;

import config.AppDataSource;
import files.util.FileResizer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

/**
 * CRUD* without R (Read)
 * Create, read, update, delete
 */
public class FileCrud {


    /**
     * Depends on how is the input done
     */
    public static void saveFilesMap(Map<String, Map<String, Object>> files) throws IOException, SQLException {
        for (Map<String, Object> file : files.values()) {
            saveOneFile(file);
        }
    }

    /**
     * Depends on how is the input done
     */
    public static void saveFiles(List<Map<String, Object>> files) throws IOException, SQLException {
        for (Map<String, Object> file : files) {
            saveOneFile(file);
        }
    }

    private static void saveOneFile(Map<String, Object> file) throws IOException, SQLException {
        try (Connection connection = AppDataSource.getTransactConnection();
             PreparedStatement ps = connection.prepareStatement("INSERT INTO FILE(name, content, image_width, image_height) VALUES (?, ?, ?, ?)")) {
            resizeIfNeededAndFillPs(ps, file);
            connection.commit();
        }
    }

    private static void resizeIfNeededAndFillPs(PreparedStatement ps, Map<String, Object> file) throws IOException, SQLException {
        String filename = (String) file.get("filename");
        File content = (File) file.get("tempfile");
        BufferedImage img = ImageIO.read(content);
        if (FileResizer.needsResize(img)) {
            BufferedImage resizedImage = FileResizer.dynamicResize(img);
            ByteArrayOutputStream os = FileResizer.getByteArrayOutputStream(resizedImage);
            fillPsAndExecute(ps, filename, resizedImage, new ByteArrayInputStream(os.toByteArray()));
        } else {
            fillPsAndExecute(ps, filename, img, new FileInputStream(content));
        }
    }

    public static void deleteOneFile(String id) throws SQLException {
        deleteOneFile(Integer.valueOf(id));
    }

    private static void deleteOneFile(Integer id) throws SQLException {
        try (Connection connection = AppDataSource.getTransactConnection();
             PreparedStatement ps = connection.prepareStatement("DELETE FROM FILE WHERE ID=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
            connection.commit();
        }
    }

    private static void fillPsAndExecute(PreparedStatement ps, String fileName, BufferedImage img, InputStream inputStream) throws SQLException {
        ps.setString(1, fileName);
        ps.setBinaryStream(2, inputStream);
        ps.setInt(3, img.getWidth());
        ps.setInt(4, img.getHeight());
        ps.executeUpdate();
    }

}
