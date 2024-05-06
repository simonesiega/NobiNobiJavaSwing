package nobinobi;

import java.io.BufferedReader;
import java.io.PrintWriter;

abstract class Scene {
    protected String title;
    protected String description;
    protected String image;

    public Scene() {}

    protected Scene(String filetxt){
        String[] value= filetxt.split("#");
        title=value[0];
        description=value[1];
        image=value[2];
    }

    public Scene(String title, String description, String image) {
        this.title = title;
        this.description = description;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }


    public abstract Scene loadFromFile(String line);


    public void saveToFile(PrintWriter out){
        out.println(this.getTitle()+"#"+this.getDescription()+"#"+this.getImage() +"#");
    }
    public String toString(){
        return "TITLE: "+title+"\n"+
                "DESCRIPTION: "+description+"\n"+
                "IMAGE: "+image+"\n";
    }



}
