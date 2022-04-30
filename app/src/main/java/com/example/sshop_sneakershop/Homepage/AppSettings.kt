package com.example.sshop_sneakershop.Homepage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.GridLayoutManager
import com.example.sshop_sneakershop.Product.models.Product
import com.example.sshop_sneakershop.Product.views.ProductAdapter
import com.example.sshop_sneakershop.R
import com.example.sshop_sneakershop.databinding.ActivityAppSettingBinding
import com.example.sshop_sneakershop.databinding.ActivitySearchBinding
import java.util.*

class AppSettings: AppCompatActivity() {
    private lateinit var binding: ActivityAppSettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppSettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.appSettingToolbar.setNavigationOnClickListener {
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
            finish()
        }

        binding.appSettingsSwitchChangeLanguage.isChecked = LocaleUtils.getLocale(this).language != "en"

        binding.appSettingsSwitchChangeLanguage.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (compoundButton.isPressed) {
                if (isChecked) {
                    LocaleUtils.setLocale(this, "vi")
                    recreate()
                } else {
                    LocaleUtils.setLocale(this, "en")
                    recreate()

                }

            }
        }

        binding.appSettingsSwitchDarkMode.setOnCheckedChangeListener{
            compoundButton, isChecked ->
            if (compoundButton.isPressed) {
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    recreate()
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    recreate()
                }
            }
        }
    }

    override fun attachBaseContext(newBase: Context?) {
        val saveLocale = LocaleUtils.getLocale(newBase as Context)
        newBase.resources.configuration.setLocale(saveLocale)
        super.attachBaseContext(newBase)
    }

    object LocaleUtils {
        private var sLocale: Locale? = null
        fun setLocale(context: Context, localeStr: String) {
            sLocale = Locale(localeStr)
            Locale.setDefault(sLocale)

            val ref = context.getSharedPreferences("Settings", Context.MODE_PRIVATE)
            ref.edit().putString("Lang", localeStr).apply()
        }

        fun getLocale(context: Context): Locale {
            return context.getSharedPreferences("Settings", Context.MODE_PRIVATE).getString("Lang", "en")
                ?.let { Locale(it) }!!
        }
    }
}