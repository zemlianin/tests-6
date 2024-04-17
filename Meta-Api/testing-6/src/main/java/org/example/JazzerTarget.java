package org.example;

import org.example.Calc;
import org.example.CalcException;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;

public class JazzerTarget {
    public static void fuzzerTestOneInput(FuzzedDataProvider data){
        var pipiska = new Pipiska();
        Calc calc = new Calc();
        var t = data.consumeInt();
        pipiska.GetSum(t);

        /*String opnstr = "", s = ?;
        try {
            opnstr = calc.opn(s);
        }catch(Exception ce){//используем opn как фильтр, оставляем только корректные выражения
        }
        ?*/
        //считаем, отсеивая лишнее
    }
}
