package com.yrc.pos.features.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.yrc.pos.R
import com.yrc.pos.core.YrcBaseActivity
import com.yrc.pos.core.services.SessionManagement
import com.yrc.pos.features.dashboard.DashboardActivity
import com.yrc.pos.features.forget_password.ForgetPasswordActivity
import com.yrc.pos.features.login.viewmodel.LoginViewModel
import com.yrc.pos.features.signup.SignupActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.android.ext.android.inject


class LoginActivity : YrcBaseActivity() {
    private val viewModel: LoginViewModel by inject()

    lateinit var session: SessionManagement

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        viewModel.observable.observe(this, androidx.lifecycle.Observer { result ->
            if (result.error == null) {
                if (result.value == true) {
                    moveToDashboardScreen()
                } else {
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun onSignInButtonClicked(signInButton: View) {
        if (checkValidations()) {
            viewModel.fetchData(
                editText_emailOrNumber.getText(),
                editText_password.getText(),
                editText_dutyNumber.getText()
            )
        }
    }

    fun onCreateAccountClicked(createAccountTextView: View) {
        val individualSignUpIntent = Intent(this, SignupActivity::class.java)
        startActivity(individualSignUpIntent)
    }

    fun onForgetPasswordClicked(forgetPasswordButton: View) {
        val forgetPasswordIntent = Intent(this, ForgetPasswordActivity::class.java)
        startActivity(forgetPasswordIntent)
    }

    private fun moveToDashboardScreen() {
        val dashboardIntent = Intent(this, DashboardActivity::class.java)
        startActivity(dashboardIntent)
        finish()
    }

    private fun checkValidations(): Boolean {
        if (editText_emailOrNumber.getText().isEmpty()) {
            editText_emailOrNumber.setError(getString(R.string.please_enter_cell_number_or_email))
            return false
        }
        if (editText_password.getText().isEmpty()) {
            editText_password.setError(getString(R.string.please_enter_password))
            return false
        }
        return true
    }
}
