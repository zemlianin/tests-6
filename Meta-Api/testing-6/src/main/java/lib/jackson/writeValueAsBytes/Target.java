package lib.jackson.writeValueAsBytes;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Target {
    public static void fuzzerTestOneInput(FuzzedDataProvider data) {
        var mapper = new ObjectMapper();

        var key1 = data.consumeString(30);
        var value1 = data.consumeString(30);

        var key2 = data.consumeString(30);
        var value2 = data.consumeInt();

        var key3 = data.consumeString(30);
        var value3 = data.consumeBoolean();

        var node = mapper.createObjectNode();

        node.put(key1,value1);
        node.put(key2, value2);
        node.put(key3, value3);

        try {
            mapper.writeValueAsBytes(node);
        } catch (JsonProcessingException e) {

        }
    }
}
