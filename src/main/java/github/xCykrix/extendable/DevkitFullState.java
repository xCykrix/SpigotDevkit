package github.xCykrix.extendable;

import github.xCykrix.DevkitPlugin;
import github.xCykrix.implementable.Initialize;
import github.xCykrix.implementable.Shutdown;

public abstract class DevkitFullState extends DevkitSimpleState implements Initialize, Shutdown {
  public DevkitFullState(DevkitPlugin plugin) {
    super(plugin);
  }
}
