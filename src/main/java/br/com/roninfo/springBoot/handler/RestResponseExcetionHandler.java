package br.com.roninfo.springBoot.handler;

import org.apache.commons.io.IOUtils;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;

@ControllerAdvice
public class RestResponseExcetionHandler extends DefaultResponseErrorHandler {
    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        System.out.println("Inside Error");
        return super.hasError(response);
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
//        System.out.println("Status: " + response.getStatusText());
//        System.out.println("Body: " + IOUtils.toString(response.getBody(), "UTF-8"));
//        super.handleError(response);
    }
}
