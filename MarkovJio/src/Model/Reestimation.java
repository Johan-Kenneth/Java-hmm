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
public class Reestimation
{

    //correct
    public double forward( int nbEt, String[] alphabet, String[] tabObv, double[] init, double[][] emi, double[][] trans )
    {
        int i, j, t;
        double temp, prob = 0.0;
        double[][] alpha = new double[nbEt][tabObv.length];
        for (i = 0; i < nbEt; i++) {
            alpha[i][0] = init[i] * emi[i][index( tabObv[0], alphabet )];
            //System.out.println( "alpha[t,0] " + alpha[i][0] );
        }

        t = 0;
        while (t < tabObv.length - 1) {
            j = 0;

            while (j < nbEt) {
                temp = 0.0;
                for (i = 0; i < nbEt; i++) {
                    //System.out.println( "alpha " + alpha[i][t] );
                    //System.out.println( "trans " + trans[i][j] );
                    //System.out.println( "t " + ( t + 1 ) + ", i " + ( i + 1 ) + ", j " + ( j + 1 ) );
                    temp += alpha[i][t] * trans[i][j];
                }
                //System.out.println( "emi " + emi[j][index( tabObv[t + 1], alphabet )] );
                //System.out.println( "temp " + temp );
                alpha[j][t + 1] = temp * emi[j][index( tabObv[t + 1], alphabet )];
                //System.out.println( "alpha [" + j + "," + ( t + 1 ) + "]" + ( alpha[j][t + 1] ) );
                j++;
            }
            t++;
        }
        for (i = 0; i < nbEt; i++) {
            //System.out.println( "prob " + prob );
            prob += alpha[i][tabObv.length - 1];
        }
        return prob;
    }

    //correct
    public double backward( int nbEt, String[] alphabet, String[] tabObv, double[] init, double[][] emi, double[][] trans )
    {
        int i, j, t;
        double prob = 0.0;
        double[][] beta = new double[nbEt][tabObv.length];
        for (i = 0; i < nbEt; i++) {
            beta[i][tabObv.length - 1] = 1.0;//emi[i][index(tabObv[tabObv.length-1],alphabet)];
        }
        t = tabObv.length - 2;
        while (t >= 0) {
            i = 0;
            while (i < nbEt) {
                //System.out.println( "i " + i + " t " + t );
                beta[i][t] = 0;
                for (j = 0; j < nbEt; j++) {
                    //System.out.println( "i " + i + " t " + t + " j " + j );
                    beta[i][t] += trans[i][j] * emi[j][index( tabObv[t + 1], alphabet )] * beta[j][t + 1];
                }
                i++;
            }
            t--;
        }
        for (i = 0; i < nbEt; i++) {
            prob += beta[i][0] * init[i] * emi[i][index( tabObv[0], alphabet )];
        }
        return prob;
    }

    public double forward1( int nbEt, String[] alphabet, String[] tabObv, double[] init, double[][] emi, double[][] trans )
    {
        int i, j, t;
        double temp, temp1, prob = 0.0;
        double[][] alpha = new double[nbEt][tabObv.length];
        for (i = 0; i < nbEt; i++) {
            alpha[i][0] = init[i] * emi[i][index( tabObv[0], alphabet )];
            //System.out.println(alpha[0][i]);
        }

        t = 1;
        while (t < tabObv.length) {
            for (i = 0; i < nbEt; i++) {
                temp = 0.0;
                for (j = 0; j < nbEt; j++) {
                    temp += alpha[i][t - 1] * trans[i][j];
                    //System.out.println(alpha[t-1][j]+"et"+trans[j][i]);
                }
                temp1 = 0;
                for (j = 0; j < nbEt; j++) {
                    temp1 = emi[j][index( tabObv[t], alphabet )];
                    //System.out.println(alpha[t-1][j]+"et"+trans[j][i]);
                }
                alpha[i][t] = temp * temp1;//emi[i][index(tabObv[t],alphabet)];
                //System.out.println("temp"+temp);
                //System.out.println(emi[i][index(tabObv[t],alphabet)]);
                //System.out.println(alpha[t][i]);
            }
            t++;

        }
        for (i = 0; i < nbEt; i++) {
            prob += alpha[i][tabObv.length - 1];
        }

        //System.out.println(prob);
        return prob;
    }

    //fonction qui calcule la probabilité d'émettre la dernière observation à l'intant t+1 sachant que l'on part d'un état à l'instant t
    //page 395 et 396
    //Algorithme corrigé à l'aide du livre de Laurent Behelin page 5
    public double backward1( int nbEt, String[] alphabet, String[] tabObv, double[] init, double[][] emi, double[][] trans )
    {
        int i, j, t;
        double prob = 0.0;
        double[][] beta = new double[nbEt][tabObv.length];
        for (i = 0; i < nbEt; i++) {
            beta[i][tabObv.length - 1] = 1.0;//emi[i][index(tabObv[tabObv.length-1],alphabet)];
        }
        t = tabObv.length - 2;
        while (t >= 0) {
            for (i = 0; i < nbEt; i++) {
                beta[i][t] = 0;
                for (j = 0; j < nbEt; j++) {
                    beta[i][t] += trans[i][j] * emi[j][index( tabObv[t + 1], alphabet )] * beta[j][t + 1];
                    /*System.out.println("trans"+trans[i][j]);
                    System.out.println("emi"+emi[j][index(tabObv[t],alphabet)]);
                    System.out.println("beta t+1 "+beta[t+1][j]);
                    System.out.println("calcul"+0.3*0.0*1.0);
                    System.out.println("temp"+ temp);*/

                }
                // beta[t][i] = temp;

                ///System.out.println("beta"+beta[t][i]);
            }
            t--;
        }

        for (i = 0; i < nbEt; i++) {
            prob += init[i] * beta[i][0] * emi[i][index( tabObv[0], alphabet )];
        }
        return prob;
    }

    public double[][] backwardForward( int nbEt, String[] alphabet, String[] tabObv, double[] init, double[][] emi, double[][] trans )
    {
        double[][] alpha = alpha( nbEt, alphabet, tabObv, init, emi, trans );
        double[][] beta = beta( nbEt, alphabet, tabObv, init, emi, trans );
        double[][] eps = new double[nbEt][tabObv.length];
        for (int i = 0; i < nbEt; i++) {
            for (int j = 0; j < tabObv.length; j++) {
                eps[i][j] = alpha[i][j] * beta[i][j] * emi[i][j];
            }
        }
        return eps;
    }

    public void BaumWelch( int nbEt, int nbFois, String[] alphabet, String[] tabObv, HMM hmm )
    {
        double[][] trans = hmm.getA();
        double[][] emi = hmm.getB();
        double[] init = hmm.getPi();

        double[][] trans_new = new double[trans.length][trans.length];
        double[][] emi_new = new double[emi.length][emi[0].length];
        int i, j, k, t;
        double gamma, ksi, gamma_sum, ksi_sum, gamma_num, gamma_num_sum;
        double[][] fwd = alpha( nbEt, alphabet, tabObv, init, emi, trans );
        double[][] bwd = beta( nbEt, alphabet, tabObv, init, emi, trans );
        double prob = forward( nbEt, alphabet, tabObv, init, emi, trans );
        //state change probability
        for (i = 0; i < trans.length; i++) {
            for (j = 0; j < trans[i].length; j++) {
                /*System.out.println(trans[i].length);
                System.out.println("test1");*/
                gamma = 0;
                ksi = 0;

                for (k = 0; k < nbFois; k++) {
                    //System.out.println("test2");
                    gamma_sum = 0;
                    ksi_sum = 0;

                    for (t = 0; t < tabObv.length - 1; t++) {
                        ksi_sum += fwd[i][t] * trans[i][j] * emi[j][index( tabObv[t + 1], alphabet )] * bwd[j][t + 1];
                        gamma_sum += fwd[i][t] * bwd[i][t];
                        //System.out.println("test3");
                    }
                    ksi += ( 1 / prob ) * ksi_sum;
                    gamma += ( 1 / prob ) * gamma_sum;
                }
                trans_new[i][j] = ksi / gamma;
            }
        }
        //affichetab(trans_new);
        //System.out.println("end trans");
        //emission probability
        for (i = 0; i < emi.length; i++) {
            for (j = 0; j < emi[i].length; j++) {
                gamma_num = 0;
                gamma = 0;

                for (k = 0; k < nbFois; k++) {

                    gamma_num_sum = 0;
                    gamma_sum = 0;

                    for (t = 0; t < tabObv.length - 1; t++) {
                        if (index( tabObv[t], alphabet ) == j) {
                            gamma_num_sum += fwd[i][t] * bwd[i][t];
                        }
                        gamma_sum += fwd[i][t] * bwd[i][t];
                    }
                    gamma_num += ( 1 / prob ) * gamma_num_sum;
                    gamma += ( 1 / prob ) * gamma_sum;
                }
                emi_new[i][j] = gamma_num / gamma;
            }
        }
        //affichetab(emi_new);
        //System.out.println("end emi");
        for (i = 0; i < emi.length; i++) {
            for (j = 0; j < emi[i].length; j++) {
                emi[i][j] = emi_new[i][j];
            }
        }
        for (i = 0; i < trans.length; i++) {
            for (j = 0; j < trans[i].length; j++) {
                trans[i][j] = trans_new[i][j];
            }
        }

    }

    public double[][] rTrans( int nbEt, String[] alphabet, String[] tabObv, double[] init, double[][] emi, double[][] trans )
    {
        double[][] trans_new = new double[trans.length][trans.length];
        int i, j, t;
        double gamma_sum, ksi_sum, temp, temp1;
        double[][][] ksi = ksi( nbEt, alphabet, tabObv, init, emi, trans );
        double[][] gamma = gamma( nbEt, alphabet, tabObv, init, emi, trans );
        //state change probability
        for (i = 0; i < trans.length; i++) {
            for (j = 0; j < trans[i].length; j++) {
                /*System.out.println(trans[i].length);
                    System.out.println("test1");*/
                temp = 0;
                temp1 = 0;
                //System.out.println("test2");
                gamma_sum = 0;
                ksi_sum = 0;

                for (t = 0; t < tabObv.length - 1; t++) {
                    ksi_sum += ksi[t][i][j];
                    gamma_sum += gamma[t][i];
                    //System.out.println("test3");
                }

                temp += ksi_sum;
                temp1 += gamma_sum;

                trans_new[i][j] = temp / temp1;
            }
        }
        return trans_new;
    }

    public double[][] rEmi( int nbEt, String[] alphabet, String[] tabObv, double[] init, double[][] emi, double[][] trans )
    {
        double[][] emi_new = new double[emi.length][emi[0].length];
        int i, j, t;
        double gamma_sum, gamma_num_sum, temp, temp1;
        double[][] gamma = gamma( nbEt, alphabet, tabObv, init, emi, trans );

        for (i = 0; i < emi.length; i++) {
            for (j = 0; j < emi[i].length; j++) {
                temp = 0;
                temp1 = 0;

                gamma_num_sum = 0;
                gamma_sum = 0;

                for (t = 0; t < tabObv.length - 1; t++) {
                    if (index( tabObv[t], alphabet ) == j) {
                        gamma_num_sum += gamma[t][i];
                    }
                    gamma_sum += gamma[t][i];
                }
                temp += gamma_num_sum;
                temp1 += gamma_sum;
                emi_new[i][j] = temp / temp1;
            }
        }
        return emi_new;
    }

    //correct
    public void BaumWelch2( int nbEt, int nbFois, String[] alphabet, String[] tabObv, double[] init, double[][] emi, double[][] trans )
    {
        for (int i = 0; i < nbFois; i++) {
            //state change probability
            for (int j = 0; j < trans.length; j++) {
                System.arraycopy( rTrans( nbEt, alphabet, tabObv, init, emi, trans )[j], 0, trans[j], 0, trans[j].length );

            }
            //affichetab(trans_new);
            //System.out.println("end trans");
            //emission probability
            for (int j = 0; j < emi.length; j++) {
                System.arraycopy( rEmi( nbEt, alphabet, tabObv, init, emi, trans )[j], 0, emi[j], 0, emi[j].length );

            }

            //affichetab(emi_new);
            System.arraycopy( rInit( nbEt, alphabet, tabObv, init, emi, trans ), 0, init, 0, init.length );

        }

    }

    //Complexité de Backward et Forward O(n²T)
    /*--------------------------Formules de réestimation selon Baum Welch-----------------------
    page 401 et 402 Apprentissage artificiel concepts et algorithmes
    Problème :Comment éffectuer la réestimation des probabilités lorsqu'on ne dispose
    dispose pas d'un alphabet d'observation défini.
    Recherches à éffectuer :
        Sagit -il du problème d'overfilling?
        https://cs.cmu.edu/afs/cs.cmu.edu/projet/theo-11/www/wwkb/
        Information extraction with HMM and Shrinkage.Dayne Freitag andAndrew McCallum
        In AAAT99 Workshop on ML for Information Extraction,page 31-36,1999
        Les réestimation fait ici ne suivent ni :
            -L'entraînement de Viterbi
            -L'entraine de Welch Baum
    Ces formules premettent d'affiner un modèle de Markov caché
     */
    public double[][][] ksi( int nbEt, String[] alphabet, String[] tabObv, double[] init, double[][] emi, double[][] trans )
    {
        int t, i, j;
        double[] prob = new double[tabObv.length];
        double[][] alpha = alpha( nbEt, alphabet, tabObv, init, emi, trans );
        double[][] beta = beta( nbEt, alphabet, tabObv, init, emi, trans );
        double[][][] ksi = new double[tabObv.length][nbEt][nbEt];
        //la formule est ksi[t][i][j]=(((alpha[t][i]*trans[i][j])*emi[t+1][j])*beta[t+1][j])/
        //(forward(nbEt,tabObv,init,emi,trans)*(beta(nbEt,tabObv,init,emi,trans));
        /*for (t = 0; t < tabObv.length - 1; t++) {
            for (i = 0; i < nbEt; i++) {
                for (j = 0; j < nbEt; j++) {
                    prob[t] = ( alpha[i][t] * trans[i][j] * emi[j][index( tabObv[t + 1], alphabet )] * beta[j][t + 1] );
                }
            }
        }*/
        for (t = 0; t < tabObv.length - 1; t++) {
            for (i = 0; i < nbEt; i++) {
                for (j = 0; j < nbEt; j++) {
                    ksi[t][i][j] = ( alpha[i][t] * trans[i][j] * emi[j][index( tabObv[t + 1], alphabet )] * beta[j][t + 1] ) / forward( nbEt, alphabet, tabObv, init, emi, trans );//prob[t];
                }
            }
        }
        /*for(t=0;t<tabObv.length-1;t++){
            for(i=0;i<nbEt;i++){
                for(j=0;j<nbEt;j++){
                    ksi[t][i][j]/=temp1;
                }
            }
        }*/
        return ksi;
    }

    public double[][] alpha( int nbEt, String[] alphabet, String[] tabObv, double[] init, double[][] emi, double[][] trans )
    {
//        int i, j, t;
//        double temp, temp1;
//        double[][] alpha = new double[nbEt][tabObv.length];
//        for (i = 0; i < nbEt; i++) {
//            alpha[i][0] = init[i] * emi[i][index( tabObv[0], alphabet )];
//            //System.out.println(alpha[0][i]);
//        }
//        /*t=1;
//        while(t<tabObv.length){
//            for(i=0;i<nbEt;i++){
//                temp=0.0;
//                for(j=0;j<nbEt;j++){
//                    temp += alpha[i][t-1]*trans[i][j];
//                    //System.out.println(alpha[t-1][j]+"et"+trans[j][i]);
//                }
//                temp1=0;
//                for(j=0;j<nbEt;j++){
//                    temp1= emi[j][index(tabObv[t],alphabet)];
//                    //System.out.println(alpha[t-1][j]+"et"+trans[j][i]);
//                }
//                alpha[i][t] = temp*temp1;//emi[i][index(tabObv[t],alphabet)];
//            }
//            t++;
//        }*/
//        for (i = 1; i < tabObv.length; i++) {
//            for (int k = 0; k < nbEt; k++) {
//                double sum = 0;
//                for (int l = 0; l < nbEt; l++) {
//                    sum += alpha[l][i - 1] * trans[l][k];
//                }
//                alpha[k][i] = sum * emi[k][index( tabObv[i], alphabet )];
//            }
//        }
        int i, j, t;
        double temp, prob = 0.0;
        double[][] alpha = new double[nbEt][tabObv.length];
        for (i = 0; i < nbEt; i++) {
            alpha[i][0] = init[i] * emi[i][index( tabObv[0], alphabet )];
            //System.out.println( "alpha[t,0] " + alpha[i][0] );
        }

        t = 0;
        while (t < tabObv.length - 1) {
            j = 0;

            while (j < nbEt) {
                temp = 0.0;
                for (i = 0; i < nbEt; i++) {
                    //System.out.println( "alpha " + alpha[i][t] );
                    //System.out.println( "trans " + trans[i][j] );
                    //System.out.println( "t " + ( t + 1 ) + ", i " + ( i + 1 ) + ", j " + ( j + 1 ) );
                    temp += alpha[i][t] * trans[i][j];
                }
                //System.out.println( "emi " + emi[j][index( tabObv[t + 1], alphabet )] );
                //System.out.println( "temp " + temp );
                alpha[j][t + 1] = temp * emi[j][index( tabObv[t + 1], alphabet )];
                //System.out.println( "alpha [" + j + "," + ( t + 1 ) + "]" + ( alpha[j][t + 1] ) );
                j++;
            }
            t++;
        }
//        for (i = 0; i < nbEt; i++) {
//            //System.out.println( "prob " + prob );
//            prob += alpha[i][tabObv.length - 1];
//        }

        return alpha;
    }

    public double[][] beta( int nbEt, String[] alphabet, String[] tabObv, double[] init, double[][] emi, double[][] trans )
    {
//        int i, j, t;
//        double[][] beta = new double[nbEt][tabObv.length];
//        for (i = 0; i < nbEt; i++) {
//            beta[i][tabObv.length - 1] = 1.0;//emi[i][index(tabObv[tabObv.length-1],alphabet)];
//        }
//        /*t = tabObv.length - 2;
//        while(t>=0){
//            for(i=0;i<nbEt;i++){
//                beta[i][t] = 0;
//                for(j=0;j<nbEt;j++){
//                    beta[i][t] += trans[i][j] * emi[j][index(tabObv[t+1],alphabet)] * beta[j][t+1];
//
//                }
//                // beta[t][i] = temp;
//
//                ///System.out.println("beta"+beta[t][i]);
//            }
//            t--;
//        }*/
//        int T = tabObv.length;
//        for (t = T - 2; t >= 0; t--) {
//            for (i = 0; i < nbEt; i++) {
//                beta[i][t] = 0;
//                for (j = 0; j < nbEt; j++) {
//                    beta[i][t] += ( beta[j][t + 1] * trans[i][j] * emi[j][index( tabObv[t + 1], alphabet )] );
//                }
//            }
//        }

        int i, j, t;
        double prob = 0.0;
        double[][] beta = new double[nbEt][tabObv.length];
        for (i = 0; i < nbEt; i++) {
            beta[i][tabObv.length - 1] = 1.0;//emi[i][index(tabObv[tabObv.length-1],alphabet)];
        }
        t = tabObv.length - 2;
        while (t >= 0) {
            i = 0;
            while (i < nbEt) {
                //System.out.println( "i " + i + " t " + t );
                beta[i][t] = 0;
                for (j = 0; j < nbEt; j++) {
                    //System.out.println( "i " + i + " t " + t + " j " + j );
                    beta[i][t] += trans[i][j] * emi[j][index( tabObv[t + 1], alphabet )] * beta[j][t + 1];
                }
                i++;
            }
            t--;
        }

        return beta;
    }

    public double[][] gamma( int nbEt, String[] alphabet, String[] tabObv, double[] init, double[][] emi, double[][] trans )
    {
        int i, t;
        /*double temp;
        double[][] gamma = new double[tabObv.length][nbEt];
        double[][][] ksi = ksi(nbEt,tabObv,init,emi,trans);
        for(t=0;t<tabObv.length-1;t++){
            for(i=0;i<nbEt;i++){
                temp = 0.0;
                for(j=0;j<nbEt;j++){
                    temp += ksi[t][i][j];
                }
                gamma[t][i] = temp;
            }
        }*/
        double[][] alpha = alpha( nbEt, alphabet, tabObv, init, emi, trans );
        double[][] beta = beta( nbEt, alphabet, tabObv, init, emi, trans );
        double[][][] ksi = ksi( nbEt, alphabet, tabObv, init, emi, trans );
        double[][] gamma = new double[tabObv.length][nbEt];
        double[] prob = new double[tabObv.length];

        /*
        for (t = 0; t < tabObv.length - 1; t++) {
            for (i = 0; i < nbEt; i++) {
                prob[t] += alpha[i][t] * beta[i][t];
            }
        }*/
        for (t = 0; t < tabObv.length - 1; t++) {
            for (i = 0; i < nbEt; i++) {
                gamma[t][i] = 0;
                for (int j = 0; j < nbEt; j++) {
                    gamma[t][i] += ksi[t][i][j];
                    //gamma[t][i] = ( alpha[i][t] * beta[i][t] ) / forward( nbEt, alphabet, tabObv, init, emi, trans );
                }
                //gamma[t][i] = ( alpha[i][t] * beta[i][t] ) / forward( nbEt, alphabet, tabObv, init, emi, trans );//prob[t];
            }
        }
        //System.out.println("test"+forward(nbEt,tabObv,init,emi,trans)+"   "+backward(nbEt,tabObv,init,emi,trans)+"fin");
        return gamma;
    }

    public double[] rInit( int nbEt, String[] alphabet, String[] tabObv, double[] init, double[][] emi, double[][] trans )
    {
        double[] pi = new double[nbEt];
        //double sum = 0.0;
        int i, j;
        double[][] gamma = gamma( nbEt, alphabet, tabObv, init, emi, trans );
        for (i = 0; i < nbEt; i++) {
            //System.out.println( "gamma[0][i] " + gamma[0][i] );
            pi[i] = ( gamma[0][i] );

        }
        //System.out.println(sum);
        //affichetab(pi);
        return pi;
    }

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
