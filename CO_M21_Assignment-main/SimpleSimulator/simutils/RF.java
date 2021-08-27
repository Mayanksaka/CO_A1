package simutils;

import java.util.HashMap;

public class RF {
    public static String flag="0000000000000000";
    public static HashMap<String,String>Register =new HashMap<>(){{
        
        put("000","0000000000000000");
        put("001","0000000000000000");
        put("010","0000000000000000");
        put("011","0000000000000000");
        put("100","0000000000000000");
        put("101","0000000000000000");
        put("110","0000000000000000");
        put("111","0000000000000000");
    }};

    public static void setflag(String type){
        
        switch (type){
            case "e": setRegister("111","0000000000000001");break;
            case "g": setRegister("111","0000000000000010");break;
            case "l": setRegister("111","0000000000000100");break;
            case "v": setRegister("111","0000000000001000");break;
            default : setRegister("111","0000000000000000");break;
        }
        flag=getregister("111");
    }
    public static void resetflag(){
        
        flag= getregister("111");
        setRegister("111","0000000000000000");
    }
    public static String getflag(){
        
        return flag;
    }

    public static String getregister(String s){
        
        return Register.get(s);
    }
    public static void setRegister(String s, String value){
        
        Register.put(s,value);
    }
    public static void main(String[] args){
        
        setflag("e");
        System.out.println(getregister("111"));
    }
}
