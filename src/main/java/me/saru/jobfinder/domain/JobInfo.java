package me.saru.jobfinder.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

// TODO 불완전한 singleton?
public class JobInfo {
    @JsonIgnore
    private static JobInfo jobInfoInstance;
    private String next;
    // TODO 첫 total과 이후 total이 다른 현상이 있는데 왜 그런것인가?
    private int total;

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
