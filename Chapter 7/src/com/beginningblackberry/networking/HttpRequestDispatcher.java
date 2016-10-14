package com.beginningblackberry.networking;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.io.HttpsConnection;

import net.rim.device.api.servicebook.ServiceBook;
import net.rim.device.api.servicebook.ServiceRecord;
import net.rim.device.api.system.CoverageInfo;
import net.rim.device.api.system.WLANInfo;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.Dialog;

public class HttpRequestDispatcher extends Thread {
    private String url;
    private String method; // GET or POST
    private NetworkingMainScreen screen;
    private byte[] postData;

    public HttpRequestDispatcher(String url, String method,
            NetworkingMainScreen screen, byte[] postData) {
        this.url = url;
        this.method = method;
        this.screen = screen;
        this.postData = postData;
    }

    public HttpRequestDispatcher(String url, String method,
            NetworkingMainScreen screen) {
        this.url = url;
        this.method = method;
        this.screen = screen;
    }

    private ServiceRecord getWAP2ServiceRecord() {
        ServiceBook sb = ServiceBook.getSB();
        ServiceRecord[] records = sb.getRecords();

        for (int i = 0; i < records.length; i++) {
            String cid = records[i].getCid().toLowerCase();
            String uid = records[i].getUid().toLowerCase();
            if (cid.indexOf("wptcp") != -1 && uid.indexOf("wifi") == -1
                    && uid.indexOf("mms") == -1) {
                return records[i];
            }
        }

        return null;
    }

    public void run() {
        // The following code will only build under JDE 4.5 and later
        
        try {
            String connectionParameters = "";
            // JDE 4.3 is required to get WLANInfo
            if (WLANInfo.getWLANState() == WLANInfo.WLAN_STATE_CONNECTED) {
                // Connected to a WiFi access point
                connectionParameters = ";interface=wifi";
            } else {
                int coverageStatus = CoverageInfo.getCoverageStatus();
                ServiceRecord record = getWAP2ServiceRecord();
                if (record != null
                        // In JDE 4.5 CoverageInfo changed the name of COVERAGE_CARRIER to COVERAGE_DIRECT
                        // The constant value for both is the same, '1', so you can use that to avoid any
                        // dependency on JDE 4.5
                        && (coverageStatus & CoverageInfo.COVERAGE_DIRECT) == 
                        CoverageInfo.COVERAGE_DIRECT) {
                    // Have network coverage and a WAP 2.0 service book record
                    connectionParameters = ";deviceside=true;ConnectionUID="
                            + record.getUid();
                } else if ((coverageStatus & CoverageInfo.COVERAGE_MDS) == 
                        CoverageInfo.COVERAGE_MDS) {
                    // Have an MDS service book and network coverage
                    connectionParameters = ";deviceside=false";
                } else if ((coverageStatus & CoverageInfo.COVERAGE_DIRECT) == 
                        CoverageInfo.COVERAGE_DIRECT) {
                    // Have network coverage but no WAP 2.0 service book record
                    connectionParameters = ";deviceside=true";
                }
            }

            // Pop up a dialog showing the parameters chosen
            UiApplication.getUiApplication().invokeLater(
                    new DialogRunner("Connection Params: "
                            + connectionParameters));
            
            HttpConnection connection = (HttpConnection) Connector.open(url
                    + connectionParameters);
            
            connection.setRequestMethod(method);
            if (method.equals("POST") && postData != null) {
                connection.setRequestProperty("Content-type",
                        "application/x-www-form-urlencoded");
                OutputStream requestOutput = connection.openOutputStream();
                requestOutput.write(postData);
                requestOutput.close();
            }
            int responseCode = connection.getResponseCode();
            if (connection instanceof HttpsConnection) {
                HttpsConnection secureConnection = (HttpsConnection) connection;
                String issuer = secureConnection.getSecurityInfo()
                        .getServerCertificate().getIssuer();
                UiApplication.getUiApplication().invokeLater(new DialogRunner("Secure Connection! Certificate issued by: " + issuer));
                    
                
            }
            // Really you should check for more than just HTTP_OK
            if (responseCode != HttpConnection.HTTP_OK) {
                screen.requestFailed("Unexpected response code: "
                        + responseCode);
                connection.close();
                return;
            }

            String contentType = connection.getHeaderField("Content-type");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            InputStream responseData = connection.openInputStream();
            byte[] buffer = new byte[10000];
            int bytesRead = responseData.read(buffer);
            while (bytesRead > 0) {
                baos.write(buffer, 0, bytesRead);
                bytesRead = responseData.read(buffer);
            }
            baos.close();
            connection.close();

            screen.requestSucceeded(baos.toByteArray(), contentType);
        } catch (IOException ex) {
            screen.requestFailed(ex.toString());
        }
    }

    // Utility class to display a Dialog on the UI thread
    private static class DialogRunner implements Runnable {
        private String text;

        public DialogRunner(String text) {
            this.text = text;

        }

        public void run() {
            Dialog.inform(text);
        }

    }
}
