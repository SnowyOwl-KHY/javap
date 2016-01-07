package me.kehycs.javap.util;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public static long readUnsignedInt(DataInputStream dataInputStream) throws IOException {
        long higherPart = dataInputStream.readUnsignedShort();
        long lowerPart = dataInputStream.readUnsignedShort();
        return (higherPart << 16) + (lowerPart << 16);
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

    public static String parseSingleDescriptor(String singleDescriptor) {
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

    public static Pair<String, String[]> parseMethodDescriptor(String methodDescriptor) {
        int rightBracketIndex = methodDescriptor.indexOf(')');

        String returnTypeDescriptor = methodDescriptor.substring(rightBracketIndex + 1);

        String parameterTypeDescriptor = methodDescriptor.substring(1, rightBracketIndex);
        List<String> parameterTypeList = new ArrayList<>();
        for (int i = 0; i < parameterTypeDescriptor.length(); ++i) {
            int beginIndex = i;
            while (parameterTypeDescriptor.charAt(i) == '[') {
                ++i;
            }
            if (parameterTypeDescriptor.charAt(i) == 'L') {
                ++i;
                while (parameterTypeDescriptor.charAt(i) != ';') {
                    ++i;
                }
            }
            parameterTypeList.add(parseSingleDescriptor(parameterTypeDescriptor.substring(beginIndex, i)));
        }
        String[] parameterTypeArray = parameterTypeList.toArray(new String[0]);
        return new Pair<>(returnTypeDescriptor, parameterTypeArray);
    }

}
