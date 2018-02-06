package com.teesudo.loginserver.main;

import com.teesudo.webservice.server.AppContext;
import com.typesafe.config.Config;

import java.time.Clock;

public class LoginServerContext extends AppContext {

    public LoginServerContext(Config typeSafeConfig) {
        super(typeSafeConfig);
    }

    public LoginServerContext(Config typeSafeConfig, Clock clock) {
        super(typeSafeConfig, clock);
    }


}
