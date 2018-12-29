import java.util.ArrayList;
import java.util.List;

public abstract class Scheduler{
    List<Process> processes;
    int n;

    abstract List<Process> mountAndGetJobsList(List<Process> processList);

    abstract void simulate();

    float calculateAvgTime() {
        int n = processes.size();
        int totalWaitingTime = 0, totoalTurnArounfTime = 0;
        System.out.print(this.getClass().getName()+": ");
        //System.out.println("ID\t\tCK\t\tRT\t\tWT\t\tTA\t\tCT");
        for (int i = 0; i < n; i++) {
            Process current = processes.get(i);
            if(i > 0) {
                Process oneBefore = processes.get(i - 1);
                current.setSt(oneBefore.getSt() + oneBefore.getBt());
            }
            totoalTurnArounfTime += current.getTa();
            totalWaitingTime += current.getWt();
            int completeTime = current.getTa() + current.getAt();
            //System.out.println(current);
        }
        //System.out.print("WT AVG: ");
       // System.out.printf("%.3f", (float)totalWaitingTime/(float)this.n);
        System.out.print("TA AVG: ");
        System.out.printf("%.3f",(float)totoalTurnArounfTime/(float)this.n);
        System.out.println();
        return (float)totoalTurnArounfTime/(float)this.n;
    }
    static List<Process> getDeepCopy(List<Process> processList) {
        List<Process> result = new ArrayList<>();
        processList.forEach(x -> result.add(new Process(x)));
        return result;
    }
}
