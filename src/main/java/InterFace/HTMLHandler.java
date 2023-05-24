package InterFace;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HTMLHandler {
    private static String delimeter = "%";

    private void setDelimeter(String delimeter) {
        this.delimeter = delimeter;
    }

    public String templateFill(String htmlFileString, HashMap<String, String> context) throws IOException {
        for (Map.Entry<String, String> entry : context.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            htmlFileString = htmlFileString.replaceAll(this.delimeter + key + this.delimeter, value);

        }
        return htmlFileString;
    }
}