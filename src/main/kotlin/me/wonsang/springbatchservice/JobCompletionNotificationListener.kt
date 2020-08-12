package me.wonsang.springbatchservice

import org.springframework.batch.core.BatchStatus
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.listener.JobExecutionListenerSupport
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component
import java.sql.ResultSet
import java.util.function.Consumer

@Component
class JobCompletionNotificationListener : JobExecutionListenerSupport() {

//    @Autowired
//    lateinit var jdbcTemplate: JdbcTemplate

    @Autowired
    lateinit var personRepository: PersonRepository

    override fun afterJob(jobExecution: JobExecution) {
        if (jobExecution.status == BatchStatus.COMPLETED) {
            val findAll = personRepository.findAll()
            val iterator = findAll.iterator()
            while (iterator.hasNext()) {
                println("chk => " + iterator.next().toString())
            }

//            jdbcTemplate.query("SELECT id, first_name, last_name FROM person"
//            ) { rs: ResultSet, row: Int ->
//                Person(
//                        rs.getLong(1),
//                        rs.getString(2),
//                        rs.getString(3))
//            }.forEach { person: Person -> println("Found <$person> in the database.") }

        }
    }

}