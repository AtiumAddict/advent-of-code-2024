package com.atiumaddict.day08

import com.atiumaddict.day14.Robot
import com.atiumaddict.day14.getFinalPosition
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class getFinalPositionTest :
    FunSpec({
        test("finds the correct final position") {
            val robot = Robot(Pair(3, 0), Pair(-3, 5))
            val seconds = 6
            val grid = Pair(5, 1)

            val response = getFinalPosition(robot, seconds, grid)

            response shouldBe Pair(0, 0)
        }
    })
