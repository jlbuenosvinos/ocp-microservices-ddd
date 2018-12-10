package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.infrastructure.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.callback.*;
import javax.security.sasl.RealmCallback;
import java.io.IOException;

/**
 * Created by jlbuenosvinos.
 */

public class StockSecurityCallbackHandler implements CallbackHandler {

    public static final Logger logger = LoggerFactory.getLogger(StockSecurityCallbackHandler.class);

    private String user;
    private String password;
    private String realm;

    /**
     * Constructor
     * @param user user name
     * @param password password
     * @param realm realm
     */
    public StockSecurityCallbackHandler(String user, String password,String realm) {
        this.user = user;
        this.password = password;
        this.realm = realm;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getRealm() {
        return realm;
    }

    @Override
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {

        for (int i = 0; i < callbacks.length; i++) {
            if (callbacks[i] instanceof NameCallback) {
                NameCallback nc = (NameCallback) callbacks[i];
                nc.setName(getUser());
            } else if (callbacks[i] instanceof PasswordCallback) {
                PasswordCallback pc = (PasswordCallback) callbacks[i];
                pc.setPassword(getPassword().toCharArray());
            }
            else if (callbacks[i] instanceof RealmCallback) {
                RealmCallback rc = (RealmCallback) callbacks[i];
                rc.setText(getRealm());
            }
            else {
                throw new UnsupportedCallbackException(callbacks[i], "Unrecognized Callback");
            }
        }
    }

}

