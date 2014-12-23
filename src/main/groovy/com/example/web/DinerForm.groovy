package com.example.web

import com.example.domain.Diner
import org.codehaus.groovy.runtime.InvokerHelper

/**
 * Created by mike on 12/23/14.
 */
class DinerForm extends Diner implements Form {

    DinerForm() {}

    DinerForm(Diner diner) {
        InvokerHelper.setProperties(this, diner.properties)
    }

    Diner toDiner() {
        Diner diner = new Diner()
        InvokerHelper.setProperties(diner, this.properties)
        diner
    }

    @Override
    Map toMap() {
        Map updateProps = this.properties
        ['id', 'comments', 'class'].each {propKey ->
            updateProps.remove(propKey)
        }
        updateProps
    }
}
