package de.debuglevel.graph.graph

/**
 * File formats the visualized graph can be written to.
 */
enum class Format {
    /**
     * PNG
     */
    PNG,

    /**
     * SVG (with XML header)
     */
    SVG,

    /**
     * SVG without the XML header. Useful for embedding into HTML.
     */
    SVG_WITHOUT_XML_HEADER,

    /**
     * GraphViz DOT file
     */
    DOT,
}