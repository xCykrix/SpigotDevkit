package github.xCykrix.plugin;

import github.xCykrix.DevkitPlugin;
import github.xCykrix.extendable.DevkitFullState;
import java.io.File;
import java.util.Date;
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
      String backup = "h2store-" + new Date().getTime() + ".backup";
      if (file.exists()) {
        file.renameTo(new File(this.plugin.getDataFolder() + "/" + backup));
      }
      this.plugin.getLogger().warning("Failed to load h2store. Database corrupted or v1 database format was provided.");
      this.plugin.getLogger().warning("Please report this to Plugin Author. Database has been saved as '" + backup + "' and regenerated.");
      this.plugin.getLogger().warning(ExceptionUtils.getMessage(exception));
      this.plugin.getLogger().warning("Migration from v1 to v2: https://github.com/manticore-projects/H2MigrationTool");
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
