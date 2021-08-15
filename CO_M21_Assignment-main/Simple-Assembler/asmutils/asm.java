package asmutils;

import java.util.ArrayList;

public class asm {
    public static boolean isimmideate(ArrayList<String> input){
        int last=input.size()-1;
        return input.get(last).contains("$");
    }

    public static String A(ArrayList<String> s) throws Error.InvalidRegisterException {
        Isa.description d = Isa.getdetails(s.get(0));
        String reg1=s.get(1);
        String reg2=s.get(2);
        String reg3=s.get(3);
        String opcode = d.opcode;
        String unused = "00";

        reg1 = Register.getregister(reg1);
        reg2 = Register.getregister(reg2);
        reg3 = Register.getregister(reg3);
        return opcode+unused+reg1+reg2+reg3;
    }
    public static String B(ArrayList<String> s) throws Error.InvalidRegisterException, Error.InvalidImmediateException, Error.MisMatchException {
        Isa.description d;
        if(s.get(0).equals("mov")) d = Isa.getdetails("moi");
        else d = Isa.getdetails(s.get(0));

        String reg1=s.get(1);
        String imm=s.get(2);
        String opcode = d.opcode;
        if(imm.contains("$") && (imm.lastIndexOf("$")==0)) {
            imm= Encoding.inttobinary(imm.substring(1));
        }
        else{
            throw new Error.InvalidImmediateException();

        }
        reg1 = Register.getregister(reg1);
        return opcode+reg1+imm;
    }
    public static String C(ArrayList<String> s) throws Error.InvalidRegisterException {
        Isa.description d = Isa.getdetails(s.get(0));
        String reg3=s.get(1);
        String reg4=s.get(2);
        String opcode = d.opcode;
        String unused = "00000";
        reg3 = Register.getregister(reg3);
        reg4 = Register.getregister(reg4);
        return opcode+unused+reg3+reg4;
    }
    public static String D(ArrayList<String> s) throws Error.InvalidRegisterException, Error.InvalidVariableNameException {
        Isa.description d = Isa.getdetails(s.get(0));
        String reg1=s.get(1);
        String mem_addr=s.get(2);
        String opcode = d.opcode;

        if(!Runner.variables.containsKey(mem_addr)){
            throw new Error.InvalidVariableNameException();
        }
        ArrayList<Integer> a;
        if(!Runner.vartemp.containsKey(mem_addr)) a =new ArrayList<>();
        else a=Runner.vartemp.get(mem_addr);

        a.add(Runner.linenumber);
        Runner.vartemp.put(mem_addr,a);


        reg1 = Register.getregister(reg1);
//      check memory address value
        return opcode+reg1+mem_addr;
    }
    public static String E(ArrayList<String> s) throws Error.MisMatchException {
        Isa.description d = Isa.getdetails(s.get(0));
        String mem_addr=s.get(1);
        String opcode = d.opcode;
        String unused = "000";

        if (!Runner.labels.containsKey(mem_addr)){
            ArrayList<Integer> a;
            if(!Runner.templabels.containsKey(mem_addr)) a =new ArrayList<>();
            else a=Runner.templabels.get(mem_addr);
            a.add(Runner.linenumber);
            Runner.templabels.put(mem_addr,a);
        }else{
            mem_addr=Encoding.inttobinary(String.valueOf(Runner.labels.get(mem_addr)));
        }
//      check memory address value
        return opcode+unused+mem_addr;
    }
    public static String F(ArrayList<String> s){
        Isa.description d = Isa.getdetails(s.get(0));
        String opcode = d.opcode;
        String unused = "00000000000";
        Runner.haltcalled=true;
//      check memory address value
        return opcode+unused;
    }

    public static String findfunction( ArrayList<String> tokens) throws Error.InvalidRegisterException, Error.InvalidImmediateException, Error.InvalidVariableNameException, Error.MisMatchException {
        String s;
        switch(tokens.get(0)){
//            case "add","sub","mul","xor","or","and": s=(!isimmideate(tokens)) ?A(tokens) : "throw error"; break;
//            case "mov"                             : s=(isimmideate(tokens)) ? B(tokens) : (!isimmideate(tokens)) ?C(tokens) : "throw error"; break;
//            case "rs","ls"                         : s=(isimmideate(tokens)) ? B(tokens) : "throw error"; break;
//            case "div","not","cmp"                 : s=(!isimmideate(tokens)) ?C(tokens) : "throw error"; break;
//            case "ld","st"                         : s=(!isimmideate(tokens)) ?D(tokens) : "throw error"; break;
//            case "jmp","jlt","jgt","je"            : s=(!isimmideate(tokens)) ?E(tokens) : "throw error"; break;
//            case "hlt"                             : s=(!isimmideate(tokens)) ?F(tokens) : "throw error"; break;

            case "add","sub","mul","xor","or","and": s=A(tokens); break;
            case "mov"                             : s=(isimmideate(tokens)) ? B(tokens) : C(tokens); break;
            case "rs","ls"                         : s=(isimmideate(tokens)) ? B(tokens) : "Invalid Immediate Value"; break;
            case "div","not","cmp"                 : s=C(tokens); break;
            case "ld","st"                         : s=D(tokens); break;
            case "jmp","jlt","jgt","je"            : s=E(tokens); break;
            case "hlt"                             : s=F(tokens); break;
            default: throw new Error.MisMatchException("Invalid Instruction");
        }
        return s;
    }

}
