package org.example;

import org.example.Calc;
import org.example.CalcException;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;

import java.util.ArrayList;

public class JazzerTarget {
    public static void fuzzerTestOneInput(FuzzedDataProvider data){
        Calc calc = new Calc();

        var opnstr = "";
        var s = data.consumeString(100);
        var list = new ArrayList<Exception>();
        try {
            opnstr = calc.opn(s);
        }catch (CalcException ce){

        }catch(Exception ce){//используем opn как фильтр, оставляем только корректные выражения
            list.add(ce);
        }

        System.out.println(list.size());
        //?
        //считаем, отсеивая лишнее
    }
}
