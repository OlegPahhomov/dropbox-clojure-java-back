package files.service;

public class FileService {


    /*public static void saveFilesToDb(Request request) throws IOException, ServletException, SQLException {
        List<Part> parts = FileUtil.getParts(request);
        if (parts.size() == 1) FileCrud.saveOneFile(parts.get(0));
        else {
            for (Part file : parts) FileCrud.saveOneFile(file);
            //parts.forEach(FileCrud::saveOneFile); can't do this if exceptions are unfixed
        }
    }*/

    /*public static void deleteFileFromDb(Request request) throws SQLException {
        Integer id = Integer.valueOf(request.params(":id"));
        FileCrud.deleteOneFile(id);
    }*/


}
