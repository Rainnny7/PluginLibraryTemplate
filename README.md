[![GitHub version](https://badge.fury.io/gh/Rainnny7%2FPluginLibraryTemplate.svg)](https://badge.fury.io/gh/Rainnny7%2FPluginLibraryTemplate)

# How to use
To use this plugin library, simply click on the **"Use this template"** button in the top right. Once done, open the project
in your IDE and setup the libraries found below. To use this library with a plugin, compile the library and then make a new
project in your IDE. Once you create a new project in your IDE, add the plugin library as a dependency **(DO NOT shade)**.

# Javadoc
https://rainnny7.github.io/PluginLibraryTemplate/

# Features
* A simplified command system
* A Protocol library - Reading and sending packets
* An easy to use storage solution - This makes it easier to load and save data
* A scoreboard library - This allows you to quickly create a scoreboard
* A menu library - This menu system allows you to have events per button
* A hotbar library - This is used to simply create hotbar layouts, such as for a hub
* A few utilities

# Libraries
* Paperspigot - 1.8x
* lombok - 1.18.0
* (Optional) Vault - LATEST
* gson - LATEST

# Examples

**Commands**
```java
CommandHandler.addCommand(new ExampleCommand());
CommandHandler.addArgument(ExampleCommand.class, new TestArgument());
```
```java
public class ExampleCommand {
    @Command(name = "example")
    public void onCommand(CommandProvider command) {}
}
```
```java
public class TestArgument {
    @Command(name = "test", description = "I am a test!")
    public void onCommand(CommandProvider command) {
        command.getSender().sendMessage("hi there!");
    }

    @TabComplete(name = "test")
    public List<String> onTabComplete(CommandProvider command) {
        return Arrays.asList("test", "testing", "hello", "hi");
    }
}
```

**Packets**
```java
ProtocolHandler.sendPacket(player, packetObj);
```
```java
@EventHandler
private void onClientPacket(PacketReceiveEvent event) {
    event.getPlayer().sendMessage("you sent: " + event.getPacketClass().getClass().getSimpleName());
}

@EventHandler
private void onServerPacket(PacketSendEvent event) {
    event.getPlayer().sendMessage("you received: " + event.getPacketClass().getClass().getSimpleName());
}
```

**Storage**
```yaml
# File Example
players:
  fc1d5fe7-f29b-430d-80bb-3b093a638b0f:
    name: Rainnny
    rank: OWNER
```
```java
// Loading
ConfigFile file = new ConfigFile(plugin, "players.yml");
FlatFileContainer<Account> container = new FlatFileContainer<>(file, Account.class);

container.load("players");

for (Account account : container.getValues()) {
    System.out.println("account = " + account.toString());
}
```
```java
// Saving (random UUIDs were used for an example)
ConfigFile file = new ConfigFile(plugin, "players.yml");
FlatFileContainer<Account> container = new FlatFileContainer<>(file, Account.class);

UUID uuid = UUID.randomUUID();
container.add(uuid.toString(), new Account(uuid, "Rainnny", "DEFAULT"));
container.save("players");
```
```java
@NoArgsConstructor @AllArgsConstructor @Getter
public class Account implements FlatFileToken {
    private UUID uuid;
    private String name, rank;

    @Override
    public List<String> getKeys() {
        return Arrays.asList("name", "rank");
    }

    @Override
    public Object load(String key, FlatFileEntries entries) {
        return new Account(
                UUID.fromString(key),
                String.valueOf(entries.of("name")),
                String.valueOf(entries.of("rank"))
        );
    }

    @Override
    public FlatFileEntries save() {
        return new FlatFileEntries(Arrays.asList(
                new Tuple<>("name", name),
                new Tuple<>("rank", rank)
        ));
    }

    @Override
    public String toString() {
        return "Account{" +
                "uuid=" + uuid.toString() +
                ", name='" + name + '\'' +
                ", rank='" + rank + '\'' +
                '}';
    }
}
```

**Scoreboard**
```java
// this = JavaPlugin
// 3L = delay in ticks
new ScoreboardHandler(this, ScoreboardProvider.class, 3L);
```
```java
public class ExampleScoreboard extends ScoreboardProvider {
    public ExampleScoreboard(Player player) {
        super(player);
    }

    @Override
    public String getTitle() {
        return "Title Here";
    }

    @Override
    public List<String> getLines() {
        return Arrays.asList(
                "I am",
                "a scoreboard"
        );
    }
}
```

**Menu**
```java
new ExampleMenu(player).open();
```
```java
@MenuInfo(title = "A Menu!", size = 3)
public class ExampleMenu extends Menu {
    public ExampleMenu(Player player) {
        super(player);
    }

    @Override
    public void onOpen() {
        set(0, new Button(new ItemBuilder(Material.DIRT).toItemStack()));

        set(8, new Button(new ItemBuilder(Material.WOOD).toItemStack(), event -> {
            player.sendMessage("clicked wood!");
        }));
    }
}
```

**Hotbar**
```java
HotbarManager.addHotbar(new ExampleHotbar());
```
```java
public class ExampleHotbar extends Hotbar {
    public ExampleHotbar() {
        super("Example");
        addFlag(HotbarFlag.GIVE_ON_JOIN);
    }

    @Override
    public void addItems(Player player) {
        set(0, new Button(new ItemBuilder(Material.DIRT)
                .setName("§6I am dirt!").toItemStack(), event -> {
            player.sendMessage("you clicked on a piece of dirt!");
        }));

        set(4, new Button(new ItemBuilder(Material.APPLE)
                .setName("§cApple").toItemStack()).withInteractEntityEvent(event -> {
                    player.sendMessage("you clicked on an entity: " + event.getRightClicked().getType().name());
        }));
    }
}
```