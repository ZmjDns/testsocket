package com.zmj.mvp.testsocket.utils;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Zmj
 * @date 2019/1/10
 */
public class AddDataTool {

    private String urlString ;

    private final String ALLBOUNDARY = java.util.UUID.randomUUID().toString();
    private final String _boundary = "----" + ALLBOUNDARY;
    private final String boundary = "--" + _boundary;
    private static final String END = "\r\n";
    String BOUNDARY = _boundary;
    String PREFIX = "--", LINEND = "\r\n";
    String MULTIPART_FROM_DATA = "multipart/form-data";
    String CHARSET = "UTF-8";

    public AddDataTool(String urlString) {
        this.urlString = urlString;
    }

    public DataOutputStream getDataPut() throws Exception {
        HttpURLConnection connection;
        URL url = new URL(urlString);

        connection = (HttpURLConnection) url.openConnection();

        connection.setRequestProperty("Charset", "UTF-8");
        connection.setRequestProperty("Connection", "close");
        connection.setRequestProperty("Content-Type", MULTIPART_FROM_DATA + ";boundary=" + BOUNDARY);
        connection.connect();

        connection.setRequestMethod("POST");

        return new DataOutputStream(connection.getOutputStream());
    }


}
