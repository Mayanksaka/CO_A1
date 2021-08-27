package simutils;

import java.util.Scanner;

public class MEM {
    public static int MEMSIZE=256;
    public static int currentsize=0;
    public static String[] memory = new String[MEMSIZE];
    public static void initialize(){
        for(int i=0;i<MEMSIZE;i++) memory[i]="0000000000000000";
        Scanner sc = new Scanner(System.in);
        while(sc.hasNextLine())
            memory[currentsize++]=sc.nextLine();
    }
//    public static void initialize(){
//        currentsize=11;
//        for(int i=0;i<MEMSIZE;i++)
//            memory[i]="0000000000000000";
//
//        memory[0]="0000000000001010";
//        memory[1]="0000000000001010";
//        memory[2]="0000000000001010";
//        memory[3]="0000000000001010";
//        memory[4]="0000000000001010";
//        memory[5]="0000000000001010";
//        memory[6]="0000000000001010";
//        memory[7]="0000000000001010";
//        memory[8]="0000000000001010";
//        memory[9]="0000000000001010";
//        memory[10]="1001100000000000";
//    }
    public static void dump(){
        for(String i: memory)
            System.out.println(i);
    }
    public static void main(String[] args){

    }
}
