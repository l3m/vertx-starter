package io.aveny.starter

import io.aveny.starter.di.DaggerAppComponent
import io.aveny.starter.di.TimeSeriesModule
import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.core.Handler
import io.vertx.core.http.HttpServerResponse
import io.vertx.core.json.Json
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import javax.inject.Inject
import javax.inject.Provider

class MainVerticle : AbstractVerticle() {

  init {
    DaggerAppComponent.builder()
      .timeSeriesModule(TimeSeriesModule)
      .build()
      .inject(this)
  }

  @Inject
  lateinit var timeSeriesProvider: Provider<TimeSeries>

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

  override fun stop(stopFuture : Future<Void>) {
    stopFuture.complete()
  }

  private fun createRouter() = Router.router(vertx).apply {
    get("/").handler(rootHandler)
  }

  private val rootHandler = Handler<RoutingContext> { req ->
    vertx.executeBlocking({ future: Future<MinMax> ->
      val ts = timeSeriesProvider.get()
      val result = ts.findMinMax()
      future.complete(result)
    }, false, { res ->
      req.response().endWithJson(res.result())
    })
  }

  private val rootHandlerFail = Handler<RoutingContext> { req ->
    val ts = timeSeriesProvider.get()
    val result = ts.findMinMax()
    req.response().endWithJson(result)
  }


  fun HttpServerResponse.endWithJson(obj: Any) {
    this.putHeader("Content-Type", "application/json; charset=utf-8").end(Json.encodePrettily(obj))
  }
}
