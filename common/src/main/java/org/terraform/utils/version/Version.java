package org.terraform.utils.version;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.terraform.coregen.NMSInjectorAbstract;
import org.terraform.main.TerraformGeneratorPlugin;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public enum Version {

    v1_21_6("v1_21_R5",15),
    v1_21_7("v1_21_R5",16),
    v1_21_8("v1_21_R5",17),
    v1_21_9("v1_21_R6",18),
    v1_21_10("v1_21_R6",19),
    v1_21_11("v1_21_R7",20),
    ;
    final String packName;
    final int priority;
    Version(String packName, int priority){
        this.packName = packName;
        this.priority = priority;
    }

    public String getSchematicHeader(){
        return this.toString().replace("v1_","").replace("_",".");
    }

    public String getPackName(){
        return packName;
    }

    public boolean isAtLeast(Version other){
        return priority >= other.priority;
    }

    public static final Version VERSION = toVersion(Bukkit.getServer().getBukkitVersion().split("-")[0]);

    /**
     * @param version a string like "1.20.4"
     * @return one of the enums. If this fails, failsafe is the
     * latest priority.
     */
    private static Version toVersion(@NotNull String version) {
        try{
            return Version.valueOf("v" + version.replace(".","_"));
        }catch(IllegalArgumentException e){
            TerraformGeneratorPlugin.logger.stdout("Unknown version " + version + ", trying failsafe.");
            Version highest = Version.v1_21_6;
            for(Version v:Version.values())
                if(v.isAtLeast(highest)) highest = v;
            return highest;
        }
    }

    public static @NotNull NMSInjectorAbstract getInjector() throws
            ClassNotFoundException,
            NoSuchMethodException,
            InvocationTargetException,
            InstantiationException,
            IllegalAccessException
    {

        String spigotAppend;
        //https://www.spigotmc.org/threads/how-do-i-detect-if-a-server-is-running-paper.499064/
        try {
            // Any other works, just the shortest I could find.
            Class.forName("com.destroystokyo.paper.ParticleBuilder");
            spigotAppend = "";
        } catch (ClassNotFoundException ignored) {
            TerraformGeneratorPlugin.logger.info("Spigot detected");
            spigotAppend = "spigot.";
            try{
                Class.forName("org.terraform." + spigotAppend + VERSION.getPackName() + ".NMSInjector")
                     .getDeclaredConstructor()
                     .newInstance();
            }catch(ClassNotFoundException ignoreAgain){
                TerraformGeneratorPlugin.logger.stdout("There was no spigot package for this version. This is fine if you are BELOW 1.21.9.");
                spigotAppend = "";
            }
        }
        return (NMSInjectorAbstract) Class.forName("org.terraform." + spigotAppend + VERSION.getPackName() + ".NMSInjector")
                                          .getDeclaredConstructor()
                                          .newInstance();
    }
}
