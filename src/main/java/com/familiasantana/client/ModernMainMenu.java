package com.familiasantana.client;

import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSelectWorld;

import java.io.IOException;

public class ModernMainMenu extends GuiScreen {
    private static final String[] LABELS = {"Um jogador", "Multijogador", "Opcoes", "Sair do jogo"};
    private final float[] hover = new float[LABELS.length];
    private final float[] chipHover = new float[MenuBg.NAMES.length];

    private float bw() {
        return 200;
    }

    private float bx() {
        return field_146294_l / 2F - bw() / 2F;
    }

    private float by(int i) {
        return field_146295_m / 2F - 20 + i * 34;
    }

    @Override
    public void func_73863_a(int mx, int my, float partialTicks) {
        int accent = Config.accent();
        MenuBg.full(MenuBg.style(), field_146294_l, field_146295_m, accent);

        String a = "Gangue Da ";
        String b = "Welcia";
        float aw = Fonts.width(a, 3F, true);
        float bwid = Fonts.width(b, 3F, true);
        float tx = field_146294_l / 2F - (aw + bwid) / 2F;
        float ty = by(0) - 62;
        Fonts.drawC(a, tx, ty, 0xFFFFFFFF, 3F, true, false);
        Fonts.drawC(b, tx + aw, ty, accent, 3F, true, false);

        String sub = "CLIENT 5.2   |   Minecraft 1.8.9";
        float sw = Fonts.width(sub, 0.9F, false);
        Fonts.drawC(sub, field_146294_l / 2F - sw / 2F, ty + 26, 0xFF8A8A98, 0.9F, false, false);

        for (int i = 0; i < LABELS.length; i++) {
            boolean hov = RenderUtil.hover(mx, my, bx(), by(i), bx() + bw(), by(i) + 26);
            hover[i] = Ui.approach(hover[i], hov);
            Ui.button(LABELS[i], bx(), by(i), bw(), 26, hover[i], i == 0);
        }

        // troca rapida do fundo (A / B)
        float cy = field_146295_m - 26;
        Fonts.drawC("Fundo", chipX(0) - 46, cy, 0xFF8A8A98, 0.9F, false, false);
        for (int i = 0; i < MenuBg.NAMES.length; i++) {
            boolean hov = RenderUtil.hover(mx, my, chipX(i), cy - 10, chipX(i) + 26, cy + 10);
            chipHover[i] = Ui.approach(chipHover[i], hov);
            Ui.button(String.valueOf((char) ('A' + i)), chipX(i), cy - 10, 26, 20, chipHover[i], i == MenuBg.style());
        }
    }

    private float chipX(int i) {
        return field_146294_l / 2F - 10 + i * 32;
    }

    @Override
    protected void func_73864_a(int mx, int my, int btn) throws IOException {
        super.func_73864_a(mx, my, btn);
        if (btn != 0) return;
        float cy = field_146295_m - 26;
        for (int i = 0; i < MenuBg.NAMES.length; i++) {
            if (RenderUtil.hover(mx, my, chipX(i), cy - 10, chipX(i) + 26, cy + 10)) {
                Config.setInt("menu.bg", i);
                Config.save();
                return;
            }
        }
        for (int i = 0; i < LABELS.length; i++) {
            if (!RenderUtil.hover(mx, my, bx(), by(i), bx() + bw(), by(i) + 26)) continue;
            if (i == 0) field_146297_k.func_147108_a(new GuiSelectWorld(this));
            else if (i == 1) field_146297_k.func_147108_a(new GuiMultiplayer(this));
            else if (i == 2) field_146297_k.func_147108_a(new GuiOptions(this, field_146297_k.field_71474_y));
            else field_146297_k.func_71400_g();
            return;
        }
    }

    @Override
    protected void func_73869_a(char typedChar, int keyCode) throws IOException {
        // sem ESC na tela inicial
    }

    @Override
    public boolean func_73868_f() {
        return false;
    }
}
