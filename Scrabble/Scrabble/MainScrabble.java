package Scrabble;

import javax.swing.text.TabSet;

public class MainScrabble {
    
    public static void main(String args[]){

        /*Ut.afficher("Combien de Joueurs pour cette Partie ? (Maximum 8 joueurs)  ");
        int nbJoueur = Ut.saisirEntier();
        while(!(nbJoueur <= 8 && nbJoueur >= 2)){
            Ut.clearConsole();
            Ut.afficher("Nombre Incorrect");
            Ut.afficher("Combien de Joueurs pour cette Partie ? (Maximum 8 joueurs)  ");
            nbJoueur = Ut.saisirEntier();
        }
        String[] tabNomJoueur = new String[nbJoueur];
        for (int i = 0; i < tabNomJoueur.length; i++) {
            Ut.afficher("Saisir non Joueurs " + (i+1) );
            tabNomJoueur[i] = Ut.saisirChaine();
        }*/

       
        /** 
        Extension qui vérifie si le mot est dans le dictionnaire 
         */
        Dico dico = new Dico(); // Crée une ArrayList qui contiens le contenu du fichier dicoReference
        dico.ajtMot();
       
        String[] tabNomJoueur = {"Rayan","Lucaslepd","Hitler"};
        Scrabble partie = new Scrabble(tabNomJoueur);
        partie.partie();

        Ut.afficherSL("Partie Terminé");
    }
}
