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
      // Move Database to Migration Backup
      File file = new File(this.plugin.getDataFolder() + "/" + "h2store");
      String name = "h2store.restore." + (new Date()).getTime();
      if (file.exists()) {
        file.renameTo(new File(this.plugin.getDataFolder() + "/" + name));
      }

      // Attempt to Migrate
      if (ExceptionUtils.getMessage(exception).contains("write format")) {
        this.plugin.getLogger().warning("Attempting to run migrations on h2store from v1 to v3 database format. This is a one time operation.");

        return;
      }


      this.plugin.getLogger().warning("Failed to load h2store. Database corrupted or failed to migrate.");
      this.plugin.getLogger().warning("Please report this to Plugin Author. Database has been saved as 'h2store.legacy' and initialized.");
      this.plugin.getLogger().warning("This message indicates that your h2store flat file database is damaged and may require manual repairs.");
      this.plugin.getLogger().warning("Error Message: " + ExceptionUtils.getMessage(exception));
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
