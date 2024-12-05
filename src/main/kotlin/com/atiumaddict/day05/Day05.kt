package com.atiumaddict.day05

fun solveDay05First(lines: List<String>): Long {
    val manual = parseLines05(lines)
    var result = 0L
    for (update in manual.updates) {
        val seenPages = HashSet<Int>()
        var isValidUpdate = true
        for (page in update) {
            val isValidPage = isPageValid(manual, page, seenPages)
            seenPages.add(page)
            if (!isValidPage) {
                isValidUpdate = false
            }
        }
        if (!isValidUpdate) result += getMiddlePage(update)
    }
    return result
}

private fun isPageValid(
    manual: SafetyManual,
    page: Int,
    seenPages: HashSet<Int>,
): Boolean {
    if (!manual.rules.containsKey(page)) {
        return true
    }
    val rule = manual.rules[page]
    for (seenPage in seenPages) {
        if (rule?.after?.contains(seenPage) == true) {
            return false
        }
    }
    return true
}

fun solveDay05Second(lines: List<String>): Long {
    val manual = parseLines05(lines)
    var result = 0L
    for (update in manual.updates) {
        var isValidUpdate = true
        var newUpdate = update.toList()
        for (page in update) {
            val validatedUpdate = validateAndCorrectPage(manual, page, newUpdate)
            if (!validatedUpdate.first) {
                isValidUpdate = false
//                println("Fixed update: $newUpdate after page $page")
                newUpdate = validatedUpdate.second
            }
        }
        if (!isValidUpdate) {
            result += getMiddlePage(newUpdate)
        }
    }
    return result
}

fun getMiddlePage(update: List<Int>): Int {
    return update[update.size / 2]
}

private fun validateAndCorrectPage(
    manual: SafetyManual,
    page: Int,
    update: List<Int>,
): Pair<Boolean, List<Int>> {
    if (!manual.rules.containsKey(page)) {
        return Pair(true, update)
    }
    val rule = manual.rules[page]
    var currentPageIndex = 0
    var currentPage = update[currentPageIndex]
    while (currentPage != page) {
        if (rule?.after?.contains(currentPage) == true) {
            val fixedUpdate = flipPages(page, currentPage, update, manual)
            return Pair(false, fixedUpdate)
        }
        currentPageIndex++
        currentPage = update[currentPageIndex]
    }
    return Pair(true, update)
}

fun flipPages(pageBefore: Int, pageAfter: Int, update: List<Int>, manual: SafetyManual): List<Int> {
    val pageIdx = update.indexOf(pageBefore)
    val seenPageIdx = update.indexOf(pageAfter)

    var mutableUpdate = update.toMutableList()
    mutableUpdate[pageIdx] = pageAfter
    mutableUpdate[seenPageIdx] = pageBefore
    // Fix the pages in between
    for (i in seenPageIdx + 1 until pageIdx) {
        val currentPage = mutableUpdate[i]
        val currentRule = manual.rules[currentPage]
        if (currentRule?.after?.contains(pageAfter) == true) {
            mutableUpdate = flipPages(pageAfter, currentPage, mutableUpdate, manual).toMutableList()
        }
    }
    println("Flipped pages $pageBefore and $pageAfter")
    println("New update: $mutableUpdate")
    return mutableUpdate
}

class BeforeAfter(val before: HashSet<Int>, val after: HashSet<Int>)
class SafetyManual(val rules: HashMap<Int, BeforeAfter>, val updates: List<List<Int>>)

fun parseLines05(lines: List<String>): SafetyManual {
    val rules = HashMap<Int, BeforeAfter>()
    val updates = mutableListOf<List<Int>>()
    var rulesFinished = false
    lines.forEach { line ->
        if (line.isEmpty()) {
            rulesFinished = true
        } else if (!rulesFinished) {
            val pair = line.split("|").map { it.trim().toInt() }
            if (rules.containsKey(pair[0])) {
                rules[pair[0]]?.after?.add(pair[1])
            } else {
                rules[pair[0]] = BeforeAfter(HashSet(), HashSet())
                rules[pair[0]]?.after?.add(pair[1])
            }
            if (rules.containsKey(pair[1])) {
                rules[pair[1]]?.before?.add(pair[0])
            } else {
                rules[pair[1]] = BeforeAfter(HashSet(), HashSet())
                rules[pair[1]]?.before?.add(pair[0])
            }
        } else {
            val update = line.split(",").map { it.trim().toInt() }
            updates.add(update)
        }
    }
    return SafetyManual(rules, updates)
}
