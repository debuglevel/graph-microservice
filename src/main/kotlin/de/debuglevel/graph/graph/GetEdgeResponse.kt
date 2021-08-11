package de.debuglevel.graph.graph

import de.debuglevel.graphlibrary.Color
import de.debuglevel.graphlibrary.Edge
import de.debuglevel.graphlibrary.Shape
import de.debuglevel.graphlibrary.Vertex

data class GetEdgeResponse(
    val startVertexId: String,
    val endVertexId: String,
) {
    constructor(edge: Edge<String>) : this(
        startVertexId = edge.start.content,
        endVertexId = edge.end.content,
    )
}