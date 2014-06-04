package Model;

import java.util.List;

/**
 * Created by chris on 04/06/14.
 * Used on ${PROJECT_NAME}
 * Description :
 * Interface for sorting, so that different and/or more efficient ways of sorting can be easily implemented.
 */
public interface INoteSorter {
    public List<Note> sort_alpha(List<Note> list, boolean reverse);

    public List<Note> sort_by_date(List<Note> list, boolean reverse);

    public List<Note> filter_by_name(List<Note> list, String filter);

    public List<Note> filter_by_tag(List<Note> list, String tag);
}
