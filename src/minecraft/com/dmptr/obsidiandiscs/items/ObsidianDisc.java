package com.dmptr.obsidiandiscs.items;

import java.util.List;

import com.dmptr.obsidiandiscs.CommonProxy;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ObsidianDisc extends Item {

        public ObsidianDisc(int id) {
                super(id);
                
                // Constructor configuration
                setCreativeTab(CreativeTabs.tabMisc);
                setItemName("blankObsidianDisc");
                setIconCoord(5, 1);
        }
        
        @Override
        public EnumRarity getRarity(ItemStack par1ItemStack){
        	return EnumRarity.rare;
        }
        
        @Override
        @SideOnly(Side.CLIENT)
        public void addInformation(ItemStack is, EntityPlayer player, List l, boolean B){ //Additional info (eg. the names of music discs)
        	l.add("Unencoded Disc");
        }

        public String getTextureFile() {
                return CommonProxy.ITEMS_PNG;
        }
}