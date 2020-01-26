package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.infrastructure.util;

import java.io.*;

/**
 * Created by jlbuenosvinos.
 */
public class FileUtils {

    /**
     * Reads the content of a file
     * @param resourcePath
     * @return content of the file
     * @throws IOException
     */
    public String readResource(String resourcePath) throws IOException {
        char[] buf = new char[1024];
        int len;

        try (InputStream is = getClass().getResourceAsStream(resourcePath)) {
            Reader reader = new InputStreamReader(is, "UTF-8");
            StringWriter writer = new StringWriter();
            while ((len = reader.read(buf)) != -1) {
                writer.write(buf, 0, len);
            }
            return writer.toString();
        }

    }

}
