package backgroundObj.swing;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Classe che si occupa di creare il file html di stampa finale
 */
public class CreateHtmlFile {
    private final String pathName;
    private PrintWriter writer;

    /**
     * Costruttore classe
     * @param pathName path name del file da creare
     */
    public CreateHtmlFile(String pathName) {
        this.pathName = pathName;
        try {
            writer = new PrintWriter(new FileOutputStream(pathName));
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    public String getPathName() {
        return pathName;
    }

    public void saveLine(String line) {
        writer.write(line);
    }

    /**
     * init file html
     */
    public void initFile(){
        saveLine("""
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <title>Stampa Finale</title>
                    <link rel="stylesheet" type="text/css" href="Style.css">
                </head>
                <body>
                """);
    }

    /**
     * completa file html
     */
    public void finishFile(){
        saveLine("</body>\n" +
                "</html>");
        writer.close();
        createCssFile();
    }

    /**
     * crea il file css
     */
    private void createCssFile() {
        String cssPath = pathName.replace("Print.html", "Style.css");
        try (PrintWriter cssWriter = new PrintWriter(new FileOutputStream(cssPath))) {
            cssWriter.write("""
                    body {
                        font-family: Arial, sans-serif;
                        margin: 20px;
                    }
                    #intro-title, #epilogue-title, #round-title {
                        color: #333;
                        font-size: 24px;
                        margin-bottom: 10px;
                    }
                    #intro-description, #epilogue-description, #round-description {
                        font-size: 18px;
                        color: #666;
                        margin-bottom: 20px;
                    }
                    h1, h2, h3 {
                        margin: 0;
                    }
                    p {
                        margin: 0 0 10px 0;
                    }
                    """);
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    /**
     * apre il documento html
     */
    public void openDocument(){
        try {
            // Percorso del file HTML
            File htmlFile = new File(pathName);

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
