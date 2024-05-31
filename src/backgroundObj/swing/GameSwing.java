package backgroundObj.swing;

import java.io.*;

/**
 * Classe che init il game Swing
 */
public class GameSwing {

    /**
     * Crea il frame chooseCharacter
     * @param Round numero round game
     */
    public GameSwing(int Round){
        ChooseCharacter ch = new ChooseCharacter(Round);
        ch.setVisible(true);
    }

    /**
     * Legge una determinata riga di un determinato file
     * @param filename path del file
     * @param line linea da leggere
     * @return linea nome(numero)
     */
    private String readLine(String filename, int line) {
        File file = new File(filename);

        if (!file.exists() || lenFile(filename) < line){
            return "";
        }

        String l = null;
        int c = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            while ((c != line))
            {
                c++;
            }
            l = reader.readLine();
            reader.close();
        } catch (IOException ioe) {
            System.out.println("Errore durante la lettura del file:");
            System.out.println(ioe.getMessage());
        }

        return l;
    }

    /**
     * Scrive in un file determinato il content
     * @param filename path del file
     * @param content contenuto da scrivere
     */
    public void writeFile(String filename, String content){
        File file = new File(filename);
        if (!file.exists()){
            createFileIfNotExists(file);
        }
        try{
            PrintWriter writer = new PrintWriter(file);
            writer.write(content);
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    /**
     * Crea un file
     * @param file file da creare
     */
    private static void createFileIfNotExists(File file) {
        try {
            if (!file.exists()) {
                if (!file.createNewFile()) {
                    System.out.println("Impossibile creare il file 'scenes.csv'.");
                    System.exit(-1);
                }
            }
        } catch (IOException e) {
            System.out.println("Errore durante la creazione del file:");
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * Conta la lunghezza di un file
     * @param filename path del file
     * @return int len
     */
    private int lenFile(String filename) {
        int c = 0;
        try{
            FileReader reader = new FileReader(filename);
            BufferedReader in = new BufferedReader(reader);
            while(in.readLine()!=null) c++;
        }catch(IOException e){
            e.printStackTrace();
        }

        return c;
    }


}
