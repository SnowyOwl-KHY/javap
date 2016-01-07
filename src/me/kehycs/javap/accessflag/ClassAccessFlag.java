package me.kehycs.javap.accessflag;

import me.kehycs.javap.exception.ClassFileParseException;

public class ClassAccessFlag extends AccessFlag {

    public ClassAccessFlag(int flags) throws ClassFileParseException {
        super(flags);
    }

}
