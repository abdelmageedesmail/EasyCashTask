package com.abdelmageed.easycashtask.data.locale.db

import android.content.Context
import androidx.room.*

@Database(
    entities = [CompetitionsDatabas::class, CompetitionsDetailsDatabas::class, TeamsDatabas::class, TeamDetailsDatabas::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(CompetitionsTypeConverter::class)
abstract class RoomDataBase : RoomDatabase() {

    abstract fun competitionsDao(): CompetitionsDao

    companion object {
        @Volatile
        private var INSTANCE: RoomDataBase? = null
        fun getDatabase(context: Context): RoomDataBase {
            return (INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RoomDataBase::class.java,
                    "competitions_table"
                ).build()
                INSTANCE = instance
                instance
            })
        }
    }
}