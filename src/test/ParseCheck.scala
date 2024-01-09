import java.io.File

import scala.collection.JavaConverters._

import org.scalatest.funsuite.AnyFunSuite
import com.typesafe.config.{ Config, ConfigException, ConfigFactory, ConfigRenderOptions, ConfigValueFactory }

class ParseCheck extends AnyFunSuite {
  // TODO: Add some checks for basic stuff, URLs are in-repo, in alpha order, no codename conflicts, etc.
  test("Main `libraries.conf` file parses without errors.") {
    val librariesConf = ConfigFactory.parseFile(new File("libraries.conf"))

    val names = librariesConf.getConfigList("extensions").asScala.map( (c) => c.getString("name") )
    names.foreach(println(_))
  }
}
