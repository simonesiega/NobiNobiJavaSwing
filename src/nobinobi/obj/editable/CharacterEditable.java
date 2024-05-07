package nobinobi.obj.editable;

import nobinobi.obj.Character;
import nobinobi.obj.Ability;
import nobinobi.obj.Card;


import java.util.ArrayList;

/**
 * Classe che implementa i player
 * NB é una classe editable
 */
public class CharacterEditable extends Character {
    /**
     * Super del costruttore vuoto
     */
    public CharacterEditable() {
        super();
    }

    public CharacterEditable(String l) {
        super(l);
    }

    /*
     * Setter della classe ability
     */
    public void setName(String name) {
        this.name = name;
    }
    public void setGenre(char genre) {
        this.genre = genre;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setStrength(int strength) {
        this.strength = strength;
    }
    public void setTechnique(int technique) {
        this.technique = technique;
    }
    public void setAbilities(Ability[] abilities) {
        this.abilities = abilities;
    }
    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }
}
