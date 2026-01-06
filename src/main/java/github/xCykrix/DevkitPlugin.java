package github.xCykrix;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPISpigotConfig;
import github.xCykrix.extendable.DevkitFullState;
import github.xCykrix.implementable.Initialize;
import github.xCykrix.implementable.Shutdown;

import java.util.HashSet;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class DevkitPlugin extends JavaPlugin implements Initialize, Shutdown {
  private final HashSet<DevkitFullState> registered = new HashSet<>();

  // --- PLUGIN LOGIC ---
  @Override
  public void onLoad() {
    CommandAPI.onLoad(
        new CommandAPISpigotConfig(this)
            .fallbackToLatestNMS(true));
    this.pre();
  }

  @Override
  public void onEnable() {
    this.getDataFolder().mkdirs();
    CommandAPI.onEnable();
    this.registered.forEach(Initialize::initialize);
    this.initialize();
  }

  @Override
  public void onDisable() {
    this.shutdown();
    this.registered.forEach(Shutdown::shutdown);
    CommandAPI.onDisable();
    this.registered.clear();
  }

  // --- LOADERS ---
  protected abstract void pre();

  protected <T extends DevkitFullState> T register(T state) {
    registered.add(state);
    return state;
  }
}
