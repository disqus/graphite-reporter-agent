/*
 *  Copyright 2013 DISQUS
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package com.disqus.metrics.reporter;

import java.net.InetAddress;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.HashSet;

import com.yammer.metrics.Metrics;
import com.yammer.metrics.core.Metric;
import com.yammer.metrics.core.MetricName;
import com.yammer.metrics.core.MetricPredicate;
import com.yammer.metrics.reporting.GraphiteReporter;


public class GraphiteReporterAgent {
    static MetricPredicate CASS_PREDICATE = new MetricPredicate() {
        // List of CFs to ignore
        private HashSet<String> ignore_cfs = new HashSet<String>(Arrays.asList(
            "system",
            "system_auth",
            "system_traces"
        ));

        @Override
        public boolean matches(MetricName name, Metric metric) {
            if (name.getType().equals("Connection") ||
                    (name.getType().equals("ColumnFamily") &&
                        ignore_cfs.contains(name.getScope().split("\\.")[0])) ||
                    (name.getType().equals("Streaming") && name.hasScope())) {
                return false;
            }
            return true;
        }
    };

    public static void premain(String agentArgs) throws Exception {
        String hostname = InetAddress.getLocalHost().getHostName();

        long period = 10;
        TimeUnit unit = TimeUnit.SECONDS;
        String host = "graphite.example.com";
        int port = 2003;
        String prefix = "cassandra." + hostname.split("\\.")[0];

        GraphiteReporter.enable(Metrics.defaultRegistry(), period, unit, host, port, prefix, CASS_PREDICATE);
    }
}
