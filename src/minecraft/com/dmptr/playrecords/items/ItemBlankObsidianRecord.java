package com.dmptr.playrecords.items;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.dmptr.playrecords.CommonProxy;
import com.dmptr.playrecords.PlayRecords;

public class ItemBlankObsidianRecord extends Item {
    public ItemBlankObsidianRecord(int id) {
        super(id);

        // Set the item name.
        this.setItemName("blankObsidianDisc");
        // Set the item sprite.
        this.setIconIndex(255);
        // Configure creative tab.
        this.setCreativeTab(PlayRecords.tabDiscs);
    }

    @Override
    public EnumRarity getRarity(ItemStack par1ItemStack) {
        return EnumRarity.rare;
    }

    public String getTextureFile() {
        return CommonProxy.ITEMS_PNG;
    }
}
