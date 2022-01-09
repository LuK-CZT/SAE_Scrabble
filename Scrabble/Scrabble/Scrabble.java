package Scrabble;

public class Scrabble {

    private Joueur[] joueurs;
    private int numJoueur; // joueur courant (entre 0 et joueurs.length-1)
    private Plateau plateau;
    private MEE sac;
    private static int [] nbPointsJet =  { 1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 10, 1, 2, 1, 1, 3, 8, 1, 1, 1, 1, 4, 10, 10, 10, 10 };


    public Joueur[] getJoueurs() {
        return this.joueurs;
    }

    public void setJoueurs(Joueur[] joueurs) {
        this.joueurs = joueurs;
    }

    public int getNumJoueur() {
        return this.numJoueur;
    }

    public void setNumJoueur(int numJoueur) {
        this.numJoueur = numJoueur;
    }

    public Plateau getPlateau() {
        return this.plateau;
    }

    public void setPlateau(Plateau plateau) {
        this.plateau = plateau;
    }

    public MEE getSac() {
        return this.sac;
    }

    public void setSac(MEE sac) {
        this.sac = sac;
    }

    public Scrabble(String[] tabNomJoueur){
        int nbJoueur = tabNomJoueur.length;
        joueurs = new Joueur[nbJoueur];
        for (int i = 0; i < nbJoueur; i++){
            joueurs[i] = new Joueur(tabNomJoueur[i]);
        }
        plateau = new Plateau();
        int[] tabSac = { 9, 2, 2, 3, 15, 2, 2, 2, 8, 1, 1, 5, 3, 6, 6, 2, 1, 6, 6, 6, 6, 2, 1, 1, 1, 1 };
        sac = new MEE(tabSac);
        numJoueur = Ut.randomMinMax(0, nbJoueur);
    }


    public void partie(){

        boolean chevaletEtSacVide = false;
        boolean blocage = false;
        
        for (int i = 0; i < joueurs.length ; i++ ){
            this.joueurs[i].prendJetons(this.sac, 7);
        }
        while( !(chevaletEtSacVide) || !blocage){
            blocage = true;
            for (; this.numJoueur < joueurs.length && !chevaletEtSacVide; this.numJoueur++) {

                joueurs[numJoueur].prendJetons(sac, 7 - joueurs[numJoueur].getChevalet().getNbTotEx());

                Ut.clearConsole();
                Ut.afficher(toString());
                Ut.afficher(joueurs[numJoueur].toString());

                int res = 1;
                
                res = (joueurs[numJoueur].joue(plateau, sac, nbPointsJet));
                
                chevaletEtSacVide = res == 1 && sac.estVide();
                blocage = blocage && res == -1;
            }
            numJoueur = 0;
        }



        for (int i = 0; i < joueurs.length ; i++ ){
            if(joueurs[i] == joueurs[numJoueur] && chevaletEtSacVide){
                int scoreJoueurs = 0;

                for (int j = 0; j < joueurs.length ; j++ ){
                    scoreJoueurs += joueurs[j].nbPointsChevalet(nbPointsJet);
                }

                joueurs[i].ajtScore(scoreJoueurs);
            }else{
                joueurs[i].ajtScore( - joueurs[i].nbPointsChevalet(nbPointsJet));
            }
        }

        afficheGagnant();

    }


    
    public String afficheGagnant(){
        Joueur[] vainqueurs = new Joueur[joueurs.length]; int scoreMax = 0; String res = "";
        int j = 0;
        for (int i = 0; i < joueurs.length; i++) {
            if(joueurs[i].getScore() > scoreMax){
                vainqueurs[j] = joueurs[i]; 
                scoreMax = joueurs[i].getScore();
            } else if(joueurs[i].getScore() == scoreMax){
                j++;   
                vainqueurs[j] = joueurs[i];     
            }
        }
        if(vainqueurs[1] != null){
            res = "Les vainqueurs sont "; 
            for(int i = 0; i < vainqueurs.length ; i++){
                if(vainqueurs[i] != null){
                    res+= vainqueurs[i].toString() + ", ";
                }
            }
        }
        else {
            res = "Le vainqueur est " + vainqueurs[0].toString();
        }
        return res;
    }
    





    public static int[] getNbPointsJet(){
        int[] tab = new int[nbPointsJet.length];
        for (int i = 0; i < nbPointsJet.length; i++){   
            tab[i] = nbPointsJet[i];
        }
            
       return tab;
    }

    public String toString(){
        return joueurs[this.numJoueur].toString() + '\n' + this.plateau.toString();
    }

}
