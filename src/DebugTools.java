package src;

import java.awt.*;

public class DebugTools {

    public static void outputText(String message, Graphics graphics, int x, int y) {
        if (graphics == null) {
            return;
        }
        Font initialFont = graphics.getFont();
        Color initialColor = graphics.getColor();
        graphics.setFont(new Font ("Courier New", Font.ITALIC, 17));
        graphics.setColor(Color.WHITE);
        graphics.drawString(message, x, y);
        graphics.setFont(initialFont);
        graphics.setColor(initialColor);
    }
}
