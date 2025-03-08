package net.silvertide.pmmo_skill_books.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.silvertide.pmmo_skill_books.PMMOSkillBooks;
import net.silvertide.pmmo_skill_books.items.components.SkillGrantData;
import net.silvertide.pmmo_skill_books.utils.DataComponentUtil;
import net.silvertide.pmmo_skill_books.utils.GUIUtil;

import java.util.List;

public class TabRegistry {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, PMMOSkillBooks.MOD_ID);
    public static void register(IEventBus eventBus) { CREATIVE_MODE_TABS.register(eventBus); }

    public static final String[] PMMO_SKILLS = new String[] {
            "magic",
            "slayer",
            "fishing",
            "combat",
            "alchemy",
            "mining",
            "engineering",
            "endurance",
            "building",
            "smithing",
            "swimming",
            "woodcutting",
            "gunslinging",
            "crafting",
            "excavation",
            "farming",
            "flying",
            "cooking",
            "agility",
            "sailing",
            "hunter",
            "archery",
            "taming"
    };

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MOD_TAB = CREATIVE_MODE_TABS.register("skill_book_tab",
            () -> CreativeModeTab.builder()
                    .icon(TabRegistry::getIcon)
                    .title(Component.translatable("creative_tab.skill_books"))
                    .displayItems((displayParameters, output) -> {
                        addSkillGrantItem(output, "pmmo_skill_books.diamond.magic", List.of("magic", "arcane", "swimming", "mining"), "level", 10L, 10, "black", "diamond");

                        for(String skill : PMMO_SKILLS) {
                            List<String> skillList = List.of(skill);
                            addSkillGrantItem(output, "pmmo_skill_books.plain." + skill, skillList, "xp", 1000L, -1, "blue", "plain");
                            addSkillGrantItem(output, "pmmo_skill_books.gold." + skill, skillList, "xp", 5000L, -1, "blue", "gold");
                            addSkillGrantItem(output, "pmmo_skill_books.emerald." + skill, skillList, "xp", 10000L, -1, "blue", "emerald");
                            addSkillGrantItem(output, "pmmo_skill_books.diamond." + skill, skillList, "xp", 20000L, -1, "blue", "diamond");

                            addSkillGrantItem(output, "pmmo_skill_books.plain." + skill, skillList, "level", 1L, -1, "black", "plain");
                            addSkillGrantItem(output, "pmmo_skill_books.gold." + skill, skillList, "level", 3L, -1, "black", "gold");
                            addSkillGrantItem(output, "pmmo_skill_books.emerald." + skill, skillList, "level", 5L, -1, "black", "emerald");
                            addSkillGrantItem(output, "pmmo_skill_books.diamond." + skill, skillList, "level", 10L, -1, "black", "diamond");
                        }
                    })
                    .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
                    .build());

    private static ItemStack getIcon() {
        return new ItemStack(ItemRegistry.SKILL_GRANT.get());
    }

    private static void addSkillGrantItem(CreativeModeTab.Output output, String name, List<String> skills, String applicationType, Long value, int experienceCost, String color, String rank) {
        ItemStack skillGrant = new ItemStack(ItemRegistry.SKILL_GRANT.get());
        DataComponentUtil.addSkillGrantData(skillGrant, name, skills, applicationType, value,  experienceCost, "skillbook", color, rank);
        output.accept(skillGrant);
    }
}
