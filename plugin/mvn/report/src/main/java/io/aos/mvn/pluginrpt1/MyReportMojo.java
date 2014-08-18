/****************************************************************
 * Licensed to the AOS Community (AOS) under one or more        *
 * contributor license agreements.  See the NOTICE file         *
 * distributed with this work for additional information        *
 * regarding copyright ownership.  The AOS licenses this file   *
 * to you under the Apache License, Version 2.0 (the            *
 * "License"); you may not use this file except in compliance   *
 * with the License.  You may obtain a copy of the License at   *
 *                                                              *
 *   http://www.apache.org/licenses/LICENSE-2.0                 *
 *                                                              *
 * Unless required by applicable law or agreed to in writing,   *
 * software distributed under the License is distributed on an  *
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY       *
 * KIND, either express or implied.  See the License for the    *
 * specific language governing permissions and limitations      *
 * under the License.                                           *
 ****************************************************************/
package io.aos.mvn.pluginrpt1;

import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.maven.doxia.siterenderer.Renderer;
import org.apache.maven.project.MavenProject;
import org.apache.maven.reporting.AbstractMavenReport;
import org.apache.maven.reporting.MavenReportException;

/**
 * 
 * @goal my-report
 * @phase site
 */
public class MyReportMojo extends AbstractMavenReport {

    /**
     * Directory where reports will go.
     * 
     * @parameter expression="${project.reporting.outputDirectory}"
     * @required
     * @readonly
     */
    private String outputDirectory;

    /**
     * @parameter default-value="${project}"
     * @required
     * @readonly
     */
    private MavenProject project;

    /**
     * @component
     * @required
     * @readonly
     */
    private Renderer siteRenderer;

    protected MavenProject getProject() {
        return project;
    }

    protected String getOutputDirectory() {
        return outputDirectory;
    }

    protected Renderer getSiteRenderer() {
        return siteRenderer;
    }

    public String getDescription(Locale locale) {
        return getBundle(locale).getString("report.myreport.description");
    }

    public String getName(Locale locale) {
        System.out.println(getBundle(locale).getString("report.dashboard.name"));
        return getBundle(locale).getString("report.dashboard.name");
    }

    public String getOutputName() {
        return "my-report";
    }

    private ResourceBundle getBundle(Locale locale) {
        return ResourceBundle.getBundle("my-report", locale, this.getClass().getClassLoader());
    }

    @Override
    protected void executeReport(Locale arg0) throws MavenReportException {
        
        getSink().tableCell();
        getSink().text("some text");
        getSink().tableCell_();
        getSink().head();
        getSink().title();
        getSink().text("FIDL graph report");
        getSink().title_();
        getSink().head_();

        getSink().body();
        getSink().section1();

        getSink().sectionTitle1();
        getSink().text("FIDL automata index");
        getSink().text(getName(Locale.ENGLISH));
        getSink().sectionTitle1_();
        getSink().lineBreak();
        getSink().lineBreak();

        getSink().text("List of behavioral elements with link to graphical representation of FIDL automata.");

        getSink().lineBreak();
        getSink().section1_();
        getSink().body_();
        getSink().flush();
        getSink().close();

    }

}
