package com.example.sshop_sneakershop.Account.views

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.sshop_sneakershop.Account.controllers.AccountController
import com.example.sshop_sneakershop.Account.controllers.IAccountController
import com.example.sshop_sneakershop.Account.models.Account
import com.example.sshop_sneakershop.Account.models.Payment
import com.example.sshop_sneakershop.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*


class AddPaymentActivity : AppCompatActivity() {
    private val auth = Firebase.auth
    private lateinit var accountController: IAccountController
    private var account: Account? = null

    private lateinit var editTextName: EditText
    private lateinit var editTextNumber: EditText
    private lateinit var editTextSince: EditText
    private lateinit var submitBtn: Button
    private lateinit var radioGroup: RadioGroup
    private lateinit var cardTypeImageView: ImageView
    private lateinit var picker: DatePickerDialog
    private lateinit var toolbar: MaterialToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        accountController = AccountController()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_payment_acctivity)

        val payment = Payment()

        editTextName = findViewById(R.id.AddPayment_textInputET_name)
        editTextNumber = findViewById(R.id.AddPayment_textInputET_number)
        editTextSince = findViewById(R.id.AddPayment_textInputET_since)
        submitBtn = findViewById(R.id.addPayment_button_save)
        cardTypeImageView = findViewById(R.id.AddPayment_CardType_IV)
        toolbar = findViewById(R.id.addPayment_toolbar)


        radioGroup = findViewById(R.id.AddPayment_ChooseTypeCar_RG)
        radioGroup.setOnCheckedChangeListener { _, optionId ->
            run {
                when (optionId) {
                    R.id.AddPayment_Visa_RB -> {
                        payment.type = "visa"
                        cardTypeImageView.setImageResource(R.drawable.ic_visa)
                    }
                    R.id.AddPayment_mastercard_RB -> {
                        payment.type = "master"
                        cardTypeImageView.setImageResource(R.drawable.ic_mastercard)
                    }
                    R.id.AddPayment_atm_RB -> {
                        payment.type = "atm"
                        cardTypeImageView.setImageResource(R.drawable.ic_atm)
                    }

                }
            }
        }

        editTextNumber.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(
                s: CharSequence, arg1: Int, arg2: Int,
                arg3: Int
            ) {
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {
                val initial = s.toString()
                var processed = initial.replace("\\D".toRegex(), "")
                processed = processed.replace("(\\d{4})(?=\\d)".toRegex(), "$1 ")
                if (initial != processed) {
                    s.replace(0, initial.length, processed)
                }
            }
        })

        editTextSince.setOnClickListener {
            picker = DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    val year: Int = year
                    val month: Int = month + 1
                    val day: Int = dayOfMonth
                    val calendar = Calendar.getInstance()
                    calendar.set(year, month, day)
                    payment.since = calendar.time
                    editTextSince.setText("$day/$month/$year")

                },
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            )
            picker.show()
            picker.datePicker.maxDate = System.currentTimeMillis()
        }

        submitBtn.setOnClickListener {
            if (payment.type.isNullOrEmpty()) {
                payment.type = "visa"
            }

            var notification = ""

            if (TextUtils.isEmpty(editTextName.text.toString().trim())) {
                notification = "The card holder's name must not be empty."
            } else {
                payment.name = editTextName.text.toString()
            }

            if (TextUtils.isEmpty(editTextNumber.text.toString().trim())) {
                notification = "The card number must not be empty."
            } else {
                if (editTextNumber.text.toString().length < 19) {
                    notification = "The card number must be 16 digits."
                } else {
                    payment.number = editTextNumber.text.toString()
                }
            }

            if (TextUtils.isEmpty(editTextSince.text.toString().trim())) {
                notification = "The card creation date cannot be left blank."
            }

            if (notification !== "")
                Toast.makeText(this, notification, Toast.LENGTH_SHORT).show()
            else {
                GlobalScope.launch(Dispatchers.Main) {
                    account = accountController.getUser(auth.currentUser!!.email!!)
                    if (account != null) {
                        if (account!!.payments !== null) {
                            account!!.payments!!.add(payment)
                            account = (accountController as AccountController).updateUserPayment(account!!)
                        } else {
                            val paymentList = ArrayList<Payment>()
                            paymentList.add(payment)
                            account!!.payments = paymentList
                            account = (accountController as AccountController).updateUserPayment(account!!)
                        }
                        finish()
                    }
                }
            }

        }
        toolbar.setNavigationOnClickListener { finish() }
    }
}