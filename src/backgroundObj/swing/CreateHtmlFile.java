package backgroundObj.swing;

import nobinobi.editable.AbilityEditable;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class CreateHtmlFile {
    private String pathName;
    private PrintWriter writer;

    // "src/saves/gameplay/finalprint/Print.html"
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

    public void initFile(){
        saveLine("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Stampa Finale</title>\n" +
                "</head>\n" +
                "<body>\n");
    }

    public void finishFile(){
        saveLine("</body>\n" +
                "</html>");
        writer.close();
    }
}
