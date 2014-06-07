package Model;

import java.io.*;

/**
 * Created by chris on 07/06/14.
 * Used on ProjetPoire
 * Description :
 */
public class TxtNoteExporter implements INoteExporter {
    private static java.util.logging.Logger logger = java.util.logging.Logger.getLogger(NoteFileManipulator.class.getPackage().getName());

    @Override
    public boolean supports_type(String filename) {
        return filename.endsWith(".txt");
    }

    @Override
    public boolean export_note(Opened_note note, String path) {
        try (FileOutputStream fos = new FileOutputStream(note.get_note().get_file_name());
             PrintStream ps = new PrintStream(fos)) {
            ps.print("Title : " + note.get_note().get_title() + "\r\n");
            ps.print(note.get_text());
            ps.close();
        } catch (IOException e) {
            logger.severe("Error while exporting the note " + note.get_note().get_title() + " to the file " + path);
            return false;
        }
        return true;
    }

    private String getFileContent(FileInputStream fis) throws IOException {
        StringBuilder sb = new StringBuilder();
        Reader r = new InputStreamReader(fis, "UTF-8");  //or whatever encoding
        char[] buf = new char[1024];
        int amt = r.read(buf);
        while (amt > 0) {
            sb.append(buf, 0, amt);
            amt = r.read(buf);
        }
        return sb.toString();
    }

    @Override
    public Opened_note import_note(String path) {
        Opened_note o_note;
        try (FileInputStream fis = new FileInputStream(path)) {
            String s = getFileContent(fis);
            if (!s.startsWith("Title : "))
                throw new RuntimeException();
            String title = s.substring(7, s.indexOf("\r\n"));
            o_note = new Opened_note(new Note(title));
            o_note.change_text(s.substring(s.indexOf("\r\n") + 2));
        } catch (IOException e) {
            logger.severe("There was a problem retrieving file " + path + ". Error : " + e.getMessage());
            return null;
        } catch (RuntimeException e) {
            logger.severe("The file " + path + " was probably corrupted.");
            return null;
        }
        return o_note;
    }
}
