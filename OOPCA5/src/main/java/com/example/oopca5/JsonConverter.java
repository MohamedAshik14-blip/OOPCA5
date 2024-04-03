import DTO.StudentDTO;
import com.google.gson.Gson;

import java.util.List;

public class JsonConverter {
    private static final Gson gson = new Gson();

    // Feature 7: Convert List of Entities to a JSON String
    public static <T> String listEntitiesToJson(List<T> list) {
        return gson.toJson(list);
    }

    // Feature 8: Convert a single Entity by Key as a JSON String
    public static <T> String entityToJson(T entity) {
        return gson.toJson(entity);
    }
}
