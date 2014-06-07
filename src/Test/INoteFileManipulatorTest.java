package Test;

import Model.Note;
import Model.NoteFileManipulator;
import Model.Opened_note;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.fail;

public class INoteFileManipulatorTest {

    @Test
    public void testNoteManipulation() throws Exception {
        NoteFileManipulator nf = new NoteFileManipulator();
        Note test_one = new Note("TestA");
        Opened_note test_one_opened = new Opened_note(test_one);
        test_one.add_tag("tag1");
        test_one_opened.change_text("Hello world ! This is an exemple of note");
        if (!nf.save_note(test_one_opened))
            fail("Could not save the note");
        Opened_note test_one_retrieved;
        test_one_retrieved = nf.extract_from_file(test_one);
        if (test_one_retrieved == null)
            fail("Could not retrieve the note");
        if (!test_one_retrieved.get_text().equals("Hello world ! This is an exemple of note"))
            fail("The note might have been corrupted");
    }

    private void show_list(List<Note> res) {
        System.out.print("List\n");
        for (Note n : res) {
            System.out.print(n.toString());
        }
        System.out.print("\n");
    }

    @Test
    public void testNoteListManipulation() throws Exception {
        NoteFileManipulator nf = new NoteFileManipulator();
        String note_list_file = "./.poire_note_list";
        List<Note> note_list = new ArrayList<>();
        note_list.add(new Note("Note Alpha"));
        note_list.add(new Note("Note Beta"));
        note_list.add(new Note("Note Gamma"));
        if (!nf.save_changes_note_list(note_list_file, note_list))
            fail("Could not save the list of notes");
        List<Note> note_list_retrieved = nf.extract_note_list(note_list_file);
        if (note_list_retrieved == null)
            fail("Could not retrieve the list of notes");
        if (note_list_retrieved.size() != 3)
            fail("The note list might been corrupted");
    }
}