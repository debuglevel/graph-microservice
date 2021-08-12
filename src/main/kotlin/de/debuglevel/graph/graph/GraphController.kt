package de.debuglevel.graph.graph

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.swagger.v3.oas.annotations.tags.Tag
import mu.KotlinLogging
import java.io.ByteArrayOutputStream
import java.net.URI
import java.util.*


@Secured(SecurityRule.IS_ANONYMOUS)
@Controller("/graphs")
@Tag(name = "graphs")
class GraphController(private val graphService: GraphService) {
    private val logger = KotlinLogging.logger {}

    /**
     * Get all graphs
     * @return All graphs
     */
    @Get("/")
    fun getAllGraphs(): HttpResponse<List<GetGraphResponse>> {
        logger.debug("Called getAllGraphs()")
        return try {
            val graphs = graphService.getAll()
            val getGraphResponses = graphs
                .map { GetGraphResponse(it) }

            HttpResponse.ok(getGraphResponses)
        } catch (e: Exception) {
            logger.error(e) { "Unhandled exception" }
            HttpResponse.serverError()
        }
    }

    /**
     * Get a graph
     * @param id ID of the graph
     * @return A graph
     */
    @Get("/{id}")
    fun getOneGraph(id: UUID): HttpResponse<GetGraphResponse> {
        logger.debug("Called getOneGraph($id)")
        return try {
            val graph = graphService.get(id)

            val getGraphResponse = GetGraphResponse(graph)
            HttpResponse.ok(getGraphResponse)
        } catch (e: GraphService.ItemNotFoundException) {
            logger.debug { "Getting graph $id failed: ${e.message}" }
            HttpResponse.notFound()
        } catch (e: Exception) {
            logger.error(e) { "Unhandled exception" }
            HttpResponse.serverError()
        }
    }

    /**
     * Get rendered graph
     * @param id ID of the graph
     * @return A graph rendering
     */
    @Get("/{id}/renderings")
    fun getOneGraphRender(id: UUID, @QueryValue format: Format): HttpResponse<ByteArray> {
        logger.debug("Called getOneGraphRender($id, $format)")
        return try {
            val byteArrayOutputStream = ByteArrayOutputStream()
            graphService.writeFormat(id, byteArrayOutputStream, format)
            val byteArray = byteArrayOutputStream.toByteArray()

            HttpResponse.ok(byteArray)
        }
        catch (e: GraphService.ItemNotFoundException) {
            logger.debug { "Getting graph $id rendering failed: ${e.message}" }
            HttpResponse.notFound()
        } catch (e: Exception) {
            logger.error(e) { "Unhandled exception" }
            HttpResponse.serverError()
        }
    }

    /**
     * Create a graph.
     * @return A graph with their ID
     */
    @Post("/")
    fun postOneGraph(addGraphRequest: AddGraphRequest): HttpResponse<AddGraphResponse> {
        logger.debug("Called postOneGraph($addGraphRequest)")

        return try {
            val graph = addGraphRequest.toGraph()
            val addedGraph = graphService.add(graph)

            val addGraphResponse = AddGraphResponse(addedGraph)
            HttpResponse.created(addGraphResponse, URI(addedGraph.id.toString()))
        } catch (e: Exception) {
            logger.error(e) { "Unhandled exception" }
            HttpResponse.serverError()
        }
    }

//    /**
//     * Update a graph.
//     * @param id ID of the graph
//     * @return The updated graph
//     */
//    @Put("/{id}")
//    fun putOneGraph(id: UUID, updateGraphRequest: UpdateGraphRequest): HttpResponse<UpdateGraphResponse> {
//        logger.debug("Called putOneGraph($id, $updateGraphRequest)")
//
//        return try {
//            val graph = updateGraphRequest.toGraph()
//            val updatedGraph = graphService.update(id, graph)
//
//            val updateGraphResponse = UpdateGraphResponse(updatedGraph)
//            HttpResponse.ok(updateGraphResponse)
//        } catch (e: GraphService.EntityNotFoundException) {
//            logger.debug { "Updating graph $id failed: ${e.message}" }
//            HttpResponse.notFound()
//        } catch (e: Exception) {
//            logger.error(e) { "Unhandled exception" }
//            HttpResponse.serverError()
//        }
//    }

    /**
     * Delete a graph.
     * @param id ID of the graph
     */
    @Delete("/{id}")
    fun deleteOneGraph(id: UUID): HttpResponse<Unit> {
        logger.debug("Called deleteOneGraph($id)")
        return try {
            graphService.delete(id)

            HttpResponse.noContent()
        } catch (e: GraphService.ItemNotFoundException) {
            HttpResponse.notFound()
        } catch (e: Exception) {
            logger.error(e) { "Unhandled exception" }
            HttpResponse.serverError()
        }
    }

    /**
     * Delete all graph.
     */
    @Delete("/")
    fun deleteAllGraphs(): HttpResponse<Unit> {
        logger.debug("Called deleteAllGraphs()")
        return try {
            graphService.deleteAll()

            HttpResponse.noContent()
        } catch (e: Exception) {
            logger.error(e) { "Unhandled exception" }
            HttpResponse.serverError()
        }
    }
}