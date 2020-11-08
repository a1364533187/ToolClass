package design.pattern.builder;

//女娲造人
public class Human {

    private Head head;
    private Body body;
    private Hand hand;
    private Leg leg;

    private Human() {
        throw new IllegalStateException("Not support init");
    }

    private Human(Builder builder) {
        head = builder.head;
        body = builder.body;
        hand = builder.hand;
        leg = builder.leg;
    }

    public Head getHead() {
        return head;
    }

    public Body getBody() {
        return body;
    }

    public Hand getHand() {
        return hand;
    }

    public Leg getLeg() {
        return leg;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {

        private Head head;
        private Body body;
        private Hand hand;
        private Leg leg;

        private Builder() {

        }

        public Builder head(String name) {
            head = new Head();
            head.setName(name);
            return this;
        }

        public Builder body(String name) {
            body = new Body();
            body.setName(name);
            return this;
        }

        public Builder hand(String name) {
            hand = new Hand();
            hand.setName(name);
            return this;
        }

        public Builder leg(String name) {
            leg = new Leg();
            leg.setName(name);
            return this;
        }

        public Human build() {
            return new Human(this);
        }
    }

    static class Head {

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Head{" + "name='" + name + '\'' + '}';
        }
    }

    static class Hand {

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Hand{" + "name='" + name + '\'' + '}';
        }
    }

    static class Body {

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Body{" + "name='" + name + '\'' + '}';
        }
    }

    static class Leg {

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Leg{" + "name='" + name + '\'' + '}';
        }
    }

    @Override
    public String toString() {
        return "Human{" + "head=" + head + ", body=" + body + ", hand=" + hand + ", leg=" + leg
                + '}';
    }
}
