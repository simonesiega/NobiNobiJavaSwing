import backgroundObj.swing.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args){
        GameSwing test = new GameSwing();

        while()
        try {
            // Percorso del file HTML
            File htmlFile = new File("src/saves/gameplay/finalprint/Print.html");

            // Controlla se il Desktop è supportato
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                // Verifica se il file esiste
                if (htmlFile.exists()) {
                    // Apri il file HTML nel browser predefinito
                    desktop.browse(htmlFile.toURI());
                } else {
                    System.out.println("Il file HTML non esiste: " + htmlFile.getAbsolutePath());
                }
            } else {
                System.out.println("Il Desktop non è supportato su questa piattaforma.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}