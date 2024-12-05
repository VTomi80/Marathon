package hu.gde.marathon

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Name::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    companion object{
        fun buildDatabase(context: Context):AppDatabase = Room.databaseBuilder(
            context,AppDatabase::class.java, "marathon").fallbackToDestructiveMigration().allowMainThreadQueries().build()
    }

    abstract fun nameDao() : NameDao
}

@Dao
interface NameDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(name: Name)

    @Query("SELECT * FROM names")
    fun getAll(): List<Name>

    @Query("SELECT * FROM names WHERE id = :id LIMIT 1")
    fun getOne(id:String) : Name?
}