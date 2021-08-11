package de.debuglevel.graph.graph

import de.debuglevel.graphlibrary.Edge

data class AddGraphRequest(
    val label: String,
    val vertices: List<AddVertexRequest>,
    val edges: List<AddEdgeRequest>,
) {
//    constructor(graph: Graph) : this(
//        label = graph.label
//    )

    fun toGraph(): Graph {
        val builtGraph = de.debuglevel.graphlibrary.Graph<String>()

        this.vertices.forEach {
            builtGraph.addVertex(it.toVertex())
        }

        this.edges.forEach { edge ->
            val builtEdge = Edge(
                start = builtGraph.getVertices().single { vertex -> vertex.content == edge.startVertexId },
                end = builtGraph.getVertices().single { vertex -> vertex.content == edge.endVertexId },
            )
            builtGraph.addEdge(builtEdge)
        }

        return Graph(
            id = null,
            label = this.label,
            graph = builtGraph,
        )
    }
}