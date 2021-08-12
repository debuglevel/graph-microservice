package de.debuglevel.graph.graph

import de.debuglevel.graphlibrary.*

data class AddGraphRequest(
    val label: String,
    val vertices: List<AddVertexRequest>,
    val edges: List<AddEdgeRequest>,
    val transitiveReduction: Boolean = false,
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
                start = builtGraph.vertices.single { vertex -> vertex.content == edge.startVertexId },
                end = builtGraph.vertices.single { vertex -> vertex.content == edge.endVertexId },
            )
            builtGraph.addEdge(builtEdge)
        }

        if (transitiveReduction) {
            TransitiveReduction.reduce(builtGraph)
        }

        return Graph(
            id = null,
            label = this.label,
            graph = builtGraph,
            transitiveReduction = this.transitiveReduction,
        )
    }
}