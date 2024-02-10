import java.io.File
import java.net.URL
import scala.util.{Using, Try}
import scala.collection.JavaConverters._
import org.scalatest.funsuite.AnyFunSuite
import com.typesafe.config.{ Config, ConfigFactory, ConfigValue }

class ParseCheck extends AnyFunSuite {

  Using.resource(ConfigFactory.parseFile(new File("libraries.conf"))) { config =>
    
    test("libraries.conf contains all required keys with expected values") {
      val requiredKeys = Map(
        "extensions" -> classOf[ConfigValue],
        "repositories" -> classOf[ConfigValue],
        "dependencies" -> classOf[ConfigValue]
      )
      
      requiredKeys.foreach { case (key, expectedType) =>
        assert(config.hasPath(key), s"Missing required key: $key")
        assert(expectedType.isInstance(config.getValue(key)), s"Key $key does not match the expected type.")
      }
    }

    test("no deprecated keys are present") {
      val deprecatedKeys = List("oldKey1", "oldKey2")
      deprecatedKeys.foreach(key =>
        assert(!config.hasPath(key), s"Deprecated key found: $key"))
    }

    test("config structure for 'extensions' adheres to expected schema") {
      val extensions = config.getConfigList("extensions").asScala
      val expectedSchema = Set("name", "version", "url")
      extensions.foreach { ext =>
        expectedSchema.foreach(schemaKey =>
          assert(ext.hasPath(schemaKey), s"Missing key in extension schema: $schemaKey"))
      }
    }

    test("mandatory extensions are present") {
      val mandatoryExtensions = Set("json", "xml")
      val extensionNames = config.getConfigList("extensions").asScala.map(_.getString("name")).toSet
      mandatoryExtensions.foreach { extension =>
        assert(extensionNames.contains(extension), s"Mandatory extension not found: $extension")
      }
    }

    test("extensions are sorted alphabetically and have unique names") {
      val extensions = config.getConfigList("extensions").asScala
      val names = extensions.map(_.getString("name")).toList
      assert(names.sorted == names, "Extensions are not sorted alphabetically.")
      assert(names.distinct.size == names.size, "Duplicate extension names found.")
    }

    test("dependency versions follow semantic versioning") {
      val versions = config.getConfigList("dependencies").asScala.map(_.getString("version"))
      val versionPattern = "\\d+\\.\\d+\\.\\d+".r
      versions.foreach(version =>
        assert(versionPattern.pattern.matcher(version).matches, s"Version $version does not follow semantic versioning"))
    }

    test("repository URLs are valid") {
      val repos = config.getConfigList("repositories").asScala
      repos.foreach(repo => {
        val url = repo.getString("url")
        assert(Try(new URL(url).toURI).isSuccess, s"Invalid URL: $url")
      })
    }
  }
}
