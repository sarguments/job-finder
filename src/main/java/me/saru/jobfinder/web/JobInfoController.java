package me.saru.jobfinder.web;

import me.saru.jobfinder.domain.TotalJobInfo;
import me.saru.jobfinder.dto.JobDto;
import me.saru.jobfinder.dto.JobInfoDto;
import me.saru.jobfinder.service.JobService;
import me.saru.jobfinder.service.SaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class JobInfoController {
    private SaveService saveService;
    private JobService jobService;

    @Autowired
    public JobInfoController(SaveService saveService, JobService jobService) {
        this.saveService = saveService;
        this.jobService = jobService;
    }

    @GetMapping("/info")
    public TotalJobInfo jobSave() {
        String json = jobService.fetchWantedJson();

        // TODO false
        saveService.saveJobAndCompany(json);

        return TotalJobInfo.getInstance();
    }

    private void updateProcess() {
        String next = TotalJobInfo.getInstance().getNext();
        String json = jobService.fetchNextJob(next);

        // TODO false
        saveService.saveJobAndCompany(json);
    }

    // TODO PUT?
    @GetMapping(value = "/update", params = "number")
    public TotalJobInfo jobUpdateNumber(@RequestParam int number) {
        for (int i = 0; i < number; i++) {
            updateProcess();
        }

        return TotalJobInfo.getInstance();
    }

    @GetMapping("/jobs/{jobId}")
    public JobInfoDto jobInfo(@PathVariable int jobId) {
        String json = jobService.fetchJobs(jobId);

        // TODO dto에서 바로 이렇게 해도 되나?
        return JobInfoDto.of(json);
    }

    @GetMapping("/show")
    public List<JobDto> jobInfos() {
        return jobService.findAllJob();
    }
}
