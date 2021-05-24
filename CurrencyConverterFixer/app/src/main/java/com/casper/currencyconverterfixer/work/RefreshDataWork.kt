package com.casper.currencyconverterfixer.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.casper.currencyconverterfixer.repository.RepoCurrency
import com.casper.currencyconverterfixer.room.DatabaseRoom
import retrofit2.HttpException

class RefreshDataWorker(appContext: Context, params: WorkerParameters): CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        val database = DatabaseRoom.getDatabaseInstance(applicationContext)

        val getSymbols = RepoCurrency(database)

        return try {
            getSymbols.getCurrency()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }



    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

}