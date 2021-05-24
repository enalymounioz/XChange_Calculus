package com.casper.currencyconverterfixer.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.casper.currencyconverterfixer.room.TableNames
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = TableNames.TABLE_CURRENCY)
class Currency (
    @PrimaryKey
    @ColumnInfo(name = "symbol")
    val symbol: String,
    var country: String,
    var flag: String = "",
):Parcelable{

    fun symbolAndName()= "$symbol - $country"
}