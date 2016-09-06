import com.google.inject.AbstractModule
import model.DBConnection

class Module extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[DBConnection]).asEagerSingleton()
  }
}

