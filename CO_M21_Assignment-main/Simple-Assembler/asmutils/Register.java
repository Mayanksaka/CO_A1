package asmutils;

import java.util.Map;
import java.util.regex.Pattern;

public class Register {
    public static Map<String,String> registers = Map.of("R0","000","R1","001","R2","010","R3","011","R4","100","R5","101","R6","110","FLAGS","111");
    public static String getregister(String name) throws Error.InvalidRegisterException {
            String result =registers.get(name);
            if(result==null) throw new Error.InvalidRegisterException();
            return result;

    }
    public static void main(String[]  args) throws Error.InvalidRegisterException {
        Register r = new Register();
        System.out.println(Pattern.matches("[a-zA-Z0-9_]+", "label"));
        try{
        System.out.println(r.getregister("R30"));}
        catch (Error.InvalidRegisterException e){
            System.out.println(e);
//            e.printStackTrace();
//            System.out.println("not found");
        }
    }
}
