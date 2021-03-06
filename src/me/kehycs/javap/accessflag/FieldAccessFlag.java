package me.kehycs.javap.accessflag;

import me.kehycs.javap.exception.ClassFileParseException;
import me.kehycs.javap.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class FieldAccessFlag extends AccessFlag {

    private static final int PUBLIC = 0x0001;
    private static final int PRIVATE = 0x0002;
    private static final int PROTECTED = 0x0004;
    private static final int STATIC = 0x0008;
    private static final int FINAL = 0x0010;
    private static final int VOLATILE = 0x0040;
    private static final int TRANSIENT = 0x0080;
    private static final int SYNTHETIC = 0x1000;
    private static final int ENUM = 0x4000;

    private static final int UNDEFINED_FLAGS = 0xAF20;  // 1010 1111 0010 0000

    private static final List<Pair<Integer, String>> accessFlagNameList = new ArrayList<>();

    static {
        accessFlagNameList.add(new Pair<>(PUBLIC, "ACC_PUBLIC"));
        accessFlagNameList.add(new Pair<>(PRIVATE, "ACC_PRIVATE"));
        accessFlagNameList.add(new Pair<>(PROTECTED, "ACC_PROTECTED"));
        accessFlagNameList.add(new Pair<>(STATIC, "ACC_STATIC"));
        accessFlagNameList.add(new Pair<>(FINAL, "ACC_FINAL"));
        accessFlagNameList.add(new Pair<>(VOLATILE, "ACC_VOLATILE"));
        accessFlagNameList.add(new Pair<>(TRANSIENT, "ACC_TRANSIENT"));
        accessFlagNameList.add(new Pair<>(SYNTHETIC, "ACC_SYNTHETIC"));
        accessFlagNameList.add(new Pair<>(ENUM, "ACC_ENUM"));
    }

    public FieldAccessFlag(int flags) throws ClassFileParseException {
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
        } else if (isVolatile()) {
            result.append("volatile ");
        }

        if (isTransient()) {
            result.append("transient ");
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

    private boolean isStatic() {
        return (flags & STATIC) != 0;
    }

    private boolean isFinal() {
        return (flags & FINAL) != 0;
    }

    private boolean isVolatile() {
        return (flags & VOLATILE) != 0;
    }

    private boolean isTransient() {
        return (flags & TRANSIENT) != 0;
    }

    private boolean isSynthetic() {
        return (flags & SYNTHETIC) != 0;
    }

    private boolean isEnum() {
        return (flags & ENUM) != 0;
    }

}
