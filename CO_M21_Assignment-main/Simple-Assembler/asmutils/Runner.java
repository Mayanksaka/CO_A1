package asmutils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Runner {

    static Scanner x;
    static boolean haltcalled = false;
    static int linenumber=0;
    static HashMap<String,Integer> labels=new HashMap<>();
    static HashMap<String,ArrayList<Integer>> templabels= new HashMap<>();
    static HashMap<String, Integer> variables= new HashMap<>();
    static HashMap<String,ArrayList<Integer>> vartemp= new HashMap<>();
    static String[] instructions = new String[256];
    static boolean iserror=false;


    public void closefile(){
        x.close();
    }

    public static void main(String[] args)  {
        asmutils.Runner r = new asmutils.Runner();
        try{
//            System.out.println(new File("input.txt").getAbsolutePath());
//            x = new Scanner(new File("C:\\Users\\mayan\\IdeaProjects\\untitled\\input.txt"));
            x = new Scanner(System.in);}
        catch(Exception e){
            System.out.println("File not found");
        }
        while (x.hasNextLine()) {
            String a = x.nextLine();
            Scanner y = new Scanner(a);
            ArrayList<String> sub = new ArrayList<>();
            while (y.hasNext()) {
                sub.add(y.next());
            }
            String output=null;
//            Normal Instructions
            try {
                if (sub.size() == 0) {
//                System.out.println("empty line");
                    continue;
                }
                if (sub.get(0).equals("hlt")) {
                    output = asm.F(sub);
                    instructions[linenumber] = output;
                    linenumber++;
                    haltcalled = true;
                    if (y.hasNext() || x.hasNextLine())
                        throw new Error.MisMatchException("hlt not being used as the last instruction");
                    break;

                }
                if (Isa.functions.containsKey(sub.get(0))) {

                    output = Encoding.normalinstruction(sub);
                    instructions[linenumber] = output;
                    linenumber++;
                }
//            Labeled Instruction
                else if (Encoding.islabel(sub.get(0))) {

//                check label errors like repetation, label: label: add etc, label namings
                    String label = sub.get(0).replace(":", "");
                    if (!Pattern.matches("[a-zA-Z0-9_]+", label)) throw new Error.InvalidLabelNameException();

                    if (Isa.functions.containsKey(label))
                        throw new Error.MisMatchException("label name can't be same as instruction name");
                    if (labels.containsKey(label)) {
                        throw new Error.LabelNameRepeatException();
                    }

                    if (variables.containsKey(label))
                        throw new Error.MisMatchException("Name already defined as variable");
                    sub.remove(0);
                    if(sub.size()==0){
                        throw new Error.MisMatchException("Empty label");
                    }
                    if (!Isa.functions.containsKey(sub.get(0)))
                        throw new Error.MisMatchException("Invalid Instruction name");
                    output = Encoding.normalinstruction(sub);

                    if (output == null) {
                        throw new Error.InvalidInstructionNameException();
                    }
                    instructions[linenumber] = output;
                    if (templabels.containsKey(label)) {
                        ArrayList<Integer> updatelines;
                        updatelines = templabels.get(label);
                        for (Integer i : updatelines) {
                            String fetch = instructions[i];
                            instructions[i] = fetch.substring(0, 8) + Encoding.inttobinary(String.valueOf(linenumber));
                        }
                        templabels.remove(label);
                    }
                    labels.put(label, linenumber);
                    linenumber++;
                }
//            Variable
                else if (Encoding.isvariable(sub.get(0))) {
                    if (linenumber > 0) {
                        throw new Error.MisMatchException("Variable Must defined before instruction");
                    }
//                check variable errors

//
                    String vname;
                    int size = sub.size();
                    if (size == 1) {
                        throw new Error.MisMatchException("variable must have a name");
                    } else if (size == 2) {
                        vname = sub.get(1);//check name also other wise error
                        if (Isa.functions.containsKey(vname))
                            throw new Error.MisMatchException("variable name can't be same as instruction name");

                        if (!Pattern.matches("[a-zA-Z0-9_]+", vname)) throw new Error.InvalidVarNameException();
                        if (variables.containsKey(vname)) {
                            throw new Error.MisMatchException("Variable name arleady defined");
                        } else {
                            variables.put(vname, variables.size() + 1);
                        }

                    } else {
                        throw new Error.MisMatchException("More than 1 parameters provided");
                    }

                } else {
                    throw new Error.MisMatchException("Invalid input not a instruction,variable or label");
                }

            } catch (Error.InvalidParametersException| Error.InvalidRegisterException| Error.InvalidVariableNameException | Error.InvalidImmediateException| Error.MisMatchException| Error.LabelNameRepeatException | Error.InvalidInstructionNameException | Error.InvalidLabelNameException | Error.InvalidVarNameException e) {
                System.out.println(e);
                iserror=true;

            }
//            System.out.println(output);
        }
        try{
            if (haltcalled == true && x.hasNext()) {
                throw new Error.MisMatchException("halt is not last instruction");
            }
//        update variables
            if(templabels.size()>0){
                System.out.print("label name : ");
                for (String s:templabels.keySet()) System.out.print(s+", ");
                throw new Error.MisMatchException("Not defined");}
            for (String s : variables.keySet()) {
                variables.put(s, variables.get(s) + linenumber - 1);
//            System.out.println(s+" : "+variables.get(s));
            }
            for (String s : vartemp.keySet()) {
                for (Integer i : vartemp.get(s)) {
                    String fetch = instructions[i];
                    try {
                        instructions[i] = fetch.substring(0, 8) + Encoding.inttobinary(String.valueOf(variables.get(s)));
                    } catch (Error.MisMatchException e) {
                        System.out.println(e);
                        System.out.println("mem_address_limit_exceed");
                    }
                }
            }
            if(iserror==false){
            for (String s : instructions) {
                if (s != null)
                    System.out.println(s);
            }}
        } catch (Error.MisMatchException e) {
            System.out.println(e);
        }


        r.closefile();
    }
}
