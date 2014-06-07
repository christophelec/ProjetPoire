package Model;

import java.io.Serializable;

/**
 * Created by chris on 04/06/14.
 * Used on ${PROJECT_NAME}
 * Description :
 * This class describe a note that has been opened. It is serialized in an other file than the note list (all Note are
 * serialized in the same file, but they do not contain text)
 */
public class Opened_note implements Serializable {
    transient Note note;
    boolean change_saved;
    String text;

    public Opened_note(Note n_note) {
        note = n_note;
        change_saved = false;
        text = "";
    }

    public void change_text(String n_text) {
        text = n_text;
        change_saved = false;
        note.change_last_modified();
    }

    public boolean is_saved() {
        return change_saved;
    }

    public void save() {
        change_saved = true;
    }

    public Note get_note() {
        return note;
    }

    public void set_note(Note n_note) {
        note = n_note;
    }

    public String get_text() {
        return text;
    }
}
