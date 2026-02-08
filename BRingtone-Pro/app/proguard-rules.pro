# Add project specific ProGuard rules here.
-keep class com.burak.bringtonepro.** { *; }

# Room
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-dontwarn androidx.room.paging.**
