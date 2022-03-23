package com.example.sshop_sneakershop.homepage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.viewpager.widget.PagerAdapter
import com.example.sshop_sneakershop.R
import java.util.*


 class BannerAdapter(// Context object
     var context: Context, // Array of images
     private var images: IntArray
) :
    PagerAdapter() {
    // Layout Inflater
    private var mLayoutInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
     override fun getCount(): Int {
        // return the number of images
        return images.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as LinearLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        // inflating the item.xml
        val itemView: View = mLayoutInflater.inflate(R.layout.home_banner, container, false)

        // referencing the image view from the item.xml file
        val imageView = itemView.findViewById<View>(R.id.imageViewMain) as ImageView

        // setting the image in the imageView
        imageView.setImageResource(images[position])

        // Adding the View
        Objects.requireNonNull(container).addView(itemView)
        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }

 }