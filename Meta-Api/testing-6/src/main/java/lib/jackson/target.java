package lib.jackson;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.databind.ObjectMapper;


public class target {
    public static void fuzzerTestOneInput(FuzzedDataProvider data) {
        var mapper = new ObjectMapper();

        var json = data.consumeString(30);

        try {
            var jsonNode = mapper.readTree(json);
        } catch (JsonProcessingException e) {

        }
    }

    public static void main(String[] args) {
        var mapper = new ObjectMapper();

        try {
            var jsonNode = mapper.readTree("vfbdzb");
        } catch (JsonProcessingException e) {

        }
    }
}
