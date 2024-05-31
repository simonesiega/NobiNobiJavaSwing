package backgroundObj.swing;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class CreateHtmlFile {
    private String pathName;
    private PrintWriter writer;

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
                "    <link rel=\"stylesheet\" type=\"text/css\" href=\"Style.css\">\n" +
                "</head>\n" +
                "<body>\n");
    }

    public void finishFile(){
        saveLine("</body>\n" +
                "</html>");
        writer.close();
        createCssFile();
    }

    private void createCssFile() {
        String cssPath = pathName.replace("Print.html", "Style.css");
        try (PrintWriter cssWriter = new PrintWriter(new FileOutputStream(cssPath))) {
            cssWriter.write("body {\n" +
                    "    font-family: Arial, sans-serif;\n" +
                    "    margin: 20px;\n" +
                    "}\n" +
                    "#intro-title, #epilogue-title, #round-title {\n" +
                    "    color: #333;\n" +
                    "    font-size: 24px;\n" +
                    "    margin-bottom: 10px;\n" +
                    "}\n" +
                    "#intro-description, #epilogue-description, #round-description {\n" +
                    "    font-size: 18px;\n" +
                    "    color: #666;\n" +
                    "    margin-bottom: 20px;\n" +
                    "}\n" +
                    "h1, h2, h3 {\n" +
                    "    margin: 0;\n" +
                    "}\n" +
                    "p {\n" +
                    "    margin: 0 0 10px 0;\n" +
                    "}\n");
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }
}
