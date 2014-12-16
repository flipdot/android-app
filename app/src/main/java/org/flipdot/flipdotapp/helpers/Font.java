package org.flipdot.flipdotapp.helpers;

public class Font {
    private String _fontFile;
    public Font(String fontFile){
        this._fontFile = fontFile;
    }

    public String getFontFile(){
        return this._fontFile;
    }

    public static Font WhiteRabbit = new Font("fonts/whitrabt.ttf");
    public static Font FontAwesome = new Font("fonts/fontawesome-webfont.ttf");
    public static Font IsocpeurRegular = new Font("fonts/isocpeur_regular.ttf");
}
