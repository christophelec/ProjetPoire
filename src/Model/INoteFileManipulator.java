package Model;

import java.util.List;

/**
 * Created by chris on 06/06/14.
 * Used on ProjetPoire
 * Description :
 */
public interface INoteFileManipulator {
    public Opened_note extract_from_file(Note note);

    public List<Note> extract_note_list(String file_name);

    public boolean save_note(Opened_note note);

    public boolean save_changes_note_list(String file_name, List<Note> note_list);
}
