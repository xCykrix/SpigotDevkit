package github.xCykrix;

import github.xCykrix.extendable.DevkitFullState;
import java.lang.reflect.InvocationTargetException;

public class PluginReference {
  private DevkitFullState state;

  public <T extends DevkitFullState> void create(Class<T> clazz, DevkitPlugin plugin) {
    if (this.state == null) {
      try {
        this.state = clazz.getDeclaredConstructor(DevkitPlugin.class).newInstance(plugin);
        this.state.initialize();
      } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
        throw new RuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  public <T extends DevkitFullState> T get() {
    if (this.state == null) throw new IllegalStateException("Unable to access API. Has it been initialized?");
    return (T) this.state;
  }
}
