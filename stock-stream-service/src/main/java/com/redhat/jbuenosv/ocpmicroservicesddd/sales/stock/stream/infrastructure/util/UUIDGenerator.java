package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.infrastructure.util;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.application.exception.StockApplicationException;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.util.UUID;

/**
 * Created by jlbuenosvinos.
 */
@Component
public class UUIDGenerator {

    public String getUuid() {
        MessageDigest salt = null;
        try {
            salt = MessageDigest.getInstance("SHA-256");
            salt.update(UUID.randomUUID().toString().getBytes("UTF-8"));
        }
        catch(Exception e) {
            throw new StockApplicationException(e);
        }
        return hexEncode(salt.digest());
    }

    private String hexEncode(byte[] aInput){
        StringBuilder result = new StringBuilder();
        char[] digits = {'0', '1', '2', '3', '4','5','6','7','8','9','a','b','c','d','e','f'};
        for (int idx = 0; idx < aInput.length; ++idx) {
            byte b = aInput[idx];
            result.append(digits[ (b&0xf0) >> 4 ]);
            result.append(digits[ b&0x0f]);
        }
        return result.toString();
    }
}
