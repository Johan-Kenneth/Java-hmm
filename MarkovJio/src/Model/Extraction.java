/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author johan
 */
public class Extraction
{

    //fonction qui retourne le maximum du produit de deux éléments de même indice pris dans deux tableaux différent.
    public double maxmult( int nbEt, int j, double delta[], double[][] trans )
    {
        double max = delta[0] * trans[0][j];
        int i;
        for (i = 1; i < nbEt; i++) {
            //System.out.println( "max  " + delta[i] * trans[i][j] );
            if (delta[i] * trans[i][j] > max) {
                max = delta[i] * trans[i][j];
            }
        }
        return max;
    }

    //fonction qui retourne l'indice du maximum du produit de deux éléments de même indice pris dans deux tableaux différents
    public int argMax( int nbEt, int j, double[] delta, double[][] trans )
    {
        int index = -1, i;//initialisation de index(on peut y mettre n'importe quelle valeur)
        for (i = 0; i < nbEt; i++) {
            if (maxmult( nbEt, j, delta, trans ) == delta[i] * trans[i][j]) {
                index = i;
                break;
            }
        }
        return index;
    }

    //fonction qui retourne le maximum d'un tableau
    public double max( double[] tab )
    {
        int i;
        double max = tab[0];
        for (i = 0; i < tab.length; i++) {
            if (tab[i] > max) {
                max = tab[i];
            }
        }
        return max;
    }

    //Fonction qui retourne l'indice du maximum dans un tableau
    public int argMax( double[] tab )
    {
        int index = -1, i;
        for (i = 0; i < tab.length; i++) {
            if (max( tab ) == tab[i]) {
                index = i;
            }
        }
        return index;
    }
    //Remarque tous les tableaux précédent sont de taille nbEt(qui corespond au nombre d'états)

    //Fonction qui retourne le chemin d'états le plus probable
    public double[][] delta( int nbEt, String[] tabObv, double[] init, double[][] emi, double[][] trans )
    {
        double delta[][] = new double[tabObv.length][nbEt];
        int i, t, j;
        for (i = 0; i < nbEt; i++) {
            delta[0][i] = init[i] * emi[i][0];
        }
        t = 1;
        while (t < tabObv.length) {
            j = 0;
            while (j < nbEt) {
                delta[t][j] = maxmult( nbEt, j, delta[t - 1], trans ) * emi[j][t];
                j++;
            }
            t++;
        }
        return delta;
    }

    //correct
    public int[] viterbi( int nbEt, String[] alphabet, String[] tabObv, double[] init, double[][] emi, double[][] trans )
    {
        int[][] psi = new int[tabObv.length][nbEt];
        double delta[][] = new double[tabObv.length][nbEt];
        int[] q = new int[tabObv.length];
        int i, t, j;
        for (i = 0; i < nbEt; i++) {
            delta[0][i] = init[i] * emi[i][index( tabObv[0], alphabet )];
            psi[0][i] = 0;
        }
        t = 1;
        while (t < tabObv.length) {
            j = 0;
            while (j < nbEt) {
                //System.out.println(tabObv.length);
                delta[t][j] = maxmult( nbEt, j, delta[t - 1], trans ) * emi[j][index( tabObv[t], alphabet )];
                psi[t][j] = argMax( nbEt, j, delta[t - 1], trans );
                //System.out.println( "delta[" + t + "][" + j + "] " + delta[t][j] );
                //System.out.println( "psi[" + t + "][" + j + "] " + psi[t][j] );
                //System.out.println( "j " + j );
                j++;
            }
            //System.out.println( "t " + t );
            t++;
        }
        //double prob = max(delta[tabObv.length-1]);//ð[tabObv.length doit être égal à nbEt
        q[tabObv.length - 1] = argMax( delta[tabObv.length - 1] );
        //System.out.println( "t " + t );
        t = t - 2;
        //System.out.println(tabObv.length);
        while (t >= 0) {
            //System.out.print(t);
            q[t] = psi[t + 1][q[t + 1]];
            t--;
        }
        return q;
    }

    //Fonction qui calcule la probabilité du chémin le plus probable
    public double probViterbi( int nbEt, String[] tabObv, double[] init, double[][] emi, double[][] trans )
    {
        int[][] psi = new int[tabObv.length][nbEt];
        double delta[][] = new double[tabObv.length][nbEt];
        //int[] q = new int[tabObv.length];
        int i, t, j;
        for (i = 0; i < nbEt; i++) {
            delta[0][i] = init[i] * emi[0][i];
            psi[0][i] = 0;
        }
        t = 1;
        while (t < tabObv.length) {
            j = 0;
            while (j < nbEt) {
                delta[t][j] = maxmult( nbEt, j, delta[t - 1], trans ) * emi[t][j];
                psi[t][j] = argMax( nbEt, j, delta[t - 1], trans );
                j++;
            }
            t++;
        }
        double prob = max( delta[tabObv.length - 1] );//delta[tabObv.length doit être égal à nbEt
        return prob;
    }

//    public String[] returnTarget( int[] viterbi, String[] tabObv )
//    {
//        LinkedList<> list = new LinkedList();
//        LC<String> List = new LC<>();
//        int i;
//        for (i = 0; i < viterbi.length; i++) {
//            if (viterbi[i] == 1) {
//                List.add( tabObv[i] );
//            }
//        }
//        String[] end = new String[List.size()];
//        i = 0;
//        Iterate<String> it = List.iterate();
//        while (it.hasNext()) {
//            end[i] = it.next();
//            i++;
//        }
//        return end;
//    }
    public int index( String obvCour, String[] etCour )
    {
        int j = 0, index = -1;
        while (index == -1 || j < etCour.length) {
            //System.out.println(etCour.length);
            if (obvCour.equals( etCour[j] )) {
                index = j;
                //System.out.print(index);
            }
            j++;

        }
        //
        return index;
    }
}
