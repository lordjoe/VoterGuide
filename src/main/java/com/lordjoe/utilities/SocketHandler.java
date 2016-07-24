package com.lordjoe.utilities;

import com.lordjoe.exceptions.*;

import java.net.*;
import java.io.*;

/**
 This class tests read and write from sockate
 */
public class SocketHandler {
    private int m_Port;
    private String m_Host;
    private Socket m_Socket;
    private OutputStream m_outStr;
    private InputStream m_inStr;
    private PrintWriter m_out;
    private LineNumberReader m_in;

    public SocketHandler() {
        m_Host = "localhost";
        m_Port = 80;
    }

    public SocketHandler(String host, int port) {
        m_Host = host;
        m_Port = port;
    }

    public void write(String s) {
        m_out.println(s);
        m_out.flush();
    }

    public String readLine() throws IOException {
        return (m_in.readLine());
    }


    public void open() {
        try {
            /* there is a but in the JDK 1.5 socket class which
             * does a DNS lookup in raw IP Addresses - this
             * forces that not to happen
             */
             InetAddress addr = Util.getINetAddress(m_Host);
            m_Socket = new Socket(addr, m_Port);
            m_outStr = m_Socket.getOutputStream();
            m_out = new PrintWriter(m_outStr);
            m_inStr = m_Socket.getInputStream();
            m_in = new LineNumberReader(new InputStreamReader(m_inStr));
        } catch (UnknownHostException ex) {
            throw new WrapperException(ex);
        } catch (IOException ex) {
            throw new WrapperException(ex);
        }


    }

    public void close() throws IOException {
        if (m_Socket != null) {
            m_out.close();
            m_out = null;
            m_in.close();
            m_in = null;
            ;
            m_Socket.close();
            m_Socket = null;
        }
    }

}
