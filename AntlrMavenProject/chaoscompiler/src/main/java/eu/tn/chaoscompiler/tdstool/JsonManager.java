package eu.tn.chaoscompiler.tdstool;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class JsonManager {

    public static void saveJson(String s) {
        File f = new File("mytds.json") ;
        try {
            f.createNewFile();
            FileWriter myWriter = new FileWriter("mytds.json");
            myWriter.write("@startjson tds\n" + s + "\n@endjson");
            myWriter.close();
        }
        catch (IOException e) {
            System.out.println("An error occured while trying to write in mytds.json");
        }
    }
}
