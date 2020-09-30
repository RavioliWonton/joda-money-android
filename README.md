joda-money-android
==================

[ ![Download](https://api.bintray.com/packages/chenjiajia9411/maven/joda-money-android/images/download.svg?version=1.0.1-alpha02) ](https://bintray.com/chenjiajia9411/maven/joda-money-android/1.0.1-alpha02/link)

This library is a version of [Joda-Money](https://github.com/JodaOrg/joda-money) built with Android in mind, inspired by [Joda-Time-Android](https://github.com/dlew/joda-time-android).

Compare to [original](https://github.com/cs-rafael-toledo/joda-money-android), this fork has these new features:
 * Updated code and data from upstream, and remain both binary and api compatible to original [Joda-Money](https://github.com/JodaOrg/joda-money).
 * Integrated with newly public [ICU library](https://developer.android.com/guide/topics/resources/icu4j-framework) bundled in Android SDK above API 24, and remain system JDK integrtation for compatibility.
 * Custom [CurrencyUnitProvider](https://www.joda.org/joda-money/apidocs/org.joda.money/org/joda/money/CurrencyUnitDataProvider.html), include a simple file provider and default database provider which specifically optimized for Android as described above.
 * Full documentation and annotation for Kotlin interoperability.
 * And much much more!
 
 **WARNING: THIS LIBRARY IS STILL IN ALPHA STAGE, NOT RECOMMEND APPLY TO ACTUAL PROJECT AND USE AT YOUR OWN RISK!**
 
 Release
 -------
 
 Available in JCenter. 
 
 ```implementation 'wonton.jodamoney:joda-money-android:1.0.1-alpha02'```
 
 License
 -------
 
  Apache-2.0 License 
