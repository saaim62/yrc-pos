package com.yrc.pos.features.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.yrc.pos.R
import com.yrc.pos.core.YrcBaseActivity
import com.yrc.pos.core.enums.DialogTheme
import com.yrc.pos.core.providers.AlertDialogProvider
import com.yrc.pos.core.services.APiManager
import com.yrc.pos.core.services.HttpErrorCodes
import com.yrc.pos.core.services.SessionManagement
import com.yrc.pos.core.services.YrcBaseApiResponse
import com.yrc.pos.core.session.Session
import com.yrc.pos.core.session.User
import com.yrc.pos.features.dashboard.DashboardActivity
import com.yrc.pos.features.forget_password.ForgetPasswordActivity
import com.yrc.pos.features.login.login_service.LoginRequest
import com.yrc.pos.features.login.login_service.LoginResponse
import com.yrc.pos.features.signup.SignupActivity
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : YrcBaseActivity() {
    lateinit var session: SessionManagement
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun onSignInButtonClicked(signInButton: View) {
        if (checkValidations()) {
            val loginRequest = LoginRequest()
            loginRequest.driver = editText_emailOrNumber.getText()
            loginRequest.pin = editText_password.getText()
            loginRequest.dutyNumber = editText_dutyNumber.getText()
            APiManager.loginApi(this, this, loginRequest)
            Session.clearSession()
        }
    }

    override fun onApiSuccess(apiResponse: YrcBaseApiResponse) {
        super.onApiSuccess(apiResponse)
        if (apiResponse is LoginResponse) {
            User.saveUserPrice(apiResponse.price!!)
            User.saveUserProfile(apiResponse.user!!)
            moveToDashboardScreen()
        }
    }

    override fun onApiFailure(errorCode: Int) {
        if (errorCode == HttpErrorCodes.Unauthorized.code) {
            AlertDialogProvider.showAlertDialog(
                this,
                DialogTheme.ThemeWhite,
                getString(R.string.password_incorrect)
            )
        } else {
            super.onApiFailure(errorCode)
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

    fun moveToDashboardScreen() {
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
