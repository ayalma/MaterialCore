package material.core

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View

/**
 * Created by ali on 7/3/2018.
 */
class MaterialLayoutInflater(original: LayoutInflater?, context: Context?) : LayoutInflater(original, context) {
    override fun cloneInContext(context: Context?): LayoutInflater = MaterialLayoutInflater(this, context)
    override fun onCreateView(name: String, attrs: AttributeSet): View =
            if (name[0].isUpperCase() ) createView(name, "mat.widget.", attrs) else super.onCreateView(name, attrs)

    override fun onCreateView(parent: View?, name: String, attrs: AttributeSet): View =
            if (name[0].isUpperCase()) createView(name, "mat.widget.", attrs) else super.onCreateView(parent, name, attrs)
}