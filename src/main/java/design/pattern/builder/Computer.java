package design.pattern.builder;

public class Computer {

    private String cpu;
    private String screen;
    private String memory;
    private String mainboard;

    public Computer(String cpu, String screen, String memory, String mainboard) {
        this.cpu = cpu;
        this.screen = screen;
        this.memory = memory;
        this.mainboard = mainboard;
    }
}

class NewComputer {

    private String cpu;
    private String screen;
    private String memory;
    private String mainboard;

    public NewComputer() {
        throw new RuntimeException("can not init");
    }

    private NewComputer(Builder builder) {
        cpu = builder.cpu;
        screen = builder.screen;
        memory = builder.memory;
        mainboard = builder.mainboard;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {

        private String cpu;
        private String screen;
        private String memory;
        private String mainboard;

        private Builder() {
        }

        public Builder cpu(String val) {
            cpu = val;
            return this;
        }

        public Builder screen(String val) {
            screen = val;
            return this;
        }

        public Builder memory(String val) {
            memory = val;
            return this;
        }

        public Builder mainboard(String val) {
            mainboard = val;
            return this;
        }

        public NewComputer build() {
            return new NewComputer(this);
        }
    }
}
