package pw.kaboom.extras;

import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.bukkit.enchantments.Enchantment;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import org.bukkit.inventory.ItemStack;

import org.bukkit.scheduler.BukkitRunnable;

import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

class CommandClearChat implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
			for (int i = 0; i < 100; ++i) {
				onlinePlayer.sendMessage("");
			}
			onlinePlayer.sendMessage(ChatColor.DARK_GREEN + "The chat has been cleared");
		}
		return true;
	}
}

class CommandConsole implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		final Player player = (Player) sender;

		if (args.length == 0) {
			player.sendMessage(ChatColor.RED + "Usage: /" + label + " <message ..>");
		} else {
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "minecraft:say " + ChatColor.translateAlternateColorCodes('&', String.join(" ", args)));
		}
		return true;
	}
}

class CommandDestroyEntities implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		final Player player = (Player) sender;

		for (World world : Bukkit.getServer().getWorlds()) {
			for (Entity entity : world.getEntities()) {
				if (entity.getType() != EntityType.PLAYER) {
					entity.remove();
				}
			}
		}
		player.sendMessage("Successfully destroyed all entities in every world");
		return true;
	}
}

class CommandEnchantAll implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		final Player player = (Player) sender;
		final ItemStack item = player.getInventory().getItemInMainHand();

		if (item.getType() == Material.AIR) {
			player.sendMessage("Please hold an item in your hand to enchant it");
		} else {
			item.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 32767);
			item.addUnsafeEnchantment(Enchantment.ARROW_FIRE, 32767);
			item.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 32767);
			item.addUnsafeEnchantment(Enchantment.ARROW_KNOCKBACK, 32767);
			item.addUnsafeEnchantment(Enchantment.BINDING_CURSE, 32767);
			item.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 32767);
			item.addUnsafeEnchantment(Enchantment.DAMAGE_ARTHROPODS, 32767);
			item.addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 32767);
			item.addUnsafeEnchantment(Enchantment.DEPTH_STRIDER, 32767);
			item.addUnsafeEnchantment(Enchantment.DIG_SPEED, 32767);
			item.addUnsafeEnchantment(Enchantment.DURABILITY, 32767);
			item.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 32767);
			item.addUnsafeEnchantment(Enchantment.FROST_WALKER, 32767);
			item.addUnsafeEnchantment(Enchantment.KNOCKBACK, 32767);
			item.addUnsafeEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 32767);
			item.addUnsafeEnchantment(Enchantment.LOOT_BONUS_MOBS, 32767);
			item.addUnsafeEnchantment(Enchantment.LUCK, 32767);
			item.addUnsafeEnchantment(Enchantment.LURE, 32767);
			item.addUnsafeEnchantment(Enchantment.MENDING, 32767);
			item.addUnsafeEnchantment(Enchantment.OXYGEN, 32767);
			item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 32767);
			item.addUnsafeEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 32767);
			item.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, 32767);
			item.addUnsafeEnchantment(Enchantment.PROTECTION_FIRE, 32767);
			item.addUnsafeEnchantment(Enchantment.PROTECTION_PROJECTILE, 32767);
			item.addUnsafeEnchantment(Enchantment.SILK_TOUCH, 32767);
			item.addUnsafeEnchantment(Enchantment.SWEEPING_EDGE, 32767);
			item.addUnsafeEnchantment(Enchantment.THORNS, 32767);
			item.addUnsafeEnchantment(Enchantment.VANISHING_CURSE, 32767);
			item.addUnsafeEnchantment(Enchantment.WATER_WORKER, 32767);
			player.sendMessage("I killed Tim.");
		}
		return true;
	}
}

class CommandHerobrine implements CommandExecutor {
	Main main;
	CommandHerobrine(Main main) {
		this.main = main;
	}

	private void spawnHerobrine(Player player) {
		final Location location = player.getLocation();

		final Player herobrine = (Player) location.getWorld().spawnEntity(
			location.add(location.getDirection().multiply(6)),
			EntityType.PLAYER
		);
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		final Player player = (Player) sender;
		final Location location = player.getLocation();

		if (args.length == 0) {
			final Player herobrine = (Player) location.getWorld().spawnEntity(
				location,
				EntityType.PLAYER
			);
		} else {
			if (args[0].equals("*") || args[0].equals("**")) {
				for (Player p : Bukkit.getOnlinePlayers()) {
					spawnHerobrine(p);
				}
				player.sendMessage("Successfully spawned Herobrine behind every player");
			} else {
				final Player target = Bukkit.getPlayer(args[0]);

				if (target != null) {
					spawnHerobrine(target);
					player.sendMessage("Successfully spawned Herobrine behind player \"" + target.getName() + "\"");
				} else {
					player.sendMessage("Player \"" + target.getName() + "\" not found");
				}
			}	
		}
		return true;
	}
}

class CommandJumpscare implements CommandExecutor {
	private void createJumpscare(Player player) {
		player.spawnParticle(Particle.MOB_APPEARANCE, player.getLocation(), 4);
		for (int i = 0; i < 10; ++i) {
			player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_SCREAM, 1, 0);
		}
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		final Player player = (Player) sender;

		if (args.length == 0) {
			player.sendMessage(ChatColor.RED + "Usage: /" + label + " <player>");
		} else {
			if (args[0].equals("*") || args[0].equals("**")) {
				for (Player p : Bukkit.getOnlinePlayers()) {
					createJumpscare(p);
				}
				player.sendMessage("Successfully created jumpscare for every player");
			} else {
				final Player target = Bukkit.getPlayer(args[0]);
				if (target != null) {
					createJumpscare(target);
					player.sendMessage("Successfully created jumpscare for player \"" + target.getName() + "\"");
				} else {
					player.sendMessage("Player \"" + target.getName() + "\" not found");
				}
			}	
		}
		return true;
	}
}

class CommandPrefix implements CommandExecutor {
	Main main;
	CommandPrefix(Main main) {
		this.main = main;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		final Player player = (Player) sender;

		if (args.length == 0) {
			player.sendMessage(ChatColor.RED + "Usage: /" + label + " <prefix|off>");
		} else if (args[0].equalsIgnoreCase("off")) {
			main.getConfig().set(player.getUniqueId().toString(), null);
			main.saveConfig();
			player.sendMessage("You no longer have a tag");
		} else {
			main.getConfig().set(player.getUniqueId().toString(), String.join(" ", args));
			main.saveConfig();
			player.sendMessage("You now have the tag: " + ChatColor.translateAlternateColorCodes('&', String.join(" ", args)));
		}
		return true;
	}
}

class CommandPumpkin implements CommandExecutor {
	private void placePumpkin(Player player) {
		player.getInventory().setHelmet(new ItemStack(Material.CARVED_PUMPKIN));
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		final Player player = (Player) sender;

		if (args.length == 0) {
			player.sendMessage(ChatColor.RED + "Usage: /" + label + " <player>");
		} else {
			if (args[0].equals("*") || args[0].equals("**")) {
				for (Player p : Bukkit.getOnlinePlayers()) {
					placePumpkin(p);
				}
				player.sendMessage("Everyone is now a pumpkin");
			} else {
				final Player target = Bukkit.getPlayer(args[0]);
				if (target != null) {
					placePumpkin(target);
					player.sendMessage("Player \"" + target.getName() + "\" is now a pumpkin");
				} else {
					player.sendMessage("Player \"" + target.getName() + "\" not found");
				}
			}	
		}
		return true;
	}
}

class CommandSkin implements CommandExecutor {
	Main main;
	CommandSkin(Main main) {
		this.main = main;
	}

	public boolean onCommand(CommandSender sender, Command command, String label, final String[] args) {
		final Player player = (Player) sender;

		if (args.length == 0) {
			player.sendMessage(ChatColor.RED + "Usage: /" + label + " <username>");
		} else {
			new BukkitRunnable() {
				public void run() {
					try {
						final String name = args[0];
						final URL nameUrl = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
						final HttpsURLConnection nameConnection = (HttpsURLConnection) nameUrl.openConnection();

						if (nameConnection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
							final InputStreamReader nameStream = new InputStreamReader(nameConnection.getInputStream());
							final String uuid = new JsonParser().parse(nameStream).getAsJsonObject().get("id").getAsString();
							nameStream.close();
							nameConnection.disconnect();

							final URL uuidUrl = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false");
							final HttpsURLConnection uuidConnection = (HttpsURLConnection) uuidUrl.openConnection();

							if (uuidConnection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
								final InputStreamReader uuidStream = new InputStreamReader(uuidConnection.getInputStream());
								final JsonObject response = new JsonParser().parse(uuidStream).getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();
								final String texture = response.get("value").getAsString();
								final String signature = response.get("signature").getAsString();
								uuidStream.close();
								uuidConnection.disconnect();

								final PlayerProfile textureProfile = player.getPlayerProfile();
								textureProfile.clearProperties();
								textureProfile.setProperty(new ProfileProperty("textures", texture, signature));

								new BukkitRunnable() {
									@Override
									public void run() {
										player.setPlayerProfile(textureProfile);
										player.sendMessage("Successfully set your skin to " + name + "'s");
									}
								}.runTask(main);
							} else {
								uuidConnection.disconnect();
								new BukkitRunnable() {
									@Override
									public void run() {
										player.sendMessage("Failed to change skin. Try again later");
									}
								}.runTask(main);
							}
						} else {
							nameConnection.disconnect();
							new BukkitRunnable() {
								@Override
								public void run() {
									player.sendMessage("A player with that username doesn't exist");
								}
							}.runTask(main);
						}
					} catch (Exception exception) {
					}
				}
			}.runTaskAsynchronously(main);
		}
		return true;
	}
}

class CommandSpawn implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		final Player player = (Player) sender;
		final World world = Bukkit.getWorld("world");
		final Location spawnLocation = world.getSpawnLocation();

		for (double y = spawnLocation.getY(); y <= 256; y++) {
			final Location yLocation = new Location(world, spawnLocation.getX(), y, spawnLocation.getZ());
			final Block coordBlock = world.getBlockAt(yLocation);

			if (!coordBlock.getType().isSolid() &&
			!coordBlock.getRelative(BlockFace.UP).getType().isSolid()) {
				player.teleport(yLocation);
				break;
			}
		}

		player.sendMessage("Successfully moved to the spawn");
		return true;
	}
}

class CommandSpidey implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		final Player player = (Player) sender;
		final Location eyePos = player.getEyeLocation();
		final Vector playerPos = new Vector(eyePos.getX(), eyePos.getY(), eyePos.getZ());
		final Vector direction = eyePos.getDirection();
		final int distance = 50;

		final BlockIterator blockIterator = new BlockIterator(player.getWorld(), playerPos, direction, 0, distance);

		while (blockIterator.hasNext()) {
			if (blockIterator.next().getType() != Material.AIR) {
				break;
			}

			blockIterator.next().setType(Material.COBWEB);
		}
		return true;
	}
}

class CommandTellraw implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		final Player player = (Player) sender;

		if (args.length == 0) {
			player.sendMessage(ChatColor.RED + "Usage: /" + label + " <message ..>");
		} else {
			for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
				onlinePlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', String.join(" ", args)));
			}
		}
		return true;
	}
}

class CommandUnloadChunks implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		final Player player = (Player) sender;

		for (World world : Bukkit.getServer().getWorlds()) {
			for (Chunk chunk : world.getLoadedChunks()) {
				chunk.unload(true);
			}
		}

		player.sendMessage("Successfully unloaded unused chunks");
		return true;
	}
}

class CommandUsername implements CommandExecutor {
	Main main;
	CommandUsername(Main main) {
		this.main = main;
	}

	public boolean onCommand(CommandSender sender, Command command, String label, final String[] args) {
		final Player player = (Player) sender;

		if (args.length == 0) {
			player.sendMessage(ChatColor.RED + "Usage: /" + label + " <username>");
		} else {
			new BukkitRunnable() {
				public void run() {
					try {
						String texture = "";
						String signature = "";

						final String namelong = ChatColor.translateAlternateColorCodes('&', String.join(" ", args));
						final String name = namelong.substring(0, Math.min(16, namelong.length()));

						final URL nameUrl = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
						final HttpsURLConnection nameConnection = (HttpsURLConnection) nameUrl.openConnection();

						if (nameConnection != null &&
							nameConnection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
							final InputStreamReader nameStream = new InputStreamReader(nameConnection.getInputStream());
							final String uuid = new JsonParser().parse(nameStream).getAsJsonObject().get("id").getAsString();
							nameStream.close();
							nameConnection.disconnect();

							final URL uuidUrl = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false");
							final HttpsURLConnection uuidConnection = (HttpsURLConnection) uuidUrl.openConnection();

							if (uuidConnection != null &&
								uuidConnection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
								final InputStreamReader uuidStream = new InputStreamReader(uuidConnection.getInputStream());
								final JsonObject response = new JsonParser().parse(uuidStream).getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();
								texture = response.get("value").getAsString();
								signature = response.get("signature").getAsString();

								uuidStream.close();
								uuidConnection.disconnect();
							}
						}

						final PlayerProfile profile = player.getPlayerProfile();
						profile.clearProperties();
						profile.setName(name);

						if (!("".equals(texture)) &&
							!("".equals(signature))) {
							profile.setProperty(new ProfileProperty("textures", texture, signature));
						}

						new BukkitRunnable() {
							@Override
							public void run() {
								player.setPlayerProfile(profile);
								player.sendMessage("Successfully set your username to \"" + name + "\"");
							}
						}.runTask(main);
					} catch (Exception exception) {
					}
				}
			}.runTaskAsynchronously(main);
		}
		return true;
	}
}
