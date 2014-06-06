package Model;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by chris on 04/06/14.
 * Used on ${PROJECT_NAME}
 * Description :
 * Basic note sorting using the standard Java methods.
 */
public class NoteSorter implements INoteSorter {

    @Override
    public List<Note> sort_alpha(List<Note> list, boolean reverse) {
        if (reverse)
            list.sort(new CompareAlpha().reversed());
        else
            list.sort(new CompareAlpha());
        return list;
    }

    @Override
    public List<Note> sort_by_date(List<Note> list, boolean reverse) {
        if (reverse)
            list.sort(new CompareDate().reversed());
        else
            list.sort(new CompareDate());
        return list;
    }

    @Override
    public List<Note> filter_by_name(List<Note> list, final String filter) {
        list.removeIf(new Predicate<Note>() {
            @Override
            public boolean test(Note note) {
                return !note.get_title().contains(filter);
            }
        });
        return list;
    }

    @Override
    public List<Note> filter_by_tag(List<Note> list, final String tag) {
        list.removeIf(new Predicate<Note>() {
            @Override
            public boolean test(Note note) {
                return !note.get_tags().contains(tag);
            }
        });
        return list;
    }

    private class CompareAlpha implements Comparator<Note> {
        @Override
        public int compare(Note o1, Note o2) {
            return o1.get_title().compareTo(o2.get_title());
        }

        @Override
        public Comparator<Note> reversed() {
            return new Comparator<Note>() {
                @Override
                public int compare(Note o1, Note o2) {
                    return o2.get_title().compareTo(o1.get_title());
                }
            };
        }
    }

    private class CompareDate implements Comparator<Note> {
        @Override
        public int compare(Note o1, Note o2) {
            return Long.compare(o1.get_last_modified(), o2.get_last_modified());
        }

        @Override
        public Comparator<Note> reversed() {
            return new Comparator<Note>() {
                @Override
                public int compare(Note o1, Note o2) {
                    return Long.compare(o2.get_last_modified(), o1.get_last_modified());
                }
            };
        }
    }
}
