package lib.jackson;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.fasterxml.jackson.core.util.DefaultIndenter;


public class target {
    public static void fuzzerTestOneInput(FuzzedDataProvider data){

        var t = new ByteArrayBuilder();

        var i = data.consumeInt(0,100);

        t.appendFourBytes(i);
    }
}
