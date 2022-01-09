package Scrabble;

import javax.lang.model.util.ElementScanner14;

public class Plateau {
    public static int [][] plateau = { //1 gris 2 bc 3 bf 4 rose 5 rouge
        {5, 1, 1, 2, 1, 1, 1, 5, 1, 1, 1, 2, 1, 1, 5 } , 
        {1, 4, 1, 1, 1, 3, 1, 1, 1, 3, 1, 1, 1, 4, 1 } , 
        {1, 1, 4, 1, 1, 1, 2, 1, 2, 1, 1, 1, 4, 1, 1 } ,
        {2, 1, 1, 4, 1, 1, 1, 2, 1, 1, 1, 4, 1, 1, 2 } ,
        {1, 1, 1, 1, 4, 1, 1, 1, 1, 1, 4, 1, 1, 1, 1 } ,
        {1, 3, 1, 1, 1, 3, 1, 1, 1, 3, 1, 1, 1, 3, 1 } ,
        {1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1 } ,
        {5, 1, 1, 2, 1, 1, 1, 4, 1, 1, 1, 2, 1, 1, 5 } , //milieu
        {1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1 } ,
        {1, 3, 1, 1, 1, 3, 1, 1, 1, 3, 1, 1, 1, 3, 1 } ,
        {1, 1, 1, 1, 4, 1, 1, 1, 1, 1, 4, 1, 1, 1, 1 } ,
        {2, 1, 1, 4, 1, 1, 1, 2, 1, 1, 1, 4, 1, 1, 2 } ,
        {1, 1, 4, 1, 1, 1, 2, 1, 2, 1, 1, 1, 4, 1, 1 } ,
        {1, 4, 1, 1, 1, 3, 1, 1, 1, 3, 1, 1, 1, 4, 1 } , 
        {5, 1, 1, 2, 1, 1, 1, 5, 1, 1, 1, 2, 1, 1, 5 } , 
    };                        //
    
    private int[] couleurValeur = {1,2,3,2,3};

    private Case[][] g = new Case[15][15]; // g pour grille



    /**
     * Le constructeur de Plateau n’a pas d’argument et initialise
     * l’attribut à partir de la déclaration d’une variable locale
     * indiquant les couleurs de toutes les cases :
     * int [][] plateau = { {...} , {...} , ... };
     */
    public Plateau() {
        for (int i = 0 ; i < 15; i++ ){
            for (int j = 0 ; j < 15; j++ ){
                g[i][j] = new Case(plateau[i][j]);
            }
        }
    }
    /** */

    public Plateau(Case[][] plateau){
        this.g = plateau;
    }

    /**
     * résultat : chaîne décrivant ce Plateau
     */

    public String toString() {
        String plato = "   |01 |02 |03 |04 |05 |06 |07 |08 |09 |10 |11 |12 |13 |14 |15 |" + '\n';
        char letColonne = 'A';

        for (int i = 0; i < g.length; i++, letColonne++) {
            plato += (" " + letColonne + " |");

            for (int j = 0; j < g[0].length; j++) {
                if (g[i][j].getCouleur() == 1 && !g[i][j].estRecouverte()) {
                    plato += "   |";
                } 
                else if(g[i][j].estRecouverte()) plato += " " + g[i][j].getLettre() + " |";
                else plato += " " + g[i][j].getCouleur() + " |";
            }

            plato += '\n';
            for(int n = 0; n < 64; n++) plato += "_";
            plato += '\n';
        }
        return plato;
    }

    /**
     * pré-requis : mot est un mot accepté par CapeloDico,
     * 0 <= numLig <= 14, 0 <= numCol <= 14, sens est un élément
     * de {’h’,’v’} et l’entier maximum prévu pour e est au moins 25
     * résultat : retourne vrai ssi le placement de mot sur this à partir
     * de la case (numLig, numCol) dans le sens donné par sens à l’aide
     * des jetons de e est valide.
     */

    public boolean placementValide(String mot, int numLig, int numCol, char sens, MEE e) {
        boolean res = false;
        int nbCaseRecouvert = 0;
        int nbCaseNonRecouvert = 0;
        int[] tabTest2 = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

        if( ! g[7][7].estRecouverte()){
            res = mot.length() > 2;
            

            if (sens == 'v'){
                for (int i = numLig; i <= numLig - 1  + mot.length() && res; i++){
                    
                    res = res && numCol == 7 && numLig <= 7 && numLig + mot.length() >= 7;
    
                    if (g[i][numCol].estRecouverte()){
                    res = res && g[i][numCol].getLettre() == mot.charAt(i - numLig);
    
                    }else{
                        Ut.afficher(Ut.indiceLet(mot.charAt(i - numLig)));
                        res = res && e.getTabFreq()[Ut.indiceLet(mot.charAt(i - numLig))] > tabTest2[Ut.indiceLet(mot.charAt(i - numLig))]; //giles est jaune hihi
                        tabTest2[Ut.indiceLet(mot.charAt(i - numLig))]++;
                    }
                } 
            }
            if (sens == 'h'){

                res = res && numLig == 7 && numCol <= 7 && numCol + mot.length() >= 7;

                for (int i = numCol; i <= numCol - 1  + mot.length() && res; i++){

                    if (g[numLig][i].estRecouverte()){
                    res = res && g[numLig][i].getLettre() == mot.charAt(i - numCol);
    
                    }else{
                        res = res && e.getTabFreq()[Ut.indiceLet(mot.charAt(i - numCol))] > tabTest2[Ut.indiceLet(mot.charAt(i - numCol))]; //giles est jaune hihi
                        tabTest2[Ut.indiceLet(mot.charAt(i - numCol))]++;
    
                    }
                } 
            }
            
            
        }else{
            if (sens == 'v'){
                res = numLig - 1 + mot.length() <= 14;
    
                if (numLig > 0 && res){res = res && ! g[numLig - 1][numCol].estRecouverte();}
                if(numLig + mot.length() <= 14 && res){res = res && ! g[numLig + mot.length()][numCol].estRecouverte();}
    
                for (int i = numLig; i <= numLig - 1  + mot.length() && res; i++){
    
                    if (g[i][numCol].estRecouverte()){
                    nbCaseRecouvert ++;
                    res = res && g[i][numCol].getLettre() == mot.charAt(i - numLig);
    
                    }else{
                        res = res && e.getTabFreq()[Ut.indiceLet(mot.charAt(i - numLig))] > tabTest2[Ut.indiceLet(mot.charAt(i - numLig))]; //giles est jaune hihi
                        tabTest2[Ut.indiceLet(mot.charAt(i - numLig))]++;
                        nbCaseNonRecouvert ++;
                    }
                } 
            }
            if (sens == 'h'){
                res = numCol - 1 + mot.length() <= 14;
    
                if (numCol > 0 && res){res = res && ! g[numLig][numCol].estRecouverte();}
                if(numCol + mot.length() <= 14 && res){res = res && ! g[numLig][numCol + mot.length()].estRecouverte();}
    
                for (int i = numCol; i <= numCol - 1  + mot.length() && res; i++){

                    if (g[numLig][i].estRecouverte()){
                    nbCaseRecouvert ++;
                    res = res && g[numLig][i].getLettre() == mot.charAt(i - numCol);
    
                    }else{
                        res = res && e.getTabFreq()[Ut.indiceLet(mot.charAt(i - numCol))] > tabTest2[Ut.indiceLet(mot.charAt(i - numCol))]++; //giles est jaune hihi
                        tabTest2[Ut.indiceLet(mot.charAt(i - numCol))]++;
                        nbCaseNonRecouvert ++;
    
                    }
                } 
            }
            res = res && nbCaseNonRecouvert >= 1 && nbCaseRecouvert >= 1;
        }
        return res;
        
        
    }

    /**
    * pré-requis : le placement de mot sur this à partir de la case
    *  (numLig, numCol) dans le sens donné par sens est valide
    * résultat : retourne le nombre de points rapportés par ce placement, le
    *  nombre de points de chaque jeton étant donné par le tableau nbPointsJet.
    */
    public int nbPointsPlacement(String mot, int numLig, int numCol, char sens, int[] nbPointsJet) {
        int ptTotal = 0, ptLet = 0, multipleMot = 1;
        if (sens == 'v') {
            for (int i = 0; i < mot.length(); i++, numLig++) {
                if (g[numLig][numCol].getCouleur() <= 3 && g[numLig][numCol].getCouleur() > 1 ) {
                    ptLet += nbPointsJet[Ut.indiceLet(mot.charAt(i))]* couleurValeur[g[numLig][numCol].getCouleur() - 1];      
                } else {
                    multipleMot = couleurValeur[g[numLig][numCol].getCouleur() - 1];
                    ptLet += nbPointsJet[Ut.indiceLet(mot.charAt(i))];
                }

            }
        } else {

            for (int i = 0; i < mot.length(); i++, numCol++) {

                if (g[numLig][numCol].getCouleur() <= 3 && g[numLig][numCol].getCouleur() > 1 ) {
                    ptLet += nbPointsJet[Ut.indiceLet(mot.charAt(i))]* couleurValeur[g[numLig][numCol].getCouleur() - 1];
                } else {
                    multipleMot = couleurValeur[g[numLig][numCol].getCouleur() -1];
                    ptLet += nbPointsJet[Ut.indiceLet(mot.charAt(i))];
                }
            }
        }
        ptTotal = ptLet * multipleMot;
        return ptTotal;
    }

    /**
    * pré-requis : le placement de mot sur this à partir de la case
    *    (numLig, numCol) dans le sens donné par sens à l’aide des
    *    jetons de e est valide.
    *  action/résultat : effectue ce placement et retourne le
    *    nombre de jetons retirés de e.
    */

    public int place(String mot, int numLig, int numCol, char sens, MEE e){
        
        int nbJetonUtilisé = 0;
        if(sens == 'v'){
            for (int i = numLig ; i - numLig < mot.length(); i++, nbJetonUtilisé++){
                if ( ! g[i][numCol].estRecouverte()){
                    g[i][numCol].setLettre(mot.charAt(i - numLig));
                    Ut.afficher(mot.charAt(i- numLig));
                    e.retire(Ut.indiceLet(mot.charAt(i - numLig)));
                    
                }
            } 
        }
        if(sens == 'h'){
            for (int i = numCol ; i - numCol < mot.length(); i++ ,nbJetonUtilisé++){
                if ( ! g[numLig][i].estRecouverte()){
                    g[numLig][i].setLettre(mot.charAt(i - numCol));
                    e.retire(Ut.indiceLet(mot.charAt(i - numCol)));
                    
                }
            } 
        }

        return nbJetonUtilisé;
    }

    public static void main(String[] args) {
        Plateau test = new Plateau();
        int[] tab = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
        MEE e = new MEE(tab);
        if(test.placementValide("WTS", 7, 7, 'h', e )){
            test.place("WTS", 7, 7, 'h', e);
        }
        Ut.clearConsole();
        Ut.afficher(test.toString());
    
    }
}
