import java.util.*;

public class LCFSScheduler extends Scheduler {
    public LCFSScheduler(List<Process> processList) {
        this.processes = mountAndGetJobsList(processList);
    }

    @Override
    public List<Process> mountAndGetJobsList(List<Process> processes) {
        List<Process> result = Scheduler.getDeepCopy(processes);
        result.sort(Comparator.comparingInt(Process::getAt));
        return result;
    }

    @Override
    void simulate() {
        Stack<Process> stack = new Stack<>();
        List<Process> completedProcesses = new ArrayList<>();
        HashSet<Process>  stackHistory = new HashSet<>();
        int time = 0;

        while(completedProcesses.size() != processes.size()) {
            for(Process p:processes) {
                if(!stackHistory.contains(p) && p.getAt() <= time) {
                    stack.push(p);
                    stackHistory.add(p);
                }
            }
            if(!stack.isEmpty()) {
                Process current = stack.pop();
                current.setWt(time-current.getAt());
                current.setAt(current.getBt()+current.getWt());
                time += current.getBt();
                current.setCt(time);
                completedProcesses.add(current);
            }
        }
        this.processes = completedProcesses;
    }
}
