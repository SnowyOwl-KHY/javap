package me.kehycs.javap.constantpool;

import me.kehycs.javap.exception.ClassFileParseException;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public abstract class ConstantInfo {

    private static final Map<Integer, Class<? extends ConstantInfo>> typeMap = new HashMap<>();

    static {
        typeMap.put(1, Utf8Info.class);
        typeMap.put(7, ClassInfo.class);
        typeMap.put(10, MethodRefInfo.class);
        typeMap.put(12, NameAndTypeInfo.class);
    }

    ConstantPoolSource constantPoolSource;

    public static ConstantInfo newConstantInfo(InputStream inputStream, ConstantPoolSource constantPool) throws IOException, ClassFileParseException {
        int tag = inputStream.read();
        Class<? extends ConstantInfo> typeClass = typeMap.get(tag);
        if (typeClass == null) {
            throw new ClassFileParseException("Not supported constant type.");
        }
        ConstantInfo constantInfo = null;
        try {
            constantInfo = typeClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        constantInfo.constantPoolSource = constantPool;
        constantInfo.readData(inputStream);
        return constantInfo;
    }

    public abstract void readData(InputStream inputStream) throws IOException;

    @Override
    public String toString() {
        String realContent = getRealContent();
        String content = getContent();
        return String.format("%-16s%-16s%s", getTypeName(), content, content.equals(realContent) ? "" : "// " + getRealContent());
    }

    public abstract String getTypeName();

    public abstract String getContent();

    public abstract String getRealContent();

}
