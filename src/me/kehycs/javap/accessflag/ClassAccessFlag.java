package me.kehycs.javap.accessflag;

import me.kehycs.javap.exception.ClassFileParseException;
import me.kehycs.javap.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class ClassAccessFlag extends AccessFlag {

    private static final int PUBLIC = 0x0001;
    private static final int FINAL = 0x0010;
    private static final int SUPER = 0x0020;
    private static final int INTERFACE = 0x0200;
    private static final int ABSTRACT = 0x0400;
    private static final int SYNTHETIC = 0x1000;
    private static final int ANNOTATION = 0x2000;
    private static final int ENUM = 0x4000;

    private static final int UNDEFINED_FLAGS = 0x89CE; // 1000 1001 1100 1110

    private static final List<Pair<Integer, String>> accessFlagNameList = new ArrayList<>();

    static {
        accessFlagNameList.add(new Pair<>(PUBLIC, "ACC_PUBLIC"));
        accessFlagNameList.add(new Pair<>(FINAL, "ACC_FINAL"));
        accessFlagNameList.add(new Pair<>(SUPER, "ACC_SUPER"));
        accessFlagNameList.add(new Pair<>(INTERFACE, "ACC_INTERFACE"));
        accessFlagNameList.add(new Pair<>(ABSTRACT, "ACC_ABSTRACT"));
        accessFlagNameList.add(new Pair<>(SYNTHETIC, "ACC_SYNTHETIC"));
        accessFlagNameList.add(new Pair<>(ANNOTATION, "ACC_ANNOTATION"));
        accessFlagNameList.add(new Pair<>(ENUM, "ACC_ENUM"));
    }

    public ClassAccessFlag(int flags) throws ClassFileParseException {
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
        }

        if (isFinal()) {
            result.append("final ");
        }

        if (isInterface()) {
            result.append("interface ");
        } else if (isAnnotation()) {
            result.append("@interface ");
        } else if (isEnum()) {
            result.append("enum ");
        } else {
            if (isAbstract()) {
                result.append("abstract ");
            }
            result.append("class ");
        }

        return result.toString();
    }

    private boolean isPublic() {
        return (flags & PUBLIC) != 0;
    }

    private boolean isFinal() {
        return (flags & FINAL) != 0;
    }

    private boolean isSuper() {
        return (flags & SUPER) != 0;
    }

    private boolean isInterface() {
        return (flags & INTERFACE) != 0;
    }

    private boolean isAbstract() {
        return (flags & ABSTRACT) != 0;
    }

    private boolean isSynthetic() {
        return (flags & SYNTHETIC) != 0;
    }

    private boolean isAnnotation() {
        return (flags & ANNOTATION) != 0;
    }

    private boolean isEnum() {
        return (flags & ENUM) != 0;
    }

}
