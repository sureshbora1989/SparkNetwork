package net.spark.myapplication.utils;

public class Utils {

    public static final String OPTIONS_ORDER []= {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};


    public static String formatString(String str){
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }


    public static String getAlphabeticIndex(Integer index){
        if(index <= OPTIONS_ORDER.length){
            return OPTIONS_ORDER[index];
        }else{
            return ""+index;
        }

    }

}
