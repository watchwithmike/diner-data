package com.example.domain

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate

/**
 * Created by mike on 12/23/14.
 */
class AbstractAuditable {

    @CreatedDate
    Date createdDate

    @LastModifiedDate
    Date lastModifiedDate

}

