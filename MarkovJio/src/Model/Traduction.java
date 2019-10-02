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
public class Traduction {
    public int sameSubstring(String str1, String str2, int index_str1, int index_str2){
        if(index_str1>=str1.length()){
            //return "";
            return 0;
        }
        if(index_str2>=str2.length()){
            return sameSubstring(str1, str2, ++index_str1, 0);
        }
        else{
            if(str1.charAt(index_str1) == str2.charAt(index_str2)){
                //return str1.charAt(index_str1) + sameSubstring(str1, str2, ++index_str1, ++index_str2);
                return 1 + sameSubstring(str1, str2, ++index_str1, ++index_str2);
            }
            else{
                return sameSubstring(str1, str2, index_str1, ++index_str2);
            }
        }
    }
    public int[] tabSubstring(String chaine,String[] tab){
        int i;
        int[] end = new int[tab.length];
        for(i=0;i<tab.length;i++){
            end[i] = sameSubstring(chaine,tab[i],0,0);
        }
        return end;
    }
}
