import java.util.*;

public class FCFSScheduler extends Scheduler {
    FCFSScheduler(List<Process> processList) {
        this.n = processList.size();
        this.processes = mountAndGetJobsList(processList);
    }

    @Override
    void simulate() {
        int n = processes.size();

        for (int i = 0; i < n; i++) {
            Process current = processes.get(i);
            if(i == 0) {
                current.setWt(0);
                current.setCt(current.getAt()+current.getBt());
            } else {
                Process oneBefore = processes.get(i - 1);
                current.setSt(oneBefore.getCt());
                current.setWt(current.getSt()-current.getAt());
                if(current.getWt() < 0) {
                    current.setWt(0);
                }
                current.setCt(current.getAt() + current.getTa());
            }
        }
    }

    @Override
    public List<Process> mountAndGetJobsList(List<Process> processes) {
        processes.sort(Comparator.comparingInt(Process::getAt));
        return processes;
    }
}
