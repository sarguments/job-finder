package me.saru.jobfinder.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

// TODO 불완전한 singleton?
public class TotalJobInfo {
    @JsonIgnore
    private static TotalJobInfo totalJobInfoInstance;
    private static String next;
    // TODO 첫 total과 이후 total이 다른 현상이 있는데 왜 그런것인가?
    private static int total;

    private TotalJobInfo() {
    }

    public static TotalJobInfo getInstance() {
        if (totalJobInfoInstance == null) {
            totalJobInfoInstance = new TotalJobInfo();
        }

        return totalJobInfoInstance;
    }

    public static void updateInfo(int total, String next) {
        TotalJobInfo.updateTotal(total);
        TotalJobInfo.updateNext(next);
    }

    private static void updateNext(String next) {
        TotalJobInfo.next = next;
    }

    private static void updateTotal(int total) {
        TotalJobInfo.total = total;
    }

    public int getTotal() {
        return total;
    }

    public String getNext() {
        return next;
    }
}
