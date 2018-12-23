import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        //Parse input:
        List<String> parsedLines = Files.readAllLines(Paths.get(args[0]));
        List<Process> processList = new ArrayList<>();
        int n = Integer.valueOf(parsedLines.get(0));
        for(int i = 1; i <= n; i++) {
            String[] taskData = parsedLines.get(i).split(",");
            processList.add(new Process("P"+i,Integer.valueOf(taskData[0]),Integer.valueOf(taskData[1])));
        }

        // Application:
        List<Scheduler> schedulers = new ArrayList<>();
        schedulers.add(new FCFSScheduler(processList));
        schedulers.add(new LCFSScheduler(processList));
        schedulers.add(new SJFScheduler(processList));

        schedulers.forEach(s -> {
            s.simulate();
            s.calculateAvgTime();
        });

    }

}
