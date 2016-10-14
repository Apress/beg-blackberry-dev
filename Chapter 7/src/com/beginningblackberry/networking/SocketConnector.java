package com.beginningblackberry.networking;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.SocketConnection;

public class SocketConnector extends Thread {
	private String host;
	private NetworkingMainScreen screen;


	public SocketConnector(String host, NetworkingMainScreen screen) {
		this.host = host;
		this.screen = screen;
	}
	
	public void run() {
		try {
			SocketConnection connection = (SocketConnection)Connector.open("socket://" + host + ":80");
			OutputStream out = connection.openOutputStream();
			InputStream in = connection.openInputStream();
			
			// Standard HTTP GET request all in text
			// Only the required Host header, no body
			String request = "GET / HTTP/1.1\r\n" +
			"Host:" + host + "\r\n" +
			"\r\n" +
			"\r\n";
			out.write(request.getBytes());
			out.flush();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			
			int firstByte = in.read();
			if (firstByte >= 0) {
				baos.write((byte)firstByte);
				int bytesAvailable = in.available();
				while(bytesAvailable > 0) {
					byte[] buffer = new byte[bytesAvailable];
					in.read(buffer);
					baos.write(buffer);
					bytesAvailable = in.available();
				}
			}
			baos.close();
			connection.close();
			
			screen.requestSucceeded(baos.toByteArray(), "text/plain");
		} catch (IOException ex) {
			screen.requestFailed(ex.getMessage());
		}
	}

}
