package com.ankotest.kotlin.brunodegan.firstankotest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.content_sms.*
import org.jetbrains.anko.sendSMS

/**
 * Created by brunodegan on 20-06-2017.
 */

class SmsActivity : AppCompatActivity() {
	
	val texto_inicial = "inicio_texto"
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.sms_activity)
		
		supportActionBar?.setDisplayHomeAsUpEnabled(true)
		supportActionBar?.setDisplayShowHomeEnabled(true)
		
		et_mensagem.text = Editable.Factory.getInstance()
				.newEditable(intent.getStringExtra(MainActivity.texto_inicial))
	}
	
	fun enviarSms(view: View?) {
		sendSMS("92981141974",et_mensagem.text.toString())
	}
	
	override fun onOptionsItemSelected(itemSelected: MenuItem?): Boolean {
		if(itemSelected?.itemId == android.R.id.home) {
			finish()
			return true
		}
		
		return super.onOptionsItemSelected(itemSelected)
	}
}