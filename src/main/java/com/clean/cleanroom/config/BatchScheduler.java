package com.clean.cleanroom.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class BatchScheduler {

    private final JobLauncher jobLauncher;
    private final Job deletePartnersJob;

    public BatchScheduler(JobLauncher jobLauncher, Job deletePartnersJob) {
        this.jobLauncher = jobLauncher;
        this.deletePartnersJob = deletePartnersJob;
    }

    @Scheduled(cron = "0 0 0 1 * ?")  // 매월 1일 자정에 실행
//    @Scheduled(cron = "0 * * * * ?") // 1분마다 실행 (테스트용)
    public void runBatchJob() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(deletePartnersJob, jobParameters);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}

