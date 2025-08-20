package stages.aikidoliguehdf.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

const val DATABASE_NAME = "StagesDatabase"
@Database(entities = [Stages::class,Categories::class,Places::class,StagesCatMap::class, Favorites::class, StagesFavMap::class], version = 2)

abstract class StagesRoomDatabase : RoomDatabase() {

    abstract fun stagesDao(): StagesDao

    companion object {
        @Volatile
        private var instance: StagesRoomDatabase? = null
        fun getInstance(context: Context): StagesRoomDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context,StagesRoomDatabase::class.java, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance as StagesRoomDatabase
        }
    }
}