package com.kodilla.csvconvertertask;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    public BatchConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    FlatFileItemReader<SpecificInformationPerson> reader() {
        FlatFileItemReader flatFileItemReader = new FlatFileItemReader();
        flatFileItemReader.setResource(new ClassPathResource("input.csv"));

        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames("name", "surname", "birthdayDate");

        BeanWrapperFieldSetMapper<SpecificInformationPerson> mapper = new BeanWrapperFieldSetMapper<>();
        mapper.setTargetType(SpecificInformationPerson.class);

        DefaultLineMapper<SpecificInformationPerson> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(mapper);

        flatFileItemReader.setLineMapper(lineMapper);
        return flatFileItemReader;
    }

    @Bean
    PersonProcessor processor() {
        return new PersonProcessor();
    }

    @Bean
    FlatFileItemWriter<GeneralInformationPerson> writer() {
        BeanWrapperFieldExtractor<GeneralInformationPerson> extractor = new BeanWrapperFieldExtractor<>();
        extractor.setNames(new String[]{"name", "surname", "age"});

        DelimitedLineAggregator<GeneralInformationPerson> aggregator = new DelimitedLineAggregator<>();
        aggregator.setDelimiter(",");
        aggregator.setFieldExtractor(extractor);

        FlatFileItemWriter flatFileItemWriter = new FlatFileItemWriter();
        flatFileItemWriter.setResource(new FileSystemResource("output.csv"));
        flatFileItemWriter.setShouldDeleteIfExists(true);
        flatFileItemWriter.setLineAggregator(aggregator);

        return flatFileItemWriter;
    }

    @Bean
    Step calculateAge(ItemReader<SpecificInformationPerson> reader, ItemProcessor<SpecificInformationPerson, GeneralInformationPerson> processor, ItemWriter<GeneralInformationPerson> writer) {
        return stepBuilderFactory.get("calculateAge")
                .<SpecificInformationPerson, GeneralInformationPerson>chunk(100)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    Job calculateAgeJob(Step calculateAge) {
        return jobBuilderFactory.get("calculateAgeJob")
                .incrementer(new RunIdIncrementer())
                .flow(calculateAge)
                .end()
                .build();
    }
}
