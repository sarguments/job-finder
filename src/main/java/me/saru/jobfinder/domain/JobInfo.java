package me.saru.jobfinder.domain;

// TODO singleton?
public class JobInfo {
    private int total;
    private String next;

    public JobInfo() {
    }

    public JobInfo(int total, String next) {
        this.total = total;
        this.next = next;
    }

    public int getTotal() {
        return total;
    }

    public String getNext() {
        return next;
    }
}
