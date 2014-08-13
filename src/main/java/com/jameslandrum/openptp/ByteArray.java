package com.jameslandrum.openptp;

import java.util.Arrays;

import static com.jameslandrum.openptp.DataConversion.hexstr;

/**
 * ByteArray.java
 * Provides proper data conversion to->from Sockets
 * (C) 2014 James Landrum
 */

public class ByteArray {
    private byte[] host;

    public ByteArray(byte[] ... data) {
        this();
        add(data);
    }

    public ByteArray(byte[] data) {
        host = Arrays.copyOf(data,data.length);
    }

    public ByteArray() {
        host = new byte[0];
    }

    public static byte[] compound(byte[] ... data) {
        return new ByteArray(data).getBytes();
    }

    private void resize(int newSize) {
        host = Arrays.copyOf(host, newSize);
    }

    private void append(byte[] data) {
        int offset = host.length;
        resize(host.length + data.length);
        System.arraycopy(data, 0, host, offset, data.length);
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
        return Arrays.copyOf(host,host.length);
    }

    public void clear() {
        host = new byte[0];
    }

    @Override
    public String toString() { // TODO: Remove BaseEncoding dependency - it's Android-centric.
        return hexstr(host);
    }

    public int size() {
        return host.length;
    }
}
