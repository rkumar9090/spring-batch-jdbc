package com.example.springbatchjdbc.writer;


import com.example.springbatchjdbc.model.Student;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;


@Component
public class ConsoleItemWriter implements ItemWriter<Student> {


    @Override
    public void write(Chunk<? extends Student> chunk) throws Exception {
        chunk.forEach(System.out::println);
    }
}
