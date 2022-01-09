package Scrabble;

public class Joueur {

    private String nom;
    private MEE chevalet;
    private int score;

    public Joueur(String nom) {
        this.nom = nom;
        this.score = 0;
        this.chevalet = new MEE(26);
        
    }

    public String toString() {
        return " nom='" + this.nom + "'" + ", score='" + getScore() + "'" + "\n Chevalet : " + this.chevalet.toString();
    }

    public int getScore() {
        return this.score;
    }
    public MEE getChevalet() {
        return chevalet;
    }

    public void ajtScore(int score) {
        this.score += score;
    }

    /**
     * pré-requis : nbPointsJet indique le nombre de points rapportés par
     * chaque jeton/lettre
     * résultat : le nombre de points total sur le chevalet de ce joueur
     * suggestion : bien relire la classe MEE !
     */
    public int nbPointsChevalet(int[] nbPointsJet) {
        int res = this.chevalet.sommeValeurs(nbPointsJet);
        return res;
    }

    /**
     * pré-requis : les éléments de s sont inférieurs à 26
     * action : simule la prise de nbJetons jetons par this dans le sac s,
     * dans la limite de son contenu.
     */
    public void prendJetons(MEE s, int nbJetons) {
        s.transfereAleat(this.chevalet, nbJetons);
    }

    /**
     * pré-requis : les éléments de s sont inférieurs à 26
     * et nbPointsJet.length >= 26
     * action : simule le coup de this : this choisit de passer son tour,
     * d’échanger des jetons ou de placer un mot
     * résultat : -1 si this a passé son tour, 1 si son chevalet est vide,
     * et 0 sinon
     */
    public int joue(Plateau p, MEE s, int[] nbPointsJet) {
        int res = 0;
        Ut.afficher("Que voulez vous faire "+ this.nom + ", placer , passer ou echanger ?");
        String rep = Ut.saisirChaine();
        switch(rep){
            case "passer" : res = -1; break;
            case "echanger" : if(this.chevalet.estVide()) res = 1;
            else {echangeJetons(s) ; res = 0; }
            break;
            case "placer" : if(this.chevalet.estVide()) res = 1; 
            else { joueMot(p, s, nbPointsJet); res = 0; }
            break;
        }
        return res;
    }


    /** pré-requis : les éléments de s sont inférieurs à 26
     * et nbPointsJet.length >= 26
     * action : simule le placement d’un mot de this :
     * a) le mot, sa position sur le plateau et sa direction, sont saisis
     * au clavier
     * b) vérifie si le mot est valide
     * c) si le coup est valide, le mot est placé sur le plateau
     * résultat : vrai ssi ce coup est valide, c’est-à-dire accepté par
     * CapeloDico et satisfaisant les règles détaillées plus haut
     * stratégie : utilise la méthode joueMotAux
    */
    public boolean joueMot(Plateau p, MEE s, int[] nbPointsJet){
        boolean res = false;
        Ut.afficher("Saisir le mot à placer");String mot= Ut.saisirChaine();Ut.clearConsole();
        Ut.afficher("Saisir la ligne où positioner le mot"); int numLig = Ut.saisirEntier() - 1 ;Ut.clearConsole();
        Ut.afficher("Saisir la colonne où positioner le mot"); int numCol = Ut.saisirEntier() - 1;Ut.clearConsole();
        Ut.afficher("Saisir le sens h ou v"); char sens = Ut.saisirCaractere();Ut.clearConsole();
        if(Dico.motsDico.contains(mot)){
            if (p.placementValide(mot, numLig, numCol, sens, this.chevalet) ){
            joueMotAux(p, s, nbPointsJet, mot, numLig, numCol, sens);
            res = true;
            }else {Ut.afficherSL("Position non Valide");joue(p, s, nbPointsJet);}
        }else {Ut.afficherSL("Mot non existant dans le dictionnaire référence");joue(p, s, nbPointsJet);}
        
        return res;
    }

    /** pré-requis : cf. joueMot et le placement de mot à partir de la case
     * (numLig, numCol) dans le sens donné par sens est valide
     * action : simule le placement d’un mot de this
    */
    public void joueMotAux(Plateau p, MEE s, int[] nbPointsJet, String mot, int numLig, int numCol, char sens) {
        p.place(mot, numLig, numCol, sens, this.chevalet);
        this.ajtScore(p.nbPointsPlacement(mot, numLig, numCol, sens, nbPointsJet));
    }

    /**
     * pré-requis : sac peut contenir des entiers de 0 à 25
     * action : simule l’échange de jetons de ce joueur :
     * - saisie de la suite de lettres du chevalet à échanger
     * en vérifiant que la suite soit correcte
     * - échange de jetons entre le chevalet du joueur et le sac
     * stratégie : appelle les méthodes estCorrectPourEchange et echangeJetonsAux
    */
    public void echangeJetons(MEE sac) {
        Ut.afficher("Saisir la/les Lettre(s) à échanger");
        String ensJetons = Ut.saisirChaine();
        if(estCorrectPourEchange(ensJetons)) echangeJetonsAux(sac, ensJetons);


    }

    /** résultat : vrai ssi les caractères de mot correspondent tous à des
     * lettres majuscules et l’ensemble de ces caractères est un
     * sous-ensemble des jetons du chevalet de this
    */
    public boolean estCorrectPourEchange (String mot) {
        int[] tabTest2 = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        boolean res = true;
        for (int i = 0; i < mot.length();i++){
            res = res && mot.charAt(i) == Character.toUpperCase(mot.charAt(i));
            res = res && chevalet.getTabFreq()[Ut.indiceLet(mot.charAt(i))] > tabTest2[Ut.indiceLet(mot.charAt(i ))]; //giles est jaune hihi
            tabTest2[Ut.indiceLet(mot.charAt(i))]++;
        }
        return res;
    }

    /** pré-requis : sac peut contenir des entiers de 0 à 25 et ensJetons
     * est un ensemble de jetons correct pour l’échange
     * action : simule l’échange de jetons de ensJetons avec des
     * jetons du sac tirés aléatoirement.
    */
    public void echangeJetonsAux(MEE sac, String ensJetons) {
        for(int i= 0; i< ensJetons.length() ; i++ ){
            this.chevalet.transfere(sac, Ut.indiceLet(ensJetons.charAt(i)));
            if(sac.estVide()) Ut.afficher("le sac est vide.");
            else sac.transfereAleat(this.chevalet, 1);  
        }
    }

}
