package eu.tn.chaoscompiler.graphViz;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class GraphDisplayer {
    /**
     * Affiche le graphe dans un navigateur web
     */
    public static void displayDotFile(String dotFilePath) {
        // Lecture du fichier dans une chaîne de caractères
        StringBuilder dotFileString = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(dotFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                dotFileString.append(line).append("\n");
            }
            String encoded = URLEncoder.encode(dotFileString.toString(), StandardCharsets.UTF_8).replace("+", "%20");
            String url = "http://dreampuf.github.io/GraphvizOnline/#" + encoded;
            java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));

        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la lecture du fichier dot:" + e.getMessage());
        }
    }
}
