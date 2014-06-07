package Test;

import Model.Note;
import Model.NoteSorter;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.fail;

public class NoteSorterTest {

    private List<Note> get_notes_one() {
        List<Note> n_list = new ArrayList<>();
        n_list.add(new Note(new ArrayList<String>(), null, 70, "C"));
        n_list.add(new Note(new ArrayList<String>(), null, 50, "A"));
        n_list.add(new Note(new ArrayList<String>(), null, 60, "B"));

        n_list.get(0).add_tag("Tag1");
        n_list.get(0).add_tag("Tag2");
        n_list.get(0).add_tag("Tag3");

        n_list.get(1).add_tag("A");
        n_list.get(1).add_tag("Tag");
        n_list.get(1).add_tag("B");
        n_list.get(1).add_tag("C");

        n_list.get(2).add_tag("Tag1");
        n_list.get(2).add_tag("kkk");
        n_list.get(2).add_tag("helloTag3");
        return n_list;
    }

    private void show_list(List<Note> res) {
        System.out.print("List\n");
        for (Note n : res) {
            System.out.print(n.toString());
        }
        System.out.print("\n");
    }

    private List<Note> get_notes_two() {
        List<Note> n_list = new ArrayList<>();
        n_list.add(new Note(null, null, 70, "John"));
        n_list.add(new Note(null, null, 50, "Johnny"));
        n_list.add(new Note(null, null, 60, "Jane"));
        n_list.add(new Note(null, null, 50, "Ronny"));
        return n_list;
    }

    @Test
    public void testSort_alpha() throws Exception {
        NoteSorter ns = new NoteSorter();
        List<Note> res = ns.sort_alpha(get_notes_one(), false);
        if (res.get(0).get_title() != "A" || res.get(1).get_title() != "B" || res.get(2).get_title() != "C")
            fail("Could not sort alphabetically");
        res = ns.sort_alpha(get_notes_one(), true);
        if (res.get(0).get_title() != "C" || res.get(1).get_title() != "B" || res.get(2).get_title() != "A")
            fail("Could not sort alphabetically in reverse order");
    }

    @Test
    public void testSort_by_date() throws Exception {
        NoteSorter ns = new NoteSorter();
        List<Note> res = ns.sort_alpha(get_notes_one(), false);
        if (res.get(0).get_title() != "A" || res.get(1).get_title() != "B" || res.get(2).get_title() != "C")
            fail("Could not sort by date");
        res = ns.sort_alpha(get_notes_one(), true);
        if (res.get(0).get_title() != "C" || res.get(1).get_title() != "B" || res.get(2).get_title() != "A")
            fail("Could not sort by date in reverse order");
    }

    @Test
    public void testFilter_by_name() throws Exception {
        NoteSorter ns = new NoteSorter();
        List<Note> res;
        res = ns.filter_by_name(get_notes_two(), "Ronny");
        if (res.get(0).get_title() != "Ronny" || res.size() > 1)
            fail("Could not filter by exact name");
        res = ns.filter_by_name(get_notes_two(), "J");
        if (!(res.get(0).get_title() == "John" || res.get(0).get_title() == "Johnny" || res.get(0).get_title() == "Jane" &&
                res.get(1).get_title() == "John" || res.get(1).get_title() == "Johnny" || res.get(1).get_title() == "Jane" &&
                res.get(2).get_title() == "John" || res.get(2).get_title() == "Johnny" || res.get(2).get_title() == "Jane" &&
                res.size() == 3))
            fail("Could not filter by first letter");
        res = ns.filter_by_name(get_notes_two(), "nny");
        if (!(res.get(0).get_title() == "Johnny" && res.get(1).get_title() == "Ronny" && res.size() == 2))
            fail("Could not filter with pattern inside the world");
        res = ns.filter_by_name(get_notes_two(), "k");
        if (res.size() > 0)
            fail("Could not filter with no result");
    }

    @Test
    public void testFilter_by_tag() throws Exception {
        NoteSorter ns = new NoteSorter();
        List<Note> res;
        res = ns.filter_by_tag(get_notes_one(), "A");
        if (!(res.get(0).get_title() == "A" && res.size() == 1))
            fail("Could not filter one by tag");
        res = ns.filter_by_tag(get_notes_one(), "Tag");
        if (!(res.get(0).get_title() == "A" && res.size() == 1))
            fail("Could not filter one when other tags contain the filter (e.g. \"Tag\" and \"Tag1\")");
        res = ns.filter_by_tag(get_notes_one(), "Tag1");
        if (!(res.get(0).get_title() == "C" && res.get(1).get_title() == "B" && res.size() == 2))
            fail("Could not filter two out of three");
        res = ns.filter_by_tag(get_notes_one(), "NotATag");
        if (!(res.size() == 0))
            fail("Could not filter no tag");
    }
}