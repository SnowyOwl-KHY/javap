package me.kehycs.javap.constantpool;

import me.kehycs.javap.exception.ClassFileParseException;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kehanyang on 12/29/15.
 */
public abstract class ConstantInfo {

    private static final Map<Integer, Class<? extends ConstantInfo>> typeMap = new HashMap<>();

    static {
        typeMap.put(1, Utf8Info.class);
        typeMap.put(7, ClassInfo.class);
        typeMap.put(10, MethodRefInfo.class);
        typeMap.put(12, NameAndTypeInfo.class);
    }

    ConstantPoolSource constantPoolSource;

    public static ConstantInfo newConstantInfo(InputStream inputStream, ConstantPoolSource source) throws IOException, ClassFileParseException {
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
        constantInfo.constantPoolSource = source;
        constantInfo.readData(inputStream);
        return constantInfo;
    }

    public abstract void readData(InputStream inputStream) throws IOException;

    @Override
    public String toString() {
        return String.format("%-16s%s", getTypeName(), getContent());
    }

    public abstract String getTypeName();

    public abstract String getContent();

    public abstract String getRealContent();

}