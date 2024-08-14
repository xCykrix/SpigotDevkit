package github.xCykrix;

import github.xCykrix.extendable.DevkitCommonState;
import github.xCykrix.implementable.Initialize;
import github.xCykrix.implementable.Shutdown;

import java.util.HashSet;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class DevkitPlugin extends JavaPlugin implements Initialize, Shutdown {
  private final HashSet<DevkitCommonState> registered = new HashSet<>();

  // --- PLUGIN LOGIC ---
  @Override
  public void onEnable() {
    //noinspection ResultOfMethodCallIgnored
    this.getDataFolder().mkdirs();
    this.pre();
    this.registered.forEach(Initialize::initialize);
    this.initialize();
  }

  @Override
  public void onDisable() {
    this.shutdown();
    this.registered.forEach(Shutdown::shutdown);
    this.registered.clear();
  }

  // --- LOADERS ---
  protected abstract void pre();

  @SuppressWarnings({"unused", "Called by child applications."})
  protected <T extends DevkitCommonState> T register(T state) {
    registered.add(state);
    return state;
  }
}
