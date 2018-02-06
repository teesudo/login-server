package com.teesudo.loginserver.main;

import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.SharedMetricRegistries;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.io.Resources;
import com.hazelcast.internal.metrics.MetricsRegistry;
import com.teesudo.webservice.annotation.VertxWorker;
import com.teesudo.webservice.annotation.WebRouter;
import com.teesudo.webservice.server.AppConfig;
import com.teesudo.webservice.util.VertxServerBootstrapper;
import com.typesafe.config.Config;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.ext.dropwizard.DropwizardMetricsOptions;
import io.vertx.ext.dropwizard.MetricsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

import static com.teesudo.loginserver.constant.LoginServerConstants.LOGIN_SERVER_METRICS;

/**
 * login server entrance to start the web server
 */
@WebRouter(routers = {})
@VertxWorker(workers = {})
public class LoginServer {

    private final static Logger LOGGER = LoggerFactory.getLogger(LoginServer.class);

    public static void main(String[] args) throws InterruptedException {
        // etc. config/login-server-shared.conf
        Config typeSafeConfig = new AppConfig(Resources.getResource(args[0]).getPath()).getTypeSafeConfig();

        Vertx vertx = Vertx.vertx(
//                new VertxOptions()
//                        .setWorkerPoolSize(typeSafeConfig.getInt(CONFIG_WORKER_POOL_SIZE))
//                        .setEventLoopPoolSize(typeSafeConfig.getInt(CONFIG_EVENT_LOOP_SIZE))
//                        .setMetricsOptions(
//                                new DropwizardMetricsOptions()
//                                    .setJmxEnabled(true)
//                                    .setJmxDomain(JMX_DOMAIN.val())
//                                    .setEnabled(true)
//                        )
        );

        LoginServerContext loginServerContext = buildContext(vertx, typeSafeConfig);
        buildMetrics(vertx, loginServerContext);

        VertxServerBootstrapper<LoginServer, LoginServerContext> serverHelper =
                new VertxServerBootstrapper<>(LoginServer.class, LoginServerContext.class, vertx, typeSafeConfig);
        serverHelper.startHttpServer(loginServerContext);
        serverHelper.deployWorkers(loginServerContext);

        LOGGER.info("Login Server Service is UP.");

        TimeUnit.DAYS.sleep(365);

    }

    @VisibleForTesting
    static LoginServerContext buildContext(Vertx vertx, Config config) {
        LoginServerContext loginServerContext = new LoginServerContext(config);

        LOGGER.info("LoginServerContext : {}", loginServerContext);
        return loginServerContext;
    }

    @VisibleForTesting
    static void buildMetrics(Vertx vertx, LoginServerContext loginServerContext) {
        //start vertx buildin metrics
//        loginServerContext.setMetricsService(MetricsService.create(vertx));

        //start and manual report other metrics
        MetricRegistry metricsRegistry = SharedMetricRegistries.getOrCreate(LOGIN_SERVER_METRICS);
//        loginServerContext.setServiceMetricsRegistry(metricsRegistry);

        JmxReporter.forRegistry(metricsRegistry).build().start();
        LOGGER.debug("Login Server metrics registry: {}", metricsRegistry);
    }



}
