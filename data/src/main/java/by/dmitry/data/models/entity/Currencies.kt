package by.dmitry.data.models.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "currencies",
    indices = [
        Index(value = ["abr"], unique = true)
    ]
)
data class Currencies(
    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "abr")
    val abr: String,

    @ColumnInfo(name = "favorite")
    var isFavorite: Boolean = false
) {
    @ColumnInfo(name = "_id")
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L
}