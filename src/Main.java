import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

        for (String arg : args) {
            System.out.println(arg);
            //Parse input:
            List<String> parsedLines = Files.readAllLines(Paths.get(arg));
            List<Process> processList = new ArrayList<>();
            int n = Integer.valueOf(parsedLines.get(0));
            for (int i = 1; i <= n; i++) {
                String[] taskData = parsedLines.get(i).split(",");
                processList.add(new Process("P" + i, Integer.valueOf(taskData[0]), Integer.valueOf(taskData[1])));
            }
            // Application:
            List<Scheduler> schedulers = new ArrayList<>();
            schedulers.add(new FCFSScheduler(Scheduler.getDeepCopy(processList)));
            schedulers.add(new LCFSScheduler(Scheduler.getDeepCopy(processList)));
            schedulers.add(new LCFSPreemptiveScheduler((Scheduler.getDeepCopy(processList))));
            schedulers.add(new RoundRobin2QScheduler(Scheduler.getDeepCopy(processList)));
            schedulers.add(new SJFPreemptiveScheduler(Scheduler.getDeepCopy(processList)));

            schedulers.forEach(s -> {
                s.simulate();
                s.calculateAvgTime();
            });
        }

    }
}
