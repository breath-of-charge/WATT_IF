package com.breathofcharge.wattif.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.breathofcharge.wattif.data.local.dao.CampingSessionDao
import com.breathofcharge.wattif.data.local.entity.CampingSessionEntity

@Database(
    entities = [CampingSessionEntity::class],
    version = 1,
    exportSchema = true,
)
abstract class WattIfDatabase : RoomDatabase() {
    abstract fun campingSessionDao(): CampingSessionDao

    companion object {
        @Volatile
        private var instance: WattIfDatabase? = null

        fun getInstance(context: Context): WattIfDatabase =
            instance ?: synchronized(this) {
                instance ?: Room
                    .databaseBuilder(
                        context.applicationContext,
                        WattIfDatabase::class.java,
                        "watt_if.db",
                    ).build()
                    .also { instance = it }
            }
    }
}
