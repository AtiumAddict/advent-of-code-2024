package com.atiumaddict.day05

fun solveDay05First(lines: List<String>): Long {
    val manual = parseLines05(lines)
    var result = 0L
    for (update in manual.updates) {
        val isValidUpdate = isValidUpdate(update, manual)
        if (isValidUpdate) result += getMiddlePage(update)
    }
    return result
}

fun isPageValid(
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
        val isValidUpdate = isValidUpdate(update, manual)
        if (!isValidUpdate) {
            val correctedUpdate = correctUpdate(manual, update)
            result += getMiddlePage(correctedUpdate)
        }
    }
    return result
}

fun isValidUpdate(update: List<Int>, manual: SafetyManual): Boolean {
    val seenPages = HashSet<Int>()
    for (page in update) {
        val isValidPage = isPageValid(manual, page, seenPages)
        seenPages.add(page)
        if (!isValidPage) {
            return false
        }
    }
    return true
}

fun correctUpdate(manual: SafetyManual, update: List<Int>): List<Int> {
    var correctedUpdate = mutableListOf<Int>()
    val updateSet = update.toHashSet()
    for (page in update) {
        correctedUpdate = addPage(updateSet, correctedUpdate, page, manual)
    }
    return correctedUpdate
}

fun addPage(updateSet: HashSet<Int>, correctedUpdate: List<Int>, pageToAdd: Int, manual: SafetyManual): MutableList<Int> {
    var mutableUpdate = correctedUpdate.toMutableList()
    val rule = manual.rules[pageToAdd]
    if (correctedUpdate.contains(pageToAdd)) {
        return mutableUpdate
    }
    for (beforeBeforePage in rule?.before ?: emptySet()) {
        if (!correctedUpdate.contains(beforeBeforePage) && updateSet.contains(beforeBeforePage)) {
            mutableUpdate = addPage(updateSet, mutableUpdate, beforeBeforePage, manual)
        }
    }
    mutableUpdate.add(pageToAdd)
    return mutableUpdate
}

fun getMiddlePage(update: List<Int>): Int {
    return update[update.size / 2]
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
