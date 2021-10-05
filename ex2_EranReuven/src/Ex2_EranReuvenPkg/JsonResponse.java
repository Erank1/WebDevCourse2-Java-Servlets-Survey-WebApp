package Ex2_EranReuvenPkg;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

/**
 * Class: JsonResponse, Abstract Class.
 * Description: This class will be extended by "Survey" and "Results" classes.
 * It will make it possible for the other classes to send a JSON object as response.
 * all that happens here.
 *
 * Members: myMap - is the data structure that been used in Survey and results to present
 * The survey itself and the results vector, the import to JSON from map is easy, so that is the
 * Datastructure we uses. it protected, we using it individually in each extended class.
 */
abstract public class JsonResponse {
    /**
     * private member myMap - is the data structure that been used in Survey and results to present
     */
    protected Map<String, String> myMap;

    /**
     * Function: JsonResponseOut
     * @param response - this is the 'response' that we will send to the 'request' client.
     *                 We getting it by the called functions, that also gets it from the specific servlet.
     * @throws IOException - Throws IOException once it fails.
     *
     * Desctiption: This function is the only function in the abstract class JsonResponse.
     *              Once the child class call it, we will response to the user a JSON data.
     *              The function takes the Map of the called object, and import it to JSON.
     *              Then, it writes it to the response to the user.
     */
    public void JsonResponseOut(HttpServletResponse response) throws IOException {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        myMap.forEach(builder::add);
        JsonObject obj = builder.build();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try (OutputStream out = response.getOutputStream()) {
            JsonWriter jsonW = Json.createWriter(out);
            jsonW.write(obj);
            jsonW.close();
        }
    }
}
