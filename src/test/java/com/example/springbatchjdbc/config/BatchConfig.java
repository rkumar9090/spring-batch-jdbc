package com.example.springbatchjdbc.config;

import com.example.springbatchjdbc.model.Student;
import com.example.springbatchjdbc.writer.ConsoleItemWriter;
import org.springframework.batch.core.*;
import org.springframework.batch.core.job.builder.*;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.*;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class BatchConfig {

    @Bean
    public JdbcCursorItemReader<Student> jdbcCursorItemReader(DataSource dataSource) {
        JdbcCursorItemReader<Student> reader = new JdbcCursorItemReader<>();
        reader.setDataSource(dataSource);
        reader.setSql("SELECT  name, type FROM student");
        reader.setRowMapper(new BeanPropertyRowMapper<>(Student.class));
        return reader;
    }

    @Bean
    public Job studentJob(JobRepository jobRepository, Step studentStep) {
        return new JobBuilder("studentJob", jobRepository)
                .start(studentStep)
                .build();
    }

    @Bean
    public Step studentStep(JobRepository jobRepository,
                            PlatformTransactionManager transactionManager,
                            JdbcCursorItemReader<Student> reader,
                            ConsoleItemWriter writer) {
        return new StepBuilder("studentStep", jobRepository)
                .<Student, Student>chunk(5)
                .reader(reader)
                .writer(writer)
                .transactionManager(transactionManager)
                .build();
    }
}
