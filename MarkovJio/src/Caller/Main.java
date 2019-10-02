/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Caller;

import Controller.AlpEti;
import Controller.Reading;
import Model.Learning;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 *
 * @author johan
 */
public class Main
{

    /**
     * @param args the command line arguments
     *
     * @throws java.io.FileNotFoundException
     */
    public static void main( String[] args ) throws FileNotFoundException, IOException
    {
        // TODO code application logic here
        byte nbEt = 4;
        //Learning essai = essai1.useLearnTopic( "variable", nbEt );
        Reading rd = new Reading( nbEt );
        ArrayList<String> test = rd.readFile( "Files/Train/test.java" );
        Map<Integer, AlpEti> ae = rd.readTrainFile( "Files/Train/testTrain.txt" );
        Learning essai = new Learning( "Class", nbEt, test, ae );
//        Writing wr = new Writing();
//        wr.WriteTrainFile( ae, "Files/Class/AlpEti.txt" );

        System.out.println( "Train : \n" + ae );
//        int[] tab =
//        {
//            1, 2, 3
//        };
//        System.out.println( Arrays.toString( tab ) );
        essai.curInit( ae );
        essai.curTrans( ae );
        essai.curEmi( test, ae );

//        System.out.println( Arrays.toString( tab ) );
        System.out.println( "Test file : \n" + test );
        System.out.println( "Init : \n" + Arrays.toString( essai.init() ) );
        System.out.println( "Trans :" );
        essai.printTab( essai.trans() );
        System.out.println( "Emi :" );
        essai.printTab( essai.emi() );
        essai.save( "Class", test, ae );
        essai.saveProba( "Class" );
    }
}
