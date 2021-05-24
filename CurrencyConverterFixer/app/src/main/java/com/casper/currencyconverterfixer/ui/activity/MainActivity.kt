package com.casper.currencyconverterfixer.ui.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.casper.currencyconverterfixer.R
import com.casper.currencyconverterfixer.databinding.ActivityMainBinding
import com.casper.currencyconverterfixer.ui.fragment.bottomsheet.FragmentSelectCurrency
import com.casper.currencyconverterfixer.viewmodel.ModelConverter
import com.casper.currencyconverterfixer.viewmodel.ModelCurrency
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Suppress("UNUSED_PARAMETER")
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
//    val binding:ActivityMainBinding by lazy { DataBindingUtil.setContentView(this, R.layout.activity_main) }

    private lateinit var modelCurrency: ModelCurrency
    private val modelConvert: ModelConverter by lazy {
        ViewModelProvider(
            this,
            ModelConverter.Factory(application)
        ).get(ModelConverter::class.java)
    }
    var activeCurrency = ActiveCurrency.FROM_CURRENCY


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.apply {
            lifecycleOwner = this@MainActivity
            modelConverter = modelConvert
        }


        initViewModel()
        clickListeners()
    }

    fun openCalculatorActivity(view: View) {
        val intent = Intent(this, CalculatorActivity::class.java)
        startActivity(intent)
    }

    private fun initViewModel() {

//        CURRENCY VIEWMODEL INIT
        val viewModelFactory = ModelCurrency.Factory(application, this)
        modelCurrency = ViewModelProvider(this, viewModelFactory).get(ModelCurrency::class.java)
        modelCurrency.curCurrency.observe(this, {
            it?.getContentIfNotHandled().let { c ->
                when (activeCurrency) {
                    ActiveCurrency.FROM_CURRENCY -> {
                        modelConvert.setFromCurrency(c!!)
                    }
                    ActiveCurrency.TO_CURRENCY -> {
                        modelConvert.setToCurrency(c!!)
                    }
                }
                //Checking if user has already entered a value. Make it unity if user hasn't added something
                val fromAmount = binding.fromCurrencyInput.text.toString().trim()
                if(fromAmount.isEmpty())
                    modelConvert.setFromAmount("1")
                else
                    convert()
            }
        })

        //OBSERVE CHANGES IN THE "FROM" INPUT FIELDS FOR CONVERSION
        modelConvert.fromAmount.observe(this, {
            it?.let {
                convert()
            }
        })
    }

    private fun convert(){
        CoroutineScope(Dispatchers.Main).launch {
            modelConvert.convert()
        }
    }

    private fun clickListeners() {

        //SETTING THE CURRENT CLICK CURRENCY CONVERSION BUTTON
        binding.fromCurrencyTextWrapper.setOnClickListener{
            activeCurrency = ActiveCurrency.FROM_CURRENCY
            FragmentSelectCurrency().apply {
                show(supportFragmentManager, tag)
            }
        }
        binding.toCurrencyTextWrapper.setOnClickListener{
            activeCurrency = ActiveCurrency.TO_CURRENCY
            FragmentSelectCurrency().apply {
                show(supportFragmentManager, tag)
            }
        }


        //CONVERT CURRENCY FOR EVERY TEXT ENTERED BY THE USER
        binding.fromCurrencyInput.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.trim().isNotEmpty())
                    modelConvert.setFromAmount(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}

        })
    }

    private fun convertCurrency() {
        val fromAmount = binding.fromCurrencyInput.text.toString().trim();
        if (fromAmount.isEmpty()){
            modelConvert.setFromAmount("1")
        }else{
            modelConvert.setFromAmount(fromAmount)
        }
    }

}
//CHECK THE CURRENT CURRENCY TO LISTEN TO
enum class ActiveCurrency {
    FROM_CURRENCY,
    TO_CURRENCY
}