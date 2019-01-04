package br.com.roninfo.springBoot.javaclient;

import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class JavaClientTest {

    public static void main(String[] args) {
        HttpURLConnection conn = null;
        BufferedReader reader = null;

        String user = "root";
        String pass = "roninfo";

        try {
            URL url = new URL("http://localhost:8080/v1/protected/students/17");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.addRequestProperty("Authorization","Basic " + encondeUserPassBasic(user, pass));

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuilder json = new StringBuilder();

            String line;

            while((line = reader.readLine()) != null) {
                json.append(line);
            }

            System.out.println(json.toString());
        } catch (Exception exc) {
            System.out.println(exc.getMessage());
        } finally {
            IOUtils.closeQuietly(reader);

            if (conn != null) {
                conn.disconnect();
            }
        }

    }

    private static String encondeUserPassBasic(String user, String pass) {
        String userPass = user +":"+pass;

        return new String(Base64.encodeBase64(userPass.getBytes()));
    }
}
