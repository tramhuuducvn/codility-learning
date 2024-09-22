package org.example.code_tour.warm_up;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.example.util.Logger;

class RGB {
    public static final int HEX_VALUE = 256;
    public int red;
    public int green;
    public int blue;

    public RGB(String hex) {
        if (hex.startsWith("#")) {
            hex = hex.substring(1);
        }

        this.red = Integer.parseInt(hex.substring(0, 2), 16);
        this.green = Integer.parseInt(hex.substring(2, 4), 16);
        this.blue = Integer.parseInt(hex.substring(4, 6), 16);
    }

    public RGB(int r, int g, int b) {
        this.red = r;
        this.green = g;
        this.blue = b;
    }

    public RGB diff(RGB dest) {
        return new RGB(
                diffValue(this.red, dest.red),
                diffValue(this.green, dest.green),
                diffValue(this.blue, dest.blue));
    }

    private int diffValue(int a, int b) {
        int x = (b - a) % HEX_VALUE;
        if (x < 0) {
            x += HEX_VALUE;
        }
        return x;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + red;
        result = prime * result + green;
        result = prime * result + blue;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        RGB other = (RGB) obj;
        if (red != other.red)
            return false;
        if (green != other.green)
            return false;
        if (blue != other.blue)
            return false;
        return true;
    }
}

public class HexColor {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        List<RGB> src = new ArrayList<>();
        Map<RGB, Integer> deltas = new HashMap<>();
        scanner.nextLine();

        for (int i = 0; i < n; ++i) {
            src.add(new RGB(scanner.nextLine()));
        }

        for (int i = 0; i < n; ++i) {
            RGB dest = new RGB(scanner.nextLine());
            RGB key = src.get(i).diff(dest);
            if (deltas.containsKey(key)) {
                deltas.put(key, deltas.get(key) + 1);
            } else {
                deltas.put(key, 1);
            }
        }

        Integer max = 0;
        for (Integer i : deltas.values()) {
            if (max < i) {
                max = i;
            }
        }

        scanner.close();
        Logger.log(max);
        Logger.log(n);
        System.out.printf("%.2f%%", (max / (double) n) * (double) 100);
    }
}
