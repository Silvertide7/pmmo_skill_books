package net.silvertide.pmmo_skill_books.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.silvertide.pmmo_skill_books.PMMOSkillBooks;
import net.silvertide.pmmo_skill_books.items.components.SkillGrantData;
import net.silvertide.pmmo_skill_books.utils.SkillGrantUtil;

import java.util.List;

public class TabRegistry {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, PMMOSkillBooks.MOD_ID);

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

    public static final RegistryObject<CreativeModeTab> COURSE_TAB = CREATIVE_MODE_TABS.register("skill_books",
            () -> CreativeModeTab.builder()
                    .icon(TabRegistry::getIcon)
                    .title(Component.translatable("creative_tab.skill_books"))
                    .displayItems((displayParameters, output) -> {
                        for(String skill : PMMO_SKILLS) {
                            List<String> skillList = List.of(skill);
                            addSkillGrantItem(output, "pmmo_skill_books.plain." + skill, skillList, "xp", 1000L, -1, "plain", "blue");
                            addSkillGrantItem(output, "pmmo_skill_books.gold." + skill, skillList, "xp", 5000L, -1, "gold", "blue");
                            addSkillGrantItem(output, "pmmo_skill_books.emerald." + skill, skillList, "xp", 10000L, -1, "emerald", "blue");
                            addSkillGrantItem(output, "pmmo_skill_books.diamond." + skill, skillList, "xp", 20000L, -1, "diamond", "blue");

                            addSkillGrantItem(output, "pmmo_skill_books.plain." + skill, skillList, "level", 1L, -1, "plain", "black");
                            addSkillGrantItem(output, "pmmo_skill_books.gold." + skill, skillList, "level", 3L, -1, "gold", "black");
                            addSkillGrantItem(output, "pmmo_skill_books.emerald." + skill, skillList, "level", 5L, -1, "emerald", "black");
                            addSkillGrantItem(output, "pmmo_skill_books.diamond." + skill, skillList, "level", 10L, -1, "diamond", "black");
                        }
                    })
                    .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
                    .build());
    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }

    private static ItemStack getIcon(){
        return new ItemStack(ItemRegistry.SKILL_GRANT.get());
    }

    private static void addSkillGrantItem(CreativeModeTab.Output output, String name, List<String> skills, String applicationType, Long value, int experienceCost, String rank, String color) {
        ItemStack skillBook = new ItemStack(ItemRegistry.SKILL_GRANT.get());
        SkillGrantData skillGrantData = new SkillGrantData(name, skills, applicationType, value,  experienceCost, "skillbook", rank, color);
        SkillGrantUtil.putSkillGrantData(skillBook, skillGrantData);
        output.accept(skillBook);
    }
}
