package com.jameslandrum.openptp;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/**
 * DataConversion.java
 * Provides proper data conversion to->from Sockets
 * (C) 2014 James Landrum
 */

public class DataConversion {
    /**
     *  Provides int8 byte conversion
     *  @param i    Input integer to convert to bytes.
     *  @param le   Should convert to Little-Endian.
     */
    public static byte[] i16(int i,boolean le) {
        byte[] value = new byte[2];
        value[le?1:0] = (byte) ((i >> 8) & 0xff);
        value[le?0:1] = (byte) ((i) & 0xff);
        return value;
    }

    /**
     *  Shorthand for a big-endian i16 i->b
     *  @param i Input integer to convert to bytes.
     */
    public static byte[] i16(int i) {
        return i16(i,false);
    }

    /**
     *  Shorthand for a little-endian i16 i->b
     *  @param i Input integer to convert to bytes.
     */
    public static byte[] i16l(int i) {
        return i16(i,true);
    }

    /**
     *  Provides i8 byte conversion
     *  @param b    Bytes to convert to integer.
     */
    public static int i8(byte[] b) {
        return ByteBuffer.wrap(b).getChar();
    }

    /**
     *  Provides i16 byte conversion
     *  @param b    Bytes to convert to integer.
     *  @param le   Should convert to Little-Endian.
     */
    public static int i16(byte[] b,boolean le) {
        return ByteBuffer.wrap(b).order(le?ByteOrder.LITTLE_ENDIAN:ByteOrder.BIG_ENDIAN).getShort();
    }

    /**
     *  Shorthand for a big-endian i16 b->i
     *  @param b Bytes to convert to integer.
     */
    public static int i16(byte[] b) {
        return i16(b,false);
    }

    /**
     *  Shorthand for a little-endian i16 b->i
     *  @param b Bytes to convert to integer.
     */
    public static int i16l(byte[] b) {
        return i16(b,true);
    }

    /**
     *  Provides int32 byte conversion
     *  @param i    Input integer to convert to bytes.
     *  @param le   Should convert to Little-Endian.
     */
    public static byte[] i32(int i,boolean le) {
        byte[] value = new byte[4];
        value[le?3:0] = (byte) ((i >> 24) & 0xff);
        value[le?2:1] = (byte) ((i >> 16) & 0xff);
        value[le?1:2] = (byte) ((i >> 8) & 0xff);
        value[le?0:3] = (byte) ((i) & 0xff);
        return value;
    }

    /**
     *  Shorthand for a big-endian i32
     *  @param i Input integer to convert to bytes.
     */
    public static byte[] i32(int i) {
        return i32(i,false);
    }

    /**
     *  Shorthand for a little-endian i32
     *  @param i Input integer to convert to bytes.
     */
    public static byte[] i32l(int i) {
        return i32(i,true);
    }

    /**
     *  Provides int32 byte conversion
     *  @param b    Bytes to convert to integer.
     *  @param le   Should convert to Little-Endian.
     */
    public static int i32(byte[] b,boolean le) {
        return ByteBuffer.wrap(b).order(le?ByteOrder.LITTLE_ENDIAN:ByteOrder.BIG_ENDIAN).getInt();
    }

    /**
     *  Shorthand for a big-endian i32 b->i
     *  @param b Bytes to convert to integer.
     */
    public static int i32(byte[] b) {
        return i32(b,false);
    }

    /**
     *  Shorthand for a little-endian i32 b->i
     *  @param b Bytes to convert to integer.
     */
    public static int i32l(byte[] b) {
        return i32(b,true);
    }

    /**
     *  Provides int32 byte array conversion
     *  @param b    Bytes to convert to integer array.
     *  @param le   Should convert to Little-Endian.
     */
    public static int[] i32a(byte[] b,boolean le) {
        if (b.length % 4 != 0) return new int[0];                                                   // TODO: This may need to throw an exception instead.
        int[] ints = new int[b.length / 4];
        for (int i = 0; i < ints.length; i++) {
            byte[] i32 = Arrays.copyOfRange(b,i*4,(i+1)*4);
            ints[i] = (le?i32l(i32):i32(i32));
        }
        return ints;
    }

    /**
     *  Shorthand for a little-endian i32 array
     *  @param b Bytes to convert to integer array.
     */
    public static int[] i32al(byte[] b) {
        return i32a(b, true);
    }

    /**
     *  Provides int32 byte array conversion
     *  @param ia    Integer array to convert to byte array.
     *  @param le   Should convert to Little-Endian.
     */
    public static byte[] i32a(int[] ia,boolean le) {
        ByteArray array = new ByteArray();
        for (int i = 0; i < ia.length; i++) {
            array.add(le?i32l(ia[i]):i32(ia[i]));
        }
        return array.getBytes();
    }

    /**
     *  Shorthand for a little-endian i32 array to bytes
     *  @param ia Bytes to convert to integer array.
     */
    public static byte[] i32al(int[] ia) {
        return i32a(ia,true);
    }

    /**
     *  Provides 16-bit string byte conversion
     *  @param in   Input string to convert to bytes.
     *  @param zt   Should add zero-termination.
     *  @param le   Should convert to Little-Endian.
     */
    public static byte[] str16(String in, boolean zt, boolean le) {
        try {
            byte[] value = in.getBytes(le?"UTF-16LE":"UTF-16BE");
            if (zt) value = Arrays.copyOf(value, value.length + 2);
            return value;
        } catch (UnsupportedEncodingException e) {
            throw new
                  RuntimeException("Failed to convert string due to a permanent conversion error.");
        }
    }

    /**
     *  Shorthand for a zero-terminated little-endian string.
     *  @param in Input string to convert to bytes.
     */
    public static byte[] str16lz(String in) {
        return str16(in,true,true);
    }

    /**
     *  Shorthand for a non-terminated big-endian string.
     *  @param in Input string to convert to bytes.
     */
    public static byte[] str16(String in) {
        return str16(in,false,false);
    }

    /**
     *  Provides 16-bit byte to string conversion
     *  @param in   Input string to convert to bytes.
     *  @param zt   Should add zero-termination.
     *  @param le   Should convert to Little-Endian.
     */
    public static String str16(byte[] in, boolean zt, boolean le) {
        try {
            return new String(Arrays.copyOfRange(in,0,Math.max(0,in.length-(zt?2:0))),le?"UTF-16LE":"UTF-16BE");
        } catch (UnsupportedEncodingException e) {
            throw new
                  RuntimeException("Failed to convert string due to a permanent conversion error.");
        }
    }

    /**
     *  Shorthand for a zero-terminated little-endian string.
     *  @param in Input string to convert to bytes.
     */
    public static String str16lz(byte[] in) {
        return str16(in,true,true);
    }

    /**
     *  Shorthand for a non-terminated big-endian string.
     *  @param in Input string to convert to bytes.
     */
    public static String str16(byte[] in) {
        return str16(in,false,false);
    }

    /**
     *  Shorthand for a non-terminated big-endian string.
     *  @param in Input string to convert to bytes.
     */
    public static String ptp_str16(byte[] in) {
        return str16(in,false,false);
    }

    // *********************************************
    // * Snippet on Stack Overflow
    // * Link: https://stackoverflow.com/a/140861
    // *********************************************
    /**
     * Converts a HEX String to a byte array.
     * @param in String to convert.
     */
    public static byte[] hexstr(String in) {
        int len = in.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(in.charAt(i), 16) << 4)
                    + Character.digit(in.charAt(i+1), 16));
        }
        return data;
    }

    /**
     * Converts a HEX String to a byte array.
     * @param in String to convert.
     */
    public static String hexstr(byte[] in) {
        StringBuilder builder = new StringBuilder();
        for (byte b : in) {
            builder.append(String.format("%02x ", b));
        }
        return builder.toString();
    }
}
