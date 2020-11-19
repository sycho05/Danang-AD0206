package com.aqua.aquascape.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope

@Database(entities = arrayOf(Barang::class), version = 1, exportSchema = false)
public abstract class AquascapeDatabase : RoomDatabase() {

    abstract fun dao(): Dao

    companion object {
        @Volatile
        private var INSTANCE: AquascapeDatabase? = null

        fun getDatabase(
            context: Context,
            viewModelScope: CoroutineScope
        ): AquascapeDatabase {
            val tempInstance =
                INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AquascapeDatabase::class.java,
                    "aquascape database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }

        fun getInstance(context: Context): AquascapeDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AquascapeDatabase::class.java, "aquascape_database.db"
            )
                .build()
    }
}
