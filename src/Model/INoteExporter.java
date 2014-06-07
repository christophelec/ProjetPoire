package Model;

/**
 * Created by chris on 07/06/14.
 * Used on ProjetPoire
 * Description :
 */
public interface INoteExporter {
    public boolean export_note(Opened_note note, String path);

    public Opened_note import_note(String path);

    public boolean supports_type(String filename);
}
