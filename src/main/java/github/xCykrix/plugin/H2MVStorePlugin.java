package github.xCykrix.plugin;

import github.xCykrix.DevkitPlugin;
import github.xCykrix.extendable.DevkitFullState;
import java.io.File;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.h2.mvstore.MVStore;
import org.h2.mvstore.MVStoreException;

@SuppressWarnings({"unused", "Called by child applications."})
public class H2MVStorePlugin extends DevkitFullState {
  private MVStore store;

  public H2MVStorePlugin(DevkitPlugin plugin) {
    super(plugin);
  }

  @Override
  public void initialize() {
    tryToLoad();
    if (this.store == null) tryToLoad();
  }

  private void tryToLoad() {
    try {
      this.store = new MVStore.Builder()
          .fileName(this.plugin.getDataFolder() + "/" + "h2store")
          .open();
    } catch (MVStoreException exception) {
      File file = new File(this.plugin.getDataFolder() + "/" + "h2store");
      if (file.exists()) {
        file.renameTo(new File(this.plugin.getDataFolder() + "/" + "h2store.legacy"));
      }
      this.plugin.getLogger().warning("Failed to load h2store. Database corrupted or unsupported version.");
      this.plugin.getLogger().warning("Please report this to Plugin Author. Database has been saved as 'h2store.legacy' and regenerated.");
      this.plugin.getLogger().warning(ExceptionUtils.getMessage(exception));
      this.store = null;
    }
  }

  @Override
  public void shutdown() {
    this.store.close();
    this.store = null;
  }

  @SuppressWarnings({"unused", "Called by child applications."})
  public MVStore get() {
    return this.store;
  }
}
