package me.saru.jobfinder.domain;

// TODO singleton?
public class JobInfo {
    private int total;
    private String next;
    private static JobInfo jobInfoInstance;

    private JobInfo() {
    }

    public static JobInfo getInstance() {
        if (jobInfoInstance == null) {
            jobInfoInstance = new JobInfo();
        }

        return jobInfoInstance;
    }

    public static void updateInfo(int total, String next) {
        JobInfo jobInfo = getInstance();
        jobInfo.updateTotal(total);
        jobInfo.updateNext(next);
    }

    private void updateNext(String next) {
        this.next = next;
    }

    private void updateTotal(int total) {
        this.total = total;
    }

    public int getTotal() {
        return total;
    }

    public String getNext() {
        return next;
    }
}
