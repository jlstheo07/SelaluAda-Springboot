package com.theo.SelaluAda.enums;

import java.util.Map;

public enum PlafondLevel {
    LEVEL_1(0.2, 12, Map.of(
            3, 0.04,
            6, 0.045,
            9, 0.05,
            12, 0.055
    )),
    LEVEL_2(0.4, 12, Map.of(
            3, 0.035,
            6, 0.04,
            9, 0.045,
            12, 0.055
    )),
    LEVEL_3(0.7, 12, Map.of(
            3, 0.03,
            6, 0.035,
            9, 0.04,
            12, 0.05
    )),
    LEVEL_4(1.0, 12, Map.of(
            3, 0.035,
            6, 0.045,
            9, 0.055,
            12, 0.065
    ));

    private final double plafondMultiplier;
    private final int maxTenor;
    private final Map<Integer, Double> bungaPerTenor;

    PlafondLevel(double plafondMultiplier, int maxTenor, Map<Integer, Double> bungaPerTenor) {
        this.plafondMultiplier = plafondMultiplier;
        this.maxTenor = maxTenor;
        this.bungaPerTenor = bungaPerTenor;
    }

    public double getPlafondMultiplier() {
        return plafondMultiplier;
    }

    public int getMaxTenor() {
        return maxTenor;
    }

    public double getBungaByTenor(int tenor) {
        return bungaPerTenor.getOrDefault(tenor, bungaPerTenor.get(maxTenor));
    }
}
