
fun main() {

    val input = listOf(3,26,1001,26,-4,26,3,27,1002,27,2,27,1,27,26,
        27,4,27,1001,28,-1,28,1005,28,6,99,0,0,5)
    var maxOutPut = -1
    val perms = (5..9).toList().createPerm()


//    perms.forEach {
        var outputE = 0
        while (outputE!=-9999){
            val outputA = calculate(input, listOf(9, outputE))
            val outputB = calculate(input,  listOf(8, outputA))
            val outputC = calculate(input,  listOf(7, outputB))
            val outputD = calculate(input,  listOf(6, outputC))
            outputE = calculate(input, listOf(5, outputD))
        }
        if (maxOutPut == -1) {
            maxOutPut = outputE
        } else if (outputE > maxOutPut) {
            maxOutPut = outputE
        }
//    }
//
//    println(perms)

    println(maxOutPut)

}

private fun List<Int>.createPerm(): List<List<Int>> {
    val list = this
    if(list.size==1) return listOf(list)

    val perms = mutableListOf<List<Int>>()
    val sub=list[0]
    for(perm in list.drop(1).createPerm())
        for (i in 0..perm.size){
            val newPerm=perm.toMutableList()
            newPerm.add(i,sub)
            perms.add(newPerm)
        }
    return perms
}

private fun calculate(input: List<Int>, userIn: List<Int> ): Int {
    var i = 0
    val inputSignal = userIn.iterator()
    val output: MutableList<Int> = input.toMutableList()
    while (i < output.size) {
        if (output[i] == 99) {
            println("halting...... for ${i}")
            return -9999
        }
        val digits = getDigits(output[i])
        val parameter1 = when (digits[2]) {
            1 -> i + 1
            else -> output[i + 1]
        }
        val parameter2 = when (digits[1]) {
            1 -> i + 2
            else -> output[i + 2]
        }
        val parameter3 = when (digits[0]) {
            1 -> i + 3
            else -> output[i + 3]
        }

        when (digits.last()) {
            1 -> {
                output[parameter3] = output[parameter1] + output[parameter2]
                i += 4
            }
            2 -> {
                output[parameter3] = output[parameter1] * output[parameter2]
                i += 4
            }
            3 -> {
                output[parameter1] = inputSignal.next()
                i += 2
            }
            4 -> {
                println("output in at $i: ${output[i]} ${output[parameter1]}")
                return output[parameter1]
                i += 2
            }
            5 -> {
                if(output[parameter1]!=0){
                    i = output[parameter2]
                }
                else{
                    i+=3
                }
            }
            6-> {
                if(output[parameter1]==0){
                    i= output[parameter2]
                }
                else{
                    i+=3
                }
            }
            7-> {
                if(output[parameter1] < output[parameter2]){
                    output[parameter3] = 1
                }
                else{
                    output[parameter3] = 0
                }
                i+=4
            }
            8 -> {
                if(output[parameter1] == output[parameter2]){
                    output[parameter3] = 1
                }
                else{
                    output[parameter3] = 0
                }
                i+=4
            }
        }

    }
    return -9999
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

