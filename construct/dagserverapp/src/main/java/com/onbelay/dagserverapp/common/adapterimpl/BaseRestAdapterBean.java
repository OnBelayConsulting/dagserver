package com.onbelay.dagserverapp.common.adapterimpl;

import com.onbelay.dagserverapp.auth.mapper.ApplicationAuthenticationMapper;
import com.onbelay.dagserverapp.auth.user.DagUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseRestAdapterBean {
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private ApplicationAuthenticationMapper applicationAuthenticationMapper;

    protected void logUser() {
        DagUser user = applicationAuthenticationMapper.getCurrentUser();
        logger.info("Signed in user is:" + user.getName());
        logger.info("With authorities:" + user.getAuthorities());

    }

}
