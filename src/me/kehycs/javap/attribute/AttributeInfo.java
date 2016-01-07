package me.kehycs.javap.attribute;

import me.kehycs.javap.constantpool.ConstantPoolSource;
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
    }

    protected ConstantPoolSource constantPoolSource;

    public static AttributeInfo newAttributeInfo(DataInputStream dataInputStream, ConstantPoolSource constantPoolSource) throws IOException, ClassFileParseException {
        int nameIndex = dataInputStream.readUnsignedShort();
        String name = constantPoolSource.getConstantInfo(nameIndex).getRealContent();

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
        attributeInfo.constantPoolSource = constantPoolSource;
        attributeInfo.length = ConvertTool.readUnsignedInt(dataInputStream);
        attributeInfo.readData(dataInputStream);

        return attributeInfo;
    }

    public abstract void readData(DataInputStream dataInputStream) throws IOException, ClassFileParseException;

}
