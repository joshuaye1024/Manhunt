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

    private HashSet<Player> hunters;
    private HashSet<Player> seekers;

    @EventHandler
    public void givePlayerCompass(PlayerJoinEvent event){
        Player p = event.getPlayer();
        p.sendMessage(ChatColor.BLUE + "Type " + ChatColor.RED + "hunter " + ChatColor.BLUE + "or " + ChatColor.GREEN + "seeker " + ChatColor.BLUE + "in the chat to be assigned to the proper team!");

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

    public void pointCompassToNextPlayer(Player hunterPlayer, HashSet<Player> seekerSet){
        ArrayList<Player> seekerArr = new ArrayList<>(seekerSet);

        int seekerIndex = -1;

        // Switching to next player
        if(seekerIndex == seekerArr.size()-1)
            seekerIndex = 0;
        else
            seekerIndex++;

        // Correct indice if out of bounds
        if(seekerIndex == seekerArr.size())
            seekerIndex = 0;

        //Point compass
        Player target = seekerArr.get(seekerIndex);
        double targetDistance = hunterPlayer.getLocation().distance(target.getLocation());

        hunterPlayer.setCompassTarget(target.getLocation());

        String message = ChatColor.GREEN+ target.getName() + " is " + String.valueOf(targetDistance) + " blocks away!";

        hunterPlayer.sendMessage(message);
    }
}
