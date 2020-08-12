package me.wonsang.springbatchservice

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.item.database.JpaItemWriter
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder
import org.springframework.batch.item.file.FlatFileItemReader
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import javax.persistence.EntityManagerFactory

@Configuration
@EnableBatchProcessing
class BatchConfiguration {

    @Autowired
    lateinit var jobBuilerFactory: JobBuilderFactory

    @Autowired
    lateinit var stepBuilderFactory: StepBuilderFactory

    @Autowired
    lateinit var entityManagerFactory: EntityManagerFactory

    @Bean
    fun reader(): FlatFileItemReader<Person> {
        println("BatchConfiguration -> reader")
        return FlatFileItemReaderBuilder<Person>()
                .name("personItemReader")
                .resource(ClassPathResource("sample-data.csv"))
                .delimited()
                .names(*arrayOf("firstName", "lastName"))
                .fieldSetMapper(object : BeanWrapperFieldSetMapper<Person>() {
                    init {
                        setTargetType(Person::class.java)
                    }
                })
                .build()
    }

    @Bean
    fun processor(): PersonItemProcessor {
        println("BatchConfiguration -> processor")
        return PersonItemProcessor()
    }

    @Bean
    fun writer(): JpaItemWriter<Person> {
        println("BatchConfiguration -> writer")
        return JpaItemWriterBuilder<Person>()
                .entityManagerFactory(entityManagerFactory)
                .build()
    }

    @Bean
    fun importUserJob(listener: JobCompletionNotificationListener, step1: Step): Job {
        println("BatchConfiguration -> importUserJob")
        return jobBuilerFactory.get("importUserJob")
                .incrementer(RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build()
    }

    @Bean
    fun step1(writer: JpaItemWriter<Person>): Step {
        println("BatchConfiguration -> step1")
        return stepBuilderFactory.get("step1")
                .chunk<Person, Person>(10)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build()
    }

}