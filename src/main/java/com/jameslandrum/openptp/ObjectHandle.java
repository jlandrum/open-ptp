package com.jameslandrum.openptp;

public class ObjectHandle {
    private int mObjectId;

    public ObjectHandle(int handle) {
        mObjectId = handle;
    }

    public int getId() {
        return mObjectId;
    }
}
