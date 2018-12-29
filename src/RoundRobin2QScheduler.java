import java.util.*;

public class RoundRobin2QScheduler extends Scheduler {
    RoundRobin2QScheduler(List<Process> processList) {
        this.n = processList.size();
        this.processes = mountAndGetJobsList(processList);
    }

    @Override
    List<Process> mountAndGetJobsList(List<Process> processList) {
        //processList.sort(Comparator.comparingInt(Process::getAt));
        return processList;
    }

    @Override
    void simulate() {
        List<Process> linkedList = new LinkedList<>();
        int completed = 0, time = processes.get(0).getAt()-1;
        processes.get(0).setWt(time);
        HashSet<Process> visited = new HashSet<>();
        linkedList.add(processes.get(0));
        int i = 0;
        while(completed != processes.size()) {

            for (Process p : processes) {
                if(!visited.contains(p) && p.getAt() <= time) {
                    visited.add(p);
                    linkedList.add(p);
                }
            }

            if(linkedList.size() > 0) {
                Process current = linkedList.get(i);
                current.setWt(time - current.getAt() - current.getBt()+1);
                current.setRt(current.getRt()-2);
                if(current.getRt() <= 0) {
                    linkedList.remove(current);
                    completed++;
                }
                if(linkedList.size() >0) {
                    i = (i + 1) % linkedList.size();
                }
            }
            time +=2;
        }
    }
}
