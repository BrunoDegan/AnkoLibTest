package com.ankotest.kotlin.brunodegan.firstankotest

import android.Manifest
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.maxcruz.reactivePermissions.ReactivePermissions
import com.maxcruz.reactivePermissions.entity.Permission
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import org.jetbrains.anko.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
	
	val REQUEST_CODE = 554
	
	companion object {
		val texto_inicial = "inicio_texto"
	}
	
	val reacPermissions = ReactivePermissions(this,REQUEST_CODE)
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		
		
		setSupportActionBar(toolbar)
		
		drawer_layout.setOnClickListener { view ->
			Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
					.setAction("Action", null).show()
		}
		
		
		val toggle = ActionBarDrawerToggle(
				this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
		drawer_layout.addDrawerListener(toggle)
		toggle.syncState()
		
		nav_view.setNavigationItemSelectedListener(this)
	}
	
	override fun onBackPressed() {
		
		if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
			drawer_layout.closeDrawer(GravityCompat.START)
		} else {
			super.onBackPressed()
		}
	}
	
	override fun onCreateOptionsMenu(menu: Menu): Boolean {
		// Inflate the menu; this adds items to the action bar if it is present.
		menuInflater.inflate(R.menu.main, menu)
		return true
	}
	
	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		when (item.itemId) {
			R.id.action_settings -> return true
			else -> return super.onOptionsItemSelected(item)
		}
	}
	
	private fun makePhoneCall(){
		
		val phonePermission = Permission(
				Manifest.permission.CALL_PHONE, /* A PERMISSÃO QUE SERÁ SOLICITADA */
				R.string.explicacao_permissao, /* TEXTO QUE É EXIBIDO NA SEGUNDA TENTATIVA, CASO O USÁRIO NEGUE A PERMISSÃO */
				true /* CASO O USUÁRIO VENHA NEGAR A PERMISSÃO, ELE AINDA PODERÁ PERMANECER UTILIZANDO O APLICATIVO */
		)
		
		val sendSMSpermission = Permission(Manifest.permission.SEND_SMS,
				R.string.explicacao_permissao,
				true)
		
		val permissions = listOf(phonePermission, sendSMSpermission)
		
		reacPermissions.observeResultPermissions().subscribe { event ->
			if (event.first == Manifest.permission.CALL_PHONE && event.second)
				makeCall(getString(R.string.phone_number))
			else if (event.first == Manifest.permission.SEND_SMS && event.second)
				startActivity<SmsActivity>(texto_inicial to "Ola\n\n")
			}
				
				/*when (event.first) {
					Manifest.permission.CALL_PHONE -> makeCall("981141974")
					Manifest.permission.SEND_SMS ->
						startActivity<SmsActivity>(texto_inicial to "Ola\n\n")
					else -> print("Nothing")
				}*/
		reacPermissions.evaluate(permissions)
	}
	
	override fun onNavigationItemSelected(item: MenuItem): Boolean {
		// Handle navigation view item clicks here.
		
		
		if(item.itemId == R.id.nav_contato_urgente) {
			makePhoneCall()
		} else if(item.itemId == R.id.nav_sms) {
			startActivity<SmsActivity>(texto_inicial to "Ola\n\n")
		} else if(item.itemId == R.id.nav_email) {
			email("bmdegan@gmail.com", "Trabalho Android", "Olá/n/n")
		} else if (item.itemId == R.id.nav_compartilhar) {
			share("http://www.thiengo.com.br", "Thiengo [Calopsita]")
		} else if (item.itemId == R.id.nav_site) {
			browse("http://www.thiengo.com.br")
		}
		
		drawer_layout.closeDrawer(GravityCompat.START)
		return false
	}
	
	override fun onRequestPermissionsResult(
			requestCode: Int,
			permissions: Array<String>,
			grantResults: IntArray) {
		
		if (requestCode == REQUEST_CODE) {
			reacPermissions.receive(permissions, grantResults)
		}
	}
}
