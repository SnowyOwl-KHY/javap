package me.kehycs.javap.member;

import me.kehycs.javap.accessflag.AccessFlag;
import me.kehycs.javap.attribute.AttributeInfo;
import me.kehycs.javap.constantpool.ConstantInfoProvider;
import me.kehycs.javap.exception.ClassFileParseException;
import me.kehycs.javap.main.ClassInfoProvider;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class MemberInfo {

    protected ClassInfoProvider classInfoProvider;

    protected AccessFlag accessFlag;
    protected String name;
    protected String descriptor;
    protected List<AttributeInfo> attributeInfoList = new ArrayList<>();

    public MemberInfo(DataInputStream dataInputStream, ConstantInfoProvider constantInfoProvider, ClassInfoProvider classInfoProvider) throws IOException, ClassFileParseException {
        this.classInfoProvider = classInfoProvider;

        int flags = dataInputStream.readUnsignedShort();
        setAccessFlag(flags);

        int nameIndex = dataInputStream.readUnsignedShort();
        name = constantInfoProvider.getConstantInfo(nameIndex).getRealContent();

        int descriptorIndex = dataInputStream.readUnsignedShort();
        descriptor = constantInfoProvider.getConstantInfo(descriptorIndex).getRealContent();

        int attributeCount = dataInputStream.readUnsignedShort();
        for (int i = 0; i < attributeCount; ++i) {
            AttributeInfo attributeInfo = AttributeInfo.newAttributeInfo(dataInputStream, constantInfoProvider);
            attributeInfoList.add(attributeInfo);
        }
    }

    protected abstract void setAccessFlag(int flags) throws ClassFileParseException;

    public AccessFlag getAccessFlag() {
        return accessFlag;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public String getBaseInfo() {
        StringBuilder result = new StringBuilder();
        result.append("  ").append(this).append('\n');
        result.append("    descriptor: ").append(descriptor).append('\n');
        result.append("    ").append(accessFlag).append("\n");
        return result.toString();
    }

    public String getInfo() {
        StringBuilder result = new StringBuilder();
        result.append(getBaseInfo());
        for (AttributeInfo attributeInfo : attributeInfoList) {
            result.append(attributeInfo.describe(4));
        }
        return result.toString();
    }
}
