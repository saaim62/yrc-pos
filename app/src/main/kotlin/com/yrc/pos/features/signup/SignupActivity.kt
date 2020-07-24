package com.yrc.pos.features.signup

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.yrc.pos.R
import com.yrc.pos.core.Constants
import com.yrc.pos.core.YrcBaseActivity
import com.yrc.pos.core.enums.DialogTheme
import com.yrc.pos.core.enums.UserType
import com.yrc.pos.core.providers.AlertDialogProvider
import com.yrc.pos.core.services.YrcBaseApiResponse
import com.yrc.pos.features.signup.sign_up_service.SignUpResponse
import kotlinx.android.synthetic.main.activity_signup.*
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task

class SignupActivity : YrcBaseActivity() {

    private val RC_SIGN_IN = 1111
    private var gmailId: String? = Constants.EMPTY_STRING
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
    }

    fun onSignUpButtonClicked(signUpButton: View) {
        gmailId = Constants.EMPTY_STRING
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            handleGoogleSignInResult(GoogleSignIn.getSignedInAccountFromIntent(data))
        }
    }

    private fun handleGoogleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            gmailId = account!!.email
        } catch (e: ApiException) {
            AlertDialogProvider.showFailureDialog(this, DialogTheme.ThemeWhite)
        }
    }
}