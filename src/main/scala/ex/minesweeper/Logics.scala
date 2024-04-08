package ex.minesweeper

import scala.util.Random

enum PieceName:
  case Pawn, Knight

trait Logics:
  def hit(row: Int, col: Int): Boolean
  def hasKnight(row: Int, col: Int): Boolean
  def hasPawn(row: Int, col: Int): Boolean

case class LogicsImpl(size: Int) extends Logics:
  private val pawnPiece: ChessPiece = ChessPiece(PiecesGenerator(size).randomPosition(), PieceName.Pawn)
  private val knightPiece: ChessPiece = ChessPiece(PiecesGenerator(size).randomPosition(), PieceName.Knight)
  override def hasPawn(row: Int, col: Int): Boolean = pawnPiece.position == Pair(row, col)
  override def hasKnight(row: Int, col: Int): Boolean = knightPiece.position == Pair(row, col)
  override def hit(row: Int, col: Int): Boolean = if knightPiece.move(row, col) then pawnPiece.position == knightPiece.position else false

trait ChessPiece:
  def position: Pair[Int, Int]
  def move(row: Int, col: Int): Boolean

object ChessPiece:
  def apply(position: Pair[Int, Int], pieceName: PieceName): ChessPiece = pieceName match {case PieceName.Pawn => PawnImpl(position) case PieceName.Knight => KnightImpl(position)}

  private case class PawnImpl(var position: Pair[Int, Int]) extends ChessPiece:
    override def move(row: Int, col: Int): Boolean =
      position = Pair(row, col)
      position == Pair(row, col)

  private case class KnightImpl(var position: Pair[Int, Int]) extends ChessPiece:
    override def move(row: Int, col: Int): Boolean =
      val x: Int = row - position.getX
      val y: Int = col - position.getY
      if x != 0 && y != 0 && Math.abs(x) + Math.abs(y) == 3
      then
        position = Pair(row, col)
        true
      else false

trait PiecesGenerator:
  def randomPosition(): Pair[Int, Int]

object PiecesGenerator:
  def apply(size: Int): PiecesGenerator = PiecesGeneratorImpl(size)

  private case class PiecesGeneratorImpl(size: Int) extends PiecesGenerator:
    private val pawnPosition: Pair[Int, Int] = Pair(0, 0)
    private val knightPosition: Pair[Int, Int] = Pair(0, 0)
    private val random: Random = Random()

    override def randomPosition(): Pair[Int, Int] =
      val pos: Pair[Int, Int] = Pair(random.nextInt(size), random.nextInt(size))
      if pawnPosition == pos || knightPosition == pos then randomPosition() else pos
