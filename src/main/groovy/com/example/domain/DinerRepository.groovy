package com.example.domain

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.FindAndModifyOptions
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.data.mongodb.repository.MongoRepository

/**
 * Created by mike on 12/23/14.
 */
interface DinerRepository extends MongoRepository<Diner, String>, DinerRepositoryCustom {

    Diner findById(String id)
}


interface DinerRepositoryCustom {
    Diner updateDiner(String id, Map update)
}

class DinerRepositoryImpl implements DinerRepositoryCustom {

    @Autowired
    MongoTemplate mongoTemplate

    @Override
    Diner updateDiner(String id, Map updateProps) {
        Query query = new Query(Criteria.where('id').is(id))
        Update update = new Update()
        updateProps.each {key, value ->
            update.set(key, value)
        }
        mongoTemplate.findAndModify(query, update, FindAndModifyOptions.options().returnNew(true), Diner.class)
    }
}