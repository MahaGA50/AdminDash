package model

import com.couchbase.client.java.document.RawJsonDocument
import com.couchbase.client.java.{AsyncBucket, CouchbaseCluster}
import com.couchbase.client.java.view.{AsyncViewRow, AsyncViewResult, ViewQuery}
import rx.Observable
import rx.lang.scala.JavaConversions._

object DB {

  def connectDB(url: String, bucketName: String): AsyncBucket = {
    val cluster = CouchbaseCluster.create(url)
    cluster.openBucket(bucketName).async()
  }

  def getViewResult(design: String, view: String): Observable[AsyncViewResult] ={
    connectDB("127.0.0.1", "smardex").query(ViewQuery.from(design, view))
  }

  def getViewResult(design: String, view: String, reduce: Boolean, group: Boolean): Observable[AsyncViewResult] ={
    connectDB("127.0.0.1", "smardex").query(ViewQuery.from(design, view).reduce(reduce).group(group))
  }

  def getDocument(docID:String) = {
    connectDB("127.0.0.1", "smardex").get(docID,classOf[RawJsonDocument])
  }
}

