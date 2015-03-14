# Technologies #

MemWords is developed in Java.
It uses [Stripes](http://www.stripesframework.org/) as its UI framework, combined with [Sitemesh](http://www.opensymphony.com/sitemesh/) for the page layout, and [JQuery](http://jquery.com/) as Javascript library.

MemWords also uses a dependency injection framework: [Guice](http://code.google.com/p/google-guice/). Guice is integrated into Stripes using [stripes-guicer](http://code.google.com/p/stripes-guicer/).

The project is built using [Ant](http://ant.apache.org).

Unit tests are developed with [JUnit](http://junit.org/) and [EasyMock](http://easymock.org/), and integration tests with [HtmlUnit](http://htmlunit.sourceforge.net/).

The quality of the Java code is controlled by [PMD](http://pmd.sourceForge.net/), [Checkstyle](http://checkstyle.sourceforge.net/) and [FindBugs](http://findbugs.sourceforge.net/).

The quality of the JavaScript code is controlled by [JSLint](http://www.jslint.com/), integrated into the build process thanks to [jslint4java](http://code.google.com/p/jslint4java/).

Source code coverage is measured using [Cobertura](http://cobertura.sourceforge.net/).