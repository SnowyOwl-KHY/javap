package me.kehycs.javap.main;

public class Version {

    private int major;

    private int minor;

    public Version(int major, int minor) {
        this.major = major;
        this.minor = minor;
    }

    @Override
    public String toString() {
        return "  minor version: " + minor + "\n  major version: " + major;
    }
}
