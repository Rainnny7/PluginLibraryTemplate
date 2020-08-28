# How to use
To use this plugin library, simply click on the **"Use this template"** button in the top right. Once done, open the project
in your IDE and setup the libraries found below. To use this library with a plugin, compile the library and then make a new
project in your IDE. Once you create a new project in your IDE, add the plugin library as a dependency **(DO NOT shade)**.

# Javadoc
https://rainnny7.github.io/PluginLibraryTemplate/

# Features
* A command system
* A protocol system (Reading and sending packets)
* A hotbar system
* A few utilities

# Libraries
* Paperspigot - 1.8x
* lombok - LATEST

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
