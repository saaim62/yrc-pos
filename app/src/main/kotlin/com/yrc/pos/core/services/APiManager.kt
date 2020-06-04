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

object APiManager {
    //    private val retrofit: Retrofit
//        get() {
//            val interceptor = HttpLoggingInterceptor()
//            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
//            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
//            return Retrofit.Builder()
//                .baseUrl("https://api.mocki.io/v1/")
//                .client(client)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//        }
//    @JvmStatic
//    val apiInterface: ApiInterface
//        get() = retrofit.create(ApiInterface::class.java)
//
    private lateinit var hambaServices: ApiInterface
    private const val BASE_URL = "https://api.mocki.io/v1/"

    fun initialize() {
        val interceptor = HttpLoggingInterceptor()
        interceptor.apply { interceptor.level = HttpLoggingInterceptor.Level.BODY }
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        hambaServices = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }


    fun loginApi(context: Context, apiCallbacks: ApiCallbacks, loginRequest: LoginRequest) {
        val loginApiCall = hambaServices.loginToHamba(loginRequest)
        ApiExecutor<LoginResponse>().addCallToQueue(context, loginApiCall, apiCallbacks)
    }

    fun forgetPassword(
        context: Context,
        apiCallbacks: ApiCallbacks,
        forgetPasswordRequest: ForgetPasswordRequest
    ) {
        val forgetPasswordApiCall = hambaServices.forgetPassword(forgetPasswordRequest)
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
        val newPasswordApiCall = hambaServices.resetPassword(newPasswordRequest)
        ApiExecutor<NewPasswordResponse>().addCallToQueue(context, newPasswordApiCall, apiCallbacks)
    }

    fun signUpApi(context: Context, apiCallbacks: ApiCallbacks, signUpRequest: SignUpRequest) {
        val signUpApiCall = hambaServices.signUpToHamba(signUpRequest)
        ApiExecutor<SignUpResponse>().addCallToQueue(context, signUpApiCall, apiCallbacks)
    }

    fun verifyOtpCode(
        context: Context,
        apiCallbacks: ApiCallbacks,
        verifyOtpRequest: VerifyOtpRequest
    ) {
        val verifyOtpApiCall = hambaServices.verifyOtpCode(verifyOtpRequest)
        ApiExecutor<VerifyOtpResponse>().addCallToQueue(context, verifyOtpApiCall, apiCallbacks)
    }

    fun resendOtpCode(
        context: Context,
        apiCallbacks: ApiCallbacks,
        resendOtpRequest: ResendOtpRequest
    ) {
        val resendOtpApiCall = hambaServices.resendOtpCode(resendOtpRequest)
        ApiExecutor<ResendOtpResponse>().addCallToQueue(context, resendOtpApiCall, apiCallbacks)
    }

    fun getUserProfile(context: Context, apiCallbacks: ApiCallbacks) {
        val getProfileApiCall = hambaServices.getUserProfile(Session.getAccessToken())
        ApiExecutor<GetProfileResponse>().addCallToQueue(context, getProfileApiCall, apiCallbacks)
    }

    fun getPrice(context: Context, apiCallbacks: ApiCallbacks) {
        val getProfileApiCall = hambaServices.getUserProfile(Session.getPrice())
        ApiExecutor<GetProfileResponse>().addCallToQueue(context, getProfileApiCall, apiCallbacks)
    }

    fun editIndividualProfileApi(
        context: Context,
        apiCallbacks: ApiCallbacks,
        individualProfileEditRequest: IndividualProfileEditRequest
    ) {
        val editIndividualProfileApiCall = hambaServices.editIndividualProfile(
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