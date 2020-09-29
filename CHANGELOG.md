Change Log
==========
## Version 1.0.1-alpha02
 **WARNING: THIS IS A NOT-FULLY TESTED VERSION, USE AT YOUR OWN RISK!** 
 * Fix terrible issue about the default CurrencyUnitDataProvider initialization error.
 * Clean up Android Log code.
 * Update Bintray Publish Plugin related configuration.

## Version 1.0.1-alpha01

 **WARNING: THIS IS A NOT-FULLY TESTED VERSION, USE AT YOUR OWN RISK!** 
 * Update code and data from version 1.0.1 of Joda Money.
   For release notes of Joda Money check its [changelog](https://www.joda.org/joda-money/changes-report.html#a1.0.1).
 * Use [ICU library](https://developer.android.com/guide/topics/resources/icu4j-framework) on above API 24 runtime to make more use of system library,
   and fully applied to [ULocale](https://developer.android.com/reference/android/icu/util/ULocale) of ICU library to support two-way conversion with CurrencyUnit.
 * Full annotation support for better lint check and Kotlin interoperability.
 * Add Method to use custom file or provider to initialize library.
 * Update code base to Java 8 to take advantage of Stream API and try-with-resources function, also update AGP and dependencies.
 * Improved documentation and more!


## Version 0.12

 * Initial release. Includes code from version 0.12 of Joda Money.
   For release notes of Joda Money check its [changelog](https://www.joda.org/joda-money/changes-report.html#a0.12).