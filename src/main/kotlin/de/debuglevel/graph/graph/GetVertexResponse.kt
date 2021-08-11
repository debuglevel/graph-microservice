package de.debuglevel.graph.graph

import de.debuglevel.graphlibrary.Color
import de.debuglevel.graphlibrary.Shape
import de.debuglevel.graphlibrary.Vertex

data class GetVertexResponse(
    val id: String,
    val label: String,
) {
    constructor(vertex: Vertex<String>) : this(
        id = vertex.content,
        label = vertex.text,
    )
}