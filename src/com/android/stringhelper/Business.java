package com.android.stringhelper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Business {

    public static void filterProducts(String oriPath, String destPath, String filterProducts)
            throws IOException {
        File oriFile = new File(oriPath);
        File destFile = new File(destPath);

        if (oriFile.isDirectory()) {
            if (destFile.exists()) {
                destFile.delete();
            }
            destFile.mkdirs();

            for (String filePath : oriFile.list()) {
                filterProducts(oriPath + File.separator + filePath,
                        destPath + File.separator + filePath, filterProducts);
            }
        } else {
            File resFile = new File(destPath);
            if (!resFile.exists()) {
                resFile.createNewFile();
            }
            copyDocument(oriPath, destPath, filterProducts);
        }
    }

    public static void copyDocument(String file, String newFile, String filterProducts)
            throws IOException {
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;

        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            bufferedWriter = new BufferedWriter(new FileWriter(newFile));

            String reader = "";
            boolean nextIsComment = false;
            boolean nextInvalid = false;

            while ((reader = bufferedReader.readLine()) != null) {
                reader = reader.trim();
                if (isOneLineComment(reader)) {
                    continue;
                }
                if (!nextInvalid && !nextIsComment && !reader.startsWith("<?xml")) {
                    bufferedWriter.write("\n");
                }
                if (nextIsComment) {
                    int idx = -1;
                    if (-1 != (idx = reader.indexOf("-->"))) {
                        reader = reader.substring(idx + 3, reader.length());
                        nextIsComment = false;
                    } else {
                        continue;
                    }
                }
                if (reader.startsWith("<!--") && !reader.endsWith("-->")) {
                    int idx = -1;
                    if (-1 != (idx = reader.indexOf("-->"))) {
                        reader = reader.substring(idx + 3, reader.length());
                    } else {
                        nextIsComment = true;
                        continue;
                    }
                }
                boolean curInvalid = false;
                boolean insertBr = false;
                if (nextInvalid) {
                    if (reader.endsWith("</string>")) {
                        nextInvalid = false;
                        continue;
                    } else if (reader.contains("</string>")) {
                        int idx = reader.indexOf("</string>");
                        insertBr = true;
                        reader = reader.substring(idx + 9, reader.length());
                        nextInvalid = false;
                    } else {
                        continue;
                    }
                }
                // Filter product
                for (String character : filterProducts.split(" ")) {
                    if (reader.contains("product=" + "\"" + character + "\"")) {

                        if (reader.endsWith("</string>")) {
                            curInvalid = true;
                            break;
                        } else if (reader.contains("</string>")) {
                            int idx = reader.indexOf("</string>");
                            insertBr = true;
                            reader = reader.substring(idx + 9, reader.length());
                            curInvalid = false;
                            nextInvalid = false;
                            break;
                        } else {
                            curInvalid = true;
                            nextInvalid = true;
                            break;
                        }
                    }
                }
                if (curInvalid) {
                    continue;
                }
                if (insertBr) {
                    bufferedWriter.write("\n");
                }
                bufferedWriter.write(reader);
            }
        } finally {
            bufferedReader.close();
            bufferedWriter.close();
        }
    }

    private static boolean isOneLineComment(String reader) {
        boolean result = false;
        if (reader.startsWith("<!--") && reader.endsWith("-->")) {
            result = true;
        } else if (reader.startsWith("//")) {
            result = true;
        }

        return result;
    }
}
