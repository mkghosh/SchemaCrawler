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

package schemacrawler.test.utility;


import java.sql.Connection;

import schemacrawler.crawl.SchemaCrawler;
import schemacrawler.schema.Catalog;
import schemacrawler.schemacrawler.Config;
import schemacrawler.schemacrawler.SchemaCrawlerException;
import schemacrawler.schemacrawler.SchemaCrawlerOptions;
import schemacrawler.schemacrawler.SchemaCrawlerOptionsBuilder;
import schemacrawler.schemacrawler.SchemaInfoLevelBuilder;
import schemacrawler.schemacrawler.SchemaRetrievalOptions;
import schemacrawler.schemacrawler.SchemaRetrievalOptionsBuilder;
import schemacrawler.tools.iosource.ClasspathInputResource;
import sf.util.PropertiesUtility;

public final class DatabaseTestUtility
{

  public final static SchemaCrawlerOptions schemaCrawlerOptionsWithMaximumSchemaInfoLevel = SchemaCrawlerOptionsBuilder
    .builder().withSchemaInfoLevel(SchemaInfoLevelBuilder.maximum())
    .toOptions();

  public static Catalog getCatalog(final Connection connection,
                                   final SchemaCrawlerOptions schemaCrawlerOptions)
    throws SchemaCrawlerException
  {
    return getCatalog(connection,
                      SchemaRetrievalOptionsBuilder.newSchemaRetrievalOptions(),
                      schemaCrawlerOptions);
  }

  public static Catalog getCatalog(final Connection connection,
                                   final SchemaRetrievalOptions schemaRetrievalOptions,
                                   final SchemaCrawlerOptions schemaCrawlerOptions)
    throws SchemaCrawlerException
  {
    final SchemaCrawler schemaCrawler = new SchemaCrawler(connection,
                                                          schemaRetrievalOptions,
                                                          schemaCrawlerOptions);
    final Catalog catalog = schemaCrawler.crawl();

    return catalog;
  }

  public static Config loadHsqldbConfig()
  {
    try
    {
      final ClasspathInputResource inputResource = new ClasspathInputResource("/hsqldb.INFORMATION_SCHEMA.config.properties");
      return PropertiesUtility.loadConfig(inputResource);
    }

    catch (final Exception e)
    {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  private DatabaseTestUtility()
  {
    // Prevent instantiation
  }

}
