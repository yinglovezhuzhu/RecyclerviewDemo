package com.owen.recyclerview.demo

import androidx.recyclerview.selection.ItemKeyProvider

/**
 *
 * <br/>Author：yunying.zhang
 * <br/>Email: yunyingzhang@rastar.com
 * <br/>Date: 2020/9/3
 */
class ItemStringKeyProvider(var adapter: SelectionAdapter): ItemKeyProvider<String>(ItemKeyProvider.SCOPE_MAPPED) {

    override fun getKey(position: Int): String? {
        return adapter.data[position]
    }

    override fun getPosition(key: String): Int {
        return adapter.data.indexOf(key)
    }
}