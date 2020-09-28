# Experimental, not tested
-keep class wonton.jodamoney.R$raw { *; }
-keep class org.joda.money.** { *; }
-keep class org.joda.convert.** { *; }
-keep interface org.joda.convert.** { *; }
-dontwarn org.joda.convert.FromString
-dontwarn org.joda.convert.ToString