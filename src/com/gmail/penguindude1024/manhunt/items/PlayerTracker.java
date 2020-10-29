package com.gmail.penguindude1024.manhunt.items;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class PlayerTracker implements Listener {

    private ItemStack compassStack;
    private ItemMeta im;
    private ArrayList<String> lore;
    //TODO: make method to determine who are the hunters
    private HashSet<Player> hunters;
    private HashSet<Player> seekers;

    @EventHandler
    public void givePlayerCompass(PlayerJoinEvent event){
        Player p = event.getPlayer();

        if(!p.getInventory().contains(Material.COMPASS)){

            compassStack = new ItemStack(Material.COMPASS);
            im = compassStack.getItemMeta();
            lore = new ArrayList();

            lore.add("Use this compass to track players.");

            compassStack.addUnsafeEnchantment(Enchantment.LUCK, 1);

            im.setDisplayName("Player Tracker");
            im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            im.setLore(lore);

            compassStack.setItemMeta(im);

            event.getPlayer().getInventory().addItem(compassStack);
        }
    }

    @EventHandler
    public void setPlayerStatus(AsyncPlayerChatEvent event){
        Player p = event.getPlayer();
        String message = event.getMessage();

        hunters = new HashSet<>();
        seekers = new HashSet<>();

        if(message.equalsIgnoreCase("hunter") && !seekers.contains(p)){
            hunters.add(p);
            Bukkit.broadcastMessage(ChatColor.RED  + p.getDisplayName() + " has joined the hunters!");
        }
        else if(message.equalsIgnoreCase("seeker")&&!hunters.contains(p)){
            seekers.add(p);
            Bukkit.broadcastMessage(ChatColor.GREEN  + p.getDisplayName() + " has joined the seekers!");
        }
        else{
            p.sendMessage(ChatColor.RED + "You tried to join another team when you were already on one!");
        }
    }

    @EventHandler
    public void playerClickTracker(PlayerInteractEvent event){
        Player p = event.getPlayer();

        if(p.getInventory().getItemInMainHand() == compassStack){

        }
    }

    //UHCCore Code to point compass

    /*
    public void pointCompassToNextPlayer(int mode, int cooldown) {
		PlayersManager pm = GameManager.getGameManager().getPlayersManager();
		List<UhcPlayer> pointPlayers = new ArrayList<>();

		// Check cooldown
		if (cooldown != -1 && (cooldown*TimeUtils.SECOND) + compassPlayingLastUpdate > System.currentTimeMillis()){
			sendMessage(Lang.ITEMS_COMPASS_PLAYING_COOLDOWN);
			return;
		}

		switch (mode){
			case 1:
				pointPlayers.addAll(team.getOnlinePlayingMembers());
				break;
			case 2:
				pointPlayers.addAll(pm.getOnlinePlayingPlayers());
				for (UhcPlayer teamMember : team.getOnlinePlayingMembers()){
					pointPlayers.remove(teamMember);
				}
				break;
			case 3:
				pointPlayers.addAll(pm.getOnlinePlayingPlayers());
				break;
		}

		if((pointPlayers.size() == 1 && pointPlayers.get(0).equals(this)) || pointPlayers.size() == 0){
			sendMessage(ChatColor.RED+ Lang.ITEMS_COMPASS_PLAYING_ERROR);
		}else{
			int currentIndice = -1;
			for(int i = 0 ; i < pointPlayers.size() ; i++){
				if(pointPlayers.get(i).equals(compassPlayingCurrentPlayer))
					currentIndice = i;
			}

			// Switching to next player
			if(currentIndice == pointPlayers.size()-1)
				currentIndice = 0;
			else
				currentIndice++;


			// Skipping player if == this
			if(pointPlayers.get(currentIndice).equals(this))
				currentIndice++;

			// Correct indice if out of bounds
			if(currentIndice == pointPlayers.size())
				currentIndice = 0;


			// Pointing compass
			compassPlayingCurrentPlayer = pointPlayers.get(currentIndice);
			compassPlayingLastUpdate = System.currentTimeMillis();
			try {
				Player bukkitPlayer = getPlayer();
				Player bukkitPlayerPointing = compassPlayingCurrentPlayer.getPlayer();

				bukkitPlayer.setCompassTarget(bukkitPlayerPointing.getLocation());

				String message = ChatColor.GREEN+ Lang.ITEMS_COMPASS_PLAYING_POINTING.replace("%player%", compassPlayingCurrentPlayer.getName());

				if (message.contains("%distance%")){
					int distance = (int) bukkitPlayer.getLocation().distance(bukkitPlayerPointing.getLocation());
					message = message.replace("%distance%", String.valueOf(distance));
				}

				sendMessage(message);
			} catch (UhcPlayerNotOnlineException e) {
				sendMessage(ChatColor.RED+ Lang.TEAM_PLAYER_NOT_ONLINE.replace("%player%", compassPlayingCurrentPlayer.getName()));
			}
		}

	}
     */
}
