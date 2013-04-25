Graphite Reporter Agent
=======================

Pushes metrics stored by `Metrics <http://metrics.codahale.com/>`_ for `Cassandra <http://cassandra.apache.org/>`_ into `Graphite <http://graphite.readthedocs.org/en/latest/index.html>`_. This filters ``Connection`` metrics, per-client ``Streaming`` metrics and the ``system``, ``system_auth`` and ``system_traces`` column families.

Install
-------

Update the host/port and then:

::

  wget http://search.maven.org/remotecontent?filepath=com/yammer/metrics/metrics-graphite/2.0.3/metrics-graphite-2.0.3.jar -O /usr/share/java/metrics-graphite-2.0.3.jar
  javac -cp /usr/share/cassandra/lib/metrics-core-2.0.3.jar:/usr/share/java/metrics-graphite-2.0.3.jar com/disqus/metrics/reporter/GraphiteReporterAgent.java
  jar -cfM graphite-reporter.jar .
  mv graphite-reporter.jar /usr/share/java/

Add this to ``/etc/default/cassandra``:

::

    JVM_EXTRA_OPTS="$JVM_EXTRA_OPTS -javaagent:/usr/share/java/graphite-reporter.jar"
    EXTRA_CLASSPATH="/usr/share/java/metrics-graphite-2.0.3.jar"
