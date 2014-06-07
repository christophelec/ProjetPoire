package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chris on 07/06/14.
 * Used on ProjetPoire
 * Description :
 */
public class ExporterManager {
    private Map<String, INoteExporter> _exporters;

    public ExporterManager() {
        _exporters = new HashMap<>();
        _exporters.put("Txt file", new TxtNoteExporter());
    }

    public List<String> get_exporter_list() {
        return (new ArrayList<>(_exporters.keySet()));
    }

    public boolean export_to(String type, Opened_note note, String file_name) {
        INoteExporter exp = _exporters.get(type);
        return (exp != null) && exp.export_note(note, file_name);
    }

    public Opened_note import_from(String filename) {
        for (Map.Entry<String, INoteExporter> entry : _exporters.entrySet()) {
            if (entry.getValue().supports_type(filename))
                return entry.getValue().import_note(filename);
        }
        return null;
    }
}
