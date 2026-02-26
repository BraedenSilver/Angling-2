package net.braeden.waterlogged.item;

import net.braeden.waterlogged.entity.AnglerfishEntity;
import net.braeden.waterlogged.entity.BubbleEyeEntity;
import net.braeden.waterlogged.entity.CatfishEntity;
import net.braeden.waterlogged.entity.CrabEntity;
import net.braeden.waterlogged.entity.MahiMahiEntity;
import net.braeden.waterlogged.entity.SunfishEntity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.fish.Cod;
import net.minecraft.world.entity.animal.fish.Pufferfish;
import net.minecraft.world.entity.animal.fish.Salmon;
import net.minecraft.world.entity.animal.fish.TropicalFish;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class FishingNetItem extends Item {

    public FishingNetItem(Properties properties) {
        super(properties);
    }

    /** Returns the raw fish item for this entity, or null if it cannot be caught. */
    public static Item getRawFish(LivingEntity entity) {
        if (entity instanceof SunfishEntity) return WaterloggedItems.SUNFISH;
        if (entity instanceof MahiMahiEntity) return WaterloggedItems.RAW_MAHI_MAHI;
        if (entity instanceof CrabEntity) return WaterloggedItems.RAW_CRAB_LEGS;
        if (entity instanceof CatfishEntity) return WaterloggedItems.RAW_CATFISH;
        if (entity instanceof BubbleEyeEntity) return WaterloggedItems.RAW_BUBBLE_EYE;
        if (entity instanceof AnglerfishEntity) return WaterloggedItems.RAW_ANGLERFISH;
        if (entity instanceof Cod) return Items.COD;
        if (entity instanceof Salmon) return Items.SALMON;
        if (entity instanceof TropicalFish) return Items.TROPICAL_FISH;
        if (entity instanceof Pufferfish) return Items.PUFFERFISH;
        return null;
    }

    /** Server-side: attempts to capture the fish, give raw item, drain food, and damage the net. */
    public static void doCapture(Player player, InteractionHand hand, LivingEntity target) {
        Item rawFish = getRawFish(target);
        if (rawFish == null) return;

        ItemStack stack = player.getItemInHand(hand);
        ItemStack fishItem = new ItemStack(rawFish);
        Inventory inv = player.getInventory();
        if (inv.getSlotWithRemainingSpace(fishItem) != -1 || inv.getFreeSlot() != -1) {
            target.discard();
            inv.add(fishItem);
            drainFood(player);
            EquipmentSlot slot = hand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;
            stack.hurtAndBreak(1, player, slot);
        }
    }

    private static void drainFood(Player player) {
        FoodData food = player.getFoodData();
        if (food.getSaturationLevel() > 0f) {
            food.setSaturation(Math.max(0f, food.getSaturationLevel() - 1f));
        } else {
            food.setFoodLevel(Math.max(0, food.getFoodLevel() - 1));
        }
    }
}
