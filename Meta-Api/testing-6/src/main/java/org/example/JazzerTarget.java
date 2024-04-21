package org.example;

import org.example.Calc;
import org.example.CalcException;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class JazzerTarget {
    public static void fuzzerTestOneInput(FuzzedDataProvider data){
        Calc calc = new Calc();

        double opnstr = 0;
        var s = data.consumeString(100);
        var list = new ArrayList<Exception>();
        try {
            opnstr = calc.calculate(s);
        }catch (CalcException ce){

        }
       // catch(Exception ce){//используем opn как фильтр, оставляем только корректные выражения
            // list.add(ce);
     //       try (FileWriter writer = new FileWriter("trash.txt")) {
         //       writer.write(ce.getStackTrace().toString());
       //         writer.write(ce.getMessage());
           //     writer.write(s);
    //        } catch (IOException e) {
      //      }
     //   }

//        System.out.println(list.size());
        //?
        //считаем, отсеивая лишнее
    }
}