package club.rigox.cherry;

import org.bukkit.plugin.java.JavaPlugin;

public final class Cherry extends JavaPlugin {
    private Cherry instance;

    @Override
    public void onEnable() {
        instance = this;

    }

}
