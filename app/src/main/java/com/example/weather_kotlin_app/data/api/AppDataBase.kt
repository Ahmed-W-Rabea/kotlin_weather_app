package com.example.weather_kotlin_app.data.api

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import android.util.Log
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.weather_kotlin_app.data.model.LocationEntity
import com.example.weather_kotlin_app.data.model.WeatherEntity
// File: WeatherDatabase.kt

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        val tableName = "weather"

        // Function to check if a column exists
        fun columnExists(database: SupportSQLiteDatabase, columnName: String): Boolean {
            val cursor = database.query("PRAGMA table_info($tableName)")
            val columnIndex = cursor.getColumnIndex("name")
            while (cursor.moveToNext()) {
                if (columnIndex != -1) {
                    val name = cursor.getString(columnIndex)
                    if (name == columnName) {
                        cursor.close()
                        return true
                    }
                }
            }
            cursor.close()
            return false
        }

        // Add columns only if they do not already exist
        if (!columnExists(database, "uv")) {
            database.execSQL("ALTER TABLE $tableName ADD COLUMN uv REAL NOT NULL DEFAULT 0.0")
        }
        if (!columnExists(database, "localTime")) {
            database.execSQL("ALTER TABLE $tableName ADD COLUMN localTime TEXT NOT NULL DEFAULT ''")
        }
        if (!columnExists(database, "country")) {
            database.execSQL("ALTER TABLE $tableName ADD COLUMN country TEXT NOT NULL DEFAULT ''")
        }
        if (!columnExists(database, "city")) {
            database.execSQL("ALTER TABLE $tableName ADD COLUMN city TEXT NOT NULL DEFAULT ''")
        }
        if (!columnExists(database, "conditionText")) {
            database.execSQL("ALTER TABLE $tableName ADD COLUMN conditionText TEXT NOT NULL DEFAULT ''")
        }
        if (!columnExists(database, "temperature")) {
            database.execSQL("ALTER TABLE $tableName ADD COLUMN temperature REAL NOT NULL DEFAULT 0.0")
        }
        if (!columnExists(database, "humidity")) {
            database.execSQL("ALTER TABLE $tableName ADD COLUMN humidity REAL NOT NULL DEFAULT 0.0")
        }
        // Add other columns as needed
    }
}



@Database(entities = [WeatherEntity::class, LocationEntity::class], version = 2, exportSchema = false)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun weatherDao(): WeatherDao

    companion object {
        @Volatile
        private var INSTANCE: WeatherDatabase? = null

        fun getDatabase(context: Context): WeatherDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WeatherDatabase::class.java,
                    "weather_database"
                )
                    .addMigrations(MIGRATION_1_2)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    // Method to log table schema
    fun logTableSchema() {
        this.openHelper.writableDatabase.let { database ->
            logTableSchema(database)
        }
    }

    // Function to log schema details
    private fun logTableSchema(database: SupportSQLiteDatabase) {
        val cursor = database.query("PRAGMA table_info(weather)")
        val nameColumnIndex = cursor.getColumnIndexOrThrow("name")
        val typeColumnIndex = cursor.getColumnIndexOrThrow("type")

        while (cursor.moveToNext()) {
            val columnName = cursor.getString(nameColumnIndex)
            val columnType = cursor.getString(typeColumnIndex)
            Log.d("DatabaseSchema", "Column: $columnName, Type: $columnType")
        }
        cursor.close()
    }

}
