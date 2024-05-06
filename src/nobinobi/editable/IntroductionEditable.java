package nobinobi.editable;
import nobinobi.Introduction;

public class IntroductionEditable extends Introduction{
    public IntroductionEditable(){}

    public IntroductionEditable(String line){
        super(line);
    }

    public void setTitle    (String title) {
        this.title = title;
    }

    public  void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }



}
