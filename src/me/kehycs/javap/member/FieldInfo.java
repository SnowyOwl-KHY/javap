package me.kehycs.javap.member;

import me.kehycs.javap.accessflag.FieldAccessFlag;
import me.kehycs.javap.constantpool.ConstantInfoProvider;
import me.kehycs.javap.exception.ClassFileParseException;
import me.kehycs.javap.main.ClassInfoProvider;
import me.kehycs.javap.util.ConvertTool;

import java.io.DataInputStream;
import java.io.IOException;

public class FieldInfo extends MemberInfo {

    public FieldInfo(DataInputStream dataInputStream, ConstantInfoProvider constantInfoProvider, ClassInfoProvider classInfoProvider) throws IOException, ClassFileParseException {
        super(dataInputStream, constantInfoProvider, classInfoProvider);
    }

    @Override
    protected void setAccessFlag(int flags) throws ClassFileParseException {
        accessFlag = new FieldAccessFlag(flags);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        result.append(accessFlag.getModifiers());
        result.append(ConvertTool.parseSingleDescriptor(descriptor)).append(' ');
        result.append(name).append(';');

        return result.toString();
    }
}
