package denisbuketa.android.encryption

interface Encryption {

    fun encrypt(dataToEncrypt: ByteArray): HashMap<String, ByteArray>

    fun decrypt(map: HashMap<String, ByteArray>): ByteArray?
}