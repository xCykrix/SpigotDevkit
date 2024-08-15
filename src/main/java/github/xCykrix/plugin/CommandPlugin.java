package github.xCykrix.plugin;

import dev.jorel.commandapi.CommandAPICommand;
import github.xCykrix.DevkitPlugin;
import github.xCykrix.extendable.DevkitFullState;

@SuppressWarnings({"unused", "Called by child applications."})
public class CommandPlugin extends DevkitFullState {
  public CommandPlugin(DevkitPlugin plugin) {
    super(plugin);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void shutdown() {
  }

  public CommandAPICommand create(String name) {
    return new CommandAPICommand(name);
  }

  public void register(CommandAPICommand component) {
    component.register(this.plugin);
  }
}
