package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chris on 04/06/14.
 * Used on ${PROJECT_NAME}
 * Description :
 * Basic class to represent a Note. The actual content of the note is stored in the file at file_name.
 */
public class Note implements Serializable {
    List<String> tags;
    String file_name;
    long last_modified;
    String title;

    private Note() {
    }

    public Note(String n_title) {
        last_modified = new java.util.Date().getTime();
        title = n_title;
        tags = new ArrayList<>();
        file_name = String.valueOf(last_modified) + title;
    }

    public Note(List<String> n_tags, String n_file_name, long n_last_modified, String n_title) {
        tags = n_tags;
        file_name = n_file_name;
        title = n_title;
        last_modified = n_last_modified;
    }

    public List<String> get_tags() {
        return tags;
    }

    public void add_tag(String new_tag) {
        tags.add(new_tag);
    }

    public void remove_tag(String new_tag) {
        tags.remove(new_tag);
    }

    public long get_last_modified() {
        return last_modified;
    }

    public void change_last_modified() {
        last_modified = new java.util.Date().getTime();
    }

    public String get_file_name() {
        return file_name;
    }

    public String get_title() {
        return title;
    }

    public void set_title(String n_title) {
        title = n_title;
    }

    @Override
    public String toString() {
        return "Note{" +
                "tags=" + tags +
                ", file_name='" + file_name + '\'' +
                ", last_modified=" + last_modified +
                ", title='" + title + '\'' +
                '}';
    }
}
