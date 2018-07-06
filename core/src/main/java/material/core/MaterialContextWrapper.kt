package material.core

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Resources
import android.view.LayoutInflater

/**
 * Created by ali on 7/3/2018.
 */
class MaterialContextWrapper(base: Context?) : ContextWrapper(base) {
    private val resources = MaterialResources(this, assets, super.getResources().displayMetrics, super.getResources().configuration);
    private var inflater: MaterialLayoutInflater? = null

    companion object {
        @JvmStatic
        fun wrap(context: Context): Context = if (context is MaterialContextWrapper || context is MaterialContextThemeWrapper) context
        else MaterialContextWrapper(context)

    }

    override fun getResources(): Resources = resources
    override fun getSystemService(name: String?): Any {
        if (Context.LAYOUT_INFLATER_SERVICE == name) {
            if (inflater == null) inflater = MaterialLayoutInflater(LayoutInflater.from(baseContext),this)
            return inflater!!
        }
        else return super.getSystemService(name)
    }
}