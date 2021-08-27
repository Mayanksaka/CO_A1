package simutils;

public class Runner {
    public static boolean halted = false;
    public static void main(String[] args){
        MEM.initialize();
        while(!halted){
            String Instruction = MEM.memory[PC.getcount()];
            EE.execute(Instruction);
            PC.updatecounter(PC.getcount()+1);
        }
        MEM.dump();

    }
}
