package lib.jackson.readTree;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Target {
    public static void fuzzerTestOneInput(FuzzedDataProvider data) {
        var mapper = new ObjectMapper();

        var json = data.consumeString(100);

        try {
            var jsonNode = mapper.readTree(json);
        } catch (JsonProcessingException e) {

        }
    }
}
