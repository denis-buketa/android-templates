package com.denisbuketa.soundstrue.signin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_sounds_true_sign_in_edit_text_activity.*

class SoundsTrueSignInEditTextActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sounds_true_sign_in_edit_text_activity)

        nameDefault.setOnClickListener { signUpInputName.setState(SoundsTrueSignUpInputView.State.DEFAULT) }
        nameError.setOnClickListener { signUpInputName.setState(SoundsTrueSignUpInputView.State.ERROR) }
        emailDefault.setOnClickListener { signUpInputEmail.setState(SoundsTrueSignUpInputView.State.DEFAULT) }
        emailError.setOnClickListener { signUpInputEmail.setState(SoundsTrueSignUpInputView.State.ERROR) }
        passwordDefault.setOnClickListener { signUpInputPassword.setState(SoundsTrueSignUpInputView.State.DEFAULT) }
        passwordError.setOnClickListener { signUpInputPassword.setState(SoundsTrueSignUpInputView.State.ERROR) }
    }
}