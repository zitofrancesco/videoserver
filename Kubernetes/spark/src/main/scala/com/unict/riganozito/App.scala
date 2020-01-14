package com.unict.riganozito

import com.bot4s.telegram.api.RequestHandler
import com.bot4s.telegram.clients.ScalajHttpClient
import com.bot4s.telegram.future.TelegramBot
import com.bot4s.telegram.methods.SendMessage
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.kafka010.KafkaUtils
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.{Seconds, StreamingContext}

import scala.collection.mutable.Queue
import scala.concurrent.Future

class VideoServiceBot(val token: String) extends TelegramBot{
  override val client: RequestHandler[Future] = new ScalajHttpClient(token)
}

object App {

  val bot = new VideoServiceBot("1014975967:AAGqaoFLRlUj2-RIcqhcqZjK0U-hVJgIZF0")
  val eol = bot.run()

  val conf = new SparkConf().setAppName("spark-kafka")
  val ssc = new StreamingContext(conf, Seconds(30))

  val avg_time = new Queue[Double]
  val avg_req = new Queue[Double]

  /* Configure Kafka */
  val kafkaParams = Map[String, Object](
    "bootstrap.servers" -> "PLAINTEXT://<localhost>:9092",
    "key.deserializer" -> classOf[StringDeserializer],
    "value.deserializer" -> classOf[StringDeserializer],
    "group.id" -> "spark",
    "auto.offset.reset" -> "latest",
    "enable.auto.commit" -> (false: java.lang.Boolean)
  )

  val topics = Array("videoservice.topic.spark")
  val stream = KafkaUtils.createDirectStream[String, String](
    ssc,
    PreferConsistent,
    Subscribe[String, String](topics, kafkaParams)
  )

  /* Calculate the average of the number of requests */
  val lines = stream.map(_.value)

  val flat = lines.flatMap(line => line.split(" ")).filter(number => number.contains("#")).map(element => (element.replace("#", "").toDouble, 1))
  val r_avg = flat.reduce((x, y) => (x._1 + y._1, x._2 + y._2)).map { case (sum, count) => sum / (30 * count) }

  /* Calculate the average of the response time */
  val lines1 = stream.map(_.value)
  val flat1 = lines1.flatMap(line => line.split(" ")).filter(number => number.contains("@")).map(element => (element.replace("@", "").toDouble, 1))
  val t_avg = flat1.reduce((x, y) => (x._1 + y._1, x._2 + y._2)).map { case (sum, count) => sum / (30 * count) }

  ssc.start()
  ssc.awaitTermination()

  var global_req_sum, global_time_sum, global_req_avg, global_time_avg = 0.0


  /*
  if (avg_req.length != 0) {
    for (i <- 0 to (avg_req.length - 1)) {
      global_req_sum += avg_req(i)
      global_time_sum += avg_time(i)
    }

    global_req_avg = global_req_sum / avg_req.length
    global_time_avg = global_time_sum / avg_time.length

    var perc_time = ((t_avg - global_time_avg) / global_time_avg) * 100
    var perc_req = ((r_avg - global_req_avg) / global_req_avg) * 100

    if (perc_time > 20) {
      if (perc_req > 20) {
        bot.client.apply(SendMessage("-1001250525098", "Incremento del tempo medio di risposta pari a " + perc_time + ". Registrato anche un incremento medio del numero di richieste pari a " + perc_req))
      }
      else {
        bot.client.apply(SendMessage("-1001250525098", "Incremento del tempo medio di risposta pari a " + perc_time + ". Non è stato registrato un incremento considerevole nel numero medio di richieste"))

      }
    }
  }


  if(avg_req.length==10){
    avg_req.dequeue()
    avg_time.dequeue()
  }
  avg_req += r_avg
  avg_time += t_avg
*/
}