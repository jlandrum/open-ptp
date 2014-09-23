package com.jameslandrum.openptp;

import static com.jameslandrum.openptp.DataConversion.i16l;
import static com.jameslandrum.openptp.DataConversion.i32l;

public class PtpEvent {
    private int mTransactionId;
    private EventCode mEventCode = EventCode.Undefined;

    public enum EventCode {
        Undefined(0x4000),
        CancelTransaction(0x4001),
        ObjectAdded(0x4002),
        ObjectRemoved(0x4003),
        StoreAdded(0x4004),
        StoreRemoved(0x4005),
        DevicePropChanged(0x4006),
        ObjectInfoChanged(0x4007),
        DeviceInfoChanged(0x4008),
        RequestObjectTransfer(0x4009),
        StoreFull(0x400A),
        DeviceReset(0x400B),
        StorageInfoChanged(0x400C),
        CaptureComplete(0x400D),
        UnreportedStatus(0x400E);

        private int value;

        EventCode(int i) {
            value = i;
        }

        public static EventCode fromInt(int in) {
            for (EventCode code : EventCode.values()) {
                if (code.value == in) return code;
            }
            return Undefined;
        }
    }

    public PtpEvent(Response response) {
        if (response.getLength() < 6) return;
        mEventCode = EventCode.fromInt(i16l(response.getPayload().getBytes(0, 2)));
        mTransactionId = i32l(response.getPayload().getBytes(2, 4));
    }

    public int getTransactionId() {
        return mTransactionId;
    }

    public EventCode getEventCode() {
        return mEventCode;
    }
}
