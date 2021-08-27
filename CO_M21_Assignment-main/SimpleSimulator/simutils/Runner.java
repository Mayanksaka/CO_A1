package simutils;
import java.util.ArrayList;
public class Runner {
    public static boolean halted = false;
    public static ArrayList<Integer> memory_address= new ArrayList<>();
    public static ArrayList<Integer> cycle_number= new ArrayList<>();
    public static int cycle =0;
    public static void main(String[] args){
        MEM.initialize();
        while(!halted){
            int p = PC.getcount();
            String Instruction = MEM.memory[p];
            memory_address.add(p);
            cycle_number.add(cycle);
            EE.execute(Instruction);
            PC.updatecounter(PC.getcount()+1);
            cycle++;
        }
        MEM.dump();
//        plotter.main(args);
//        --module-path "C:\Users\mayan\Downloads\CO_M21_Assignment-main\javafx-sdk-16\lib" --add-modules javafx.controls,javafx.graphics,javafx.swing,javafx.base,javafx.fxml 

    }
}
