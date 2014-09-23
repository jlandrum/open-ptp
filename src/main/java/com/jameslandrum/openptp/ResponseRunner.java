package com.jameslandrum.openptp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.jameslandrum.openptp.DataConversion.*;

public class ResponseRunner {
    List<Object> mObjectList;
    boolean mIsValid;

    public boolean isValid() {
        return mIsValid;
    }
    public Object[] createObjectArray() {
        return mObjectList.toArray(new Object[mObjectList.size()]);
    }

    public ResponseRunner(PtpResponse response, DataType ... dataTypes) {
        mObjectList = new ArrayList<>();
        byte[] data = response.getData().getBytes();

        try {
            int off = 0;
            for (DataType type : dataTypes) {
                switch (type) {
                    case UINT16:
                        int twobyte = i16l(Arrays.copyOfRange(data, off, off + 2));
                        mObjectList.add(twobyte);
                        off += 2;
                        break;
                    case UINT32:
                        int fourbyte = i32l(Arrays.copyOfRange(data, off, off + 4));
                        mObjectList.add(fourbyte);
                        off += 4;
                        break;
                    case STRING:
                        int len = data[off] * 2;
                        off += 1;
                        String string = str16lz(Arrays.copyOfRange(data, off, off + len));
                        off += len;
                        mObjectList.add(string);
                        break;
                    case UINT32_ARRAY:
                        int count = i32l(Arrays.copyOfRange(data, off, off + 4));
                        off += 4;
                        int[] items = new int[count];
                        for (int i = 0; i < count; i++) {
                            items[i] = i16l(Arrays.copyOfRange(data, off, off + 2));
                            off += 2;
                        }
                        mObjectList.add(items);
                        break;
                }
            }
        } catch (IndexOutOfBoundsException e) {
            mObjectList.clear();
            mIsValid = false;
        }

        mIsValid = true;
    }
}
