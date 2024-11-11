package github.xCykrix.helper;

import dev.dejvokep.boostedyaml.YamlDocument;
import github.xCykrix.DevkitPlugin;
import github.xCykrix.extendable.DevkitSimpleState;
import github.xCykrix.records.PlaceholderPair;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.minimessage.tag.standard.StandardTags;
import org.apache.commons.lang3.exception.ExceptionUtils;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class LanguageFile extends DevkitSimpleState {
  private final YamlDocument base;

  private final Component prefix;

  private final MiniMessage miniMessage = MiniMessage.builder()
      .tags(TagResolver.builder()
          .resolver(StandardTags.defaults())
          .build())
      .build();

  public LanguageFile(DevkitPlugin plugin, YamlDocument base) {
    super(plugin);
    this.base = base;
    this.prefix = getBaseComponentFromID("chat-prefix");
  }

  public Component getComponentFromID(String id, boolean prefix, PlaceholderPair... pairs) {
    ArrayList<TagResolver.Single> tagResolverSingles = new ArrayList<>();
    tagResolverSingles.add(
        prefix ? Placeholder.component("prefix", this.prefix) : Placeholder.component("prefix", Component.text("")));
    for (PlaceholderPair pair : pairs) {
      tagResolverSingles.add(Placeholder.component(pair.id(), pair.component()));
    }
    return this.miniMessage.deserialize(this.getDataFromID(id, false), tagResolverSingles.toArray(TagResolver[]::new));
  }

  public Component getErrorComponentFromID(String id, @Nullable Exception exception, boolean prefix) {
    return this.getComponentFromID(
        id,
        prefix,
        new PlaceholderPair("error",
            Component.text(exception != null ? ExceptionUtils.getMessage(exception) : "Missing Exception Message")),
        new PlaceholderPair("stack", Component
            .text(exception != null ? ExceptionUtils.getStackTrace(exception) : "Missing Exception Stacktrace")));
  }

  public Component getBaseComponentFromID(String id) {
    return this.miniMessage.deserialize(this.getDataFromID(id, true));
  }

  private String getDataFromID(String id, boolean base) throws RuntimeException {
    id = (!base ? "language." : "") + id;
    String result = this.base.getOptionalString(id).orElse(null);
    if (result == null)
      throw new RuntimeException("Key '" + id + "' was not located in the language file.");
    return result;
  }
}
