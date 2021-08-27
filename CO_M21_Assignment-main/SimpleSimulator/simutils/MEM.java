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
    public static void dump(){
        for(String i: memory)
            System.out.println(i);
    }
    public static void main(String[] args){

    }
}
