package me.kehycs.javap.util;

import java.util.HashMap;
import java.util.Map;

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

    private static final Map<Character, String> descriptorMap = new HashMap<>();

    static {
        descriptorMap.put('B', "byte");
        descriptorMap.put('C', "char");
        descriptorMap.put('D', "double");
        descriptorMap.put('F', "float");
        descriptorMap.put('I', "int");
        descriptorMap.put('J', "long");
        descriptorMap.put('S', "short");
        descriptorMap.put('Z', "boolean");
        descriptorMap.put('V', "void");
    }

    public static String convertSingleDescriptor(String singleDescriptor) {
        int arrayDimensionCount = 0;
        for (; arrayDimensionCount < singleDescriptor.length() && singleDescriptor.charAt(arrayDimensionCount) == '['; ++arrayDimensionCount)
            ;
        StringBuilder result = new StringBuilder();
        char type = singleDescriptor.charAt(arrayDimensionCount);
        if (type != 'L') {
            result.append(descriptorMap.get(type));
        } else {
            result.append(singleDescriptor.substring(arrayDimensionCount + 1));
            if (result.charAt(result.length() - 1) == ';') {
                result.deleteCharAt(result.length() - 1);
            }
        }
        while (arrayDimensionCount-- > 0) {
            result.append("[]");
        }
        return result.toString().replace('/', '.');
    }

}
