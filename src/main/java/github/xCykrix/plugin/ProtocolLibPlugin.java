package github.xCykrix.plugin;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import github.xCykrix.DevkitPlugin;
import github.xCykrix.extendable.DevkitCommonState;

@SuppressWarnings({"unused", "Called by child applications."})
public class ProtocolLibPlugin extends DevkitCommonState {
  private ProtocolManager protocolManager;

  public ProtocolLibPlugin(DevkitPlugin plugin) {
    super(plugin);
  }

  @Override
  public void initialize() {
    this.protocolManager = ProtocolLibrary.getProtocolManager();
  }

  @Override
  public void shutdown() {
    this.protocolManager = null;
  }

  @SuppressWarnings({"unused", "Called by child applications."})
  public ProtocolManager get() {
    return this.protocolManager;
  }
}
