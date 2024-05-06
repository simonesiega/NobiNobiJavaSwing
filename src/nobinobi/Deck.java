package nobinobi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Deck <T extends  Scene>{

    private ArrayList <T> cards;

    public Deck(){
        this.cards=new ArrayList<T>();

    }
    /*public Deck(BufferedReader reader) throws IOException{
        String line;
        while((line=reader.readLine())!=null){

        }
    }*/

    public void addScene(T s){
        this.cards.add(s);
    }
    public void removeCard(int index){
        this.cards.remove(index);

    }
    public Scene getScene(int index){
        return cards.get(index) ;


    }

    public int getSize(){
        return this.cards.size();
    }
    public void save(PrintWriter out){
        for(T s: this.cards){
            s.saveToFile(out);
        }
    }

    @Override
    public String toString() {
        String res="carta "+this.getSize()+": \n";
        for (int i = 0; i <this.getSize(); i++) {
            res=res+this.getScene(i)+"\n";
        }

        return res;
    }
}
