Graphite Reporter Agent
=======================

Pushes metrics stored by `Metrics <http://metrics.codahale.com/>`_ for `Cassandra <http://cassandra.apache.org/>`_ into `Graphite <http://graphite.readthedocs.org/en/latest/index.html>`_. This filters ``Connection`` metrics, per-client ``Streaming`` metrics and the ``system``, ``system_auth`` and ``system_traces`` column families.

Build & Install
-------

Update the constants like host/port in ``src/main/java/com/disqus/metrics/reporter/GraphiteReporterAgent.java``

::

  mvn clean package dependency:copy-dependencies

and then copy the following resulting artifacts on your target host

* target/graphite-reporter-agent-1.0-SNAPSHOT.jar
* target/dependency/metrics-graphite-2.0.3.jar

we will assume that you copied them to */usr/share/java*

Add this to ``/etc/default/cassandra``:

::

    JVM_EXTRA_OPTS="$JVM_EXTRA_OPTS -javaagent:/usr/share/java/graphite-reporter.jar"
    EXTRA_CLASSPATH="/usr/share/java/metrics-graphite-2.0.3.jar"
