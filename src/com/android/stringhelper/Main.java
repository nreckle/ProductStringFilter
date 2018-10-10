package com.android.stringhelper;

import java.io.IOException;

import static com.android.stringhelper.Config.*;

public class Main {
    public static void main(String[] args) {
        try {
            Business.filterProducts(ORI_FOLDER, DEST_FOLDER, FILTER_PRODUCTS);
        } catch (IOException e) {

        }
    }
}
