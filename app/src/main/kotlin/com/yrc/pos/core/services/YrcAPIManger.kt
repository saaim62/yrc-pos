package com.yrc.pos.core.services

import android.content.Context
import com.yrc.pos.core.session.Session
import com.yrc.pos.features.forget_password.forget_password_service.ForgetPasswordRequest
import com.yrc.pos.features.forget_password.forget_password_service.ForgetPasswordResponse
import com.yrc.pos.features.forget_password.new_password_service.NewPasswordRequest
import com.yrc.pos.features.forget_password.new_password_service.NewPasswordResponse
import com.yrc.pos.features.login.login_service.LoginRequest
import com.yrc.pos.features.login.login_service.LoginResponse
import com.yrc.pos.features.profile.edit_profile_service.IndividualProfileEditRequest
import com.yrc.pos.features.profile.edit_profile_service.IndividualProfileEditResponse
import com.yrc.pos.features.profile.get_profile_service.GetProfileResponse
import com.yrc.pos.features.signup.code_verification_service.VerifyOtpRequest
import com.yrc.pos.features.signup.code_verification_service.VerifyOtpResponse
import com.yrc.pos.features.signup.resend_otp_service.ResendOtpRequest
import com.yrc.pos.features.signup.resend_otp_service.ResendOtpResponse
import com.yrc.pos.features.signup.sign_up_service.SignUpRequest
import com.yrc.pos.features.signup.sign_up_service.SignUpResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class YrcAPIManger(var yrcServices: ApiInterface, private val context: Context) {
    
    fun loginApi(apiCallbacks: ApiCallbacks, loginRequest: LoginRequest) {
        val loginApiCall = yrcServices.loginToHamba(loginRequest)
        ApiExecutor<LoginResponse>().addCallToQueue(context, loginApiCall, apiCallbacks)
    }

    fun forgetPassword(
        context: Context,
        apiCallbacks: ApiCallbacks,
        forgetPasswordRequest: ForgetPasswordRequest
    ) {
        val forgetPasswordApiCall = yrcServices.forgetPassword(forgetPasswordRequest)
        ApiExecutor<ForgetPasswordResponse>().addCallToQueue(
            context,
            forgetPasswordApiCall,
            apiCallbacks
        )
    }

    fun resetPassword(
        context: Context,
        apiCallbacks: ApiCallbacks,
        newPasswordRequest: NewPasswordRequest
    ) {
        val newPasswordApiCall = yrcServices.resetPassword(newPasswordRequest)
        ApiExecutor<NewPasswordResponse>().addCallToQueue(context, newPasswordApiCall, apiCallbacks)
    }

    fun signUpApi(context: Context, apiCallbacks: ApiCallbacks, signUpRequest: SignUpRequest) {
        val signUpApiCall = yrcServices.signUpToHamba(signUpRequest)
        ApiExecutor<SignUpResponse>().addCallToQueue(context, signUpApiCall, apiCallbacks)
    }

    fun verifyOtpCode(
        context: Context,
        apiCallbacks: ApiCallbacks,
        verifyOtpRequest: VerifyOtpRequest
    ) {
        val verifyOtpApiCall = yrcServices.verifyOtpCode(verifyOtpRequest)
        ApiExecutor<VerifyOtpResponse>().addCallToQueue(context, verifyOtpApiCall, apiCallbacks)
    }

    fun resendOtpCode(
        context: Context,
        apiCallbacks: ApiCallbacks,
        resendOtpRequest: ResendOtpRequest
    ) {
        val resendOtpApiCall = yrcServices.resendOtpCode(resendOtpRequest)
        ApiExecutor<ResendOtpResponse>().addCallToQueue(context, resendOtpApiCall, apiCallbacks)
    }

    fun getUserProfile(context: Context, apiCallbacks: ApiCallbacks) {
        val getProfileApiCall = yrcServices.getUserProfile(Session.getAccessToken())
        ApiExecutor<LoginResponse>().addCallToQueue(context, getProfileApiCall, apiCallbacks)
    }

    fun editIndividualProfileApi(
        context: Context,
        apiCallbacks: ApiCallbacks,
        individualProfileEditRequest: IndividualProfileEditRequest
    ) {
        val editIndividualProfileApiCall = yrcServices.editIndividualProfile(
            Session.getAccessToken(),
            individualProfileEditRequest
        )
        ApiExecutor<IndividualProfileEditResponse>().addCallToQueue(
            context,
            editIndividualProfileApiCall,
            apiCallbacks
        )
    }
}