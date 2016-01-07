package me.kehycs.javap.attribute;

import me.kehycs.javap.constantpool.ConstantPoolSource;
import me.kehycs.javap.exception.ClassFileParseException;
import me.kehycs.javap.util.ConvertTool;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public abstract class AttributeInfo {

    protected long length;

    private static Map<String, Class<? extends AttributeInfo>> typeMap = new HashMap<>();
    static {
        typeMap.put("Code", CodeAttribute.class);
    }

    protected ConstantPoolSource constantPoolSource;

    public static AttributeInfo newAttributeInfo(InputStream inputStream, ConstantPoolSource constantPoolSource) throws IOException, ClassFileParseException {
        byte[] tempData = new byte[4];

        inputStream.read(tempData, 0, 2);
        int nameIndex = (int) ConvertTool.parseNumber(tempData, 0, 2);
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
        inputStream.read(tempData);
        attributeInfo.length = ConvertTool.parseNumber(tempData);
        attributeInfo.readData(inputStream);

        return attributeInfo;
    }

    public abstract void readData(InputStream inputStream) throws IOException, ClassFileParseException;

}
