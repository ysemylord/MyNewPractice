package com;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.function.DoubleConsumer;

public class TTT {
    public static void main(String[] args) {

        double[] points = new double[]{

                0,
                9,
                99,
                999,
                999.9,
                9999,
                99999,
                999999,

                1000000,

                9999999,
                99999999,
                999999999,

                1000000000,
                1010000500,
                1110500000,
                1114500000,
                1115500000,
                1116500000,
                1117500000,
                1118500000,
                1119500000,

                1920000,
                17280511,
                155520000,

                1399680000,
                12597120000f,
                15552000000f
        };
        Arrays.stream(points).forEach(new DoubleConsumer() {
            @Override
            public void accept(double v) {
                System.out.println("args = " + hit1Billion(v));
            }
        });

    }

    public static String hit1Billion(double point) {
        String showedPrice;

        if (point < 1000000) {//less than 1 million
            showedPrice = getShowPriceFLOOR(point);
        } else if (point < 1000000000) { //less than 1 billion
            point = point / 1000000;
            showedPrice = roundDownToThreeDecimal(point) + "M";
        } else {//reach 1 billion
            point = point / 1000000000;
            showedPrice = roundDownToThreeDecimal(point) + "B";
        }
        return showedPrice;
    }

    public static final String PATTEN_POINT = "###,###";

    public static String getShowPriceFLOOR(double data) {
        DecimalFormat df = new DecimalFormat(PATTEN_POINT);
        df.setRoundingMode(RoundingMode.DOWN);
        return df.format(data);
    }

    public static String roundDownToThreeDecimal(double number) {
        DecimalFormat format = new DecimalFormat("0.000");
        format.setRoundingMode(RoundingMode.DOWN);
        String formatString = format.format(number);
        return formatString.substring(0,5);
    }



}
