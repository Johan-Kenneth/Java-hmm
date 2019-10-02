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
public class HMM
{

    /*The number of states*/
    protected byte numStates;
    /*The number of differants observations(alphabet length)*/
    protected int numObservations;
    /*The initial probabilities for each states pi[state]*/
    private double[] pi;
    /*The change probabilities to switch from state A to state B: a[stateA][stateB]*/
    private double a[][];
    /*The probabilities to emit symbol S in state A : b[stateA][symbolS]*/
    private double b[][];
    /*The number of reestimations*/
    public short numReestimation;

    /**/
    /**
     * Initialize the HMM
     *
     * @param numStates
     * @param numObservations
     */
    public HMM( byte numStates, int numObservations )
    {
        this.numReestimation = 0;
        this.numStates = numStates;
        this.numObservations = numObservations;
        pi = new double[numStates];
        a = new double[numStates][numStates];
        b = new double[numStates][numObservations];
        this.reset();
    }

    /*Reset de HMM*/
    private void reset()
    {
        int i, j;
        //set startup probability
        for (i = 0; i < numStates; i++) {
            pi[i] = 0;
        }
        //set change state probability
        for (i = 0; i < numStates; i++) {
            for (j = 0; j < numStates; j++) {
                a[i][j] = 0;
            }
        }
        //emission probability
        for (i = 0; i < numStates; i++) {
            for (j = 0; j < numStates; j++) {
                b[i][j] = 0;
            }
        }
    }

    public double[] getPi()
    {
        return this.pi;
    }

    public void setPi( double[] pi )
    {
        this.pi = pi;
    }

    public double[][] getA()
    {
        return this.a;
    }

    public void setA( double[][] a )
    {
        this.a = a;
    }

    public double[][] getB()
    {
        return this.b;
    }

    public void setB( double[][] b )
    {
        this.b = b;
    }

}
