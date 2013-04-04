package com.dmptr.playrecords;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

import com.dmptr.playrecords.items.ItemObsidianDisc;
import com.dmptr.playrecords.items.ItemObsidianRecord;

@Mod(modid="PlayRecords", name="PlayRecords", version="0.0.3")
@NetworkMod(clientSideRequired=true, serverSideRequired=false)
public class PlayRecords {
        // the instance of the mod that Forge uses.
        @Instance("PlayRecords")
        public static PlayRecords instance;

        // says where the client and server 'proxy' code is loaded.
        @SidedProxy(clientSide="com.dmptr.playrecords.client.ClientProxy", serverSide="com.dmptr.playrecords.CommonProxy")
        public static CommonProxy proxy;

        @PreInit
        public void preInit(FMLPreInitializationEvent event) {
                // if the file doesn't already exist, it will be created.
                Configuration config = new Configuration(event.getSuggestedConfigurationFile());
                config.load();

                // get options
                Property recordsInDungeonsConfig = config.get(Configuration.CATEGORY_GENERAL, "recordsInDungeons", true);
                recordsInDungeonsConfig.comment = "generate records in dungeons";
                recordsInDungeons = recordsInDungeonsConfig.getBoolean(false);

                Property recordsCraftableConfig = config.get(Configuration.CATEGORY_GENERAL, "recordsCraftable", true);
                recordsCraftableConfig.comment = "enable crafting recipes for records";
                recordsCraftable = recordsCraftableConfig.getBoolean(false);

                // get item IDs
                obsidianDiscID = config.getItem("obsidianDiscID", 22639).getInt();
                fireRecordID = config.getItem("fireRecordID", 22640).getInt();
                discordRecordID = config.getItem("discordRecordID", 22641).getInt();
                callmeRecordID = config.getItem("callmeRecordID", 22642).getInt();
                pirateRecordID = config.getItem("pirateRecordID", 22643).getInt();

                // save config
                config.save();
        }

        @Init
        public void load(FMLInitializationEvent event) {
                // add items
                obsidianDisc = new ItemObsidianDisc(obsidianDiscID);

                records.put("fireRecord", new ItemObsidianRecord(fireRecordID, "fire", "FelixMoog - We Didn't Start The Fire").setIconCoord(0, 1));
                records.put("discordRecord", new ItemObsidianRecord(discordRecordID, "discord", "FelixMoog - Discord (Remix)").setIconCoord(2, 1));
                records.put("callmeRecord", new ItemObsidianRecord(callmeRecordID, "callme", "FelixMoog - Call Me Maybe").setIconCoord(5, 1));
                records.put("pirateRecord", new ItemObsidianRecord(pirateRecordID, "pirate", "FelixMoog - He's A Pirate").setIconCoord(3, 1));

                // add record crafting if enabled
                if (recordsCraftable) {
                        // add basic crafting
                        ItemStack obsidianStack = new ItemStack(Block.obsidian);
                        ItemStack blockGoldStack = new ItemStack(Block.blockGold);

                        ItemStack obsidianDiscStack = new ItemStack(obsidianDisc);

                        ItemStack fireballChargeStack = new ItemStack(Item.fireballCharge);
                        
                        ItemStack swordSteelStack = new ItemStack(Item.swordSteel);
                        ItemStack rawFishStack = new ItemStack(Item.fishRaw);
                        ItemStack boatStack = new ItemStack(Item.boat);

                        GameRegistry.addRecipe(new ItemStack(obsidianDisc), "xxx", "xyx", "xxx", 'x', obsidianStack, 'y', blockGoldStack);
                        
                        // add record crafting
                        GameRegistry.addRecipe(new ItemStack(records.get("fireRecord")),
                        		" x ", "xox", " x ", 'o', obsidianDiscStack,
                        		'x', fireballChargeStack);
                        GameRegistry.addRecipe(new ItemStack(records.get("pirateRecord")),
                        		" x ", "fof", " b ", 'o', obsidianDiscStack,
                        		'x', swordSteelStack, 'f', rawFishStack, 'b', boatStack);
                }

                // add records to dungeon chests if enabled
                if (recordsInDungeons) {
                		for (Item value : records.values()) {
                			    ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(value), 1, 1, 5));
                		}  
                }

                // add item names to Language Registry
                LanguageRegistry.addName(records.get("fireRecord"), "Obsidian Disc");
                LanguageRegistry.addName(obsidianDisc, "Unencoded Obsidian Disc");

                proxy.registerRenderers();
        }

        @PostInit
        public void postInit(FMLPostInitializationEvent event) {
                // stub
        }

        // configuration options
        public static boolean recordsInDungeons, recordsCraftable;
        public static int obsidianDiscID, fireRecordID, discordRecordID, pirateRecordID, callmeRecordID;

        // items
        public static HashMap<String, Item> records = new HashMap();
        public static Item obsidianDisc;
}
