package com.atiumaddict.day09

fun solveDay09First(lines: List<String>): Long {
    val layout = lines[0]
    val blocks = getInitialBlocks(layout)
    var nextFreeMemory = findNextFreeMemory(blocks, 0)
    for (i in blocks.size - 1 downTo 0) {
        if (blocks[i] == ".") continue
        if (nextFreeMemory > i) break
        blocks[nextFreeMemory] = blocks[i]
        blocks[i] = "."
        nextFreeMemory = findNextFreeMemory(blocks, nextFreeMemory + 1)
    }
    val checksum = calculateChecksum(blocks)
    return checksum
}

private fun getInitialBlocks(layout: String): MutableList<String> {
    val blocks = mutableListOf<String>()
    for (i in layout.indices) {
        if (i % 2 != 0) {
            val numberOfBlocks = layout[i].toString().toInt()
            for (j in 0 until numberOfBlocks) {
                blocks.add(".")
            }
        } else {
            val blockIds = getBlockIds(i, layout)
            blocks.addAll(blockIds.map { it.toString() })
        }
    }
    return blocks
}

fun calculateChecksum(blocks: List<String>): Long {
    var checksum = 0L
    for (i in blocks.indices) {
        if (blocks[i] == ".") continue
        checksum += blocks[i].toLong() * i
    }
    return checksum

}

fun findNextFreeMemory(blocks: List<String>, i: Int): Int {
    for (j in i until blocks.size) {
        if (blocks[j] == ".") {
            return j
        }
    }
    return -1
}

fun getBlockIds(i: Int, layout: String): List<Int> {
    var id = 0
    if (i > 1) {
        id = i.div(2)
    }
    val blockIds = mutableListOf<Int>()
    val numberOfBlocks = layout[i].toString().toInt()
    for (j in 0 until numberOfBlocks) {
        blockIds.add(id)
    }
    return blockIds
}

fun solveDay09Second(lines: List<String>): Long {
    val layout = lines[0]
    var blocks = getInitialBlocks(layout)
    var currentFile = mutableListOf(blocks[blocks.size - 1])
    for (i in blocks.size - 2 downTo 0) {
        if (currentFile.isEmpty()) {
            if (blocks[i] == ".") continue
            else currentFile = mutableListOf(blocks[i])
            continue
        }
        if (blocks[i] != currentFile[0]) {
            blocks = moveFile(blocks, currentFile, i)
            currentFile = if (blocks[i] != ".") mutableListOf(blocks[i]) else mutableListOf()
        } else {
            currentFile.add(blocks[i])
        }
    }
    val checksum = calculateChecksum(blocks)
    return checksum
}

fun moveFile(
    blocks: MutableList<String>,
    fileToMove: List<String>,
    startingIndex: Int,
): MutableList<String> {
    val minimumWidth = fileToMove.size
    val nextFreeMemory = findNextFreeMemoryOfWidth(blocks, 0, minimumWidth, startingIndex)
    if (nextFreeMemory == -1) {
        return blocks
    }
    for (j in nextFreeMemory until nextFreeMemory + minimumWidth) {
        blocks[j] = fileToMove[j - nextFreeMemory]
        val previousIndex = startingIndex + j - nextFreeMemory + 1
        if (previousIndex < blocks.size) blocks[previousIndex] = "."
    }
    return blocks
}

fun findNextFreeMemoryOfWidth(blocks: List<String>, startingIndex: Int, minimumWidth: Int, maximumIndex: Int): Int {
    var shouldSkipUntil: Int? = null
    for (j in startingIndex until blocks.size) {
        if (j > maximumIndex) return -1
        if (shouldSkipUntil != null && j < shouldSkipUntil) {
            continue
        }
        if (blocks[j] == ".") {
            for (k in j until j + minimumWidth) {
                if (k > blocks.size - 1) return -1
                if (blocks[k] != ".") {
                    shouldSkipUntil = k
                    break
                }
                if (k == j + minimumWidth - 1) {
                    return j
                }
            }
        }
    }
    return -1
}

