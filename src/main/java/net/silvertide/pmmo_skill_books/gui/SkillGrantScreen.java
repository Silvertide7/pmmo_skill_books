package net.silvertide.pmmo_skill_books.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringUtil;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.silvertide.pmmo_skill_books.PMMOSkillBooks;
import net.silvertide.pmmo_skill_books.items.components.SkillGrantData;
import net.silvertide.pmmo_skill_books.network.PacketHandler;
import net.silvertide.pmmo_skill_books.network.server_packets.SB_GrantSkill;
import net.silvertide.pmmo_skill_books.utils.GUIUtil;
import net.silvertide.pmmo_skill_books.utils.SkillGrantUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class SkillGrantScreen extends Screen {
    private static final ResourceLocation TEXTURE = new ResourceLocation(PMMOSkillBooks.MOD_ID, "textures/gui/gui_skill_grant.png");
    private static final int SCREEN_WIDTH = 206;
    private static final int SCREEN_HEIGHT = 137;

    //CLOSE BUTTON CONSTANTS
    private static final int CONFIRM_BUTTON_X = 108;
    private static final int CONFIRM_BUTTON_Y = 109;
    private static final int CONFIRM_BUTTON_WIDTH = 38;
    private static final int CONFIRM_BUTTON_HEIGHT = 9;

    private static final int CLOSE_BUTTON_X = 150;
    private static final int CLOSE_BUTTON_Y = 109;
    private static final int CLOSE_BUTTON_WIDTH = 38;
    private static final int CLOSE_BUTTON_HEIGHT = 9;

    private boolean hoveringCloseButton = false;
    private boolean closeButtonDown = false;
    private boolean hoveringConfirmButton = false;
    private boolean confirmButtonDown = false;

    private final SkillGrantData skillGrantData;

    private String selectedSkill = "";
    private int selectedCardIndex = -1;
    private List<SkillChoiceCard> choiceCards;

    public SkillGrantScreen(SkillGrantData skillGrantData) {
        super(Component.literal(""));
        this.skillGrantData = skillGrantData;
        createSkillChoiceCards();
    }


    public void createSkillChoiceCards() {
        this.choiceCards = new ArrayList<>();
        int index = 0;
        for (String skill : this.skillGrantData.skills()) {
            SkillChoiceCard skillChoiceCard = new SkillChoiceCard(this, index, skill);
            this.choiceCards.add(skillChoiceCard);
            index++;
        }
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        try {
            renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
            super.render(guiGraphics, mouseX, mouseY, partialTicks);
        } catch (Exception ignore) {
            onClose();
        }
    }

    public void renderBackground(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        renderScreenBackground(guiGraphics);

        renderGrantInformation(guiGraphics);
        renderSkillTitle(guiGraphics);
        renderGainTitle(guiGraphics);
        renderButtons(guiGraphics, mouseX, mouseY);

        if(!choiceCards.isEmpty()) {
            for(SkillChoiceCard choiceCard : choiceCards) {
                choiceCard.render(guiGraphics, mouseX, mouseY);
            }
        }
    }

    private void renderButtons(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {
        renderConfirmButton(guiGraphics, mouseX, mouseY);
        renderCloseButton(guiGraphics, mouseX, mouseY);
    }

    private void renderConfirmButton(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        int buttonX = this.getScreenStartX() + CONFIRM_BUTTON_X;
        int buttonY = this.getScreenStartY() + CONFIRM_BUTTON_Y;
        this.hoveringConfirmButton = isHoveringConfirmButton(mouseX, mouseY);

        guiGraphics.blit(TEXTURE, buttonX, buttonY, 0, getConfirmButtonVertOffset(), CONFIRM_BUTTON_WIDTH, CONFIRM_BUTTON_HEIGHT);
        Component text = Component.translatable("pmmo_skill_books.screen.grant_skill.confirm");
        GUIUtil.drawScaledCenteredWordWrap(guiGraphics, 0.7F, this.font, text, buttonX + CONFIRM_BUTTON_WIDTH / 2, buttonY +  + CONFIRM_BUTTON_HEIGHT / 2, 100, getConfirmTextColor());
    }

    private int getConfirmButtonVertOffset() {
        if(this.selectedCardIndex < 0) {
            return 224;
        } else if (this.confirmButtonDown) {
            return 214;
        } else if (this.hoveringConfirmButton) {
            return 204;
        } else {
            return 194;
        }
    }

    private int getConfirmTextColor() {
        if(this.selectedCardIndex < 0) {
            return 0x6A6969;
        } else if(this.confirmButtonDown) {
            return 0xFCF5E5;
        } else if (this.hoveringConfirmButton) {
            return 0x000000;
        } else {
            return 0x4F4231;
        }
    }

    private boolean isHoveringConfirmButton(double mouseX, double mouseY) {
        return isHovering(CONFIRM_BUTTON_X, CONFIRM_BUTTON_Y, CONFIRM_BUTTON_WIDTH, CONFIRM_BUTTON_HEIGHT, mouseX, mouseY);
    }

    private void renderCloseButton(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        int buttonX = this.getScreenStartX() + CLOSE_BUTTON_X;
        int buttonY = this.getScreenStartY() + CLOSE_BUTTON_Y;
        this.hoveringCloseButton = isHoveringCloseButton(mouseX, mouseY);

        guiGraphics.blit(TEXTURE, buttonX, buttonY, 0, getCloseButtonVertOffset(), CLOSE_BUTTON_WIDTH, CLOSE_BUTTON_HEIGHT);
        Component text = Component.translatable("pmmo_skill_books.screen.grant_skill.close");
        GUIUtil.drawScaledCenteredWordWrap(guiGraphics, 0.7F, this.font, text, buttonX + CLOSE_BUTTON_WIDTH / 2, buttonY +  + CLOSE_BUTTON_HEIGHT / 2, 100, getCloseTextColor());
    }

    private int getCloseButtonVertOffset() {
        if (this.closeButtonDown) {
            return 214;
        } else if (this.hoveringCloseButton) {
            return 204;
        } else {
            return 194;
        }
    }

    private int getCloseTextColor() {
        if(this.closeButtonDown) {
            return 0xFCF5E5;
        } else if (this.hoveringCloseButton) {
            return 0x000000;
        } else {
            return 0x4F4231;
        }
    }

    private boolean isHoveringCloseButton(double mouseX, double mouseY) {
        return isHovering(CLOSE_BUTTON_X, CLOSE_BUTTON_Y, CLOSE_BUTTON_WIDTH, CLOSE_BUTTON_HEIGHT, mouseX, mouseY);
    }

    private void renderScreenBackground(GuiGraphics guiGraphics) {
        int x = this.getScreenStartX();
        int y = this.getScreenStartY();

        guiGraphics.blit(TEXTURE, x, y, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
    }

    private void renderGrantInformation(@NotNull GuiGraphics guiGraphics) {
        int x = this.getScreenStartX() + 150;
        int y = this.getScreenStartY() + 40;

        String skill = "Skill";
        if(!StringUtil.isNullOrEmpty(this.selectedSkill)) {
            skill = GUIUtil.getTranslatedSkillString(this.selectedSkill);
        }

        Component text = Component.translatable(SkillGrantUtil.getSkillBookEffectTranslationKey(skillGrantData.getApplicationType(), skillGrantData.applicationValue()), skillGrantData.applicationValue(), skill);

        GUIUtil.drawScaledCenteredWordWrap(guiGraphics, 0.70F, this.font, text, x, y, 80, 0x000000);

    }

    private void renderSkillTitle(GuiGraphics guiGraphics) {
        int textX = this.getScreenStartX() + 57;
        int textY = this.getScreenStartY() + 15;

        Component buttonTextComp = Component.translatable("pmmo_skill_books.screen.grant_skill.choose_skill");
        GUIUtil.drawScaledCenteredWordWrap(guiGraphics, 0.85F, this.font, buttonTextComp, textX, textY, 100, 0x000000);
    }

    private void renderGainTitle(GuiGraphics guiGraphics) {
        int textX = this.getScreenStartX() + 150;
        int textY = this.getScreenStartY() + 15;

        Component buttonTextComp = Component.translatable("pmmo_skill_books.screen.grant_skill.grant_effect");
        GUIUtil.drawScaledCenteredWordWrap(guiGraphics, 0.85F, this.font, buttonTextComp, textX, textY, 100, 0x000000);
    }

    private int getScreenStartX() {
        return (this.width - SCREEN_WIDTH) / 2;
    }

    private int getScreenStartY() {
        return (this.height - SCREEN_HEIGHT) / 2;
    }

    private boolean isHovering(int x, int y, int width, int height, double mouseX, double mouseY) {
        return GUIUtil.isHovering(this.getScreenStartX(), this.getScreenStartY(), x, y, width, height, mouseX, mouseY);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if(this.confirmButtonDown && this.hoveringConfirmButton) {
            PacketHandler.sendToServer(new SB_GrantSkill(this.selectedSkill, skillGrantData.applicationType(), skillGrantData.applicationValue(), skillGrantData.experienceCost()));
            this.confirmButtonDown = false;
            this.closeButtonDown = false;
            return true;
        } else if(this.closeButtonDown && this.hoveringCloseButton) {
            this.onClose();

            this.confirmButtonDown = false;
            this.closeButtonDown = false;
            return true;
        } else {
            for(SkillChoiceCard card : this.choiceCards) {
                card.mouseReleased();
            }
        }

        this.confirmButtonDown = false;
        this.closeButtonDown = false;
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if(this.hoveringConfirmButton && this.selectedCardIndex >= 0) {
            confirmButtonDown = true;
            return true;
        } else if(this.hoveringCloseButton) {
            closeButtonDown = true;
            return true;
        } else {
            for(SkillChoiceCard card : this.choiceCards) {
                card.mouseClicked();
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean isPauseScreen() { return false; }

    public String getSelectedSkill() {
        return selectedSkill;
    }

    public void setSelectedSkill(int index, String selectedSkill) {
        this.selectedCardIndex = index;
        this.selectedSkill = selectedSkill;

        for(SkillChoiceCard choiceCard : this.choiceCards) {
            choiceCard.setIsSelected(choiceCard.getIndex() == index);
        }
    }


    private class SkillChoiceCard {
        private static final int CARD_X = 17;
        private static final int CARD_Y = 26;
        private static final int CARD_WIDTH = 82;
        private static final int CARD_HEIGHT = 18;

        // Control Data
        private boolean isCardPressed = false;

        //Incoming Instance Data
        private final SkillGrantScreen skillGrantScreen;
        private final int index;
        private final String skill;

        private boolean isHoveringCard = false;
        private boolean isSelected = false;

        public SkillChoiceCard(SkillGrantScreen skillGrantScreen, int index, String skill) {
            this.skillGrantScreen = skillGrantScreen;
            this.index = index;
            this.skill = skill;
        }

        public void render(GuiGraphics guiGraphics, int mouseX, int mouseY) {
            isHoveringCard = this.isHoveringOnCard(0,0, CARD_WIDTH, CARD_HEIGHT, mouseX, mouseY);

            renderCard(guiGraphics);
            renderSkillText(guiGraphics);
        }


        private void renderCard(GuiGraphics guiGraphics) {
            guiGraphics.blit(TEXTURE, getCardStartX(), getCardStartY(), 0, getCardVertOffset(), CARD_WIDTH, CARD_HEIGHT);
        }

        private int getCardVertOffset() {
            if(this.isSelected) {
                return 175;
            } else if (this.isHoveringCard)   {
                return 156;
            } else {
                return 137;
            }
        }

        private void renderSkillText(GuiGraphics guiGraphics) {
            int textOffsetX = CARD_WIDTH / 2;
            int textOffsetY = CARD_HEIGHT / 2;
            float textScale = 0.7F;

            Component skill = Component.translatable("pmmo." + this.skill);
            GUIUtil.drawScaledCenteredWordWrap(guiGraphics, textScale, skillGrantScreen.font, skill, getCardStartX() + textOffsetX, getCardStartY() + textOffsetY, 90, getSkillTextColor());
        }

        private int getSkillTextColor() {
            if(this.isSelected) {
                return 0xFCF5E5;
            } else if (this.isHoveringCard) {
                return 0x000000;
            } else {
                return 0x4F4231;
            }
        }

        private boolean isHoveringOnCard(int x, int y, int width, int height, double mouseX, double mouseY) {
            return GUIUtil.isHovering(getCardStartX(), getCardStartY(), x, y, width, height, mouseX, mouseY);
        }

        // HELPERS
        private int getCardStartX() {
            return skillGrantScreen.getScreenStartX() + CARD_X;
        }

        private int getCardStartY() {
            return skillGrantScreen.getScreenStartY() + CARD_Y + (this.index * (CARD_HEIGHT + 2));
        }

        public void mouseReleased() {
            if(isCardPressed && isHoveringCard) {
                handleCardPress();
            }
            this.isCardPressed = false;
        }

        public void mouseClicked() {
            if(this.isHoveringCard) this.isCardPressed = true;
        }

        private void handleCardPress() {
            this.skillGrantScreen.setSelectedSkill(this.index, this.skill);
        }

        public void setIsSelected(boolean isSelected) {
            this.isSelected = isSelected;
        }

        public int getIndex() {
            return this.index;
        }
    }
}
