package me.kehycs.javap.attribute;

import me.kehycs.javap.constantpool.ConstantInfoProvider;
import me.kehycs.javap.exception.ClassFileParseException;
import me.kehycs.javap.util.ConvertTool;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class AttributeInfo {

    protected long length;

    private static Map<String, Class<? extends AttributeInfo>> typeMap = new HashMap<>();
    static {
        typeMap.put("Code", CodeAttribute.class);
        typeMap.put("Exception", ExceptionAttribute.class);
        typeMap.put("LineNumberTable", LineNumberTableAttribute.class);
        typeMap.put("LocalVariableTable", LocalVariableTableAttribute.class);
        typeMap.put("SourceFile", SourceFileAttribute.class);
        typeMap.put("ConstantValue", ConstantValueAttribute.class);
    }

    protected ConstantInfoProvider constantInfoProvider;

    public static AttributeInfo newAttributeInfo(DataInputStream dataInputStream, ConstantInfoProvider constantInfoProvider) throws IOException, ClassFileParseException {
        int nameIndex = dataInputStream.readUnsignedShort();
        String name = constantInfoProvider.getConstantInfo(nameIndex).getRealContent();

        Class<? extends AttributeInfo> typeClass = typeMap.get(name);
        if (typeClass == null) {
            throw new ClassFileParseException("Not supported attribute type");
        }

        AttributeInfo attributeInfo;
        try {
            attributeInfo = typeClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
        attributeInfo.constantInfoProvider = constantInfoProvider;
        attributeInfo.length = ConvertTool.readUnsignedInt(dataInputStream);
        attributeInfo.readData(dataInputStream);

        return attributeInfo;
    }

    public abstract void readData(DataInputStream dataInputStream) throws IOException, ClassFileParseException;

    public abstract String describe(int blankNumber);

}
