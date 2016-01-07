package me.kehycs.javap.member;

import me.kehycs.javap.accessflag.MethodAccessFlag;
import me.kehycs.javap.constantpool.ConstantPoolSource;
import me.kehycs.javap.exception.ClassFileParseException;
import me.kehycs.javap.util.ConvertTool;
import me.kehycs.javap.util.Pair;

import java.io.DataInputStream;
import java.io.IOException;

public class MethodInfo extends MemberInfo {

    public MethodInfo(DataInputStream dataInputStream, ConstantPoolSource constantPoolSource) throws IOException, ClassFileParseException {
        super(dataInputStream, constantPoolSource);
    }

    @Override
    protected void setAccessFlag(int flags) throws ClassFileParseException {
        accessFlag = new MethodAccessFlag(flags);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        result.append(accessFlag.getModifiers());

        Pair<String, String[]> methodDescriptor = ConvertTool.parseMethodDescriptor(descriptor);

        String returnType = methodDescriptor.first;
        result.append(returnType).append(' ');

        result.append(name);

        String[] parameterTypes = methodDescriptor.second;
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
