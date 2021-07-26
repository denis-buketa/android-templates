package com.denisbuketa.soundstrue.signin

import android.content.Context
import android.graphics.Typeface
import android.text.InputType.*
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import kotlinx.android.synthetic.main.st_view_sign_up_input.view.*

class SoundsTrueSignUpInputView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    enum class State {
        DEFAULT,
        ERROR
    }

    /**
     * Determines what type of input we expect
     */
    enum class InputType {
        TEXT,
        EMAIL,
        PASSWORD
    }

    /**
     * Used to map SignUpInputType attribute values to InputType values
     * Check attrs/SignUpInputType
     */

    private val inputTypeMap = mapOf(
        0 to InputType.TEXT,
        1 to InputType.EMAIL,
        2 to InputType.PASSWORD
    )

    private var state = State.DEFAULT
    private var inputType: InputType = InputType.TEXT
    private var hint: String? = null
    private var font: Typeface? = null
    private var isPasswordVisible: Boolean = false

    private val passwordTransformationMethod = AsteriskPasswordTransformationMethod()

    init {
        parseStyledAttributes(attrs)

        inflate(context, R.layout.st_view_sign_up_input, this)

        // Setup background that covers all states
        background = ResourcesCompat.getDrawable(resources, R.drawable.st_sign_up_input_background, null)
        background.state = intArrayOf()

        // Observe edit text focus
        inputEditText.setOnFocusChangeListener { _, hasFocus -> onFocusedStateChanged(hasFocus) }

        prepareForInputType()
    }

    private fun parseStyledAttributes(attrs: AttributeSet?) {
        context?.theme?.obtainStyledAttributes(
            attrs,
            R.styleable.StSignUpInputView,
            0,
            0
        )?.apply {
            try {
                inputType = inputTypeMap[getInt(R.styleable.StSignUpInputView_stInputType, 0)] ?: InputType.TEXT
                hint = getString(R.styleable.StSignUpInputView_hint)
            } finally {
                recycle()
            }
        }
    }

    /**
     * Prepares this View for specific [InputType]
     */
    private fun prepareForInputType() {

        when (inputType) {
            InputType.TEXT -> {
                inputEditText.inputType = TYPE_CLASS_TEXT
            }
            InputType.PASSWORD -> {
                font = inputEditText.typeface
                inputEditText.inputType = TYPE_CLASS_TEXT or TYPE_TEXT_VARIATION_PASSWORD
                inputEditText.typeface = font
                inputEditText.transformationMethod = passwordTransformationMethod
                image.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.st_ic_show_password, null))
                imageContainer.setOnClickListener { togglePasswordVisibility(!isPasswordVisible) }
                imageContainer.visibility = View.VISIBLE
            }
            else -> {
                inputEditText.inputType = TYPE_CLASS_TEXT or TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            }
        }

        hint?.let { inputEditText.hint = it }
    }

    private fun togglePasswordVisibility(passwordVisible: Boolean) {
        if (inputType == InputType.PASSWORD) {
            val passwordTransformationMethod = if (passwordVisible) null else passwordTransformationMethod
            val inputType = TYPE_CLASS_TEXT or (if (passwordVisible) TYPE_TEXT_VARIATION_VISIBLE_PASSWORD else TYPE_TEXT_VARIATION_PASSWORD)
            val font = inputEditText.typeface
            inputEditText.inputType = inputType
            inputEditText.typeface = font
            inputEditText.transformationMethod = passwordTransformationMethod
            image.setImageResource(if (passwordVisible) R.drawable.st_ic_hide_password else R.drawable.st_ic_show_password)
            isPasswordVisible = passwordVisible
        }
    }

    private fun onFocusedStateChanged(isFocused: Boolean) {
        if (isFocused) {
            setState(State.DEFAULT)
        }
    }

    fun setState(state: State) {
        if (state != this.state) {
            this.state = state
            refreshDrawableState()
            refreshTextColor()
        }
    }

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val drawableStates = super.onCreateDrawableState(extraSpace + 1)
        if (state == State.ERROR) {
            View.mergeDrawableStates(drawableStates, intArrayOf(R.attr.state_error))
        }
        return drawableStates
    }

    private fun refreshTextColor() {
        when(state) {
            State.DEFAULT -> inputEditText.setTextColor(ResourcesCompat.getColor(resources, R.color.st_default_color, null))
            State.ERROR -> inputEditText.setTextColor(ResourcesCompat.getColor(resources, R.color.st_error_color, null))
        }

    }

    /**
     * Allows us to use Asterisk (*) instead of dots for the password characters
     *
     * For more info chec:
     * - [android.text.method.PasswordTransformationMethod]
     * - https://stackoverflow.com/questions/14051962/change-edittext-password-mask-character-to-asterisk
     */
    private class AsteriskPasswordTransformationMethod : PasswordTransformationMethod() {

        override fun getTransformation(source: CharSequence, view: View): CharSequence {
            return PasswordCharSequence(source)
        }

        private inner class PasswordCharSequence(private val mSource: CharSequence) : CharSequence {

            override val length: Int
                get() = mSource.length

            override fun get(index: Int): Char {
                return '*'
            }

            override fun subSequence(startIndex: Int, endIndex: Int): CharSequence {
                return mSource.subSequence(startIndex, endIndex)
            }
        }
    }
}
