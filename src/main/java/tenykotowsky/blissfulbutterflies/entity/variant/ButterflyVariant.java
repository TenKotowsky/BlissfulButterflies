package tenykotowsky.blissfulbutterflies.entity.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum ButterflyVariant {
    MONARCH(0),
    BLUE(1),
    PURPLE(2),
    ORANGE(3),
    WHITE(4);

    private static final ButterflyVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.
            comparingInt(ButterflyVariant::getId)).toArray(ButterflyVariant[]::new);
    private final int id;

    ButterflyVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static ButterflyVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
