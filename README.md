<!--- some badges to display on the GitHub page -->

![Travis (.org)](https://img.shields.io/travis/debuglevel/graph-microservice?label=Travis%20build)
![Gitlab pipeline status](https://img.shields.io/gitlab/pipeline/debuglevel/graph-microservice?label=GitLab%20build)
![GitHub release (latest SemVer)](https://img.shields.io/github/v/release/debuglevel/graph-microservice?sort=semver)
![GitHub](https://img.shields.io/github/license/debuglevel/graph-microservice)
[![Gitpod ready-to-code](https://img.shields.io/badge/Gitpod-ready--to--code-blue?logo=gitpod)](https://gitpod.io/#https://github.com/debuglevel/graph-microservice)

# Graph Microservice

## HTTP API

### OpenAPI / Swagger

There is an OpenAPI (former: Swagger) specification created, which is available
at <http://localhost:8080/swagger/greeter-microservice-0.1.yml>, `build/tmp/kapt3/classes/main/META-INF/swagger/` in the
source directory and `META-INF/swagger/` in the jar file. It can easily be pasted into
the [Swagger Editor](https://editor.swagger.io) which provides a live demo
for [Swagger UI](https://swagger.io/tools/swagger-ui/), but also offers to create client libraries
via [OpenAPI Generator](https://openapi-generator.tech).

### Add graph

```bash
$ curl --location --request POST 'http://localhost:8080/graphs/' \
--header 'Content-Type: application/json' \
--data-raw '{
  "label": "My Graph",
  "transitiveReduction": true,
  "vertices": [
    {
      "id": "v1",
      "label": "Vertex 1"
    },
    {
      "id": "v2",
      "label": "Vertex 2",
      "color": "Red",
      "shape": "Rectangle"
    }
  ],
  "edges": [
    {
      "startVertexId": "v1",
      "endVertexId": "v2"
    }
  ]
}'

```

### Get graph

```bash
$ curl --location --request GET 'http://localhost:8080/graphs/d4208e71-1253-46aa-80e7-ab7b88e4e32d'

{
  "id" : "d4208e71-1253-46aa-80e7-ab7b88e4e32d",
  "label" : "My Graph",
  "vertices" : [ {
    "id" : "v1",
    "label" : "Vertex 1"
  }, {
    "id" : "v2",
    "label" : "Vertex 2"
  } ],
  "edges" : [ {
    "startVertexId" : "v1",
    "endVertexId" : "v2"
  } ]
}
```

### Get rendered graph
```bash
$ curl --location --request GET 'http://localhost:8080/graphs/d4208e71-1253-46aa-80e7-ab7b88e4e32d/renderings/?format=SVG_WITHOUT_XML_HEADER'

<svg width="129px" height="155px"
 viewBox="0.00 0.00 97.10 116.00" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
<g id="graph0" class="graph" transform=" rotate(0) translate(4 112)">
<title>graphname</title>
<polygon fill="#ffffff" stroke="transparent" points="-4,4 -4,-112 93.1019,-112 93.1019,4 -4,4"/>
<!-- &#45;1412758549 -->
<g id="node1" class="node">
<title>&#45;1412758549</title>
<ellipse fill="#ebebeb" stroke="#000000" cx="44.5509" cy="-90" rx="44.6019" ry="18"/>
<text text-anchor="middle" x="44.5509" y="-85.8" font-family="Times,serif" font-size="14.00" fill="#000000">Vertex 1</text>
</g>
<!-- &#45;525253907 -->
<g id="node2" class="node">
<title>&#45;525253907</title>
<ellipse fill="#ebebeb" stroke="#000000" cx="44.5509" cy="-18" rx="44.6019" ry="18"/>
<text text-anchor="middle" x="44.5509" y="-13.8" font-family="Times,serif" font-size="14.00" fill="#000000">Vertex 2</text>
</g>
<!-- &#45;1412758549&#45;&gt;&#45;525253907 -->
<g id="edge1" class="edge">
<title>&#45;1412758549&#45;&gt;&#45;525253907</title>
<path fill="none" stroke="#000000" d="M44.5509,-71.8314C44.5509,-64.131 44.5509,-54.9743 44.5509,-46.4166"/>
<polygon fill="#000000" stroke="#000000" points="48.051,-46.4132 44.5509,-36.4133 41.051,-46.4133 48.051,-46.4132"/>
</g>
</g>
</svg>
```

## gRPC API

For services that primarily handle RPC-style requests (in contrast to resource-orientated REST)
, [gRPC](https://grpc.io/) might be a better approach.

### Protocol buffer and Service definitions

`*.proto` files in `src/main/proto/` describe the gRPC services and their protobuf.

### Generation

`./gradlew generateProto` generates Java/Kotlin files from the service and protobuf definitions
at `src/main/proto/*.proto`. They will be placed at `build/generated/source/proto/main/` and contain classes for
the `message`s and functions for the `service` `rpc`s.

### Endpoint implementation

Implementing endpoints is quite simple and demonstrated in `GreetingEndpoint`.

### List services

As gRPC server reflection is provided by the `ReflectionFactory`, `grpcurl` can list all available services:

```
$ ./grpcurl -plaintext localhost:50051 list
greeting.Greeting
grpc.reflection.v1alpha.ServerReflection
```

### Call

A gRPC call can be made with:

```
$ ./grpcurl -plaintext -d '{"name":"Hans", "locale":"de_DE"}' localhost:50051 greeting.Greeting/Greet
{
  "message": "Grüß Gott, Hans"
}
```

## Configuration

There is a `application.yml` included in the jar file. Its content can be modified and saved as a
separate `application.yml` on the level of the jar file. Configuration can also be applied via the other supported ways
of Micronaut (see <https://docs.micronaut.io/latest/guide/index.html#config>). For Docker, the configuration via
environment variables is the most interesting one (see `docker-compose.yml`).
