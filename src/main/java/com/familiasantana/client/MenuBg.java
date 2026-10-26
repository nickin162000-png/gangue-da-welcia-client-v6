package com.familiasantana.client;

import java.util.Random;

/** Fundos da tela inicial. 0 = A (classico com particulas), 1 = B (aurora). */
public class MenuBg {
    public static final String[] NAMES = {"A - Classico", "B - Aurora"};

    private static final int N = 50;
    private static final float[][] P = new float[N][4];

    static {
        Random r = new Random(42);
        for (float[] p : P) {
            p[0] = r.nextFloat();
            p[1] = r.nextFloat();
            p[2] = 0.1F + r.nextFloat() * 0.45F;
            p[3] = 0.8F + r.nextFloat() * 1.8F;
        }
    }

    public static int style() {
        int s = Config.getInt("menu.bg", 0);
        return s < 0 || s >= NAMES.length ? 0 : s;
    }

    public static void full(int style, float w, float h, int accent) {
        float t = (System.currentTimeMillis() % 3600000L) / 1000F;
        if (style == 1) aurora(w, h, accent, t);
        else classic(w, h, accent, t);
    }

    private static void classic(float w, float h, int accent, float t) {
        RenderUtil.gradient(0, 0, w, h, 0xFF0B0B10, 0xFF181426);
        RenderUtil.circle(w * 0.15F, h * 0.85F, 190, RenderUtil.withAlpha(accent, 0x16));
        RenderUtil.circle(w * 0.88F, h * 0.15F, 150, RenderUtil.withAlpha(accent, 0x10));
        float span = h + 8F;
        for (float[] p : P) {
            float y = h + 4F - ((p[1] * span + t * p[2] * 60F) % span);
            RenderUtil.circle(p[0] * w, y, p[3], 0x30FFFFFF);
        }
    }

    private static void aurora(float w, float h, int accent, float t) {
        RenderUtil.gradient(0, 0, w, h, 0xFF06070B, 0xFF0E1B2C);
        int second = RenderUtil.lerpColor(accent, 0xFF2D9CFF, 0.6F);
        int third = RenderUtil.lerpColor(accent, 0xFFFF6BCB, 0.5F);
        RenderUtil.circle(w * (0.30F + 0.12F * (float) Math.sin(t * 0.35)), h * (0.40F + 0.12F * (float) Math.cos(t * 0.28)),
                210, RenderUtil.withAlpha(accent, 0x1C));
        RenderUtil.circle(w * (0.72F + 0.10F * (float) Math.cos(t * 0.30)), h * (0.35F + 0.15F * (float) Math.sin(t * 0.22)),
                190, RenderUtil.withAlpha(second, 0x1A));
        RenderUtil.circle(w * (0.50F + 0.20F * (float) Math.sin(t * 0.18)), h * (0.85F + 0.05F * (float) Math.cos(t * 0.4)),
                230, RenderUtil.withAlpha(third, 0x14));
        // raios de luz diagonais
        for (int i = 0; i < 7; i++) {
            float x = ((i * 0.17F + t * 0.015F) % 1.2F - 0.1F) * w;
            RenderUtil.line(x, -10, x - h * 0.45F, h + 10, 26F + i * 4F, 0x07FFFFFF);
        }
        // brilho no horizonte
        RenderUtil.gradient(0, h * 0.72F, w, h, 0x00000000, RenderUtil.withAlpha(accent, 0x26));
    }

    /** Miniatura para a tela de configuracoes. */
    public static void thumb(int style, float x, float y, float w, float h, int accent) {
        if (style == 1) {
            RenderUtil.gradient(x, y, x + w, y + h, 0xFF06070B, 0xFF0E1B2C);
            RenderUtil.circle(x + w * 0.3F, y + h * 0.5F, h * 0.32F, RenderUtil.withAlpha(accent, 0x55));
            RenderUtil.circle(x + w * 0.7F, y + h * 0.45F, h * 0.28F,
                    RenderUtil.withAlpha(RenderUtil.lerpColor(accent, 0xFF2D9CFF, 0.6F), 0x55));
            RenderUtil.gradient(x, y + h * 0.7F, x + w, y + h, 0x00000000, RenderUtil.withAlpha(accent, 0x44));
        } else {
            RenderUtil.gradient(x, y, x + w, y + h, 0xFF0B0B10, 0xFF181426);
            RenderUtil.circle(x + w * 0.22F, y + h * 0.62F, h * 0.25F, RenderUtil.withAlpha(accent, 0x30));
            for (int i = 0; i < 9; i++) {
                RenderUtil.circle(x + 8 + (i * 37 % (int) (w - 16)), y + 6 + (i * 23 % (int) (h - 12)), 1F, 0x66FFFFFF);
            }
        }
    }
}
