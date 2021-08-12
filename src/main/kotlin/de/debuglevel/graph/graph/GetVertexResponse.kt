package de.debuglevel.graph.graph

import de.debuglevel.graphlibrary.Color
import de.debuglevel.graphlibrary.Shape
import de.debuglevel.graphlibrary.Vertex

data class GetVertexResponse(
    val id: String,
    val label: String,
    val color: Color,
    val shape: Shape,
) {
    constructor(vertex: Vertex<String>) : this(
        id = vertex.content,
        label = vertex.text,
        color = vertex.color,
        shape = vertex.shape,
    )
}