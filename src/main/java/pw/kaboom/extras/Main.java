package pw.kaboom.extras;

import java.util.Collections;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.block.BlockFace;
import org.bukkit.plugin.java.JavaPlugin;

import pw.kaboom.extras.commands.*;
import pw.kaboom.extras.modules.block.BlockCheck;
import pw.kaboom.extras.modules.block.BlockPhysics;
import pw.kaboom.extras.modules.entity.EntityExplosion;
import pw.kaboom.extras.modules.entity.EntityKnockback;
import pw.kaboom.extras.modules.entity.EntitySpawn;
import pw.kaboom.extras.modules.entity.EntityTeleport;
import pw.kaboom.extras.modules.player.PlayerChat;
import pw.kaboom.extras.modules.player.PlayerCommand;
import pw.kaboom.extras.modules.player.PlayerConnection;
import pw.kaboom.extras.modules.player.PlayerDamage;
import pw.kaboom.extras.modules.player.PlayerInteract;
import pw.kaboom.extras.modules.player.PlayerTeleport;
import pw.kaboom.extras.modules.server.ServerCommand;
import pw.kaboom.extras.modules.server.ServerPing;

public final class Main extends JavaPlugin {
	@Override
	public void onLoad() {
		/* Fill lists */
		Collections.addAll(
			BlockPhysics.blockFaces,
			BlockFace.NORTH,
			BlockFace.SOUTH,
			BlockFace.WEST,
			BlockFace.EAST,
			BlockFace.UP
		);

		/* Load missing config.yml defaults */
		getConfig().options().copyDefaults(true);
		saveConfig();
	}

	@Override
	public void onEnable() {
		/* Commands */
		this.getCommand("broadcastvanilla").setExecutor(new CommandBroadcastVanilla());
		this.getCommand("clearchat").setExecutor(new CommandClearChat());
		this.getCommand("console").setExecutor(new CommandConsole());
		this.getCommand("destroyentities").setExecutor(new CommandDestroyEntities());
		this.getCommand("enchantall").setExecutor(new CommandEnchantAll());
		this.getCommand("jumpscare").setExecutor(new CommandJumpscare());
		this.getCommand("kaboom").setExecutor(new CommandKaboom());
		this.getCommand("prefix").setExecutor(new CommandPrefix());
		this.getCommand("pumpkin").setExecutor(new CommandPumpkin());
		this.getCommand("serverinfo").setExecutor(new CommandServerInfo());
		this.getCommand("skin").setExecutor(new CommandSkin());
		this.getCommand("spawn").setExecutor(new CommandSpawn());
		this.getCommand("spidey").setExecutor(new CommandSpidey());
		this.getCommand("tellraw").setExecutor(new CommandTellraw());
		this.getCommand("unloadchunks").setExecutor(new CommandUnloadChunks());
		this.getCommand("username").setExecutor(new CommandUsername());

		ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(this, ListenerPriority.NORMAL, PacketType.Play.Client.WINDOW_CLICK) {
			@Override
			public void onPacketReceiving(PacketEvent event) {
				final int maxInventorySize = 46;
				if (event.getPacket().getIntegers().read(1) > maxInventorySize ||
						event.getPacket().getIntegers().read(1) < 0) {
					event.setCancelled(true);
				}
			}
		});

		/* Block-related modules */
		this.getServer().getPluginManager().registerEvents(new BlockCheck(), this);
		this.getServer().getPluginManager().registerEvents(new BlockPhysics(), this);

		/* Entity-related modules */
		this.getServer().getPluginManager().registerEvents(new EntityExplosion(), this);
		this.getServer().getPluginManager().registerEvents(new EntityKnockback(), this);
		this.getServer().getPluginManager().registerEvents(new EntitySpawn(), this);
		this.getServer().getPluginManager().registerEvents(new EntityTeleport(), this);

		/* Player-related modules */
		this.getServer().getPluginManager().registerEvents(new PlayerChat(), this);
		this.getServer().getPluginManager().registerEvents(new PlayerCommand(), this);
		this.getServer().getPluginManager().registerEvents(new PlayerConnection(), this);
		this.getServer().getPluginManager().registerEvents(new PlayerDamage(), this);
		this.getServer().getPluginManager().registerEvents(new PlayerInteract(), this);
		this.getServer().getPluginManager().registerEvents(new PlayerTeleport(), this);

		/* Server-related modules */
		this.getServer().getPluginManager().registerEvents(new ServerCommand(), this);
		this.getServer().getPluginManager().registerEvents(new ServerPing(), this);
	}
}
