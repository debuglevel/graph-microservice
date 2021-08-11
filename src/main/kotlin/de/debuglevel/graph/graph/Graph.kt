package de.debuglevel.graph.graph

import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

//@Entity
data class Graph(
//    @Id
//    @GeneratedValue
    var id: UUID?,
    var label: String,
    var graph: de.debuglevel.graphlibrary.Graph<String>
)