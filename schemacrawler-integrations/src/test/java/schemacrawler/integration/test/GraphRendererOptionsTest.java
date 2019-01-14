/*
========================================================================
SchemaCrawler
http://www.schemacrawler.com
Copyright (c) 2000-2019, Sualeh Fatehi <sualeh@hotmail.com>.
All rights reserved.
------------------------------------------------------------------------

SchemaCrawler is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

SchemaCrawler and the accompanying materials are made available under
the terms of the Eclipse Public License v1.0, GNU General Public License
v3 or GNU Lesser General Public License v3.

You may elect to redistribute this code under any of these licenses.

The Eclipse Public License is available at:
http://www.eclipse.org/legal/epl-v10.html

The GNU General Public License v3 and the GNU Lesser General Public
License v3 are available at:
http://www.gnu.org/licenses/

========================================================================
*/

package schemacrawler.integration.test;


import static java.nio.file.Files.copy;
import static java.nio.file.Files.createDirectories;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.hamcrest.MatcherAssert.assertThat;
import static schemacrawler.test.utility.ExecutableTestUtility.executableExecution;
import static schemacrawler.test.utility.ExecutableTestUtility.hasSameContentAndTypeAs;
import static schemacrawler.test.utility.ExecutableTestUtility.outputFileOf;
import static schemacrawler.test.utility.FileHasContent.classpathResource;
import static schemacrawler.test.utility.TestUtility.clean;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;

import schemacrawler.schemacrawler.IncludeAll;
import schemacrawler.schemacrawler.RegularExpressionExclusionRule;
import schemacrawler.schemacrawler.RegularExpressionInclusionRule;
import schemacrawler.schemacrawler.SchemaCrawlerOptions;
import schemacrawler.schemacrawler.SchemaCrawlerOptionsBuilder;
import schemacrawler.schemacrawler.SchemaInfoLevelBuilder;
import schemacrawler.test.utility.BaseSchemaCrawlerTest;
import schemacrawler.test.utility.TestDatabaseConnectionParameterResolver;
import schemacrawler.tools.executable.SchemaCrawlerExecutable;
import schemacrawler.tools.integration.graph.GraphOptions;
import schemacrawler.tools.integration.graph.GraphOptionsBuilder;
import schemacrawler.tools.integration.graph.GraphOutputFormat;
import schemacrawler.tools.text.schema.SchemaTextDetailType;

@ExtendWith(TestDatabaseConnectionParameterResolver.class)
public class GraphRendererOptionsTest
  extends BaseSchemaCrawlerTest
{

  private static final String GRAPH_OPTIONS_OUTPUT = "graph_options_output/";

  private static Path directory;

  @BeforeAll
  public static void removeOutputDir()
    throws Exception
  {
    clean(GRAPH_OPTIONS_OUTPUT);
  }

  @BeforeAll
  public static void setupDirectory()
    throws Exception
  {
    final Path codePath = Paths.get(GraphRendererOptionsTest.class
      .getProtectionDomain().getCodeSource().getLocation().toURI()).normalize()
      .toAbsolutePath();
    directory = codePath
      .resolve("../../../schemacrawler-docs/graphs/"
               + GraphRendererOptionsTest.class.getSimpleName())
      .normalize().toAbsolutePath();
    FileUtils.deleteDirectory(directory.toFile());
    createDirectories(directory);
  }

  @Test
  public void executableForGraph_00(final TestInfo testInfo,
                                    final Connection connection)
    throws Exception
  {
    final SchemaCrawlerOptions schemaCrawlerOptions = schemaCrawlerOptionsWithMaximumSchemaInfoLevel();
    final GraphOptions graphOptions = GraphOptionsBuilder.builder().toOptions();

    executableGraph(SchemaTextDetailType.schema.name(),
                    connection,
                    schemaCrawlerOptions,
                    graphOptions,
                    currentMethodName(testInfo));
  }

  @Test
  public void executableForGraph_01(final TestInfo testInfo,
                                    final Connection connection)
    throws Exception
  {
    final GraphOptionsBuilder graphOptionsBuilder = GraphOptionsBuilder
      .builder();
    graphOptionsBuilder.sortTableColumns();
    graphOptionsBuilder.showOrdinalNumbers();
    final GraphOptions graphOptions = graphOptionsBuilder.toOptions();

    executableGraph(SchemaTextDetailType.schema.name(),
                    connection,
                    SchemaCrawlerOptionsBuilder.newSchemaCrawlerOptions(),
                    graphOptions,
                    currentMethodName(testInfo));
  }

  @Test
  public void executableForGraph_02(final TestInfo testInfo,
                                    final Connection connection)
    throws Exception
  {
    final GraphOptionsBuilder graphOptionsBuilder = GraphOptionsBuilder
      .builder();
    graphOptionsBuilder.noForeignKeyNames();
    final GraphOptions graphOptions = graphOptionsBuilder.toOptions();

    executableGraph(SchemaTextDetailType.schema.name(),
                    connection,
                    SchemaCrawlerOptionsBuilder.newSchemaCrawlerOptions(),
                    graphOptions,
                    currentMethodName(testInfo));
  }

  @Test
  public void executableForGraph_03(final TestInfo testInfo,
                                    final Connection connection)
    throws Exception
  {
    final GraphOptionsBuilder graphOptionsBuilder = GraphOptionsBuilder
      .builder();
    graphOptionsBuilder.noSchemaCrawlerInfo(true);
    graphOptionsBuilder.showDatabaseInfo(false);
    graphOptionsBuilder.showJdbcDriverInfo(false);
    final GraphOptions graphOptions = graphOptionsBuilder.toOptions();

    executableGraph(SchemaTextDetailType.schema.name(),
                    connection,
                    SchemaCrawlerOptionsBuilder.newSchemaCrawlerOptions(),
                    graphOptions,
                    currentMethodName(testInfo));
  }

  @Test
  public void executableForGraph_04(final TestInfo testInfo,
                                    final Connection connection)
    throws Exception
  {
    final GraphOptionsBuilder graphOptionsBuilder = GraphOptionsBuilder
      .builder();
    graphOptionsBuilder.showUnqualifiedNames();
    final GraphOptions graphOptions = graphOptionsBuilder.toOptions();

    executableGraph(SchemaTextDetailType.schema.name(),
                    connection,
                    SchemaCrawlerOptionsBuilder.newSchemaCrawlerOptions(),
                    graphOptions,
                    currentMethodName(testInfo));
  }

  @Test
  public void executableForGraph_05(final TestInfo testInfo,
                                    final Connection connection)
    throws Exception
  {
    final SchemaCrawlerOptionsBuilder schemaCrawlerOptionsBuilder = SchemaCrawlerOptionsBuilder
      .builder().includeTables(new RegularExpressionInclusionRule(".*BOOKS"));
    final SchemaCrawlerOptions schemaCrawlerOptions = schemaCrawlerOptionsBuilder
      .toOptions();

    final GraphOptions graphOptions = GraphOptionsBuilder.builder().toOptions();

    executableGraph(SchemaTextDetailType.schema.name(),
                    connection,
                    schemaCrawlerOptions,
                    graphOptions,
                    currentMethodName(testInfo));
  }

  @Test
  public void executableForGraph_06(final TestInfo testInfo,
                                    final Connection connection)
    throws Exception
  {
    final SchemaCrawlerOptions schemaCrawlerOptions = schemaCrawlerOptionsWithMaximumSchemaInfoLevel();
    final GraphOptions graphOptions = GraphOptionsBuilder.builder().toOptions();

    executableGraph(SchemaTextDetailType.brief.name(),
                    connection,
                    schemaCrawlerOptions,
                    graphOptions,
                    currentMethodName(testInfo));
  }

  @Test
  public void executableForGraph_07(final TestInfo testInfo,
                                    final Connection connection)
    throws Exception
  {
    final SchemaCrawlerOptions schemaCrawlerOptions = schemaCrawlerOptionsWithMaximumSchemaInfoLevel();
    final GraphOptions graphOptions = GraphOptionsBuilder.builder().toOptions();

    executableGraph(SchemaTextDetailType.schema.name(),
                    connection,
                    schemaCrawlerOptions,
                    graphOptions,
                    currentMethodName(testInfo));
  }

  @Test
  public void executableForGraph_08(final TestInfo testInfo,
                                    final Connection connection)
    throws Exception
  {
    final SchemaCrawlerOptionsBuilder schemaCrawlerOptionsBuilder = SchemaCrawlerOptionsBuilder
      .builder().includeTables(new RegularExpressionInclusionRule(".*BOOKS"));
    final SchemaCrawlerOptions schemaCrawlerOptions = schemaCrawlerOptionsBuilder
      .toOptions();

    final GraphOptionsBuilder graphOptionsBuilder = GraphOptionsBuilder
      .builder();
    graphOptionsBuilder.noForeignKeyNames().showUnqualifiedNames();
    final GraphOptions graphOptions = graphOptionsBuilder.toOptions();

    executableGraph(SchemaTextDetailType.schema.name(),
                    connection,
                    schemaCrawlerOptions,
                    graphOptions,
                    currentMethodName(testInfo));
  }

  @Test
  public void executableForGraph_09(final TestInfo testInfo,
                                    final Connection connection)
    throws Exception
  {
    final SchemaCrawlerOptionsBuilder schemaCrawlerOptionsBuilder = SchemaCrawlerOptionsBuilder
      .builder().includeTables(new RegularExpressionInclusionRule(".*BOOKS"))
      .grepOnlyMatching(true);
    final SchemaCrawlerOptions schemaCrawlerOptions = schemaCrawlerOptionsBuilder
      .toOptions();

    final GraphOptionsBuilder graphOptionsBuilder = GraphOptionsBuilder
      .builder();
    graphOptionsBuilder.noForeignKeyNames().showUnqualifiedNames();
    final GraphOptions graphOptions = graphOptionsBuilder.toOptions();

    executableGraph(SchemaTextDetailType.schema.name(),
                    connection,
                    schemaCrawlerOptions,
                    graphOptions,
                    currentMethodName(testInfo));
  }

  @Test
  public void executableForGraph_10(final TestInfo testInfo,
                                    final Connection connection)
    throws Exception
  {
    final SchemaCrawlerOptions schemaCrawlerOptions = SchemaCrawlerOptionsBuilder
      .builder()
      .includeGreppedColumns(new RegularExpressionInclusionRule(".*\\.REGIONS\\..*"))
      .toOptions();

    final GraphOptions graphOptions = GraphOptionsBuilder.builder().toOptions();

    executableGraph(SchemaTextDetailType.schema.name(),
                    connection,
                    schemaCrawlerOptions,
                    graphOptions,
                    currentMethodName(testInfo));
  }

  @Test
  public void executableForGraph_11(final TestInfo testInfo,
                                    final Connection connection)
    throws Exception
  {
    final SchemaCrawlerOptions schemaCrawlerOptions = SchemaCrawlerOptionsBuilder
      .builder()
      .includeGreppedColumns(new RegularExpressionInclusionRule(".*\\.REGIONS\\..*"))
      .grepOnlyMatching(true).toOptions();

    final GraphOptions graphOptions = GraphOptionsBuilder.builder().toOptions();

    executableGraph(SchemaTextDetailType.schema.name(),
                    connection,
                    schemaCrawlerOptions,
                    graphOptions,
                    currentMethodName(testInfo));
  }

  @Test
  public void executableForGraph_12(final TestInfo testInfo,
                                    final Connection connection)
    throws Exception
  {
    final GraphOptionsBuilder graphOptionsBuilder = GraphOptionsBuilder
      .builder();
    graphOptionsBuilder.showRowCounts();
    final GraphOptions graphOptions = graphOptionsBuilder.toOptions();

    final SchemaCrawlerOptions schemaCrawlerOptions = schemaCrawlerOptionsWithMaximumSchemaInfoLevel();

    executableGraph(SchemaTextDetailType.schema.name(),
                    connection,
                    schemaCrawlerOptions,
                    graphOptions,
                    currentMethodName(testInfo));
  }

  @Test
  public void executableForGraph_13(final TestInfo testInfo,
                                    final Connection connection)
    throws Exception
  {
    final Map<String, String> graphvizAttributes = new HashMap<>();

    final String GRAPH = "graph.";
    graphvizAttributes.put(GRAPH + "splines", "ortho");

    final String NODE = "node.";
    graphvizAttributes.put(NODE + "shape", "none");

    final GraphOptionsBuilder graphOptionsBuilder = GraphOptionsBuilder
      .builder();
    graphOptionsBuilder.withGraphvizAttributes(graphvizAttributes);
    final GraphOptions graphOptions = graphOptionsBuilder.toOptions();

    final SchemaCrawlerOptions schemaCrawlerOptions = schemaCrawlerOptionsWithMaximumSchemaInfoLevel();

    executableGraph(SchemaTextDetailType.schema.name(),
                    connection,
                    schemaCrawlerOptions,
                    graphOptions,
                    currentMethodName(testInfo));
  }

  @Test
  public void executableForGraph_lintschema(final TestInfo testInfo,
                                            final Connection connection)
    throws Exception
  {
    final SchemaCrawlerOptionsBuilder schemaCrawlerOptionsBuilder = SchemaCrawlerOptionsBuilder
      .builder().withSchemaInfoLevel(SchemaInfoLevelBuilder.maximum())
      .includeSchemas(new RegularExpressionInclusionRule(".*\\.FOR_LINT"));
    final SchemaCrawlerOptions schemaCrawlerOptions = schemaCrawlerOptionsBuilder
      .toOptions();

    final GraphOptions graphOptions = GraphOptionsBuilder.builder().toOptions();

    executableGraph(SchemaTextDetailType.schema.name(),
                    connection,
                    schemaCrawlerOptions,
                    graphOptions,
                    currentMethodName(testInfo));
  }

  private void executableGraph(final String command,
                               final Connection connection,
                               final SchemaCrawlerOptions options,
                               final GraphOptions graphOptions,
                               final String testMethodName)
    throws Exception
  {
    SchemaCrawlerOptions schemaCrawlerOptions = options;
    if (options.getSchemaInclusionRule().equals(new IncludeAll()))
    {
      schemaCrawlerOptions = SchemaCrawlerOptionsBuilder.builder()
        .fromOptions(options)
        .includeSchemas(new RegularExpressionExclusionRule(".*\\.SYSTEM_LOBS|.*\\.FOR_LINT"))
        .toOptions();
    }

    final SchemaCrawlerExecutable executable = new SchemaCrawlerExecutable(command);
    executable.setSchemaCrawlerOptions(schemaCrawlerOptions);

    final GraphOptionsBuilder graphOptionsBuilder = GraphOptionsBuilder
      .builder(graphOptions);
    graphOptionsBuilder.sortTables(true);
    graphOptionsBuilder.noInfo(graphOptions.isNoInfo());
    if (!"maximum".equals(options.getSchemaInfoLevel().getTag()))
    {
      graphOptionsBuilder.weakAssociations(true);
    }
    executable.setAdditionalConfiguration(graphOptionsBuilder.toConfig());

    // Generate diagram, so that we have something to look at, even if
    // the DOT file comparison fails
    final Path testDiagramFile = executableExecution(connection,
                                                     executable,
                                                     GraphOutputFormat.png);
    copy(testDiagramFile,
         directory.resolve(testMethodName + ".png"),
         REPLACE_EXISTING);

    // Check DOT file
    final String referenceFileName = testMethodName;
    assertThat(outputFileOf(executableExecution(connection,
                                                executable,
                                                GraphOutputFormat.scdot)),
               hasSameContentAndTypeAs(classpathResource(GRAPH_OPTIONS_OUTPUT
                                                         + referenceFileName
                                                         + ".dot"),
                                       GraphOutputFormat.scdot));
  }

}
