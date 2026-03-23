package com.example.invoicebatchsystem.batch.config;

/*
 * We import our input model because each CSV row
 * should become one InvoiceCsvRow object.
 */
import com.example.invoicebatchsystem.batch.model.InvoiceCsvRow;

/*
 * FlatFileItemReader is the Spring Batch reader
 * that reads delimited flat files like CSV.
 */
import org.springframework.batch.infrastructure.item.file.FlatFileItemReader;

/*
 * FlatFileItemReaderBuilder makes it easier to create the reader
 * using builder style instead of manual object setup.
 */

import org.springframework.batch.infrastructure.item.file.builder.FlatFileItemReaderBuilder;

/*
 * ClassPathResource is used to load files from src/main/resources.
 *
 * Our CSV file is inside:
 * resources/sample-data/invoices.csv
 */
import org.springframework.core.io.ClassPathResource;

/*
 * @Bean and @Configuration are used so Spring can create and manage
 * this reader as part of application context.
 */
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * This class is configuration for batch reader setup.
 *
 * Big project view:
 * batch.config package usually holds job, step, reader, writer bean setup.
 */
@Configuration
public class InvoiceBatchReaderConfig {

    /*
     * This method creates a FlatFileItemReader bean.
     *
     * Why bean?
     * Because later the batch step will need this reader,
     * and Spring should be able to inject it.
     */
    @Bean
    public FlatFileItemReader<InvoiceCsvRow> invoiceCsvReader() {

        /*
         * We return a configured reader using builder pattern.
         *
         * Generic type <InvoiceCsvRow> means:
         * each item read from file becomes one InvoiceCsvRow object.
         */
        return new FlatFileItemReaderBuilder<InvoiceCsvRow>()

                /*
                 * Name helps identify this reader bean.
                 * Useful for debugging and readability.
                 */
                .name("invoiceCsvReader")

                /*
                 * resource tells Spring where the input file is located.
                 *
                 * Since file is inside resources, we use ClassPathResource.
                 */
                .resource(new ClassPathResource("sample-data/invoices.csv"))

                /*
                 * delimited() tells Spring the file uses delimiter-separated values.
                 * For CSV, delimiter is comma.
                 */
                .delimited()

                /*
                 * names(...) defines the column names in the CSV header order.
                 *
                 * These names should align with InvoiceCsvRow field names.
                 */
                .names("invoiceNumber", "customerName", "amount", "dueDate", "status")

                /*
                 * linesToSkip(1) tells reader to skip the first line,
                 * because first line is the CSV header.
                 */
                .linesToSkip(1)

                /*
                 * targetType(...) tells Spring:
                 * map each row into this Java class.
                 */
                .targetType(InvoiceCsvRow.class)

                /*
                 * build() finishes builder configuration and returns reader object.
                 */
                .build();
    }
}