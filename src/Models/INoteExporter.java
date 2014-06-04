package Models;

/**
 * Created by gossel_c on 04/06/2014.
 */

public interface INoteExporter {
    public boolean  serializeNote(INote note);
    public INote    deserializeNote(String path);
}
