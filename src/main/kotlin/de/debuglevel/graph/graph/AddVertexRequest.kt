package de.debuglevel.graph.graph

import de.debuglevel.graphlibrary.Color
import de.debuglevel.graphlibrary.Shape
import de.debuglevel.graphlibrary.Vertex


data class AddVertexRequest(
    val id: String,
    val label: String,
) {
//    constructor(vertex: Vertex) : this(
//        id = vertex.id,
//        label = vertex.label
//    )

    fun toVertex(): Vertex<String> {
        return Vertex(
            content = id,
            color = Color.Gray,
            shape = Shape.Ellipse,
            tooltip = "",
            text = label,
        )
    }
}