package com.fittingvroom.utils

class DecimalLimiter  {
    fun getProcessedString(string: String, MAX_DECIMAL: Int) : String {
        var str = string
        if (str[0] == '.') str = "0$str" //если первый символ ".", то перед ней поставится 0
        val max = str.length

        var rFinal = "" //Результат обработки строки
        var after = false //Если после точки, то true
        var i = 0
        var up = 0
        var decimal = 0
        var t: Char

        /**Возвращает количество символов, соответствующих заданному предикату "."
         * Каждая точка после первой удаляется.
        */
        val decimalCount = str.count{ ".".contains(it) }
        if (decimalCount > 1)
            return str.dropLast(1)

        /**Проходим по строке, оставляя 1 символ после точки
         */
        while (i < max) {
            t = str[i]
            if (t != '.' && !after) { // Если символ перед точкой - плюсуем его к строке
                up++
            } else if (t == '.') {
                after = true
            } else {                 //Если символ после точки - плюсуем к строке пока не станет больше разрешенного максимума
                decimal++
                if (decimal > MAX_DECIMAL)
                    return rFinal
            }
            rFinal += t
            i++
        }
        return rFinal
    }
}