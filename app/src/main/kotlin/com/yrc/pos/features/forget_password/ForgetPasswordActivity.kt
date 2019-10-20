package com.yrc.pos.features.forget_password

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
import com.yrc.pos.features.forget_password.forget_password_service.ForgetPasswordRequest
import com.yrc.pos.features.forget_password.forget_password_service.ForgetPasswordResponse
import kotlinx.android.synthetic.main.activity_forget_password.*

class ForgetPasswordActivity : YrcBaseActivity() {

    companion object {
        @JvmStatic val KEY_EMAIL_ADDRESS_OR_NUMBER = "EmailAddressOrNumber"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)
    }

    fun onContinueButtonClicked(continueButton: View) {

        if (checkValidations()) {

            var forgetPasswordRequest = ForgetPasswordRequest()
            if (YrcUtils.isPhoneNumber(editText_emailAddress.getText())) {
                forgetPasswordRequest.number = editText_emailAddress.getText()
            } else {
                forgetPasswordRequest.email = editText_emailAddress.getText()
            }

            APiManager.forgetPassword(this, this, forgetPasswordRequest)
        }
    }

    override fun onApiSuccess(apiResponse: YrcBaseApiResponse) {
        super.onApiSuccess(apiResponse)

        if (apiResponse is ForgetPasswordResponse) {
            if (apiResponse.success!!) {
                moveToNewPasswordActivity()
            } else {
                AlertDialogProvider.showAlertDialog(this, DialogTheme.ThemeGreen, apiResponse.message)
            }
        }
    }

    private fun moveToNewPasswordActivity(){
        val newPasswordIntent = Intent(this, NewPasswordActivity::class.java)
        newPasswordIntent.putExtra(KEY_EMAIL_ADDRESS_OR_NUMBER, editText_emailAddress.getText())
        startActivity(newPasswordIntent)
    }

    private fun checkValidations() : Boolean {

        if (editText_emailAddress.getText().isEmpty()) {
            editText_emailAddress.setError(getString(R.string.please_enter_cell_number_or_email))
            return false
        }

        if (YrcUtils.isPhoneNumber(editText_emailAddress.getText())) {
            if (editText_emailAddress.getText().length < 10) {
                editText_emailAddress.setError(getString(R.string.please_enter_valid_cell_number))
                return false
            }
        } else {
            if (!YrcUtils.isEmailValid(editText_emailAddress.getText())) {
                editText_emailAddress.setError(getString(R.string.please_enter_valid_email))
                return false
            }
        }

        return true
    }
}