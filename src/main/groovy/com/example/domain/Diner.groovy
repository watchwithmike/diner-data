package com.example.domain

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.mapping.Document

@Document
@TypeAlias("Diner")
class Diner extends AbstractAuditable{

    @Id
    String id
    String name
    String address
    List<Comment> comments

    def addComment(Comment comment) {
        if (!comments) {
            comments = new ArrayList<Comment>()
        }
        comments.add(comment)
    }
}
