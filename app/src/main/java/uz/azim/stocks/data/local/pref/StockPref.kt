package uz.azim.stocks.data.local.pref

import android.content.Context


const val PREF_NAME = "uz.stock_pref"
private const val KEY_LAST_SYNCED = "last_synced"
private const val KEY_LAST_SYNCED_STAT = "last_synced_stat"

class StockPref(context: Context) {

    private val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun getLastSyncedTime(): Long {
        return pref.getLong(KEY_LAST_SYNCED, -1)
    }

    fun getLastSyncedTimeStats(): Long {
        return pref.getLong(KEY_LAST_SYNCED_STAT, -1)
    }

    fun saveSyncedTime(time: Long) {
        pref.edit().putLong(KEY_LAST_SYNCED, time).apply()
    }

    fun saveSyncedTimeStats(timeMillis: Long) {
        pref.edit().putLong(KEY_LAST_SYNCED_STAT, timeMillis).apply()
    }

}