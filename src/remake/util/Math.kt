package remake.util

/**
 * Created by Chef.Xie on 2021-10-12
 */
object Math {
    fun pow(seed: Int, times: Int): Int {
        var result = 1
        for (i in 0..times) {
            result *= seed
        }
        return result
    }
}