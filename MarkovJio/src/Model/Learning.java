/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.AlpEti;
import Controller.Reading;
import Controller.Writing;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author johan
 */
public final class Learning
{

    private Map<Integer, AlpEti> trainFile = new HashMap();
    private String topicName;
    private byte nbEt;
    //public float[] initProbaFile;
    //private float[][] emiProbaFile;
    //private float[][] transProbaFile;
    private List<String> alphabetFile = new ArrayList();
    private int[] nbInitFile = new int[4];
    private int[][] nbTransFile = new int[4][4];
    private final String CHEMIN_ALPETI = "AlpEti.txt";
    private final String CHEMIN_ALPHABET = "Alphabet.txt";
    private final String CHEMIN_INIT = "nbInit.txt";
    private final String CHEMIN_TRANS = "nbTrans.txt";
    private final String CHEMIN_EMI_PROBA = "ProbaEmi.txt";
    private final String CHEMIN_INIT_PROBA = "ProbaInit.txt";
    private final String CHEMIN_TRANS_PROBA = "ProbaTrans.txt";

    //Sujet d'étude par défaut
    /*public Learning() throws FileNotFoundException
    {
        Reading rd = new Reading();
        this.topicName = "";
        this.trainFile = rd.readTrainFile( "Files/AlpEti.txt" );
        //this.initProbaFile = rd.readInit( 4, "Files/ProbaInit.txt" );
        //this.emiProbaFile = rd.readEmi( 4, "Files/ProbaEmi.txt" );
        //this.transProbaFile = rd.readTrans( 4, "Files/ProbaInit.txt" );
        this.alphabetFile = rd.readFile( "Files/Alphabet.txt" );
        this.nbInitFile = rd.readNbInit( 4, "Files/nbInit.txt" );
        this.nbTransFile = rd.readNbTrans( 4, "Files/nbTrans.txt" );
    }*/
    public String getTopicName()
    {
        return topicName;
    }

    public void setTopicName( String name )
    {
        this.topicName = name;
    }

    public void setNbEt( byte nbEt )
    {
        this.nbEt = nbEt;
    }

    public byte getNbEt()
    {
        return this.nbEt;
    }

    public Learning( String name, byte nbEt, List<String> newWords, Map<Integer, AlpEti> trainFile ) throws FileNotFoundException, IOException
    {
        this.nbEt = nbEt;
        this.topicName = name;
        if (isCreated( name )) {
            load( name );
        } else {
            createLearningFolder( name );
            initialiseFiles();
            save( name, newWords, trainFile );
//          Why must we load files here? They are empty.
            load( name );
        }
    }

    public void load( String name ) throws FileNotFoundException
    {
        Reading rd = new Reading( nbEt );
        this.trainFile = rd.readTrainFile( "Files/" + name + "/" + CHEMIN_ALPETI );
        //System.out.println( "TrainFile : " + this.trainFile );
        this.alphabetFile = unique( rd.readFile( "Files/" + name + "/" + CHEMIN_ALPHABET ) );
        System.out.println( "AlphabetFile : " + alphabetFile );
        this.nbInitFile = rd.readNbInit( "Files/" + name + "/" + CHEMIN_INIT );
        this.nbTransFile = rd.readNbTrans( "Files/" + name + "/" + CHEMIN_TRANS );
    }

    /*public void save() throws IOException
    {
        Writing wr = new Writing();
        wr.WriteAlpFile( alphabetFile, "Files/Alphabet.txt" );
        wr.WriteNbInit( nbInitFile, "Files/nbInit.txt" );
        wr.WriteNbTrans( nbTransFile, "Files/nbTrans.txt" );
        wr.WriteTrainFile( trainFile, "Files/AlpEti.txt" );
    }*/
    public final void save( String name, List<String> newWords, Map<Integer, AlpEti> trainFile ) throws IOException //Passer "" en parametre  pour savegarder dans le même repertoire
    {
        Writing wr = new Writing();
        wr.WriteAlpFile( newWords, "Files/" + name + "/" + CHEMIN_ALPHABET, alphabetFile );
        wr.WriteNbInit( nbInitFile, "Files/" + name + "/" + CHEMIN_INIT );
        wr.WriteNbTrans( nbTransFile, "Files/" + name + "/" + CHEMIN_TRANS );
        wr.WriteTrainFile( trainFile, "Files/" + name + "/" + CHEMIN_ALPETI );
    }

    public final void saveProba( String name ) throws IOException //Passer "" en parametre  pour savegarder dans le même repertoire
    {
        Writing wr = new Writing();
        wr.WriteEmi( emi(), "Files/" + name + "/" + CHEMIN_EMI_PROBA );
        wr.WriteInit( init(), "Files/" + name + "/" + CHEMIN_INIT_PROBA );
        wr.WriteTrans( trans(), "Files/" + name + "/" + CHEMIN_TRANS_PROBA );
    }

    public void updateTopicName( String name )
    {

        moveFile( "Files/" + topicName + "/" + CHEMIN_ALPHABET, name );
        moveFile( "Files/" + topicName + "/" + CHEMIN_INIT, name );
        moveFile( "Files/" + topicName + "/" + CHEMIN_TRANS, name );
        moveFile( "Files/" + topicName + "/" + CHEMIN_ALPETI, name );
        this.topicName = name;
    }

    public void moveFile( String filename, String name )
    {
        File file = new File( filename );
        File dir = new File( "Files/" + name );
        boolean success
                = file.renameTo( new File( dir, file.getName() ) );
        if (!success) {
            System.out.println( "une érreur est sur venu lors de la création du fichier :\n"
                    + "\nFiles/" + name + "/" + filename );
        }
    }

    private boolean createLearningFolder( String name ) throws IOException
    {
        boolean exists = ( new File( "Files/" + name ) ).exists();
        if (!exists) {
            boolean success = ( new File( "Files/" + name ) ).mkdir();
            if (success) {
                System.out.println( "Le sujet " + name + " a eté crée avec succès" );
                File f1 = new File( "Files/" + name + "/" + CHEMIN_ALPHABET );
                File f2 = new File( "Files/" + name + "/" + CHEMIN_INIT );
                File f3 = new File( "Files/" + name + "/" + CHEMIN_TRANS );
                File f4 = new File( "Files/" + name + "/" + CHEMIN_ALPETI );
                File f5 = new File( "Files/" + name + "/" + CHEMIN_EMI_PROBA );
                File f6 = new File( "Files/" + name + "/" + CHEMIN_INIT_PROBA );
                File f7 = new File( "Files/" + name + "/" + CHEMIN_TRANS_PROBA );
                new File( "Files/" + name + "/Train" ).mkdir();
                f1.createNewFile();
                f2.createNewFile();
                f3.createNewFile();
                f4.createNewFile();
                f5.createNewFile();
                f6.createNewFile();
                f7.createNewFile();
            } else {
                System.out.println( "Une érreur est survenue lors de la création du sujet " + name );
            }
        } else {
            System.out.println( "Le sujet d'étude " + name + "existe déja. " );
        }
        return exists;
    }

    private boolean isCreated( String name )
    {
        //System.out.println( "isCreated : " + ( new File( "Files/" + name ) ).exists() );
        return ( new File( "Files/" + name ) ).exists();
    }

    public float[] init()
    {
        float[] initProb = new float[nbEt];
        int nbDocs = 0;
        for (int i = 0; i < nbEt; i++) {
            nbDocs += nbInitFile[i];
        }
        for (int i = 0; i < nbEt; i++) {
            initProb[i] = nbInitFile[i] / nbDocs;
        }
        return initProb;
    }

    public int sum( int[] tab )
    {
        int sum = 0;
        for (int i = 0; i < tab.length; i++) {
            sum += tab[i];
        }
        return sum;
    }

    public float[][] trans()
    {
        int total;
        float[][] trans = new float[nbEt][nbEt];
        for (int i = 0; i < trans.length; i++) {
            total = sum( nbTransFile[i] );
            //System.out.println( "total : " + total );
            for (int j = 0; j < trans[i].length; j++) {
                if (total == 0) {
                    //System.out.println( " ici " );
                    trans[i][j] = 0;
                } else {

//                    System.out.println( "nbTrans : " + nbTransFile[i][j] );
//                    System.out.println( "total : " + total );
                    trans[i][j] = (float) nbTransFile[i][j] / total;
//                    System.out.println( "trans[i][j]: " + trans[i][j] );
                }

            }
        }
        return trans;
    }

    public void printTab( float[][] tab )
    {
        for (float[] tab1 : tab) {
            for (int j = 0; j < tab1.length; j++) {
                System.out.println( tab1[j] );
            }
            System.out.println();
        }
    }

    public float lissage( float nb, int total )
    {
        float rt = 0;
        if (nb != 0) {
            rt = nb / total;
        }
        return rt;
    }

    public float selonQue( byte i, int elemTF, int[] total )
    {
        float var;
        switch (i) {
            case 0:
                //System.out.println( "trainPre : " + trainFile.get( elemTF ).getNbPre().toString() );
                var = lissage( Float.valueOf( trainFile.get( elemTF ).getNbPre().toString() ), total[i] );
                break;
            case 1:
                //System.out.println( "trainTar : " + trainFile.get( elemTF ).getNbTar().toString() );
                var = lissage( Float.valueOf( trainFile.get( elemTF ).getNbTar().toString() ), total[i] );
                break;
            case 2:
                //System.out.println( "trainPos : " + trainFile.get( elemTF ).getNbPos().toString() );
                var = lissage( Float.valueOf( trainFile.get( elemTF ).getNbPos().toString() ), total[i] );
                break;
            default:
                //System.out.println( "trainOth : " + trainFile.get( elemTF ).getNbOth().toString() );
                var = lissage( Float.valueOf( trainFile.get( elemTF ).getNbOth().toString() ), total[i] );
                break;
        }
        return var;
    }

    public float[][] emi()
    {
        float[][] prob = new float[nbEt][trainFile.size()];
        int[] total = new int[nbEt];
        for (int i = 0; i < trainFile.size(); i++) {
            total[0] += Integer.valueOf( trainFile.get( i ).getNbPre().toString() );
            total[1] += Integer.valueOf( trainFile.get( i ).getNbTar().toString() );
            total[2] += Integer.valueOf( trainFile.get( i ).getNbPos().toString() );
            total[3] += Integer.valueOf( trainFile.get( i ).getNbOth().toString() );
        }
        for (byte i = 0; i < nbEt; i++) {
            for (int j = 0; j < trainFile.size(); j++) {
                //System.out.println( "selonQue = " + selonQue( i, j, total ) );
                prob[i][j] = selonQue( i, j, total );

            }

        }
        return prob;
    }

    //Ecrire la fonction qui vérifie la correspondance entre le fichier source le fichier d'étiquetage
    /*A corriger*/
    public boolean verifyAlpEtiFile( Map<Integer, AlpEti> AlpEti )
    {
        boolean isOk = true;
        try {
            /*for (int i = 0; i < AlpEti.size(); i++)
            {
                System.out.println( "Test len : \n" + AlpEti.size() );
                System.out.println( "index :" + i );
                System.out.println( "AlEti \n:" + AlpEti + " AlpEti(i) : \n" + AlpEti.get( i ) );
                System.out.println( Integer.valueOf( AlpEti.get( i ).getNbPre().toString() ) );
                int var2 = Integer.valueOf( AlpEti.get( i ).getNbTar().toString() );
                int var3 = Integer.valueOf( AlpEti.get( i ).getNbPos().toString() );
                int var4 = Integer.valueOf( AlpEti.get( i ).getNbOth().toString() );
            }*/
        } catch (NumberFormatException e) {
            isOk = false;
        }
        return isOk;
    }

    public boolean search( String str, List<String> tab )
    {
        int j = 0;
        boolean app = false;
        //System.out.println( "tab size : " + tab.size() );
        while (j < tab.size() && !app) {
            if (str.equals( tab.get( j ) )) {
                app = true;
            } else {
                j++;
            }
        }
        return app;
    }

    public List<String> unique( List<String> tab )
    {
        List<String> mp = new ArrayList();
        //System.out.println( "tab.size() :" + tab.size() );
        int j = 0;
        while (j < tab.size()) {
            if (!mp.contains( tab.get( j ) )) {
                mp.add( tab.get( j ) );
                //System.out.println( "mp" + mp );
            }
            j++;
        }
        return mp;
    }

    public int indexUnique( String str, List<String> mp )
    {
        for (int i = 0; i < mp.size(); i++) {
            if (mp.get( i ).equals( str )) {
                return i;
            }
        }
        return -1;
    }

    public List<Integer> allIndex( String str, List<String> tab )
    {
        List<Integer> present = new ArrayList();
        for (int j = 0; j < tab.size(); j++) {
            if (str.equals( tab.get( j ) )) {
                present.add( j );
            }
        }
        return present;
    }

    public int index( String str, List<String> tab )
    {
        int j = 0, index = -1;
        boolean app = false;
        while (j < tab.size() && !app) {
            if (str.equals( tab.get( j ) )) {
                index = j;
                app = true;
            } else {
                j++;
            }
        }
        return index;
    }

    //P-Fonction qui  actualise le tableau d'initialisation à partir du fichier source et du fichier d'étiquetage
    public void curInit( Map<Integer, AlpEti> fichEtiCour )
    {
        if (verifyAlpEtiFile( fichEtiCour )) {
//            System.out.println( fichEtiCour.keySet() );
            AlpEti ae = fichEtiCour.get( 1 );
            //System.out.println( ( 1 + Integer.valueOf( ae.getNbPre().toString() ) ) );
            //System.out.println( nbInitFile.length );
            nbInitFile[0] += Integer.valueOf( ae.getNbPre().toString() );
            nbInitFile[1] += Integer.valueOf( ae.getNbTar().toString() );
            nbInitFile[2] += Integer.valueOf( ae.getNbPos().toString() );
            nbInitFile[3] += Integer.valueOf( ae.getNbOth().toString() );
        } else {
            System.out.println( "Votre fichier d'étiquetage est érroné" );
        }
    }

    /*public int selonQue( byte j, int i, Map<Integer, AlpEti> fichEtiCour )
    {
        int var;
        switch (j)
        {
            case 0:
                var = Integer.valueOf( inSelonQue( i, fichEtiCour ) ) + Integer.valueOf( inSelonQue( i + 1, fichEtiCour ) );
                for (int k = 0; k < 4; k++)
                {
                    int var1 = Integer.valueOf( inSelonQue( i, fichEtiCour ) ) + Integer.valueOf( inSelonQue( i + 1, fichEtiCour ) );

                }
                break;
            case 1:
                var = Integer.valueOf( inSelonQue( i, fichEtiCour ) );
                break;
            case 2:
                var = Integer.valueOf( inSelonQue( i, fichEtiCour ) );
                break;
            default:
                var = Integer.valueOf( inSelonQue( i, fichEtiCour ) );
                break;
        }
        return var;
    }*/
    public int selonQue( int i, int j, Map<Integer, AlpEti> fichEtiCour )
    {
        int var;
        switch (i) {
            case 0:
                var = Integer.valueOf( fichEtiCour.get( j ).getNbPre().toString() );
                //System.out.println( "var0 : " + var );
                break;
            case 1:
                var = Integer.valueOf( fichEtiCour.get( j ).getNbTar().toString() );
                //System.out.println( "var1 : " + var );
                break;
            case 2:
                var = Integer.valueOf( fichEtiCour.get( j ).getNbPos().toString() );
                //System.out.println( "var2 : " + var );
                break;
            default:
                var = Integer.valueOf( fichEtiCour.get( j ).getNbOth().toString() );
                //System.out.println( "var3 : " + var );
                break;
        }
        return var;
    }

    public void curTrans( Map<Integer, AlpEti> fichEtiCour )
    {
        if (verifyAlpEtiFile( fichEtiCour )) {
            for (int k = 0; k < fichEtiCour.size() - 1; k++) {
                for (int i = 0; i < nbTransFile.length; i++) {
                    for (int j = 0; j < nbTransFile[i].length; j++) {
                        //System.out.println( "index : " + k );
                        int var = selonQue( i, k, fichEtiCour );
                        int var1 = selonQue( j, k + 1, fichEtiCour );
                        if (( var != 0 ) && ( var1 != 0 )) {
                            //System.out.println( "var :" + var + " ,var1 :" + var1 );
                            nbTransFile[i][j] += 1;

                        }
                        //System.out.println( "nbTransFile[" + i + "][" + j + "] : " + nbTransFile[i][j] );
                    }
                }
            }
        } else {
            System.out.println( "Votre fichier d'étiquetage est érroné" );
        }

    }

    //Il est obligatiore de ne traiter que les fichiers étiquetés sous le format {0,1} {0,1} {0,1} {0,1}  où {0,1} est l'ensemble
    //dans lequel nous devons choisir nos éléments ex : 1 0 0 0
    //Le fichier résultant ne nous sert qu'à calculer les probabilités d'émission
    public void curEmi( List<String> fich, Map<Integer, AlpEti> fichEtiCour )
    {
        if (verifyAlpEtiFile( fichEtiCour )) {

            List<String> unique = unique( fich );
            int i = 0, nbUsed = 0;
            System.out.println( "alphabet : " + alphabetFile );

            while (i < fich.size()) {
                int temp = indexUnique( fich.get( i ), unique );
                if (alphabetFile.contains( fich.get( i ) )) {
//                    System.out.println( i + "1 : " + fichEtiCour.get( i ) );
                    nbUsed += 1;
                    AlpEti ae = trainFile.get( index( fich.get( i ), alphabetFile ) );
                    AlpEti ei = fichEtiCour.get( temp );
//                    System.out.println( "trainFile : " + trainFile );
                    ae.setAll( Integer.valueOf( ae.getNbPre().toString() ) + Integer.valueOf( ei.getNbPre().toString() ),
                            Integer.valueOf( ae.getNbTar().toString() ) + Integer.valueOf( ei.getNbTar().toString() ),
                            Integer.valueOf( ae.getNbPos().toString() ) + Integer.valueOf( ei.getNbPos().toString() ),
                            Integer.valueOf( ae.getNbOth().toString() ) + Integer.valueOf( ei.getNbOth().toString() ) );

                } else {
                    int var = indexUnique( fich.get( i ), unique ) + nbUsed;
                    alphabetFile.add( fich.get( i ) );
                    System.out.println( "AlphabetFile : " + alphabetFile );
//                    System.out.println( "i : " + i );
                    AlpEti ae = new AlpEti();
                    ae.setAll( fichEtiCour.get( var ).getNbPre(), fichEtiCour.get( var ).getNbTar(),
                            fichEtiCour.get( var ).getNbPos(), fichEtiCour.get( var ).getNbOth() );
                    trainFile.put( trainFile.size(), ae );
//                    System.out.println( "var : " + var );
//                    System.out.println( " trainFile(i) " + ae );
                }
                i++;
            }
        } else {
            System.out.println( "Votre fichier d'étiquetage est érroné" );
        }
        System.out.println( "\n Fin : trainFile :" + trainFile );
    }

    private Learning getLearning() throws FileNotFoundException, IOException
    {
        return this;
    }

    private void initialiseFiles()
    {
        for (int i = 0; i < nbInitFile.length; i++) {
            nbInitFile[i] = 0;
        }
        for (int[] nbTransFile1 : nbTransFile) {
            for (int j = 0; j < nbTransFile1.length; j++) {
                nbTransFile1[j] = 0;
            }
        }
    }
}
