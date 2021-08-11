package de.debuglevel.graph.graph

import io.micronaut.context.annotation.ConfigurationProperties
import java.time.Duration

@ConfigurationProperties("app.graph.graphs")
class GraphProperties {
    var someDuration: Duration = Duration.ofSeconds(1)
    var someText: String = "default"
    var someInteger: Int = 1
    var someDouble: Double = 1.0
}
