package com.clean.cleanroom.config;

import com.clean.cleanroom.partner.entity.Partner;
import com.clean.cleanroom.partner.repository.PartnerRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
@EnableBatchProcessing // 배치 기능 활성화
public class BatchConfig {

    private final JobRepository jobRepository;
    private final PartnerRepository partnerRepository;
    private final PlatformTransactionManager transactionManager;


    public BatchConfig(JobRepository jobRepository,
                       PartnerRepository partnerRepository,
                       PlatformTransactionManager transactionManager) {
        this.jobRepository = jobRepository;
        this.partnerRepository = partnerRepository;
        this.transactionManager = transactionManager;
    }


    @Bean
    public Job deleteOldPartnersJob() {
        return new JobBuilder("deleteOldPartnersJob", jobRepository)
                .start(deleteOldPartnersStep())
                .build();
    }

    @Bean
    public Step deleteOldPartnersStep() {
        return new StepBuilder("deleteOldPartnersStep", jobRepository)
                .tasklet(deleteOldPartnersTasklet(), transactionManager)
                .build();
    }

    @Bean
    public Tasklet deleteOldPartnersTasklet() {
        return (contribution, chunkContext) -> {

            // 배치 작업 시작
            System.out.println("============배치 작업 시작");

            // 파트너 레포지토리에서 한달전에 탈퇴한 회원 찾기
            List<Partner> oldDeletedPartners =
                    partnerRepository.findAllByIsDeletedTrueAndDeletedAtBefore(LocalDateTime.now().minusMonths(1));

            // 소프트 딜리트된 회원 데이터 삭제
            partnerRepository.deleteAll(oldDeletedPartners);

            return RepeatStatus.FINISHED;
        };
    }
}

