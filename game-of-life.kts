#!/usr/bin/env kscript

import info.ditrapani.termgrid.Color
import info.ditrapani.termgrid.TermGrid
import info.ditrapani.termgrid.TermGridImpl

val s = """
    ---------+------
    ++--------+-----
    ++------+++-----
    --++------------
    --++------------
    ----------------
    """.trimIndent()

val gridBox = Grid.build(s)

fun updateView(grid: Grid, termGrid: TermGrid): Unit {
    for (y in 0 until grid.height) {
        for (x in 0 until grid.width) {
            val cell = grid.cells.get(y).get(x)
            val c = Cell.toChar(cell)
            val (fg, bg) = when(cell) {
                CellState.Alive -> Pair(Color.Grey, Color.Aqua)
                CellState.Dead -> Pair(Color.HotPink3, Color.Grey)
            }
            termGrid.set(y, x, c, fg, bg)
        }
    }
    termGrid.draw()
}

tailrec fun loop(grid: Grid, termGrid: TermGrid): Nothing {
    updateView(grid, termGrid)
    val newGrid = grid.next()
    Thread.sleep(400)
    return loop(newGrid, termGrid)
}

when (gridBox) {
    is Either.Left -> 
        println(gridBox.v)
    is Either.Right -> {
        val grid = gridBox.v
        val termGrid = TermGridImpl(grid.height, grid.width)
        termGrid.clear()
        loop(grid, termGrid)
        termGrid.reset()
    }
}

enum class CellState {
    Alive, Dead
}

object Cell {
    fun toChar(cellState: CellState): Char = when (cellState) {
        CellState.Alive -> '+'
        CellState.Dead -> '-'
    }

    fun next(cellState: CellState, neighborCount: Int): CellState =
        when (neighborCount) {
            in 0..1 -> CellState.Dead
            2 -> cellState
            3 -> CellState.Alive
            else -> CellState.Dead
        }

    fun fromChar(char: Char): CellState =
        when (char) {
            '+' -> CellState.Alive
            else -> CellState.Dead
        }
}

sealed class Either<out A, out B> {
    data class Left<A>(val v: A): Either<A, Nothing>()
    data class Right<B>(val v: B): Either<Nothing, B>()
}

data class Grid(val cells: List<List<CellState>>) {
    companion object {
        val neighborDelta = listOf(
                -1 to -1, -1 to 0, -1 to 1,
                 0 to -1,           0 to 1,
                 1 to -1,  1 to 0,  1 to 1
        )

        fun build(str: String): Either<String, Grid> {
            val lines = str.split("\n")
            println(lines)
            return if ((lines.size < 3) || (width(lines) < 3)) {
                Either.Left("Board must be at least 3 x 3")
            } else if (!lineLengthsMatch(lines)) {
                Either.Left("Board line lengths don't match")
            } else if (!onlyPlusesAndDashes(lines)) {
                Either.Left("Board must contain only + and - characters")
            } else {
                val cells = lines.map { it.map { Cell.fromChar(it) } }
                Either.Right(Grid(cells))
            }
        }

        fun width(lines: List<String>): Int =
            lines.getOrNull(0)?.length ?: 0

        fun lineLengthsMatch(lines: List<String>): Boolean {
            val initSize = width(lines)
            return lines.map { it.length }.all { it == initSize}
        }

        fun onlyPlusesAndDashes(lines: List<String>): Boolean =
            lines.all { it.all { char -> char == '-' || char == '+'}}
    }

    val height: Int = cells.size
    val width: Int = cells.getOrElse(0, { listOf<CellState>()}).size

    fun next(): Grid {
        val nextCells = (0 until height).map( {
            row -> (0 until width).map( {
                column -> nextCell(row, column)
            })
        })
        return Grid(nextCells)
    }

  private fun countAliveNeighbors(row: Int, column: Int): Int {
    fun offset(x: Int, dx: Int, size: Int): Int = when (val sum = x + dx) {
      -1 -> size - 1
      size -> 0
      else -> sum 
    }
    fun getNeighbor(neighborDelta: Pair<Int, Int>): CellState {
      val (rowDelta, columnDelta) = neighborDelta
      val newRow = offset(row, rowDelta, height)
      val newColumn = offset(column, columnDelta, width)
      return cells.get(newRow).get(newColumn)
    }
    return Grid.neighborDelta.map(::getNeighbor).count({ it == CellState.Alive })
  }

  private fun nextCell(row: Int, column: Int): CellState =
      Cell.next(cells.get(row).get(column), countAliveNeighbors(row, column))
}
