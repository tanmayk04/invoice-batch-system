package com.example.invoicebatchsystem.batch.config;

import com.example.invoicebatchsystem.batch.model.InvoiceCsvRow;
import com.example.invoicebatchsystem.batch.processor.InvoiceProcessor;
import com.example.invoicebatchsystem.batch.writer.InvoiceWriter;
import com.example.invoicebatchsystem.entity.Invoice;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.file.FlatFileItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * @Configuration tells Spring:
 * this class contains batch setup code.
 */
@Configuration
public class InvoiceBatchJobConfig {

    /*
     * This method creates the Step.
     *
     * Why step first?
     * Because Job will use this step.
     *
     * Step is where we connect:
     * Reader -> Processor -> Writer
     */
    @Bean
    public Step invoiceStep(JobRepository jobRepository,
                            FlatFileItemReader<InvoiceCsvRow> invoiceCsvReader,
                            InvoiceProcessor invoiceProcessor,
                            InvoiceWriter invoiceWriter) {

        /*
         * StepBuilder is used to define one batch step.
         *
         * "invoiceStep" is just the name of this step.
         */
        return new StepBuilder("invoiceStep", jobRepository)

                /*
                 * chunk(input, output)
                 *
                 * This says:
                 * - process data in chunks of 3 records
                 * - input type is InvoiceCsvRow
                 * - output type is Invoice
                 *
                 * transactionManager is needed because DB writes should happen
                 * inside transaction boundaries.
                 */
                .<InvoiceCsvRow, Invoice>chunk(3)

                /*
                 * Reader: reads CSV rows
                 */
                .reader(invoiceCsvReader)

                /*
                 * Processor: validates and converts CSV row -> Invoice
                 */
                .processor(invoiceProcessor)

                /*
                 * Writer: saves Invoice entities into database
                 */
                .writer(invoiceWriter)

                /*
                 * build() completes the step definition
                 */
                .build();
    }

    /*
     * This method creates the Job.
     *
     * Job is the full batch process.
     * Right now it has only one step.
     */
    @Bean
    public Job invoiceImportJob(JobRepository jobRepository, Step invoiceStep) {

        /*
         * JobBuilder is used to define the batch job.
         *
         * "invoiceImportJob" is the name of the job.
         */
        return new JobBuilder("invoiceImportJob", jobRepository)

                /*
                 * start(...) tells job which step to run first.
                 * Since we have only one step, job starts and ends with this step.
                 */
                .start(invoiceStep)

                /*
                 * build() completes the job definition
                 */
                .build();
    }
}