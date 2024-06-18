package me.spm.commons.compatibility;

public class CompatibilityHandler {
    private static CompatibilityHandler instance;

    private final Version version;
    private final boolean spigot;
    private final boolean paper;

    private CompatibilityHandler() {
        instance = this;

        ClassLoader classLoader = this.getClass().getClassLoader();
        version = Version.getByServer();
        spigot = classLoader.getDefinedPackage("org.spigotmc") != null;
        paper = classLoader.getDefinedPackage("com.destroystokyo.paper") != null;
    }

    /**
     * Creates a new instance if the statically saved instance is null
     *
     * @return the CompatibilityHandler instance
     */
    public static CompatibilityHandler getInstance() {
        if (instance == null) {
            new CompatibilityHandler();
        }
        return instance;
    }

    public Version getVersion() {
        return version;
    }

    public boolean isSpigot() {
        return spigot;
    }

    public boolean isPaper() {
        return paper;
    }

    public Internals getInternals() {
        return version.getCraftBukkitInternals();
    }

}
