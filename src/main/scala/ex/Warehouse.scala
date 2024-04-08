package ex

import util.Optionals.Optional
import util.Optionals.Optional.*
import util.Sequences.*
import util.Sequences.Sequence.*

trait Item:
  def code: Int
  def name: String
  def tags: Sequence[String]

object Item:
  def apply(
      code: Int,
      name: String,
      tags: String*
  ): Item = ItemImpl(code, name, Sequence(tags*))
  private case class ItemImpl(
      override val code: Int,
      override val name: String,
      override val tags: Sequence[String]
  ) extends Item

/** A warehouse is a place where items are stored.
  */
trait Warehouse:
  /** Stores an item in the warehouse.
    * @param item
    *   the item to store
    */
  def store(item: Item): Unit

  /** Searches for items with the given tag.
    * @param tag
    *   the tag to search for
    * @return
    *   the list of items with the given tag
    */
  def searchItems(tag: String): Sequence[Item]

  /** Retrieves an item from the warehouse.
    * @param code
    *   the code of the item to retrieve
    * @return
    *   the item with the given code, if present
    */
  def retrieve(code: Int): Optional[Item]

  /** Removes an item from the warehouse.
    * @param item
    *   the item to remove
    */
  def remove(item: Item): Unit

  /** Checks if the warehouse contains an item with the given code.
    * @param itemCode
    *   the code of the item to check
    * @return
    *   true if the warehouse contains an item with the given code, false
    *   otherwise
    */
  def contains(itemCode: Int): Boolean

  /** Optional method extracts the tag t in common to all items (if any)
    * @param t
    */
  def sameTag(t: String): String
end Warehouse

object Warehouse:
  def apply(): Warehouse = WarehouseImpl()
  private case class WarehouseImpl() extends Warehouse:
    private var items: Sequence[Item] = Sequence.empty

    override def retrieve(code: Int): Optional[Item] =
      items.find(_.code == code)

    override def remove(item: Item): Unit = items = items.filter(_ != item)

    override def contains(itemCode: Int): Boolean =
      !retrieve(itemCode).isEmpty

    override def store(item: Item): Unit = items = Cons(item, items)

    override def searchItems(tag: String): Sequence[Item] =
      items.filter(_.tags.contains(tag))

    override def sameTag(t: String): String =
      ExtractTag.unapply(items).getOrElse("")

// Implement an extractor sameTag(t) on a list of Items that extracts the tag t in common to all items (if any)
object ExtractTag:
  def unapply(items: Sequence[Item]): Option[String] = items match
    case Sequence.Nil() => None
    case Cons(head, tail) =>
      val commonTag: Optional[String] =
        tail.foldLeft(head.tags)((acc, item) => acc.intersect(item.tags)).head
      if commonTag.isEmpty then None else Some(commonTag.orElse(""))

@main def mainWarehouse(): Unit =
  val warehouse = Warehouse()
  val dellXps = Item(33, "Dell XPS 15", "notebook")
  val dellInspiron = Item(34, "Dell Inspiron 13", "notebook")
  val xiaomiMoped = Item(35, "Xiaomi S1", "moped", "mobility")

  // val dellXps = Item(33, "Dell XPS 15", Sequence("notebook"))
  // val dellInspiron = Item(34, "Dell Inspiron 13", Sequence("notebook"))
  // val xiaomiMoped = Item(35, "Xiaomi S1", Sequence("moped", "mobility"))

  println(warehouse.contains(dellXps.code)) // false
  println(
    warehouse.store(dellXps)
  ) // side effect, add dell xps to the warehouse
  println(warehouse)
  println(warehouse.contains(dellXps.code)) // true
  warehouse.store(
    dellInspiron
  ) // side effect, add dell Inspiron to the warehouse
  warehouse.store(xiaomiMoped) // side effect, add xiaomi moped to the warehouse
  println(warehouse.searchItems("mobility")) // Sequence(xiaomiMoped)
  println(
    warehouse.searchItems("notebook")
  ) // Sequence(dellXps, dell Inspiron)
  println(warehouse.retrieve(11)) // None
  println(warehouse.retrieve(dellXps.code)) // Just(dellXps)
  warehouse.remove(dellXps) // side effect, remove dell xps from the warehouse
  println(warehouse.retrieve(dellXps.code)) // None
  val otherWarehouse = Warehouse()
  otherWarehouse.store(dellXps)
  otherWarehouse.store(dellInspiron)
  println(otherWarehouse.sameTag("notebook")) // notebook

/** Hints:
  *   - Implement the Item with a simple case class
  *   - Implement the Warehouse keeping a private List of items
  *   - Start implementing contains and store
  *   - Implement searchItems using filter and contains
  *   - Implement retrieve using find
  *   - Implement remove using filter
  *   - Refactor the code of Item accepting a variable number of tags (hint: use
  *     _*)
  */
