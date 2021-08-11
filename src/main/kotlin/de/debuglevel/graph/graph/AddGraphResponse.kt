package de.debuglevel.graph.graph

import java.util.*

data class AddGraphResponse(
    val id: UUID,
    val label: String,
) {
    constructor(graph: Graph) : this(
        graph.id!!,
        graph.label,
    )
}