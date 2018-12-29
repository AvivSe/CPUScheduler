import java.util.*;

public class LCFSPreemptiveScheduler extends Scheduler{
    private int timeQuantum = 2;
    private int timeQuantumFlag = 0;
    private int currentWorkPosition = 0;

    public LCFSPreemptiveScheduler(List<Process> processList) {
        this.n = processList.size();
        HashSet<Process> toFilter = new HashSet<>();
        for (int i = 0; i < processList.size(); i++) {
            if(processList.get(i).getBt() == 0) {
                boolean flag = false;
                for(int j = 0; j < processList.size(); j++) {
                    if(i != j && processList.get(i).getAt() == processList.get(j).getAt()) {
                        flag = true;
                    }
                }
                if(!flag) {
                    toFilter.add(processList.get(i));
                }
            }
        }
        processList.removeIf(toFilter::contains);
        this.processes = mountAndGetJobsList(processList);
    }

    @Override
    public List<Process> mountAndGetJobsList(List<Process> processes) {
        processes.sort(Comparator.comparingInt(Process::getAt));
        return processes;
    }

    @Override
    void simulate() {
        Stack<Process> stack = new Stack<>();
        List<Process> completedProcesses = new ArrayList<>();
        HashSet<Process>  stackHistory = new HashSet<>();
        int time = this.processes.get(0).getAt();

        while(completedProcesses.size() != processes.size()) {
            for(Process p:processes) {
                if(!stackHistory.contains(p) && p.getAt() <= time) {
                    stack.push(p);
                    stackHistory.add(p);
                }
            }
            if(!stack.isEmpty()) {
                Process current = stack.peek();
                current.setRt(current.getRt() - 1);
                if (current.getRt() <= 0) {
                    completedProcesses.add(current);
                    current.setWt(time - current.getAt() - current.getBt()+1);
                    stack.pop();
                }
            }
            time++;

        }
        this.processes = completedProcesses;
    }
}
