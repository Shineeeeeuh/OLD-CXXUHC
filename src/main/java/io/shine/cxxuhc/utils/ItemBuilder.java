package io.shine.cxxuhc.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;


public class ItemBuilder {
	private static Map<Enchantment, Integer> enchantmentlevel = new HashMap<>();
	private static ArrayList<Enchantment> enchants = new ArrayList<>();
	private static Material mat1;
	private static String name1;
	private static ArrayList<String> lores1 = new ArrayList<>();
	private static short data1;
	private static int amount1;
	private static List<ItemFlag> flags = new ArrayList<>();
	private static MaterialData matdata;
	private static ItemStack it2;
	
	public ItemBuilder(Material mat) {
		mat1 = mat;
		amount1 = 1;
		data1 = (short) 0;
		lores1 = new ArrayList<>();
		flags = new ArrayList<>();
		enchants = new ArrayList<>();
		enchantmentlevel = new HashMap<>();
		matdata = null;
		name1 = null;
		it2 = null;
	}
	
	public ItemBuilder(Material mat, short data) {
		amount1 = 1;
		mat1 = mat;
		data1 = data;
		lores1 = new ArrayList<>();
		flags = new ArrayList<>();
		enchants = new ArrayList<>();
		enchantmentlevel = new HashMap<>();
		name1 = null;
		matdata = null;
		it2 = null;
	}
	
	public ItemBuilder(Material mat, int amount) {
		mat1 = mat;
		amount1 = amount;
		data1 = (short) 0;
		lores1 = new ArrayList<>();
		flags = new ArrayList<>();
		enchants = new ArrayList<>();
		enchantmentlevel = new HashMap<>();
		name1 = null;
		matdata = null;
		it2 = null;
	}
	
	public ItemBuilder(Material mat, short data, int amount) {
		amount1 = amount;
		mat1 = mat;
		data1 = data;
		lores1 = new ArrayList<>();
		flags = new ArrayList<>();
		enchants = new ArrayList<>();
		enchantmentlevel = new HashMap<>();
		name1 = null;
		matdata = null;
		it2 = null;
	}
	
	public ItemBuilder(ItemStack it) {
		amount1 = it.getAmount();
		mat1 = it.getType();
		data1 = it.getDurability();
		lores1 = new ArrayList<>();
		flags = new ArrayList<>();
		enchants = new ArrayList<>();
		enchantmentlevel = new HashMap<>();
		matdata = null;
		it2 = it;
		name1 = null;
	}
	
	public ItemBuilder setName(String name){
		name1 = name;
		return this;
	}
	
	public ItemBuilder addEnchantement(Enchantment ench, int level){
		enchants.add(ench);
		enchantmentlevel.put(ench, level);
		return this;
	}
	
	public ItemBuilder addLore(String lore){
		lores1.add(lore);
		return this;
	}
	
	public ItemBuilder addItemFlag(ItemFlag itf){
		flags.add(itf);
		return this;
	}
	
	public ItemBuilder setMaterialData(MaterialData md){
		matdata = md;
		return this;
	}
	
	public ItemStack build(){
		ItemStack it;
		if(it2 == null){
			it = new ItemStack(mat1, amount1, data1);
		}
		else{
			it = it2;
		}
		ItemMeta itm = it.getItemMeta();
		if(matdata != null){
			it.setData(matdata);
		}
		if(name1 != null){
			itm.setDisplayName(name1);
		}
		if(lores1.size() > 0){
			itm.setLore(lores1);
		}
		if(enchants.size() > 0){
			for(Enchantment ench : enchants){
				itm.addEnchant(ench, enchantmentlevel.get(ench), true);
			}
		}
		if(flags.size() > 0){
			for(ItemFlag itf : flags){
				itm.addItemFlags(itf);
			}
		}
		it.setItemMeta(itm);
		return it;
	}
}