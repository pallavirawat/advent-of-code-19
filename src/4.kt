fun main() {
    var counter = 0;
    for (i in 353096..843212){
        val digits = getDigits(i)
        if(digitAlwaysIncreasing(digits) && doubleDigitPresent(digits)){
            println(i)
            counter++
        }
    }

    print(counter)
}

fun digitAlwaysIncreasing(digits: List<Int>): Boolean {
    val sorted = digits.sortedDescending()
    return digits == sorted
}

fun doubleDigitPresent(digits: List<Int>): Boolean {
    val sorted = digits.sorted()
    val groupBy = sorted.groupBy { it }
    val doubleDigit = groupBy.map { entry -> entry.value.size }.filter { it==2 }
    return doubleDigit.isNotEmpty()
}

private fun getDigits(number: Int): MutableList<Int> {
    var numLocal1 = number
    val digits = mutableListOf<Int>()
    while (numLocal1 != 0) {
        digits.add(numLocal1 % 10)
        numLocal1 /= 10
    }
    return digits
}
