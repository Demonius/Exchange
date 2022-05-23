package by.dmitry.data.models.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "favorites",
    indices = [
        Index(value = ["abr"], unique = true)
    ]
)
data class ExchangeFavorites(
    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "abr")
    val abr: String
) {
    @ColumnInfo(name = "_id")
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L
}