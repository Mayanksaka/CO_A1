package asmutils;

import java.util.HashMap;

public class Isa {

    static class description{
        String opcode=null;
//        Encoding type=null;
        int parameters=0;
        description(String opcode, int parameters){
            this.opcode=opcode;
//            this.type=type;
            this.parameters=parameters;
        }
    }
    public static HashMap<String,description> functions = new HashMap<>(){{
        put("add", new description("00000", 3));
        put("sub", new description("00001", 3));
        put("moi", new description("00010", 2));
        put("mov", new description("00011", 2));
        put("ld",  new description("00100", 2));
        put("st",  new description("00101", 2));
        put("mul", new description("00110", 3));
        put("div", new description("00111", 2));
        put("rs",  new description("01000", 2));
        put("ls",  new description("01001", 2));
        put("xor", new description("01010", 3));
        put("or",  new description("01011", 3));
        put("and", new description("01100", 3));
        put("not", new description("01101", 2));
        put("cmp", new description("01110", 2));
        put("jmp", new description("01111", 1));
        put("jlt", new description("10000", 1));
        put("jgt", new description("10001", 1));
        put("je",  new description("10010", 1));
        put("hlt", new description("10011", 0));
    }};

    public static description getdetails(String name){
        try{
            description result =functions.get(name);
            return result;
        }catch(Exception e){
            System.out.println("Not found");
            return null;
        }
    }

}
