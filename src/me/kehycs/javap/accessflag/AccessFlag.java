package me.kehycs.javap.accessflag;

import me.kehycs.javap.exception.ClassFileParseException;
import me.kehycs.javap.util.Pair;

import java.util.ArrayList;
import java.util.List;

public abstract class AccessFlag {

    protected int flags;

    public AccessFlag(int flags) throws ClassFileParseException {
        if ((flags & getUndefinedFlags()) != 0) {
            throw new ClassFileParseException("Unknown access flag.");
        }
        this.flags = flags;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("flags: ");
        boolean firstFlag = true;

        List<Pair<Integer, String>> accessFlagNameList = getAccessFlagNameList();

        for (Pair<Integer, String> flag : accessFlagNameList) {
            int flagCode = flag.first;
            if ((flags & flagCode) != 0) {
                if (firstFlag) {
                    firstFlag = false;
                } else {
                    result.append(", ");
                }
                result.append(flag.second);
            }
        }
        return result.toString();
    }

    protected abstract List<Pair<Integer, String>> getAccessFlagNameList();

    protected abstract int getUndefinedFlags();

    public abstract String getModifiers();

}
