package Model;

import java.io.*;
import java.util.List;

/**
 * Created by chris on 06/06/14.
 * Used on ProjetPoire
 * Description :
 * This class is used to retrieve notes from files.
 */
public class NoteFileManipulator implements INoteFileManipulator {
    private static java.util.logging.Logger logger = java.util.logging.Logger.getLogger(NoteFileManipulator.class.getPackage().getName());

    @Override
    public Opened_note extract_from_file(Note note) {
        Opened_note o_note;
        try (FileInputStream fis = new FileInputStream(note.get_file_name());
             ObjectInputStream oos = new ObjectInputStream(fis)) {
            o_note = (Opened_note) oos.readObject();
        } catch (ClassNotFoundException e) {
            logger.severe("File " + note.get_file_name() + " is probably corrupted. Error :" + e.getMessage());
            return null;
        } catch (IOException e) {
            logger.severe("There was a problem retrieving file " + note.get_file_name() + ". Error : " + e.getMessage());
            return null;
        }
        o_note.set_note(note);
        return o_note;
    }

    @Override
    public List<Note> extract_note_list(String file_name) {
        List<Note> n_note_list;
        try (FileInputStream fis = new FileInputStream(file_name);
             ObjectInputStream oos = new ObjectInputStream(fis)) {
            n_note_list = ((List<Note>) oos.readObject());
        } catch (ClassNotFoundException e) {
            logger.severe("File " + file_name + " is probably corrupted. Error :" + e.getMessage());
            return null;
        } catch (IOException e) {
            logger.severe("There was a problem retrieving file " + file_name + ". Error : " + e.getMessage());
            return null;
        }
        return n_note_list;
    }

    @Override
    public boolean save_note(Opened_note note) {
        try (FileOutputStream fos = new FileOutputStream(note.get_note().get_file_name());
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(note);
            oos.flush();
        } catch (IOException e) {
            logger.severe("There was a problem saving in the file " + note.get_note().get_file_name() + ". Error : " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean save_changes_note_list(String file_name, List<Note> note_list) {
        try (FileOutputStream fos = new FileOutputStream(file_name);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(note_list);
            oos.flush();
        } catch (IOException e) {
            logger.severe("There was a problem saving in the file " + file_name + ". Error : " + e.getMessage());
            return false;
        }
        return true;
    }
}
