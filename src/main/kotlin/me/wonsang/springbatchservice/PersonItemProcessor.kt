package me.wonsang.springbatchservice

import org.springframework.batch.item.ItemProcessor

class PersonItemProcessor : ItemProcessor<Person, Person> {
    override fun process(item: Person): Person? {
        println("PersonItemProcessor -> process")
        return Person(lastName = item.lastName, firstName = item.firstName)
    }
}