package me.kehycs.javap.member;

import me.kehycs.javap.accessflag.MethodAccessFlag;
import me.kehycs.javap.attribute.AttributeInfo;
import me.kehycs.javap.attribute.CodeAttribute;
import me.kehycs.javap.constantpool.ConstantInfoProvider;
import me.kehycs.javap.exception.ClassFileParseException;
import me.kehycs.javap.main.ClassInfoProvider;
import me.kehycs.javap.util.ConvertTool;
import me.kehycs.javap.util.Pair;

import java.io.DataInputStream;
import java.io.IOException;

public class MethodInfo extends MemberInfo {

    private String returnType;

    private String[] parameterTypes;

    public MethodInfo(DataInputStream dataInputStream, ConstantInfoProvider constantInfoProvider, ClassInfoProvider classInfoProvider) throws IOException, ClassFileParseException {
        super(dataInputStream, constantInfoProvider, classInfoProvider);
    }

    @Override
    protected void parseDescriptor(DataInputStream dataInputStream, ConstantInfoProvider constantInfoProvider) throws IOException {
        super.parseDescriptor(dataInputStream, constantInfoProvider);

        Pair<String, String[]> methodDescriptor = ConvertTool.parseMethodDescriptor(descriptor);
        returnType = methodDescriptor.first;
        parameterTypes = methodDescriptor.second;
    }

    @Override
    protected void additionalOperation(AttributeInfo attributeInfo) {
        if (attributeInfo instanceof CodeAttribute) {
            boolean hasArgThis = ((MethodAccessFlag) accessFlag).isStatic();
            ((CodeAttribute) attributeInfo).setArgsSize(parameterTypes.length + (hasArgThis ? 0 : 1));
        }
    }

    @Override
    protected void setAccessFlag(int flags) throws ClassFileParseException {
        accessFlag = new MethodAccessFlag(flags);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        result.append(accessFlag.getModifiers());

        result.append(returnType).append(' ');

        if (name.equals("<init>")) {
            result.append(classInfoProvider.getClassName());
        } else {
            result.append(name);
        }

        result.append('(');
        for (int i = 0; i < parameterTypes.length; ++i) {
            if (i > 0) {
                result.append(", ");
            }
            result.append(parameterTypes[i]);
        }
        result.append(");");
        return result.toString();
    }
}
