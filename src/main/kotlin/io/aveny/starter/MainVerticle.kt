package io.aveny.starter

import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.core.Handler
import io.vertx.core.http.HttpServerResponse
import io.vertx.core.json.Json
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext

class MainVerticle : AbstractVerticle() {

  override fun start(startFuture: Future<Void>) {
    val router = createRouter()

    vertx.createHttpServer()
      .requestHandler { router.accept(it) }
      .listen(config().getInteger("http.port", 8080)) { result ->
        if (result.succeeded()) {
          startFuture.complete()
        } else {
          startFuture.fail(result.cause())
        }
      }
  }

  private fun createRouter() = Router.router(vertx).apply {
    get("/").handler(handlerRoot)
    get("/attributes").handler(handlerAttributes5e)
  }

  private val handlerRoot = Handler<RoutingContext> { req ->
    req.response().end("Welcome!")
  }

  private val handlerAttributes5e = Handler<RoutingContext> { req ->
    req.response().endWithJson(makeAttributes())
  }

  fun HttpServerResponse.endWithJson(obj: Any) {
    this.putHeader("Content-Type", "application/json; charset=utf-8").end(Json.encodePrettily(obj))
  }
}
