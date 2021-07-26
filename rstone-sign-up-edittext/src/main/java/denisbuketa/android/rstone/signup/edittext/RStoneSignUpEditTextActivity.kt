package denisbuketa.android.rstone.signup.edittext

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_rstone_sign_up_edit_text.*

class RStoneSignUpEditTextActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rstone_sign_up_edit_text)

        nameDefault.setOnClickListener { signUpInputName.setValidationState(SignUpInputView.ValidationState.DEFAULT) }
        nameError.setOnClickListener { signUpInputName.setValidationState(SignUpInputView.ValidationState.ERROR) }
        emailDefault.setOnClickListener { signUpInputEmail.setValidationState(SignUpInputView.ValidationState.DEFAULT) }
        emailError.setOnClickListener { signUpInputEmail.setValidationState(SignUpInputView.ValidationState.ERROR) }
        emailValid.setOnClickListener { signUpInputEmail.setValidationState(SignUpInputView.ValidationState.VALID) }
        passwordDefault.setOnClickListener { signUpInputPassword.setValidationState(SignUpInputView.ValidationState.DEFAULT) }
        passwordError.setOnClickListener { signUpInputPassword.setValidationState(SignUpInputView.ValidationState.ERROR) }
    }
}
