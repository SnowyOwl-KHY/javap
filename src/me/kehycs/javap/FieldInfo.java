package me.kehycs.javap;

import me.kehycs.javap.accessflag.FieldAccessFlag;
import me.kehycs.javap.attribute.AttributeInfo;
import me.kehycs.javap.constantpool.ConstantPoolSource;
import me.kehycs.javap.exception.ClassFileParseException;
import me.kehycs.javap.util.ConvertTool;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class FieldInfo {

    private FieldAccessFlag accessFlag;

    private String name;

    private String descriptor;

    private List<AttributeInfo> attributeInfoList = new ArrayList<>();

    public FieldInfo(InputStream inputStream, ConstantPoolSource constantPool) throws IOException, ClassFileParseException {
        byte[] tempData = new byte[2];

        inputStream.read(tempData);
        int flags = (int) ConvertTool.parseNumber(tempData);
        accessFlag = new FieldAccessFlag(flags);

        inputStream.read(tempData);
        int nameIndex = (int) ConvertTool.parseNumber(tempData);
        name = constantPool.getConstantInfo(nameIndex).getRealContent();

        inputStream.read(tempData);
        int descriptorIndex = (int) ConvertTool.parseNumber(tempData);
        descriptor = constantPool.getConstantInfo(descriptorIndex).getRealContent();

        inputStream.read(tempData);
        int attributeCount = (int) ConvertTool.parseNumber(tempData);
        for (int i = 0; i < attributeCount; ++i) {
            AttributeInfo attributeInfo = AttributeInfo.newAttributeInfo(inputStream, constantPool);
            attributeInfoList.add(attributeInfo);
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(accessFlag.getFieldModifiers());
        result.append(ConvertTool.parseSingleDescriptor(descriptor)).append(' ');
        result.append(name).append(';');
        return result.toString();
    }

    public String getDescriptor() {
        return descriptor;
    }

    public FieldAccessFlag getAccessFlag() {
        return accessFlag;
    }
}
