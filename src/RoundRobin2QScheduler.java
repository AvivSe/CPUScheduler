import java.util.*;

public class RoundRobin2QScheduler extends Scheduler {
    int timeQuantum = 2;
    RoundRobin2QScheduler(List<Process> processList) {
        this.n = processList.size();
        this.processes = mountAndGetJobsList(processList);
    }

    @Override
    List<Process> mountAndGetJobsList(List<Process> processList) {
        processList.sort(Comparator.comparingInt(Process::getAt));
        return processList;
    }

    @Override
    void simulate() {
        List<Process> localProcesses = this.processes;
        List<Process> completedProcesses = new ArrayList<>();
        Queue<Process> onWorking = new LinkedList<>();
        HashSet<Process> stackHistory = new HashSet<>();

        Process currentProcess;

        int currentClock = 0;
        asdf(localProcesses, onWorking, stackHistory, currentClock);
        while(localProcesses.size() != completedProcesses.size()){
            if(onWorking.isEmpty())
            {
                currentClock = getCurrentClock(localProcesses, onWorking, stackHistory, currentClock);
            }
            else
            {
                currentProcess = onWorking.remove();
                if(currentProcess.getRt() > 2)
                {
                    currentProcess.setRt(currentProcess.getRt()-2);
                    currentClock = getCurrentClock(localProcesses, onWorking, stackHistory, currentClock);
                    for (Process p :onWorking) {
                        p.setWt(p.getWt()+1);
                    }
                    currentClock = getCurrentClock(localProcesses, onWorking, stackHistory, currentClock);
                    for (Process p :onWorking) {
                        p.setWt(p.getWt()+1);
                    }
                    onWorking.add(currentProcess);
                }
                else if(currentProcess.getRt() == 2)
                {
                    currentClock = getCurrentClock(localProcesses, onWorking, stackHistory, currentProcess, currentClock);
                    for (Process p :onWorking) {
                        p.setWt(p.getWt()+1);
                    }
                    currentClock = getCurrentClock(localProcesses, onWorking, stackHistory, currentClock);
                    for (Process p :onWorking) {
                        p.setWt(p.getWt()+1);
                    }
                    completedProcesses.add(currentProcess);
                }
                else if(currentProcess.getRt() == 1)
                {
                    currentClock = getCurrentClock(localProcesses, onWorking, stackHistory, currentProcess, currentClock);
                    for (Process p :onWorking) {
                        p.setWt(p.getWt()+1);
                    }
                    completedProcesses.add(currentProcess);
                }
                else
                {
                    completedProcesses.add(currentProcess);
                }
            }
        }
        this.processes = completedProcesses;
    }

    private void asdf(List<Process> localProcesses, Queue<Process> onWorking, HashSet<Process> stackHistory, int currentClock) {
        for (Process process : localProcesses) {
            if (!stackHistory.contains(process) && process.getAt() <= currentClock) {
                onWorking.add(process);
                stackHistory.add(process);
            }
        }
    }

    private int getCurrentClock(List<Process> localProcesses, Queue<Process> onWorking, HashSet<Process> stackHistory, int currentClock) {
        currentClock++;
        asdf(localProcesses, onWorking, stackHistory, currentClock);
        return currentClock;
    }

    private int getCurrentClock(List<Process> localProcesses, Queue<Process> onWorking, HashSet<Process> stackHistory, Process currentProcess, int currentClock) {
        currentProcess.setRt(0);
        currentClock = getCurrentClock(localProcesses, onWorking, stackHistory, currentClock);
        return currentClock;
    }
}
