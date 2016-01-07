package me.kehycs.javap;

import me.kehycs.javap.accessflag.FieldAccessFlag;
import me.kehycs.javap.attribute.AttributeInfo;
import me.kehycs.javap.constantpool.ConstantPoolSource;
import me.kehycs.javap.exception.ClassFileParseException;
import me.kehycs.javap.util.ConvertTool;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FieldInfo {

    private FieldAccessFlag accessFlag;

    private String name;

    private String descriptor;

    private List<AttributeInfo> attributeInfoList = new ArrayList<>();

    public FieldInfo(DataInputStream dataInputStream, ConstantPoolSource constantPool) throws IOException, ClassFileParseException {
        int flags = dataInputStream.readUnsignedShort();
        accessFlag = new FieldAccessFlag(flags);

        int nameIndex = dataInputStream.readUnsignedShort();
        name = constantPool.getConstantInfo(nameIndex).getRealContent();

        int descriptorIndex = dataInputStream.readUnsignedShort();
        descriptor = constantPool.getConstantInfo(descriptorIndex).getRealContent();

        int attributeCount = dataInputStream.readUnsignedShort();
        for (int i = 0; i < attributeCount; ++i) {
            AttributeInfo attributeInfo = AttributeInfo.newAttributeInfo(dataInputStream, constantPool);
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
