package com.yrc.pos.features.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.yrc.pos.R
import com.yrc.pos.core.YrcBaseActivity
import com.yrc.pos.core.enums.DialogTheme
import com.yrc.pos.core.providers.AlertDialogProvider
import com.yrc.pos.core.services.APiManager
import com.yrc.pos.core.services.YrcBaseApiResponse
import com.yrc.pos.core.services.HttpErrorCodes
import com.yrc.pos.core.services.SessionManagement
import com.yrc.pos.core.session.Session
import com.yrc.pos.core.session.User
import com.yrc.pos.features.dashboard.DashboardActivity
import com.yrc.pos.features.enclosure_clock_tower.EnclosureClockTowerFragment
import com.yrc.pos.features.enclosure_clock_tower.EnclosureClockTowerPrintingActivity
import com.yrc.pos.features.enclosure_g_and_p.EnclosureGandPFragment
import com.yrc.pos.features.enclosure_g_and_p.EnclosureGandPPrintingActivity
import com.yrc.pos.features.forget_password.ForgetPasswordActivity
import com.yrc.pos.features.login.login_service.LoginRequest
import com.yrc.pos.features.login.login_service.LoginResponse
import com.yrc.pos.features.profile.get_profile_service.GetProfileResponse
import com.yrc.pos.features.signup.HelloUserActivity
import com.yrc.pos.features.signup.SignupActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.editText_password


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
            handleLoginResponse(apiResponse)
        }
        if (apiResponse is GetProfileResponse) {
            User.saveUserPrice(apiResponse.price!!)
            var userPrice = User.getUserPrice()
            if (userPrice != null) {
                if (userPrice.code != null) {
                    //           Toast.makeText(this@LoginActivity, "price is saved", Toast.LENGTH_LONG).show()
                } else {
                    moveToHelloScreen()
                }
            }
        }
            if (apiResponse is GetProfileResponse) {
                User.saveUserProfile(apiResponse.user!!)

                var userProfile = User.getUserProfile()
                if (userProfile != null) {
                    if (userProfile.site == 0) {

                            Toast.makeText(
                                this@LoginActivity,
                                "user showed@@@@@@@@@@@@",
                                Toast.LENGTH_LONG
                            )
                                .show()
                        moveToDashboardScreen()
                    } else {
                        moveToHelloScreen()

                    }
                }
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

    private fun priceResponse(getProfileResponse: GetProfileResponse) {
        if (User.getUserPrice()!!.code != null) {
            getProfileResponse.price?.let { Session.storePrice(it) }
            //   Toast.makeText(this@LoginActivity, "price saved", Toast.LENGTH_LONG).show()
            callGetPriceApi()
        } else {
            AlertDialogProvider.showAlertDialog(
                this,
                DialogTheme.ThemeWhite,
                getProfileResponse.message
            )
        }
    }

    private fun handleLoginResponse(loginResponse: LoginResponse) {
        if (loginResponse.code == 200) {
            Session.storeSession(loginResponse.accessToken)
            callGetProfileApi()
        } else {
            AlertDialogProvider.showAlertDialog(this, DialogTheme.ThemeWhite, loginResponse.message)
        }
    }

    private fun callGetProfileApi() {
        if (Session.isSessionAvailable()) {
            APiManager.getUserProfile(this, this)
          //  moveToDashboardScreen()
              moveToHelloScreen()
        } else {
            AlertDialogProvider.showAlertDialog(
                this,
                DialogTheme.ThemeWhite,
                getString(R.string.session_not_available)
            )
        }
    }


    private fun callGetPriceApi() {
        if (Session.isSessionAvailable()) {
            APiManager.getUserPrice(this, this)
        } else {
            AlertDialogProvider.showAlertDialog(
                this,
                DialogTheme.ThemeWhite,
                getString(R.string.session_not_available)
            )
        }
    }


    fun onCreateAccountClicked(createAccountTextView: View) {
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

    private fun moveToEnclouserGandPref() {
        val enclouserGandPrefIntent = Intent(this, EnclosureClockTowerPrintingActivity::class.java)
        startActivity(enclouserGandPrefIntent)
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
