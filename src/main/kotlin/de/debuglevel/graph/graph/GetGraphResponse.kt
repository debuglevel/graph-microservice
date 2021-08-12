package de.debuglevel.graph.graph

import java.util.*

data class GetGraphResponse(
    val id: UUID,
    val label: String,
    val vertices: List<GetVertexResponse>,
    val edges: List<GetEdgeResponse>,
    val transitiveReduction: Boolean,
) {
    constructor(graph: Graph) : this(
        graph.id!!,
        graph.label,
        graph.graph.vertices.map { GetVertexResponse(it) },
        graph.graph.edges.map { GetEdgeResponse(it) },
        graph.transitiveReduction,
    )
}