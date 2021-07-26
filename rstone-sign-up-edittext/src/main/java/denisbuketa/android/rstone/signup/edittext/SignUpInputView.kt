package denisbuketa.android.rstone.signup.edittext

import android.content.Context
import android.graphics.Typeface
import android.text.InputType.*
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.view_sign_up_input.view.*

class SignUpInputView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    enum class BackgroundState {
        DEFAULT,
        INPUT,
        ERROR
    }

    enum class ValidationState {
        VALID,
        ERROR,
        DEFAULT
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

    private var backgroundState = BackgroundState.DEFAULT
    private var validationState = ValidationState.DEFAULT
    private var inputType: InputType = InputType.TEXT
    private var hint: String? = null
    private var font: Typeface? = null
    private var isPasswordVisible: Boolean = false

    init {
        parseStyledAttributes(attrs)

        inflate(context, R.layout.view_sign_up_input, this)

        // Setup background that covers all states
        background = resources.getDrawable(R.drawable.sign_up_input_background, null)
        background.state = intArrayOf()

        // Observe edit text focus
        inputEditText.setOnFocusChangeListener { _, hasFocus -> onFocusedStateChanged(hasFocus) }

        prepareForInputType()
    }

    private fun parseStyledAttributes(attrs: AttributeSet?) {
        context?.theme?.obtainStyledAttributes(
            attrs,
            R.styleable.SignUpInputView,
            0,
            0
        )?.apply {
            try {
                inputType = inputTypeMap[getInt(R.styleable.SignUpInputView_inputType, 0)] ?: InputType.TEXT
                hint = getString(R.styleable.SignUpInputView_hint)
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
                image.setImageDrawable(resources.getDrawable(R.drawable.ic_show_password, null))
                imageContainer.setOnClickListener { togglePasswordVisibility(!isPasswordVisible) }
                imageContainer.isClickable = false
            }
            else -> {
                inputEditText.inputType = TYPE_CLASS_TEXT or TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            }
        }

        hint?.let { inputEditText.hint = it }
    }

    private fun togglePasswordVisibility(passwordVisible: Boolean) {
        if (inputType == InputType.PASSWORD) {
            val inputType = TYPE_CLASS_TEXT or (if (passwordVisible) TYPE_TEXT_VARIATION_VISIBLE_PASSWORD else TYPE_TEXT_VARIATION_PASSWORD)
            val font = inputEditText.typeface
            inputEditText.inputType = inputType
            inputEditText.typeface = font
            image.setImageResource(if (passwordVisible) R.drawable.ic_visibility_off_black_24dp else R.drawable.ic_show_password)
            isPasswordVisible = passwordVisible
        }
    }

    private fun onFocusedStateChanged(isFocused: Boolean) {
        updateBackgroundState(isFocused)
        updateImageForPasswordInputType(isFocused)

        if (isFocused.not()) {
            togglePasswordVisibility(false)
        }
    }

    private fun updateBackgroundState(isFocused: Boolean) {
        if (isFocused) {
            setBackgroundState(BackgroundState.INPUT)
        } else {
            if (backgroundState == BackgroundState.INPUT) {
                setBackgroundState(BackgroundState.DEFAULT)
            }
        }
    }

    private fun updateImageForPasswordInputType(isFocused: Boolean) {
        if (inputType == InputType.PASSWORD) {
            image.setImageResource(if (isPasswordVisible) R.drawable.ic_visibility_off_black_24dp else R.drawable.ic_show_password)
            imageContainer.visibility = if (isFocused) View.VISIBLE else View.GONE
            imageContainer.isClickable = isFocused
        } else {
            image.setImageDrawable(null)
            imageContainer.visibility = View.GONE
            imageContainer.isClickable = false
        }
    }

    fun setValidationState(validationState: ValidationState) {

        /**
         * Validation status can't be changed if the input is focused
         */
        if (inputEditText.isFocused) {
            return
        }

        this.validationState = validationState
        imageContainer.isClickable = false
        when (validationState) {
            ValidationState.VALID -> {
                image.setImageResource(R.drawable.ic_check_black_24dp)
                imageContainer.visibility = View.VISIBLE
                setBackgroundState(BackgroundState.DEFAULT)
            }
            ValidationState.ERROR -> {
                image.setImageResource(R.drawable.ic_error)
                imageContainer.visibility = View.VISIBLE
                setBackgroundState(BackgroundState.ERROR)
            }
            ValidationState.DEFAULT -> {
                image.setImageDrawable(null)
                imageContainer.visibility = View.GONE
                setBackgroundState(BackgroundState.DEFAULT)
            }
        }
    }

    private fun setBackgroundState(state: BackgroundState) {
        backgroundState = state
        refreshDrawableState()
    }


    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val drawableStates = super.onCreateDrawableState(extraSpace + 1)
        if (backgroundState == BackgroundState.ERROR) {
            View.mergeDrawableStates(drawableStates, intArrayOf(R.attr.state_error))
        }
        if (backgroundState == BackgroundState.INPUT) {
            View.mergeDrawableStates(drawableStates, intArrayOf(R.attr.state_input))
        }
        return drawableStates
    }
}