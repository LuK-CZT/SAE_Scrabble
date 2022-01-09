package Scrabble;

public class Case {

    private int couleur;
    private char lettre;
    private boolean recouvert;

    /**
     * pré-requis : uneCouleur est un entier entre 1 et 5
     * action : 
     */

    public Case(int uneCouleur) {
        this.couleur = uneCouleur;
    }

    /**
     * résultat : la couleur de this, un nombre entre 1 et 5
     */

    public int getCouleur() {
        return this.couleur;
    }

    /**
     * pré-requis : cette case est recouverte
     */

    public char getLettre() {
        return this.lettre;
    }

    /**
     * pré-requis : let est une lettre majuscule
     */

    public void setLettre(char let) {
        this.lettre = let;
        this.recouvert = true;
    }

    /**
     * résultat : vrai ssi la case est recouverte par une lettre
     */

    public boolean estRecouverte() {
        boolean res = false;
        if (this.recouvert){
            res = true;
        }
        return res;
    }


    public String toString() {
        return "{" +" couleur='" + getCouleur() + "'" +", lettre='" + getLettre() + "'" +", recouvert='" + estRecouverte() + "'" +"}";
    }
    

}



