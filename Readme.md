# Provide the ablity to filter AOSP modules with multi PRODUCT_TYPE String

When using AOSP module with Android Studio, like Setting module, multi products strings with the same id, Developers cannot build successfully with Android Studio.
This project will help you filter the unnecessary product strings.

## How to use

1. Config the needed PRODUCT_TYPE in Config.java;
2. Config the origin ROOT and output ROOT;
3. Build the project, then use the ouput ROOT res/.
