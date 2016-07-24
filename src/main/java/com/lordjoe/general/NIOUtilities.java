package com.lordjoe.general;

import com.lordjoe.lang.*;
import com.lordjoe.exceptions.*;
import com.lordjoe.utilities.*;

import java.io.*;
import java.nio.*;
import java.nio.charset.*;
import java.nio.channels.*;
import java.net.*;
import java.util.*;


/**
 * com.lordjoe.general.NIOUtilities
 *
 * @author Steve Lewis
 * @date Mar 2, 2006
 */
public class NIOUtilities
{
    public final static NIOUtilities[] EMPTY_ARRAY = {};
    public final static Class THIS_CLASS = NIOUtilities.class;
    public static final int CONNECTION_TIMEOUT = 500;
    public static final int DEFAULT_READ_TIMEOUT = 500;

    public static final Charset DEFAULT_CHARSET =
            Charset.forName("us-ascii");
    public static final CharsetDecoder DECODER = DEFAULT_CHARSET.newDecoder();


    public static void writeString(SocketChannel out, String data)
    {
        writeBytes(out, data.getBytes());
    }

    public static void writeBytes(SocketChannel channel, byte[] data)
    {
        // Write continuously on the buffer
        try {
            ByteBuffer buffer = null;
            buffer =
                    ByteBuffer.wrap(data);
            channel.write(buffer);
            buffer.clear();
        }
        catch (IOException e) {
            throw new WrappingException(e);
        }
    }

    public static String readWithTimeOut(SocketChannel channel,
                                         Selector selector, long timeout)
    {
        boolean readDone = false;
        long end = TimeOps.now() + timeout;
        StringBuffer sb = new StringBuffer();
        CharBuffer charBuffer = CharBuffer.allocate(1024);
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

        if (channel.isBlocking())
            throw new IllegalArgumentException("readWithTimeOut requires a non-blocking channel");
        // Write continuously on the buffer
        try {
            while (!readDone) {
                while (selector.select(DEFAULT_READ_TIMEOUT) > 0) {
                    // Get set of ready objects
                    ThreadUtilities.guaranteeNoInterrupt();
                    long del =  end - TimeOps.now();
                    if (del < 0)
                        throw new ResponseTimeoutException("Socket Read Timeout", timeout);
                    Set readyKeys = selector.selectedKeys();
                    Iterator readyItor = readyKeys.iterator();

                    // Walk through set
                    while (readyItor.hasNext()) {

                        // Get key from set
                        SelectionKey key =
                                (SelectionKey) readyItor.next();

                        // Remove current entry
                        readyItor.remove();

//                        // Get channel
//                        SocketChannel keyChannel =
//                                (SocketChannel) key.channel();
//
//                        if (channel.isReadable()) {
                           int nRead = channel.read(buffer);
                            buffer.flip();
                              // Decode buffer
                            DECODER.decode(buffer, charBuffer, false);
                            String read = charBuffer.toString();
                            for (int i = 0; i < read.length(); i++) {
                                char c = read.charAt(i);
                                if (c == 0)
                                    continue;
                                if (c == 255)
                                    continue;
                                if (c == '\r') {
                                     readDone = true;
                                 }
                                if (c == '\n') {
                                     readDone = true;
                                 }
                                 else {
                                    sb.append(c);
                                }

                            }
                            // Clear for next pass
                            buffer.clear();
                            charBuffer.clear();


                        }
                    }

            }

            return sb.toString();
        }
        catch (IOException e) {
            throw new WrappingException(e);
        }
    }


    /**
     * create a Soccket channel
     *
     * @param host
     * @param port
     * @return
     */
    public static SocketChannel buildSocketChannel(String host, int port)
    {
        try {
            SocketChannel ret = SocketChannel.open();
            ret.configureBlocking(false);
            GeneralUtilities.printString("connecting to host " + host + " on port " + port);
            /* there is a but in the JDK 1.5 socket class which
            * does a DNS lookup in raw IP Addresses - this
            * forces that not to happen
            */
            InetAddress addr = StringOps.getINetAddress(host);
            SocketAddress so = new InetSocketAddress(addr, port);
            Selector selector = Selector.open();

            // Record to selector (OP_CONNECT type)
            SelectionKey clientKey = ret.register(selector, SelectionKey.OP_CONNECT);
            ret.connect(so);
            //Create selector

            // Waiting for the connection
            while (selector.select(CONNECTION_TIMEOUT) > 0) {

                // Get keys
                Set keys = selector.selectedKeys();
                Iterator i = keys.iterator();

                // For each key...
                while (i.hasNext()) {
                    SelectionKey key = (SelectionKey) i.next();

                    // Remove the current key
                    i.remove();

                    // Get the socket channel held by the key
                    SocketChannel channel = (SocketChannel) key.channel();

                    // Attempt a connection
                    if (key.isConnectable()) {

                        // Connection OK
                        GeneralUtilities.printString("Server Found");

                        // Close pending connections
                        if (channel.isConnectionPending())
                            channel.finishConnect();
                    }
                }
            }
            return ret;
        }
        catch (IOException ex) {
            throw new WrapperException(ex);
        }
    }
}
