package denisbuketa.android.encryption

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class EncryptionActivity : AppCompatActivity() {

    private val demoUserGuid = "jh837sk-ask32-4dasf23-861f"
    private val demoPassword = "password"

    private val encryption = EncryptionMAndAbove()
    private val encryptionPreM = EncryptionPreM()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_encryption)

        testEncryption()
        testEncryptionPreM()
    }

    private fun testEncryption() {
        Log.d("debug_log", "testing testEncryption")
        Log.d("debug_log", "demoUserGuid: $demoUserGuid")

        val userGuidEncryption = encryption.encrypt(demoUserGuid.toByteArray(Charsets.UTF_8))

        Log.d(
            "debug_log",
            "userGuidEncryption: ${userGuidEncryption[EncryptionMAndAbove.IV_KEY]}, ${userGuidEncryption[EncryptionMAndAbove.ENCRYPTED_KEY]}"
        )

        var userGuidDecryption = encryption.decrypt(userGuidEncryption)

        Log.d(
            "debug_log",
            "userGuidDecryption 1: ${String(userGuidDecryption ?: byteArrayOf(), Charsets.UTF_8)}}"
        )

        userGuidDecryption = encryption.decrypt(userGuidEncryption)

        Log.d(
            "debug_log",
            "userGuidDecryption 2: ${String(userGuidDecryption ?: byteArrayOf(), Charsets.UTF_8)}}"
        )

        userGuidDecryption = encryption.decrypt(userGuidEncryption)

        Log.d(
            "debug_log",
            "userGuidDecryption 3: ${String(userGuidDecryption ?: byteArrayOf(), Charsets.UTF_8)}}"
        )
    }

    private fun testEncryptionPreM() {
        Log.d("debug_log", "testing testEncryptionPreM")
        Log.d("debug_log", "demoUserGuid: $demoUserGuid")

        encryptionPreM.init(demoPassword.toCharArray())

        val userGuidEncryption = encryptionPreM.encrypt(demoUserGuid.toByteArray(Charsets.UTF_8))

        Log.d(
            "debug_log",
            "userGuidEncryption: ${userGuidEncryption[EncryptionMAndAbove.IV_KEY]}, ${userGuidEncryption[EncryptionMAndAbove.ENCRYPTED_KEY]}"
        )

        var userGuidDecryption = encryptionPreM.decrypt(userGuidEncryption)

        Log.d(
            "debug_log",
            "userGuidDecryption 1: ${String(userGuidDecryption ?: byteArrayOf(), Charsets.UTF_8)}}"
        )

        userGuidDecryption = encryptionPreM.decrypt(userGuidEncryption)

        Log.d(
            "debug_log",
            "userGuidDecryption 2: ${String(userGuidDecryption ?: byteArrayOf(), Charsets.UTF_8)}}"
        )

        userGuidDecryption = encryptionPreM.decrypt(userGuidEncryption)

        Log.d(
            "debug_log",
            "userGuidDecryption 3: ${String(userGuidDecryption ?: byteArrayOf(), Charsets.UTF_8)}}"
        )
    }
}
