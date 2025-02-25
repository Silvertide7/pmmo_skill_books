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
import net.silvertide.pmmo_skill_books.items.components.SkillBookData;
import net.silvertide.pmmo_skill_books.util.SkillBookUtil;

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
                            addSkillBook(output, skill, "xp", 1000L, "blue", "plain");
                            addSkillBook(output, skill, "xp", 5000L, "blue", "gold");
                            addSkillBook(output, skill, "xp", 10000L, "blue", "emerald");
                            addSkillBook(output, skill, "xp", 20000L, "blue", "diamond");

                            addSkillBook(output, skill, "level", 1L, "black", "plain");
                            addSkillBook(output, skill, "level", 3L, "black", "gold");
                            addSkillBook(output, skill, "level", 5L, "black", "emerald");
                            addSkillBook(output, skill, "level", 10L, "black", "diamond");
                        }
                    })
                    .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
                    .build());
    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }

    private static ItemStack getIcon(){
        return new ItemStack(ItemRegistry.SKILL_BOOK.get());
    }

    private static void addSkillBook(CreativeModeTab.Output output, String skill, String applicationType, Long value, String bookColor, String trimColor) {
        ItemStack skillBook = new ItemStack(ItemRegistry.SKILL_BOOK.get());
        SkillBookData skillBookData = new SkillBookData(skill, applicationType, value, bookColor, trimColor);
        SkillBookUtil.putSkillBookData(skillBook, skillBookData);
        output.accept(skillBook);
    }
}
