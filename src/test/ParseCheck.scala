// Import necessary libraries and modules
import java.io.File // File handling in Java
import scala.collection.JavaConverters._ // Convert Java collections to Scala
import scala.util.Using // Scala utility for safe resource management
import org.scalatest.funsuite.AnyFunSuite // ScalaTest suite for unit tests
import com.typesafe.config.{ConfigFactory, ConfigRenderOptions, ConfigValueFactory} // Typesafe Config library for configuration management

// Define a class `ParseCheck` extending `AnyFunSuite` to write test cases
class ParseCheck extends AnyFunSuite {

  // Define a test case to check the parsing of the `libraries.conf` file
  test("Main `libraries.conf` file parses without errors.") {
    // `Using.resource` ensures that the file resource is automatically closed after use
    Using.resource(ConfigFactory.parseFile(new File("libraries.conf"))) { librariesConf =>
      // Extract a sequence of `name` values from the `extensions` config list
      val names: Seq[String] = librariesConf.getConfigList("extensions").asScala.map(_.getString("name"))
      // Print each name to the console - primarily for debugging purposes
      names.foreach(println)

      // Place for additional tests to validate the configuration file's contents
      // Examples: checking for unique names, verifying URLs, etc.
    }
  }
}
