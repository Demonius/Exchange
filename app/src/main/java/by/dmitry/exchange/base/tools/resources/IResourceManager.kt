package by.dmitry.exchange.base.tools.resources


interface IResourceManager {

    fun getText(resId: Int): CharSequence

    fun getString(resId: Int): String

    fun getString(resId: Int, vararg formatArgs: Any): String

    fun getColor(id: Int): Int

    fun getDimensionPixelSize(dimenRes: Int): Int

    fun getStringArray(resId: Int): Array<String>

}