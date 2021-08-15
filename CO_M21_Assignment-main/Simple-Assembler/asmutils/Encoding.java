package asmutils;

import java.util.ArrayList;

public class Encoding {

//    public static class A_type extends Encoding{
//
//        A_type(String opcode, String reg1, String reg2, String reg3 ){
//            this.name= "A";
//            this.opcode= opcode;
//            this.unused="00";
//            this.reg1=reg1;
//            this.reg2= reg2;
//            this.reg3=reg3;
//        }
//        public String output(){
//            return opcode+unused+reg1+reg2+reg3;
//        }
//    }
//    public static class B_type extends Encoding{
//        String ImmedateValue;
//        B_type(String opcode, String reg1, String ImmedateValue){
//            this.name= "B";
//            this.opcode= opcode;
//            this.reg1=reg1;
//            this.ImmedateValue=ImmedateValue;
//        }
//
//
//
//        public String output(){
//            return opcode+reg1+ImmedateValue;
//        }
//    }
//    public static class C_type extends Encoding{
//
//        C_type(String opcode, String reg1, String reg2){
//            this.name= "C";
//            this.opcode= opcode;
//            this.unused="00000";
//            this.reg1=reg1;
//            this.reg2= reg2;
//        }
//        public String output(){
//            return opcode+unused+reg1+reg2;
//        }
//    }
//
//    public static class D_type extends Encoding{
//        String MemoryAddress;
//
//        D_type(String opcode, String reg1, String MemoryAddress){
//            this.name= "D";
//            this.opcode= opcode;
//            this.reg1=reg1;
//            this.MemoryAddress=MemoryAddress;
//        }
//        public String output(){
//            return opcode+reg1+MemoryAddress;
//        }
//    }
//    public static class E_type extends Encoding{
//        String MemoryAddress;
//
//        E_type(String opcode, String MemoryAddress){
//            this.name= "E";
//            this.opcode= opcode;
//            this.unused="000";
//            this.MemoryAddress=MemoryAddress;
//        }
//        public String output(){
//            return opcode+unused+MemoryAddress;
//        }
//    }
//    public static class F_type extends Encoding{
//
//        F_type(String opcode){
//            this.name= "F";
//            this.opcode= opcode;
//            this.unused= "00000000000";
//        }
//        public String output(){
//            return opcode+unused;
//        }
//    }
//
//

    public static boolean islabel(String s) {
        return s.contains(":") && s.lastIndexOf(":")==s.length()-1;
    }
    public static boolean isvariable(String s){
        return s.equals("var");
    }
    public static String inttobinary(String s) throws Error.MisMatchException {
        int value=Integer.parseInt(s);
        if(0>value && value>256) throw new Error.MisMatchException("Illegal Immediate values (less than 0 or more than 255)");
        return String.format("%8s",Integer.toBinaryString(value)).replace(' ','0');
    }

    public static String normalinstruction(ArrayList<String> sub) throws Error.InvalidParametersException, Error.InvalidRegisterException, Error.InvalidImmediateException, Error.InvalidVariableNameException, Error.MisMatchException {
        int para = sub.size() - 1;
        Isa.description values = Isa.getdetails(sub.get(0));
        if (para != values.parameters) {
            throw new Error.InvalidParametersException("Expected parameters : "+ values.parameters+", Recieved parameters : "+para);
        }
         return asm.findfunction(sub);
    }


    public static void main(String[] args){

    }
}


