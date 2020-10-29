package com.gmail.penguindude1024.manhunt;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.penguindude1024.manhunt.items.*;

public class Manhunt extends JavaPlugin {

    @Override
    public void onEnable(){
        //Fired when the server enables the plugin
        Bukkit.getServer().getPluginManager().registerEvents(PlayerTracker, this);
    }

    @Override
    public void onDisable(){
        //Fired when the server enables the plugin
    }

}
