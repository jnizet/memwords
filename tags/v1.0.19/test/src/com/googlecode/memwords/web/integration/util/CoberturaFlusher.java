package com.googlecode.memwords.web.integration.util;

import static com.googlecode.memwords.web.integration.util.IntegrationUtils.*;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;

/**
 * Class used to call a special action of the wabapp which flushes cobertura
 * @author JB
 */
public class CoberturaFlusher {

    public static void main(String[] args) throws FailingHttpStatusCodeException,
                                                  MalformedURLException,
                                                  IOException {
        WebClient wc = IntegrationUtils.startWebClient();
        Page page = wc.getPage(url("/util/IntegrationTests.action?flushCobertura="));
        String outputFileName = System.getProperty("com.googlecode.memwords.test.web.integration.datafile");
        if (outputFileName == null) {
            outputFileName = "cobertura/report/cobertura-integration.ser";
        }
        BufferedOutputStream out = null;
        try {
            out = new BufferedOutputStream(new FileOutputStream(new File(outputFileName)));
            out.write(page.getWebResponse().getContentAsBytes());
        }
        finally {
            try {
                out.close();
            }
            catch (IOException e) {
                // ignore
            }
        }
    }
}
