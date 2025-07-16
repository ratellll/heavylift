package com.workout.heavylift.util;

public class OneRepMaxUtil {

    public static double calculate(double weight, int reps) {
        if (reps <= 1) return weight;
        return weight * (1 + reps / 30.0);
    }
}
