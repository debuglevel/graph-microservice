package de.debuglevel.graph.graph

import de.debuglevel.graphlibrary.Edge


data class AddEdgeRequest(
//    val id: String,
    val startVertexId: String,
    val endVertexId: String,
) {
//    constructor(edge: Edge) : this(
//        id = edge.id,
//        startVertexId = edge.startVertexId,
//        endVertexId = edge.endVertexId,
//    )

//    fun toEdge(): Edge {
//        return Edge(
//            id = null,
//            startVertexId = this.startVertexId,
//            endVertexId = this.endVertexId,
//        )
//    }
}