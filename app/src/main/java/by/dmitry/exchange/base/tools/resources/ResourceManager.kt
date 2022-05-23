package by.dmitry.exchange.base.tools.resources

import android.content.Context
import android.content.ContextWrapper
import androidx.annotation.DimenRes
import androidx.annotation.Px
import javax.inject.Inject

class ResourceManager @Inject constructor(context: Context) : ContextWrapper(context),
    IResourceManager {

    override fun getStringArray(resId: Int): Array<String> = resources.getStringArray(resId)

    @Px
    override fun getDimensionPixelSize(@DimenRes dimenRes: Int) =
        resources.getDimensionPixelSize(dimenRes)
}