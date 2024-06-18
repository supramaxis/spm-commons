package me.spm.commons.compatibility;

import org.bukkit.Bukkit;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum Internals {

    /**
     * Represents upcoming CraftBukkit versions.
     * <br>
     * toString() returns the actual internals version instead of "NEW"
     */
    NEW(true),
    v1_21_R1(true),
    v1_20_R3(true),
    v1_20_R2(true),
    v1_20_R1(true),
    v1_19_R3(true),
    v1_19_R2(true),
    v1_19_R1(true),
    /**
     * Represents an implementation other than CraftBukkit.
     */
    UNKNOWN(false);

    private final boolean craftBukkitInternals;

    Internals(boolean craftBukkitInternals) {
        this.craftBukkitInternals = craftBukkitInternals;
    }

    /**
     * Returns if the server uses CraftBukkit internals
     *
     * @return true if the server uses CraftBukkit internals
     */
    public boolean useCraftBukkitInternals() {
        return craftBukkitInternals;
    }

    @Override
    public String toString() {
        if (this == NEW) {
            return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        } else {
            return name();
        }
    }

    /* Statics */
    /**
     * Contains all values of this enumeration.
     */
    public static final Set<Internals> INDEPENDENT = new HashSet<>(Arrays.asList(Internals.values()));

    /**
     * Returns a Set of all internals that are equal to or higher than the environment version
     *
     * @param internals the oldest internals in the Set
     * @return a Set of all internals that are equal to or higher than the environment version
     */
    public static Set<Internals> addHigher(Internals internals) {
        Internals[] values = values();
        return new HashSet<>(Arrays.asList(values).subList(0, internals.ordinal() + 1));
    }

    /**
     * Returns if the environment version is equal to or higher than the provided internals version
     *
     * @param internals the minimum internals version to check
     * @return if the environment version is equal to or higher than the provided internals version
     */

    public static boolean isAtLeast(Internals internals) {
        return addHigher(internals).contains(CompatibilityHandler.getInstance().getInternals());
    }

    public static boolean isAtMost(Internals internals) {
        return internals == CompatibilityHandler.getInstance().getInternals() || !isAtLeast(internals);
    }
}
