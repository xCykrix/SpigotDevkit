package github.xCykrix.extendable;

import github.xCykrix.DevkitPlugin;
import github.xCykrix.implementable.Initialize;
import github.xCykrix.implementable.Shutdown;

public abstract class DevkitCommonState implements Initialize, Shutdown {
  protected final DevkitPlugin plugin;

  public DevkitCommonState(DevkitPlugin plugin) {
    this.plugin = plugin;
  }
}
