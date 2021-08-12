package de.debuglevel.graph.graph

import de.debuglevel.graphlibrary.Color
import de.debuglevel.graphlibrary.Shape
import de.debuglevel.graphlibrary.Vertex


data class AddVertexRequest(
    val id: String,
    val label: String,
    val color: Color = Color.Blue,
    val shape: Shape = Shape.Ellipse,
    val tooltip: String = "",
) {
//    constructor(vertex: Vertex) : this(
//        id = vertex.id,
//        label = vertex.label
//    )

    fun toVertex(): Vertex<String> {
        return Vertex(
            content = id,
            color = color,
            shape = shape,
            tooltip = tooltip,
            text = label,
        )
    }
}