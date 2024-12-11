package com.aub.backend_aub_shop.util;

import java.nio.ByteBuffer;
import java.util.UUID;

public class UUIDUtils {

    /**
     * Converts a misinterpreted String representation of a UUID back into a proper UUID object.
     *
     * @param misinterpretedId The incorrect String representation of the UUID.
     * @return The correctly parsed UUID object.
     */
    public static UUID convertMisinterpretedId(String misinterpretedId) {
        String cleanedId = misinterpretedId.replaceAll("-", ""); // Remove dashes
        byte[] bytes = new byte[16];
        for (int i = 0; i < 16; i++) {
            String byteStr = cleanedId.substring(i * 2, i * 2 + 2);
            bytes[i] = (byte) Integer.parseInt(byteStr, 16);
        }
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        long mostSigBits = bb.getLong();
        long leastSigBits = bb.getLong();
        return new UUID(mostSigBits, leastSigBits);
    }
    
    public static byte[] toBinary(UUID uuid) {
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return bb.array();
    }

    public static UUID fromBinary(byte[] bytes) {
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        return new UUID(bb.getLong(), bb.getLong());
    }
}
