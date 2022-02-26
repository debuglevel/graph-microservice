package de.debuglevel.graph.graph

import de.debuglevel.graphlibrary.export.DotExporter
import de.debuglevel.graphlibrary.export.GraphvizExporter
import mu.KotlinLogging
import java.io.OutputStream
import java.util.*
import jakarta.inject.Singleton

@Singleton
class GraphService(
    private val graphProperties: GraphProperties,
) {
    private val logger = KotlinLogging.logger {}

    private val graphs = mutableMapOf<UUID, Graph>()

    val count: Int
        get() {
            logger.debug { "Getting graphs count..." }

            val count = graphs.count()

            logger.debug { "Got graphs count: $count" }
            return count
        }

    fun exists(id: UUID): Boolean {
        logger.debug { "Checking if graph $id exists..." }

        val isExisting = graphs.containsKey(id)

        logger.debug { "Checked if graph $id exists: $isExisting" }
        return isExisting
    }

    fun get(id: UUID): Graph {
        logger.debug { "Getting graph with ID '$id'..." }

        val graph = graphs.getOrElse(id) {
            logger.debug { "Getting graph with ID '$id' failed" }
            throw ItemNotFoundException(id)
        }

        logger.debug { "Got graph with ID '$id': $graph" }
        return graph
    }

    fun writeFormat(id: UUID, outputStream: OutputStream, format: Format) {
        logger.debug { "Writing graph SVG with ID '$id'..." }

        val graph = get(id)
        val dot = DotExporter.generate(graph.graph)

        if (format == Format.DOT) {
            outputStream.writer().use { it.write(dot) }
        } else {
            val graphvizFormat = when (format) {
                Format.PNG -> guru.nidi.graphviz.engine.Format.PNG
                Format.SVG -> guru.nidi.graphviz.engine.Format.SVG_STANDALONE
                Format.SVG_WITHOUT_XML_HEADER -> guru.nidi.graphviz.engine.Format.SVG
                Format.DOT -> throw IllegalArgumentException("DOT should have been handled above.")
            }

            GraphvizExporter.render(dot, outputStream, graphvizFormat)
        }

        logger.debug { "Wrote graph SVG with ID '$id': $graph" }
    }

    fun add(graph: Graph): Graph {
        logger.debug { "Adding graph '$graph'..." }

        graph.id = UUID.randomUUID()
        graphs[graph.id!!] = graph

        logger.debug { "Added graph: $graph" }
        return graph
    }

//    fun update(id: UUID, graph: Graph): Graph {
//        logger.debug { "Updating graph '$graph' with ID '$id'..." }
//
//        // an object must be known to Hibernate (i.e. retrieved first) to get updated;
//        // it would be a "detached entity" otherwise.
//        val updateGraph = this.get(id).apply {
//            label = graph.label
//        }
//
//        val updatedGraph = graphRepository.update(updateGraph)
//
//        logger.debug { "Updated graph: $updatedGraph with ID '$id'" }
//        return updatedGraph
//    }

    fun getAll(): Set<Graph> {
        logger.debug { "Getting all graphs ..." }

        val graphs = this.graphs.values.toSet()

        logger.debug { "Got ${graphs.size} graphs" }
        return graphs
    }

    fun delete(id: UUID) {
        logger.debug { "Deleting graph with ID '$id'..." }

        if (graphs.containsKey(id)) {
            graphs.remove(id)
        } else {
            throw ItemNotFoundException(id)
        }

        logger.debug { "Deleted graph with ID '$id'" }
    }

    fun deleteAll() {
        logger.debug { "Deleting all graphs..." }

        val countBefore = graphs.count()
        graphs.clear()
        val countAfter = graphs.count()
        val countDeleted = countBefore - countAfter

        logger.debug { "Deleted $countDeleted of $countBefore graphs, $countAfter remaining" }
    }

    class ItemNotFoundException(criteria: Any) : Exception("Entity '$criteria' does not exist.")
}