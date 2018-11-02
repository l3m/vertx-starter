package io.aveny.starter

import dagger.Module
import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.core.Handler
import io.vertx.core.http.HttpServerResponse
import io.vertx.core.json.Json
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import javax.inject.Inject

class MainVerticle : AbstractVerticle() {

  @Inject lateinit var roller: Roller

  init {
    DaggerAppComponent.builder()
      .rollerModule(RollerModule)
      .build()
      .inject(this)
  }

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
    get("/").handler(rootHandler)
    get("/roll/:diceCount/:die/:bonus").handler(handlerAttributes5e)
  }

  private val rootHandler = Handler<RoutingContext> { req ->
    vertx.executeBlocking({ future : Future<MinMax>->
      // Call some blocking API that takes a significant amount of time to return
      val result = TimeSeries().findMinMax()
      future.complete(result)
    }, { res ->
        req.response().endWithJson(res.result())
    })
  }

    private val handlerAttributes5e = Handler<RoutingContext> { req ->
    val diceCount = req.pathParam("diceCount").toInt()
    val die = req.pathParam("die").toInt()
    val bonus = req.pathParam("bonus").toInt()
    val rr = roller.roll(RollDesc(diceCount, die, bonus))
    req.response().endWithJson(rr)
  }

  fun HttpServerResponse.endWithJson(obj: Any) {
    this.putHeader("Content-Type", "application/json; charset=utf-8").end(Json.encodePrettily(obj))
  }
}
