package Scrabble;
public class MEE {

    private int[] tabFreq; // tabFreq[i] est le nombre d’exemplaires (fréquence) de l’élément i
    private int nbTotEx; // nombre total d’exemplaires

    public int getNbTotEx() {
        return this.nbTotEx;
    }

    public int[] getTabFreq(){
        int[] tab = new int[tabFreq.length];
        for (int i = 0; i < tabFreq.length; i++){
            tab[i] = tabFreq[i];
        }
            
       return tab;
    }

    /**
     * pré-requis : max >= 0
     * action : crée un multi-ensemble vide dont les éléments seront
     * inférieurs à max
     */

    public MEE(int max) {
        this.tabFreq = new int[max];
        this.nbTotEx = 0;
    }

    /**
     * pré-requis : les éléments de tab sont positifs ou nuls
     * action : crée un multi-ensemble dont le tableau de fréquences est
     * . une copie de tab
     */

    public MEE(int[] tab) {
        this.tabFreq = new int[tab.length];
        for(int i = 0; i< this.tabFreq.length ; i++){
            this.tabFreq[i] = tab[i];
            this.nbTotEx += tab[i];
        }
        
    }

    /**
     * constructeur par copie
     */
    public MEE(MEE e) {
        this(e.tabFreq);
    }

    public String toString(){
        String res = "";
        for(int i = 0; i< this.tabFreq.length; i++){
            if(this.tabFreq[i] > 0){
                res += Ut.letIndice(i) + " : " + this.tabFreq[i] + " ";
            }
            
        }
        return res;
    }

    /**
     * résultat : vrai ssi cet ensemble est vide
     */

    public boolean estVide() {
        return this.nbTotEx == 0;
    }

    /**
     * pré-requis : 0 <= i < tabFreq.length
     * action : ajoute un exemplaire de i à this
     */

    public void ajoute(int i) {
        this.tabFreq[i]++;
        this.nbTotEx++;
    }

    /**
     * pré-requis : 0 <= i < tabFreq.length
     * action/résultat : retire un exemplaire de i de this s’il en existe,
     * et retourne vrai ssi cette action a pu être effectuée
     */

    public boolean retire(int i) {
        boolean res = false;
        if(this.tabFreq[i] > 0){
            this.tabFreq[i]--;
            this.nbTotEx--;
            res = true;
        }
        return res;
    }

    /**
     * pré-requis : this est non vide
     * action/résultat : retire de this un exemplaire choisi aléatoirement
     * et le retourne
     */

    public int retireAleat() {
        int i = Ut.randomMinMax(0, this.tabFreq.length - 1);
        boolean valid = false;
        while(!valid){
            valid = retire(i);
        }
        return i;
    }

    /**
     * pré-requis : 0 <= i < tabFreq.length
     * action/résultat : transfère un exemplaire de i de this vers e s’il
     * en existe, et retourne vrai ssi cette action a pu être effectuée
     */

    public boolean transfere(MEE e, int i) {
        boolean res = false;
        if(retire(i)){
            e.ajoute(i);res = true;
        }
        return res;
    }

    /**
     * pré-requis : k >= 0
     * action : tranfère k exemplaires choisis aléatoirement de this vers e
     * dans la limite du contenu de this
     * résultat : le nombre d’exemplaires effectivement transférés
     */
    public int transfereAleat(MEE e, int k) {
        int i = 0;
        for(;i < k;i++){
            boolean valid = false;
            while(!valid){
                valid = transfere(e, Ut.randomMinMax(0, this.tabFreq.length -1));
            }   
        }
        return i;
    }

    /**
     * pré-requis : tabFreq.length <= v.length
     * résultat : retourne la somme des valeurs des exemplaires des
     * éléments de this, la valeur d’un exemplaire d’un élément i
     * de this étant égale à v[i]
     */


    public int sommeValeurs(int[] v) {
        int somme = 0;
        for (int i = 0; i > tabFreq.length; i++ ){
            somme += tabFreq[i] * v[i];
        }
        return somme;
    }



}