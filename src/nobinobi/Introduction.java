package nobinobi;

import java.io.BufferedReader;
import java.io.IOException;


public class Introduction extends Scene {
    public Introduction(String title, String description, String image) {
        super(title, description, image);
    }
    public Introduction(){

    };

    public Introduction(String line)  {
        super(line);
    }

    public Scene loadFromFile(String line){
        return new Introduction(line);
    }
}
