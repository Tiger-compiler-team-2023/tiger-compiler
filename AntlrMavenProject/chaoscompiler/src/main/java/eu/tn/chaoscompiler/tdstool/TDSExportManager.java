package eu.tn.chaoscompiler.tdstool;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TDSExportManager {

    public static void saveJson(String s, String name) {
        String n = "tds_" + name + ".puml" ;
        File f = new File(n) ;
        try {
            f.createNewFile();
            FileWriter myWriter = new FileWriter(n);
            myWriter.write("@startjson tds\n" + s + "\n@endjson");
            myWriter.close();
        }
        catch (IOException e) {
            System.out.println("An error occured while trying to write in " + n);
        }
    }
}
