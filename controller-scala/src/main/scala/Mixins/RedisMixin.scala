import redis.clients.jedis.Jedis

trait RedisMixin {
  final val redis = new Jedis("localhost", System.getenv("REDIS_PORT"))
  redis.auth(System.getenv("REDIS_PASSWORD"))
}