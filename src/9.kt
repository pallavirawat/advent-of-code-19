fun main() {
    val input = listOf(109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99)
    val output: List<Int> = calculate(input)
    println(output)
}

data class IntWithRelative(var value: Int, var relative: Int)

private fun calculate(input: List<Int>): List<Int> {
    var i = 0
    val outputWithRelative = input.map { IntWithRelative(it, 0) }
    while (i < outputWithRelative.size) {
        if (outputWithRelative[i].value == 99) {
            println("halting...... for ${i}")
            return outputWithRelative.map { it.value }
        }
        val digits = getDigits(outputWithRelative[i].value)
        val parameter1 = when (digits[2]) {
            0 -> outputWithRelative[i + 1].value
            1 -> i + 1
            else -> i+1  + outputWithRelative[i+1].relative
        }
        val parameter2 = when (digits[1]) {
            0 -> outputWithRelative[i + 2].value
            1 -> i + 2
            else -> i+2  + outputWithRelative[i+2].relative
        }
        val parameter3 = when (digits[0]) {
            0 -> outputWithRelative[i + 3].value
            1 -> i + 3
            else -> i+3 + outputWithRelative[i+3].relative
        }

        when (digits.last()) {
            1 -> {
                outputWithRelative[parameter3].value = outputWithRelative[parameter1].value + outputWithRelative[parameter2].value
                i += 4
            }
            2 -> {
                outputWithRelative[parameter3].value = outputWithRelative[parameter1].value * outputWithRelative[parameter2].value
                i += 4
            }
            3 -> {
                outputWithRelative[parameter1].value = readLine()!!.toInt()
                i += 2
            }
            4 -> {
                println("output in at $i: ${outputWithRelative[i].value} ${outputWithRelative[parameter1].value}")
                i += 2
            }
            5 -> {
                if(outputWithRelative[parameter1].value!=0){
                    i = outputWithRelative[parameter2].value
                }
                else{
                    i+=3
                }
            }
            6-> {
                if(outputWithRelative[parameter1].value==0){
                    i= outputWithRelative[parameter2].value
                }
                else{
                    i+=3
                }
            }
            7-> {
                if(outputWithRelative[parameter1].value < outputWithRelative[parameter2].value){
                    outputWithRelative[parameter3].value = 1
                }
                else{
                    outputWithRelative[parameter3].value = 0
                }
                i+=4
            }
            8 -> {
                if(outputWithRelative[parameter1].value == outputWithRelative[parameter2].value){
                    outputWithRelative[parameter3].value = 1
                }
                else{
                    outputWithRelative[parameter3].value = 0
                }
                i+=4
            }
            9-> {
                outputWithRelative[parameter2].relative += outputWithRelative[parameter2].value
            }
        }

    }
    return outputWithRelative.map { it.value }
}

private fun getDigits(number: Int): List<Int> {
    var numLocal1 = number
    var counter = 5
    val digits = mutableListOf<Int>()
    while (counter > 0) {
        digits.add(numLocal1 % 10)
        numLocal1 /= 10
        counter--
    }
    return digits.reversed()
}

