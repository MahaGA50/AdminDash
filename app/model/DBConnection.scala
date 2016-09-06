package model

import com.couchbase.client.java.{ CouchbaseCluster}
import play.api.Play


class DBConnection  {
  lazy val confURL = Play.current.configuration.getString("server.url").getOrElse("127.0.0.1")
  lazy val confBucket = Play.current.configuration.getString("bucket.name").getOrElse("smardex")
  lazy val cluster = CouchbaseCluster.create(confURL)
  lazy val bucket = cluster.openBucket(confBucket).async()
  }