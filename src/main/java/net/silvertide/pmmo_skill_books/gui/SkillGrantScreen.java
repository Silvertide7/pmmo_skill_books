package net.silvertide.pmmo_skill_books.gui;

import harmonised.pmmo.network.Networking;
import io.netty.util.internal.StringUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.silvertide.pmmo_skill_books.PMMOSkillBooks;
import net.silvertide.pmmo_skill_books.items.components.SkillGrantData;
import net.silvertide.pmmo_skill_books.network.server_packets.SB_GrantSkill;
import net.silvertide.pmmo_skill_books.utils.GUIUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class SkillGrantScreen extends Screen {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(PMMOSkillBooks.MOD_ID, "textures/gui/gui_skill_grant.png");
    private static final int SCREEN_WIDTH = 206;
    private static final int SCREEN_HEIGHT = 137;

    private static final int CARD_HEIGHT = 25;
    private static final int CARD_WIDTH = 90;

    //CLOSE BUTTON CONSTANTS
    private static final int CONFIRM_BUTTON_X = 65;
    private static final int CONFIRM_BUTTON_Y = 106;
    private static final int CONFIRM_BUTTON_WIDTH = 70;
    private static final int CONFIRM_BUTTON_HEIGHT = 15;

    private static final int CLOSE_BUTTON_X = 23;
    private static final int CLOSE_BUTTON_Y = 106;
    private static final int CLOSE_BUTTON_WIDTH = 70;
    private static final int CLOSE_BUTTON_HEIGHT = 15;

    private boolean closeButtonDown = false;
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
            super.render(guiGraphics, mouseX, mouseY, partialTicks);
        } catch (Exception ignore) {
            onClose();
        }
    }

    @Override
    public void renderBackground(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        renderTransparentBackground(guiGraphics);
        renderScreenBackground(guiGraphics);
//        renderTitle(guiGraphics);
        renderSkillTitle(guiGraphics);
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
        int buttonX = this.getScreenStartX() + CONFIRM_BUTTON_X + CONFIRM_BUTTON_WIDTH / 2;
        int buttonY = this.getScreenStartY() + CONFIRM_BUTTON_Y + CONFIRM_BUTTON_HEIGHT / 2;

        Component text = Component.literal("Confirm");
        GUIUtil.drawScaledCenteredWordWrap(guiGraphics, 0.7F, this.font, text, buttonX, buttonY + 13, 100, 0x000000);
    }

    private void renderCloseButton(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        int buttonX = this.getScreenStartX() + CLOSE_BUTTON_X + CLOSE_BUTTON_WIDTH / 2;
        int buttonY = this.getScreenStartY() + CLOSE_BUTTON_Y + CLOSE_BUTTON_HEIGHT / 2;

        Component text = Component.literal("Cancel");
        GUIUtil.drawScaledCenteredWordWrap(guiGraphics, 0.7F, this.font, text, buttonX, buttonY + 13, 100, 0x000000);
    }

    private void renderScreenBackground(GuiGraphics guiGraphics) {
        int x = this.getScreenStartX();
        int y = this.getScreenStartY();

        guiGraphics.blit(TEXTURE, x, y, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
    }

    private void renderSkillTitle(GuiGraphics guiGraphics) {
        int textX = this.getScreenStartX() + 148;
        int textY = this.getScreenStartY() + 15;

        Component buttonTextComp = Component.translatable("pmmo_skill_books.screen.grant_skill.choose_skill");
        GUIUtil.drawScaledCenteredWordWrap(guiGraphics, 0.85F, this.font, buttonTextComp, textX, textY, 100, 0x000000);
    }

    private int getScreenStartX() {
        return (this.width - SCREEN_WIDTH) / 2;
    }

    private int getScreenStartY() {
        return (this.height - SCREEN_HEIGHT) / 2;
    }

    private boolean isHoveringConfirmButton(double mouseX, double mouseY) {
        return isHovering(CONFIRM_BUTTON_X, CONFIRM_BUTTON_Y, CONFIRM_BUTTON_WIDTH, CONFIRM_BUTTON_HEIGHT, mouseX, mouseY);
    }

    private boolean isHoveringCloseButton(double mouseX, double mouseY) {
        return isHovering(CONFIRM_BUTTON_X, CONFIRM_BUTTON_Y, CONFIRM_BUTTON_WIDTH, CONFIRM_BUTTON_HEIGHT, mouseX, mouseY);
    }

    private boolean isHovering(int x, int y, int width, int height, double mouseX, double mouseY) {
        return GUIUtil.isHovering(this.getScreenStartX(), this.getScreenStartY(), x, y, width, height, mouseX, mouseY);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if(confirmButtonDown && isHoveringConfirmButton(mouseX, mouseY)) {
            Networking.sendToServer(new SB_GrantSkill("arcane", "level", 10L,  10, true));
            return true;
        } else if(closeButtonDown && isHoveringCloseButton(mouseX, mouseY)) {
            this.onClose();
//            Networking.sendToServer(new SB_GrantSkill("arcane", "level", 10L,  10, true));
            return true;
        } else {
            for(SkillChoiceCard card : this.choiceCards) {
                card.mouseReleased();
            }
        }

        closeButtonDown = false;
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if(isHoveringConfirmButton(mouseX, mouseY) && StringUtil.isNullOrEmpty(this.selectedSkill)) {
            confirmButtonDown = true;
            return true;
        } else if(isHoveringCloseButton(mouseX, mouseY)) {
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
        private static final int CARD_X = 112;
        private static final int CARD_Y = 46;

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

            renderLine(guiGraphics);
            renderCardTitle(guiGraphics);
        }


        private void renderLine(GuiGraphics guiGraphics) {
            guiGraphics.blit(TEXTURE, getCardStartX(), getCardStartY() + CARD_HEIGHT, 114, getLineVOffset(), 72, 1);
        }

        private int getLineVOffset() {
            if(this.isSelected) {
                return 141;
            } else if (this.isHoveringCard)   {
                return 140;
            } else {
                return 139;
            }
        }

        private void renderCardTitle(GuiGraphics guiGraphics) {
            int textOffsetX = 28;
            int textOffsetY = 5;
            float textScale = 0.7F;

            GUIUtil.drawScaledString(guiGraphics, textScale, skillGrantScreen.font, this.skill, getCardStartX() + textOffsetX, getCardStartY() + textOffsetY, 0xFFFFFF);
        }

        private boolean isHoveringOnCard(int x, int y, int width, int height, double mouseX, double mouseY) {
            return GUIUtil.isHovering(getCardStartX(), getCardStartY(), x, y, width, height, mouseX, mouseY);
        }

        // HELPERS
        private int getCardStartX() {
            return skillGrantScreen.getScreenStartX() + CARD_X;
        }

        private int getCardStartY() {
            return skillGrantScreen.getScreenStartY() + CARD_Y + (this.index * (CARD_HEIGHT + 1));
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
