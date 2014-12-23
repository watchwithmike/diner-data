package com.example.domain

import com.example.DinerData
import com.example.web.DinerForm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import spock.lang.Shared
import spock.lang.Specification

/**
 * Created by mike on 12/23/14.
 */
@ContextConfiguration(classes = DinerData, loader = SpringApplicationContextLoader)
class DinerRepositoryTest extends Specification {

    @Autowired
    DinerRepository dinerRepository

    @Shared
    boolean mongoReset = false
    void setup() {
        if(!mongoReset) {
            dinerRepository.deleteAll()
            mongoReset = true
        }
    }

    def "Saving a diner adds it to the repository"() {
        given: "The starting size of the repository"
        def startingSize = dinerRepository.count()

        when: "A diner is saved"
        dinerRepository.save(new Diner(name: "Mike's Diner", address: "123 Example Street"))

        then: "The number of diners i n the repository has gone up 1"
        dinerRepository.count() == startingSize + 1
    }

    def "Updating part of a diner using save() eliminates missing fields"() {
        given: "A diner stored in the database"
        def original = dinerRepository.save(new Diner(name: "Eat at Joe's", address: "123 Groovy Lane"))

        when: "The diner's name is updated with save"
        def updated = dinerRepository.save(new Diner(id: original.id, name: "Joe & Sons"))

        then: "The name is updated"
        updated.name != original.name

        and: "The address is gone"
        !updated.address

        and: "The createdDate is gone"
        !updated.createdDate
    }

    def "Updating part of a diner using update() and properties retains untouched properties"(){
        given: "A diner with comments in the database."
        def moesDiner = new Diner(
                name: "Moe's Drive In", address: "123 Golden Mile",
                comments: [
                    new Comment(email: 'moe@example.com', text: 'My place is great!'),
                    new Comment(email: 'mike@exmaple.com', text: "Eat at Mike's instead!")
                ]
        )
        def orginalMoes = dinerRepository.save(moesDiner)

        and: "A dinerForm made from the stored diner"
        def dinerForm = new DinerForm(orginalMoes)

        and: "A modified field in the dinerForm"
        dinerForm.address = "123 Main Street"

        when: "The diner is updated using the update method"
        def updated = dinerRepository.updateDiner(dinerForm.id, dinerForm.toMap())

        then: "The updated diner address has changed"
        updated.address != orginalMoes.address

        and: "The remaining properties remain"
        updated.name == orginalMoes.name
        updated.comments.size() == orginalMoes.comments.size()
        updated.createdDate == orginalMoes.createdDate

    }

}
