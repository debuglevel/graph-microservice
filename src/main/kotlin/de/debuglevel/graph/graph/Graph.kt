package de.debuglevel.graph.graph

import java.util.*

//@Entity
data class Graph(
//    @Id
//    @GeneratedValue
    var id: UUID?,
    var label: String,
    var graph: de.debuglevel.graphlibrary.Graph<String>,
    var transitiveReduction: Boolean,
)
