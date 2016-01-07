package me.kehycs.javap.main;

import me.kehycs.javap.accessflag.ClassAccessFlag;
import me.kehycs.javap.constantpool.ConstantInfo;
import me.kehycs.javap.constantpool.ConstantInfoProvider;
import me.kehycs.javap.exception.ClassFileParseException;
import me.kehycs.javap.member.FieldInfo;
import me.kehycs.javap.member.MethodInfo;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parser implements ConstantInfoProvider, ClassInfoProvider {

    private static final byte[] MAGIC_NUMBER = {(byte) 0xCA, (byte) 0xFE, (byte) 0xBA, (byte) 0xBE};

    private DataInputStream dataInputStream;

    private Version version;

    private List<ConstantInfo> constantPool = new ArrayList<>();

    private ClassAccessFlag classAccessFlag;

    private String className;

    private String superClassName;

    private List<String> interfaceNames = new ArrayList<>();

    private List<FieldInfo> fieldInfoList = new ArrayList<>();

    private List<MethodInfo> methodInfoList = new ArrayList<>();

    public Parser(DataInputStream dataInputStream) {
        this.dataInputStream = dataInputStream;
    }

    public void close() throws IOException {
        dataInputStream.close();
    }

    public String parse() throws IOException, ClassFileParseException {

        readMagicNumber();

        readVersion();

        readConstantPool();

        readAccessFlag();

        readClassInfo();

        readFieldInfo();

        readMethodInfo();

        return describe();
    }

    private boolean isMagicNumber(byte[] bytes) {
        if (bytes.length != MAGIC_NUMBER.length) {
            return false;
        }
        for (int i = 0; i < bytes.length; i++) {
            if (bytes[i] != MAGIC_NUMBER[i]) {
                return false;
            }
        }
        return true;
    }

    private void readMagicNumber() throws IOException, ClassFileParseException {
        byte[] tempData = new byte[4];
        dataInputStream.read(tempData);
        if (!isMagicNumber(tempData)) {
            throw new ClassFileParseException("Magic number error.");
        }
    }

    private void readVersion() throws IOException {
        int minorVersionNumber = dataInputStream.readUnsignedShort();
        int majorVersionNumber = dataInputStream.readUnsignedShort();
        version = new Version(majorVersionNumber, minorVersionNumber);
    }

    private void readConstantPool() throws IOException, ClassFileParseException {
        int constantPoolCount = dataInputStream.readUnsignedShort() - 1;

        for (int i = 0; i < constantPoolCount; ++i) {
            ConstantInfo constantInfo = ConstantInfo.newConstantInfo(dataInputStream, this);
            constantPool.add(constantInfo);
        }
    }

    private void readAccessFlag() throws IOException, ClassFileParseException {
        int accessFlags = dataInputStream.readUnsignedShort();
        classAccessFlag = new ClassAccessFlag(accessFlags);
    }

    private void readClassInfo() throws IOException {

        int classIndex = dataInputStream.readUnsignedShort();
        className = getConstantInfo(classIndex).getRealContent();

        int superClassIndex = dataInputStream.readUnsignedShort();
        superClassName = getConstantInfo(superClassIndex).getRealContent();

        int interfaceNumber = dataInputStream.readUnsignedShort();
        for (int i = 0; i < interfaceNumber; ++i) {
            int interfaceIndex = dataInputStream.readUnsignedShort();
            interfaceNames.add(getConstantInfo(interfaceIndex).getRealContent());
        }
    }

    private void readFieldInfo() throws IOException, ClassFileParseException {
        int fieldInfoCount = dataInputStream.readUnsignedShort();
        for (int i = 0; i < fieldInfoCount; i++) {
            FieldInfo fieldInfo = new FieldInfo(dataInputStream, this, this);
            fieldInfoList.add(fieldInfo);
        }
    }

    public void readMethodInfo() throws IOException, ClassFileParseException {
        int methodInfoCount = dataInputStream.readUnsignedShort();
        for (int i = 0; i < methodInfoCount; ++i) {
            MethodInfo methodInfo = new MethodInfo(dataInputStream, this, this);
            methodInfoList.add(methodInfo);
        }
    }

    public ConstantInfo getConstantInfo(int index) {
        return constantPool.get(index - 1);     // 常量池索引从1开始
    }

    private String describe() {
        StringBuilder result = new StringBuilder();

        describeClass(result);

        result.append(version).append("\n");
        result.append("  ").append(classAccessFlag).append("\n");

        describeConstantPool(result);

        result.append("{\n");

        describeFields(result);

        describeMethods(result);

        return result.append('}').toString();
    }

    private void describeClass(StringBuilder result) {
        result.append(classAccessFlag.getModifiers()).append(className);
        if (!superClassName.equals("java/lang/Object")) {
            result.append(" extends ").append(superClassName);
        }
        if (interfaceNames.size() > 0) {
            result.append(" implements ");
            for (int i = 0; i < interfaceNames.size(); ++i) {
                if (i > 0) {
                    result.append(", ");
                }
                result.append(interfaceNames.get(i));
            }
        }
        result.append("\n");
    }

    private void describeConstantPool(StringBuilder result) {
        result.append("Constant pool:\n");
        int maxIndexLength = String.valueOf(constantPool.size()).length();
        for (int i = 1; i <= constantPool.size(); i++) {
            ConstantInfo constantInfo = getConstantInfo(i);
            result.append("  ");
            int indexLengthDiff = maxIndexLength - String.valueOf(i).length();
            while (indexLengthDiff-- > 0) {
                result.append(" ");
            }
            result.append("#").append(i).append(" = ").append(constantInfo).append("\n");
        }
    }

    private void describeFields(StringBuilder result) {
        for (FieldInfo fieldInfo : fieldInfoList) {
            result.append(fieldInfo.getBaseInfo()).append('\n');
        }
    }

    private void describeMethods(StringBuilder result) {
        for (MethodInfo methodInfo : methodInfoList) {
            result.append(methodInfo.getInfo()).append('\n');
        }
    }

    public String getClassName() {
        return className;
    }
}
