data class Amplifier(val input: MutableList<Int>, var isActive: Boolean = true) {
    var counter = 0

    fun calculate(signalIn: Int, phase: Int): Int {
        if(!isActive){
            return signalIn
        }
        while (counter < input.size) {
            if (input[counter] == 99) {
                isActive = false
                println("halting...... for ${counter}")
                return -99
            }
            val digits = getDigits(input[counter])


            val parameter1 = when (digits[2]) {
                1 -> counter + 1
                else -> input[counter + 1]
            }
            if(digits.last()==4){
                counter += 2
                return input[parameter1]
            }
            val parameter2 = when (digits[1]) {
                1 -> counter + 2
                else -> input[counter + 2]
            }
            val parameter3 = when (digits[0]) {
                1 -> counter + 3
                else -> input[counter + 3]
            }

            when (digits.last()) {
                1 -> {
                    input[parameter3] = input[parameter1] + input[parameter2]
                    counter += 4
                }
                2 -> {
                    input[parameter3] = input[parameter1] * input[parameter2]
                    counter += 4
                }
                3 -> {
                    input[parameter1] = when(counter){
                        0 -> phase
                        else-> signalIn
                    }
                    counter += 2
                }
                5 -> {
                    if (input[parameter1] != 0) {
                        counter = input[parameter2]
                    } else {
                        counter += 3
                    }
                }
                6 -> {
                    if (input[parameter1] == 0) {
                        counter = input[parameter2]
                    } else {
                        counter += 3
                    }
                }
                7 -> {
                    if (input[parameter1] < input[parameter2]) {
                        input[parameter3] = 1
                    } else {
                        input[parameter3] = 0
                    }
                    counter += 4
                }
                8 -> {
                    if (input[parameter1] == input[parameter2]) {
                        input[parameter3] = 1
                    } else {
                        input[parameter3] = 0
                    }
                    counter += 4
                }
            }

        }
        println("going out of bounds")
        return -999
    }

}

fun main() {

    val input = listOf(
        3,8,1001,8,10,8,105,1,0,0,21,34,55,68,93,106,187,268,349,430,99999,3,9,102,5,9,9,1001,9,2,9,4,9,99,3,9,1001,9,5,9,102,2,9,9,101,2,9,9,102,2,9,9,4,9,99,3,9,101,2,9,9,102,4,9,9,4,9,99,3,9,101,4,9,9,102,3,9,9,1001,9,2,9,102,4,9,9,1001,9,2,9,4,9,99,3,9,101,2,9,9,1002,9,5,9,4,9,99,3,9,101,1,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,1,9,9,4,9,99,3,9,101,2,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,101,1,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,1001,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,101,2,9,9,4,9,99,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,101,1,9,9,4,9,3,9,101,1,9,9,4,9,3,9,101,2,9,9,4,9,3,9,1001,9,2,9,4,9,99,3,9,1002,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,101,1,9,9,4,9,3,9,101,1,9,9,4,9,99,3,9,101,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,101,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,1002,9,2,9,4,9,99
    )
    var maxOutputThruster = 0
    val perms = (5..9).toList().createPerm()


    perms.forEach {
        val amplifierA = Amplifier(input.toMutableList())
        val amplifierB = Amplifier(input.toMutableList())
        val amplifierC = Amplifier(input.toMutableList())
        val amplifierD = Amplifier(input.toMutableList())
        val amplifierE = Amplifier(input.toMutableList())
        println("$it")
        val amplifierAPhase = it[0]
        val amplifierBPhase = it[1]
        val amplifierCPhase = it[2]
        val amplifierDPhase = it[3]
        val amplifierEPhase = it[4]
        var maxOutPutForCurrentPerm = -1
        var loop = 1
        println("================yo loop $loop================")
        var outputA = amplifierA.calculate( 0, amplifierAPhase)
        printState(outputA, amplifierA, "A")
        var outputB = amplifierB.calculate( outputA, amplifierBPhase)
        printState(outputB, amplifierB, "B")
        var outputC = amplifierC.calculate( outputB, amplifierCPhase)
        printState(outputC, amplifierC, "C")
        var outputD = amplifierD.calculate( outputC, amplifierDPhase)
        printState(outputD, amplifierD, "D")
        var outputE = amplifierE.calculate( outputD, amplifierEPhase)
        printState(outputE, amplifierE, "E")
        maxOutPutForCurrentPerm = outputE

        while (amplifierE.isActive) {
            println("\n================yo loop ${++loop}================")
            outputA = amplifierA.calculate(outputE, amplifierAPhase)
            printState(outputA, amplifierA, "A")
            outputB = amplifierB.calculate(outputA, amplifierBPhase)
            printState(outputB, amplifierB, "B")
            outputC = amplifierC.calculate(outputB, amplifierCPhase)
            printState(outputC, amplifierC, "C")
            outputD = amplifierD.calculate(outputC, amplifierDPhase)
            printState(outputD, amplifierD, "D")
            outputE = amplifierE.calculate(outputD, amplifierEPhase)
            printState(outputE, amplifierE, "E")
            if (maxOutPutForCurrentPerm == -1) {
                maxOutPutForCurrentPerm = outputE
            } else if (outputE > maxOutPutForCurrentPerm) {
                maxOutPutForCurrentPerm = outputE
            }
        }
        println(maxOutPutForCurrentPerm)
        println()
        println()

        if (maxOutPutForCurrentPerm > maxOutputThruster) {
            maxOutputThruster = maxOutPutForCurrentPerm
        }
    }

    print("max output is $maxOutputThruster")


}

private fun printState(outputA: Int, amplifierA: Amplifier, name : String) {
    println("Amplifier $name ==== input : $outputA , pointer: ${amplifierA.counter}, output : $outputA , isActive : ${amplifierA.isActive}")
}

private fun List<Int>.createPerm(): List<List<Int>> {
    val list = this
    if (list.size == 1) return listOf(list)

    val perms = mutableListOf<List<Int>>()
    val sub = list[0]
    for (perm in list.drop(1).createPerm())
        for (i in 0..perm.size) {
            val newPerm = perm.toMutableList()
            newPerm.add(i, sub)
            perms.add(newPerm)
        }
    return perms
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

