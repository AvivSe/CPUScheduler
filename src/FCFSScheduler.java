import java.util.*;

public class FCFSScheduler extends Scheduler {
    FCFSScheduler(List<Process> processList) {
        this.processes = mountAndGetJobsList(processList);
    }

    @Override
    void simulate() {
        int n = processes.size();
        for (int i = 0; i < n; i++) {
            Process current = processes.get(i);
            if(i == 0) {
                current.setWt(0);
                current.setSt(0);
            } else {
                Process oneBefore = processes.get(i - 1);
                current.setSt(oneBefore.getSt() + oneBefore.getBt());
                current.setWt(current.getSt()-current.getAt());
                current.setCt(+current.getSt() + current.getBt());
                if(current.getWt() < 0) {
                    current.setWt(0);
                }
            }
        }
    }

    @Override
    public List<Process> mountAndGetJobsList(List<Process> processes) {
        List<Process> result = Scheduler.getDeepCopy(processes);

        result.sort(Comparator.comparingInt(Process::getAt));
        return result;
    }
}
