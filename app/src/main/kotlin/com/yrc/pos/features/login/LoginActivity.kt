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
//        session = SessionManagement(applicationContext)
//        if (session.isLoggedIn()){
//            val i = Intent(applicationContext,DashboardActivity::class.java)
//            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//            startActivity(i)
//            finish()
//        }
    }

    fun onSignInButtonClicked(signInButton: View) {
//        login()

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

//        if (apiResponse is GetProfileResponse) {
//            User.saveUserPrice(apiResponse.user!!)
//
//            var userPrice = User.getUserPrice()
//            if (userPrice != null) {
//                if (userPrice.code != null) {
//                    Toast.makeText(this@LoginActivity, "price is saved", Toast.LENGTH_LONG).show()
//                } else {
//                    moveToHelloScreen()
//                }
//            }
//        }


        if (apiResponse is GetProfileResponse) {
            User.saveUserProfile(apiResponse.user!!)

            var userProfile = User.getUserProfile()
            if (userProfile != null) {
                if (userProfile.code != null) {
                    Toast.makeText(this@LoginActivity, "user showed@@@@@@@@@@@@", Toast.LENGTH_LONG).show()
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

//    private fun login(){
//        val loginRequest = LoginRequest()
//        loginRequest.driver = editText_emailOrNumber!!.getText()
//        loginRequest.pin = editText_dutyNumber!!.getText()
//        loginRequest.dutyNumber = editText_password!!.getText()
//        val loginResponseCall = apiInterface.getlogin(loginRequest)
//        loginResponseCall!!.enqueue(object : retrofit2.Callback<LoginResponse?> {
//            override fun onResponse(call: retrofit2.Call<LoginResponse?>, response: retrofit2.Response<LoginResponse?>) {
//                if (response.isSuccessful) {
//                    val loginResponse = response.body()
//                    if (loginResponse != null) {
//                        if (loginResponse.code == 200) {
//                            session.createLoginSession("","", "",loginResponse.code.toString())
//                            Toast.makeText(this@LoginActivity, "LoggedIn", Toast.LENGTH_LONG).show()
//                            val intent = Intent(this@LoginActivity, DashboardActivity::class.java)
//                            startActivity(intent)
//                            finish()
//                        } else {
//                            Toast.makeText(this@LoginActivity, "False", Toast.LENGTH_LONG).show()
//                        }
//                    }
//
//                } else {
//                    Toast.makeText(this@LoginActivity, "Not Login", Toast.LENGTH_LONG).show()
//                }
//            }

//            override fun onFailure(call: retrofit2.Call<LoginResponse?>, t: Throwable) {
//                Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
//            }
//        })
//    }

    private fun priceResponse(getProfileResponse: GetProfileResponse) {
//        if (User.getUserPrice()!!.code != null) {
//            Session.storePrice(getProfileResponse.price.toString())
//         //   Toast.makeText(this@LoginActivity, "price saved", Toast.LENGTH_LONG).show()
//            callGetPriceApi()
//        } else {
//            AlertDialogProvider.showAlertDialog(this, DialogTheme.ThemeWhite, getProfileResponse.message)
//        }
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
            moveToDashboardScreen()

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
            APiManager.getPrice(this, this)
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

    private fun checkValidations(): Boolean {

        if (editText_emailOrNumber.getText().isEmpty()) {
            editText_emailOrNumber.setError(getString(R.string.please_enter_cell_number_or_email))
            return false
        }

//        if (YrcUtils.isPhoneNumber(editText_emailOrNumber.getText())) {
//            if (editText_emailOrNumber.getText().length < 10) {
//                editText_emailOrNumber.setError(getString(R.string.please_enter_valid_cell_number))
//                return false
//            }
//        } else {
//            if (!YrcUtils.isEmailValid(editText_emailOrNumber.getText())) {
//                editText_emailOrNumber.setError(getString(R.string.please_enter_valid_email))
//                return false
//            }
//        }

        if (editText_password.getText().isEmpty()) {
            editText_password.setError(getString(R.string.please_enter_password))
            return false
        }

        return true
    }
}
