package me.kehycs.javap.main;

import me.kehycs.javap.accessflag.ClassAccessFlag;
import me.kehycs.javap.constantpool.ConstantInfo;
import me.kehycs.javap.constantpool.ConstantInfoProvider;
import me.kehycs.javap.exception.ClassFileParseException;
import me.kehycs.javap.member.FieldInfo;
import me.kehycs.javap.member.MemberInfo;
import me.kehycs.javap.member.MethodInfo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Parser implements ConstantInfoProvider, ClassInfoProvider {

    private static final byte[] MAGIC_NUMBER = {(byte) 0xCA, (byte) 0xFE, (byte) 0xBA, (byte) 0xBE};

    private File classFile;

    private DataInputStream dataInputStream;

    private Version version;

    private List<ConstantInfo> constantPool = new ArrayList<>();

    private ClassAccessFlag classAccessFlag;

    private String className;

    private String superClassName;

    private List<String> interfaceNames = new ArrayList<>();

    private List<MemberInfo> memberInfoList = new ArrayList<>();

    public Parser(File file) throws FileNotFoundException {
        this(new DataInputStream(new FileInputStream(file)));
        classFile = file;
    }

    private Parser(DataInputStream dataInputStream) {
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
        int length = dataInputStream.read(tempData);
        if (length != 4) {
            throw new ClassFileParseException("Too short file.");
        }
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
            memberInfoList.add(fieldInfo);
        }
    }

    public void readMethodInfo() throws IOException, ClassFileParseException {
        int methodInfoCount = dataInputStream.readUnsignedShort();
        for (int i = 0; i < methodInfoCount; ++i) {
            MethodInfo methodInfo = new MethodInfo(dataInputStream, this, this);
            memberInfoList.add(methodInfo);
        }
    }

    public ConstantInfo getConstantInfo(int index) {
        return constantPool.get(index - 1);     // 常量池索引从1开始
    }

    private String describe() throws IOException {
        StringBuilder result = new StringBuilder();

        describeFile(result);

        describeClass(result);

        result.append(version).append("\n");
        result.append("  ").append(classAccessFlag).append("\n");

        describeConstantPool(result);

        result.append("{\n");

        describeMembers(result);

        return result.append('}').toString();
    }

    private void describeFile(StringBuilder result) throws IOException {
        result.append("Classfile ").append(classFile.getCanonicalPath()).append('\n');
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

    private void describeMembers(StringBuilder result) {
        for (int i = 0; i < memberInfoList.size(); ++i) {
            result.append(memberInfoList.get(i).getInfo());
            if (i != memberInfoList.size() - 1) {
                result.append('\n');
            }
        }
    }

    public String getClassName() {
        return className;
    }

    // for test
    public static void main(String[] args) throws IOException, ClassFileParseException {
//        Parser parser = new Parser(new File("../../../../../Test/Java/HelloWorld.class"));
        Parser parser = new Parser(new File("/Users/kehanyang/Test/Java/HelloWorld.class"));
        System.out.println(parser.parse());
        parser.close();
    }
}
