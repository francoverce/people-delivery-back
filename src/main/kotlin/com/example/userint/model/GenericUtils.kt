package model

import java.util.function.Function
import java.util.function.Supplier

object GenericUtils {
    fun <T> callAndThrow(action: Supplier<T>, exception: Function<Exception?, RuntimeException?>): T {
        return try {
            action.get()
        } catch (ex: Exception) {
            throw exception.apply(ex)!!
        }
    }

    fun getValueFromKeyValue(hm: Map<*, *>, key: Any): Any? {
        for (o in hm.keys) {
            if (o != null && o == key) {
                return hm[o]
            }
        }
        return null
    }
}
