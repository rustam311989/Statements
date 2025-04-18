package hhh.irp2;

public class MyLine {
    private String name;
    private boolean[] days = new boolean[31];

    public String getName() {
        return name;
    }

    public boolean isDuty(int i) {
        return days[i];
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDuty(int i, boolean day) {
        days[i] = day;
    }
}
