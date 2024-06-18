package me.spm.commons.compatibility;

import org.bukkit.Bukkit;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static me.spm.commons.compatibility.Internals.*;

public enum Version {

    /**
     * Represents upcoming versions.
     * <p>
     * getCraftBukkitInternals() might return a known package version or NEW.
     */
    NEW(true, true, true, Internals.NEW),
    MC1_20_4(true, true, true, v1_20_R3),
    MC1_20_3(true, true, true, v1_20_R3),
    MC1_20_2(true, true, true, v1_20_R2),
    MC1_20_1(true, true, true, v1_20_R1),
    MC1_20(true, true, true, v1_20_R1),
    MC1_19_4(true, true, true, v1_19_R3),
    MC1_19_3(true, true, true, v1_19_R2),
    MC1_19_2(true, true, true, v1_19_R1);


    private final Internals craftBukkitInternals;

    Version(boolean uuids, boolean newMobNames, boolean newMaterials, Internals craftBukkitInternals) {
        this.craftBukkitInternals = craftBukkitInternals;
    }

    public static Version getByServer() {
        try {
            ClassLoader classLoader = Version.class.getClassLoader();

            if (classLoader.getDefinedPackage("org.bukkit.craftbukkit") != null) {
                String versionString = Bukkit.getServer().getVersion().split("\\(MC: ")[1].split("\\)")[0];
                for (Version version : Version.values()) {
                    if (version.toString().equals(versionString)) {
                        return version;
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException  e) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return NEW;
    }

    public Internals getCraftBukkitInternals() {
        if (this == NEW) {
            try {
                return Internals.valueOf(Internals.NEW.toString());
            } catch (IllegalArgumentException e) {
                return Internals.NEW;
            }
        } else {
            return craftBukkitInternals;
        }
    }

    @Override
    public String toString() {
        String[] string = super.toString().replace("_", ".").split("MC");

        if (string.length == 2) {
            return string[1];
        } else {
            return string[0];
        }
    }

    public static final Set<Version> INDEPENDENT;

    static {
        INDEPENDENT = new HashSet<>(Arrays.asList(Version.values()));
    }

    /**
     * Returns a Set of all versions that are equal to or higher than the provided version
     *
     * @param version the oldest version in the Set
     * @return a Set of all versions that are equal to or higher than the provided version
     */
    public static Set<Version> addHigher(Version version) {
        Version[] values = values();
        return new HashSet<>(Arrays.asList(values).subList(0, version.ordinal() + 1));
    }

    /**
     * Returns if the environment version is equal to or higher than the provided version
     *
     * @param version the minimum version to check
     * @return if the environment version is equal to or higher than the provided version
     */
    public static boolean isAtLeast(Version version) {
        return addHigher(version).contains(CompatibilityHandler.getInstance().getVersion());
    }

    public static boolean isAtMost(Version version) {
        return version == CompatibilityHandler.getInstance().getVersion() || !isAtLeast(version);
    }
}
