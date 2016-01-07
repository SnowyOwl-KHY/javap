package me.kehycs.javap.constantpool;

import me.kehycs.javap.exception.ClassFileParseException;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class ConstantInfo {

    private static final Map<Integer, Class<? extends ConstantInfo>> typeMap = new HashMap<>();
    static {
        typeMap.put(1, UTF8Constant.class);
        typeMap.put(7, ClassConstant.class);
        typeMap.put(10, MethodRefConstant.class);
        typeMap.put(12, NameAndTypeConstant.class);
    }

    protected ConstantInfoProvider constantInfoProvider;

    public static ConstantInfo newConstantInfo(DataInputStream dataInputStream, ConstantInfoProvider constantPool) throws IOException, ClassFileParseException {
        int tag = dataInputStream.read();
        Class<? extends ConstantInfo> typeClass = typeMap.get(tag);
        if (typeClass == null) {
            throw new ClassFileParseException("Not supported constant type.");
        }
        ConstantInfo constantInfo;
        try {
            constantInfo = typeClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
        constantInfo.constantInfoProvider = constantPool;
        constantInfo.readData(dataInputStream);

        return constantInfo;
    }

    public abstract void readData(DataInputStream dataInputStream) throws IOException;

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
