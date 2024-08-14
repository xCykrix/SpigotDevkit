package github.xCykrix.plugin;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import dev.dejvokep.boostedyaml.spigot.SpigotSerializer;
import github.xCykrix.DevkitPlugin;
import github.xCykrix.extendable.DevkitCommonState;
import github.xCykrix.helper.LanguageFile;
import github.xCykrix.records.Resource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import javax.annotation.Nullable;
import org.apache.commons.lang3.exception.ExceptionUtils;

@SuppressWarnings({"unused", "Called by child applications."})
public class ConfigurationPlugin extends DevkitCommonState {
  private final HashMap<String, YamlDocument> yamlDocumentHashMap = new HashMap<>();
  private LanguageFile languageFile;

  public ConfigurationPlugin(DevkitPlugin plugin) {
    super(plugin);
  }

  @SuppressWarnings("UnusedReturnValue")
  public ConfigurationPlugin register(Resource resource) {
    try {
      yamlDocumentHashMap.put(resource.id(), YamlDocument.create(
          resource.parentFolder() == null ? new File(this.plugin.getDataFolder(), resource.id()) : new File(this.plugin.getDataFolder() + "/" + resource.parentFolder(), resource.id()),
          resource.resource(),
          GeneralSettings.builder().setSerializer(SpigotSerializer.getInstance()).setUseDefaults(true).build(),
          LoaderSettings.builder().setAutoUpdate(true).build(),
          DumperSettings.DEFAULT,
          UpdaterSettings.builder().setVersioning(new BasicVersioning("version")).build()
      ));
    } catch (IOException exception) {
      this.plugin.getLogger().severe("Failed to Initialize Configuration File: " + resource.id());
      this.plugin.getLogger().severe(ExceptionUtils.getStackTrace(exception));
    }
    return this;
  }

  @SuppressWarnings("UnusedReturnValue")
  public ConfigurationPlugin registerLanguageFile(InputStream inputStream) {
    this.register(new Resource("language.yml", null, inputStream));
    return this;
  }

  @Nullable
  public YamlDocument getYAMLFile(String id) {
    return this.yamlDocumentHashMap.getOrDefault(id, null);
  }

  public LanguageFile getLanguageFile() {
    if (this.languageFile == null) {
      YamlDocument yamlDocument = this.getYAMLFile("language.yml");
      if (yamlDocument == null)
        throw new IllegalStateException("Unable to access LanguageFile. LanguageFile is not initialized.");
      this.languageFile = new LanguageFile(this.plugin, this.getYAMLFile("language.yml"));
    }
    return this.languageFile;
  }

  @Override
  public void initialize() {
  }

  @Override
  public void shutdown() {
    this.yamlDocumentHashMap.clear();
  }
}
