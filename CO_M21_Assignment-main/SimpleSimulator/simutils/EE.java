package simutils;

public class EE {

    public static void callbyopcode(String s){
        String input = s.substring(5);
        switch (s.substring(0,5)){
            case "00000": addition(input);break;
            case "00001": subtraction(input);break;
            case "00010": move_immediate(input);break;
            case "00011": mov_register(input);break;
            case "00100": load(input);break;
            case "00101": store(input);break;
            case "00110": multiply(input);break;
            case "00111": divide(input);break;
            case "01000": right_shift(input);break;
            case "01001": left_shift(input);break;
            case "01010": exclusive_or(input);break;
            case "01011": or(input);break;
            case "01100": and(input);break;
            case "01101": invert(input);break;
            case "01110": comparE(input);break;
            case "01111": unconditional_jump(input);break;
            case "10000": jumpIfLessThan(input);break;
            case "10001": jumpIfGreaterThan(input);break;
            case "10010": jumpIfEqual(input);break;
            case "10011": halt(input);break;
        }
    }

    private static void addition(String input) {
        String r1=input.substring(2,5);
        int r2=intValueOf(RF.getregister(input.substring(5,8)));
        int r3=intValueOf(RF.getregister(input.substring(8)));
        int value = r2+r3;
        if(value>Math.pow(2,16))
            RF.setflag("v");
        RF.setRegister(r1,stringValurOf(value));
    }

    private static void subtraction(String input) {
        String r1=input.substring(2,5);
        int r2=intValueOf(RF.getregister(input.substring(5,8)));
        int r3=intValueOf(RF.getregister(input.substring(8)));
        int value = r2-r3;
        if(value<0){
            RF.setflag("v");
            value=0;}
        RF.setRegister(r1,stringValurOf(value));
    }

    private static void move_immediate(String input) {
        String r1=input.substring(0,3);
        String r2=input.substring(3);
        RF.setRegister(r1,"00000000"+r2);
    }

    private static void mov_register(String input){
        String r1=input.substring(5,8);
        String r2=input.substring(8);
        if(r2.equals("111"))
            RF.setRegister(r1,RF.getflag());
        else
            RF.setRegister(r1,RF.getregister(r2));
    }

    private static void load(String input) {
        String r1= input.substring(0,3);
        int imm = intValueOf(input.substring(3));
        RF.setRegister(r1,MEM.memory[imm]);
    }

    private static void store(String input) {
        String r1= input.substring(0,3);
        int imm = intValueOf(input.substring(3));
        MEM.memory[imm]=RF.getregister(r1);
    }

    private static void multiply(String input) {
        String r1=input.substring(2,5);
        int r2=intValueOf(RF.getregister(input.substring(5,8)));
        int r3=intValueOf(RF.getregister(input.substring(8)));
        int value = r2*r3;
        if(value>Math.pow(2,16))
            RF.setflag("v");
        RF.setRegister(r1,stringValurOf(value));
    }

    private static void divide(String input) {
        int r3=intValueOf(RF.getregister(input.substring(5,8)));
        int r4=intValueOf(RF.getregister(input.substring(8)));
        int quotient=r3/r4;
        int remainder=r3%r4;
        RF.setRegister("000",stringValurOf(quotient));
        RF.setRegister("001",stringValurOf(remainder));
    }

    private static void right_shift(String input) {
        String r1=input.substring(0,3);
        int imm=intValueOf(input.substring(3));
        StringBuilder s = new StringBuilder();
        for(int i =0;i<imm;i++) s.append('0');
        s.append(RF.getregister(r1));
        RF.setRegister(r1,s.toString().substring(0,16));
    }

    private static void left_shift(String input) {
        String r1=input.substring(0,3);
        int imm=intValueOf(input.substring(3));
        StringBuilder s = new StringBuilder();
        s.append(RF.getregister(r1));
        for(int i =0;i<imm;i++) s.append('0');
        RF.setRegister(r1,s.toString().substring(s.length()-16));
    }

    private static void exclusive_or(String input) {
        String r1=input.substring(2,5);
        String r2=RF.getregister(input.substring(5,8));
        String r3=RF.getregister(input.substring(8));
        StringBuilder s = new StringBuilder();
        for(int i =0;i<16;i++){
            if(r2.charAt(i)==r3.charAt(i)) s.append("0");
            else s.append("1");
        }
        RF.setRegister(r1,s.toString());
    }

    private static void or(String input) {
        String r1=input.substring(2,5);
        String r2=RF.getregister(input.substring(5,8));
        String r3=RF.getregister(input.substring(8));
        StringBuilder s = new StringBuilder();
        for(int i =0;i<16;i++){
            if(r2.charAt(i)=='0' && r3.charAt(i)=='0') s.append("0");
            else s.append("1");
        }
        RF.setRegister(r1,s.toString());
    }

    private static void and(String input) {
        String r1=input.substring(2,5);
        String r2=RF.getregister(input.substring(5,8));
        String r3=RF.getregister(input.substring(8));
        StringBuilder s = new StringBuilder();
        for(int i =0;i<16;i++){
            if(r2.charAt(i)=='1' && r3.charAt(i)=='1') s.append("1");
            else s.append("0");
        }
        RF.setRegister(r1,s.toString());
    }

    private static void invert(String input) {
        String r1=input.substring(5,8);
        String r2=RF.getregister(input.substring(8));
        StringBuilder s = new StringBuilder();
        for(Character c : r2.toCharArray())
            s.append((c.equals('0')) ? '1': '0');
        RF.setRegister(r1,s.toString());
    }

    private static void comparE(String input) {
        String r1=RF.getregister(input.substring(5,8));
        String r2=RF.getregister(input.substring(8));
        if(r1.equals(r2)) RF.setflag("e");
        else if(intValueOf(r1)<intValueOf(r2)) RF.setflag("l");
        else RF.setflag("g");

    }

    private static void unconditional_jump(String input) {
        String r1= input.substring(0,3);
        int mem_add = intValueOf(input.substring(3));
        PC.updatecounter(mem_add);
    }

    private static void jumpIfLessThan(String input) {
        String r1= input.substring(0,3);
        int mem_add = intValueOf(input.substring(3));
        if(RF.getregister("111").equals("0000000000000100"))
            PC.updatecounter(mem_add);
    }

    private static void jumpIfGreaterThan(String input) {
        String r1= input.substring(0,3);
        int mem_add = intValueOf(input.substring(3));
        if(RF.getregister("111").equals("0000000000000010"))
            PC.updatecounter(mem_add);
    }

    private static void jumpIfEqual(String input) {
        String r1= input.substring(0,3);
        int mem_add = intValueOf(input.substring(3));
        if(RF.getregister("111").equals("0000000000000001"))
            PC.updatecounter(mem_add);
    }

    private static void halt(String input) {
        Runner.halted=true;
    }

    private static int intValueOf(String s){
        return Integer.parseInt(s,2);
    }

    private static String stringValurOf(int n){
        return String.format("%16s",Integer.toBinaryString(n)).replace(' ','0');
    }

    public static void execute(String Instuction){
        callbyopcode(Instuction);
        print();

    }
    public static void print(){
        System.out.print(String.format("%8s",Integer.toBinaryString(PC.getcount())).replace(' ','0')+" ");
        for(int i =0;i<8;i++){
            String s = String.format("%3s",Integer.toBinaryString(i)).replace(' ','0');
//            System.out.print(s+ " ");
            System.out.print(RF.getregister(s)+" ");}
        System.out.println();
        RF.resetflag();
//        RF.setflag('d');
    }
    public static void main(String[] args){

    }
}
