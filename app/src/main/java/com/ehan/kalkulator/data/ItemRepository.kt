package com.ehan.kalkulator.data

import kotlinx.coroutines.flow.Flow

class ItemRepository(private val itemDao: ItemDao) {
    val allItems: Flow<List<ItemEntity>> = itemDao.getAllItems()

    suspend fun insert(item: ItemEntity) {
        itemDao.insertItem(item)
    }
}