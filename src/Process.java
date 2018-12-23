import java.util.Objects;

public class Process {
    private int at;
    private int bt;
    private int rt;
    private int st;
    private int wt;
    private  int ct;

    public int getCt() {
        return ct;
    }

    public void setCt(int ct) {
        this.ct = ct;
    }

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    Process(String id, int arriveTime, int runningTime) {
        this.id = id;
        this.at = arriveTime-1;
        this.bt = runningTime;
        this.rt = this.bt;
        this.st = 0;
    }

    public int getRt() {
        return rt;
    }

    public void setRt(int rt) {
        this.rt = rt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Process process = (Process) o;
        return Objects.equals(id, process.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public int getWt() {
        return wt;
    }

    public void setWt(int wt) {
        this.wt = wt;
    }

    Process(Process p) {
        this.at = p.at;
        this.bt = p.bt;
        this.st = p.st;
        this.rt = p.rt;
        this.wt = p.wt;
        this.id = p.id;
    }

    @Override
    public String toString() {
        return id + "\t\t" + at + "\t\t" + bt + "\t\t" +wt + "\t\t" + getTa() +"\t\t" + ct;
    }

    int getSt() {
        return st;
    }

    void setSt(int st) {
        this.st = st;
    }

    public void setAt(int at) {
        this.at = at;
    }

    public void setBt(int bt) {
        this.bt = bt;
    }

    public int getTa() {
        return bt+wt;
    }

    int getAt() {
        return at;
    }

    int getBt() {
        return bt;
    }


}