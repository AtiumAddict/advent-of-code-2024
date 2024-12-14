package com.atiumaddict.day13

fun solveDay13First(lines: List<String>): Long {
    val machines = getMachines(lines)
    var totalTokens = 0L
    for (machine in machines) {
        val tokens = calculateTokensWithMath(machine)
        if (tokens.first != 0L) {
            totalTokens += (tokens.first * 3) + tokens.second
        }
    }
    return totalTokens
}

class Machine(var prize: Pair<Long, Long>, val buttonA: Pair<Long, Long>, val buttonB: Pair<Long, Long>) {
    override fun toString(): String {
        return "Machine(prize=$prize, buttonA=$buttonA, buttonB=$buttonB)"
    }
}

/**
 * @deprecated This function is too slow for the second part of the problem.
 */
fun calculateTokensWithBruteForce(machine: Machine): Pair<Long, Long> {
    var aTokens = 0L
    var bTokens = 1L + machine.prize.first / machine.buttonB.first
    while (aTokens < 100 && bTokens > 0) {
        var clawPosition = findClawPosition(aTokens, bTokens, machine)
        if (clawPosition == machine.prize) {
            return Pair(aTokens, bTokens)
        }
        if (clawPosition.first < machine.prize.first && clawPosition.second < machine.prize.second) {
            while (clawPosition.first < machine.prize.first && clawPosition.second < machine.prize.second) {
                aTokens++
                clawPosition = findClawPosition(aTokens, bTokens, machine)
                if (clawPosition == machine.prize) {
                    return Pair(aTokens, bTokens)
                }
            }
        }
        bTokens--
    }
    return Pair(0, 0)
}

private fun findClawPosition(
    aTokens: Long,
    bTokens: Long,
    machine: Machine
): Pair<Long, Long> {
    var clawPositionX = aTokens * machine.buttonA.first + bTokens * machine.buttonB.first
    var clawPositionY = aTokens * machine.buttonA.second + bTokens * machine.buttonB.second
    return Pair(clawPositionX, clawPositionY)
}

const val additive = 10000000000000

fun solveDay13Second(lines: List<String>): Long {
    val machines = getMachines(lines)
    var totalTokens = 0L
    for (machine in machines) {
        machine.prize = Pair(machine.prize.first + additive, machine.prize.second + additive)
        val tokens = calculateTokensWithMath(machine)
        if (tokens.first != 0L) {
            totalTokens += (tokens.first * 3) + tokens.second
        }
    }
    return totalTokens
}

fun calculateTokensWithMath(machine: Machine): Pair<Long, Long> {
    val buttonAX = machine.buttonA.first
    val buttonAY = machine.buttonA.second
    val buttonBX = machine.buttonB.first
    val buttonBY = machine.buttonB.second
    val prizeX = machine.prize.first
    val prizeY = machine.prize.second
    val divisor = buttonAX * buttonBY - buttonAY * buttonBX
    val a = (prizeX * buttonBY - prizeY * buttonBX) / divisor
    val b = (buttonAX * prizeY - buttonAY * prizeX) / divisor
    return if (buttonAX * a + buttonBX * b == prizeX && buttonAY * a + buttonBY * b == prizeY) {
        Pair(a, b)
    } else Pair(0, 0)
}

fun getMachines(lines: List<String>): List<Machine> {
    val machines = mutableListOf<Machine>()
    var buttonA: Pair<Long, Long>? = null
    var buttonB: Pair<Long, Long>? = null
    var prize: Pair<Long, Long>? = null

    lines.forEach { line ->
        when {
            line.startsWith("Button A:") -> {
                val (x, y) = line.substringAfter("Button A:").split(",").map { it.trim().substring(2).toLong() }
                buttonA = Pair(x, y)
            }

            line.startsWith("Button B:") -> {
                val (x, y) = line.substringAfter("Button B:").split(",").map { it.trim().substring(2).toLong() }
                buttonB = Pair(x, y)
            }

            line.startsWith("Prize:") -> {
                val (x, y) = line.substringAfter("Prize:").split(",").map { it.trim().substring(2).toLong() }
                prize = Pair(x, y)
            }
        }

        if (buttonA != null && buttonB != null && prize != null) {
            machines.add(Machine(prize!!, buttonA!!, buttonB!!))
            buttonA = null
            buttonB = null
            prize = null
        }
    }

    return machines
}

