package me.kehycs.javap.util;

public class Pair<T, U> {

    public T first;

    public U second;

    public Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }

    public boolean equals(Object o) {
        if (!(o instanceof Pair))
            return false;
        Pair e = (Pair) o;
        return eq(first, e.first) && eq(second, e.second);
    }

    private static boolean eq(Object o1, Object o2) {
        return o1 == null ? o2 == null : o1.equals(o2);
    }

    public int hashCode() {
        return (first == null ? 0 : first.hashCode()) ^
                (second == null ? 0 : second.hashCode());
    }

    public String toString() {
        return first + ", " + second;
    }
}
