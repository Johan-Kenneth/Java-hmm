/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author johan
 */
public class Reading
{

    private byte nbEt;

    public Reading( byte nbEt )
    {
        this.nbEt = nbEt;
    }

    //Lit le fichier d'entrainement
    public Map<Integer, AlpEti> readTrainFile( String path ) throws FileNotFoundException
    {
        //fichEtiq : fichier étiqueté
        //Les fichier doit être formaté de la forme suivante : "nbPre nbTarget nbPost nbOther/..../ nbPre nbTarget nbPost nbOther /"
        AlpEti<Integer, Integer, Integer, Integer> list;

        Map<Integer, AlpEti> fichEtiq = new HashMap();

        ArrayList tab = readFile( path );
        int count = 0;
        int nbPr = 0, nbTa = 0, nbPo = 0, nbOt = 0;
        try {
            for (Object element : tab) {
                count++;
                //System.out.println( count );

//                if (count != 0)
//                {
                switch (count % 5) {
                    case 1:
                        //System.out.println( count + " count " + " element " + element );
                        nbPr = Integer.valueOf( element.toString() );
                        //System.out.println( nbPr );
                        break;
                    case 2:
                        nbTa = Integer.valueOf( element.toString() );
                        break;
                    case 3:
                        //System.out.println( count + " count" );
                        nbPo = Integer.valueOf( element.toString() );
                        break;
                    case 4:
                        //System.out.println( count + " count" );
                        nbOt = Integer.valueOf( element.toString() );
                        //System.out.println( nbOt + " nbOt" );
                        break;
                    default:
                        //System.out.println( count + " count" );
                        int key = ( count / 5 ) - 1;
                        //System.out.println( "key :" + key + " " );
                        list = new AlpEti( nbPr, nbTa, nbPo, nbOt );
                        fichEtiq.put( key, list );
                        //System.out.println( "fichEtiq :" + fichEtiq );
                        //System.out.println( count );
                        nbPr = 0;
                        nbTa = 0;
                        nbPo = 0;
                        nbOt = 0;
                        break;
                }
                //}

            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            System.out.println( "Votre fichier d'étiquettage est érroné" );
        }

        //System.out.println( fichEtiq );
        return fichEtiq;
    }

    //Lit tout type de fichier
    public ArrayList<String> readFile( String path ) throws FileNotFoundException
    {
        File file = new File( path );
        Scanner input = new Scanner( file );
        ArrayList<String> tab = new ArrayList();
        while (input.hasNext()) {
            tab.add( input.next() );
        }
        return tab;
    }

    //Lit le fichier de probabilités d'initialisation
    public float[] readInit( String path ) throws FileNotFoundException
    {
        float[] probInit = new float[nbEt];
        ArrayList tab = readFile( path );
        try {
            tab = readFile( path );
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        int count = 0;
        for (byte i = 0; i < nbEt; i++) {
            probInit[i] = Float.valueOf( tab.get( count ).toString() );
            count++;
        }
        //System.out.println( Arrays.toString( probInit ) );
        return probInit;
    }

    public int[] readNbInit( String path ) throws FileNotFoundException
    {
        int[] probInit = new int[nbEt];
        ArrayList tab = readFile( path );
        try {
            tab = readFile( path );
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        int count = 0;
        for (byte i = 0; i < nbEt; i++) {
            probInit[i] = Integer.valueOf( tab.get( count ).toString() );
            count++;
        }
        //System.out.println( Arrays.toString( probInit ) );
        return probInit;
    }

    //Lit de fichier de probabilité de transitition
    public float[][] readTrans( String path ) throws FileNotFoundException
    {
        float probTrans[][] = new float[nbEt][nbEt];
        ArrayList tab = readFile( path );
        try {
            tab = readFile( path );
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        int count = 0;
        for (byte i = 0; i < nbEt; i++) {
            for (byte j = 0; j < nbEt; j++) {
                probTrans[i][j] = Float.valueOf( tab.get( count ).toString() );
                count++;
            }
            //System.out.println( Arrays.toString( probTrans[i] ) );
        }
        //System.out.println( Arrays.toString( probTrans ) );
        return probTrans;
    }

    public int[][] readNbTrans( String path ) throws FileNotFoundException
    {
        int probTrans[][] = new int[nbEt][nbEt];
        ArrayList tab = readFile( path );
        try {
            tab = readFile( path );
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        System.out.println( "tab : " + tab );
        int count = 0;
        for (byte i = 0; i < nbEt; i++) {
            for (byte j = 0; j < nbEt; j++) {
                probTrans[i][j] = Integer.valueOf( tab.get( count ).toString() );
                count++;
            }
            //System.out.println( Arrays.toString( probTrans[i] ) );
        }
        //System.out.println( Arrays.toString( probTrans ) );
        return probTrans;
    }

    //Lit le fichier de probanilité d'émission
    public float[][] readEmi( String path ) throws FileNotFoundException
    {
        ArrayList tab = readFile( path );
        try {
            tab = readFile( path );
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        int taille = Integer.valueOf( tab.get( 0 ).toString() );
        float probEmi[][] = new float[nbEt][taille];
        int count = 1;
        for (byte i = 0; i < nbEt; i++) {
            for (byte j = 0; j < taille; j++) {
                probEmi[i][j] = Float.valueOf( tab.get( count ).toString() );
                count++;
            }
            System.out.println( Arrays.toString( probEmi[i] ) );
        }
        return probEmi;
    }

}
