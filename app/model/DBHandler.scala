package model

import com.couchbase.client.java.AsyncBucket
import com.couchbase.client.java.document.RawJsonDocument
import com.couchbase.client.java.view.{ViewQuery, AsyncViewResult}
import rx.Observable

object DBHandler {

  def getViewResult(design: String, view: String)(implicit bucket:AsyncBucket): Observable[AsyncViewResult] ={
    bucket.query(ViewQuery.from(design, view))
  }

  def getViewResult(design: String, view: String, reduce: Boolean, group: Boolean)
                   (implicit bucket:AsyncBucket): Observable[AsyncViewResult] = {
    bucket.query(ViewQuery.from(design, view).reduce(reduce).group(group))
  }

  def getDocument(docID:String)(implicit bucket:AsyncBucket) = {
    bucket.get(docID,classOf[RawJsonDocument])
  }
}

