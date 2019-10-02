/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

/**
 *
 * @author johan
 * @param <Pr>
 * @param <T>
 * @param <Ps>
 * @param <O>
 */
public class AlpEti<Pr, T, Ps, O>
{

    //private static int nb;
    //private int index;
    private Pr nbPre;
    private T nbTar;
    private Ps nbPos;
    private O nbOth;

    public AlpEti()
    {
        //this.index = ++nb;
        this.nbPre = null;
        this.nbTar = null;
        this.nbPos = null;
        this.nbOth = null;
    }

    public AlpEti( Pr nbPre, T nbTar, Ps nbPos, O nbOth )
    {
        //this.index = ++nb;
        this.nbPre = nbPre;
        this.nbTar = nbTar;
        this.nbPos = nbPos;
        this.nbOth = nbOth;
    }

    public Pr getNbPre()
    {
        return nbPre;
    }

    public T getNbTar()
    {
        return nbTar;
    }

    public Ps getNbPos()
    {
        return nbPos;
    }

    public O getNbOth()
    {
        return nbOth;
    }

    public void setNbPre( Pr nbPre )
    {
        this.nbPre = nbPre;
    }

    public void setNbTar( T nbTar )
    {
        this.nbTar = nbTar;
    }

    public void setNbPos( Ps nbPos )
    {
        this.nbPos = nbPos;
    }

    public void setNbOth( O nbOth )
    {
        this.nbOth = nbOth;
    }

    public void setAll( Pr nbPre, T nbTar, Ps nbPos, O nbOth )
    {
        this.nbPre = nbPre;
        this.nbTar = nbTar;
        this.nbPos = nbPos;
        this.nbOth = nbOth;
    }

    @Override
    public String toString()
    {
        return "" + nbPre + " " + nbTar + " " + nbPos + " " + nbOth + "";
    }

}
