/*
 *
 * SchemaCrawler
 * http://www.schemacrawler.com
 * Copyright (c) 2000-2016, Sualeh Fatehi.
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * library; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307, USA.
 *
 */
package schemacrawler.tools.lint;


import static sf.util.Utility.isBlank;

import java.io.Serializable;
import java.util.UUID;

import schemacrawler.schemacrawler.Config;
import schemacrawler.schemacrawler.InclusionRule;
import schemacrawler.schemacrawler.RegularExpressionRule;
import sf.util.ObjectToString;

public class LinterConfig
  implements Serializable, Comparable<LinterConfig>
{

  private static final long serialVersionUID = 83079182550531365L;

  private final String linterId;
  private final String linterInstanceId;
  private boolean runLinter;
  private LintSeverity severity;
  private LintDispatch dispatch;
  private int dispatchThreshold;
  private final Config config;
  private String tableInclusionPattern;
  private String tableExclusionPattern;
  private String columnInclusionPattern;
  private String columnExclusionPattern;

  public LinterConfig(final String linterId)
  {
    if (isBlank(linterId))
    {
      throw new IllegalArgumentException("No linter id provided");
    }
    this.linterId = linterId;
    linterInstanceId = UUID.randomUUID().toString();
    runLinter = true; // default value
    config = new Config();
    dispatch = LintDispatch.none; // default value
  }

  public String getLinterInstanceId()
  {
    return linterInstanceId;
  }

  @Override
  public int compareTo(final LinterConfig other)
  {
    if (other == null)
    {
      return -1;
    }

    int comparison = 0;

    if (comparison == 0)
    {
      comparison = (severity == null? LintSeverity.low: severity)
        .compareTo(other.severity == null? LintSeverity.low: other.severity);
    }

    if (comparison == 0)
    {
      comparison = linterId.compareTo(other.linterId);
    }

    return comparison;
  }

  public void dispatch()
  {
    if (dispatch != null)
    {
      dispatch.dispatch();
    }
  }

  public InclusionRule getColumnInclusionRule()
  {
    return new RegularExpressionRule(columnInclusionPattern,
                                     columnExclusionPattern);
  }

  public Config getConfig()
  {
    return config;
  }

  public LintDispatch getDispatch()
  {
    return dispatch;
  }

  public int getDispatchThreshold()
  {
    return dispatchThreshold;
  }

  @Deprecated
  public String getId()
  {
    return getLinterId();
  }

  public String getLinterId()
  {
    return linterId;
  }

  public LintSeverity getSeverity()
  {
    return severity;
  }

  public InclusionRule getTableInclusionRule()
  {
    return new RegularExpressionRule(tableInclusionPattern,
                                     tableExclusionPattern);
  }

  public boolean isRunLinter()
  {
    return runLinter;
  }

  public void put(final String key, final String value)
  {
    config.put(key, value);
  }

  public void putAll(final Config config)
  {
    this.config.putAll(config);
  }

  public void setColumnExclusionPattern(final String columnExclusionPattern)
  {
    this.columnExclusionPattern = columnExclusionPattern;
  }

  public void setColumnInclusionPattern(final String columnInclusionPattern)
  {
    this.columnInclusionPattern = columnInclusionPattern;
  }

  public void setDispatch(final LintDispatch dispatch)
  {
    if (dispatch == null)
    {
      this.dispatch = LintDispatch.none;
    }
    else
    {
      this.dispatch = dispatch;
    }
  }

  public void setDispatchThreshold(final int dispatchThreshold)
  {
    this.dispatchThreshold = dispatchThreshold;
  }

  public void setRunLinter(final boolean runLinter)
  {
    this.runLinter = runLinter;
  }

  public void setSeverity(final LintSeverity severity)
  {
    this.severity = severity;
  }

  public void setTableExclusionPattern(final String tableExclusionPattern)
  {
    this.tableExclusionPattern = tableExclusionPattern;
  }

  public void setTableInclusionPattern(final String tableInclusionPattern)
  {
    this.tableInclusionPattern = tableInclusionPattern;
  }

  @Override
  public String toString()
  {
    return ObjectToString.toString(this);
  }

}
