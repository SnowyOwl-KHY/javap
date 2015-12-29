package me.kehycs.javap.util;

/**
 * Created by kehanyang on 12/29/15.
 */
public class ConvertTool {

    public static long parseNumber(byte[] bytes, int beginIndex, int endIndex) {
        long result = 0;
        for (int i = beginIndex; i < endIndex; i++) {
            byte b = bytes[i];
            result <<= 8;
            result |= b & 0xFF;
        }
        return result;
    }

    public static long parseNumber(byte[] bytes) {
        return parseNumber(bytes, 0, bytes.length);
    }

}
