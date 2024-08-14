package github.xCykrix.plugin;

import github.xCykrix.DevkitPlugin;
import github.xCykrix.extendable.DevkitCommonState;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;

@SuppressWarnings({"unused", "Called by child applications."})
public class AdventurePlugin extends DevkitCommonState {
  private BukkitAudiences audiences;

  public AdventurePlugin(DevkitPlugin plugin) {
    super(plugin);
  }

  @Override
  public void initialize() {
    this.audiences = BukkitAudiences.create(this.plugin);
  }

  @Override
  public void shutdown() {
    this.audiences = null;
  }

  public BukkitAudiences get() {
    return audiences;
  }
}
