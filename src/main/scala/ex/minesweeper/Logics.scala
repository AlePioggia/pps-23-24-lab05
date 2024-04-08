package ex.minesweeper

import scala.util.Random

trait Logics:
    def pawn: Pair[Int, Int]
    def knight: Pair[Int, Int]
    def hit(row: Int, col: Int): Boolean
    def hasKnight(row: Int, col: Int): Boolean
    def hasPawn(row: Int, col: Int): Boolean

object Logics:
    def apply(
        pawn: Pair[Int, Int],
        knight: Pair[Int, Int],
        size: Int
    ): Logics = 
        LogicsImpl(pawn, knight, size)

    private case class LogicsImpl(
        override val pawn: Pair[Int, Int],
        override val knight: Pair[Int, Int],
        val size: Int
    ) extends Logics:

      private val pawnPiece: ChessPiece = Pawn(PiecesGenerator(size).generatePawnInArandomPosition());
      private val knightPiece: ChessPiece = Knight(PiecesGenerator(size).generateKnightInARandomPosition())

      override def hasPawn(row: Int, col: Int): Boolean = pawnPiece.position == Pair(row, col)

      override def hasKnight(row: Int, col: Int): Boolean = knightPiece.position == Pair(row, col)

      override def hit(row: Int, col: Int): Boolean = 
        if knightPiece.move(row, col) then pawnPiece.position == knightPiece.position else false

trait ChessPiece:
    def position: Pair[Int, Int]
    def position_(row: Int, col: Int): Unit = Pair(row, col)
    def move(row: Int, col: Int): Boolean

object Knight:
    def apply(position: Pair[Int, Int]): ChessPiece = KnightImpl(position)
    private case class KnightImpl(var position: Pair[Int, Int]) extends ChessPiece:
        override def move(row: Int, col: Int): Boolean =
            val x: Int = row - position.getX
            val y: Int = col - position.getY
            if x != 0 && y != 0 && Math.abs(x) + Math.abs(y) == 3 
            then 
                position = Pair(row, col)
                true 
            else false

object Pawn:
    def apply(position: Pair[Int, Int]): ChessPiece = PawnImpl(position)
    private case class PawnImpl(var position: Pair[Int, Int]) extends ChessPiece:
        override def move(row: Int, col: Int): Boolean = 
            position = Pair(row, col)
            position == Pair(row, col)

trait PiecesGenerator:
    def generatePawnInArandomPosition(): Pair[Int, Int]
    def generateKnightInARandomPosition(): Pair[Int, Int]

object PiecesGenerator:
    def apply(size: Int): PiecesGenerator = PiecesGeneratorImpl(size)
    private case class PiecesGeneratorImpl(size: Int) extends PiecesGenerator:
        val random: Random = Random()
        val pawnPosition: Pair[Int, Int] = randomPosition
        val knightPosition: Pair[Int, Int] = randomPosition

        override def generatePawnInArandomPosition(): Pair[Int, Int] = randomPosition
        override def generateKnightInARandomPosition(): Pair[Int, Int] = randomPosition

        def randomPosition: Pair[Int, Int] = 
            val pos: Pair[Int, Int] = Pair(random.nextInt(size), random.nextInt(size))
            if (pawnPosition != null && pawnPosition == pos || knightPosition != null && knightPosition == randomPosition) then randomPosition else pos 