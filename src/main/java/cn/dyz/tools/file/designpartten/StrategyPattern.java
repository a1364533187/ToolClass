package cn.dyz.tools.file.designpartten;

public class StrategyPattern {

    public static void main(String[] args) {
        ThemeManager themeManager = new ThemeManager(new DefaultTheme("red"));
        System.out.println(themeManager.getTheme().getColor());
    }
}

class ThemeManager {

    private Theme theme;

    ThemeManager(Theme theme) {
        this.theme = theme;
    }

    public Theme getTheme() {
        return theme;
    }
}

abstract class Theme {

    abstract String getColor();
}

class DefaultTheme extends Theme {

    private String color;

    DefaultTheme(String color) {
        this.color = color;
    }

    @Override
    public String getColor() {
        return color;
    }
}
