import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SJFPreemptiveScheduler extends Scheduler {
    public SJFPreemptiveScheduler(List<Process> processList) {
        this.n = processList.size();
        processList.removeIf(x->x.getBt()==0);
        this.processes = mountAndGetJobsList(processList);
    }

    void simulate() {
        int n = this.processes.size();

        int complete = 0, t = this.processes.get(0).getAt(), minm = Integer.MAX_VALUE;
        int finish_time;
        Process shortest = processes.get(0);
        boolean check = false;

        // completed
        while (complete != n) {

            // Find process with minimum
            // remaining time among the
            // processes that arrives till the
            // current time`
            for (Process current : this.processes) {
                if ((current.getAt() <= t) &&
                        (current.getRt() < minm) && current.getRt() > 0) {
                    minm = current.getRt();
                    shortest = current;
                    check = true;
                }

            }

            if (!check) {
                t++;
                continue;
            }

            // Reduce remaining time by one
            shortest.setRt(shortest.getRt() - 1);

            // Update minimum
            minm = shortest.getRt();
            if (minm == 0)
                minm = Integer.MAX_VALUE;

            // If a process gets completely
            // executed
            if (shortest.getRt() == 0) {
                // Increment complete
                complete++;
                check = false;

                // Find finish time of current
                // process
                finish_time = t + 1;
                // Calculate waiting time
                shortest.setWt(finish_time - shortest.getBt() - shortest.getAt());
                shortest.setCt(finish_time);
                if (shortest.getWt() < 0) {
                    shortest.setWt(0);
                }

            }
            // Increment time
            t++;
        }
    }

    @Override
    public List<Process> mountAndGetJobsList(List<Process> processes) {
        processes.sort(Comparator.comparingInt(Process::getAt));
        return processes;
    }
}
