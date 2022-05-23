package by.dmitry.exchange.di.modules

import android.app.Application
import android.content.Context
import androidx.room.Room
import by.dmitry.data.db.AppDatabase
import by.dmitry.exchange.di.scope.PerApplication
import dagger.Module
import dagger.Provides
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory

@Module(
    includes = [
        ViewModelModule::class,
        DataModule::class,
        DomainModule::class,
        ToolsModule::class,
        RepositoryModule::class
    ]
)
object ApplicationModule {

    private const val NAME_DATABASE = "db_storage.db"
    private const val SECURE_SHARED_PREFS = "PRIVATE_DATA_USER"
    private val USER_PASSPHRASE = "n_#]~4NJ^FFxa54_".toCharArray()

    @Provides
    @PerApplication
    fun provideApplicationContext(application: Application): Context =
        application.applicationContext


    @Provides
    @PerApplication
    fun providesRoom(applicationContext: Context): AppDatabase {
        val passphrase = SQLiteDatabase.getBytes(USER_PASSPHRASE)
        val factory = SupportFactory(passphrase)
        return Room.databaseBuilder(applicationContext, AppDatabase::class.java, NAME_DATABASE)
            .addMigrations()
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
}