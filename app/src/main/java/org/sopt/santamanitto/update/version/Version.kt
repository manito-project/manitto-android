package org.sopt.santamanitto.update.version

class Version private constructor(private val version: String) : Comparable<Version> {

    companion object {
        const val MAJOR = 0
        const val MINOR = 1
        const val FETCH = 2

        private val versionRegex = Regex("\\d+.\\d+.\\d+")

        fun create(version: String): Version {
            if (!version.matches(versionRegex)) {
                throw IllegalArgumentException("Version must be $versionRegex")
            }
            return Version(version)
        }
    }

    private val parsedVersion: IntArray

    val major: Int
        get() = parsedVersion[MAJOR]

    val minor: Int
        get() = parsedVersion[MINOR]

    val fetch: Int
        get() = parsedVersion[FETCH]

    override fun compareTo(other: Version): Int {
        return version.compareTo(other.version)
    }

    fun compare(version: String, what: Int): Int {
        val other = create(version)
        return parsedVersion[what].compareTo(other.get(what))
    }

    fun get(what: Int): Int {
        if (what !in 0 until 2) {
            throw IllegalArgumentException("what is in 0 ~ 2")
        }
        return parsedVersion[what]
    }

    init {
        val parsed = version.split(".")
        val temp = IntArray(2)
        for (i in 0 until 2) {
            temp[i] = parsed[i].toInt()
        }
        parsedVersion = temp
    }
}