package com.yrc.pos.features.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.yrc.pos.R
import com.yrc.pos.core.YrcBaseActivity
import com.yrc.pos.core.YrcUtils
import com.yrc.pos.core.enums.DialogTheme
import com.yrc.pos.core.providers.AlertDialogProvider
import com.yrc.pos.core.services.APiManager
import com.yrc.pos.core.services.YrcBaseApiResponse
import com.yrc.pos.core.services.HttpErrorCodes
import com.yrc.pos.core.session.Session
import com.yrc.pos.core.session.User
import com.yrc.pos.features.dashboard.DashboardActivity
import com.yrc.pos.features.forget_password.ForgetPasswordActivity
import com.yrc.pos.features.login.login_service.LoginRequest
import com.yrc.pos.features.login.login_service.LoginResponse
import com.yrc.pos.features.profile.get_profile_service.GetProfileResponse
import com.yrc.pos.features.signup.HelloUserActivity
import com.yrc.pos.features.signup.SignupActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.editText_password

class LoginActivity : YrcBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun onSignInButtonClicked(signInButton: View) {
        moveToDashboardScreen()
        if (checkValidations()) {

            var loginRequest = LoginRequest()

            if (YrcUtils.isPhoneNumber(editText_emailOrNumber.getText())) {
                loginRequest.number = editText_emailOrNumber.getText()
                loginRequest.password = editText_password.getText()
            } else {
                loginRequest.email = editText_emailOrNumber.getText()
                loginRequest.password = editText_password.getText()
            }

            Session.clearSession()
            APiManager.loginApi(this, this, loginRequest)
        }
    }

    override fun onApiSuccess(apiResponse: YrcBaseApiResponse) {
        super.onApiSuccess(apiResponse)

        if (apiResponse is LoginResponse) {
            handleLoginResponse(apiResponse)
        }

        if (apiResponse is GetProfileResponse) {

            User.setCurrentProfileOutdated(false)
            User.saveUserProfile(apiResponse.details!!)
            
            if (User.getUserProfile()!!.firstName != null && User.getUserProfile()!!.firstName!!.isNotEmpty()) {
                moveToDashboardScreen()
            } else {
                moveToHelloScreen()
            }
        }
    }

    override fun onApiFailure(errorCode: Int) {
        if (errorCode == HttpErrorCodes.Unauthorized.code) {
            AlertDialogProvider.showAlertDialog(this, DialogTheme.ThemeWhite, getString(R.string.password_incorrect))
        } else {
            super.onApiFailure(errorCode)
        }
    }

    private fun handleLoginResponse(loginResponse: LoginResponse){
        if (loginResponse.status!!) {
            Session.storeSession(loginResponse.accessToken, loginResponse.secretKey, loginResponse.tokenType)
            callGetProfileApi()
        } else {
            AlertDialogProvider.showAlertDialog(this, DialogTheme.ThemeWhite, loginResponse.message)
        }
    }

    private fun callGetProfileApi(){
        if (Session.isSessionAvailable() && User.isCurrentProfileOutdated()) {
            APiManager.getUserProfile(this, this)
        } else {
            AlertDialogProvider.showAlertDialog(this, DialogTheme.ThemeWhite, getString(R.string.session_not_available))
        }
    }

    fun onCreateAccountClicked(createAccountTextView: View){
        val individualSignUpIntent = Intent(this, SignupActivity::class.java)
        startActivity(individualSignUpIntent)
    }

    fun onContinueAsGuestClicked(guestView: View) {
        val helloUserIntent = Intent(this, HelloUserActivity::class.java)
        startActivity(helloUserIntent)
    }

    fun onForgetPasswordClicked(forgetPasswordButton: View) {
        val forgetPasswordIntent = Intent(this, ForgetPasswordActivity::class.java)
        startActivity(forgetPasswordIntent)
    }

    private fun moveToHelloScreen() {
        val helloUserIntent = Intent(this, HelloUserActivity::class.java)
        startActivity(helloUserIntent)
        finish()
    }

    private fun moveToDashboardScreen() {
        val dashboardIntent = Intent(this, DashboardActivity::class.java)
        startActivity(dashboardIntent)
        finish()
    }

    private fun checkValidations() : Boolean {

        if (editText_emailOrNumber.getText().isEmpty()) {
            editText_emailOrNumber.setError(getString(R.string.please_enter_cell_number_or_email))
            return false
        }

        if (YrcUtils.isPhoneNumber(editText_emailOrNumber.getText())) {
            if (editText_emailOrNumber.getText().length < 10) {
                editText_emailOrNumber.setError(getString(R.string.please_enter_valid_cell_number))
                return false
            }
        } else {
            if (!YrcUtils.isEmailValid(editText_emailOrNumber.getText())) {
                editText_emailOrNumber.setError(getString(R.string.please_enter_valid_email))
                return false
            }
        }

        if (editText_password.getText().isEmpty()) {
            editText_password.setError(getString(R.string.please_enter_password))
            return false
        }

        return true
    }
}
