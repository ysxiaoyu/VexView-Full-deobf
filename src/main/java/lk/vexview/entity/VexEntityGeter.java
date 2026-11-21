/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.entity;

import com.mojang.authlib.GameProfile;
import lk.vexview.entity.VexPlayerEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.EntityLeashKnot;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityEnderEye;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityMinecartChest;
import net.minecraft.entity.item.EntityMinecartCommandBlock;
import net.minecraft.entity.item.EntityMinecartFurnace;
import net.minecraft.entity.item.EntityMinecartHopper;
import net.minecraft.entity.item.EntityMinecartMobSpawner;
import net.minecraft.entity.item.EntityMinecartTNT;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityElderGuardian;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.entity.monster.EntityEvoker;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityGiantZombie;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.monster.EntityHusk;
import net.minecraft.entity.monster.EntityIllusionIllager;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntityPolarBear;
import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityStray;
import net.minecraft.entity.monster.EntityVex;
import net.minecraft.entity.monster.EntityVindicator;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityDonkey;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityMule;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityParrot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySkeletonHorse;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.passive.EntityZombieHorse;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityDragonFireball;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.entity.projectile.EntityEvokerFangs;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.entity.projectile.EntityLlamaSpit;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.entity.projectile.EntityShulkerBullet;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.entity.projectile.EntitySpectralArrow;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class VexEntityGeter {
    private static WorldClient s1 = Minecraft.getMinecraft().world;

    public static AbstractClientPlayer m1(GameProfile gameProfile) {
        for (Entity entity : Minecraft.getMinecraft().world.loadedEntityList) {
            AbstractClientPlayer abstractClientPlayer;
            if (!(entity instanceof AbstractClientPlayer) || !(abstractClientPlayer = (AbstractClientPlayer)entity).getGameProfile().getName().equals(gameProfile.getName())) continue;
            return abstractClientPlayer;
        }
        return new VexPlayerEntity(Minecraft.getMinecraft().world, gameProfile);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static Entity m2(String string) {
        int n;
        block174: {
            String string2 = string;
            int n2 = -1;
            switch (string2.hashCode()) {
                case -1924565927: {
                    if (!string2.equals("AREA_EFFECT_CLOUD")) break;
                    n = n2 = 0;
                    break block174;
                }
                case 1295910038: {
                    if (!string2.equals("ARMOR_STAND")) break;
                    n = n2 = 1;
                    break block174;
                }
                case 62553065: {
                    if (!string2.equals("ARROW")) break;
                    n = n2 = 2;
                    break block174;
                }
                case 65525: {
                    if (!string2.equals("BAT")) break;
                    n = n2 = 3;
                    break block174;
                }
                case 63281826: {
                    if (!string2.equals("BLAZE")) break;
                    n = n2 = 4;
                    break block174;
                }
                case 2044224: {
                    if (!string2.equals("BOAT")) break;
                    n = n2 = 5;
                    break block174;
                }
                case 13282263: {
                    if (!string2.equals("CAVE_SPIDER")) break;
                    n = n2 = 6;
                    break block174;
                }
                case 1463990677: {
                    if (!string2.equals("CHICKEN")) break;
                    n = n2 = 7;
                    break block174;
                }
                case 66923: {
                    if (!string2.equals("COW")) break;
                    n = n2 = 8;
                    break block174;
                }
                case 1746652494: {
                    if (!string2.equals("CREEPER")) break;
                    n = n2 = 9;
                    break block174;
                }
                case 2022138428: {
                    if (!string2.equals("DONKEY")) break;
                    n = n2 = 10;
                    break block174;
                }
                case 1548158433: {
                    if (!string2.equals("DRAGON_FIREBALL")) break;
                    n = n2 = 11;
                    break block174;
                }
                case -186615738: {
                    if (!string2.equals("ELDER_GUARDIAN")) break;
                    n = n2 = 12;
                    break block174;
                }
                case 2041798783: {
                    if (!string2.equals("ENDER_CRYSTAL")) break;
                    n = n2 = 13;
                    break block174;
                }
                case -1430253686: {
                    if (!string2.equals("ENDER_DRAGON")) break;
                    n = n2 = 14;
                    break block174;
                }
                case -1781303918: {
                    if (!string2.equals("ENDERMAN")) break;
                    n = n2 = 15;
                    break block174;
                }
                case 614161333: {
                    if (!string2.equals("ENDERMITE")) break;
                    n = n2 = 16;
                    break block174;
                }
                case -2114868646: {
                    if (!string2.equals("EVOKER_FANGS")) break;
                    n = n2 = 17;
                    break block174;
                }
                case 2057262010: {
                    if (!string2.equals("EVOKER")) break;
                    n = n2 = 18;
                    break block174;
                }
                case -2111315350: {
                    if (!string2.equals("EXPERIENCE_ORB")) break;
                    n = n2 = 19;
                    break block174;
                }
                case -552425298: {
                    if (!string2.equals("EYE_OF_ENDER")) break;
                    n = n2 = 20;
                    break block174;
                }
                case 628972693: {
                    if (!string2.equals("FALLING_BLOCK")) break;
                    n = n2 = 21;
                    break block174;
                }
                case -1909209140: {
                    if (!string2.equals("FIREWORK_ROCKET")) break;
                    n = n2 = 22;
                    break block174;
                }
                case 67780065: {
                    if (!string2.equals("GHAST")) break;
                    n = n2 = 23;
                    break block174;
                }
                case 67809701: {
                    if (!string2.equals("GIANT")) break;
                    n = n2 = 24;
                    break block174;
                }
                case -747394671: {
                    if (!string2.equals("GUARDIAN")) break;
                    n = n2 = 25;
                    break block174;
                }
                case 68928445: {
                    if (!string2.equals("HORSE")) break;
                    n = n2 = 26;
                    break block174;
                }
                case 2229285: {
                    if (!string2.equals("HUSK")) break;
                    n = n2 = 27;
                    break block174;
                }
                case -1504101618: {
                    if (!string2.equals("ILLUSIONER")) break;
                    n = n2 = 28;
                    break block174;
                }
                case -1504674734: {
                    if (!string2.equals("DROPPED_ITEM")) break;
                    n = n2 = 29;
                    break block174;
                }
                case -562844383: {
                    if (!string2.equals("ITEM_FRAME")) break;
                    n = n2 = 30;
                    break block174;
                }
                case 219275573: {
                    if (!string2.equals("FIREBALL")) break;
                    n = n2 = 31;
                    break block174;
                }
                case -2092169398: {
                    if (!string2.equals("LEASH_KNOT")) break;
                    n = n2 = 32;
                    break block174;
                }
                case 72516629: {
                    if (!string2.equals("LLAMA")) break;
                    n = n2 = 33;
                    break block174;
                }
                case 1205299762: {
                    if (!string2.equals("LLAMA_SPIT")) break;
                    n = n2 = 34;
                    break block174;
                }
                case 1282404205: {
                    if (!string2.equals("MAGMA_CUBE")) break;
                    n = n2 = 35;
                    break block174;
                }
                case -580440589: {
                    if (!string2.equals("MINECART")) break;
                    n = n2 = 36;
                    break block174;
                }
                case -1781359215: {
                    if (!string2.equals("CHEST_MINECART")) break;
                    n = n2 = 37;
                    break block174;
                }
                case 908666137: {
                    if (!string2.equals("COMMAND_BLOCK_MINECART")) break;
                    n = n2 = 38;
                    break block174;
                }
                case 792798490: {
                    if (!string2.equals("FURNACE_MINECART")) break;
                    n = n2 = 39;
                    break block174;
                }
                case 764647838: {
                    if (!string2.equals("HOPPER_MINECART")) break;
                    n = n2 = 40;
                    break block174;
                }
                case 986363658: {
                    if (!string2.equals("SPAWNER_MINECART")) break;
                    n = n2 = 41;
                    break block174;
                }
                case -287426696: {
                    if (!string2.equals("TNT_MINECART")) break;
                    n = n2 = 42;
                    break block174;
                }
                case 2378017: {
                    if (!string2.equals("MULE")) break;
                    n = n2 = 43;
                    break block174;
                }
                case -1948396067: {
                    if (!string2.equals("MOOSHROOM")) break;
                    n = n2 = 44;
                    break block174;
                }
                case -1969257312: {
                    if (!string2.equals("OCELOT")) break;
                    n = n2 = 45;
                    break block174;
                }
                case 1708608196: {
                    if (!string2.equals("PAINTING")) break;
                    n = n2 = 46;
                    break block174;
                }
                case -1942082154: {
                    if (!string2.equals("PARROT")) break;
                    n = n2 = 47;
                    break block174;
                }
                case 79214: {
                    if (!string2.equals("PIG")) break;
                    n = n2 = 48;
                    break block174;
                }
                case 1927926085: {
                    if (!string2.equals("ZOMBIE_PIGMAN")) break;
                    n = n2 = 49;
                    break block174;
                }
                case -291037131: {
                    if (!string2.equals("POLAR_BEAR")) break;
                    n = n2 = 50;
                    break block174;
                }
                case 83226: {
                    if (!string2.equals("TNT")) break;
                    n = n2 = 51;
                    break block174;
                }
                case -1885316070: {
                    if (!string2.equals("RABBIT")) break;
                    n = n2 = 52;
                    break block174;
                }
                case 78865723: {
                    if (!string2.equals("SHEEP")) break;
                    n = n2 = 53;
                    break block174;
                }
                case -1504469236: {
                    if (!string2.equals("SHULKER")) break;
                    n = n2 = 54;
                    break block174;
                }
                case -157617771: {
                    if (!string2.equals("SHULKER_BULLET")) break;
                    n = n2 = 55;
                    break block174;
                }
                case -1847105819: {
                    if (!string2.equals("SILVERFISH")) break;
                    n = n2 = 56;
                    break block174;
                }
                case -1484593075: {
                    if (!string2.equals("SKELETON")) break;
                    n = n2 = 57;
                    break block174;
                }
                case -1288904373: {
                    if (!string2.equals("SKELETON_HORSE")) break;
                    n = n2 = 58;
                    break block174;
                }
                case 78988968: {
                    if (!string2.equals("SLIME")) break;
                    n = n2 = 59;
                    break block174;
                }
                case -1198126707: {
                    if (!string2.equals("SMALL_FIREBALL")) break;
                    n = n2 = 60;
                    break block174;
                }
                case 260993616: {
                    if (!string2.equals("SNOW_GOLEM")) break;
                    n = n2 = 61;
                    break block174;
                }
                case 1474260482: {
                    if (!string2.equals("SNOWBALL")) break;
                    n = n2 = 62;
                    break block174;
                }
                case 788023630: {
                    if (!string2.equals("SPECTRAL_ARROW")) break;
                    n = n2 = 63;
                    break block174;
                }
                case -1842623771: {
                    if (!string2.equals("SPIDER")) break;
                    n = n2 = 64;
                    break block174;
                }
                case 79149330: {
                    if (!string2.equals("SQUID")) break;
                    n = n2 = 65;
                    break block174;
                }
                case 79235593: {
                    if (!string2.equals("STRAY")) break;
                    n = n2 = 66;
                    break block174;
                }
                case 943567908: {
                    if (!string2.equals("TRADER_LLAMA")) break;
                    n = n2 = 67;
                    break block174;
                }
                case 68581: {
                    if (!string2.equals("EGG")) break;
                    n = n2 = 68;
                    break block174;
                }
                case -1975104561: {
                    if (!string2.equals("ENDER_PEARL")) break;
                    n = n2 = 69;
                    break block174;
                }
                case 1226127931: {
                    if (!string2.equals("EXPERIENCE_BOTTLE")) break;
                    n = n2 = 70;
                    break block174;
                }
                case -1929101933: {
                    if (!string2.equals("POTION")) break;
                    n = n2 = 71;
                    break block174;
                }
                case 84873: {
                    if (!string2.equals("VEX")) break;
                    n = n2 = 72;
                    break block174;
                }
                case -2125864634: {
                    if (!string2.equals("VILLAGER")) break;
                    n = n2 = 73;
                    break block174;
                }
                case -106320427: {
                    if (!string2.equals("IRON_GOLEM")) break;
                    n = n2 = 74;
                    break block174;
                }
                case -1940664743: {
                    if (!string2.equals("VINDICATOR")) break;
                    n = n2 = 75;
                    break block174;
                }
                case 82603943: {
                    if (!string2.equals("WITCH")) break;
                    n = n2 = 76;
                    break block174;
                }
                case -1734240269: {
                    if (!string2.equals("WITHER")) break;
                    n = n2 = 77;
                    break block174;
                }
                case 1826013977: {
                    if (!string2.equals("WITHER_SKELETON")) break;
                    n = n2 = 78;
                    break block174;
                }
                case -2043676687: {
                    if (!string2.equals("WITHER_SKULL")) break;
                    n = n2 = 79;
                    break block174;
                }
                case 2670162: {
                    if (!string2.equals("WOLF")) break;
                    n = n2 = 80;
                    break block174;
                }
                case -1643025882: {
                    if (!string2.equals("ZOMBIE")) break;
                    n = n2 = 81;
                    break block174;
                }
                case -499196828: {
                    if (!string2.equals("ZOMBIE_HORSE")) break;
                    n = n2 = 82;
                    break block174;
                }
                case -679759041: {
                    if (!string2.equals("ZOMBIE_VILLAGER")) break;
                    n = n2 = 83;
                    break block174;
                }
                case -1932423455: {
                    if (!string2.equals("PLAYER")) break;
                    n2 = 84;
                }
            }
            n = n2;
        }
        switch (n) {
            case 0: {
                return new EntityAreaEffectCloud((World)s1);
            }
            case 1: {
                return new EntityArmorStand((World)s1);
            }
            case 2: {
                return new EntityArrow((World)s1){

                    protected ItemStack getArrowStack() {
                        return null;
                    }
                };
            }
            case 3: {
                return new EntityBat((World)s1);
            }
            case 4: {
                return new EntityBlaze((World)s1);
            }
            case 5: {
                return new EntityBoat((World)s1);
            }
            case 6: {
                return new EntityCaveSpider((World)s1);
            }
            case 7: {
                return new EntityChicken((World)s1);
            }
            case 8: {
                return new EntityCow((World)s1);
            }
            case 9: {
                return new EntityCreeper((World)s1);
            }
            case 10: {
                return new EntityDonkey((World)s1);
            }
            case 11: {
                return new EntityDragonFireball((World)s1);
            }
            case 12: {
                return new EntityElderGuardian((World)s1);
            }
            case 13: {
                return new EntityEnderCrystal((World)s1);
            }
            case 14: {
                return new EntityDragon((World)s1);
            }
            case 15: {
                return new EntityEnderman((World)s1);
            }
            case 16: {
                return new EntityEndermite((World)s1);
            }
            case 17: {
                return new EntityEvokerFangs((World)s1);
            }
            case 18: {
                return new EntityEvoker((World)s1);
            }
            case 19: {
                return new EntityXPOrb((World)s1);
            }
            case 20: {
                return new EntityEnderEye((World)s1);
            }
            case 21: {
                return new EntityFallingBlock((World)s1);
            }
            case 22: {
                return new EntityFireworkRocket((World)s1);
            }
            case 23: {
                return new EntityGhast((World)s1);
            }
            case 24: {
                return new EntityGiantZombie((World)s1);
            }
            case 25: {
                return new EntityGuardian((World)s1);
            }
            case 26: {
                return new EntityHorse((World)s1);
            }
            case 27: {
                return new EntityHusk((World)s1);
            }
            case 28: {
                return new EntityIllusionIllager((World)s1);
            }
            case 29: {
                return new EntityItem((World)s1);
            }
            case 30: {
                return new EntityItemFrame((World)s1);
            }
            case 31: {
                return new EntityLargeFireball((World)s1);
            }
            case 32: {
                return new EntityLeashKnot((World)s1);
            }
            case 33: {
                return new EntityLlama((World)s1);
            }
            case 34: {
                return new EntityLlamaSpit((World)s1);
            }
            case 35: {
                return new EntityMagmaCube((World)s1);
            }
            case 36: {
                return new EntityMinecart((World)s1){

                    public EntityMinecart.Type getType() {
                        return EntityMinecart.Type.RIDEABLE;
                    }
                };
            }
            case 37: {
                return new EntityMinecartChest((World)s1);
            }
            case 38: {
                return new EntityMinecartCommandBlock((World)s1);
            }
            case 39: {
                return new EntityMinecartFurnace((World)s1);
            }
            case 40: {
                return new EntityMinecartHopper((World)s1);
            }
            case 41: {
                return new EntityMinecartMobSpawner((World)s1);
            }
            case 42: {
                return new EntityMinecartTNT((World)s1);
            }
            case 43: {
                return new EntityMule((World)s1);
            }
            case 44: {
                return new EntityMooshroom((World)s1);
            }
            case 45: {
                return new EntityOcelot((World)s1);
            }
            case 46: {
                return new EntityPainting((World)s1);
            }
            case 47: {
                return new EntityParrot((World)s1);
            }
            case 48: {
                return new EntityPig((World)s1);
            }
            case 49: {
                return new EntityPigZombie((World)s1);
            }
            case 50: {
                return new EntityPolarBear((World)s1);
            }
            case 51: {
                return new EntityTNTPrimed((World)s1);
            }
            case 52: {
                return new EntityRabbit((World)s1);
            }
            case 53: {
                return new EntitySheep((World)s1);
            }
            case 54: {
                return new EntityShulker((World)s1);
            }
            case 55: {
                return new EntityShulkerBullet((World)s1);
            }
            case 56: {
                return new EntitySilverfish((World)s1);
            }
            case 57: {
                return new EntitySkeleton((World)s1);
            }
            case 58: {
                return new EntitySkeletonHorse((World)s1);
            }
            case 59: {
                return new EntitySlime((World)s1);
            }
            case 60: {
                return new EntitySmallFireball((World)s1);
            }
            case 61: {
                return new EntitySnowman((World)s1);
            }
            case 62: {
                return new EntitySnowball((World)s1);
            }
            case 63: {
                return new EntitySpectralArrow((World)s1);
            }
            case 64: {
                return new EntitySpider((World)s1);
            }
            case 65: {
                return new EntitySquid((World)s1);
            }
            case 66: {
                return new EntityStray((World)s1);
            }
            case 67: {
                return new EntityLlama((World)s1);
            }
            case 68: {
                return new EntityEgg((World)s1);
            }
            case 69: {
                return new EntityEnderPearl((World)s1);
            }
            case 70: {
                return new EntityExpBottle((World)s1);
            }
            case 71: {
                return new EntityPotion((World)s1);
            }
            case 72: {
                return new EntityVex((World)s1);
            }
            case 73: {
                return new EntityVillager((World)s1);
            }
            case 74: {
                return new EntityIronGolem((World)s1);
            }
            case 75: {
                return new EntityVindicator((World)s1);
            }
            case 76: {
                return new EntityWitch((World)s1);
            }
            case 77: {
                return new EntityWither((World)s1);
            }
            case 78: {
                return new EntityWitherSkeleton((World)s1);
            }
            case 79: {
                return new EntityWitherSkull((World)s1);
            }
            case 80: {
                return new EntityWolf((World)s1);
            }
            case 81: {
                return new EntityZombie((World)s1);
            }
            case 82: {
                return new EntityZombieHorse((World)s1);
            }
            case 83: {
                return new EntityZombieVillager((World)s1);
            }
            case 84: {
                return new VexPlayerEntity((World)Minecraft.getMinecraft().world);
            }
        }
        System.out.println(string);
        return null;
    }
}

