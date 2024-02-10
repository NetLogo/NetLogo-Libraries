import java.io.File
import java.net.URL
import scala.collection.JavaConverters._
import scala.util.Try
import org.scalatest.funsuite.AnyFunSuite
import com.typesafe.config.{ Config, ConfigFactory }

class ParseCheck extends AnyFunSuite {
  
  // Define the configuration object from the 'libraries.conf' file.
  val librariesConf: Config = ConfigFactory.parseFile(new File("libraries.conf"))
  
  // Test to ensure the 'libraries.conf' file contains the expected keys.
  test("libraries.conf has all required keys") {
    val requiredKeys = List("extensions", "repositories", "dependencies")
    requiredKeys.foreach { key =>
      assert(librariesConf.hasPath(key), s"Missing required key: $key")
    }
  }

  // Test to verify that version numbers in the 'dependencies' section follow semantic versioning.
  test("Version numbers follow semantic versioning") {
    val versions = librariesConf.getConfigList("dependencies").asScala.map(_.getString("version"))
    val versionPattern = "\\d+\\.\\d+\\.\\d+".r // Semantic versioning regex pattern.
    versions.foreach { version =>
      assert(versionPattern.pattern.matcher(version).matches, s"Version $version does not follow semantic versioning")
    }
  }

  // Test to check if the 'extensions' are sorted alphabetically without duplicates.
  test("extensions are sorted alphabetically with no duplicates") {
    val extensions = librariesConf.getConfigList("extensions").asScala
    val names = extensions.map(_.getString("name")).toList
    assert(names.sorted == names, "Extensions should be sorted alphabetically.")
    assert(names.distinct.size == names.size, "There are duplicate extension names.")
  }

  // Test to ensure that repository URLs are valid.
  test("Repository URLs are valid") {
    val repos = librariesConf.getConfigList("repositories").asScala
    repos.foreach { repo =>
      val url = repo.getString("url")
      assert(url.nonEmpty && isValidUrl(url), s"Repository URL is not valid: $url")
    }
  }

  // Utility function to check if a URL is valid.
  def isValidUrl(url: String): Boolean = Try(new URL(url).toURI).isSuccess
}
