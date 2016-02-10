package tmp.test;

import static org.ops4j.pax.exam.CoreOptions.*;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.*;

import tmp.Foo;

import java.io.File;
import javax.inject.Inject;
import org.junit.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.ConfigurationManager;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.options.MavenArtifactUrlReference;
import org.ops4j.pax.exam.options.MavenUrlReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(PaxExam.class)
public class DeclarativeServiceTest {

  private final static String DFL_KARAF_VERSION = "4.0.4";

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Inject
  private Foo foo;

  @Configuration
  public Option[] config() {

    MavenArtifactUrlReference karafUrl = maven().groupId("org.apache.karaf").artifactId("apache-karaf")
            .version(karafVersion()).type("zip");

    MavenUrlReference karafStandardRepo = maven().groupId("org.apache.karaf.features").artifactId("standard")
            .version(karafVersion()).classifier("features").type("xml");

    return new Option[]{
      // enable for debugging
      // KarafDistributionOption.debugConfiguration("5005", true),
      // setup karaf distribution
      karafDistributionConfiguration().frameworkUrl(karafUrl).unpackDirectory(new File("target", "exam"))
      .useDeployFolder(false),
      // Set log level
      //logLevel(LogLevel.INFO),
      //
      keepRuntimeFolder(),
      //
      configureConsole().ignoreLocalConsole(),
      // let's add some features
      features(karafStandardRepo, "logging", "scr"),
      // add some bundles
      mavenBundle().groupId("de.maggu2810.playground.test").artifactId("api").versionAsInProject().start(),
      mavenBundle().groupId("de.maggu2810.playground.test").artifactId("service1").versionAsInProject().start(),
      mavenBundle().groupId("de.maggu2810.playground.test").artifactId("service2").versionAsInProject().start()
    // add additional options here...
    };
  }

  public static String karafVersion() {
    ConfigurationManager cm = new ConfigurationManager();
    String karafVersion = cm.getProperty("pax.exam.karaf.version", DFL_KARAF_VERSION);
    return karafVersion;
  }

  @Test
  public void testComponent() {
    logger.info("TEST LOG");
    System.out.println("TEST STDOUT");

    Assert.assertNotNull(foo);
    Assert.assertEquals(4, foo.doWhatever());
  }

}
