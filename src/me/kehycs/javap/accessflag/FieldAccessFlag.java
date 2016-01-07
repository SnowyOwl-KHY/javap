package me.kehycs.javap.accessflag;

import me.kehycs.javap.exception.ClassFileParseException;

public class FieldAccessFlag extends AccessFlag {

    public FieldAccessFlag(int flags) throws ClassFileParseException {
        super(flags);
    }

}
