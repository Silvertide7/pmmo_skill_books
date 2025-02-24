package net.silvertide.pmmo_skill_books.tabs;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.silvertide.pmmo_skill_books.PMMOSkillBooks;
import net.silvertide.pmmo_skill_books.items.ModItems;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, PMMOSkillBooks.MOD_ID);

    public static final RegistryObject<CreativeModeTab> SKILL_BOOK_TAB = CREATIVE_MODE_TABS.register("skill_book_tab",
            () -> CreativeModeTab.builder().icon(ModCreativeModeTabs::getIcon)
                    .title(Component.translatable("creative_tab.skill_books"))
                    .displayItems((displayParameters, output) -> {
                        ModItems.skillBookItems.forEach((skillBook -> {
                            output.accept(skillBook.registryObject().get());
                        }));

                        ModItems.customBookItems.forEach((customBook -> {
                            output.accept(customBook.registryObject().get());
                        }));
                    }).build());
    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }

    private static ItemStack getIcon(){
        return new ItemStack(ModItems.skillBookItems.get(ModItems.skillBookItems.size()-1).registryObject().get());
    }
}
