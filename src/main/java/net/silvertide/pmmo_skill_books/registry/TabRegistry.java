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
import net.silvertide.pmmo_skill_books.utils.DataComponentUtil;

public class TabRegistry {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, PMMOSkillBooks.MOD_ID);
    public static void register(IEventBus eventBus) { CREATIVE_MODE_TABS.register(eventBus); }

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MOD_TAB = CREATIVE_MODE_TABS.register("skill_book_tab",
            () -> CreativeModeTab.builder()
                    .icon(TabRegistry::getIcon)
                    .title(Component.translatable("creative_tab.skill_books"))
                    .displayItems((displayParameters, output) -> {
                        addSkillBook(output, "endurance", "xp", 2000L, "blue", "gold");

                    })
                    .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
                    .build());

    private static ItemStack getIcon(){
        return new ItemStack(ItemRegistry.SKILL_BOOK.get());
    }

    private static void addSkillBook(CreativeModeTab.Output output, String skill, String applicationType, Long value, String bookColor, String trimColor) {
        ItemStack skillBook = new ItemStack(ItemRegistry.SKILL_BOOK.get());
        DataComponentUtil.addSkillBookData(skillBook, skill, applicationType, value, bookColor, trimColor);
        output.accept(skillBook);
    }
}
