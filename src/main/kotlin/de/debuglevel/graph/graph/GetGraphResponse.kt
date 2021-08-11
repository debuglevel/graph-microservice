package de.debuglevel.graph.graph

import java.util.*

data class GetGraphResponse(
    val id: UUID,
    val label: String,
    val vertices: List<GetVertexResponse>,
    val edges: List<GetEdgeResponse>,
) {
    constructor(graph: Graph) : this(
        graph.id!!,
        graph.label,
        graph.graph.getVertices().map { GetVertexResponse(it) },
        graph.graph.getEdges().map { GetEdgeResponse(it) },
    )
}