package nobinobi.editable;

import nobinobi.*;
import nobinobi.Character;


import java.io.PrintWriter;
import java.util.ArrayList;

public class CharacterEditable extends Character {

    public CharacterEditable() {
    }

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

    public void setTecnique(int tecnique) {
        this.tecnique = tecnique;
    }

    public void setAbilities(Ability[] abilities) {
        this.abilities = abilities;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }
}
