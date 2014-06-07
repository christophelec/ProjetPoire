package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

/**
 * Created by chris on 06/06/14.
 * Used on ProjetPoire
 * Description :
 * Manager for everything note-related
 */

public class NoteManager {
    private static java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(NoteFileManipulator.class.getPackage().getName());

    private List<Note> _notes;
    private INoteSorter _sorter;
    private INoteFileManipulator _manipulator;
//TODO: INoteExporter

    public NoteManager() {
        Preferences pref = Preferences.userRoot().node(this.getClass().getName());
        _sorter = new NoteSorter();
        _manipulator = new NoteFileManipulator();

        _notes = _manipulator.extract_note_list(pref.get("note_list_path", "./.poire_note_list"));
        if (_notes == null) {
            _notes = new ArrayList<>();
            if (!_manipulator.save_changes_note_list(pref.get("note_list_path", "./.poire_note_list"), _notes))
                throw new RuntimeException("Could not create the list of notes in " + pref.get("note_list_path", "./"));
        }
    }

    public List<Note> get_notes() {
        return get_sorted_notes(Sort_type.ALPHA, false, _notes);
    }

    public List<Note> get_sorted_notes(Sort_type sort_type, boolean sort_order, List<Note> cur_notes) {
        switch (sort_type) {
            case DATE:
                return _sorter.sort_alpha(cur_notes, sort_order);
            case ALPHA:
                return _sorter.sort_by_date(cur_notes, sort_order);
            default:
                LOGGER.warning("Sorting type not implemented : " + sort_type);
                return cur_notes;
        }
    }

    public List<Note> get_notes_by_name(String filter) {
        return _sorter.filter_by_name(_notes, filter);
    }

    public List<Note> get_notes_by_tag(List<String> tags) {
        List<Note> r_notes = new ArrayList<>(_notes);

        for (String tag : tags) {
            r_notes = _sorter.filter_by_tag(r_notes, tag);
        }
        return r_notes;
    }

    public Opened_note open_note(Note note) {
        return _manipulator.extract_from_file(note);
    }

    public boolean save_note(Opened_note note) {
        if (!note.is_saved())
            if (_manipulator.save_note(note))
                note.save();
        return (note.is_saved());
    }

    public Opened_note create_note() {
        Opened_note n_note = new Opened_note(new Note(generate_title()));
        if (!save_note(n_note))
            return null;
        _notes.add(n_note.get_note());
        return n_note;
    }

    private String generate_title() {
        Preferences pref = Preferences.userRoot().node(this.getClass().getName());
        int cur_num;
        try {
            cur_num = Integer.parseInt(pref.get("note_title", "1"));
        } catch (NumberFormatException e) {
            cur_num = 1;
        }
        pref.put("note_title", String.valueOf(cur_num + 1));
        return "Note" + String.valueOf(cur_num);
    }

    public enum Sort_type {
        DATE, ALPHA
    }

    //TODO: exports, imports and get_supported_exports
}
