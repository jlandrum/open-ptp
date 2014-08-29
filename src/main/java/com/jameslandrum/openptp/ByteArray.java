package com.jameslandrum.openptp;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.jameslandrum.openptp.DataConversion.hexstr;

/**
 * ByteArray.java
 * Provides proper data conversion to->from Sockets
 * (C) 2014 James Landrum
 */

public class ByteArray {
    private byte[] host;
    private int length;

    public ByteArray(byte[] ... data) {
        this();
        add(data);
    }

    public ByteArray(byte[] data) {
        host = Arrays.copyOf(data,length);
    }

    public ByteArray() {
        host = new byte[0];
    }

    public static byte[] compound(byte[] ... data) {
        return new ByteArray(data).getBytes();
    }

    private void resize(int newSize) {
        if (newSize > host.length) {
            host = Arrays.copyOf(host, newSize);
        }
        length = newSize;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    private void append(byte[] data) {
        int offset = length;
        resize(length + data.length);
        for (int i = 0; i < data.length; i++) {
            host[i + offset] = data[i];
        }
        //System.arraycopy(data, 0, host, offset, data.length);
    }

    public void add(byte[] data) {
        append(data);
    }

    public void add(byte[] ... data) {
        for (byte[] bytes : data) {
            append(bytes);
        }
    }

    public byte[] getBytes(int start, int length) {
        int offset;

        if (length <= 0) {
            offset = host.length + length;
        } else {
            offset = start+length;
        }

        return Arrays.copyOfRange(host,start,offset);
    }

    public byte[] getBytes() {
        return Arrays.copyOf(host,length);
    }

    public byte[] getBytes(int start) {
        return getBytes(start,0);
    }

    public void clear() {
        length = 0;
    }

    @Override
    public String toString() { // TODO: Remove BaseEncoding dependency - it's Android-centric.
        return hexstr(host);
    }

    public int size() {
        return length;
    }

    public static String toString(byte[] payload) {
        return hexstr(payload);
    }
}
