/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author johan
 */
public class Writing
{

    //Il est important de classer les clés de 0 en montant
    public void WriteTrainFile( Map<Integer, AlpEti> fichEtiq, String path ) throws IOException
    {
        String str = "";
        for (int i = 0; i < fichEtiq.size(); i++) {
            //System.out.println( fichEtiq.size() );
            //System.out.println( fichEtiq.get( i ) );
            str += fichEtiq.get( i ).toString() + " / ";
        }

        try (FileWriter file = new FileWriter( path, false )) {
            file.write( str );
            file.close();
        }
    }

    public void WriteAlpFile( List<String> newWords, String path, List<String> alphabet ) throws IOException
    {
        try (FileWriter file = new FileWriter( path, true )) {
            for (String newWord : newWords) {
                file.write( newWord + " " );
            }
        }
    }

    public boolean lookup( String obvCour, List<String> etCour )
    {
        int j = 0;
        boolean app = false;
        //System.out.print(etCour.length);
        while (j < etCour.size() && !app) {
            if (obvCour.equals( etCour.get( j ) )) {
                app = true;

            } else {
                j++;
            }
        }
        return app;
    }

    //nbPre : le nombre de fois qu'un fichier a commencé avec un élément étiqueté
    public void WriteInit( float[] init, String path ) throws IOException
    {
        try (FileWriter file = new FileWriter( path, false )) {
            String elt = new String();
            for (float prob : init) {
                elt += prob + " ";
            }
            file.write( elt );
        }
    }

    public void WriteNbInit( int[] init, String path ) throws IOException
    {
        try (FileWriter file = new FileWriter( path, false )) {
            String elt = new String();
            for (int prob : init) {
                elt += prob + " ";
            }
            file.write( elt );
        }
    }

    public void WriteTrans( float[][] trans, String path ) throws IOException
    {
        try (FileWriter file = new FileWriter( path, false )) {
            String elt = new String();
            for (float[] tran : trans) {
                for (int j = 0; j < tran.length; j++) {
                    elt += tran[j] + " ";
                }
            }
            file.write( elt );
        }
    }

    public void WriteNbTrans( int[][] trans, String path ) throws IOException
    {
        try (FileWriter file = new FileWriter( path, false )) {
            String elt = new String();
            for (int[] tran : trans) {
                for (int j = 0; j < tran.length; j++) {
                    elt += tran[j] + " ";
                }
            }
            file.write( elt );
        }
    }

    public void WriteEmi( float[][] emi, String path ) throws IOException
    {
        try (FileWriter file = new FileWriter( path, false )) {
            String elt = emi[0].length + " ";
            for (float[] emi1 : emi) {
                for (int j = 0; j < emi1.length; j++) {
                    elt += emi1[j] + " ";
                }
            }
            file.write( elt );
        }
    }
}
