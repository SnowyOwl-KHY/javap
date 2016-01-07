package me.kehycs.javap.accessflag;

import me.kehycs.javap.exception.ClassFileParseException;
import me.kehycs.javap.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class MethodAccessFlag extends AccessFlag {

    private static final int PUBLIC = 0x0001;
    private static final int PRIVATE = 0x0002;
    private static final int PROTECTED = 0x0004;
    private static final int STATIC = 0x0008;
    private static final int FINAL = 0x0010;
    private static final int SYNCHRONIZED = 0x0020;
    private static final int BRIDGE = 0x0040;
    private static final int VARARGS = 0x0080;
    private static final int NATIVE = 0x0100;
    private static final int ABSTRACT = 0x0400;
    private static final int STRICTFP = 0x0800;
    private static final int SYNTHETIC = 0x1000;

    private static final int UNDEFINED_FLAGS = 0xE200;  // 1110 0010 0000 0000

    private static final List<Pair<Integer, String>> accessFlagNameList = new ArrayList<>();

    static {
        accessFlagNameList.add(new Pair<>(PUBLIC, "ACC_PUBLIC"));
        accessFlagNameList.add(new Pair<>(PRIVATE, "ACC_PRIVATE"));
        accessFlagNameList.add(new Pair<>(PROTECTED, "ACC_PROTECTED"));
        accessFlagNameList.add(new Pair<>(STATIC, "ACC_STATIC"));
        accessFlagNameList.add(new Pair<>(FINAL, "ACC_FINAL"));
        accessFlagNameList.add(new Pair<>(SYNCHRONIZED, "ACC_SYNCHRONIZED"));
        accessFlagNameList.add(new Pair<>(BRIDGE, "ACC_BRIDGE"));
        accessFlagNameList.add(new Pair<>(VARARGS, "ACC_VARARGS"));
        accessFlagNameList.add(new Pair<>(NATIVE, "ACC_NATIVE"));
        accessFlagNameList.add(new Pair<>(ABSTRACT, "ACC_ABSTRACT"));
        accessFlagNameList.add(new Pair<>(STRICTFP, "ACC_STRICTFP"));
        accessFlagNameList.add(new Pair<>(SYNTHETIC, "ACC_SYNTHETIC"));
    }

    public MethodAccessFlag(int flags) throws ClassFileParseException {
        super(flags);
    }

    @Override
    protected List<Pair<Integer, String>> getAccessFlagNameList() {
        return accessFlagNameList;
    }

    @Override
    protected int getUndefinedFlags() {
        return UNDEFINED_FLAGS;
    }

    @Override
    public String getModifiers() {
        StringBuilder result = new StringBuilder();

        if (isPublic()) {
            result.append("public ");
        } else if (isPrivate()) {
            result.append("private ");
        } else if (isProtected()) {
            result.append("protected ");
        }

        if (isStatic()) {
            result.append("static ");
        }

        if (isFinal()) {
            result.append("final ");
        }

        if (isSynchronized()) {
            result.append("synchronized ");
        }

        if (isNative()) {
            result.append("native ");
        }

        if (isAbstract()) {
            result.append("abstract ");
        }

        return result.toString();
    }

    private boolean isPublic() {
        return (flags & PUBLIC) != 0;
    }

    private boolean isPrivate() {
        return (flags & PRIVATE) != 0;
    }

    private boolean isProtected() {
        return (flags & PROTECTED) != 0;
    }

    public boolean isStatic() {
        return (flags & STATIC) != 0;
    }

    private boolean isFinal() {
        return (flags & FINAL) != 0;
    }

    private boolean isSynchronized() {
        return (flags & SYNCHRONIZED) != 0;
    }

    private boolean isBridge() {
        return (flags & BRIDGE) != 0;
    }

    private boolean isVarargs() {
        return (flags & VARARGS) != 0;
    }

    private boolean isNative() {
        return (flags & NATIVE) != 0;
    }

    private boolean isAbstract() {
        return (flags & ABSTRACT) != 0;
    }

    private boolean isStrictfp() {
        return (flags & STRICTFP) != 0;
    }

    private boolean isSynthetic() {
        return (flags & SYNTHETIC) != 0;
    }
}
