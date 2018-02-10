package com.teesudo.loginserver.main;

import com.codahale.metrics.MetricRegistry;
import com.hazelcast.internal.metrics.MetricsRegistry;
import com.teesudo.webservice.server.AppContext;
import com.typesafe.config.Config;
import io.vertx.ext.dropwizard.MetricsService;

import java.time.Clock;

public class LoginServerContext extends AppContext {

    public LoginServerContext(Config typeSafeConfig) {
        super(typeSafeConfig);
    }

    public LoginServerContext(Config typeSafeConfig, Clock clock) {
        super(typeSafeConfig, clock);
    }

    private MetricRegistry metricRegistry;
    private MetricsService metricsService;

    public MetricRegistry getMetricRegistry() {
        return metricRegistry;
    }

    public void setMetricsRegistry(MetricRegistry metricRegistry) {
        this.metricRegistry = metricRegistry;
    }

    public MetricsService getMetricsService() {
        return metricsService;
    }

    public void setMetricsService(MetricsService metricsService) {
        this.metricsService = metricsService;
    }
}
