package denisbuketa.android.encryption

import java.security.SecureRandom
import java.util.*
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

private const val BYTE_ARRAY_SIZE = 256
private const val IV_BYTE_ARRAY_SIZE = 16
private const val ITERATION_COUNT = 1234
private const val KEY_LENGTH = 256
private const val KEY_FACTORY_ALGORITHM = "PBKDF2WithHmacSHA1"
private const val KEY_SPEC_ALGORITHM = "AES"
private const val TRANSFORMATION = "AES/CBC/PKCS7Padding"

/**
 * EncryptionMAndAbove class used for version before M.
 */
class EncryptionPreM : Encryption {

    companion object {
        const val SALT_KEY = "salt"
        const val IV_KEY = "iv"
        const val ENCRYPTED_KEY = "encrypted"
    }

    private var salt: ByteArray? = null
    private var keySpec: SecretKeySpec? = null

    fun init(password: CharArray) {
        try {
            salt = generateSalt()
            keySpec = generateKey(salt!!, password)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun generateSalt(): ByteArray {
        val random = SecureRandom()
        val salt = ByteArray(BYTE_ARRAY_SIZE)
        random.nextBytes(salt)
        return salt
    }

    private fun generateKey(salt: ByteArray, password: CharArray): SecretKeySpec {
        val pbKeySpec = PBEKeySpec(password, salt, ITERATION_COUNT, KEY_LENGTH)
        val secretKeyFactory = SecretKeyFactory.getInstance(KEY_FACTORY_ALGORITHM)
        val keyBytes = secretKeyFactory.generateSecret(pbKeySpec).encoded
        return SecretKeySpec(keyBytes, KEY_SPEC_ALGORITHM)
    }

    override fun encrypt(dataToEncrypt: ByteArray): HashMap<String, ByteArray> {

        val map = HashMap<String, ByteArray>()

        if (salt == null || keySpec == null) {
            return map
        }

        try {
            val ivRandom = SecureRandom()
            val iv = ByteArray(IV_BYTE_ARRAY_SIZE)
            ivRandom.nextBytes(iv)
            val ivSpec = IvParameterSpec(iv)

            val cipher = Cipher.getInstance(TRANSFORMATION)
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec)
            val encrypted = cipher.doFinal(dataToEncrypt)

            map[SALT_KEY] = salt!!
            map[IV_KEY] = iv
            map[ENCRYPTED_KEY] = encrypted
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return map

    }

    override fun decrypt(map: HashMap<String, ByteArray>): ByteArray? {
        var decrypted: ByteArray? = null

        try {
            val iv = map[IV_KEY]
            val encrypted = map[ENCRYPTED_KEY]

            val cipher = Cipher.getInstance(TRANSFORMATION)
            val ivSpec = IvParameterSpec(iv)
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec)
            decrypted = cipher.doFinal(encrypted)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return decrypted
    }
}